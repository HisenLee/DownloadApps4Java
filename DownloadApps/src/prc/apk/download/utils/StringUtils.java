package prc.apk.download.utils;

import java.util.ArrayList;
import java.util.List;

import prc.apk.download.entity.Apk;


public final class StringUtils {
	/**
	 * checkEmptyStr
	 * @param str
	 * @return
	 */
	public static boolean isEmptyStr(String str) {
		if("".equals(str) || str==null) {
			return true;
		} else {
			return false;
		}
	}
	
	 public static boolean checkExists(Apk appInfo, List<Apk> allAppList)
     {
		 boolean exists = false;
         for (Apk alreadyExistsItem : allAppList)
         {
             if (alreadyExistsItem.getPkgName().equals(appInfo.getPkgName()))
             {
                 exists = true;
             }
         }
         return exists;
     }
	
	public static long downloadNumberFromString(String downloads)
    {
        if (downloads.contains("万"))
        {
            if (downloads.contains("."))
            {
                long intgr = Long.parseLong(downloads.substring(0, downloads.indexOf(".")));
                long dt = Long.parseLong(downloads.substring(downloads.indexOf(".") + 1, downloads.indexOf("万")));
                return intgr * 10000 + dt * 1000;
            }
            return Long.parseLong(downloads.substring(0, downloads.indexOf("万"))) * 10000;
        }
        if (downloads.contains("亿"))
        {
            if (downloads.contains("."))
            {
                long intgr = Long.parseLong(downloads.substring(0, downloads.indexOf(".")));
                long dt = Long.parseLong(downloads.substring(downloads.indexOf(".") + 1, downloads.indexOf("亿")));
                return intgr * 10000 * 10000 + dt * 10000 * 1000;
            }
            else
            {
                return Long.parseLong(downloads.substring(0, downloads.indexOf("亿"))) * 10000 * 10000;
            }
        }
        if (downloads.contains("千"))
        {
            return Long.parseLong(downloads.substring(0, downloads.indexOf("千"))) * 1000;
        }
        return Long.parseLong(downloads);
    }
	
	public static String getCategoryFromBaidu(String categoryUrl)
    {   
		String category = "";
        int categoryId = Integer.parseInt(categoryUrl.substring(categoryUrl.length() - 4, categoryUrl.length() - 1));
        switch (categoryId) {	
		case 503:
        	category =  "社交通讯";
        	break;
        case 501:
        	category =  "系统工具";
        	break;
        case 510:
        	category =  "理财购物";
        	break;
        case 502:
        	category =  "主题壁纸";
        	break;
        case 509:
        	category =  "旅游出行";
        	break;
        case 506:
        	category =  "影音播放";
        	break;
        case 508:
        	category =  "拍摄美化";
        	break;
        case 504:
        	category =  "生活实用";
        	break;
        case 505:
        	category =  "资讯阅读";
        	break;
        case 507:
        	category =  "办公学习";
        	break;

        case 401:
        	category =  "休闲益智";
        	break;
        case 406:
        	category =  "赛车竞速";
        	break;
        case 407:
        	category =  "棋牌桌游";
        	break;
        case 402:
        	category =  "角色扮演";
        	break;
        case 404:
        	category =  "模拟辅助";
        	break;
        case 403:
        	category =  "动作射击";
        	break;
        case 200:
        	category =  "网络游戏";
        	break;
        case 408:
        	category =  "经营养成";
        	break;
        case 405:
        	category =  "体育竞技";
        	break;
		default:
			break;
		}
        return category;           
    }
	
