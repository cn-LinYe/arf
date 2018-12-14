package com.arf.access.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessDeviceStatusInfoDao;
import com.arf.access.entity.AccessDeviceStatusInfo;
import com.arf.access.entity.AccessManagement;
import com.arf.access.entity.AccessDeviceStatusInfo.Status;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessDeviceStatusInfoDao")
public class AccessDeviceStatusInfoDaoImpl extends BaseDaoImpl<AccessDeviceStatusInfo, Long> implements IAccessDeviceStatusInfoDao{

	@Override
	public AccessDeviceStatusInfo findByAccessMacAndStatus(String accessMac, Status status) {
		StringBuffer hql = new StringBuffer("from AccessDeviceStatusInfo where accessMac = :accessMac");
		hql.append(" and status = :status");
		hql.append(" order by createDate Desc ");
		TypedQuery<AccessDeviceStatusInfo> typedQuery = super.entityManager.createQuery(hql.toString(), AccessDeviceStatusInfo.class);
		typedQuery.setParameter("accessMac", accessMac);
		typedQuery.setParameter("status", status);
		List<AccessDeviceStatusInfo> list = typedQuery.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

}
