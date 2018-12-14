package com.arf.relievedassist.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.relievedassist.dao.IRelievedAssistContentRecordDao;
import com.arf.relievedassist.entity.RelievedAssistContentRecord;
import com.arf.relievedassist.service.IRelievedAssistContentRecordService;

@Service("relievedAssistContentRecordService")
public class RelievedAssistContentRecordServiceImpl extends BaseServiceImpl<RelievedAssistContentRecord, Long> implements IRelievedAssistContentRecordService{

	@Resource(name="relievedAssistContentRecordDao")
	IRelievedAssistContentRecordDao relievedAssistContentRecordDao;

	@Override
	protected BaseDao<RelievedAssistContentRecord, Long> getBaseDao() {
		return relievedAssistContentRecordDao;
	}

	@Override
	public PageResult<Map<String, Object>> findListByCondition(String userName, String communityNumber, Date startTime,
			Date endTime, Long classifyId, Integer pageNo, Integer pageSize,Integer orderType) {
		return relievedAssistContentRecordDao.findListByCondition(userName, communityNumber, startTime, endTime, 
				classifyId, pageNo, pageSize, orderType);
	}

	@Override
	public PageResult<Map<String, Object>> findByIds(String communityNumber,List<Long> idList,Integer pageNo,Integer pageSize,Integer orderType) {
		return relievedAssistContentRecordDao.findByIds(communityNumber,idList, pageNo, pageSize, orderType);
	}

	@Override
	public List<RelievedAssistContentRecord> findByUserName(String userName) {
		return relievedAssistContentRecordDao.findByUserName(userName);
	}
	
}
