package com.bslee.net;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



import com.bslee.businesses.BusinessesAction;
import com.bslee.businesses.BusinessesRequestParam;
import com.bslee.businesses.BusinessesResponseBean;
import com.bslee.util.AbAppUtil;

public class AsyncTaskManager {
	private ExecutorService mPool;
	private static AsyncTaskManager imageDownload;
	private BusinessesAction userAction=null;   
	private AsyncTaskManager(int nThreads) {
		mPool = Executors.newFixedThreadPool(5);
		userAction=new BusinessesAction();
	}
	/**
	 * ������ͼƬ������.
	 *
	 * @return single instance of AbImageDownloadPool
	 */
    public static AsyncTaskManager getInstance() { 
        
		if (imageDownload == null) { 
        	//�õ�cpu������Ŀ
        	int nThreads = AbAppUtil.getNumCores();
        	//ͬʱִ�е��߳���Ŀ
        	imageDownload = new AsyncTaskManager(nThreads*5); 
        } 
        return imageDownload;
    } 
    
	public void getVisitor(BusinessesRequestParam param,
			RequestListener<BusinessesResponseBean> requestListener) {
		userAction.asyncGetVisitor(mPool, param, requestListener);
	}
}
