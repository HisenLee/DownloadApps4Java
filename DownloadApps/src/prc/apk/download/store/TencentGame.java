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

public class TencentGame {

	private String[] url_app_categories = {
			"http://sj.qq.com/myapp/category.htm?orgame=2&categoryId=148&pageContext=", //棋牌中心
            "http://sj.qq.com/myapp/category.htm?orgame=2&categoryId=147&pageContext=", //休闲益智
            "http://sj.qq.com/myapp/category.htm?orgame=2&categoryId=121&pageContext=", //网络游戏
            "http://sj.qq.com/myapp/category.htm?orgame=2&categoryId=149&pageContext=", //飞行射击
            "http://sj.qq.com/myapp/category.htm?orgame=2&categoryId=144&pageContext=", //动作冒险
            "http://sj.qq.com/myapp/category.htm?orgame=2&categoryId=151&pageContext=", //体育竞速            
            "http://sj.qq.com/myapp/category.htm?orgame=2&categoryId=153&pageContext=", //经营策略
            "http://sj.qq.com/myapp/category.htm?orgame=2&categoryId=146&pageContext="  //角色扮演
	};

	private boolean isNeedProxy = false;

	public TencentGame(boolean isNeedProxy) {
		this.isNeedProxy = isNeedProxy;
	}

	public List<Apk> getApkInfo() {
		List<Apk> allAppList = new ArrayList<Apk>();
		for (String categoryUrl : url_app_categories) {
			for (int pageIndex = 0; pageIndex <= 10; pageIndex++) {
				String pageUrl = categoryUrl + pageIndex * 20;
				String htmlCode = null;
				try {
					Log.info("Fetch web page: " + pageUrl);
					htmlCode = RequestUtils.getHtml(pageUrl, isNeedProxy);
					String[] appInfos = htmlCode.split("app-info clearfix");
					for (String splitItem : appInfos) {
						if (appInfos[0] == splitItem) {
							continue;
						}
						// get appDetailUrl
                        int appDetailUrlBegin = splitItem.indexOf("href=\"") + ("href=\"").length();
                        int appDetailUrlEnd = splitItem.indexOf("\" target=");
                        String appDetailUrl = "http://sj.qq.com/myapp/" + splitItem.substring(appDetailUrlBegin, appDetailUrlEnd).trim();

                        // get pkgName
                        int pkgNameBegin = appDetailUrl.indexOf("detail.htm?apkName=") + ("detail.htm?apkName=").length();
                        int pkgNameEnd = appDetailUrl.length();
                        String pkgName = appDetailUrl.substring(pkgNameBegin, pkgNameEnd).trim();
                        try {
                        	// get appDetailHtml
                            htmlCode = RequestUtils.getHtml(appDetailUrl, isNeedProxy);
    						Apk appInfo = StringUtils.getAppFromTencent(htmlCode, pkgName, "Game");
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
            	Apk item = SearchSingleAppInfo.getAppInfoSearchFromTencent(topAppInfo, "Game", isNeedProxy);
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
		List<List<String>> rows = ExcelUtil.readXlsxExcel(Config.BASIC_PATH + "\\TencentGame\\Top" + Config.TENCENT_GAME_TOP + ".xlsx");
		for (List<String> list : rows) {
			
			String rankStr = list.get(0).toString();
			int rank = Integer.parseInt(rankStr);
			rankStr = StringUtils.parseRankFromInt(rank);
			String apkName = list.get(1).toString(); 
			String pkgName = list.get(3).toString();
			
			// Rank_Name_Store_SoftOrGame_.apk
			String appName = Config.BASIC_PATH + "\\TencentGame\\" + 
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
				CommonUtils.getRealExcel("TencentGame");
	            // 根据新数据的表格来重命名新rank排序的apk文件
				CommonUtils.reNameFile("TencentGame");
	            // 根据新数据的表格来删除多余的apk文件
	            Log.info("delte unused files...");
	            CommonUtils.delUnusedFiles("TencentGame");
			}
		}
	};
	
}
