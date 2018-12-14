package com.arf.relievedassist.dao;

import java.util.List;
import java.util.Map;

import com.arf.core.dao.BaseDao;
import com.arf.relievedassist.entity.RelievedAssistCommentRecord;
import com.arf.relievedassist.entity.RelievedAssistCommentRecord.Status;

public interface IRelievedAssistCommentRecordDao extends BaseDao<RelievedAssistCommentRecord, Long>{
	/**
	 * 根据帖子id集合查询
	 * @param contentIdList
	 * @return
	 */
	public List<Map<String, Object>> findByContentIds(List<Long> contentIdList);
	
	/**
	 * 批量更新评论状态
	 * @param contentId
	 */
	public void updateStatusById(List<Long> idList,Status status);
	
	/**
	 * 根据帖子id查询
	 * @param contentIdList
	 * @return
	 */
	public List<RelievedAssistCommentRecord> findByContentId(Long contentId);
	
	/**
	 * 根据评论id查询
	 * @param commentId
	 * @return
	 */
	public List<RelievedAssistCommentRecord> findByCommentId(Long commentId);
}
