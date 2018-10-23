package prc.apk.download.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {
	/**
	 * getTime
	 * 
	 * @return
	 */
	public static final String getNowTime() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}
	
	/**
	 * getDate
	 * 
	 * @return
	 */
	public static final String getDate() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
		return sdf.format(d);
	}
}
