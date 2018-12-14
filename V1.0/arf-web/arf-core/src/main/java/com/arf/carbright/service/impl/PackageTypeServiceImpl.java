package com.arf.carbright.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arf.carbright.dao.PackageTypeDao;
import com.arf.carbright.entity.PackageType;
import com.arf.carbright.entity.PackageType.IsEnabled;
import com.arf.carbright.service.PackageTypeService;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("packageTypeService")
public class PackageTypeServiceImpl extends BaseServiceImpl<PackageType, Long>
		implements PackageTypeService{

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "packageTypeDao")
	private PackageTypeDao packageTypeDao;
	
	@Resource(name = "redisService")
	private RedisService redisService;

	@Override
	protected BaseDao<PackageType, Long> getBaseDao() {
		return packageTypeDao;
	}
	
	@Override
	public List<PackageType> findByBusinessId(Long businessId,PackageType.IsEnabled enabled){
		return packageTypeDao.findByBusinessId(businessId, enabled);
	}
	
	@Override
	public List<PackageType> findByBusinessIdAndType(Long businessId,PackageType.IsEnabled enabled,String businessServiceType){
		return packageTypeDao.findByBusinessIdAndType(businessId, enabled, businessServiceType);
	}

	@Override
	public PackageType findByStartTimeNum(Long startTimeNum) {
		return packageTypeDao.findByStartTimeNum(startTimeNum);
	}

	@Override
	public PackageType getPackageType(Integer packTypeId) {
		return packageTypeDao.getPackageType(packTypeId);
	}
}
