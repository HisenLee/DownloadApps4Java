package prc.apk.download.utils;

import prc.apk.download.entity.Apk;

public class SearchSingleAppInfo {

	 public static Apk getAppInfoSearchFromBaidu(Apk topAppItemFromExcel, String isSoft, boolean isNeedProxy)
     {
		 Apk needApp = null;

         String apkName_Excel = topAppItemFromExcel.getApkName();
         String pkgName_Excel = topAppItemFromExcel.getPkgName();
         String category_Excel = topAppItemFromExcel.getCategory();

         String htmlCode = null;
         try
         {
        	 String searchUrl = "http://shouji.baidu.com/s?wd=" + apkName_Excel + "#page1";
             htmlCode = RequestUtils.getHtml(searchUrl, isNeedProxy);
             String[] appInfos = htmlCode.split("class=\"app-name");
             for (String splitItem : appInfos)
             {
                 if (appInfos[0] == splitItem)
                 {
                     continue;
                 }
                 try
                 {
                     // get pkgName from search result
                     int pkgNameSplitBegin = splitItem.indexOf("data_package=\"") + ("data_package=\"").length();
                     int pkgNameSplitEnd = splitItem.indexOf("data_versionname=");
                     String pkgNameSplit = splitItem.substring(pkgNameSplitBegin, pkgNameSplitEnd).trim();
                     String pkgName = pkgNameSplit.substring(0, pkgNameSplit.length() - 1);

                    
                     if (pkgName.equals(pkgName_Excel))
                     {
                         // get apkName
                    	 String apkName = apkName_Excel;

                         // get category
                    	 String category = category_Excel;

                         // get installCount
                         int installCountSplitBegin = splitItem.indexOf("download-num\">") + ("download-num\">").length();
                         int installCountSplitEnd = splitItem.indexOf("</span>¥Œœ¬‘ÿ</em>");
                         String installCountStr = splitItem.substring(installCountSplitBegin, installCountSplitEnd).trim();
                         long installCount = StringUtils.downloadNumberFromString(installCountStr);

                         // get apkSize 
                         int apkSizeSplitBegin = splitItem.indexOf("size\">") + ("size\">").length();
                         int apkSizeSplitEnd = splitItem.indexOf("class=\"down\">");
                         String apkSizeSplit = splitItem.substring(apkSizeSplitBegin, apkSizeSplitEnd).trim();
                         int apkSizeEnd = apkSizeSplit.indexOf("</span>");
                         String apkSize = apkSizeSplit.substring(0, apkSizeEnd).trim();

                         // get downloadUrl
                         int downloadUrlSplitBegin = splitItem.indexOf("data_url=\"") + ("data_url=\"").length();
                         int downloadUrlSplitEnd = splitItem.indexOf("data_from=\"");
                         String downloadUrlSplit = splitItem.substring(downloadUrlSplitBegin, downloadUrlSplitEnd).trim();
                         String downloadUrl = downloadUrlSplit.substring(0, downloadUrlSplit.length() - 1);

                         // get version
                         int versionSplitBegin = splitItem.indexOf("data_versionname=\"") + ("data_versionname=\"").length();
                         int versionSplitEnd = splitItem.indexOf("data_icon=");
                         String versionSplit = splitItem.substring(versionSplitBegin, versionSplitEnd).trim();
                         String version = versionSplit.substring(0, versionSplit.length() - 1);

                         Apk appInfo = new Apk(apkName, pkgName, version, apkSize, category, "", installCount, isSoft, downloadUrl, "Baidu");
                         needApp = appInfo;
                         break;
                     }
                 } // end try
                 catch (Exception ex)
                 {
                     ex.printStackTrace();
                 } // end catch                            

             } // end foreach
         }
         catch (Exception ex)
         {
             needApp = null;
             Log.info("Cannot find " + apkName_Excel + " from baidu");
         }
         return needApp;
     }
	 
