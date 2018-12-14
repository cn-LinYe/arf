package com.arf.access.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessTempPasswdDao;
import com.arf.access.entity.AccessTempPasswd;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessTempPasswdDaoImpl")
public class AccessTempPasswdDaoImpl extends BaseDaoImpl<AccessTempPasswd, Long> implements IAccessTempPasswdDao{

	@Override
	public AccessTempPasswd findByBlueMacAndPwd(String password, String bluetoothMac) {
		StringBuffer hql = new StringBuffer("from AccessTempPasswd where ");
		hql.append(" bluetoothMac = :bluetoothMac and passwd = :password ");
		hql.append(" order by createDate desc ");
		TypedQuery<AccessTempPasswd> query = this.entityManager.createQuery(hql.toString(), AccessTempPasswd.class);
		query.setParameter("bluetoothMac", bluetoothMac);
		query.setParameter("password", password);
		try {
			List<AccessTempPasswd> list = query.getResultList();
			return list.isEmpty()?null:list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
