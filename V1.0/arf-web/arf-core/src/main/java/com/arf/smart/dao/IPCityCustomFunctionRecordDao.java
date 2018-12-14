package com.arf.smart.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.smart.entity.PCityCustomFunctionRecord;

public interface IPCityCustomFunctionRecordDao extends BaseDao<PCityCustomFunctionRecord, Long>{
	
	/**
	 * 根据城市编号查询城市自定义图标排序
	 * @param cityCode
	 * @return
	 */
	public List<PCityCustomFunctionRecord> findByCityCode(String cityCode);

}
