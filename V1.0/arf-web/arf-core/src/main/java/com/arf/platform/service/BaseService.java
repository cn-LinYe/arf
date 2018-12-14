package com.arf.platform.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;

import com.arf.core.cache.redis.RedisService;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.LicensePlateModel;
import com.arf.core.entity.Member;
import com.arf.core.oldws.PushUntils;
import com.arf.core.service.CommunityModelService;
import com.arf.core.service.LicensePlateModelService;
import com.arf.core.service.MemberService;
import com.arf.core.service.SmsService;
import com.arf.core.util.MStringUntils;
import com.arf.eparking.entity.ParkingInfo;
import com.arf.eparking.service.ParkingInfoService;
import com.arf.notice.NoticeSource;
import com.arf.notice.NoticeSource.NoticeType;
import com.arf.platform.LPConstants;
import com.arf.platform.entity.PParkingcar;
import com.arf.platform.entity.RStoprecord;
import com.arf.platform.vo.ArriveReportVo;
import com.arf.platform.vo.LeaveReportVo;
import com.arf.platform.vo.ModifyArrivedLicenseVo;
import com.arf.platform.vo.ParkingStatusVo;
import com.arf.platform.vo.QueryCarInfoVo;

public class BaseService {
	private static Logger log = LoggerFactory.getLogger(BaseService.class);
	@Resource(name = "communityModelServiceImpl")
	private CommunityModelService communityModelService;
	
	@Resource(name = "parkingInfoServiceImpl")
	private ParkingInfoService parkingInfoServiceImpl;
	
	@Resource(name = "smsServiceImpl")
	private SmsService smsService;
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Value("${member.balance.notify}")
	protected String balanceNotify;
	
	@Resource(name = "PParkingcarServiceImpl")
	private PParkingcarService pParkingcarServiceImpl;
	
