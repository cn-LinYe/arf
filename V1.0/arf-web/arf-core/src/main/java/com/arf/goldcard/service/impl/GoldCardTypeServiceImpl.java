package com.arf.goldcard.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.goldcard.dao.IGoldCardTypeDao;
import com.arf.goldcard.entity.GoldCardType;
import com.arf.goldcard.service.IGoldCardTypeService;

@Service("goldCardTypeService")
public class GoldCardTypeServiceImpl extends BaseServiceImpl<GoldCardType, Long> implements IGoldCardTypeService{

	@Resource(name = "goldCardTypeDao")
	private IGoldCardTypeDao goldCardTypeDao;
	
	@Override
	protected BaseDao<GoldCardType, Long> getBaseDao() {
		return goldCardTypeDao;
	}

	@Override
	public GoldCardType findByTypeNum(String typeNum) {
		List<GoldCardType> types = this.findList(null, null, new Filter("typeNum",Operator.eq,typeNum,false));
		if(CollectionUtils.isEmpty(types)){
			return null;
		}
		return types.get(0);
	}

	@Override
	public List<GoldCardType> findByUnShelved(Integer unShelved,Date nowTime) {
		
		return goldCardTypeDao.findByUnShelved(unShelved,nowTime);
	}
}
