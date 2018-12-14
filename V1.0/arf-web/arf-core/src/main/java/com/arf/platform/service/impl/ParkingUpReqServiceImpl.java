package com.arf.platform.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arf.platform.LPConstants;
import com.arf.platform.service.BaseService;
import com.arf.platform.service.IParkingUpReportBusinessDealService;
import com.arf.platform.service.IParkingUpReqService;
import com.arf.platform.vo.ArriveReportVo;
import com.arf.platform.vo.LeaveReportVo;
import com.arf.platform.vo.ModifyArrivedLicenseVo;
import com.arf.platform.vo.ParkingStatusListVo;
import com.arf.platform.vo.ParkingStatusVo;
import com.arf.platform.vo.QueryCarInfoVo;

import net.sf.json.JSONArray;

/**
 * <b>类描述：</b>
 * 处理停车场的上行请求的服务类（此服务中不做事物的处理）
 * @author arf
 * @version 1.0
 *
 */
@Service("parkingUpReqServiceImpl")
public class ParkingUpReqServiceImpl extends BaseService implements IParkingUpReqService {
	
	private static Logger log = LoggerFactory.getLogger(ParkingUpReqServiceImpl.class);
	
	/**
	 * 停车场实时状态汇报业务处理类
	 */
	@Resource(name = "parkingStatusReportBusinessDealServiceImpl")
	private IParkingUpReportBusinessDealService parkingStatusReportBusinessDealServiceImpl;
	
	/**
	 * 来车汇报业务处理类
	 */
	@Resource(name = "arriveReportBusinessDealServiceImpl")
	private IParkingUpReportBusinessDealService arriveReportBusinessDealServiceImpl;
	
	/**
	 * 来车汇报业务处理类
	 */
	@Resource(name = "leaveReportBusinessDealServiceImpl")
	private IParkingUpReportBusinessDealService leaveReportBusinessDealServiceImpl;
	
	/**
	 * 车辆信息查询业务处理类
	 */
	@Resource(name = "queryCarInfoBusinessDealServiceImpl")
	private IParkingUpReportBusinessDealService queryCarInfoBusinessDealServiceImpl;
	
	/**
	 * 车辆信息查询业务处理类
	 */
	@Resource(name = "modifyArrivedLicenseBusinessDealServiceImpl")
	private IParkingUpReportBusinessDealService modifyArrivedLicenseBusinessDealServiceImpl;
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public Map<String, Object> dealParkingStatusReport(ParkingStatusListVo listVo,HttpServletRequest request) {
		Map<String, Object> responseMsg = null;
		log.info(">>>>>:[停车场实时状态汇报接口dealParkingStatusReport]请求参数: " + listVo.getParkingStatusListVo());
		
		//1、解析json数据
		String jsonData = listVo.getParkingStatusListVo();
		List<ParkingStatusVo> vo = new ArrayList<ParkingStatusVo>();
		if (!StringUtils.isBlank(jsonData)) {
			try {
				JSONArray array = JSONArray.fromObject(jsonData);
				vo = (List<ParkingStatusVo>) JSONArray.toCollection(array, ParkingStatusVo.class);
			} catch (Exception e) {
				log.error(">>>>>:[停车场实时状态汇报接口dealParkingStatusReport]解析返回数据异常", e);
				responseMsg = new HashMap<String,Object>();
				responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_DATA_ERROR);
				responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Data Error!");
				responseMsg.put(LPConstants.RETURNKEY_FAILED_LIST,null);
				return responseMsg;
			}
        }
		listVo.setVo(vo);
		//2、数据验证 (只将解析后的数据传入验证) 数据验证放入到process(String version, String communityNo, RequestDataVo vo)里
//		responseMsg = checkData(BaseService.PARKINGUPREQ_TYPE_STATUS,vo);
//		if(responseMsg != null){
//			return responseMsg;
//		}
		//3、数据业务处理
		responseMsg = parkingStatusReportBusinessDealServiceImpl.process(null, null, listVo);
		
