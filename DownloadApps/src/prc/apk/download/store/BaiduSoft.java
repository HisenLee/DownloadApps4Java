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

public class BaiduSoft {
		
    private String[] url_app_categories = {
         "http://shouji.baidu.com/software/503/", //社交通讯
         "http://shouji.baidu.com/software/501/", //系统工具
         "http://shouji.baidu.com/software/510/", //理财购物
         "http://shouji.baidu.com/software/502/", //主题壁纸
         "http://shouji.baidu.com/software/509/", //旅游出行
         "http://shouji.baidu.com/software/506/", //影音播放
         "http://shouji.baidu.com/software/508/", //拍摄美化
         "http://shouji.baidu.com/software/504/", //生活实用
         "http://shouji.baidu.com/software/505/", //资讯阅读
         "http://shouji.baidu.com/software/507/"  //办公学习            
     };
    
    private boolean isNeedProxy = false;
    
	public BaiduSoft(boolean isNeedProxy) {
		this.isNeedProxy = isNeedProxy;
	}
    
	public List<Apk> getApkInfo() {
		List<Apk> allAppList = new ArrayList<Apk>();
		for (String categoryUrl : url_app_categories) {
			for (int pageIndex = 1; pageIndex <= 3; pageIndex++) {
				String pageUrl = categoryUrl + "list_" + pageIndex + ".html";
				String htmlCode = null;
				try {
					Log.info("Fetch web page: " + pageUrl);
                    htmlCode = RequestUtils.getHtml(pageUrl, isNeedProxy);
                    String[] appInfos = htmlCode.split("app-box");
                    for (String splitItem : appInfos) {
                    	if (appInfos[0] == splitItem)
                        {
                            continue;
                        }
                    	String category = StringUtils.getCategoryFromBaidu(categoryUrl);
                    	Apk appInfo = StringUtils.getAppFromBaidu(splitItem, category, "Soft");
                    	if (appInfo!=null && !StringUtils.checkExists(appInfo, allAppList)) 
						{
							allAppList.add(appInfo);
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
            	Apk item = SearchSingleAppInfo.getAppInfoSearchFromBaidu(topAppInfo, "Soft", isNeedProxy);
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
		List<List<String>> rows = ExcelUtil.readXlsxExcel(Config.BASIC_PATH + "\\BaiduSoft\\Top" + Config.BAIDU_SOFT_TOP + ".xlsx");
		for (List<String> list : rows) {
			
			String rankStr = list.get(0).toString();
			int rank = Integer.parseInt(rankStr);
			rankStr = StringUtils.parseRankFromInt(rank);
			String apkName = list.get(1).toString(); 
			String pkgName = list.get(3).toString();
			
			// Rank_Name_Store_SoftOrGame_.apk
			String appName = Config.BASIC_PATH + "\\BaiduSoft\\" + 
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
				CommonUtils.getRealExcel("BaiduSoft");
	            // 根据新数据的表格来重命名新rank排序的apk文件
				CommonUtils.reNameFile("BaiduSoft");
	            // 根据新数据的表格来删除多余的apk文件
	            Log.info("delte unused files...");
	            CommonUtils.delUnusedFiles("BaiduSoft");
			}
		}
	};
	
}
