package com.arf.laundry.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.laundry.dao.ILaundryCategoryPriceDao;
import com.arf.laundry.entity.LaundryCategoryPrice;
import com.arf.laundry.service.ILaundryCategoryPriceService;

@Service("laundryCategoryPriceServiceImpl")
public class LaundryCategoryPriceServiceImpl extends BaseServiceImpl<LaundryCategoryPrice, Long> implements ILaundryCategoryPriceService {

	@Resource(name = "laundryCategoryPriceDaoImpl")
	private ILaundryCategoryPriceDao laundryCategoryPriceDaoImpl; 
	
	@Override
	protected BaseDao<LaundryCategoryPrice, Long> getBaseDao() {
		return laundryCategoryPriceDaoImpl;
	}

	@Override
	public List<LaundryCategoryPrice> findSuperCategoryByBusinessNumStatus(Integer businessNum,LaundryCategoryPrice.Status status) {
		return laundryCategoryPriceDaoImpl.findSuperCategoryByBusinessNumStatus(businessNum,status);
	}

	@Override
	public List<LaundryCategoryPrice> findCategoryByBusinessNumAndSuperCategoryStatus(Integer businessNum, String superCategoryNum,LaundryCategoryPrice.Status status) {
		return laundryCategoryPriceDaoImpl.findCategoryByBusinessNumAndSuperCategoryStatus(businessNum,superCategoryNum,status);
	}
	
	@Override
	public List<LaundryCategoryPrice> findByCondition(Integer businessNum, String superCategoryNum, LaundryCategoryPrice.Status status){
		return laundryCategoryPriceDaoImpl.findByCondition(businessNum, superCategoryNum, status);
	}

	@Override
	public LaundryCategoryPrice findByCategoryNumStatus(String categoryNum,LaundryCategoryPrice.Status status) {
		return laundryCategoryPriceDaoImpl.findByCategoryNumStatus(categoryNum,status);
	}
	
	@Override
	public LaundryCategoryPrice findByBusinessNumCategoryNumStatus(String businessNum,String categoryNum,LaundryCategoryPrice.Status status){
		return laundryCategoryPriceDaoImpl.findByBusinessNumCategoryNumStatus(businessNum, categoryNum, status);
	}

}
