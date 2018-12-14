package com.arf.core.dao;

import com.arf.core.entity.WxAccessToken;

public interface WxAccessTokenDao extends BaseDao<WxAccessToken, Long>{
	
	/**
	 * 通过classifier查找
	 * @param classifier
	 * @return
	 */
	WxAccessToken findByClassifier(String classifier);
}
