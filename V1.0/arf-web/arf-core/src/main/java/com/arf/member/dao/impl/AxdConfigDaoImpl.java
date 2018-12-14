package com.arf.member.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.member.dao.IAxdConfigDao;
import com.arf.member.entity.AxdConfig;

@Repository("axdConfigDaoImpl")
public class AxdConfigDaoImpl extends BaseDaoImpl<AxdConfig, Long> implements IAxdConfigDao {

	@Override
	public AxdConfig findByUsernameAndLicense(String userName, String license) {
		String jpql = "select axdConfig from AxdConfig axdConfig where axdConfig.userName = :userName and axdConfig.license = :license";
		List<AxdConfig> list=entityManager.createQuery(jpql, AxdConfig.class).setParameter("userName", userName).setParameter("license", license).getResultList();
		return list.isEmpty()?null:list.get(0);
	}

}
