package com.arf.relievedassist.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.relievedassist.dao.IRelievedAssistClassifyDao;
import com.arf.relievedassist.entity.RelievedAssistClassify;
import com.arf.relievedassist.service.IRelievedAssistClassifyService;

@Service("relievedAssistClassifyService")
public class RelievedAssistClassifyServiceImpl extends BaseServiceImpl<RelievedAssistClassify, Long> implements IRelievedAssistClassifyService{

	@Resource(name="relievedAssistClassifyDao")
	IRelievedAssistClassifyDao relievedAssistClassifyDao;

	@Override
	protected BaseDao<RelievedAssistClassify, Long> getBaseDao() {
		return relievedAssistClassifyDao;
	}

	@Override
	public List<RelievedAssistClassify> findNormalClassify(RelievedAssistClassify.Status status) {
		return relievedAssistClassifyDao.findNormalClassify(status);
	}
	
}
