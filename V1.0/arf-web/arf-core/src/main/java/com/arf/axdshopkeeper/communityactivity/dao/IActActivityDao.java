package com.arf.axdshopkeeper.communityactivity.dao;

import java.util.List;

import com.arf.axdshopkeeper.communityactivity.entity.ActActivity;
import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;

public interface IActActivityDao extends BaseDao<ActActivity, Long> {

	PageResult<ActActivity> findListByCondition(
			String communityNumber, Integer pageNo, Integer pageSize,
			boolean findTimeout);

	List<ActActivity> findByKeyword(String keyword, String communityNumber);

}
