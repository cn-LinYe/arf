package com.arf.carbright.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.carbright.dao.IDiscountInfoDao;
import com.arf.carbright.entity.DiscountInfo;
import com.arf.carbright.entity.DiscountInfo.Status;
import com.arf.carbright.service.IDiscountInfoService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("discountInfoServiceImpl")
public class DiscountInfoServiceImpl extends BaseServiceImpl<DiscountInfo, Long> implements IDiscountInfoService{

	@Resource(name="discountInfoDaoImpl")
	IDiscountInfoDao discountInfoDao;
	@Override
	protected BaseDao<DiscountInfo, Long> getBaseDao() {
		return discountInfoDao;
	}
	@Override
	public DiscountInfo findByBusinessNum(Integer businessNum, Status status) {
		return discountInfoDao.findByBusinessNum(businessNum, status);
	}

}
