package prc.apk.download.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;



public final class Config {
	
	public static String BASIC_PATH = ""; // Local Path
	
	// Select Platform Store(Selected:1 Unselected:0) --- BAIDU
    public static int BAIDU_SELECTED_SOFT;
    public static int BAIDU_SOFT_TOP;
    public static int BAIDU_SELECTED_GAME;
    public static int BAIDU_GAME_TOP;
    // Select Platform Store(Selected:1 Unselected:0) --- TENCENT
    public static int TENCENT_SELECTED_SOFT;
    public static int TENCENT_SOFT_TOP;
    public static int TENCENT_SELECTED_GAME;
    public static int TENCENT_GAME_TOP;
    // Select Platform Store(Selected:1 Unselected:0) --- QIHOO360
    public static int QIHOO360_SELECTED_SOFT;
    public static int QIHOO360_SOFT_TOP;
    public static int QIHOO360_SELECTED_GAME;
    public static int QIHOO360_GAME_TOP;
    // Select Platform Store(Selected:1 Unselected:0) --- WANDOUJIA
    public static int WANDOUJIA_SELECTED_SOFT;
    public static int WANDOUJIA_SOFT_TOP;
    public static int WANDOUJIA_SELECTED_GAME;
    public static int WANDOUJIA_GAME_TOP;    
    
	public void loadConfig() {
		List<String> lines = new ArrayList<String>();
		
		String settingTxt = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("./Setting.txt"));
			while ((settingTxt = reader.readLine()) != null) {
				lines.add(settingTxt);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (lines != null)
        {
            for (String s : lines)
            {
                if (s != null)
                {
                    if (s.length() <= 0 || s.startsWith("#"))
                    {
                        continue;
                    }
                    else
                    {
                        String[] keyValuePair = s.trim().split("=");
                        if (keyValuePair.length <= 1)
                        {
                            Log.info("Config item invalid: " + s);
                            continue;
                        }
                        else if (keyValuePair.length == 2)
                        {
                            try
                            {
                                String key = keyValuePair[0];
                                String value = keyValuePair[1];
                                if (value != null && value.length() > 0)
                                {
                                    applyConfigFromKeyValuePair(key, value);
                                }
                            }
                            catch (Exception ex)
                            {
                                Log.info("Config invalid, Line: " + s);
                            }
                        }

                    }
                }
            }
        }
	}
	
	 private static void applyConfigFromKeyValuePair(String k, String v)
     {
         Log.info("Configration " + k + "=" + v);
         switch (k)
         {
             case "OUT_PUT_DIR":
            	 File file = new File(v);
                 if (!file.exists())
                 {
                     FileUtils.createDir(v);
                 }
                 BASIC_PATH = v;
                 if (!BASIC_PATH.endsWith("\\"))
                 {
                	 BASIC_PATH = BASIC_PATH + "\\";
                 }
                 break;
             case "BAIDU_SELECTED_SOFT":
                 BAIDU_SELECTED_SOFT = Integer.parseInt(v);
                 break;
             case "BAIDU_SOFT_TOP":
                 BAIDU_SOFT_TOP = Integer.parseInt(v);
                 break;
             case "BAIDU_SELECTED_GAME":
                 BAIDU_SELECTED_GAME = Integer.parseInt(v);
                 break;
             case "BAIDU_GAME_TOP":
                 BAIDU_GAME_TOP = Integer.parseInt(v);
                 break;

             case "TENCENT_SELECTED_SOFT":
                 TENCENT_SELECTED_SOFT = Integer.parseInt(v);
                 break;
             case "TENCENT_SOFT_TOP":
                 TENCENT_SOFT_TOP = Integer.parseInt(v);
                 break;
             case "TENCENT_SELECTED_GAME":
                 TENCENT_SELECTED_GAME = Integer.parseInt(v);
                 break;
             case "TENCENT_GAME_TOP":
                 TENCENT_GAME_TOP = Integer.parseInt(v);
                 break;

             case "QIHOO360_SELECTED_SOFT":
                 QIHOO360_SELECTED_SOFT = Integer.parseInt(v);
                 break;
             case "QIHOO360_SOFT_TOP":
                 QIHOO360_SOFT_TOP = Integer.parseInt(v);
                 break;
             case "QIHOO360_SELECTED_GAME":
                 QIHOO360_SELECTED_GAME = Integer.parseInt(v);
                 break;
             case "QIHOO360_GAME_TOP":
                 QIHOO360_GAME_TOP = Integer.parseInt(v);
                 break;

             case "WANDOUJIA_SELECTED_SOFT":
                 WANDOUJIA_SELECTED_SOFT = Integer.parseInt(v);
                 break;
             case "WANDOUJIA_SOFT_TOP":
                 WANDOUJIA_SOFT_TOP = Integer.parseInt(v);
                 break;
             case "WANDOUJIA_SELECTED_GAME":
                 WANDOUJIA_SELECTED_GAME = Integer.parseInt(v);
                 break;
             case "WANDOUJIA_GAME_TOP":
                 WANDOUJIA_GAME_TOP = Integer.parseInt(v);
                 break;
         }
     } // end method applyConfigFromKeyValuePair
    
}