	@Resource(name = "RStoprecordServiceImpl")
	private RStoprecordService rStoprecordServiceImpl;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "licensePlateModelServiceImpl")
	private LicensePlateModelService licensePlateModelService;
	
	/**
	 * 停车场上行请求类型：1 停车场实时状态汇报
	 */
	public static final Integer PARKINGUPREQ_TYPE_STATUS = 1;

	/**
	 * 停车场上行请求类型：2 来车汇报
	 */
	public static final Integer PARKINGUPREQ_TYPE_CAR_ARRIVE = 2;

	/**
	 * 停车场上行请求类型：3 走车汇报
	 */
	public static final Integer PARKINGUPREQ_TYPE_CAR_LEAVE = 3;
	
	/**
	 * 停车场上行请求类型：10 车辆信息查询
	 */
	public static final Integer PARKINGUPREQ_TYPE_QUERY_CAR_INFO = 10;
	
	/**
	 * 停车场上行请求类型：22 修改场内车牌接口
	 */
	public static final Integer PARKINGUPREQ_TYPE_MODIFY_ARRIVED_LICENSE = 22;
	
	public static final int PARKING_TYPE_COMMUNITY = 0;//停车场类型 小区
	public static final int PARKING_TYPE_EMERGENCY = 1;//停车场类型 紧急场所
	
	
	/**
	 * 数据验证 1 停车场实时状态汇报
	 * @param reportType	请求类型 1 停车场实时状态汇报 2 来车汇报 3 走车汇报 10 车辆信息查询 22 修改场内车牌接口
	 * @param listVo	请求数据（参数和业务数据）
	 * @return
	 */
	protected Map<String, Object> checkData(int reportType,List<ParkingStatusVo> listVo) {
		Map<String, Object> responseMsg = null;
		if(reportType == PARKINGUPREQ_TYPE_STATUS){
			//循环遍历传输过来的小区（停车场）实时状态
			for (ParkingStatusVo vo : listVo) {
				//1、是否为空？
				if(StringUtils.isBlank(vo.getVer()) 
						|| StringUtils.isBlank(vo.getCommunityNo()) 
						|| MStringUntils.isNullObject(vo.getTotal())
						|| MStringUntils.isNullObject(vo.getEmpty()) 
						|| MStringUntils.isNullObject(vo.getSheduled()) 
						|| MStringUntils.isNullObject(vo.getRscheduled())
						|| MStringUntils.isNullObject(vo.getInternal()) 
						|| MStringUntils.isNullObject(vo.getRinternal()) 
						|| MStringUntils.isNullObject(vo.getStatus())
						|| MStringUntils.isNullObject(vo.getParkingType())){
					log.info(">>>>>:[停车场实时状态汇报checkData]参数异常,请求参数:"+vo.toString());
					responseMsg = new HashMap<String,Object>();
					responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_PARAM_ERROR);
					responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Param Error!");
					responseMsg.put(LPConstants.RETURNKEY_FAILED_LIST,null);
					return responseMsg;
				}
				
				//2、小区是否存在？parkingType = 0时做验证
				if(vo.getParkingType() == PARKING_TYPE_COMMUNITY){
					try {
						CommunityModel community = getCommunity(vo.getCommunityNo());
						if(community == null){
//							log.info(">>>>>:[停车场实时状态汇报checkData]参数异常,小区不存在:"+vo.toString());
							responseMsg = new HashMap<String,Object>();
							responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_COMMUNITY_NO_EXIST);
							responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Community no exist!");
							responseMsg.put(LPConstants.RETURNKEY_FAILED_LIST,null);
							return responseMsg;
						}
						vo.setCommunity(community);
					} catch (Exception e) {
						log.error(String.format(">>>>>:[停车场实时状态汇报checkData]服务器异常vo = %s", vo.toString()), e);
						responseMsg = new HashMap<String,Object>();
						responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SERVER_ERROR);
						responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Server Error!");
						responseMsg.put(LPConstants.RETURNKEY_FAILED_LIST,null);
						return responseMsg;
					}
				}else if(vo.getParkingType() == PARKING_TYPE_EMERGENCY){//停车场是否存在？parkingType = 1时做验证
					try {
						ParkingInfo parkingInfo = parkingInfoServiceImpl.findByParkingNo(vo.getCommunityNo());
						if(parkingInfo == null){
//							log.info(">>>>>:[停车场实时状态汇报checkData]参数异常,停车场不存在:"+vo.toString());
							responseMsg = new HashMap<String,Object>();
							responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_COMMUNITY_NO_EXIST);
							responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Community no exist!");
							responseMsg.put(LPConstants.RETURNKEY_FAILED_LIST,null);
							return responseMsg;
						}
						vo.setParkingInfo(parkingInfo);
					} catch (Exception e) {
						log.error(String.format(">>>>>:[停车场实时状态汇报checkData]服务器异常vo = %s", vo.toString()), e);
						responseMsg = new HashMap<String,Object>();
						responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SERVER_ERROR);
						responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Server Error!");
						responseMsg.put(LPConstants.RETURNKEY_FAILED_LIST,null);
						return responseMsg;
					}
				}
			}
		}
		return responseMsg;
	}

	/**
	 * 数据验证 2 来车汇报
	 * @param reportType	请求类型 1 停车场实时状态汇报 2 来车汇报 3 走车汇报 10 车辆信息查询 22 修改场内车牌接口
	 * @param vo	请求数据（参数和业务数据）
	 * @return
	 */
	protected Map<String, Object> checkData(int reportType,ArriveReportVo vo) {
		Map<String, Object> responseMsg = null;
		if(reportType == PARKINGUPREQ_TYPE_CAR_ARRIVE){
			//1、是否为空？
			if(StringUtils.isBlank(vo.getVer()) 
					|| StringUtils.isBlank(vo.getCommunityNo()) 
					|| StringUtils.isBlank(vo.getLicense())
					|| StringUtils.isBlank(vo.getLicenseColor()) 
					|| StringUtils.isBlank(vo.getCarType()) 
					|| StringUtils.isBlank(vo.getArriveTime())
					|| StringUtils.isBlank(vo.getArriveCarImgUrl()) 
					|| StringUtils.isBlank(vo.getCarColor()) 
					|| MStringUntils.isNullObject(vo.getIsBook()) 
					|| MStringUntils.isNullObject(vo.getStopType())
					|| MStringUntils.isNullObject(vo.getParkingType())){
				responseMsg = new HashMap<String,Object>();
				responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_PARAM_ERROR);
				responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Param Error!");
				responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
				return responseMsg;
			}
			if(vo.getIsBook() == 1 ){
				if(StringUtils.isBlank(vo.getOrderSn())){
					responseMsg = new HashMap<String,Object>();
					responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_PARAM_ERROR);
					responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Param Error!");
					responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
					return responseMsg;
				}
			}
			
			//2、小区是否存在？ parkingType = 0时做验证
			if(vo.getParkingType() == PARKING_TYPE_COMMUNITY){
				try {
					CommunityModel community = getCommunity(vo.getCommunityNo());
					if(community == null){
						//尽管找不到小区，但还是保存来车记录
//						String license = vo.getLicense();//车牌号码，UTF8编码
//						String arriveTime = vo.getArriveTime();//时间戳(秒)
//						String berthNo = vo.getBerthNo();//车位编号，UTF8编码
//						Integer stopType = vo.getStopType();//停车类型 0月卡车,1临时车,2免费车,3电子账户车
//						String arriveCarImgUrl = vo.getArriveCarImgUrl();//来车图片url，utf8编码
//						PParkingcar pParkingcar = pParkingcarServiceImpl.findByCommLicArr(vo.getCommunityNo(), license, new Date(Long.valueOf(arriveTime)*1000l));
//						if(pParkingcar == null){
//							pParkingcar = new PParkingcar();
//							pParkingcar.setParkingId(null);
//							pParkingcar.setCommunityNo(vo.getCommunityNo());
//							pParkingcar.setLicense(license);
//							pParkingcar.setArriveTime(new Date(Long.valueOf(arriveTime.trim()) * 1000L));
//							pParkingcar.setBerthNo(berthNo);
//							pParkingcar.setReportTime(new Date());
//							pParkingcar.setStopType(Short.valueOf(stopType.toString()));
//							pParkingcar.setArriveCarImgUrl(arriveCarImgUrl);
//							pParkingcar.setParkingType((short)PParkingcar.ParkingType.Community.ordinal());
//							pParkingcar.setPropertyNumber(null);
//					    	pParkingcar.setBranchId(null);
//					    	pParkingcarServiceImpl.save(pParkingcar);
//						}
						responseMsg = new HashMap<String,Object>();
						responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_COMMUNITY_NO_EXIST);
						responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Community no exist!");
						responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
						return responseMsg;
					}
					vo.setCommunity(community);
				} catch (Exception e) {
					responseMsg = new HashMap<String,Object>();
					responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SERVER_ERROR);
					responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Server Error!");
					responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
					return responseMsg;
				}
			}else if(vo.getParkingType() == PARKING_TYPE_EMERGENCY){//停车场是否存在？parkingType = 1时做验证
				try {
					ParkingInfo parkingInfo = parkingInfoServiceImpl.findByParkingNo(vo.getCommunityNo());
					if(parkingInfo == null){
						//尽管找不到停车场，但还是保存来车记录
//						String license = vo.getLicense();//车牌号码，UTF8编码
//						String arriveTime = vo.getArriveTime();//时间戳(秒)
//						String berthNo = vo.getBerthNo();//车位编号，UTF8编码
//						Integer stopType = vo.getStopType();//停车类型 0月卡车,1临时车,2免费车,3电子账户车
//						String arriveCarImgUrl = vo.getArriveCarImgUrl();//来车图片url，utf8编码
//						PParkingcar pParkingcar = pParkingcarServiceImpl.findByCommLicArr(vo.getCommunityNo(), license, new Date(Long.valueOf(arriveTime)*1000l));
//						if(pParkingcar == null){
//							pParkingcar = new PParkingcar();
//							pParkingcar.setParkingId(null);
//							pParkingcar.setCommunityNo(vo.getCommunityNo());
//							pParkingcar.setLicense(license);
//							pParkingcar.setArriveTime(new Date(Long.valueOf(arriveTime.trim()) * 1000L));
//							pParkingcar.setBerthNo(berthNo);
//							pParkingcar.setReportTime(new Date());
//							//停车类型：0月卡车,1临时车,3免费车,4电子账户车
//							if(stopType == 3){
//								pParkingcar.setStopType((short)2);
//							}else if(stopType == 4){
//								pParkingcar.setStopType((short)3);
//							}else{
//								pParkingcar.setStopType(Short.valueOf(stopType.toString()));
//							}
//							pParkingcar.setArriveCarImgUrl(arriveCarImgUrl);
//							pParkingcar.setParkingType((short)PParkingcar.ParkingType.Parking.ordinal());
//							pParkingcar.setPropertyNumber(null);
//					    	pParkingcar.setBranchId(null);
//					    	pParkingcarServiceImpl.save(pParkingcar);
//						}
						log.info(">>>>>:[停车场来车汇报checkData]参数异常,停车场不存在:"+vo.toString());
						responseMsg = new HashMap<String,Object>();
						responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_COMMUNITY_NO_EXIST);
						responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Community no exist!");
						responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
						return responseMsg;
					}
					vo.setParkingInfo(parkingInfo);
				} catch (Exception e) {
					log.error(String.format(">>>>>:[停车场实时状态汇报checkData]服务器异常vo = %s", vo.toString()), e);
					responseMsg = new HashMap<String,Object>();
					responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SERVER_ERROR);
					responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Server Error!");
					responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
					return responseMsg;
				}
			}
			
			//3、来车时间大于当前时间
			String arriveTime = vo.getArriveTime();
			if(Long.valueOf(arriveTime.trim()) > new Date().getTime()/1000 ){
				responseMsg = new HashMap<String,Object>();
				responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY__ARRIVETIME_ERROR);
				responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "arriveTime error!");
				responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
				return responseMsg;
			}
			
		}

	
		return responseMsg;
	}
	
	/**
	 * 数据验证 3 走车汇报
	 * @param reportType	请求类型 1 停车场实时状态汇报 2 来车汇报 3 走车汇报 10 车辆信息查询 22 修改场内车牌接口
	 * @param vo	请求数据（参数和业务数据）
	 * @return
	 */
	protected Map<String, Object> checkData(int reportType,LeaveReportVo vo) {
		Map<String, Object> responseMsg = null;
		if(reportType == PARKINGUPREQ_TYPE_CAR_LEAVE){
			//1、是否为空？
			if(StringUtils.isBlank(vo.getVer()) 
					|| StringUtils.isBlank(vo.getCommunityNo())
					|| StringUtils.isBlank(vo.getLicense())
					|| StringUtils.isBlank(vo.getArriveTime())
					|| StringUtils.isBlank(vo.getLeaveTime())
					|| MStringUntils.isNullObject(vo.getFee())
					|| StringUtils.isBlank(vo.getIsBook())
					|| StringUtils.isBlank(vo.getStopType())
					|| MStringUntils.isNullObject(vo.getPayType())
					|| StringUtils.isBlank(vo.getIsEscape())
					|| MStringUntils.isNullObject(vo.getParkingType())){
				log.info(">>>>>:[停车场走车汇报checkData]参数异常,请求参数:"+vo.toString());
				responseMsg = new HashMap<String,Object>();
				responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_PARAM_ERROR);
				responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Param Error!");
				responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
				return responseMsg;
			}
			
			if(!"0".equals(vo.getIsEscape().trim()) ){
				if(MStringUntils.isNullObject(vo.getEscape())){
					responseMsg = new HashMap<String,Object>();
					responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_PARAM_ERROR);
					responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Param Error!");
					responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
					return responseMsg;
				}
			}
			
			//2、小区是否存在？parkingType = 0时做验证
			if(vo.getParkingType() == PARKING_TYPE_COMMUNITY){
				try {
					CommunityModel community = getCommunity(vo.getCommunityNo());
					if(community == null){
						//尽管找不到小区，但还是保存走车记录
						String license = vo.getLicense();//车牌号码，UTF8编码
						String arriveTime = vo.getArriveTime();//时间戳(秒)
						String leaveTime = vo.getLeaveTime();//时间戳(秒)
						Integer fee = vo.getFee();//停车费，单位：分
						String stopType = vo.getStopType();//0月卡车,1临时车,3免费车,4电子账户车
						Integer payType = vo.getPayType();//支付方式:0钱包支付，1微信支付，2支付宝支付，3 银行卡 4、现金
						String arriveUrl = vo.getArriveCarImgUrl();//来车图片url，utf8编码
						String leaveUrl = vo.getLeaveCarImgUrl();//走车图片url，utf8编码
						PParkingcar pParkingcar = pParkingcarServiceImpl.findByCommLicArr(vo.getCommunityNo(), license, new Date(Long.valueOf(arriveTime)*1000l));
						RStoprecord rStoprecord = rStoprecordServiceImpl.findByComLicArrLeav(vo.getCommunityNo(), license,new Date(Long.valueOf(arriveTime)*1000l),
								new Date(Long.valueOf(leaveTime)*1000l));
						if(rStoprecord == null){
							rStoprecord = new RStoprecord();
							rStoprecord.setAreaId(null);
							rStoprecord.setParkingId(null);
							rStoprecord.setParkingNumber(vo.getCommunityNo());
							rStoprecord.setParkingName(null);
							rStoprecord.setBerthId(null);
							rStoprecord.setBerthNo(null);
							rStoprecord.setOutTradeNo(null);
							rStoprecord.setLicense(license);
							rStoprecord.setArriveTime(new Date(Long.valueOf(arriveTime) * 1000L));
							rStoprecord.setLeaveTime(new Date(Long.valueOf(leaveTime) * 1000L));
							rStoprecord.setDeration(Integer.valueOf(leaveTime) - Integer.valueOf(arriveTime));//停车时长(秒)
							rStoprecord.setReceivedMoney(new BigDecimal(0));//预收,单位元
							BigDecimal bigDecimalFee = new BigDecimal(fee);
							bigDecimalFee = bigDecimalFee.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
							rStoprecord.setReceivablesMoney(bigDecimalFee);//应收,单位元
							if(payType == 4 || Integer.valueOf(stopType) == 0){
								rStoprecord.setFee(bigDecimalFee);//停车费用（实收）,单位元
							}else{
								rStoprecord.setFee(new BigDecimal(0));//停车费用（实收）,单位元
							}
							rStoprecord.setFeeType(0);//计费方式 0计时(默认) 1买时
							//停车类型：0月卡车,1临时车,2免费车,3电子账户车
							if(Integer.valueOf(stopType) == 3){
								rStoprecord.setStopType(2);
							}else if(Integer.valueOf(stopType) == 4){
								rStoprecord.setStopType(3);
							}else{
								rStoprecord.setStopType(Integer.valueOf(stopType));
							}
							rStoprecord.setPayType(payType);//支付方式:0钱包支付，1微信支付，2支付宝支付，3 银行卡 4、现金
							if(payType == 4 || Integer.valueOf(stopType) == 0){
								rStoprecord.setFeePayStatus(RStoprecord.FeePayStatus.Paid.ordinal());//支付状态0;未支付1;已支付 2;支付失败 
								rStoprecord.setPaidDate(new Date());
							}else{
								rStoprecord.setFeePayStatus(RStoprecord.FeePayStatus.Not_Paid.ordinal());//支付状态0;未支付1;已支付 2;支付失败 
							}
							rStoprecord.setCreateTime(new Date());
							rStoprecord.setArriveCarImgUrl(arriveUrl);
							rStoprecord.setLeaveCarImgUrl(leaveUrl);
							rStoprecord.setUserName(null);
							rStoprecord.setRealName(null);
							rStoprecord.setBranchId(null);
							rStoprecord.setHeadOfficeId(null);
							rStoprecord.setPopertyNumber(null);
							rStoprecordServiceImpl.save(rStoprecord);
						}
						//删除 实时停车信息
						if(pParkingcar != null){
							boolean result = pParkingcarServiceImpl.deleteById(pParkingcar.getId());
//							pParkingcarServiceImpl.delete(pParkingcar.getId());
						}
						//删除其他实时停车记录
						List<PParkingcar> illegalParkingcarList = pParkingcarServiceImpl.findByLic(license);
						if(CollectionUtils.isNotEmpty(illegalParkingcarList)){
							boolean result = pParkingcarServiceImpl.deleteByCommunityNumberAndLicense(vo.getCommunityNo(),license);
//							for (PParkingcar illegalParkingcar : illegalParkingcarList) {
//								if(illegalParkingcar != null && vo.getCommunityNo().equals(illegalParkingcar.getCommunityNo())){
//									pParkingcarServiceImpl.delete(illegalParkingcar);
//								}
//							}
						}
						log.info(">>>>>:[停车场走车汇报checkData]参数异常,小区不存在:"+vo.toString());
						responseMsg = new HashMap<String,Object>();
						responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_COMMUNITY_NO_EXIST);
						responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Community no exist!");
						responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
						return responseMsg;
					}
					vo.setCommunity(community);
				} catch (Exception e) {
					log.error(String.format(">>>>>:[停车场实时状态汇报checkData]服务器异常vo = %s", vo.toString()), e);
					responseMsg = new HashMap<String,Object>();
					responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SERVER_ERROR);
					responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Server Error!");
					responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
					return responseMsg;
				}
			}else if(vo.getParkingType() == PARKING_TYPE_EMERGENCY){//停车场是否存在？parkingType = 1时做验证
				try {
					ParkingInfo parkingInfo = parkingInfoServiceImpl.findByParkingNo(vo.getCommunityNo());
					if(parkingInfo == null){
						//尽管找不到停车场，但还是保存走车记录
						String license = vo.getLicense();//车牌号码，UTF8编码
						String arriveTime = vo.getArriveTime();//时间戳(秒)
						String leaveTime = vo.getLeaveTime();//时间戳(秒)
						Integer fee = vo.getFee();//停车费，单位：分
						String stopType = vo.getStopType();//0月卡车,1临时车,3免费车,4电子账户车
						Integer payType = vo.getPayType();//支付方式:0钱包支付，1微信支付，2支付宝支付，3 银行卡 4、现金
						String arriveUrl = vo.getArriveCarImgUrl();//来车图片url，utf8编码
						String leaveUrl = vo.getLeaveCarImgUrl();//走车图片url，utf8编码
						PParkingcar pParkingcar = pParkingcarServiceImpl.findByCommLicArr(vo.getCommunityNo(), license, new Date(Long.valueOf(arriveTime)*1000l));
						RStoprecord rStoprecord = rStoprecordServiceImpl.findByComLicArrLeav(vo.getCommunityNo(), license,new Date(Long.valueOf(arriveTime)*1000l),
								new Date(Long.valueOf(leaveTime)*1000l));
						if(rStoprecord == null){
							rStoprecord = new RStoprecord();
							rStoprecord.setAreaId(null);
							rStoprecord.setParkingId(null);
							rStoprecord.setParkingNumber(vo.getCommunityNo());
							rStoprecord.setParkingName(null);
							rStoprecord.setBerthId(null);
							rStoprecord.setBerthNo(null);
							rStoprecord.setOutTradeNo(null);
							rStoprecord.setLicense(license);
							rStoprecord.setArriveTime(new Date(Long.valueOf(arriveTime) * 1000L));
							rStoprecord.setLeaveTime(new Date(Long.valueOf(leaveTime) * 1000L));
							rStoprecord.setDeration(Integer.valueOf(leaveTime) - Integer.valueOf(arriveTime));//停车时长(秒)
							rStoprecord.setReceivedMoney(new BigDecimal(0));//预收,单位元
							BigDecimal bigDecimalFee = new BigDecimal(fee);
							bigDecimalFee = bigDecimalFee.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
							rStoprecord.setReceivablesMoney(bigDecimalFee);//应收,单位元
							if(payType == 4 || Integer.valueOf(stopType) == 0){
								rStoprecord.setFee(bigDecimalFee);//停车费用（实收）,单位元
							}else{
								rStoprecord.setFee(new BigDecimal(0));//停车费用（实收）,单位元
							}
							rStoprecord.setFeeType(0);//计费方式 0计时(默认) 1买时
							//停车类型：0月卡车,1临时车,2免费车,3电子账户车
							if(Integer.valueOf(stopType) == 3){
								rStoprecord.setStopType(2);
							}else if(Integer.valueOf(stopType) == 4){
								rStoprecord.setStopType(3);
							}else{
								rStoprecord.setStopType(Integer.valueOf(stopType));
							}
							rStoprecord.setPayType(payType);//支付方式:0钱包支付，1微信支付，2支付宝支付，3 银行卡 4、现金
							if(payType == 4 || Integer.valueOf(stopType) == 0){
								rStoprecord.setFeePayStatus(RStoprecord.FeePayStatus.Paid.ordinal());//支付状态0;未支付1;已支付 2;支付失败 
								rStoprecord.setPaidDate(new Date());
							}else{
								rStoprecord.setFeePayStatus(RStoprecord.FeePayStatus.Not_Paid.ordinal());//支付状态0;未支付1;已支付 2;支付失败 
							}
							rStoprecord.setCreateTime(new Date());
							rStoprecord.setArriveCarImgUrl(arriveUrl);
							rStoprecord.setLeaveCarImgUrl(leaveUrl);
							rStoprecord.setUserName(null);
							rStoprecord.setRealName(null);
							rStoprecord.setBranchId(null);
							rStoprecord.setHeadOfficeId(null);
							rStoprecord.setPopertyNumber(null);
							rStoprecordServiceImpl.save(rStoprecord);
						}
						//删除 实时停车信息
						if(pParkingcar != null){
							boolean result = pParkingcarServiceImpl.deleteById(pParkingcar.getId());
//							pParkingcarServiceImpl.delete(pParkingcar.getId());
						}
						//删除其他实时停车记录
						List<PParkingcar> illegalParkingcarList = pParkingcarServiceImpl.findByLic(license);
						if(CollectionUtils.isNotEmpty(illegalParkingcarList)){
							boolean result = pParkingcarServiceImpl.deleteByCommunityNumberAndLicense(vo.getCommunityNo(),license);
//							for (PParkingcar illegalParkingcar : illegalParkingcarList) {
//								if(illegalParkingcar != null && vo.getCommunityNo().equals(illegalParkingcar.getCommunityNo())){
//									pParkingcarServiceImpl.delete(illegalParkingcar);
//								}
//							}
						}
						log.info(">>>>>:[停车场走车汇报checkData]参数异常,停车场不存在:"+vo.toString());
						responseMsg = new HashMap<String,Object>();
						responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_COMMUNITY_NO_EXIST);
						responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Community no exist!");
						responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
						return responseMsg;
					}
					vo.setParkingInfo(parkingInfo);
				} catch (Exception e) {
					log.error(String.format(">>>>>:[停车场实时状态汇报checkData]服务器异常vo = %s", vo.toString()), e);
					responseMsg = new HashMap<String,Object>();
					responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SERVER_ERROR);
					responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Server Error!");
					responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
					return responseMsg;
				}
			}
			
			//3、来车时间大于走车时间?
			if(Long.valueOf(vo.getArriveTime()) > Long.valueOf(vo.getLeaveTime()) ){
				log.info(">>>>>:[停车场走车汇报checkData]来车时间大于走车时间:"+vo.toString());
				responseMsg = new HashMap<String,Object>();
				responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_LEAVETIME_ERROR);
				responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "leaveTime Error!");
				responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
			}
		}
		return responseMsg;
	}
	
	/**
	 * 数据验证 10 车辆信息查询
	 * @param reportType	请求类型 1 停车场实时状态汇报 2 来车汇报 3 走车汇报 10 车辆信息查询 22 修改场内车牌接口
	 * @param vo	请求数据（参数和业务数据）
	 * @return
	 */
	protected Map<String, Object> checkData(int reportType,QueryCarInfoVo vo) {
		Map<String, Object> responseMsg = null;
		if(reportType == PARKINGUPREQ_TYPE_QUERY_CAR_INFO){
			//1、是否为空？
			if(StringUtils.isBlank(vo.getVer()) 
					|| StringUtils.isBlank(vo.getCommunityNo()) 
					|| StringUtils.isBlank(vo.getLicense()) 
					|| MStringUntils.isNullObject(vo.getQueryType())
					|| MStringUntils.isNullObject(vo.getParkingType())){
				responseMsg = new HashMap<String,Object>();
				responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_PARAM_ERROR);
				responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Param Error!");
				return responseMsg;
			}
			
			//2、小区是否存在？parkingType = 0时做验证
			if(vo.getParkingType() == PARKING_TYPE_COMMUNITY){
				try {
					CommunityModel community = getCommunity(vo.getCommunityNo());
					if(community == null){
						responseMsg = new HashMap<String,Object>();
						responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_COMMUNITY_NO_EXIST);
						responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Community no exist!");
						return responseMsg;
					}
					vo.setCommunity(community);
				} catch (Exception e) {
					e.printStackTrace();
					responseMsg = new HashMap<String,Object>();
					responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SERVER_ERROR);
					responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Server Error!");
					return responseMsg;
				}
			}else if(vo.getParkingType() == PARKING_TYPE_EMERGENCY){//停车场是否存在？parkingType = 1时做验证
				try {
					ParkingInfo parkingInfo = parkingInfoServiceImpl.findByParkingNo(vo.getCommunityNo());
					if(parkingInfo == null){
						log.info(">>>>>:[车辆信息查询checkData]参数异常,停车场不存在:"+vo.toString());
						responseMsg = new HashMap<String,Object>();
						responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_COMMUNITY_NO_EXIST);
						responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Community no exist!");
						responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);
						return responseMsg;
					}
					vo.setParkingInfo(parkingInfo);
				} catch (Exception e) {
					log.error(String.format(">>>>>:[停车场实时状态汇报checkData]服务器异常vo = %s", vo.toString()), e);
					responseMsg = new HashMap<String,Object>();
					responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SERVER_ERROR);
					responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Server Error!");
					responseMsg.put(LPConstants.RESULTMAP_KEY_DATA, null);;
					return responseMsg;
				}
			}
		}
		return responseMsg;
	}
	
	/**
	 * 数据验证 22 修改场内车牌接口
	 * @param reportType 请求类型 1 停车场实时状态汇报 2 来车汇报 3 走车汇报 10 车辆信息查询 22 修改场内车牌接口
	 * @param vo 请求数据（参数和业务数据）
	 * @return
	 */
	protected Map<String, Object> checkData(int reportType,ModifyArrivedLicenseVo vo) {
		Map<String, Object> responseMsg = null;
		if(reportType == PARKINGUPREQ_TYPE_MODIFY_ARRIVED_LICENSE){
			String ver = vo.getVer();//协议版本号
			String communityNo = vo.getCommunityNo();//小区编号(停车场编码)
			Integer parkingType = vo.getParkingType();//停车场类型 0小区 1 紧急场所
			String license = vo.getLicense();//原始车牌号码
			String modifiedLicense = vo.getModifiedLicense();//修改后的车牌号码
			Long timestamp = vo.getTimestamp();//道闸系统当前Unix时间戳
			Long arrivedTimestamp = vo.getArrivedTimestamp();//车辆原始来车时间Unix时间戳
			Long modifiedArrivedTimestamp = vo.getModifiedArrivedTimestamp();//修改后的来车时间Unix时间戳,保留字段,如需要对来车时间也进行修改,可使用该字段
			Integer fieldType = vo.getFieldType();//0场外 1场内
			//参数为空？
			if(StringUtils.isBlank(ver)
					|| StringUtils.isBlank(communityNo)
					|| parkingType == null
					|| StringUtils.isBlank(license)
					|| StringUtils.isBlank(modifiedLicense)
					|| timestamp == null
					|| arrivedTimestamp == null
					|| modifiedArrivedTimestamp == null){
				responseMsg = new HashMap<String,Object>();
				responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_PARAM_ERROR);
				responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Param Error!");
				return responseMsg;
			}
			//小区/停车场 存在？信息
			if(parkingType == PARKING_TYPE_COMMUNITY){
				try {
					CommunityModel community = getCommunity(vo.getCommunityNo());
					if(community != null){
						vo.setCommunity(community);
					}
				} catch (Exception e) {
					log.error(">>>>>:[修改场内车牌接口modifyArrivedLicense]获取小区信息时异常", e);
				}
				
			}else if(parkingType == PARKING_TYPE_EMERGENCY){
				if(fieldType == null){
					responseMsg = new HashMap<String,Object>();
					responseMsg.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_PARAM_ERROR);
					responseMsg.put(LPConstants.RESULTMAP_KEY_MSG, "Param Error!");
					return responseMsg;
				}
				try {
					ParkingInfo parkingInfo = parkingInfoServiceImpl.findByParkingNo(vo.getCommunityNo());
					if(parkingInfo != null){
						vo.setParkingInfo(parkingInfo);
					}
				} catch (Exception e) {
					log.error(">>>>>:[修改场内车牌接口modifyArrivedLicense]获取停车场信息时异常", e);
				}
			}
			
		}
		return responseMsg;
	}
	
	/**
	 * 得到小区信息
	 * @param communityNo 小区编号
	 * @return
	 * @throws Exception
	 */
	private CommunityModel getCommunity(String communityNo) throws Exception{
		List<CommunityModel> listCommunity = communityModelService.selectByLicenseCommunitys(communityNo);
		return (CollectionUtils.isEmpty(listCommunity)) ? null : listCommunity.get(0);
	}
	
	public void pushMessage(String userName, String content, Map<String, String> map) {
		PushUntils pushUntils = PushUntils.getUserPushUntils();
		Member member = memberService.findByUsername(userName);
		// 设备是Android
		if (member != null && member.getCurrentEquipment() == 1 && "0".equals(member.getIsSendPush())) {
			try {
				pushUntils.pushNotificationMsgToAndroidUser(member.getUsername(), "安心点", content, map,null);
			} catch (APIConnectionException e) {
				e.printStackTrace();
			} catch (APIRequestException e) {
				e.printStackTrace();
			}
		}
		// 设备是IOS
		if (member != null && member.getCurrentEquipment() == 2 && "0".equals(member.getIsSendPush())) {
			try {
				pushUntils.pushNotificationMsgToIOSUser(member.getUsername(), content, map, null);
			} catch (APIConnectionException e) {
				e.printStackTrace();
			} catch (APIRequestException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendSMSMessage(String userName, String content) {
		Member member = memberService.findByUsername(userName);
		if (member != null && "0".equals(member.getIsSendMessage())) {
			smsService.send(userName, content);
		}
	}
	
}
