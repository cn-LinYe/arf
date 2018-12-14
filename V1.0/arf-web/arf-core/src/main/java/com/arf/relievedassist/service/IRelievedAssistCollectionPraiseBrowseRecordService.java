package com.arf.relievedassist.service;

import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.service.BaseService;
import com.arf.relievedassist.entity.RelievedAssistCollectionPraiseBrowseRecord;
import com.arf.relievedassist.entity.RelievedAssistCollectionPraiseBrowseRecord.CollectionStatus;
import com.arf.relievedassist.entity.RelievedAssistCollectionPraiseBrowseRecord.Type;

public interface IRelievedAssistCollectionPraiseBrowseRecordService extends BaseService<RelievedAssistCollectionPraiseBrowseRecord, Long>{
	/**
	 * 根据帖子id集合和用户名集合查询
	 * @param contentIdList
	 * @param userList
	 * @return
	 */
	public List<RelievedAssistCollectionPraiseBrowseRecord> findByUserAndContentId(List<Long> contentIdList,
			List<String> userList,Type type);
	
	/**
	 * 根据帖子id集合查询排序
	 * @param contentIdList
	 * @param type
	 * @return
	 */
	public PageResult<Map<String, Object>> getSortContentId(List<Long> contentIdList,Type type,Integer pageNo,Integer pageSize);
	
	/**
	 * 根据帖子id和用户名和类型查询
	 * @param contentId
	 * @param userName
	 * @param commentId
	 * @param type
	 * @return
	 */
	public RelievedAssistCollectionPraiseBrowseRecord findByUserAndContentIdAndType(Long contentId,String userName,
			Type type,Long commentId);
	
	/**
	 * 根据帖子id查询
	 * @param contentId
	 * @return
	 */
	public List<RelievedAssistCollectionPraiseBrowseRecord> findByContentId(Long contentId);
	
	/**
	 * 批量更新点赞收藏状态
	 * @param contentId
	 */
	public void updateStatusById(List<Long> idList,CollectionStatus status);
	
	/**
	 * 根据评论id查询
	 * @param commentId
	 * @return
	 */
	public List<RelievedAssistCollectionPraiseBrowseRecord> findByCommentId(Long commentId);
}