	public static String getCategoryFromQihoo(String categoryUrl)
    {
		String category = "";
        int index_start = categoryUrl.indexOf("/cid/") + ("/cid/").length();
        int index_end = categoryUrl.indexOf("/order/");
        int categoryId = Integer.parseInt(categoryUrl.substring(index_start, index_end));
        
        switch (categoryId) {
        case 11:
        	category =  "系统安全";
        	break;
        case 12:
        	category =  "通讯社交";
        	break;
        case 14:
        	category =  "影音视听";
        	break;
        case 15:
        	category =  "新闻阅读";
        	break;
        case 16:
        	category =  "生活休闲";
        	break;
        case 18:
        	category =  "主题壁纸";
        	break;
        case 17:
        	category =  "办公商务";
        	break;
        case 102228:
        	category =  "摄影摄像";
        	break;
        case 102230:
        	category =  "购物优惠";
        	break;
        case 102231:
        	category =  "地图旅游";
        	break;
        case 102232:
        	category =  "教育学习";
        	break;
        case 102139:
        	category =  "金融理财";
        	break;
        case 102233:
        	category =  "健康医疗";
        	break;


        case 101587:
        	category =  "角色扮演";
        	break;
        case 19:
        	category =  "休闲益智";
        	break;
        case 20:
        	category =  "动作冒险";
        	break;
        case 100451:
        	category =  "网络游戏";
        	break;
        case 51:
        	category =  "体育竞速";
        	break;
        case 52:
        	category =  "飞行射击";
        	break;
        case 53:
        	category =  "经营策略";
        	break;
        case 54:
        	category =  "棋牌天地";
        	break;
        case 102238:
        	category =  "儿童游戏";
        	break;

		default:
			break;
		}
        
        return category;
    }
	
	public static String getCategoryFromWandoujia(String categoryUrl)
    {
		String category = "";
        int index_start = categoryUrl.lastIndexOf("/") + 1;
        int index_end = categoryUrl.length();
        int categoryId = Integer.parseInt(categoryUrl.substring(index_start, index_end));

        switch (categoryId)
        {
            case 5014:
            	category =  "通讯社交";
            	break;
            case 5029:
            	category =  "影音播放";
            	break;
            case 5019:
            	category =  "新闻阅读";
            	break;
            case 5026:
            	category =  "考试学习";
            	break;
            case 5023:
            	category =  "金融理财";
            	break;
            case 5021:
            	category =  "旅游出行";
            	break;
            case 5018:
            	category =  "系统工具";
            	break;
            case 5022:
            	category =  "办公商务";
            	break;
            case 5024:
            	category =  "手机美化";
            	break;
            case 5016:
            	category =  "摄影图像";
            	break;
            case 5017:
            	category =  "网上购物";
            	break;
            case 5020:
            	category =  "生活休闲";
            	break;
            case 5028:
            	category =  "健康运动";
            	break;
            case 5027:
            	category =  "育儿亲子";
            	break;


            case 6001:
            	category =  "休闲益智";
            	break;
            case 6008:
            	category =  "扑克棋牌";
            	break;
            case 6002:
            	category =  "飞行射击";
            	break;
            case 6009:
            	category =  "网络游戏";
            	break;
            case 6006:
            	category =  "角色扮演";
            	break;
            case 6003:
            	category =  "跑酷竞速";
            	break;
            case 6004:
            	category =  "动作冒险";
            	break;
            case 6007:
            	category =  "经营策略";
            	break;
            case 6005:
            	category =  "体育竞技";
            	break;
            case 5015:
            	category =  "辅助工具";
            	break;
            default:
    			break;
        }
        return category;
    }
	
	public static String getApkNameContainsInvalid(String apkName) {
		if (apkName.contains(":")) // ? * / \ < > : " |
        {
            apkName = apkName.replace(':', '-');
        }
        if (apkName.contains("?"))
        {
            apkName = apkName.replace('?', '-');
        }
        if (apkName.contains("*"))
        {
            apkName = apkName.replace('*', '-');
        }
        if (apkName.contains("|"))
        {
            apkName = apkName.replace('|', '-');
        }
        if (apkName.contains("/"))
        {
            apkName = apkName.replace('/', '-');
        }
        if (apkName.contains("\\"))
        {
            apkName = apkName.replace('\\', '-');
        }
        if (apkName.contains("<"))
        {
            apkName = apkName.replace('<', '-');
        }
        if (apkName.contains(">"))
        {
            apkName = apkName.replace('>', '-');
        }
        if (apkName.contains("\""))
        {
            apkName = apkName.replace('"', '-');
        }
        return apkName;
	}
	
