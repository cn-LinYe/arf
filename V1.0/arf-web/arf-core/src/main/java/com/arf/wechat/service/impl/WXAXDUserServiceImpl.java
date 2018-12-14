package com.arf.wechat.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.wechat.dao.IWXAXDUserDao;
import com.arf.wechat.entity.WXAXDUser;
import com.arf.wechat.service.IWXAXDUserService;

@Service("wxAXDUserServiceImpl")
public class WXAXDUserServiceImpl extends BaseServiceImpl<WXAXDUser, Long>
		implements IWXAXDUserService {

	@Resource(name = "wxAXDUserDaoImpl")
	private IWXAXDUserDao wxAXDUserDaoImpl;
	
	@Override
	protected BaseDao<WXAXDUser, Long> getBaseDao() {
		return wxAXDUserDaoImpl;
	}

	@Override
	public WXAXDUser findByOpenId(String userIdentify) {
		return wxAXDUserDaoImpl.findByOpenId(userIdentify);
	}

	
	
}
