package com.arf.axdshopkeeper.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.axdshopkeeper.dao.IShopkeeperLevelDao;
import com.arf.axdshopkeeper.entity.ShopkeeperLevel;
import com.arf.axdshopkeeper.service.IShopkeeperLevelService;
import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.google.common.collect.Lists;

@Service("shopkeeperLevelService")
public class ShopkeeperLevelServiceImpl extends
		BaseServiceImpl<ShopkeeperLevel, Long> implements
		IShopkeeperLevelService {
	
	@Resource(name = "shopkeeperLevelDao")
	private IShopkeeperLevelDao shopkeeperLevelDao;
	
	@Override
	protected BaseDao<ShopkeeperLevel, Long> getBaseDao() {
		return null;
	}
	
	@Override
	public ShopkeeperLevel findByLevelIndex(Integer levelIndex){
		List<Filter> filters = Lists.newArrayList();
		filters.add(new Filter("levelIndex",Operator.eq,levelIndex));
		try{
			List<ShopkeeperLevel> list = this.findList(null,filters,null);
			if(CollectionUtils.isNotEmpty(list)){
				return list.get(0);
			}else{
				return null;
			}
		}catch(Exception e){
			return null;
		}
		
	}

}
