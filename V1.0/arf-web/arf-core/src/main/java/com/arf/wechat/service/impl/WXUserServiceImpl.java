package com.arf.wechat.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.wechat.dao.WXUserDao;
import com.arf.wechat.entity.WXUser;
import com.arf.wechat.service.WXUserService;

@Service("wxUserServiceImpl")
public class WXUserServiceImpl extends BaseServiceImpl<WXUser, Long> implements WXUserService {

	@Resource(name = "wxUserDaoImpl")
	private WXUserDao wxUserDaoImpl;
	
	@Override
	protected BaseDao<WXUser, Long> getBaseDao() {
		return wxUserDaoImpl;
	}

	@Override
	public WXUser findByOpenId(String openId) {
		return this.wxUserDaoImpl.findByOpenId(openId);
	}

	@Override
	public WXUser findByUnionId(String openId) {
		return this.wxUserDaoImpl.findByUnionId(openId);
	}

	

}
