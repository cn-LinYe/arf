package com.arf.thirdlogin.dao;

import com.arf.core.dao.BaseDao;
import com.arf.thirdlogin.entity.ThirdPartyAccount;

public interface ThirdPartyAccountDao extends BaseDao<ThirdPartyAccount, Long>{

	 /**
     * 通过qq或微信openid或微博uid查找用户
     * @param openid
     * @param uid
     * @return
     */
	ThirdPartyAccount findByOpenidOrUid(String qqOpenid,String wxOpenid,String uid,String userName);
}
