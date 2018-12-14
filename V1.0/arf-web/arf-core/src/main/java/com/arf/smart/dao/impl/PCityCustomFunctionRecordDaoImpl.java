package com.arf.smart.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.smart.dao.IPCityCustomFunctionRecordDao;
import com.arf.smart.entity.PCityCustomFunctionRecord;

@Repository("pcityCustomFunctionRecordDao")
public class PCityCustomFunctionRecordDaoImpl extends BaseDaoImpl<PCityCustomFunctionRecord, Long> implements IPCityCustomFunctionRecordDao{

	@Override
	public List<PCityCustomFunctionRecord> findByCityCode(String cityCode) {
		StringBuffer hql = new StringBuffer("from PCityCustomFunctionRecord where 1 = 1");
		if(StringUtils.isNotBlank(cityCode)){
			hql.append(" and (cities like CONCAT('%',:cityCode,'%') ");
		}
		
		TypedQuery<PCityCustomFunctionRecord> typedQuery = super.entityManager.createQuery(hql.toString(), PCityCustomFunctionRecord.class);
		
		if(StringUtils.isNotBlank(cityCode)){
			typedQuery.setParameter("cityCode", cityCode);
		}
		
		List<PCityCustomFunctionRecord> list=typedQuery.getResultList();
		return list;
	}

}
