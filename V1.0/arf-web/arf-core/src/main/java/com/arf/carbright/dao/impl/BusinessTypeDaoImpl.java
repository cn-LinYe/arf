package com.arf.carbright.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.BusinessTypeDao;
import com.arf.carbright.entity.BusinessType;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("businessTypeDao")
public class BusinessTypeDaoImpl extends BaseDaoImpl<BusinessType, Long> implements BusinessTypeDao {


}
