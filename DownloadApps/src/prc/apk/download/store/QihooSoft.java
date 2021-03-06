package prc.apk.download.store;

import java.util.ArrayList;
import java.util.List;

import prc.apk.download.action.DownloadAction;
import prc.apk.download.action.DownloadAction.IDownloadCallBack;
import prc.apk.download.entity.Apk;
import prc.apk.download.entity.AppBean;
import prc.apk.download.utils.CommonUtils;
import prc.apk.download.utils.Config;
import prc.apk.download.utils.ExcelUtil;
import prc.apk.download.utils.Log;
import prc.apk.download.utils.RequestUtils;
import prc.apk.download.utils.SearchSingleAppInfo;
import prc.apk.download.utils.StringUtils;

public class QihooSoft {
	private String[] url_app_categories = {
			"http://zhushou.360.cn/list/index/cid/11/order/download/?page=", //系统安全
            "http://zhushou.360.cn/list/index/cid/12/order/download/?page=", //通讯社交
            "http://zhushou.360.cn/list/index/cid/14/order/download/?page=", //影音视听
            "http://zhushou.360.cn/list/index/cid/15/order/download/?page=", //新闻阅读
            "http://zhushou.360.cn/list/index/cid/16/order/download/?page=", //生活休闲
            "http://zhushou.360.cn/list/index/cid/18/order/download/?page=", //主题壁纸
            "http://zhushou.360.cn/list/index/cid/17/order/download/?page=", //办公商务
            "http://zhushou.360.cn/list/index/cid/102228/order/download/?page=", //摄影摄像
            "http://zhushou.360.cn/list/index/cid/102230/order/download/?page=", //购物优惠
            "http://zhushou.360.cn/list/index/cid/102231/order/download/?page=", //地图旅游
            "http://zhushou.360.cn/list/index/cid/102232/order/download/?page=", //教育学习
            "http://zhushou.360.cn/list/index/cid/102139/order/download/?page=", //金融理财
            "http://zhushou.360.cn/list/index/cid/102233/order/download/?page=", //健康医疗
	};

	private boolean isNeedProxy = false;

	public QihooSoft(boolean isNeedProxy) {
		this.isNeedProxy = isNeedProxy;
	}

	public List<Apk> getApkInfo() {
		List<Apk> allAppList = new ArrayList<Apk>();
		for (String categoryUrl : url_app_categories) {
			for (int pageIndex = 1; pageIndex <= 2; pageIndex++) {
				String pageUrl = categoryUrl + pageIndex;
				String htmlCode = null;
				try {
					Log.info("Fetch web page: " + pageUrl);
					htmlCode = RequestUtils.getHtml(pageUrl, isNeedProxy);
					String[] appInfos = htmlCode.split("detail/index/soft_id");
					for (int i = 0; i < appInfos.length; i++) {
						String splitItem = appInfos[i];
						if (appInfos[0] == splitItem) {
							continue;
						}
                        if (i % 2 == 0){
                            continue;
                        }
                       // get apkDetailUrl
                        int softId = splitItem.indexOf("\">");
                        String detailUrl = "http://zhushou.360.cn/detail/index/soft_id" + splitItem.substring(0, softId);
                        try {
                        	// apkDetailHtml
                            htmlCode = RequestUtils.getHtml(detailUrl, isNeedProxy);
                            String category = StringUtils.getCategoryFromQihoo(categoryUrl);
                            Apk appInfo = StringUtils.getAppFromQihoo(htmlCode, category, "Soft");
                            if (appInfo!=null && !StringUtils.checkExists(appInfo, allAppList)) 
    						{
    							allAppList.add(appInfo);
    						}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}// end for page
		} // end foreach
		
		List<Apk> highRankApps = StringUtils.getHighRankApps("Soft");
		for (Apk topAppInfo : highRankApps){
            if (!StringUtils.checkExists(topAppInfo, allAppList)) 
            {
                // if allAppList does not contain hot app, then get topApp from webpage and add topApp to allAppList
            	Apk item = SearchSingleAppInfo.getAppInfoSearchFromQihoo(topAppInfo, "Soft", isNeedProxy);
                if (item != null)
                {
                    allAppList.add(item);
                }
                               
            }
        } // end foreach highRankApps
		
		// allAppList: OrderByInstallCount
        allAppList.sort(null);
		
		return allAppList;
	}
	
	public void download() {
		List<AppBean> appDatas = new ArrayList<AppBean>();
		List<List<String>> rows = ExcelUtil.readXlsxExcel(Config.BASIC_PATH + "\\QihooSoft\\Top" + Config.QIHOO360_SOFT_TOP + ".xlsx");
		for (List<String> list : rows) {
			
			String rankStr = list.get(0).toString();
			int rank = Integer.parseInt(rankStr);
			rankStr = StringUtils.parseRankFromInt(rank);
			String apkName = list.get(1).toString(); 
			String pkgName = list.get(3).toString();
			
			// Rank_Name_Store_SoftOrGame_.apk
			String appName = Config.BASIC_PATH + "\\QihooSoft\\" + 
					rankStr +"_"+apkName+"_"+pkgName+"_.apk";
			String downloadUrl = list.get(8).toString();
			
			AppBean appBean = new AppBean(appName, downloadUrl);
			appDatas.add(appBean);
		}// end for
		if(appDatas.size()>0) {
			DownloadAction downloadAction = new DownloadAction(appDatas, isNeedProxy, downloadCallBack);
			downloadAction.download();
		}
		
	}
	
	IDownloadCallBack downloadCallBack = new IDownloadCallBack() {
		
		@Override
		public void downloadActionProcess(String process, boolean isFinish) {
			Log.info(process);
			if (isFinish) {
				// 下载成功的app可能会由于链接失效而少于excel中的数据，所以需要整理出实际需要的新的excel
				CommonUtils.getRealExcel("QihooSoft");
	            // 根据新数据的表格来重命名新rank排序的apk文件
				CommonUtils.reNameFile("QihooSoft");
	            // 根据新数据的表格来删除多余的apk文件
	            Log.info("delte unused files...");
	            CommonUtils.delUnusedFiles("QihooSoft");
			}
		}
	};
	
}
