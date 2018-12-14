package com.arf.axdshopkeeper.shopkeeperrecommend.service;

import java.util.List;
import java.util.Map;

import com.arf.axdshopkeeper.shopkeeperrecommend.entity.CofRecommend;
import com.arf.base.PageResult;
import com.arf.core.service.BaseService;

public interface ICofRecommendService extends BaseService<CofRecommend,Long> {

	/**
	 * 店长推荐首页查询接口（店长推荐+总部推荐最近一条）
	 * @param communityNumber
	 * @return
	 */
	public CofRecommend findLatest(String communityNumber);
	
	/**
	 * 查询店长推荐列表,根据用户名判断用户是否有点赞收藏操作（分页）
	 * @param pageSize
	 * @param pageNo
	 * @param userName
	 * @param communityNumber
	 * @param shopkeeperUserName
	 * @return
	 */
	public PageResult<Map<String, Object>> findExtraByCommunityNumberListPage(Integer pageSize,Integer pageNo,String userName,
			String communityNumber,String shopkeeperUserName);
	
	/**
	 * 店长推荐列表查询（含总部推荐）
	 * 根据小区编码，查询此小区的推荐列表，列表中包含店长推荐和总部推荐，根据时间顺序倒叙（分页）
	 * @param pageSize
	 * @param pageNo
	 * @param communityNumber
	 * @return
	 */
	public PageResult<CofRecommend> findByCommunityNumberListPage(Integer pageSize,Integer pageNo,String communityNumber,String shopkeeperUserName);
	
	/**
	 * 店长推荐详情 
	 * 根据推荐id,查询当前店长推荐详情，
	 * @param id
	 * @return
	 */
	public CofRecommend findById(Long id);
	
	/**
	 * 店长推荐详情,根据用户名判断用户是否有点赞收藏操作
	 * @param id
	 * @param userName
	 * @return
	 */
	public Map<String,Object> findMapByIdUserName(Long id,String userName);
	
	/**
	 * 根据关键字搜索推荐内容
	 * @param keyword
	 * @param communityNumber
	 * @param limit 返回限制的条数
	 * @return
	 */
	public List<CofRecommend> findByKeyword(String keyword, String communityNumber,int limit);

	/**
	 * 获取下一篇推荐
	 * @param recommendId
	 * @param userName
	 * @param communityNumber
	 * @return
	 */
	public Map<String, Object> findNextRecommendByIdUserNameCommunityNumber(
			Long recommendId, String userName, String communityNumber);
}
