package com.arf.axdshopkeeper.shopkeeperrecommend.dao;

import java.util.Map;

import com.arf.axdshopkeeper.shopkeeperrecommend.entity.CofComment;
import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;

public interface ICofCommentDao extends BaseDao<CofComment,Long> {

	/**
	 * 查询评论列表
	 * @param pageNo
	 * @param pageSize
	 * @param recommendId
	 * @return
	 */
	public PageResult<CofComment> findByRecommendIdListPage(Integer pageNo,Integer pageSize, Long recommendId);
	
	/**
	 * 查询评论列表含用户头像姓名
	 * @param pageNo
	 * @param pageSize
	 * @param recommendId
	 * @return
	 */
	public PageResult<Map<String,Object>> findMoreByRecommendIdListPage(Integer pageNo,Integer pageSize, Long recommendId);
	
	/**
	 * 统计评论数
	 * @param recommendId
	 * @return
	 */
	public Integer countByRecommendId(Long recommendId);
}
