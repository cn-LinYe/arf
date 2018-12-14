package com.arf.review.service;

import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.service.BaseService;
import com.arf.review.entity.ServiceReview;
import com.arf.review.entity.ServiceReview.Type;

public interface ServiceReviewService extends BaseService<ServiceReview, Long>{

	/**
	 * 根据订单编号查询评论
	 * @param orderNo
	 * @return
	 */
	List<ServiceReview> findByOutTradeNo(String orderNo);
	
	/**
	 * 通过订单编号集合查询
	 * @param orderNo
	 * @return
	 */
	List<ServiceReview> findByOutTradeNos(List<Object> orderNos);

	/**
	 * 根据订单编号和上一评论查询
	 * @param outTradeNo
	 * @param forReview 为空即查找第一条评论
	 * @return
	 */
	ServiceReview findByOutTradeNoAndForReview(String outTradeNo, ServiceReview forReview);
	
	/**
	 * 查看评论方法
	 * @param orderNo
	 * @param score
	 * @param type
	 * @param pageSize
	 * @param pageNo
	 * @param isImages
	 * @param anonymous
	 * @param businessNum
	 * @param userName
	 * @return
	 */
	PageResult<Map<String,Object>> findReviewInfo(String orderNo,Integer score,Type type,Integer pageSize,Integer pageNo,Integer isImages,Integer anonymous,Integer businessNum,String userName);

	/**
	 * 查看带图片数量
	 * @return
	 */
	 Map<String,Object> selectCountImages(String orderNo, Integer score, Type type, Integer isImages, Integer anonymous, Integer businessNum, String userName);
	/**
	 * 根据评分查看数量
	 */
	Map<String, Object> findCountByScores(String orderNo, Integer score, Type type, Integer isImages, Integer anonymous, Integer businessNum, String userName);

}
