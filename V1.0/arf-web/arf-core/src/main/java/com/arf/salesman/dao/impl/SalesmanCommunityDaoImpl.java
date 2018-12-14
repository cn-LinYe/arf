package com.arf.salesman.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.salesman.dao.ISalesmanCommunityDao;
import com.arf.salesman.entity.SalesmanCommunity;

@Repository("salesmanCommunityDaoImpl")
public class SalesmanCommunityDaoImpl extends BaseDaoImpl<SalesmanCommunity, Long>implements ISalesmanCommunityDao{

	@SuppressWarnings("unchecked")
	@Override
	public PageResult<Map<String, Object>> findByUserName(String userName,Integer pageNo,Integer pageSize) {
		
		StringBuffer sqlCount = new StringBuffer(" SELECT  count(1)");
		StringBuffer sql = new StringBuffer(" SELECT  ");
		sql.append(" case when ro.status=0 THEN 0 ELSE 1 END switchGates,");
		sql.append(" rc.community_name communityName,rc.community_number communityNumber  ");
		sql.append(" FROM r_salesman_community rc");
		sql.append(" LEFT JOIN r_saleman_operating ro ");
		sql.append(" ON rc.community_number =ro.community_number");
		sql.append(" WHERE rc.user_name=:userName");
		sql.append(" ORDER BY rc.create_date ");
		
		sqlCount.append(" FROM r_salesman_community rc");
		sqlCount.append(" LEFT JOIN r_saleman_operating ro ");
		sqlCount.append(" ON rc.community_number =ro.community_number");
		sqlCount.append(" WHERE rc.user_name=:userName");
		
		Query query =entityManager.createNativeQuery(sql.toString());
		Query queryCount =entityManager.createNativeQuery(sqlCount.toString());
		
		
		if (pageNo!=null&&pageSize!=null) {
			query.setMaxResults(pageSize);
			query.setFirstResult((pageNo-1)*pageSize);
		}
		query.setParameter("userName", userName);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list=query.getResultList();
		
		int count =0;
		try {
			count =((BigInteger)queryCount.getSingleResult()).intValue();			
		} catch (Exception e) {
		}
		PageResult<Map<String,Object>> result = new PageResult<Map<String, Object>>(list,count);
		return result;
	}

	@Override
	public SalesmanCommunity findByUserNameCommunity(String communityNum, String userName) {
		String hql ="from SalesmanCommunity where communityNumber =:communityNum and userName=:userName";
		TypedQuery<SalesmanCommunity> query =entityManager.createQuery(hql,SalesmanCommunity.class);
		query.setParameter("communityNum", communityNum);
		query.setParameter("userName", userName);
		List<SalesmanCommunity> salesmanCommunities = null;
		try {
			salesmanCommunities=query.getResultList();
		} catch (Exception e) {
		}
		return CollectionUtils.isEmpty(salesmanCommunities)?null:salesmanCommunities.get(0);
	}
}





