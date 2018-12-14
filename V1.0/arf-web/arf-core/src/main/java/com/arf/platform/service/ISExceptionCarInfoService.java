package com.arf.platform.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.platform.entity.SExceptionCarInfo;

public interface ISExceptionCarInfoService extends BaseService<SExceptionCarInfo, Long> {

	/**
	 * 根据车牌号查询 异常车辆表 s_exception_car_info
	 * @param license
	 * @return
	 */
	List<SExceptionCarInfo> findByLicense(String license);

	
}
