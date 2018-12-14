package com.arf.crowdfunding.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.crowdfunding.dao.ZcShopkeeperPartnerIncomeGatherDao;
import com.arf.crowdfunding.entity.ZcShopkeeperPartnerIncomeGather;

@Repository("zcShopkeeperPartnerIncomeGatherDao")
public class ZcShopkeeperPartnerIncomeGatherDaoImpl extends BaseDaoImpl<ZcShopkeeperPartnerIncomeGather, Long> implements ZcShopkeeperPartnerIncomeGatherDao{

	@Override
	public List<ZcShopkeeperPartnerIncomeGather> findIncomeGatherbyInquire(String community_number, String project_id,
			String shopkeeper_partner_user, Integer income_inquire, Integer condition) {
		StringBuilder jpql = new StringBuilder();				
		jpql.append("from ZcShopkeeperPartnerIncomeGather where communityNumber=:communityNumber and projectId=:projectId");
		jpql.append(" and projectId=:projectId and shopkeeperPartnerUser=:shopkeeperPartnerUser and incomeInquire=:incomeInquire");
		if(condition>0)//此参数可用
		{
			jpql.append(" order by incomeTimeEnd");
		}
		TypedQuery<ZcShopkeeperPartnerIncomeGather> typedQuery= entityManager.createQuery(jpql.toString(), ZcShopkeeperPartnerIncomeGather.class);
		typedQuery.setParameter("communityNumber", community_number);
		typedQuery.setParameter("projectId", project_id);
		typedQuery.setParameter("shopkeeperPartnerUser", shopkeeper_partner_user);
		typedQuery.setParameter("incomeInquire", income_inquire);
		if(condition>0)//此参数可用
		{
			typedQuery.setFirstResult(0);			
			typedQuery.setMaxResults(condition);
		}
		List<ZcShopkeeperPartnerIncomeGather> list = typedQuery.getResultList();

		return CollectionUtils.isEmpty(list)?null:list;
	}

	
}
