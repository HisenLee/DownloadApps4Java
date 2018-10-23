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

public class QihooGame {
	private String[] url_app_categories = {
			"http://zhushou.360.cn/list/index/cid/101587/order/download/?page=", //角色扮演
            "http://zhushou.360.cn/list/index/cid/19/order/download/?page=", //休闲益智
            "http://zhushou.360.cn/list/index/cid/20/order/download/?page=", //动作冒险
            "http://zhushou.360.cn/list/index/cid/100451/order/download/?page=", //网络游戏
            "http://zhushou.360.cn/list/index/cid/51/order/download/?page=", //体育竞速
            "http://zhushou.360.cn/list/index/cid/52/order/download/?page=", //飞行射击
            "http://zhushou.360.cn/list/index/cid/53/order/download/?page=", //经营策略
            "http://zhushou.360.cn/list/index/cid/54/order/download/?page=", //棋牌天地
            "http://zhushou.360.cn/list/index/cid/102238/order/download/?page=" //儿童游戏
	};

	private boolean isNeedProxy = false;

	public QihooGame(boolean isNeedProxy) {
		this.isNeedProxy = isNeedProxy;
	}

	public List<Apk> getApkInfo() {
		List<Apk> allAppList = new ArrayList<Apk>();
		for (String categoryUrl : url_app_categories) {
			for (int pageIndex = 1; pageIndex <= 3; pageIndex++) {
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
                            Apk appInfo = StringUtils.getAppFromQihoo(htmlCode, category, "Game");
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
		
		List<Apk> highRankApps = StringUtils.getHighRankApps("Game");
		for (Apk topAppInfo : highRankApps){
            if (!StringUtils.checkExists(topAppInfo, allAppList)) 
            {
                // if allAppList does not contain hot app, then get topApp from webpage and add topApp to allAppList
            	Apk item = SearchSingleAppInfo.getAppInfoSearchFromQihoo(topAppInfo, "Game", isNeedProxy);
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
		List<List<String>> rows = ExcelUtil.readXlsxExcel(Config.BASIC_PATH + "\\QihooGame\\Top" + Config.QIHOO360_GAME_TOP + ".xlsx");
		for (List<String> list : rows) {
			
			String rankStr = list.get(0).toString();
			int rank = Integer.parseInt(rankStr);
			rankStr = StringUtils.parseRankFromInt(rank);
			String apkName = list.get(1).toString(); 
			String pkgName = list.get(3).toString();
			
			// Rank_Name_Store_SoftOrGame_.apk
			String appName = Config.BASIC_PATH + "\\QihooGame\\" + 
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
				CommonUtils.getRealExcel("QihooGame");
	            // 根据新数据的表格来重命名新rank排序的apk文件
				CommonUtils.reNameFile("QihooGame");
	            // 根据新数据的表格来删除多余的apk文件
	            Log.info("delte unused files...");
	            CommonUtils.delUnusedFiles("QihooGame");
			}
		}
	};
}
