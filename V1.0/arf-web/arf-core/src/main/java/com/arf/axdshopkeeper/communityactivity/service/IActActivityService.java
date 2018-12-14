package com.arf.axdshopkeeper.communityactivity.service;

import java.util.List;

import com.arf.axdshopkeeper.communityactivity.entity.ActActivity;
import com.arf.base.PageResult;
import com.arf.core.service.BaseService;

public interface IActActivityService extends BaseService<ActActivity, Long> {

	/**
	 * 根据小区编号、分页条件，查询正常的
	 * @param communityNumber
	 * @param pageNo
	 * @param pageSize
	 * @param findTimeout true 查询过期的 false 不查询过期的
	 * @return
	 */
	PageResult<ActActivity> findListByCondition(
			String communityNumber, Integer pageNo, Integer pageSize,boolean findTimeout);

	/**
	 * 用于安心点首页关键字、小区查询
	 * @param keyword
	 * @param communityNumber
	 * @return
	 */
	List<ActActivity> findByKeyword(String keyword, String communityNumber);

}
