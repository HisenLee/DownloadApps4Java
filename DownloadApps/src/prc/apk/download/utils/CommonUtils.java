package prc.apk.download.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import prc.apk.download.entity.Apk;

public class CommonUtils {
	
	public static void generateExcel(String platform, List<Apk> apks) {
		int realNumber = -1;
		if(platform.equals("BaiduSoft")) {
			realNumber = Config.BAIDU_SOFT_TOP;
		} else if(platform.equals("BaiduGame")) {
			realNumber = Config.BAIDU_GAME_TOP;
		} else if(platform.equals("QihooSoft")) {
			realNumber = Config.QIHOO360_SOFT_TOP;
		} else if(platform.equals("QihooGame")) {
			realNumber = Config.QIHOO360_GAME_TOP;
		} else if(platform.equals("TencentSoft")) {
			realNumber = Config.TENCENT_SOFT_TOP;
		} else if(platform.equals("TencentGame")) {
			realNumber = Config.TENCENT_GAME_TOP;
		} else if(platform.equals("WandoujiaSoft")) {
			realNumber = Config.WANDOUJIA_SOFT_TOP;
		} else if(platform.equals("WandoujiaGame")) {
			realNumber = Config.WANDOUJIA_GAME_TOP;
		}
		// generateAll
		ExcelUtil.generateExcel(Config.BASIC_PATH + "\\"+platform+"\\"+platform+".xlsx", platform, apks);
		// generateTop(多下载20个)
		List<Apk> topApps = apks.subList(0, realNumber + 20); 
		ExcelUtil.generateExcel(Config.BASIC_PATH + "\\"+platform+"\\Top" + realNumber + ".xlsx", "Top" + realNumber , topApps);
	}
	
	
	public static void getRealExcel(String platform) {
		int realNumber = -1;
		if(platform.equals("BaiduSoft")) {
			realNumber = Config.BAIDU_SOFT_TOP;
		} else if(platform.equals("BaiduGame")) {
			realNumber = Config.BAIDU_GAME_TOP;
		} else if(platform.equals("QihooSoft")) {
			realNumber = Config.QIHOO360_SOFT_TOP;
		} else if(platform.equals("QihooGame")) {
			realNumber = Config.QIHOO360_GAME_TOP;
		} else if(platform.equals("TencentSoft")) {
			realNumber = Config.TENCENT_SOFT_TOP;
		} else if(platform.equals("TencentGame")) {
			realNumber = Config.TENCENT_GAME_TOP;
		} else if(platform.equals("WandoujiaSoft")) {
			realNumber = Config.WANDOUJIA_SOFT_TOP;
		} else if(platform.equals("WandoujiaGame")) {
			realNumber = Config.WANDOUJIA_GAME_TOP;
		}
			
		// 已经下载完毕的文件
		List<Apk> appInfosFromFolder = new ArrayList<Apk>();
		File dir = new File(Config.BASIC_PATH + "\\" + platform + "\\");
		File[] files = dir.listFiles();
		for(int i=0; i<files.length; i++) {
			File itemFile = files[i];
			String fileName = itemFile.getName();
			if(FileUtils.getFileExtension(fileName).equals("apk")) {
				String[] tmpArr = fileName.split("_");
				String rankStr = tmpArr[0];
                int rank = Integer.parseInt(rankStr);
                String apkName = tmpArr[1];
                int pkgNameBegin = fileName.indexOf(apkName + "_") + (apkName + "_").length();
                int pkgNameEnd = fileName.indexOf("_.apk");
                String pkgName = fileName.substring(pkgNameBegin, pkgNameEnd).trim();

                Apk appInfo = new Apk(rank, apkName, pkgName);
                appInfosFromFolder.add(appInfo);
			}
		}
		// Excel中的数据
		List<Apk> appInfosFromExcel = ExcelUtil.getAppsFromExcel(Config.BASIC_PATH + "\\"+platform+"\\Top" + realNumber+".xlsx");
		List<Apk> appInfosResult = new ArrayList<Apk>();
		for (Apk itemFromExcel : appInfosFromExcel) {
			if (appInfosFromFolder.contains(itemFromExcel)) { // 
				if (appInfosResult.size() < realNumber) {
					appInfosResult.add(itemFromExcel);
				}
			}
		}
		// 删除旧数据的表格
		for(int i=0; i<files.length; i++) {
			File itemFile = files[i];
			String fileName = itemFile.getName();
			if(FileUtils.getFileExtension(fileName).equals("xlsx")) {
				FileUtils.deleteFile(itemFile.getAbsolutePath());
			}
		}
		ExcelUtil.generateExcel(Config.BASIC_PATH + "\\"+platform+"\\Top" + realNumber +".xlsx", "Top" + realNumber , appInfosResult);
	}
	
