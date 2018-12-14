package com.arf.carbright.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.carbright.dao.IBusinessToCommunityDao;
import com.arf.carbright.entity.BusinessToCommunity;
import com.arf.carbright.service.IPbusinessToCommunityService;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.redis.CacheNameDefinition;

@Service("pbusinessToCommunityServiceImpl")
public class PbusinessToCommunityServiceImpl extends BaseServiceImpl<BusinessToCommunity, Long> implements IPbusinessToCommunityService {

	@Resource(name = "businessToCommunityDaoImpl")
	private IBusinessToCommunityDao iBusinessToCommunityDao;
	@Resource(name = "redisService")
	private RedisService redisService;
	
	
	@Override
	protected BaseDao<BusinessToCommunity, Long> getBaseDao() {
		return iBusinessToCommunityDao;
	}

	@Override
	public Map<String,Object> findByBusinessNum(String businessNum) {
		return iBusinessToCommunityDao.findByBusinessNum(businessNum);
	}

	@Override
	public BusinessToCommunity findRedisByBusinessId(Long id) {
		BusinessToCommunity businessToCommunity=redisService.hGet(String.format(CacheNameDefinition.RedisBusinessToCommunityKey, id), String.valueOf(id),BusinessToCommunity.class);
		if (businessToCommunity!=null) {
			return businessToCommunity;
		}
		BusinessToCommunity entity=findByBusinessId(id);
		if (entity==null) {
			return null;
		}
		redisService.hset(String.format(CacheNameDefinition.RedisBusinessToCommunityKey, id), String.valueOf(id), entity);
		redisService.setExpiration(String.format(CacheNameDefinition.RedisBusinessToCommunityKey, id), CacheNameDefinition.Default_Expiration);
		return entity;
	}

	@Override
	public BusinessToCommunity findByBusinessId(Long id) {
		return iBusinessToCommunityDao.findByBusinessId(id);
	}
}
