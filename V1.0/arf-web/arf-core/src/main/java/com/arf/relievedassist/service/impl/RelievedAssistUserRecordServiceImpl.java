package com.arf.relievedassist.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.relievedassist.dao.IRelievedAssistUserRecordDao;
import com.arf.relievedassist.entity.RelievedAssistUserRecord;
import com.arf.relievedassist.service.IRelievedAssistUserRecordService;

@Service("relievedAssistUserRecordService")
public class RelievedAssistUserRecordServiceImpl extends BaseServiceImpl<RelievedAssistUserRecord, Long> implements IRelievedAssistUserRecordService{

	@Resource(name="relievedAssistUserRecordDao")
	IRelievedAssistUserRecordDao relievedAssistUserRecordDao;

	@Override
	protected BaseDao<RelievedAssistUserRecord, Long> getBaseDao() {
		return relievedAssistUserRecordDao;
	}

	@Override
	public List<Map<String, Object>> findByUserList(List<String> userList) {
		return relievedAssistUserRecordDao.findByUserList(userList);
	}

	@Override
	public List<Map<String, Object>> findByCommunityNumber(String communityNumber) {
		return relievedAssistUserRecordDao.findByCommunityNumber(communityNumber);
	}

	@Override
	public List<RelievedAssistUserRecord> findByUserAndCommunity(String userName, String communityNumber,Boolean isMain) {
		return relievedAssistUserRecordDao.findByUserAndCommunity(userName, communityNumber, isMain);
	}

	@Override
	public RelievedAssistUserRecord findUserByCommUser(String communityNumber, String userName) {
		return relievedAssistUserRecordDao.findUserByCommUser(communityNumber, userName);
	}

	@Override
	public List<RelievedAssistUserRecord> findByUsers(List<String> userList) {
		return relievedAssistUserRecordDao.findByUsers(userList);
	}

	@Override
	public List<RelievedAssistUserRecord> findByUserAndCommunity(String userName, String communityNumber) {
		return relievedAssistUserRecordDao.findByUserAndCommunity(userName,communityNumber);
	}
	
}