	 public static Apk getAppInfoSearchFromTencent(Apk topAppItemFromExcel, String isSoft, boolean isNeedProxy)
     {
		 Apk needApp = null;

         String apkName_Excel = topAppItemFromExcel.getApkName();
         String pkgName_Excel = topAppItemFromExcel.getPkgName();

         String htmlCode = null;
         try
         {
        	 String searchUrl = "http://sj.qq.com/myapp/detail.htm?apkName=" + pkgName_Excel;
             htmlCode = RequestUtils.getHtml(searchUrl, isNeedProxy);

             Apk appInfo = StringUtils.getAppFromTencent(htmlCode, pkgName_Excel, isSoft);
             needApp = appInfo;     
         }
         catch (Exception e)
         {
             needApp = null;
             Log.info("Cannot find " + apkName_Excel + " from tencent");
         }
         return needApp;
     }
	 
	 public static Apk getAppInfoSearchFromQihoo(Apk topAppItemFromExcel, String isSoft, boolean isNeedProxy)
     {
		 Apk needApp = null;

		 String apkName_Excel = topAppItemFromExcel.getApkName();
		 String pkgName_Excel = topAppItemFromExcel.getPkgName();
		 String category_Excel = topAppItemFromExcel.getCategory();

		 String htmlCode = null;
         try
         {
        	 String searchUrl = "http://zhushou.360.cn/search/index/?kw=" + apkName_Excel + "&page=1";
             htmlCode = RequestUtils.getHtml(searchUrl, isNeedProxy);
             Log.info("Fetch web page: " + searchUrl);
             String[] appInfos = htmlCode.split("detail/index/soft_id");
             for (int i = 0; i < appInfos.length; i++)
             {
            	 String splitItem = appInfos[i];
                 if (appInfos[0] == splitItem)
                 {
                     continue;
                 }
                 if (i % 2 == 0)
                 {
                     continue;
                 }
                 // get apkDetailUrl
                 int softId = splitItem.indexOf("\">");
                 String detailUrl = "http://zhushou.360.cn/detail/index/soft_id" + splitItem.substring(0, softId);

                 try
                 {
                     // apkDetailHtml
                     htmlCode = RequestUtils.getHtml(detailUrl, isNeedProxy);
                     Apk appInfo = StringUtils.getAppFromQihoo(htmlCode, category_Excel, isSoft);
                     if(appInfo!=null)
                     {
                    	 if (appInfo.getPkgName().equals(pkgName_Excel))
                         {
                             needApp = appInfo;
                             break;
                         }
                    	 
                     }
                     
                 }
                 catch (Exception ex)
                 {
                     Log.info(detailUrl + " broken");
                     ex.printStackTrace();
                 } // end catch                                           

             } // end foreach
         }
         catch (Exception e)
         {
             needApp = null;
             Log.info("Cannot find " + apkName_Excel + " from qihoo");
         }
         return needApp;
     }
	 
	 public static Apk getAppInfoSearchFromWandoujia(Apk topAppItemFromExcel, String isSoft, boolean isNeedProxy)
     {
		 Apk needApp = null;

		 String apkName_Excel = topAppItemFromExcel.getApkName();
		 String pkgName_Excel = topAppItemFromExcel.getPkgName();
		 String category_Excel = topAppItemFromExcel.getCategory();

		 String htmlCode = null;
         try
         {
        	 String searchUrl = "http://www.wandoujia.com/" + pkgName_Excel;
             htmlCode = RequestUtils.getHtml(searchUrl, isNeedProxy);
             Log.info("Fetch web page: " + searchUrl);

             // get downloadUrl
             String downloadUrl = "http://www.wandoujia.com/apps/" + pkgName_Excel + "/download";

             Apk appInfo = StringUtils.getAppFromWandoujia(htmlCode, pkgName_Excel, downloadUrl, category_Excel, isSoft);
             needApp = appInfo;
         }
         catch (Exception e)
         {
             needApp = null;
             Log.info("Cannot find " + apkName_Excel + " from wandoujia");
         }
         return needApp;
     }
	
}
