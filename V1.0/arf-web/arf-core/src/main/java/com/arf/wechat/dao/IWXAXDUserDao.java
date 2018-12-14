package com.arf.wechat.dao;

import com.arf.core.dao.BaseDao;
import com.arf.wechat.entity.WXAXDUser;

public interface IWXAXDUserDao extends BaseDao<WXAXDUser, Long> {

	WXAXDUser findByOpenId(String userIdentify);

}
