package com.arf.wechat.service;

import com.arf.core.service.BaseService;
import com.arf.wechat.entity.WXAXDUser;

public interface IWXAXDUserService extends BaseService<WXAXDUser, Long> {

	WXAXDUser findByOpenId(String userIdentify);

}
