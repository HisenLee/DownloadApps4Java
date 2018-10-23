package prc.apk.download.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public final class RequestUtils {
	public static final int NET_UNAVAILABLE = -1;
	public static final int NET_AVAILABLE_WITHPROXY = 0;
	public static final int NET_AVAILABLE_WITHOUTPROXY = 1;
	/**
	 * Net available
	 * @return
	 */
	public static int checkNetwork() {
		int netType = NET_UNAVAILABLE; //default: net do not work
		boolean withoutProxy = withoutProxyConn();
		boolean withProxy = withProxyConn();
		if(withoutProxy) {
			netType = NET_AVAILABLE_WITHOUTPROXY;
		} 
		if(withProxy) {
			netType = NET_AVAILABLE_WITHPROXY;
		}
		return netType;
	}
	static boolean withoutProxyConn() {
		boolean flag = false;
		try {
			URL url = new URL("https://www.baidu.com");
			URLConnection URLconnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
			httpConnection.setConnectTimeout(5000);
			httpConnection.setReadTimeout(5000);
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		} 
		return flag;
	}
	
	static boolean withProxyConn() {
		boolean flag = false;
		try {
			URL url = new URL("https://www.baidu.com");
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy-shz.intel.com", 911));  
			URLConnection urlConnection = url.openConnection(proxy);  
			
			HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
			httpConnection.setConnectTimeout(5000);
			httpConnection.setReadTimeout(5000);
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		} 
		return flag;
	}

	/**
	 * UrlEncode
	 * @param url
	 * @return
	 */
	public static final String urlEncode(String url) {
		String encodeStr = "";
		try {
			encodeStr = java.net.URLEncoder.encode(url,   "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}   
		return encodeStr;
	}
	
	/**
	 * @throws IOException 
	 * GetHtmlByAddress
	 * 
	 * @param address
	 * @return
	 * @throws  
	 */
	public static final String getHtml(String address, boolean isNeedProxy) throws IOException {
		StringBuilder sb = new StringBuilder();
		URL url = new URL(address);
		URLConnection urlConnection = null;
		if(isNeedProxy) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy-shz.intel.com", 911));  
			urlConnection = url.openConnection(proxy);  
		} else {
			urlConnection = url.openConnection();
		}
		HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
		httpConnection.setConnectTimeout(5000);
		httpConnection.setReadTimeout(5000);
		httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
		int responseCode = httpConnection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			InputStream in = httpConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(in, "utf-8");
			BufferedReader bufr = new BufferedReader(isr);
			String str;
			while ((str = bufr.readLine()) != null) {
				sb.append(str);
			}
			bufr.close();
		} 
		return sb.toString();
	}
	
	
}
