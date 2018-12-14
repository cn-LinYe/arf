package com.arf.relievedassist.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.relievedassist.dao.IRelievedAssistCollectionPraiseBrowseRecordDao;
import com.arf.relievedassist.entity.RelievedAssistCollectionPraiseBrowseRecord;
import com.arf.relievedassist.entity.RelievedAssistCollectionPraiseBrowseRecord.CollectionStatus;
import com.arf.relievedassist.entity.RelievedAssistCollectionPraiseBrowseRecord.Type;
import com.arf.relievedassist.service.IRelievedAssistCollectionPraiseBrowseRecordService;

@Service("relievedAssistCollectionPraiseBrowseRecordService")
public class RelievedAssistCollectionPraiseBrowseRecordServiceImpl extends BaseServiceImpl<RelievedAssistCollectionPraiseBrowseRecord, Long> implements IRelievedAssistCollectionPraiseBrowseRecordService{

	@Resource(name="relievedAssistCollectionPraiseBrowseRecordDao")
	IRelievedAssistCollectionPraiseBrowseRecordDao relievedAssistCollectionPraiseBrowseRecordDao;

	@Override
	protected BaseDao<RelievedAssistCollectionPraiseBrowseRecord, Long> getBaseDao() {
		return relievedAssistCollectionPraiseBrowseRecordDao;
	}

	@Override
	public List<RelievedAssistCollectionPraiseBrowseRecord> findByUserAndContentId(List<Long> contentIdList,
			List<String> userList,Type type) {
		return relievedAssistCollectionPraiseBrowseRecordDao.findByUserAndContentId(contentIdList, userList, type);
	}

	@Override
	public PageResult<Map<String, Object>> getSortContentId(List<Long> contentIdList, Type type,Integer pageNo,Integer pageSize) {
		return relievedAssistCollectionPraiseBrowseRecordDao.getSortContentId(contentIdList, type, pageNo, pageSize);
	}

	@Override
	public RelievedAssistCollectionPraiseBrowseRecord findByUserAndContentIdAndType(Long contentId,
			String userName, Type type, Long commentId) {
		return relievedAssistCollectionPraiseBrowseRecordDao.findByUserAndContentIdAndType(contentId, userName, type, commentId);
	}

	@Override
	public List<RelievedAssistCollectionPraiseBrowseRecord> findByContentId(Long contentId) {
		return relievedAssistCollectionPraiseBrowseRecordDao.findByContentId(contentId);
	}

	@Override
	public void updateStatusById(List<Long> idList, CollectionStatus status) {
		relievedAssistCollectionPraiseBrowseRecordDao.updateStatusById(idList, status);
	}

	@Override
	public List<RelievedAssistCollectionPraiseBrowseRecord> findByCommentId(Long commentId) {
		return relievedAssistCollectionPraiseBrowseRecordDao.findByCommentId(commentId);
	}
}