		if(responseMsg == null){
			responseMsg = new HashMap<String,Object>();
			responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SERVER_ERROR);
			responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Server Error!");
			responseMsg.put(LPConstants.RETURNKEY_FAILED_LIST,null);
		}
		return responseMsg;
	}


	@Override
	public Map<String, Object> dealArriveReport(ArriveReportVo vo,HttpServletRequest request) {
		Map<String, Object> responseMsg = null;
		//1、数据验证
		log.info(">>>>>:[停车场来车汇报 dealArriveReport]请求参数: " + vo);
		responseMsg = checkData(BaseService.PARKINGUPREQ_TYPE_CAR_ARRIVE,vo);
		if(responseMsg != null){
			return responseMsg;
		}
		//2、数据业务处理
		responseMsg = arriveReportBusinessDealServiceImpl.process(vo.getVer(), vo.getCommunityNo(), vo);
		
		if(responseMsg == null){
			responseMsg = new HashMap<String,Object>();
			responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SERVER_ERROR);
			responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Server Error!");
			responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
		}
		return responseMsg;
	}

	@Override
	public Map<String, Object> dealLeaveReport(LeaveReportVo vo,HttpServletRequest request) {
		Map<String, Object> responseMsg = null;
		//1、数据验证
		log.info(">>>>>:[停车场走车汇报dealLeaveReport]请求参数 : " + vo);
		responseMsg = checkData(BaseService.PARKINGUPREQ_TYPE_CAR_LEAVE,vo);
		if(responseMsg != null){
			return responseMsg;
		}
		
		//2、数据业务处理
		responseMsg = leaveReportBusinessDealServiceImpl.process(vo.getVer(), vo.getCommunityNo(), vo);
		
		if(responseMsg == null){
			responseMsg = new HashMap<String,Object>();
			responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SERVER_ERROR);
			responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Server Error!");
			responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
		}
		return responseMsg;
	}

	@Override
	public Map<String, Object> dealQueryCarInfo(QueryCarInfoVo vo, HttpServletRequest request) {
		Map<String, Object> responseMsg = null;
		//测试用，打印接收到的数据
		log.info("===== 车辆信息查询dealQueryCarInfo请求参数 : " + vo);
		//1、数据验证
		responseMsg = checkData(BaseService.PARKINGUPREQ_TYPE_QUERY_CAR_INFO,vo);
		if(responseMsg != null){
			return responseMsg;
		}
		//2、数据业务处理
		responseMsg = queryCarInfoBusinessDealServiceImpl.process(vo.getVer(), vo.getCommunityNo(), vo);
		
		if(responseMsg == null){
			responseMsg = new HashMap<String,Object>();
			responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SERVER_ERROR);
			responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Server Error!");
			responseMsg.put(LPConstants.RETURNKEY_CAR_LICENSE, vo.getLicense());
			responseMsg.put(LPConstants.RETURNKEY_CAR_ACCOUNT, null);
			responseMsg.put(LPConstants.RETURNKEY_CAR_ESCAPE, null);
			responseMsg.put(LPConstants.RETURNKEY_CAR_EXCEPTION, null);
		}
		return responseMsg;
	}


	@Override
	public Map<String, Object> modifyArrivedLicense(ModifyArrivedLicenseVo vo,HttpServletRequest request) {
		Map<String, Object> responseMsg = null;
		log.info(">>>>>:[修改场内车牌接口modifyArrivedLicense]请求参数 : " + vo);
		responseMsg = checkData(BaseService.PARKINGUPREQ_TYPE_MODIFY_ARRIVED_LICENSE,vo);
		if(responseMsg != null){
			return responseMsg;
		}
		responseMsg = modifyArrivedLicenseBusinessDealServiceImpl.process(vo.getVer(), vo.getCommunityNo(), vo);
		if(responseMsg == null){
			responseMsg = new HashMap<String,Object>();
			responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SERVER_ERROR);
			responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Server Error!");
			responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
		}
		return responseMsg;
	}

}
