package com.arf.axdshopkeeper.shopkeeperrecommend.service;

import com.arf.axdshopkeeper.shopkeeperrecommend.entity.CofUserFavorites;
import com.arf.core.service.BaseService;

public interface ICofUserFavoritesService extends BaseService<CofUserFavorites, Long> {

	public CofUserFavorites findByType(String userName,Long recommendId,CofUserFavorites.Type type);
	
	public Integer countByType(Long recommendId,CofUserFavorites.Type type);
}