	public static Apk getAppFromBaidu(String splitItem, String category, String isSoft) {
        int splitBegin = splitItem.indexOf("class=\"name\">") + ("class=\"name\">").length();
        int splitEnd = splitItem.indexOf("<em>装进手机</em>");
        String temp = splitItem.substring(splitBegin, splitEnd).trim();

        // getApkName
        int nameEndIndex = temp.indexOf("</p>");
        String apkName = temp.substring(0, nameEndIndex).trim();
        apkName = getApkNameContainsInvalid(apkName);


        // getInstallCount
        int installCountBegin = temp.indexOf("down\">") + ("down\">").length();
        int installCountEnd = temp.indexOf("下载</span>");
        String installCountStr = temp.substring(installCountBegin, installCountEnd);
        long installCount = StringUtils.downloadNumberFromString(installCountStr);
        // getApkSize
        int tempSizeBegin = temp.indexOf("class=\"size\">") + ("class=\"size\">").length();
        int tempSizeEnd = temp.indexOf("class=\"down-btn\">");
        String tempSizeStr = temp.substring(tempSizeBegin, tempSizeEnd);
        int tempSizeEnd2 = tempSizeStr.indexOf("</span>");
        String apkSize = tempSizeStr.substring(0, tempSizeEnd2);
        // getDownloadUrl
        int downloadUrlBegin = temp.indexOf("data_url=\"") + ("data_url=\"").length();
        int downloadUrlEnd = temp.indexOf("data_name=");
        String downloadUrlTmp = temp.substring(downloadUrlBegin, downloadUrlEnd).trim();
        String downloadUrl = downloadUrlTmp.substring(0, downloadUrlTmp.length() - 1);
        // getpkgName
        int pkgNameBegin = temp.indexOf("data_package=\"") + ("data_package=\"").length();
        int pkgNameEnd = temp.indexOf("data_versionname=");
        String pkgNameTmp = temp.substring(pkgNameBegin, pkgNameEnd).trim();
        String pkgName = pkgNameTmp.substring(0, pkgNameTmp.length() - 1);
        // getVersion
        int versionBegin = temp.indexOf("data_versionname=\"") + ("data_versionname=\"").length();
        int versionEnd = temp.indexOf("data_icon=");
        String versionTmp = temp.substring(versionBegin, versionEnd).trim();
        String version = versionTmp.substring(0, versionTmp.length() - 1);

        Apk appInfo = new Apk(apkName, pkgName, version, apkSize, category, "", installCount, isSoft, downloadUrl, "Baidu");
        return appInfo;
    }
	
	 public static Apk getAppFromTencent(String htmlCode, String pkgName, String isSoft){
         // get appName
         int appNameSplitBegin = htmlCode.indexOf("det-name-int\">") + ("det-name-int\">").length();
         int appNameSplitEnd = htmlCode.indexOf("det-insnum-line");
         if(appNameSplitEnd==-1 || appNameSplitEnd<appNameSplitBegin) {
        	return null; 
         }
         String appNameSplit = htmlCode.substring(appNameSplitBegin, appNameSplitEnd).trim();
         int appNameEnd = appNameSplit.indexOf("</div>");
         String apkName = appNameSplit.substring(0, appNameEnd).trim();
         apkName = getApkNameContainsInvalid(apkName);


         // get installCount
         int installCountSplitBegin = htmlCode.indexOf("det-ins-num\">") + ("det-ins-num\">").length();
         int installCountSplitEnd = htmlCode.indexOf("det-size");
         String installCountSplit = htmlCode.substring(installCountSplitBegin, installCountSplitEnd).trim();
         int installCountEnd = installCountSplit.indexOf("下载</div>");
         String installCountStr = installCountSplit.substring(0, installCountEnd).trim();
         long installCount = StringUtils.downloadNumberFromString(installCountStr);

         // get apkSize
         int apkSizeSplitBegin = htmlCode.indexOf("det-size\">") + ("det-size\">").length();
         int apkSizeSplitEnd = htmlCode.indexOf("det-type-tit");
         String apkSizeSplit = htmlCode.substring(apkSizeSplitBegin, apkSizeSplitEnd).trim();
         int apkSizeEnd = apkSizeSplit.indexOf("</div>");
         String apkSize = apkSizeSplit.substring(0, apkSizeEnd).trim();

         // get category
         int categorySplitBegin = htmlCode.indexOf("J_DetCate\">") + ("J_DetCate\">").length();
         int categorySplitEnd = htmlCode.indexOf("desc\">");
         String categorySplit = htmlCode.substring(categorySplitBegin, categorySplitEnd).trim();
         int categoryEnd = categorySplit.indexOf("</a>");
         String category = categorySplit.substring(0, categoryEnd).trim();

         // get version
         int versionSplitBegin = htmlCode.indexOf("det-othinfo-data\">") + ("det-othinfo-data\">").length();
         int versionSplitEnd = htmlCode.indexOf("det-othinfo-tit\">更新时间");
         String versionSplit = htmlCode.substring(versionSplitBegin, versionSplitEnd).trim();
         int versionEnd = versionSplit.indexOf("</div>");
         String version = versionSplit.substring(0, versionEnd).trim();

         // get company
         String company = "";
         int companySplitBegin = htmlCode.indexOf("det-othinfo-tit\">开发商：</div>") + ("det-othinfo-tit\">开发商：</div>").length();
         int companySplitEnd = htmlCode.indexOf("det-intro-tit");
         if (companySplitEnd!=-1 && companySplitEnd>companySplitBegin)
         {
        	 String companySplit = htmlCode.substring(companySplitBegin, companySplitEnd).trim();
             int companyBegin = companySplit.indexOf("det-othinfo-data\">") + ("det-othinfo-data\">").length();
             int companyEnd = companySplit.indexOf("</div>");

             if (companyEnd!=-1 && companyEnd>companyBegin)
             {
                 company = companySplit.substring(companyBegin, companyEnd).trim();
             }
             
         }           

         // get downloadUrl
         int downloadUrlSplitBegin = htmlCode.indexOf("ex_url=\"") + ("ex_url=\"").length();
         int downloadUrlSplitEnd = htmlCode.indexOf("asistanturlid=\"");
         String downloadUrlSplit = htmlCode.substring(downloadUrlSplitBegin, downloadUrlSplitEnd).trim();
         String downloadUrl = downloadUrlSplit.substring(0, downloadUrlSplit.length() - 1);

         Apk appInfo = new Apk(apkName, pkgName, version, apkSize, category, company, installCount, isSoft, downloadUrl, "Tencent");
         return appInfo;
     }
	 
