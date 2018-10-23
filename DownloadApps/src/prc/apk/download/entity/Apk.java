package prc.apk.download.entity;


public class Apk implements Comparable<Apk> {

	private int rank;
	
	private String apkName;
	private String pkgName;
	private String version;
	private String apkSize;
	private String category;
	private String company;
	private long installCount;
	private String SoftOrGame;
	private String downloadUrl;
	private String store;

	public Apk() {
		// TODO Auto-generated constructor stub
	}
	
	public Apk(int rank, String apkName, String pkgName, String version, String apkSize,
			String category, String company, long installCount,
			String SoftOrGame, String downloadUrl, String store) {
		super();
		this.rank = rank;
		this.apkName = apkName;
		this.pkgName = pkgName;
		this.version = version;
		this.apkSize = apkSize;
		this.category = category;
		this.company = company;
		this.installCount = installCount;
		this.SoftOrGame = SoftOrGame;
		this.downloadUrl = downloadUrl;
		this.store = store;
	}

	public Apk(String apkName, String pkgName, String version, String apkSize,
			String category, String company, long installCount,
			String SoftOrGame, String downloadUrl, String store) {
		super();
		this.apkName = apkName;
		this.pkgName = pkgName;
		this.version = version;
		this.apkSize = apkSize;
		this.category = category;
		this.company = company;
		this.installCount = installCount;
		this.SoftOrGame = SoftOrGame;
		this.downloadUrl = downloadUrl;
		this.store = store;
	}
	
	public Apk(int rank, String apkName, String pkgName) {
		super();
		this.rank = rank;
		this.apkName = apkName;
		this.pkgName = pkgName;
	}

	public Apk(String apkName, String pkgName, String category) {
		super();
		this.apkName = apkName;
		this.pkgName = pkgName;
		this.category = category;
	}
	
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getApkSize() {
		return apkSize;
	}

	public void setApkSize(String apkSize) {
		this.apkSize = apkSize;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public double getInstallCount() {
		return installCount;
	}

	public void setInstallCount(long installCount) {
		this.installCount = installCount;
	}

	public String getSoftOrGame() {
		return SoftOrGame;
	}

	public void setSoftOrGame(String SoftOrGame) {
		this.SoftOrGame = SoftOrGame;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	@Override
	public String toString() {
		return "Apk [apkName=" + apkName + ", pkgName=" + pkgName
				+ ", version=" + version + ", apkSize=" + apkSize
				+ ", category=" + category + ", company=" + company
				+ ", installCount=" + installCount + ", SoftOrGame="
				+ SoftOrGame + ", downloadUrl=" + downloadUrl + ", store="
				+ store + "]";
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEquals = false;
		Apk other = (Apk)obj;
		if(other.getPkgName().equals(this.getPkgName())){
			isEquals = true;
		} else {
			isEquals = false;
		}
		return isEquals;
	}

	@Override
	public int compareTo(Apk out) {
		
		if(this == null && out == null) {  
		    return 0;  
		}
		if(this == null) {
			return -1;
		}
		if(out == null) {
			return 1;
		}
		if (this == out) {
			return 0;
		} 
		if (this.installCount >= out.installCount) {
			return -1;
		} else {
			return 1;
		}
	}
}
