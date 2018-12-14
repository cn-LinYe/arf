package com.arf.axdshopkeeper.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.axdshopkeeper.dao.IShopkeeperApplyDao;
import com.arf.axdshopkeeper.entity.ShopkeeperApply;
import com.arf.axdshopkeeper.service.IShopkeeperApplyService;
import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.google.common.collect.Lists;

@Service("shopkeeperApplyServiceImpl")
public class ShopkeeperApplyServiceImpl extends
		BaseServiceImpl<ShopkeeperApply, Long> implements
		IShopkeeperApplyService {

	@Resource(name = "shopkeeperApplyDaoImpl")
	private IShopkeeperApplyDao shopkeeperApplyDaoImpl;
	
	@Override
	protected BaseDao<ShopkeeperApply, Long> getBaseDao() {
		return shopkeeperApplyDaoImpl;
	}

	@Override
	public ShopkeeperApply findByMobileAndCommunity(String mobile, String communityNumber) {
		List<Filter> filters = Lists.newArrayList();
		filters.add(new Filter("applyMobile",Operator.eq,mobile));
		filters.add(new Filter("communityNumber",Operator.eq,communityNumber));
		List<ShopkeeperApply> list = this.findList(null, filters, null);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

}
