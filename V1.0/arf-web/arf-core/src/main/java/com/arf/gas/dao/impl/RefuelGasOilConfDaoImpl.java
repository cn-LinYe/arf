package com.arf.gas.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gas.dao.IRefuelGasOilConfDao;
import com.arf.gas.entity.RefuelGasOilConf;
import com.arf.gas.entity.RefuelGasOilConf.RetailWholesale;

@Repository("refuelGasOilConfDaoImpl")
public class RefuelGasOilConfDaoImpl extends BaseDaoImpl<RefuelGasOilConf, Long> implements IRefuelGasOilConfDao{

	@Override
	public List<RefuelGasOilConf> findAllOilType(Integer gasNum, Integer businessNum, RetailWholesale retailWholesale,List<Byte> oilTypeList) {
		StringBuffer queryHql = new StringBuffer(" from RefuelGasOilConf where 1=1 and status=:status");
		if(gasNum!=null){
			queryHql.append(" and (gasNum=:gasNum or gasNum is null)");
		}else{
			queryHql.append(" and gasNum is null");
		}
		if(businessNum!=null){
			queryHql.append(" and (businessNum=:businessNum or businessNum is null)");
		}else{
			queryHql.append(" and businessNum is null");
		}
		if(retailWholesale!=null){
			queryHql.append(" and retailWholesale=:retailWholesale");
		}
		if(oilTypeList!=null&&oilTypeList.size()>0){
			queryHql.append(" and oilType in (:oilTypeList) ");
		}
		queryHql.append(" and effectiveStart <=:now");
		queryHql.append(" and effectiveEnd >=:now");
		TypedQuery<RefuelGasOilConf> typedQuery = this.entityManager.createQuery(queryHql.toString(), RefuelGasOilConf.class);
		if(gasNum!=null){
			typedQuery.setParameter("gasNum", gasNum);
		}
		if(businessNum!=null){
			typedQuery.setParameter("businessNum", businessNum);
		}
		if(retailWholesale!=null){
			typedQuery.setParameter("retailWholesale", retailWholesale);
		}
		if(oilTypeList!=null&&oilTypeList.size()>0){
			typedQuery.setParameter("oilTypeList", oilTypeList);
		}
		typedQuery.setParameter("status", RefuelGasOilConf.Status.Active);
		typedQuery.setParameter("now", new Date());
		List<RefuelGasOilConf> list =typedQuery.getResultList();
		return list;
	}
	
	@Override
	public int updateStatus() {
		StringBuffer hql =new StringBuffer(" update RefuelGasOilConf a set a.status =:status where a.status=1 and a.effectiveEnd <=:now");
		Query queryCount = entityManager.createQuery(hql.toString());
		queryCount.setParameter("status", RefuelGasOilConf.Status.Expired);
		queryCount.setParameter("now", new Date());
		return queryCount.executeUpdate();
	}

}
