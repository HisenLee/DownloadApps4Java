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

public class WandoujiaGame {
	 private String[] url_app_categories = {
			 "http://www.wandoujia.com/category/6001",  //��������
	            "http://www.wandoujia.com/category/6008", //�˿�����
	            "http://www.wandoujia.com/category/6002", //�������
	            "http://www.wandoujia.com/category/6009", //������Ϸ
	            "http://www.wandoujia.com/category/6006", //��ɫ����
	            "http://www.wandoujia.com/category/6003", //�ܿᾺ��
	            "http://www.wandoujia.com/category/6004", //����ð��
	            "http://www.wandoujia.com/category/6007", //��Ӫ����
	            "http://www.wandoujia.com/category/6005", //��������
	            "http://www.wandoujia.com/category/5015"  //��������
	     };
	    
	    private boolean isNeedProxy = false;
	    
		public WandoujiaGame(boolean isNeedProxy) {
			this.isNeedProxy = isNeedProxy;
		}
	    
		public List<Apk> getApkInfo() {
			List<Apk> allAppList = new ArrayList<Apk>();
			for (String categoryUrl : url_app_categories) {
				for (int pageIndex = 1; pageIndex <= 5; pageIndex++) {
					String pageUrl = categoryUrl + "/" + pageIndex;
					String htmlCode = null;
					try {
						Log.info("Fetch web page: " + pageUrl);
	                    htmlCode = RequestUtils.getHtml(pageUrl, isNeedProxy);
	                    String[] appInfos = htmlCode.split("app-title-h2");
	                    for (String splitItem : appInfos) {
	                    	if (appInfos[0] == splitItem)
	                        {
	                            continue;
	                        }
	                    	try {
	                    		// get apkDetailUrl
                                int detailBegin = splitItem.indexOf("href=\"") + ("href=\"").length();
                                int detailEnd = splitItem.indexOf("title=\"");
                                String detailUrlSplit = splitItem.substring(detailBegin, detailEnd).trim();
                                String detailUrl = detailUrlSplit.substring(0, detailUrlSplit.length() - 1).trim();

                                // get pkgName
                                int pkgNameBegin = detailUrl.lastIndexOf("/") + ("/").length();
                                String pkgName = detailUrl.substring(pkgNameBegin, detailUrl.length());

                                // get downloadUrl
                                String downloadUrl = "http://www.wandoujia.com/apps/" + pkgName + "/download";

                                // apkDetailHtml
                                htmlCode = RequestUtils.getHtml(detailUrl, isNeedProxy);

                                // get category
                                String category = StringUtils.getCategoryFromWandoujia(categoryUrl);

                                Apk appInfo = StringUtils.getAppFromWandoujia(htmlCode, pkgName, downloadUrl, category, "Game");
                                if (appInfo != null && !StringUtils.checkExists(appInfo, allAppList))
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
	            	Apk item = SearchSingleAppInfo.getAppInfoSearchFromWandoujia(topAppInfo, "Game", isNeedProxy);
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
			List<List<String>> rows = ExcelUtil.readXlsxExcel(Config.BASIC_PATH + "\\WandoujiaGame\\Top" + Config.WANDOUJIA_GAME_TOP + ".xlsx");
			for (List<String> list : rows) {
				
				String rankStr = list.get(0).toString();
				int rank = Integer.parseInt(rankStr);
				rankStr = StringUtils.parseRankFromInt(rank);
				String apkName = list.get(1).toString(); 
				String pkgName = list.get(3).toString();
				
				// Rank_Name_Store_SoftOrGame_.apk
				String appName = Config.BASIC_PATH + "\\WandoujiaGame\\" + 
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
					// ���سɹ���app���ܻ���������ʧЧ������excel�е����ݣ�������Ҫ�����ʵ����Ҫ���µ�excel
					CommonUtils.getRealExcel("WandoujiaGame");
		            // ���������ݵı������������rank�����apk�ļ�
					CommonUtils.reNameFile("WandoujiaGame");
		            // ���������ݵı����ɾ�������apk�ļ�
		            Log.info("delte unused files...");
		            CommonUtils.delUnusedFiles("WandoujiaGame");
				}
			}
		};
		
}
