package com.arf.axdshopkeeper.shopkeeperrecommend.dao;

import com.arf.axdshopkeeper.shopkeeperrecommend.entity.CofUserFavorites;
import com.arf.core.dao.BaseDao;

public interface ICofUserFavoritesDao extends BaseDao<CofUserFavorites,Long>{

	public CofUserFavorites findByType(String userName,Long recommendId,CofUserFavorites.Type type);
	
	public Integer countByType(Long recommendId,CofUserFavorites.Type type);
}
