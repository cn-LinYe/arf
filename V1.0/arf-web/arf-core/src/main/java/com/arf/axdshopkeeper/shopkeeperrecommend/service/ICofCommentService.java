package com.arf.axdshopkeeper.shopkeeperrecommend.service;

import java.util.Map;

import com.arf.axdshopkeeper.shopkeeperrecommend.entity.CofComment;
import com.arf.base.PageResult;
import com.arf.core.service.BaseService;

public interface ICofCommentService extends BaseService<CofComment, Long> {

	/**
	 * 根据推荐id获取评论列表
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
