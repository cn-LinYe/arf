package com.arf.carbright.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.IBusinessToCommunityDao;
import com.arf.carbright.entity.BusinessToCommunity;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("businessToCommunityDaoImpl")
public class BusinessToCommunityDaoImpl extends BaseDaoImpl<BusinessToCommunity, Long> implements IBusinessToCommunityDao {

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> findByBusinessNum(String businessNum) {
		
		StringBuffer jpql = new StringBuffer(" SELECT c.community_number,c.property_number,c.branch_id,c.community_name,");
		jpql.append("  r.gas_status gasStatus,r.oil_status oilStatus,r.gas_num gasNum,r.gas_name gasName");
		jpql.append(" FROM p_business_to_community b ");
		jpql.append(" LEFT JOIN community c ON c.community_number =b.community_num");
		jpql.append(" LEFT JOIN refuel_gas_station r ON r.business_num =b.business_num");
		jpql.append(" WHERE  b.business_num  = :businessNum");
		Query query = entityManager.createNativeQuery(jpql.toString());
		query.setParameter("businessNum", businessNum);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> rows = query.getResultList();
		return (CollectionUtils.isEmpty(rows))?null:rows.get(0);
	}

	@Override
	public BusinessToCommunity findByBusinessId(Long id) {
		StringBuffer sb=new StringBuffer("from BusinessToCommunity where id=:id");
		TypedQuery<BusinessToCommunity> query=entityManager.createQuery(sb.toString(),BusinessToCommunity.class);
		query.setParameter("id", id);
		List<BusinessToCommunity> list =query.getResultList();
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
}
