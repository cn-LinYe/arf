package com.arf.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.MethodTimeDao;
import com.arf.core.entity.MethodTime;

@Repository("methodTimeDaoImpl")
public class MethodTimeDaoImpl extends BaseDaoImpl<MethodTime, Long> implements MethodTimeDao {

}