	public static void reNameFile(String platform) {
		int realNumber = -1;
		if(platform.equals("BaiduSoft")) {
			realNumber = Config.BAIDU_SOFT_TOP;
		} else if(platform.equals("BaiduGame")) {
			realNumber = Config.BAIDU_GAME_TOP;
		} else if(platform.equals("QihooSoft")) {
			realNumber = Config.QIHOO360_SOFT_TOP;
		} else if(platform.equals("QihooGame")) {
			realNumber = Config.QIHOO360_GAME_TOP;
		} else if(platform.equals("TencentSoft")) {
			realNumber = Config.TENCENT_SOFT_TOP;
		} else if(platform.equals("TencentGame")) {
			realNumber = Config.TENCENT_GAME_TOP;
		} else if(platform.equals("WandoujiaSoft")) {
			realNumber = Config.WANDOUJIA_SOFT_TOP;
		} else if(platform.equals("WandoujiaGame")) {
			realNumber = Config.WANDOUJIA_GAME_TOP;
		}
		File dir = new File(Config.BASIC_PATH + "\\"+platform+"\\");
		File[] files = dir.listFiles();
		
		List<Apk> appInfosFromExcel = ExcelUtil.getAppsFromExcel(Config.BASIC_PATH + "\\"+platform+"\\Top" + realNumber+".xlsx");
		for (Apk appItemFromExcel : appInfosFromExcel) {
            int rankFromExcel = appItemFromExcel.getRank();
            String rankStrFromExcel = StringUtils.parseRankFromInt(rankFromExcel);
            String pkgNameFromExcel = appItemFromExcel.getPkgName();
            for(int i=0; i<files.length; i++) {
    			File itemFile = files[i];
    			String fileName = itemFile.getName();
    			if(FileUtils.getFileExtension(fileName).equals("apk")) {
    				String[] tmpArr = fileName.split("_");
                    String apkNameFromFolder = tmpArr[1];
                    int pkgNameBegin = fileName.indexOf(apkNameFromFolder + "_") + (apkNameFromFolder + "_").length();
                    int pkgNameEnd = fileName.indexOf("_.apk");
                    String pkgNameFromFolder = fileName.substring(pkgNameBegin, pkgNameEnd).trim();

                    if (pkgNameFromExcel.equals(pkgNameFromFolder)) {
                    	String newName = itemFile.getParent() + "\\" + rankStrFromExcel + "_" + apkNameFromFolder + "_" + pkgNameFromFolder + "_.apk";
                    	itemFile.renameTo(new File(newName));
                    	break;
                    }
    			}
    		}
		} // end outter for
	}
	
	public static void delUnusedFiles(String platform) {
		int realNumber = -1;
		if(platform.equals("BaiduSoft")) {
			realNumber = Config.BAIDU_SOFT_TOP;
		} else if(platform.equals("BaiduGame")) {
			realNumber = Config.BAIDU_GAME_TOP;
		} else if(platform.equals("QihooSoft")) {
			realNumber = Config.QIHOO360_SOFT_TOP;
		} else if(platform.equals("QihooGame")) {
			realNumber = Config.QIHOO360_GAME_TOP;
		} else if(platform.equals("TencentSoft")) {
			realNumber = Config.TENCENT_SOFT_TOP;
		} else if(platform.equals("TencentGame")) {
			realNumber = Config.TENCENT_GAME_TOP;
		} else if(platform.equals("WandoujiaSoft")) {
			realNumber = Config.WANDOUJIA_SOFT_TOP;
		} else if(platform.equals("WandoujiaGame")) {
			realNumber = Config.WANDOUJIA_GAME_TOP;
		}
		List<Apk> appInfosFromExcel = ExcelUtil.getAppsFromExcel(Config.BASIC_PATH + "\\" + platform + "\\Top" + realNumber +".xlsx");
		File dir = new File(Config.BASIC_PATH + "\\"+platform+"\\");
		File[] files = dir.listFiles();
		for(int i=0; i<files.length; i++) {
			File itemFile = files[i];
			String fileName = itemFile.getName();
			if(FileUtils.getFileExtension(fileName).equals("apk")) {
				String[] tmpArr = fileName.split("_");
				String rankStr = tmpArr[0];
                int rank = Integer.parseInt(rankStr);
                String apkNameFromFolder = tmpArr[1];
                int pkgNameBegin = fileName.indexOf(apkNameFromFolder + "_") + (apkNameFromFolder + "_").length();
                int pkgNameEnd = fileName.indexOf("_.apk");
                String pkgNameFromFolder = fileName.substring(pkgNameBegin, pkgNameEnd).trim();
                Apk appInfo = new Apk(rank, apkNameFromFolder, pkgNameFromFolder);
                if (!appInfosFromExcel.contains(appInfo))
                {
                	FileUtils.deleteFile(itemFile.getAbsolutePath());
                }
			}
		}
		
		
	}
	

}
