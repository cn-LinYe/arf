package com.arf.carbright.dao;

import java.util.List;

import com.arf.carbright.entity.DiscountUsetime;
import com.arf.carbright.entity.DiscountUsetime.DiscountFrequency;
import com.arf.core.dao.BaseDao;

public interface IDiscountUsetimeDao extends BaseDao<DiscountUsetime, Long>{

	/**
	 * 根据优惠id和优惠类型查找优惠时间
	 * @param id
	 * @param frequency
	 * @return
	 */
	List<DiscountUsetime> findByDiscountFrequency(Long id,Integer frequency);
}
