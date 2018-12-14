package com.arf.core.service;

import com.arf.core.entity.WxAccessToken;


public interface WxAccessTokenService extends BaseService<WxAccessToken, Long>{
	/**
	 * 通过classifier查找
	 * @param classifier
	 * @return
	 */
	WxAccessToken findByClassifier(String classifier);
}
