package prc.apk.download.view;

import java.util.List;

import prc.apk.download.entity.Apk;
import prc.apk.download.store.BaiduGame;
import prc.apk.download.store.BaiduSoft;
import prc.apk.download.store.QihooGame;
import prc.apk.download.store.QihooSoft;
import prc.apk.download.store.TencentGame;
import prc.apk.download.store.TencentSoft;
import prc.apk.download.store.WandoujiaGame;
import prc.apk.download.store.WandoujiaSoft;
import prc.apk.download.utils.CommonUtils;
import prc.apk.download.utils.Config;
import prc.apk.download.utils.FileUtils;
import prc.apk.download.utils.Log;
import prc.apk.download.utils.RequestUtils;


public class Test {
	
	public static void main(String[] args) {
		// loadConfig
		Config config = new Config();
		config.loadConfig();
		// checkNetwork
		int netType = RequestUtils.checkNetwork();
		boolean isNeedProxy=false; // 当前网路环境是否需要Intel代理,默认不需要
		if(netType==RequestUtils.NET_UNAVAILABLE) {
			// 网络不通
			Log.info("Network is unavailable...");
			return;
		}  
		if(netType==RequestUtils.NET_AVAILABLE_WITHPROXY) {
			// 需要Intel代理
			isNeedProxy = true;
		}
		
	    // BaiduSoft
        int BAIDU_SELECTED_SOFT = Config.BAIDU_SELECTED_SOFT;
        if(BAIDU_SELECTED_SOFT == 1)
        {
        	FileUtils.createDir(Config.BASIC_PATH+"\\BaiduSoft");
        	// get apks
        	BaiduSoft store = new BaiduSoft(isNeedProxy);
        	List<Apk> apks = store.getApkInfo();
        	// generate
        	CommonUtils.generateExcel("BaiduSoft", apks);
        	// download
        	store.download();
        	Log.info("BaiduSoft finished...");
        }
        // BaiduGame
        int BAIDU_SELECTED_GAME = Config.BAIDU_SELECTED_GAME;
        if(BAIDU_SELECTED_GAME == 1)
        {
        	FileUtils.createDir(Config.BASIC_PATH+"\\BaiduGame");
        	// get apks
        	BaiduGame store = new BaiduGame(isNeedProxy);
        	List<Apk> apks = store.getApkInfo();
        	// generate
        	CommonUtils.generateExcel("BaiduGame", apks);
        	// download
        	store.download();
        	Log.info("BaiduGame finished...");
        }
        
        // TencentSoft
        int TENCENT_SELECTED_SOFT = Config.TENCENT_SELECTED_SOFT;
        if(TENCENT_SELECTED_SOFT == 1)
        {
        	FileUtils.createDir(Config.BASIC_PATH+"\\TencentSoft");
        	// get apks
        	TencentSoft store = new TencentSoft(isNeedProxy);
        	List<Apk> apks = store.getApkInfo();
        	// generate
        	CommonUtils.generateExcel("TencentSoft", apks);
        	// download
        	store.download();
        	Log.info("TencentSoft finished...");
        }
        // TencentGame
        int TENCENT_SELECTED_GAME = Config.TENCENT_SELECTED_GAME;
        if(TENCENT_SELECTED_GAME == 1)
        {
        	FileUtils.createDir(Config.BASIC_PATH+"\\TencentGame");
        	// get apks
        	TencentGame store = new TencentGame(isNeedProxy);
        	List<Apk> apks = store.getApkInfo();
        	// generate
        	CommonUtils.generateExcel("TencentGame", apks);
        	// download
        	store.download();
        	Log.info("TencentGame finished...");
        }
        
        // QihooSoft
        int QIHOO360_SELECTED_SOFT = Config.QIHOO360_SELECTED_SOFT;
        if(QIHOO360_SELECTED_SOFT == 1)
        {
        	FileUtils.createDir(Config.BASIC_PATH+"\\QihooSoft");
        	// get apks
        	QihooSoft store = new QihooSoft(isNeedProxy);
        	List<Apk> apks = store.getApkInfo();
        	// generate
        	CommonUtils.generateExcel("QihooSoft", apks);
        	// download
        	store.download();
        	Log.info("QihooSoft finished...");
        }
        // QihooGame
        int QIHOO360_SELECTED_GAME = Config.QIHOO360_SELECTED_GAME;
        if(QIHOO360_SELECTED_GAME == 1)
        {
        	FileUtils.createDir(Config.BASIC_PATH+"\\QihooGame");
        	// get apks
        	QihooGame store = new QihooGame(isNeedProxy);
        	List<Apk> apks = store.getApkInfo();
        	// generate
        	CommonUtils.generateExcel("QihooGame", apks);
        	// download
        	store.download();
        	Log.info("QihooGame finished...");
        }
        
        // WandoujiaSoft
        int WANDOUJIA_SELECTED_SOFT = Config.WANDOUJIA_SELECTED_SOFT;
        if(WANDOUJIA_SELECTED_SOFT == 1)
        {
        	FileUtils.createDir(Config.BASIC_PATH+"\\WandoujiaSoft");
        	// get apks
        	WandoujiaSoft store = new WandoujiaSoft(isNeedProxy);
        	List<Apk> apks = store.getApkInfo();
        	// generate
        	CommonUtils.generateExcel("WandoujiaSoft", apks);
        	// download
        	store.download();
        	Log.info("WandoujiaSoft finished...");
        }
        // WandoujiaGame
        int WANDOUJIA_SELECTED_GAME = Config.WANDOUJIA_SELECTED_GAME;
        if(WANDOUJIA_SELECTED_GAME == 1)
        {
        	FileUtils.createDir(Config.BASIC_PATH+"\\WandoujiaGame");
        	// get apks
        	WandoujiaGame store = new WandoujiaGame(isNeedProxy);
        	List<Apk> apks = store.getApkInfo();
        	// generate
        	CommonUtils.generateExcel("WandoujiaGame", apks);
        	// download
        	store.download();
        	Log.info("WandoujiaGame finished...");
        }
        Log.info("All apks downloaded finished...");
		
	} // end main
	
	
}
