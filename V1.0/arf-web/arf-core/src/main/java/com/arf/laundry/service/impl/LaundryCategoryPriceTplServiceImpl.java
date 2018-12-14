package com.arf.laundry.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.laundry.dao.ILaundryCategoryPriceTplDao;
import com.arf.laundry.entity.LaundryCategoryPriceTpl;
import com.arf.laundry.service.ILaundryCategoryPriceTplService;

@Service("laundryCategoryPriceTplServiceImpl")
public class LaundryCategoryPriceTplServiceImpl extends BaseServiceImpl<LaundryCategoryPriceTpl, Long> implements ILaundryCategoryPriceTplService {

	@Resource(name = "laundryCategoryPriceTplDaoImpl")
	private ILaundryCategoryPriceTplDao laundryCategoryPriceTplDaoImpl;
	
	@Override
	protected BaseDao<LaundryCategoryPriceTpl, Long> getBaseDao() {
		return laundryCategoryPriceTplDaoImpl;
	}

	@Override
	public LaundryCategoryPriceTpl findByCategoryNum(String categoryNum){
		return laundryCategoryPriceTplDaoImpl.findByCategoryNum(categoryNum);
	}
}
