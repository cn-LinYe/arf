package com.arf.smart.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.smart.dao.PCustomIconDao;
import com.arf.smart.entity.PCustomIcon;
import com.arf.smart.service.PCustomIconService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("pcustomIconService")
public class PCustomIconServiceImpl extends BaseServiceImpl<PCustomIcon, Long> implements PCustomIconService{

	@Resource(name="pcustomIconDao")
	PCustomIconDao pcustomIconDao;
	
	@Override
	protected BaseDao<PCustomIcon, Long> getBaseDao() {
		return pcustomIconDao;
	}

}
