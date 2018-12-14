package com.arf.eparking.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.eparking.dao.ParkingFeeApplicationRecordsDao;
import com.arf.eparking.entity.ParkingFeeApplicationRecords;
import com.arf.eparking.entity.ParkingFeeApplicationRecords.ApplyStatus;
import com.arf.eparking.service.ParkingFeeApplicationRecordsService;

@Service("parkingFeeApplicationRecordsService")
public class ParkingFeeApplicationRecordsServiceImpl extends BaseServiceImpl<ParkingFeeApplicationRecords, Long> implements ParkingFeeApplicationRecordsService{

	@Resource(name="parkingFeeApplicationRecordsDao")
	ParkingFeeApplicationRecordsDao parkingFeeApplicationRecordsDao;
	
	@Override
	protected BaseDao<ParkingFeeApplicationRecords, Long> getBaseDao() {
		return parkingFeeApplicationRecordsDao;
	}

	@Override
	public PageResult<Map<String, Object>> findByBusinessNumAndApplyStatus(String businessNum, ApplyStatus applyStatus,
			Integer pageSize, Integer pageNo) {
		return parkingFeeApplicationRecordsDao.findByBusinessNumAndApplyStatus(businessNum, applyStatus, pageSize, pageNo);
	}

	@Override
	public List<ParkingFeeApplicationRecords> findByBusinessNumAndOrderNo(String businessNum, String orderNo) {
		return parkingFeeApplicationRecordsDao.findByBusinessNumAndOrderNo(businessNum, orderNo);
	}

	@Override
	public List<ParkingFeeApplicationRecords> findByBusinessNumAndLicense(String businessNum, String communityNumber,
			String license, Date arriveTime) {
		return parkingFeeApplicationRecordsDao.findByBusinessNumAndLicense(businessNum, communityNumber, license, arriveTime);
	}

	@Override
	public PageResult<Map<String, Object>> findByOpenId(String openId, Integer pageSize,Integer pageNo) {
		return parkingFeeApplicationRecordsDao.findByOpenId(openId, pageSize, pageNo);
	}

	@Override
	public List<Map<String, Object>> statisticsBusinessNumOrder(String businessNum, ApplyStatus applyStatus,
			String startTime, String endTime) {
		return parkingFeeApplicationRecordsDao.statisticsBusinessNumOrder(businessNum, applyStatus, startTime, endTime);
	}

	@Override
	public PageResult<Map<String, Object>> findByBusinessNumAndApplyStatus(String businessNum, ApplyStatus applyStatus,
			Integer pageSize, Integer pageNo, String startTime, String endTime) {
		return parkingFeeApplicationRecordsDao.findByBusinessNumAndApplyStatus(businessNum, applyStatus, pageSize, pageNo, startTime, endTime);
	}

}
