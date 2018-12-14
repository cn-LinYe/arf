package com.arf.carbright.dao;

import java.util.Map;

import com.arf.carbright.entity.BusinessToCommunity;
import com.arf.core.dao.BaseDao;

public interface IBusinessToCommunityDao extends BaseDao<BusinessToCommunity, Long> {
	

	/**
	 * 通过商户id查找
	 * @param bussinessNum
	 * @return
	 */
	public Map<String,Object> findByBusinessNum(String businessNum);
	
	/**
	 * 通过商户编号查找
	 * @return
	 */
	BusinessToCommunity findByBusinessId(Long id);
	
}
