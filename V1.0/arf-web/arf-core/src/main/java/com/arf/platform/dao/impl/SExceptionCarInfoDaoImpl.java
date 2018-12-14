package com.arf.platform.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.platform.dao.ISExceptionCarInfoDao;
import com.arf.platform.entity.SExceptionCarInfo;

@Repository("sExceptionCarInfoDaoImpl")
public class SExceptionCarInfoDaoImpl extends BaseDaoImpl<SExceptionCarInfo, Long> implements ISExceptionCarInfoDao {

	@Override
	public List<SExceptionCarInfo> findByLicense(String license) {
		String hql = "from com.arf.platform.entity.SExceptionCarInfo where license = :license";
		return entityManager.createQuery(hql,SExceptionCarInfo.class).setParameter("license", license).getResultList();
	}


}
