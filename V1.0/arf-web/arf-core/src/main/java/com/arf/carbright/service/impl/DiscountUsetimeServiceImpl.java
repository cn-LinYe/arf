package com.arf.carbright.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.carbright.dao.IDiscountUsetimeDao;
import com.arf.carbright.entity.DiscountUsetime;
import com.arf.carbright.service.IDiscountUsetimeService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("discountUsetimeServiceImpl")
public class DiscountUsetimeServiceImpl extends BaseServiceImpl<DiscountUsetime, Long> implements IDiscountUsetimeService{

	
	@Resource(name="discountUsetimeDaoImpl")
	IDiscountUsetimeDao discountUsetimeDao;
	@Override
	protected BaseDao<DiscountUsetime, Long> getBaseDao() {
		return discountUsetimeDao;
	}
	@Override
	public List<DiscountUsetime> findByDiscountFrequency(Long id, Integer frequency) {
		return discountUsetimeDao.findByDiscountFrequency(id, frequency);
	}

}
