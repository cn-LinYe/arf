package com.arf.platform.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.platform.entity.SExceptionCarInfo;

public interface ISExceptionCarInfoDao extends BaseDao<SExceptionCarInfo, Long>{

	/**
	 * 根据车牌号查询 异常车辆表 s_exception_car_info
	 * @param license
	 * @return
	 */
	List<SExceptionCarInfo> findByLicense(String license);

}
