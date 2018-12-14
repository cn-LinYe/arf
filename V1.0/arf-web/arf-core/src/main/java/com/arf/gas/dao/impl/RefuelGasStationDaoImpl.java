package com.arf.gas.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gas.dao.IRefuelGasStationDao;
import com.arf.gas.entity.RefuelGasCard;
import com.arf.gas.entity.RefuelGasStation;

@Repository("refuelGasStationDaoImpl")
public class RefuelGasStationDaoImpl extends BaseDaoImpl<RefuelGasStation, Long> implements IRefuelGasStationDao{

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public Map<String, Object> findByGasNum(Integer gasNum, Integer businessNum) {
		StringBuffer queryHql = new StringBuffer("select ");
		queryHql.append(" a.id id, a.gas_status gasStatus,a.oil_status oilStatus,");
		queryHql.append(" a.business_num businessNum,a.gas_num gasNum,a.gas_name gasName,");
		queryHql.append(" b.business_name businessName,b.address businessAddress,b.business_pic businessPic,");
		queryHql.append(" b.business_description businessDescription,b.lat lat,b.lng lng");
		queryHql.append(" from refuel_gas_station a ");
		
		queryHql.append(" left join  p_business b on b.business_num=a.business_num");
		queryHql.append(" where 1=1 ");
		
		if(gasNum!=null){
			queryHql.append(" and a.gas_num =:gasNum");
		}
		if(businessNum!=null){
			queryHql.append(" and a.business_num =:businessNum");
		}
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		
		if(gasNum!=null){
			typedQuery.setParameter("gasNum", gasNum);
		}
		if(businessNum!=null){
			typedQuery.setParameter("businessNum", businessNum);
		}
		
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list = typedQuery.getResultList();
		try {
			return list.isEmpty()?null:list.get(0);
		} catch (Exception e) {
			logger.error("查询油站信息出错", e);
			return null;
		}
	}
	@Override
	public RefuelGasStation findByGasNumAndBusiness(Integer gasNum, Integer businessNum) {
		StringBuffer hql =new StringBuffer("from RefuelGasStation where  businessNum= :businessNum");
		if (gasNum!=null) {
			hql.append(" and gasNum = :gasNum ");	
		}
		Query query =this.entityManager.createQuery(hql.toString(),RefuelGasStation.class);
		query.setParameter("businessNum", businessNum);
		if (gasNum!=null) {
			query.setParameter("gasNum", gasNum);
		}
		List<RefuelGasStation> list=query.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

}
