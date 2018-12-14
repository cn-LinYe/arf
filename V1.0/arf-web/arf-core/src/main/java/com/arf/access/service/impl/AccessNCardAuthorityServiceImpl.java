package com.arf.access.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessNCardAuthorityDao;
import com.arf.access.entity.AccessNCardAuthority;
import com.arf.access.service.IAccessNCardAuthorityService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessNCardAuthorityService")
public class AccessNCardAuthorityServiceImpl extends BaseServiceImpl<AccessNCardAuthority, Long> implements IAccessNCardAuthorityService{

	@Resource(name="accessNCardAuthorityDao")
	IAccessNCardAuthorityDao accessNCardAuthorityDao;

	@Override
	protected BaseDao<AccessNCardAuthority, Long> getBaseDao() {
		return accessNCardAuthorityDao;
	}

	@Override
	public List<AccessNCardAuthority> findByCardId(Long cardId) {
		return accessNCardAuthorityDao.findByCardId(cardId);
	}

	@Override
	public void deleteByCardId(Long cardId) {
		accessNCardAuthorityDao.deleteByCardId(cardId);
	}

}
