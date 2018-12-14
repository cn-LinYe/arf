package com.arf.advertisements.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.advertisements.dao.ICloudButtonStyleDao;
import com.arf.advertisements.entity.CloudButtonStyle;
import com.arf.advertisements.entity.CloudButtonStyle.ButtonStatus;
import com.arf.advertisements.entity.CloudButtonStyle.ButtonType;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("cloudButtonStyleDao")
public class CloudButtonStyleDaoImpl extends BaseDaoImpl<CloudButtonStyle, Long> implements ICloudButtonStyleDao{

	@Override
	public List<CloudButtonStyle> findByButtonTypeButtonStatusCityCode(
			ButtonType buttonType, ButtonStatus buttonStatus, String cityCode) {
		StringBuffer hql = new StringBuffer("from CloudButtonStyle where 1 = 1");
		hql.append(" and buttonType = :buttonType");
		hql.append(" and buttonStatus = :buttonStatus");
//		if(StringUtils.isNotBlank(cityCode)){
//			hql.append(" and (excludeCities is null or FIND_IN_SET(':cityCode',excludeCities))");
//		}else{
//			hql.append(" and (excludeCities is null)");
//		}
		hql.append(" and buttonStartTime <= now() and now() <= buttonEndTime");
		TypedQuery<CloudButtonStyle> typedQuery = entityManager.createQuery(hql.toString(), CloudButtonStyle.class);
		typedQuery.setParameter("buttonType", buttonType);
		typedQuery.setParameter("buttonStatus", buttonStatus);
//		if(StringUtils.isNotBlank(cityCode)){
//			typedQuery.setParameter("cityCode", cityCode);
//		}
		return typedQuery.getResultList();
	}

}
