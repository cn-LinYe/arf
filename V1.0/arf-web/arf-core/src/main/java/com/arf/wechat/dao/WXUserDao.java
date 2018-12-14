package com.arf.wechat.dao;

import com.arf.core.dao.BaseDao;
import com.arf.wechat.entity.WXUser;

public interface WXUserDao extends BaseDao<WXUser, Long> {
	WXUser findByOpenId(String openId);
	WXUser findByUnionId(String openId);
}
