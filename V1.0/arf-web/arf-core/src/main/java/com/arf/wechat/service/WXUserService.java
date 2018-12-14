package com.arf.wechat.service;

import com.arf.core.service.BaseService;
import com.arf.wechat.entity.WXUser;

public interface WXUserService extends BaseService<WXUser, Long> {
	WXUser findByOpenId(String openId);
	WXUser findByUnionId(String openId);
}
