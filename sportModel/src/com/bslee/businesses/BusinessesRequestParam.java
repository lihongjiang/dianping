package com.bslee.businesses;

import java.util.HashMap;
import java.util.Map;

import com.bslee.net.RequestParam;

public class BusinessesRequestParam extends RequestParam {
	private Map<String, String> mMap = null;

	public BusinessesRequestParam(Map<String, String> paramMap) {
		mMap = new HashMap<String, String>();
		mMap.putAll( paramMap);

	}

	@Override
	public Map<String, String> getParams() {

		return mMap;
	}

}