	 public static Apk getAppFromQihoo(String htmlCode, String category, String isSoft)
     {
         // get apkName, pkgName, downloadUrl
         int split1Begin = htmlCode.indexOf("var detail") + ("var detail").length();
         int split1End = htmlCode.indexOf("<body class=\"index");
         if(split1End == -1){        	 
        	 return null; // 应用获取异常
         }
         String split1 = htmlCode.substring(split1Begin, split1End).trim();

         int apkNameSplitBegin = split1.indexOf("sname': '") + ("sname': '").length();
         int apkNameSplitEnd = split1.indexOf("'type'");
         String apkNameSplit = split1.substring(apkNameSplitBegin, apkNameSplitEnd).trim();
         String apkName = apkNameSplit.substring(0, apkNameSplit.length() - 2);
         apkName = getApkNameContainsInvalid(apkName);


         int pkgNameSplitBegin = split1.indexOf("pname': \"") + ("pname': \"").length();
         int pkgNameSplitEnd = split1.indexOf("'downloadUrl");
         String pkgNameSplit = split1.substring(pkgNameSplitBegin, pkgNameSplitEnd).trim();
         String pkgName = pkgNameSplit.substring(0, pkgNameSplit.length() - 2);

         int downloadUrlSplitBegin = split1.indexOf("downloadUrl': '") + ("downloadUrl': '").length();
         int downloadUrlSplitEnd = split1.indexOf("'filemd5");
         String downloadUrlSplit = split1.substring(downloadUrlSplitBegin, downloadUrlSplitEnd).trim();
         String downloadUrl = downloadUrlSplit.substring(0, downloadUrlSplit.length() - 2);

         // get installCount, apkSize
         int split2Begin = htmlCode.indexOf("class=\"s-3\">") + ("class=\"s-3\">").length();
         int split2End = htmlCode.indexOf("立即安装</a>");
         String split2 = htmlCode.substring(split2Begin, split2End).trim();

         int installCountSplitBegin = split2.indexOf("下载：") + ("下载：").length();
         int installCountSplitEnd = split2.indexOf("次</span>");
         String installCountStr = split2.substring(installCountSplitBegin, installCountSplitEnd).trim();
         long installCount = StringUtils.downloadNumberFromString(installCountStr);

         int apkSizeBegin = split2.indexOf("=\"s-3\">") + ("=\"s-3\">").length();
         int apkSizeEnd = split2.lastIndexOf("</span>");
         String apkSize = split2.substring(apkSizeBegin, apkSizeEnd).trim();

         // get version, company
         int split3Begin = htmlCode.indexOf("作者：</strong>") + ("作者：</strong>").length();
         int split3End = htmlCode.indexOf("<strong>系统");
         String split3 = htmlCode.substring(split3Begin, split3End).trim();

         int companyEnd = split3.indexOf("</td>");
         String company = split3.substring(0, companyEnd);

         int versionBegin = split3.indexOf("版本：</strong>") + ("版本：</strong>").length();
         int versionEnd;
         int endFlag = split3.indexOf("<!--");
         if (endFlag != -1)
         {
             versionEnd = split3.indexOf("<!--");
         }
         else
         {
             versionEnd = split3.lastIndexOf("</td>");
         }
         String version = split3.substring(versionBegin, versionEnd).trim();

         Apk appInfo = new Apk(apkName, pkgName, version, apkSize, category, company, installCount, isSoft, downloadUrl, "Qihoo");
         return appInfo;
     }
	 
