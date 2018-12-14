package com.arf.carbright.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.carbright.dao.IRGetCouponRecordDao;
import com.arf.carbright.entity.RGetCouponRecord;
import com.arf.carbright.service.IRGetCouponRecordService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("rGetCouponRecordServiceImpl")
public class RGetCouponRecordServiceImpl extends BaseServiceImpl<RGetCouponRecord, Long> implements IRGetCouponRecordService {

	@Resource(name="rGetCouponRecordDao")
	private IRGetCouponRecordDao rGetCouponRecord;
	
	@Override
	protected BaseDao<RGetCouponRecord, Long> getBaseDao() {		
		return rGetCouponRecord;
	}

}
