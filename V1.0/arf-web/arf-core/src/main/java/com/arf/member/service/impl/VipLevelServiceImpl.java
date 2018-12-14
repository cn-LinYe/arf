package com.arf.member.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.Order;
import com.arf.core.Order.Direction;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.member.dao.IVipLevelDao;
import com.arf.member.entity.VipLevel;
import com.arf.member.service.IVipLevelService;

@Service("vipLevelService")
public class VipLevelServiceImpl extends BaseServiceImpl<VipLevel, Long> implements IVipLevelService {

	@Resource(name="vipLevelDaoImpl")
	private IVipLevelDao vipLevelDao;
	
	@Override
	protected BaseDao<VipLevel, Long> getBaseDao() {
		return vipLevelDao;
	}
	
	@Override
	public VipLevel findFirstByLeExp(long experience){
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order("requiredExperience", Direction.desc));
		List<VipLevel> vipLevels = this.findList(1, orders, new Filter("requiredExperience",Operator.le,experience));
		return CollectionUtils.isNotEmpty(vipLevels)?vipLevels.get(0):null;
	}

	@Override
	public VipLevel findByLevel(int level) {
		List<Order> orders = new ArrayList<Order>();
		List<VipLevel> vipLevels = this.findList(1, orders, new Filter("level",Operator.eq,level));
		return CollectionUtils.isNotEmpty(vipLevels)?vipLevels.get(0):null;
	}
}
