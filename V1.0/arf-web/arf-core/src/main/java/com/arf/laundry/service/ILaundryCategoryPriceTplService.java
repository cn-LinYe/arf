package com.arf.laundry.service;

import com.arf.core.service.BaseService;
import com.arf.laundry.entity.LaundryCategoryPriceTpl;

public interface ILaundryCategoryPriceTplService extends BaseService<LaundryCategoryPriceTpl, Long> {

	/**
	 * 根据类目编号查询
	 * @param categoryNum
	 * @return
	 */
	LaundryCategoryPriceTpl findByCategoryNum(String categoryNum);
}
