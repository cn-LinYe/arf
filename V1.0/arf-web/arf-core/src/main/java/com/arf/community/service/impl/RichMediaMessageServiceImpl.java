package com.arf.community.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.community.dao.RichMediaMessageDao;
import com.arf.community.entity.RichMediaMessage;
import com.arf.community.service.RichMediaMessageService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("richMediaMessageServiceImpl")
public class RichMediaMessageServiceImpl extends BaseServiceImpl<RichMediaMessage, Long> implements RichMediaMessageService {

	@Resource(name="richMediaMessageDaoImpl")
	RichMediaMessageDao richMediaMessageDaoImpl;
	
	@Override
	protected BaseDao<RichMediaMessage, Long> getBaseDao() {
		return richMediaMessageDaoImpl;
	}

	

}
