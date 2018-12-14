package com.arf.laundry.dao;

import com.arf.core.dao.BaseDao;
import com.arf.laundry.entity.LaundryCategoryPriceTpl;

public interface ILaundryCategoryPriceTplDao extends BaseDao<LaundryCategoryPriceTpl, Long> {

	LaundryCategoryPriceTpl findByCategoryNum(String categoryNum);
}
