package prc.apk.download.action;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

public final class SingleTask implements Runnable {
	private String mDownLoadLink, mFileName;
	private boolean needProxy;
	
	public SingleTask(String downLoadLink, String fileName, boolean needProxy) {
		this.mDownLoadLink = downLoadLink;
		this.mFileName = fileName;
		this.needProxy = needProxy;
	}
	
	@Override
	public void run() {
		download();
	}
	
	private void download() {
		HttpURLConnection connection = null;
		try {
			if(needProxy) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy-shz.intel.com", 911));  
				connection = (HttpURLConnection) new URL(mDownLoadLink).openConnection(proxy);
			} else {
				connection = (HttpURLConnection) new URL(mDownLoadLink).openConnection();
			}
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; " +
	                "Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) " +
	                "Chrome/58.0.3029.110 Safari/537.36");
			// connection.setRequestProperty("Range", "bytes=0");
			connection.connect();
			writeFile(new BufferedInputStream(connection.getInputStream()), mFileName);
		} catch(SocketTimeoutException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch(UnknownHostException e) {
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * wirte2local disk
	 * @param inputStream
	 */
	private void writeFile(BufferedInputStream bufferedInputStream,
			String filename) {
		File destfileFile = new File(filename);
		if (destfileFile.exists()) {
			destfileFile.delete();
		}
		if (!destfileFile.getParentFile().exists()) {
			destfileFile.getParentFile().mkdir();
		}
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(destfileFile);
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = bufferedInputStream.read(b, 0, b.length)) != -1) {
				fileOutputStream.write(b, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fileOutputStream) {
					fileOutputStream.flush();
					fileOutputStream.close();
				}
				if (null != bufferedInputStream)
					bufferedInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
