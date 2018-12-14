package com.arf.platform.service;

import java.util.Map;

import com.arf.platform.vo.RequestDataVo;

/**
 * 停车场数据汇报的业务处理服务
 * @author dengsongping
 *
 */
public interface IParkingUpReportBusinessDealService {

	/**
	 * 停车场数据汇报的业务处理<br>
	 * @param version 版本号
	 * @param communityNo 小区编号
	 * @param dataMap
	 */
	Map<String, Object> process(String version, String communityNo, RequestDataVo vo);
	
}