	 public static Apk getAppFromWandoujia(String htmlCode, String pkgName, String downloadUrl, String category, String isSoft)
     {            
         // get apkName
		 String apkName = "";
         int apkNameSplitBegin = htmlCode.indexOf("app-info") + ("app-info").length();
         int apkNameSplitEnd = htmlCode.indexOf("class=\"download-wp");
         if (apkNameSplitEnd != -1)
         {
        	 String apkNameSplit = htmlCode.substring(apkNameSplitBegin, apkNameSplitEnd).trim();
             int apkNameBegin = apkNameSplit.indexOf("itemprop=\"name\">") + ("itemprop=\"name\">").length();
             int apkNameEnd = apkNameSplit.indexOf("</span>");
             apkName = apkNameSplit.substring(apkNameBegin, apkNameEnd).trim();
             apkName = getApkNameContainsInvalid(apkName);

         }
         else
         {
             int apkNameSplitEnd2 = htmlCode.indexOf("offline-info");
             // 豌豆荚有可能会搜出下线的app，此时需要特殊处理
             if (apkNameSplitEnd2!=-1)
             {
                 return null;
             }
             
         }

         // get InstallCount
         int installCountSplitBegin = htmlCode.indexOf("UserDownloads:") + ("UserDownloads:").length();
         int installCountSplitEnd = htmlCode.indexOf("</i><b>次下载</b>");
         String installCountSplit = htmlCode.substring(installCountSplitBegin, installCountSplitEnd).trim();
         int installCountBegin = installCountSplit.indexOf("\">") + ("\">").length();
         int installCountEnd = installCountSplit.length();
         String installCountStr = installCountSplit.substring(installCountBegin, installCountEnd).trim();
         long installCount = StringUtils.downloadNumberFromString(installCountStr);

         // get apkSize
         int apkSizeSplitBegin = htmlCode.indexOf("fileSize") + ("fileSize").length();
         int apkSizeSplitEnd = htmlCode.indexOf("<dt>分类</dt>");
         String apkSizeSplit = htmlCode.substring(apkSizeSplitBegin, apkSizeSplitEnd).trim();
         int apkSizeBegin = apkSizeSplit.indexOf("content=\"") + ("content=\"").length();
         int apkSizeEnd = apkSizeSplit.indexOf("\"></dd>");
         String apkSize = apkSizeSplit.substring(apkSizeBegin, apkSizeEnd).trim();

         // get version
         String version = "";

         int versionSplitBegin = htmlCode.indexOf("<dt>版本</dt>") + ("<dt>版本</dt>").length();
         // 有的app界面没有要求这一栏
         int hasNeedFlag = htmlCode.indexOf("<dt>要求</dt>");
         if (hasNeedFlag != -1)
         {
             int versionSplitEnd = hasNeedFlag;
             String versionSplit = htmlCode.substring(versionSplitBegin, versionSplitEnd).trim();

             int versionTmpFlag = versionSplit.indexOf("&nbsp;");
             if (versionTmpFlag != -1)
             {
                 int versionBegin = versionSplit.indexOf("<dd>&nbsp;") + ("<dd>&nbsp;").length();
                 int versionEnd = versionSplit.indexOf("</dd>");
                 version = versionSplit.substring(versionBegin, versionEnd).trim();
             }
             else
             {
                 int versionBegin = versionSplit.indexOf("<dd>") + ("<dd>").length();
                 int versionEnd = versionSplit.indexOf("</dd>");
                 version = versionSplit.substring(versionBegin, versionEnd).trim();
             }
         }
         else
         {
             // 没有要求这一栏(但有来自这一栏)
             int versionSplitEnd = htmlCode.indexOf("<dt>来自</dt>");
             if (versionSplitEnd != -1)
             {
            	 String versionSplit = htmlCode.substring(versionSplitBegin, versionSplitEnd).trim();

                 int versionTmpFlag = versionSplit.indexOf("&nbsp;");
                 if (versionTmpFlag != -1)
                 {
                     int versionBegin = versionSplit.indexOf("<dd>&nbsp;") + ("<dd>&nbsp;").length();
                     int versionEnd = versionSplit.indexOf("</dd>");
                     version = versionSplit.substring(versionBegin, versionEnd).trim();
                 }
                 else
                 {
                     int versionBegin = versionSplit.indexOf("<dd>") + ("<dd>").length();
                     int versionEnd = versionSplit.indexOf("</dd>");
                     version = versionSplit.substring(versionBegin, versionEnd).trim();
                 }
             }
             else
             {
                 // 没有要求这一栏, 也没有来自这一栏
                 versionSplitEnd = htmlCode.indexOf("infos relative-rec log-param-f");
                 String versionSplit = htmlCode.substring(versionSplitBegin, versionSplitEnd).trim();

                 int versionTmpFlag = versionSplit.indexOf("&nbsp;");
                 if (versionTmpFlag != -1)
                 {
                     int versionBegin = versionSplit.indexOf("<dd>&nbsp;") + ("<dd>&nbsp;").length();
                     int versionEnd = versionSplit.indexOf("</dd>");
                     version = versionSplit.substring(versionBegin, versionEnd).trim();
                 }
                 else
                 {
                     int versionBegin = versionSplit.indexOf("<dd>") + ("<dd>").length();
                     int versionEnd = versionSplit.indexOf("</dd>");
                     version = versionSplit.substring(versionBegin, versionEnd).trim();
                 }
             }

         }

         // get company
         String company = "";
         int companySplitBegin = htmlCode.indexOf("<dt>来自</dt>") + ("<dt>来自</dt>").length();
         int companySplitEnd = htmlCode.indexOf("</dl>");
         if (companySplitBegin!=-1 && companySplitEnd!=-1 && companySplitEnd> companySplitBegin)
         {
        	 String companySplit = htmlCode.substring(companySplitBegin, companySplitEnd).trim();
             int companyBegin = companySplit.indexOf("itemprop=\"name\">") + ("itemprop=\"name\">").length();
             int companyEnd = companySplit.indexOf("</span>");
             if (companyBegin!=-1 && companyEnd!=-1 && companyEnd>companyBegin)
             {
                 company = companySplit.substring(companyBegin, companyEnd).trim();
             }
             
         }            

         Apk appInfo = new Apk(apkName, pkgName, version, apkSize, category, company, installCount, isSoft, downloadUrl, "Wandoujia");
         return appInfo;
     } // end getAppFromWandoujia method
	 
	 public static List<Apk> getHighRankApps(String SoftOrGame) {
		List<Apk> highRankApps = new ArrayList<Apk>();
		List<List<String>> rows = ExcelUtil.readXlsxExcel("./" + "HighRank"
				+ SoftOrGame + ".xlsx");
		for (List<String> list : rows) {
			String apkName = list.get(0).toString();
			if(!StringUtils.isEmptyStr(apkName))
			{
				String pkgName = list.get(1).toString();
				String category = list.get(2).toString();
				Apk appInfo = new Apk(apkName, pkgName, category);
				highRankApps.add(appInfo);
			}
			
		}// end for
		return highRankApps;
     }
	
	 public static String parseRankFromInt(int rank) {        
		 String ranking = "";
         if (rank < 10)
         {
             ranking = "000" + rank;
         }
         else if (rank < 100)
         {
             ranking = "00" + rank;
         }
         else if (rank < 1000)
         {
             ranking = "0" + rank;
         }
         else
         {
             ranking = rank + "";
         }
         return ranking;
     }
	 
}
