package com.bslee.businesses;

import java.util.List;

import com.bslee.net.FastJsonUtil;
import com.bslee.net.NetDataManager;
import com.bslee.net.ResponseBean;

public class BusinessesResponseBean extends ResponseBean {

	public String response = null;
	public boolean success = false;
	public boolean isOver = false;
	public BusinessesResponseBean(String response) {
		super(response);
		this.response = response;
	}

	public void resolve(boolean isAdd) {
		if (response == null) {
			return;
		}
		BusinessesResult br=null;
		try {
			br = FastJsonUtil.getObject(response,
					BusinessesResult.class);
		} catch (Exception e) {
		
			e.printStackTrace();
			return;
		}
		if ("OK".equals(br.status)) {
			success=true;
			if (!isAdd) {
				List<Businesses> result = FastJsonUtil.getListObject(
						br.businesses, Businesses.class);
				if (result.size() > 0) {
					NetDataManager.userresult = result;
				}

			} else {

				List<Businesses> result = FastJsonUtil.getListObject(
						br.businesses, Businesses.class);
				if (result.size() < 40) {
					isOver = true;
				}
				NetDataManager.userresult.addAll(result);
			}
		}

	}
}
