package com.arf.carbright.service;

import java.util.Map;

import com.arf.carbright.entity.BusinessToCommunity;
import com.arf.core.service.BaseService;

public interface IPbusinessToCommunityService extends BaseService<BusinessToCommunity, Long> {
	

	/**
	 * 通过商户id查找
	 * @param bussinessNum
	 * @return
	 */
	Map<String,Object> findByBusinessNum(String businessNum);
	
	/**
	 * 通过小区编号查询
	 * @param businessNum
	 * @return
	 */
	BusinessToCommunity findRedisByBusinessId(Long id);
	
	/**
	 * 通过商户编号查找
	 * @return
	 */
	BusinessToCommunity findByBusinessId(Long id);
}
