package com.arf.carbright.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.carbright.dao.IAxdVouchersRecordDao;
import com.arf.carbright.entity.AxdVouchersRecord;
import com.arf.carbright.entity.AxdVouchersRecord.FeePayStatus;
import com.arf.carbright.entity.AxdVouchersRecord.UsedStatus;
import com.arf.carbright.entity.AxdVouchersRecord.VouchersType;
import com.arf.carbright.service.IAxdVouchersRecordService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("axdVouchersRecordServiceImpl")
public class AxdVouchersRecordServiceImpl extends BaseServiceImpl<AxdVouchersRecord, Long> implements IAxdVouchersRecordService{

	@Resource(name ="axdVouchersRecordDaoImpl")
	private IAxdVouchersRecordDao axdVouchersRecordDaoImpl;
	
	@Override
	protected BaseDao<AxdVouchersRecord, Long> getBaseDao() {
		return axdVouchersRecordDaoImpl;
	}

	@Override
	public PageResult<AxdVouchersRecord> findUserVouchersRecord(String userName, VouchersType vouchersType,
			UsedStatus usedStatus, int pageSize, int pageNo) {
		return axdVouchersRecordDaoImpl.findUserVouchersRecord(userName, vouchersType, usedStatus, pageSize, pageNo);
	}

	@Override
	public List<AxdVouchersRecord> findByStatusAndEndTime(UsedStatus usedStatus, Date endTime) {
		return axdVouchersRecordDaoImpl.findByStatusAndEndTime(usedStatus, endTime);
	}

	@Override
	public AxdVouchersRecord findUsedDishVouchersByNum(String userName,String vouchersNums, UsedStatus usedStatus) {
		return axdVouchersRecordDaoImpl.findUsedDishVouchersByNum(userName,vouchersNums, usedStatus);
	}

	@Override
	public Integer findUserAllVouchersRecord(String userName, UsedStatus usedStatus) {
		return axdVouchersRecordDaoImpl.findUserAllVouchersRecord(userName, usedStatus);
	}

	@Override
	public List<Map<String , Object>> findUserVouchersType(String userName, UsedStatus usedStatus) {
		return axdVouchersRecordDaoImpl.findUserVouchersType(userName, usedStatus);
	}

	@Override
	public void updateBatch(Integer useStatus, Integer feePayType, String outTradeNo, Integer feePayStatus,
			List<String> list) {
		axdVouchersRecordDaoImpl.updateBatch(useStatus, feePayType, outTradeNo, feePayStatus, list);
	}

	@Override
	public AxdVouchersRecord findUsedDishVouchersByNum(String orderNo, FeePayStatus feePayStatus) {
		return axdVouchersRecordDaoImpl.findUsedDishVouchersByNum(orderNo, feePayStatus);
	}

	@Override
	public AxdVouchersRecord findVouchersByNum(String userName, String vouchersNums) {
		return axdVouchersRecordDaoImpl.findVouchersByNum(userName, vouchersNums);
	}

}
