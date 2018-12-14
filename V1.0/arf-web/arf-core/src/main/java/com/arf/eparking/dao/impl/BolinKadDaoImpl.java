package com.arf.eparking.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.eparking.dao.BolinKadDao;
import com.arf.eparking.entity.BolinKadModel;
import com.arf.eparking.service.BolinKadService;

@Repository("bolinKadDaoImpl")
public class BolinKadDaoImpl extends BaseDaoImpl<BolinKadModel, Long> implements BolinKadDao {


}
