package prc.apk.download.action;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import prc.apk.download.entity.AppBean;

public final class DownloadAction {

	private boolean needProxy;
	private List<AppBean> appDatas;
	private IDownloadCallBack downloadCallBack;
	
	public interface IDownloadCallBack {
		public void downloadActionProcess(String process, boolean isFinish);
	}
	
	public DownloadAction(List<AppBean> appDatas, boolean needProxy, IDownloadCallBack downloadCallBack) {
		this.appDatas = appDatas;
		this.needProxy = needProxy;
		this.downloadCallBack = downloadCallBack;
	}
	
	public void download() {
		// download
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		for (AppBean imageBean : appDatas) {
			SingleTask singleTask = new SingleTask(imageBean.getDownloadUrl(), 
					imageBean.getAppName(),
					needProxy);
			executorService.submit(singleTask);
		}
		executorService.shutdown();
		while (true) {
			downloadCallBack.downloadActionProcess("download apks...", false);
			if (executorService.isTerminated()) {
				downloadCallBack.downloadActionProcess("download apk finished...", true);
				break;
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} // end while
		
	}
	
}
