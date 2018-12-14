package com.arf.thirdlogin.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.thirdlogin.dao.ThirdPartyAccountDao;
import com.arf.thirdlogin.entity.ThirdPartyAccount;
import com.arf.thirdlogin.service.ThirdPartyAccountService;

@Service("thirdPartyAccountService")
public class ThirdPartyAccountServiceImpl extends BaseServiceImpl<ThirdPartyAccount, Long> implements ThirdPartyAccountService{

	@Resource(name="thirdPartyAccountDao")
	ThirdPartyAccountDao thirdPartyAccountDao;
	
	@Override
	protected BaseDao<ThirdPartyAccount, Long> getBaseDao() {
		return thirdPartyAccountDao;
	}

	@Override
	public ThirdPartyAccount findByOpenidOrUid(String qqOpenid, String wxOpenid, String uid, String userName) {
		return thirdPartyAccountDao.findByOpenidOrUid(qqOpenid, wxOpenid, uid, userName);
	}
}
