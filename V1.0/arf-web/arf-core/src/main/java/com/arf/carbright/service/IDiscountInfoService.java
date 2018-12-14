package com.arf.carbright.service;

import com.arf.carbright.entity.DiscountInfo;
import com.arf.core.service.BaseService;

public interface IDiscountInfoService extends BaseService<DiscountInfo, Long>{
	
	/**
	 * 根据商户编号查找优惠
	 * @param businessNum
	 * @param status
	 * @return
	 */
	DiscountInfo findByBusinessNum(Integer businessNum,DiscountInfo.Status status);

}
