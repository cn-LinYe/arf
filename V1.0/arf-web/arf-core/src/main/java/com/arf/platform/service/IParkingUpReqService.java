package com.arf.platform.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.arf.platform.vo.ArriveReportVo;
import com.arf.platform.vo.LeaveReportVo;
import com.arf.platform.vo.ModifyArrivedLicenseVo;
import com.arf.platform.vo.ParkingStatusListVo;
import com.arf.platform.vo.QueryCarInfoVo;

/**
 * <b>类描述：</b>停车场上行请求业务处理服务<br/>
 * <b>说明：</b>停车场上行数据的请求，不在业务层抛出异常，全部异常信息在实现类中处理；
 * 返回的结果全部为Map,Map中存放(ret:返回值和msg:错误信息),错误信息全部以英文形式提供
 * @author dengsongping
 *
 */
public interface IParkingUpReqService {

	/**
	 * 处理停车场实时状态汇报<br>
     * dealParkingStatusReport
	 * @param vo 请求参数包装类
	 * @param request 整个request对象
	 * @return Map<String,String>
	 */
	Map<String, Object> dealParkingStatusReport(ParkingStatusListVo vo,HttpServletRequest request);

	/**
	 * 处理来车汇报<br>
     * dealArriveReport 
     * @param vo 请求参数包装类
	 * @param request 整个request对象
	 * @return Map<String,String>
	 */
	Map<String, Object> dealArriveReport(ArriveReportVo vo,HttpServletRequest request);

	/**
	 * 处理走车汇报<br>
     * dealLeaveReport
     * @param vo 请求参数包装类
	 * @param request 整个request对象
	 * @return Map<String,String>
	 */
	Map<String, Object> dealLeaveReport(LeaveReportVo vo,HttpServletRequest request);

	/**
	 * 处理车辆信息查询<br>
     * dealQueryCarInfo
     * @param vo 请求参数包装类
	 * @param request 整个request对象
	 * @return Map<String,String>
	 */
	Map<String, Object> dealQueryCarInfo(QueryCarInfoVo vo, HttpServletRequest request);

	/**
	 * 修改场内车牌接口
	 * @param vo
	 * @param request
	 * @return
	 */
	Map<String, Object> modifyArrivedLicense(ModifyArrivedLicenseVo vo,HttpServletRequest request);
	
}
