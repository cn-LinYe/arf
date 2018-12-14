package com.arf.activity.childrensday.dao;

import java.util.List;

import com.arf.activity.childrensday.entity.ActivityChildren61Works;
import com.arf.core.Page;
import com.arf.core.dao.BaseDao;

public interface IActivityChildren61WorksDao extends
		BaseDao<ActivityChildren61Works, Long> {

	ActivityChildren61Works findByWorksNum(Integer worksNum);

	/**
	 * 按照 worksNum authorName 分页模糊查询
	 * @param pageNo
	 * @param pageSize
	 * @param keyword
	 * @param orderBy 0 排序枚举:WORKS_NUM_ASC 1 编号升序;RANK_DESC 排名降序 活动过期默认按RANK_DESC 排名降序
	 * @return
	 */
	Page<ActivityChildren61Works> findByCondition(Integer pageNo,
			Integer pageSize, String keyword,Integer orderBy);

	/**
	 * 排名降序
	 * @return
	 */
	List<ActivityChildren61Works> findAllByOrder();

	
	
}
