package com.arf.eparking.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.eparking.entity.OrderfeeRuleItem;
import com.arf.eparking.entity.OrderfeeRuleItem.Items;
import com.arf.eparking.entity.OrderfeeRuleItem.Use;

public interface OrderfeeRuleItemService extends BaseService<OrderfeeRuleItem, Long>{

	/**
	 * 通过停车场id,是否启用,配置名 查询预定费用相关限制配置项子项表
	 * @see {@link com.arf.eparking.service.OrderfeeRuleItemService.findByParkingIdUse(String, Integer, Items)}
	 * @param parkingId
	 * @param use 为空查询可用/不可用 {@link com.arf.eparking.entity.OrderfeeRuleItem.Use}
	 * @return
	 */
	@Deprecated
	OrderfeeRuleItem findByParkingIdUse(String parkingId,Integer use,String key);
	
	/**
	 * 通过停车场id,是否启用,配置名 查询预定费用相关限制配置项子项表
	 * @param parkingId
	 * @param use 为空查询可用/不可用 {@link com.arf.eparking.entity.OrderfeeRuleItem.Use}
	 * @return
	 */
	OrderfeeRuleItem findByParkingIdUse(String parkingId,Use use, Items items);
	
	/**
	 * 通过停车场ID查询该停车场所有的配置项
	 * @param parkingId
	 * @param use
	 * @return
	 */
	List<OrderfeeRuleItem>  findByParkingIdUse(String parkingId,Use use);

}
