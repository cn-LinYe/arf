package com.arf.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.WxAccessTokenDao;
import com.arf.core.entity.WxAccessToken;
import com.arf.core.service.WxAccessTokenService;

@Service("wxAccessTokenService")
public class WxAccessTokenServiceImpl extends BaseServiceImpl<WxAccessToken, Long> implements WxAccessTokenService {

	@Resource(name = "wxAccessTokenDao")
	private WxAccessTokenDao wxAccessTokenDao;

	@Override
	protected BaseDao<WxAccessToken, Long> getBaseDao() {
		return wxAccessTokenDao;
	}

	@Override
	public WxAccessToken findByClassifier(String classifier) {
		return wxAccessTokenDao.findByClassifier(classifier);
	}

}
