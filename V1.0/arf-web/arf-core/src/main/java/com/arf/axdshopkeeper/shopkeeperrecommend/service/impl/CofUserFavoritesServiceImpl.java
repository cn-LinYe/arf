package com.arf.axdshopkeeper.shopkeeperrecommend.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.axdshopkeeper.shopkeeperrecommend.dao.ICofUserFavoritesDao;
import com.arf.axdshopkeeper.shopkeeperrecommend.entity.CofUserFavorites;
import com.arf.axdshopkeeper.shopkeeperrecommend.service.ICofUserFavoritesService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("cofUserFavoritesServiceImpl")
public class CofUserFavoritesServiceImpl extends BaseServiceImpl<CofUserFavorites, Long>
		implements ICofUserFavoritesService {

	@Resource(name = "cofUserFavoritesDaoImpl")
	private ICofUserFavoritesDao cofUserFavoritesDaoImpl;
	
	@Override
	protected BaseDao<CofUserFavorites, Long> getBaseDao() {
		return cofUserFavoritesDaoImpl;
	}
	
	@Override
	public CofUserFavorites findByType(String userName,Long recommendId,CofUserFavorites.Type type){
		return cofUserFavoritesDaoImpl.findByType(userName, recommendId, type);
	}
	
	@Override
	public Integer countByType(Long recommendId,CofUserFavorites.Type type){
		return cofUserFavoritesDaoImpl.countByType(recommendId,type);
	}
}
