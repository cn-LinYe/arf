package com.arf.carbright.dao;

import com.arf.carbright.entity.DiscountInfo;
import com.arf.core.dao.BaseDao;

public interface IDiscountInfoDao extends BaseDao<DiscountInfo, Long>{
	/**
	 * 根据商户编号查找优惠
	 * @param businessNum
	 * @param status
	 * @return
	 */
	DiscountInfo findByBusinessNum(Integer businessNum,DiscountInfo.Status status);
}
