package prc.apk.download.utils;



public final class Log {

	public static final String info(String message) {
		System.out.println(DateUtils.getNowTime()+"--->" + message);
		return DateUtils.getNowTime()+"--->" + message;
	}
}
