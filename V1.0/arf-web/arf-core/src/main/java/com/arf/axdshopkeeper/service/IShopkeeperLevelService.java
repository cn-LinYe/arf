package com.arf.axdshopkeeper.service;

import com.arf.axdshopkeeper.entity.ShopkeeperLevel;
import com.arf.core.service.BaseService;

public interface IShopkeeperLevelService extends
		BaseService<ShopkeeperLevel, Long> {

	ShopkeeperLevel findByLevelIndex(Integer levelIndex);
}
