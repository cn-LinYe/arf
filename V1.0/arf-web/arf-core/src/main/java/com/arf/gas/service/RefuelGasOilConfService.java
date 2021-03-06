package com.arf.gas.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.gas.entity.RefuelGasOilConf;
import com.arf.gas.entity.RefuelGasOilConf.RetailWholesale;

public interface RefuelGasOilConfService extends BaseService<RefuelGasOilConf, Long>{

	/**
	 * 查找商户批发或零售所有油类型的油品价格 
	 * @param gasNum
	 * @param businessNum
	 * @param retailWholesale
	 * @return
	 */
	public List<RefuelGasOilConf> findAllOilType(Integer gasNum,Integer businessNum,RetailWholesale retailWholesale,List<Byte> oilTypeList);
	
	/**
	 * 将有效期过了的油品价格状态改为已过期
	 * @return
	 */
	public int updateStatus();
}
