package com.arf.axdshopkeeper.service;

import com.arf.axdshopkeeper.dto.ShopkeeperCommunityDto;
import com.arf.axdshopkeeper.entity.ShopkeeperCommunity;
import com.arf.base.PageResult;
import com.arf.core.service.BaseService;

public interface IShopkeeperCommunityService extends
		BaseService<ShopkeeperCommunity, Long> {
	ShopkeeperCommunity findByNumber(String communityNumber);

	PageResult<ShopkeeperCommunity> findByCondition(Integer pageSize,
			Integer pageNo, String cityNo, ShopkeeperCommunityDto.OrderBy orderBy);
}
