package com.arf.salesman.dao;

import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.salesman.entity.SalesmanCommunity;

public interface ISalesmanCommunityDao extends BaseDao<SalesmanCommunity, Long> {

	
	/**
	 * 通过用户名查找小区以及小区状态
	 * @param userName
	 * @return
	 */
	PageResult<Map<String,Object>> findByUserName(String userName,Integer pageNo,Integer pageSize);
	
	/**
	 * 通过用户名小区查找
	 * @return
	 */
	SalesmanCommunity findByUserNameCommunity(String communityNum,String userName);
}
