package com.arf.eparking.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.carbright.search.ParkingOrderRecordCondition;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.eparking.dao.ParkingOrderRecordDao;
import com.arf.eparking.entity.ParkingOrderRecord;
import com.arf.eparking.service.ParkingOrderRecordService;

@Service("parkingOrderRecordServiceImpl")
public class ParkingOrderRecordServiceImpl extends BaseServiceImpl<ParkingOrderRecord, Long>  implements ParkingOrderRecordService {

	@Resource(name = "parkingOrderRecordDaoImpl")
	private ParkingOrderRecordDao parkingOrderRecordDaoImpl;
	
	@Override
	protected BaseDao<ParkingOrderRecord, Long> getBaseDao() {
		return parkingOrderRecordDaoImpl;
	}
	
	@Override
	public ParkingOrderRecord findByOrderNo(String orderNo) {
		return parkingOrderRecordDaoImpl.findByOrderNo(orderNo);
	}

	@Override
	public ParkingOrderRecord findByUserNameAndArriveTime(String userName,Date arriveTime) {
		return parkingOrderRecordDaoImpl.findByUserNameAndArriveTime(userName,arriveTime);
	}

	@Override
	public PageResult<Map<String,Object>> findListByCondition(ParkingOrderRecordCondition condition) {
		return parkingOrderRecordDaoImpl.findListByCondition(condition);
	}

	@Override
	public ParkingOrderRecord findByUserName(String userName,Integer status) {
		return parkingOrderRecordDaoImpl.findByUserName(userName,status);
	}

	@Override
	public List<ParkingOrderRecord> findOrderEnableCancel() {
		return parkingOrderRecordDaoImpl.findOrderEnableCancel();
	}

	@Override
	public List<ParkingOrderRecord> findOrderEnableExtension() {
		return parkingOrderRecordDaoImpl.findOrderEnableExtension();
	}

	@Override
	public List<ParkingOrderRecord> findBreakContract(String userName) {
		return parkingOrderRecordDaoImpl.findBreakContract(userName);
	}

	@Override
	public ParkingOrderRecord findByLicenArriveTime(String license, Date date) {
		return parkingOrderRecordDaoImpl.findByLicenArriveTime(license,date);
	}
	
	@Override
	public List<ParkingOrderRecord> findOngoingOrderByUserName(String userName) {
		return parkingOrderRecordDaoImpl.findOngoingOrderByUserName(userName);
	}
	
	@Override
	public ParkingOrderRecord findOrderByUserName(String userName,String status) {
		return parkingOrderRecordDaoImpl.findOrderByUserName(userName, status);
	}

	@Override
	public PageResult<ParkingOrderRecord> findByCondition(ParkingOrderRecordCondition condition) {
		return parkingOrderRecordDaoImpl.findByCondition(condition);
	}

}
