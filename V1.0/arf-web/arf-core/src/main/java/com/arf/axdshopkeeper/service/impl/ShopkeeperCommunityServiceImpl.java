package com.arf.axdshopkeeper.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.axdshopkeeper.dao.IShopkeeperCommunityDao;
import com.arf.axdshopkeeper.dto.ShopkeeperCommunityDto;
import com.arf.axdshopkeeper.entity.ShopkeeperCommunity;
import com.arf.axdshopkeeper.service.IShopkeeperCommunityService;
import com.arf.base.PageResult;
import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.google.common.collect.Lists;


@Service("shopkeeperCommunitServiceImpl")
public class ShopkeeperCommunityServiceImpl extends
		BaseServiceImpl<ShopkeeperCommunity, Long> implements
		IShopkeeperCommunityService {

	@Resource(name = "shopkeeperCommunityDaoImpl")
	private IShopkeeperCommunityDao shopkeeperCommunityDaoImpl;
	
	@Override
	protected BaseDao<ShopkeeperCommunity, Long> getBaseDao() {
		return shopkeeperCommunityDaoImpl;
	}
	
	@Override
	public ShopkeeperCommunity findByNumber(String communityNumber) {
		List<Filter> filters = Lists.newArrayList();
		filters.add(new Filter("communityNumber",Operator.eq,communityNumber));
		List<ShopkeeperCommunity> list = this.findList(null, filters, null);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public PageResult<ShopkeeperCommunity> findByCondition(Integer pageSize,
			Integer pageNo, String cityNo, ShopkeeperCommunityDto.OrderBy orderBy) {
		return shopkeeperCommunityDaoImpl.findByCondition(pageSize,pageNo,cityNo,orderBy);
	}
}
