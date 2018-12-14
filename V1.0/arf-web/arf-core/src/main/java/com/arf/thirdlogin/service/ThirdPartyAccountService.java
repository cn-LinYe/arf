package com.arf.thirdlogin.service;

import com.arf.core.service.BaseService;
import com.arf.thirdlogin.entity.ThirdPartyAccount;

public interface ThirdPartyAccountService extends BaseService<ThirdPartyAccount, Long>{

	/**
     * 通过qq或微信openid或微博uid查找用户
     * @param qqsopenid
     * @param uid
     * @return
     */
	ThirdPartyAccount findByOpenidOrUid(String qqOpenid,String wxOpenid,String uid,String userName);
}
