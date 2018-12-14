package com.arf.relievedassist.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.relievedassist.dao.IRelievedAssistCommentRecordDao;
import com.arf.relievedassist.entity.RelievedAssistCommentRecord;
import com.arf.relievedassist.entity.RelievedAssistCommentRecord.Status;
import com.arf.relievedassist.service.IRelievedAssistCommentRecordService;

@Service("relievedAssistCommentRecordService")
public class RelievedAssistCommentRecordServiceImpl extends BaseServiceImpl<RelievedAssistCommentRecord, Long> implements IRelievedAssistCommentRecordService{

	@Resource(name="relievedAssistCommentRecordDao")
	IRelievedAssistCommentRecordDao relievedAssistCommentRecordDao;

	@Override
	protected BaseDao<RelievedAssistCommentRecord, Long> getBaseDao() {
		return relievedAssistCommentRecordDao;
	}

	@Override
	public List<Map<String, Object>> findByContentIds(List<Long> contentIdList) {
		return relievedAssistCommentRecordDao.findByContentIds(contentIdList);
	}

	@Override
	public void updateStatusById(List<Long> idList, Status status) {
		relievedAssistCommentRecordDao.updateStatusById(idList, status);
	}
	
	@Override
	public List<RelievedAssistCommentRecord> findByContentId(Long contentId) {
		return relievedAssistCommentRecordDao.findByContentId(contentId);
	}

	@Override
	public List<RelievedAssistCommentRecord> findByCommentId(Long commentId) {
		return relievedAssistCommentRecordDao.findByCommentId(commentId);
	}
	
}
