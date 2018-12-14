package com.arf.carbright.service;

import java.util.List;

import com.arf.carbright.entity.PUserRange;
import com.arf.core.AppMessage;
import com.arf.core.service.BaseService;

public interface PUserRangService extends BaseService<PUserRange, Long> {

	/**
	 * 分小区查看套餐类型
	 * @param parkingId
	 * @return
	 */
	public AppMessage selectPackage(String parkingId);
	
	/**
	 * 通过范围编码查找
	 * @param rangeNum
	 * @return
	 */
	public PUserRange findByRangeNum(String rangeNum);
	
	/**通过商户ID查符合的套餐
	 * @param businessId
	 * @return
	 */
	public List<PUserRange> findbyBusinessId(int businessId);
}
