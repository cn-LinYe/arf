package com.arf.eparking.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.eparking.dao.ParkingInfoDao;
import com.arf.eparking.entity.ParkingInfo;
import com.arf.eparking.service.ParkingInfoService;
import com.arf.eparking.vo.ParkingInfoSearchVo;
import com.arf.platform.dao.PParKingrealTimeStatusDao;

@Service("parkingInfoServiceImpl")
public class ParkingInfoServiceImpl extends BaseServiceImpl<ParkingInfo, Long> implements ParkingInfoService {

	@Resource(name = "parkingInfoDaoImpl")
	private ParkingInfoDao parkingInfoDao;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "PParKingrealTimeStatusDaoImpl")
	private PParKingrealTimeStatusDao parKingRealTimeStatusDao;
	
	@Override
	protected BaseDao<ParkingInfo, Long> getBaseDao() {
		return parkingInfoDao;
	}

	@Override
	public ParkingInfo findByParkingNo(String parkingNo) {
		return parkingInfoDao.findByParkingNo(parkingNo);
	}
}
