package com.arf.smart.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.smart.entity.PCityCustomFunctionRecord;

public interface IPCityCustomFunctionRecordService extends BaseService<PCityCustomFunctionRecord, Long>{
	
	/**
	 * 根据城市编号查询城市自定义图标排序
	 * @param cityCode
	 * @return
	 */
	public List<PCityCustomFunctionRecord> findByCityCode(String cityCode);

}
