package com.arf.core.service.impl;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.MethodTimeDao;
import com.arf.core.entity.MethodTime;
import com.arf.core.service.MethodTimeService;

@Service("methodTimeServiceImpl")
@Lazy(false)
public class MethodTimeServiceImpl extends BaseServiceImpl<MethodTime, Long> implements MethodTimeService {

	@Resource(name = "methodTimeDaoImpl")
	private MethodTimeDao methodTimeDao;

	@Override
	protected BaseDao<MethodTime, Long> getBaseDao() {
		return methodTimeDao;
	}
}
