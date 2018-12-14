package com.arf.platform.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.platform.dao.ISExceptionCarInfoDao;
import com.arf.platform.entity.SExceptionCarInfo;
import com.arf.platform.service.ISExceptionCarInfoService;

@Service("sExceptionCarInfoServiceImpl")
public class SExceptionCarInfoServiceImpl extends BaseServiceImpl<SExceptionCarInfo, Long>
		implements ISExceptionCarInfoService {

	@Resource(name = "sExceptionCarInfoDaoImpl")
	private ISExceptionCarInfoDao sExceptionCarInfoDaoImpl;
	
	@Override
	protected BaseDao<SExceptionCarInfo, Long> getBaseDao() {
		return sExceptionCarInfoDaoImpl;
	}

	@Override
	public List<SExceptionCarInfo> findByLicense(String license) {
		return sExceptionCarInfoDaoImpl.findByLicense(license);
	}

}
