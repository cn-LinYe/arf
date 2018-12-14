package com.arf.installment.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.installment.dao.IInstallmentTypeDao;
import com.arf.installment.dto.InstallmentTypeDto;
import com.arf.installment.entity.InstallmentType;
import com.arf.installment.entity.InstallmentType.Type;

@Repository("installmentTypeDaoImpl")
public class InstallmentTypeDaoImpl extends BaseDaoImpl<InstallmentType,Long> implements IInstallmentTypeDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<InstallmentTypeDto> findByType(Type type) {
		
		StringBuffer sql =new StringBuffer(" SELECT ");
		sql.append("  id ,create_date createDate,type_num typeNum,");
		sql.append("  optional_periods as optionalPeriods,");
		sql.append("  installation_fee as installationFee,");
		sql.append("  market_price as marketPrice, status,");
		sql.append("  installment_price as installmentPrice,");
		sql.append("  model as model,first_funds as firstFunds,pre_pay_point as prePayPoint,");
		sql.append("  interest as interest,type_name as typeName,overdue_fine_day as overdueFineDay");
		sql.append("  FROM i_installment_type ");
		sql.append("  WHERE type=:type");
		sql.append("  and status="+InstallmentType.Status.UNDERWAY.ordinal());
		Query query =this.entityManager.createNativeQuery(sql.toString());
		query.setParameter("type", type.ordinal());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> rows = query.getResultList();
		List<InstallmentTypeDto> list = new ArrayList<InstallmentTypeDto>();
		if(CollectionUtils.isNotEmpty(rows)){
			for(Map<String,Object> row:rows){
				list.add(JSON.toJavaObject(new JSONObject(row), InstallmentTypeDto.class));
			}
		}
		return list;
	}

}
