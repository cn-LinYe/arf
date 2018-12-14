package com.arf.carbright.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.carbright.dao.IRRedemptionCouponRecordDao;
import com.arf.carbright.entity.RRedemptionCouponRecord;
import com.arf.carbright.service.IRRedemptionCouponRecordService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("rRedemptionCouponRecordServiceImpl")
public class RRedemptionCouponRecordServiceImpl extends BaseServiceImpl<RRedemptionCouponRecord, Long> implements IRRedemptionCouponRecordService{

	@Resource(name="rRedemptionCouponRecordDao")
	private IRRedemptionCouponRecordDao rRedemptionCouponRecordDao;
	
	@Override
	protected BaseDao<RRedemptionCouponRecord, Long> getBaseDao() {		
		return rRedemptionCouponRecordDao;
	}

	@Override
	public RRedemptionCouponRecord findByRedemptionCode(String redemptionCode) {
		return rRedemptionCouponRecordDao.findByRedemptionCode(redemptionCode);
	}

	@Override
	public int statisticsCountByGetCouponId(String getCouponId) {
		return rRedemptionCouponRecordDao.statisticsCountByGetCouponId(getCouponId);
	}

}
