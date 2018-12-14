package com.arf.royalstar.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.royalstar.dao.RSCarwashingDeviceDao;
import com.arf.royalstar.entity.RSCarwashingDevice;

@Repository("rsCarwashingDeviceDaoImpl")
public class RSCarwashingDeviceDaoImpl extends BaseDaoImpl<RSCarwashingDevice, Long> implements RSCarwashingDeviceDao {

	@Override
	public RSCarwashingDevice findByDeviceCode(String deviceCode){
		String hql = " from RSCarwashingDevice cd where cd.deviceCode = :deviceCode";
		TypedQuery<RSCarwashingDevice> query = this.entityManager.createQuery(hql, RSCarwashingDevice.class);
		query.setParameter("deviceCode", deviceCode);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Map<String, Object> resolvePayQRCode(String deviceCode,String userName) {
		StringBuffer sql =new StringBuffer(" SELECT ");
		sql.append("  r.coin_count_once coinCountOnce, p.business_num as businessNum,");
		sql.append("  p.business_name as businessName,p.address as businessAddress ,p.business_pic as businessPic ,");
		sql.append("  p.mobile as businessPhone,p.business_hours as businessHours ,p.business_grade as businessGrade ,");
		sql.append("  p.business_comment as businessComment ,p.business_description as businessDescription ,r.device_status deviceStatus");
		sql.append("  FROM rs_carwashing_device r ");
		sql.append("  LEFT JOIN p_business p ON p.business_num = r.business_num");
		sql.append("  WHERE 1=1  AND r.device_code=:deviceCode ");
		
		Query query =this.entityManager.createNativeQuery(sql.toString());	
		query.setParameter("deviceCode", deviceCode);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list  =query.getResultList();
		return CollectionUtils.isEmpty(list)?null:list.get(0);
		
	}
}
