package com.arf.eparking.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.eparking.dao.OrderfeeRuleItemDao;
import com.arf.eparking.entity.OrderfeeRuleItem;
import com.arf.eparking.entity.OrderfeeRuleItem.Items;
import com.arf.eparking.entity.OrderfeeRuleItem.Use;

@Repository("orderfeeRuleItemDaoImpl")
public class OrderfeeRuleItemDaoImpl extends BaseDaoImpl<OrderfeeRuleItem, Long> implements OrderfeeRuleItemDao {

	@Override
	@Deprecated // {@link com.arf.eparking.dao.impl.OrderfeeRuleItemDaoImpl.findByParkingIdUse(String, Integer, Items)}
	public OrderfeeRuleItem findByParkingIdUse(String parkingId, Integer use,String key) {
		StringBuffer hql = new StringBuffer("from OrderfeeRuleItem o where 1 = 1 and parkingId = :parkingId and iNo = :key");
		if(use != null){
			hql.append(" and iUse = :use");
		}
		TypedQuery<OrderfeeRuleItem> query = entityManager.createQuery(hql.toString(), OrderfeeRuleItem.class);
		query.setParameter("parkingId",parkingId);
		query.setParameter("key",key);
		if(use != null){
			query.setParameter("use",use);
		}
		List<OrderfeeRuleItem> list = query.getResultList();
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	@Override
	public OrderfeeRuleItem findByParkingIdUse(String parkingId, Use use,Items items) {
		Assert.notNull(items,"配置项名称不能为空");
		return this.findByParkingIdUse(parkingId, use!=null?use.ordinal():null, items.toString());
	}

	@Override
	public List<OrderfeeRuleItem> findByParkingIdUse(String parkingId, Use use) {
		StringBuffer hql = new StringBuffer("from OrderfeeRuleItem o where 1 = 1 and parkingId = :parkingId");
		if(use != null){
			hql.append(" and iUse = :use");
		}
		TypedQuery<OrderfeeRuleItem> query = entityManager.createQuery(hql.toString(), OrderfeeRuleItem.class);
		query.setParameter("parkingId",parkingId);
		if(use != null){
			query.setParameter("use",use.ordinal());
		}
		List<OrderfeeRuleItem> list = query.getResultList();
		return list;
	}

}
