package com.arf.eparking.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.eparking.dao.BolinKadDao;
import com.arf.eparking.entity.BolinKadModel;
import com.arf.eparking.service.BolinKadService;

@Repository("bolinKadServiceImpl")
public class BolinKadServiceImpl extends BaseServiceImpl<BolinKadModel, Long> implements BolinKadService {

	@Resource(name = "bolinKadDaoImpl")
	private BolinKadDao bolinKadDao;
	
	@Override
	protected BaseDao<BolinKadModel, Long> getBaseDao() {
		return bolinKadDao;
	}


}
