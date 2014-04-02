package com.bslee.businesses;

import java.util.concurrent.ExecutorService;

import com.bslee.net.HttpUtils;
import com.bslee.net.RequestListener;
import com.sport.region.AppKeyAndSecret;
import com.sport.region.DemoApiTool;

public class BusinessesAction {

	public void asyncGetVisitor(ExecutorService mPool,
			final BusinessesRequestParam param,
			final RequestListener<BusinessesResponseBean> listener) {
		mPool.execute(new Runnable() {

			public void run() {
				listener.onStart();

				BusinessesResponseBean bean = getBusinesses(param);
				listener.onComplete(bean);
			}
		});
	}

	private BusinessesResponseBean getBusinesses(BusinessesRequestParam param) {

		String response =null;

		try {
			response = DemoApiTool.requestApi(
					AppKeyAndSecret.find_businesses_by_region,
					AppKeyAndSecret.APP_KEY, AppKeyAndSecret.APP_SECRET,
					param.getParams());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return new BusinessesResponseBean(response);
	}
}
