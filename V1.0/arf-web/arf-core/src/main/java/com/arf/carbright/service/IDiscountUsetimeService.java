package com.arf.carbright.service;

import java.util.List;

import com.arf.carbright.entity.DiscountUsetime;
import com.arf.core.service.BaseService;

public interface IDiscountUsetimeService extends BaseService<DiscountUsetime, Long>{
	
	/**
	 * 根据优惠id和优惠类型查找优惠时间
	 * @param id
	 * @param frequency
	 * @return
	 */
	List<DiscountUsetime> findByDiscountFrequency(Long id,Integer frequency);
}
