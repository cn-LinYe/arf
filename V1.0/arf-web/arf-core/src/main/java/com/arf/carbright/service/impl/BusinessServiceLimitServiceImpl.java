package com.arf.carbright.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.carbright.dao.IBusinessServiceLimitDao;
import com.arf.carbright.entity.BusinessServiceLimit;
import com.arf.carbright.service.IBusinessServiceLimitService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("businessServiceLimitServiceImpl")
public class BusinessServiceLimitServiceImpl extends BaseServiceImpl<BusinessServiceLimit, Long> implements IBusinessServiceLimitService{

	@Resource(name="businessServiceLimitDaoImpl")
	IBusinessServiceLimitDao iBusinessServiceLimitDao;
	@Override
	protected BaseDao<BusinessServiceLimit, Long> getBaseDao() {
		return iBusinessServiceLimitDao;
	}
	@Override
	public BusinessServiceLimit findByBusinessNum(Integer businessNum) {
		return iBusinessServiceLimitDao.findByBusinessNum(businessNum);
	}

}
