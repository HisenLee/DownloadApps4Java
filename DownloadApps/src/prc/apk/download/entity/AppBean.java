package prc.apk.download.entity;
/**
 * 供下载时候使用
 */
public class AppBean {

	private String appName; 
	private String downloadUrl;
	
	public AppBean(String appName, String downloadUrl) {
		super();
		this.appName = appName;
		this.downloadUrl = downloadUrl;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	@Override
	public String toString() {
		return "AppBean [appName=" + appName + ", downloadUrl=" + downloadUrl
				+ "]";
	}
	
}
