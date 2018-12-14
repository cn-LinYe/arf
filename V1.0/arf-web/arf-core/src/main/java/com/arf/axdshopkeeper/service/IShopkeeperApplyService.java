package com.arf.axdshopkeeper.service;

import com.arf.axdshopkeeper.entity.ShopkeeperApply;
import com.arf.core.service.BaseService;

public interface IShopkeeperApplyService extends
		BaseService<ShopkeeperApply, Long> {
	
	ShopkeeperApply findByMobileAndCommunity(String mobile,String communityNumber);
}
