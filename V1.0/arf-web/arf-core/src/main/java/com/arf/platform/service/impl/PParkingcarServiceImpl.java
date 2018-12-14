/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.platform.service.impl;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arf.carbright.MerchantTransctionRecordSource;
import com.arf.carbright.entity.AxdVouchersRecord;
import com.arf.carbright.entity.AxdVouchersRecord.UsedStatus;
import com.arf.carbright.entity.MerchantTransactionRecord;
import com.arf.carbright.service.IAxdVouchersRecordService;
import com.arf.carbright.service.MerchantTransactionRecordService;
import com.arf.carbright.service.PbusinessService;
import com.arf.carbright.service.impl.RefundDeductedPointVouchers;
import com.arf.core.AppMessage;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.dto.ParkingFeeDto;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.Member;
import com.arf.core.service.CommunityModelService;
import com.arf.core.service.LicensePlateModelService;
import com.arf.core.service.MemberService;
import com.arf.core.service.PaymentSetingModelService;
import com.arf.core.service.SmsService;
import com.arf.core.service.SysconfigService;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.core.thread.ThreadPoolFactory;
import com.arf.core.util.HttpUtil;
import com.arf.core.util.MStringUntils;
import com.arf.eparking.AkParkingFactory;
import com.arf.eparking.entity.EscapeRecord;
import com.arf.eparking.entity.ParkingFeeApplicationRecords;
import com.arf.eparking.service.EscapeRecordService;
import com.arf.eparking.service.ParkingFeeApplicationRecordsService;
import com.arf.goldcard.dto.UserGoldCardAccountDto;
import com.arf.goldcard.entity.GoldCardTransferRecord;
import com.arf.goldcard.entity.UserGoldCardAccount;
import com.arf.goldcard.service.IGoldCardTransferRecordService;
import com.arf.goldcard.service.IUserGoldCardAccountService;
import com.arf.member.dto.PointGiftDto;
import com.arf.member.entity.PointTransferRecord;
import com.arf.member.entity.PointTransferRecord.PointGiftBusinessType;
import com.arf.member.entity.RAccountTransferRecord;
import com.arf.member.service.IPointTransferRecordService;
import com.arf.member.service.IRAccountTransferRecordService;
import com.arf.member.vo.PointGiftVo;
import com.arf.notice.NoticeSource;
import com.arf.notice.NoticeSource.NoticeType;
import com.arf.payment.AliPaymentCallBackData;
import com.arf.payment.OrderNumPrefixConstraint;
import com.arf.payment.WeixinPaymentCallBackData;
import com.arf.payment.dto.PaymentResultCacheDto;
import com.arf.payment.service.PaymentCallbackService;
import com.arf.payment.service.RegisterPaymentCallBackService;
import com.arf.platform.RStoprecordJobs;
import com.arf.platform.dao.PParkingcarDao;
import com.arf.platform.entity.PParkingcar;
import com.arf.platform.entity.RStoprecord;
import com.arf.platform.entity.SMemberAccount;
import com.arf.platform.service.ISMemberAccountService;
import com.arf.platform.service.PParkingcarService;
import com.arf.platform.service.RStoprecordService;
import com.arf.redis.CacheNameDefinition;
import com.google.gson.Gson;


/**
 * Service - PParkingcar表
 * 
 * @author arf
 * @version 1.0
 */
@Service("PParkingcarServiceImpl")
public class PParkingcarServiceImpl extends BaseServiceImpl<PParkingcar, Long> implements PParkingcarService,PaymentCallbackService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	final String DEFAULT_DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";
	@Resource(name = "PParkingcarDaoImpl")
	private PParkingcarDao pParkingcarDao;
	
	@Resource(name = "licensePlateModelServiceImpl")
	private LicensePlateModelService licensePlateService;
	
	@Resource(name = "communityModelServiceImpl")
	private CommunityModelService communityService;
	
	@Resource(name = "RStoprecordServiceImpl")
	private RStoprecordService stoprecordService;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "paymentSetingModelServiceImpl")
	private PaymentSetingModelService paymentSetingModelService;
	
	@Value("${SOCKET_SERVER_URL}")
	protected String SOCKET_URL;
	
	@Value("${DWON_REQUEST_URL}")
	protected String DWON_REQUEST_URL;
	
	@Resource(name = "userGoldCardAccountServiceImpl")
	private IUserGoldCardAccountService userGoldCardAccountServiceImpl;
	
	@Resource(name = "goldCardTransferRecordServiceImpl")
	private IGoldCardTransferRecordService goldCardTransferRecordServiceImpl;
	
	@Resource(name="sMemberAccountServiceImpl")
	private ISMemberAccountService sMemberAccountServiceImpl;
	
	@Resource( name = "rAccountTransferRecordServiceImpl")
	private IRAccountTransferRecordService rAccountTransferRecordServiceImpl;
	
	@Resource(name = "escapeRecordServiceImpl")
	private EscapeRecordService escapeRecordServiceImpl;
	
	@Resource(name = "communityModelServiceImpl")
	private CommunityModelService communityModelService;
	
	@Resource(name = "smsServiceImpl")
	private SmsService smsService;
	
	@Resource(name = "sysconfigServiceImpl")
	private SysconfigService sysconfigServiceImpl;
	@Resource(name ="axdVouchersRecordServiceImpl")
	private IAxdVouchersRecordService axdVouchersRecordServiceImpl;
	
	@Resource( name = "pointTransferRecordServiceImpl")
	private IPointTransferRecordService pointTransferRecordServiceImpl;	
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "axdVouchersRecordServiceImpl")
	private IAxdVouchersRecordService axdVouchersRecordService;

	@Resource(name="parkingFeeApplicationRecordsService")
	ParkingFeeApplicationRecordsService parkingFeeApplicationRecordsService;
	
	@Resource(name="pBusinessService")
	private PbusinessService pBusinessService;
	
	@Resource(name = "merchantTransactionRecordService")
	private MerchantTransactionRecordService merchantTransactionRecordService;
	
	@Autowired
	private AkParkingFactory factory;
	
	private final String Log_Temp_Parking_Fee_Notify_Gateway_Retry_Key = "Log_Temp_Parking_Fee_Notify_Gateway_Retry:";
	public static final String NOTIFY_ECC_RETRY_INTERVAL = "NOTIFY_ECC_RETRY_INTERVAL";//下发ECC通知如果网络等异常导致的失败重发频率间隔设置参数,
																					//例如设置为1,2,3则标识失败后的第1,2,3秒都会进行重发,
																					//之后则按照3秒的稳定间隔重发
	public static final Map<String,RetryNotifyEcc> Retry_Notify_ECC_Map= new ConcurrentHashMap<String,RetryNotifyEcc>();
	
	@Override
	@Transactional
	protected BaseDao<PParkingcar, Long> getBaseDao() {
		return pParkingcarDao;
	}

	
	@Override
	public PParkingcar findByCommLicArr(String communityNo, String license, Date arriveTime) {
		return pParkingcarDao.findByCommLicArr( communityNo,license,arriveTime);
	}
	
	@Override
	public PParkingcar findByLicenseAndArriveTime(String license,Date arriveTime) {
		return pParkingcarDao.findByLicenseAndArriveTime( license,arriveTime);
	}

	@Override
	@Transactional
	public List<PParkingcar> findByLic(String license) {
		return pParkingcarDao.findByLic(license);
	}


	@Override
	public PParkingcar findByCommLic(String communityNo, String license) {
		return pParkingcarDao.findByCommLic(communityNo,license);
	}

	@Override
	public List<Map<String, Object>> findByLicenseList(List<String> licenses) {
		return pParkingcarDao.findByLicenseList(licenses);
	}

	@Override
	public List<ParkingFeeDto> findTempParkingCar(String userName,String communityNumber,String licenseNumber,long parkingCarId) {
		List<ParkingFeeDto> parkingFees = new ArrayList<ParkingFeeDto>();
		if(StringUtils.isBlank(userName)){
			return parkingFees;
		}
		List<PParkingcar> cars=null;
		//车牌号和小区编号不为空
		if (StringUtils.isNotBlank(communityNumber)&&StringUtils.isNotBlank(licenseNumber)) {
			cars= pParkingcarDao.findByLic(licenseNumber);
		}else if(parkingCarId>0){//停车记录id不为空
			PParkingcar car=pParkingcarDao.find(parkingCarId);
			if(car!=null){
				cars=new ArrayList<PParkingcar>();
				cars.add(car);
			}else{
				return null;
			}			
		}
		else{//否则根据用户名查询
			cars= pParkingcarDao.findTempParkingCar(userName);
		}
		
		//查找逃欠费记录
		List<EscapeRecord> escapeRecordList = escapeRecordServiceImpl.findByUserNameAndRecoveryStatus(userName,EscapeRecord.RecoveryStatus.no_recovery);
		BigDecimal escapeFeeTotal = new BigDecimal(0);
		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(escapeRecordList)){
			for (EscapeRecord escapeRecord : escapeRecordList) {
				BigDecimal escapeFee = escapeRecord.getEscapeFee();
				if(escapeFee != null){
					escapeFeeTotal = escapeFeeTotal.add(escapeFee);
				}
				break;
			}
		}
		if (cars!=null&&cars.size()>0) {
			for(PParkingcar car : cars){
				String communityNo = car.getCommunityNo();
				String license = car.getLicense();
				//Date arrivedTime = car.getArriveTime();
				CommunityModel community = communityService.findByNumber(communityNo);
				if(community == null){
					continue;
				}
				//道闸本次停车费
				ParkingFeeDto feeDto = this.getParkingFee(userName, license, community,car);
				try{
					feeDto.setStopType(PParkingcar.StopType.Temp_Parking.ordinal());
					feeDto.setParkingCarId(car.getId());
					feeDto.setEscapeFee(escapeFeeTotal);
					if(feeDto.getRet() == 0){// 0 为成功
						//实际支付：本次停车费 + 欠费
						feeDto.setTotalFee(feeDto.getTotalFee().add(escapeFeeTotal));
						feeDto.setUnpaidFee(feeDto.getUnpaidFee().add(escapeFeeTotal));
					}
					
				}catch (Exception e) {
					log.error("临时停车费异常",e);
				}
				parkingFees.add(feeDto);
			}
		}
		// 排个序
		Collections.sort(parkingFees, new Comparator<ParkingFeeDto>() {
			@Override
			public int compare(ParkingFeeDto o1, ParkingFeeDto o2) {
				if(o1 == null || o2 == null){
					return 0;
				}
				Long o1Start = o1.getParkingStartTime();// 停车开始时间戳 单位:秒
				Long o2Start = o2.getParkingStartTime();// 停车开始时间戳 单位:秒
				if(o1Start == null && o2Start == null){ 
					return 0;
				}
				if(o1Start == null){
					return 1;
				}else if(o2Start == null){
					return -1;
				}else{
					return 0 - o1Start.compareTo(o2Start);
				}
			}
		});
		return parkingFees;
	}
	
	/**
	 * 获取停车费信息
	 * @return
	 * @see com.arf.platform.service.impl.PParkingcarServiceImpl.getParkingFee(String, String, CommunityModel, PParkingcar)
	 */
	@Deprecated
	private ParkingFeeDto fetchParkingFee(String userName, String license, CommunityModel community,PParkingcar parkingCar) {
		String communityNo = community.getCommunity_number();
		String communityName = community.getCommunityName();
		int executeMethod = community.getExecuteMethod();
		Date arrivedTime = parkingCar.getArriveTime();
		
		switch (executeMethod) {
		case 1:
			Map<String,String> param = new HashMap<String,String>();
			param.put("communityNumber", communityNo);
			param.put("interfaceType", "7");
			
			JSONObject bizData = new JSONObject();
			bizData.put("licensePlateNumber", license);
			
			param.put("bizData", bizData.toJSONString());
			
			String result = null;
			try {
				result = HttpUtil.doHttpPost(SOCKET_URL, param);
				log.info("获取停车费信息fetchParkingFee--FRTK，道闸返回信息：" + result);
				if(StringUtils.isBlank(result)){
					return new ParkingFeeDto(ParkingFeeDto.SOCKET_SERVER_EXCEPTION, "服务器返回数据为空"); 
				}
				JSONObject resObj = JSON.parseObject(result);
				int code = resObj.getIntValue("code");
				String msg = resObj.getString("msg");
				if (code != 0) {
					return new ParkingFeeDto(code, msg); 
				}
				JSONObject js = JSON.parseObject(resObj.getString("extrDatas"));
				//此处又从新请求了一次socket-server，原因：socket-server有时候会返回{"extrDatas":null,"code":0,"msg":"操作成功"}
				if(js == null){
					Thread.sleep(700);
					result = HttpUtil.doHttpPost(SOCKET_URL, param);
					log.info("获取停车费信息fetchParkingFee--FRTK，道闸返回信息：" + result);
					if(StringUtils.isBlank(result)){
						return new ParkingFeeDto(ParkingFeeDto.SOCKET_SERVER_EXCEPTION, "服务器返回数据为空"); 
					}
					resObj = JSON.parseObject(result);
					code = resObj.getIntValue("code");
					msg = resObj.getString("msg");
					if (code != 0) {
						return new ParkingFeeDto(code, msg); 
					}
					js = JSON.parseObject(resObj.getString("extrDatas"));
					if(js == null){
						return new ParkingFeeDto(ParkingFeeDto.SOCKET_SERVER_EXCEPTION, "服务器返回数据为空"); 
					}
				}
				Long intime = js.getLongValue("intime");//停车时长
				//Long parkingTime = new Date().getTime() / 1000 - intime;
				String barrierGateOrderNo = js.getString("orderNum");
				ParkingFeeDto feeDto = new ParkingFeeDto(code, msg, intime, parkingCar.getArriveTime().getTime() / 1000,
						communityNo, communityName, license, barrierGateOrderNo,js.getBigDecimal("fee").divide(new BigDecimal(100)));
				
				List<RStoprecord> paidRecords = stoprecordService.findAllPaidRecord(communityNo, license, arrivedTime);
				BigDecimal paidTotalFee = new BigDecimal(0);
				if(!CollectionUtils.isEmpty(paidRecords)){
					for(RStoprecord paidRecord : paidRecords){
						if(paidRecord.getFee() != null){
							paidTotalFee = paidTotalFee.add(paidRecord.getFee());
						}
					}
				}
				
				feeDto.setPaidFee(paidTotalFee);
				if(feeDto.getRet() == ParkingFeeDto.RET_SUCCESS && feeDto.getUnpaidFee() != null){
					feeDto.setTotalFee(paidTotalFee.add(feeDto.getUnpaidFee()));
				}else{
					feeDto.setTotalFee(null);
				}
				return feeDto;
			} catch (Exception e) {
				log.error("获取临时停车费信息时出现异常,参数" + JSON.toJSONString(param),e);
				return new ParkingFeeDto(ParkingFeeDto.EXCEPTION,e.getMessage());
			}
//			break;
		case 2:
			String url = DWON_REQUEST_URL;
			// Map<String, Object> map_parm = new HashMap<String, Object>();
			Map<String, String> mapLP = new HashMap<String, String>();
			JSONObject bizData2 = new JSONObject();
			bizData2.put("version", "1");
			bizData2.put("license", license);
			bizData2.put("userId", userName);
			bizData2.put("leaveTime", new Date().getTime() / 1000 + ""); //单位:秒  unix时间戳
			bizData2.put("parkingType", parkingCar.getParkingType());
			mapLP.put("interface_lanpeng", "51");
			mapLP.put("bizData", bizData2.toJSONString());
			mapLP.put("communityNo", communityNo);
			String result2 = null;
			try {
				result2 = HttpUtil.doHttpPost(url, mapLP);
				log.info("获取停车费信息fetchParkingFee--LP，道闸返回信息：" + result2);
				if(StringUtils.isBlank(result2)){
					return new ParkingFeeDto(ParkingFeeDto.SOCKET_SERVER_EXCEPTION, "服务器返回数据为空"); 
				}else{
					log.info("查询临时停车费信息(蓝鹏)--->result="+result2);
				}
				JSONObject resObj = JSON.parseObject(result2);
				int code = resObj.getIntValue("code");
				String msg = resObj.getString("msg");
				if (code != 0) {
					return new ParkingFeeDto(code, msg); 
				}
				
				JSONArray jsonArray = resObj.getJSONArray("extrDatas");
//				jsonObject.getLong("arriveTime");//停车开始时间
				if(CollectionUtils.isEmpty(jsonArray)){
					return new ParkingFeeDto(ParkingFeeDto.SOCKET_SERVER_EXCEPTION, "服务器返回数据为空"); 
				}
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				ParkingFeeDto feeDto = new ParkingFeeDto(code, msg, jsonObject.getLong("parkingHour"),parkingCar.getArriveTime().getTime() / 1000, communityNo, communityName, license,"",jsonObject.getBigDecimal("fee").divide(new BigDecimal(100)));
				//查询某次来车已经支付的订单记录
				List<RStoprecord> paidRecords = stoprecordService.findAllPaidRecord(communityNo, license, arrivedTime);
				BigDecimal paidTotalFee = new BigDecimal(0);
				if(!CollectionUtils.isEmpty(paidRecords)){
					for(RStoprecord paidRecord : paidRecords){
						if(paidRecord.getFee() != null){
							paidTotalFee = paidTotalFee.add(paidRecord.getFee());
						}
					}
				}
				feeDto.setPaidFee(paidTotalFee);//已经支付的费用
				if(feeDto.getRet() == ParkingFeeDto.RET_SUCCESS && feeDto.getUnpaidFee() != null){
					feeDto.setTotalFee(paidTotalFee.add(feeDto.getUnpaidFee()));
				}else{
					feeDto.setTotalFee(null);
				}
				return feeDto;
			} catch (Exception e) {
				log.error("获取临时停车费信息时出现异常,参数" + JSON.toJSONString(mapLP),e);
				return new ParkingFeeDto(ParkingFeeDto.EXCEPTION,e.getMessage());
			}
//			break;
		default:
			String dfmsg = "小区"+communityNo+"["+communityName+"]"+"错误的executeMethod("+executeMethod+")";
			log.info(dfmsg);
			return new ParkingFeeDto(ParkingFeeDto.EXECUTE_METHOD_WRONG,dfmsg);
		}

	}
	
	@Override
	public ParkingFeeDto getParkingFee(String userName, String license,CommunityModel community, PParkingcar parkingCar) {
		if(community == null){
			return null;
		}
		String communityNo = community.getCommunity_number();
		String communityName = community.getCommunityName();
		int executeMethod = community.getExecuteMethod();
		Date arrivedTime = parkingCar.getArriveTime();
		
		switch (executeMethod) {
		case 1:
			Map<String,String> param = new HashMap<String,String>();
			param.put("communityNumber", communityNo);
			param.put("interfaceType", "7");
			
			JSONObject bizData = new JSONObject();
			bizData.put("licensePlateNumber", license);
			
			param.put("bizData", bizData.toJSONString());
			
			String result = null;
			try {
				result = HttpUtil.doHttpPost(SOCKET_URL, param);
				log.info("获取停车费信息fetchParkingFee--FRTK，道闸返回信息：" + result);
				if(StringUtils.isBlank(result)){
					return new ParkingFeeDto(ParkingFeeDto.SOCKET_SERVER_EXCEPTION, "服务器返回数据为空"); 
				}
				JSONObject resObj = JSON.parseObject(result);
				int code = resObj.getIntValue("code");
				String msg = resObj.getString("msg");
				if (code != 0) {
					return new ParkingFeeDto(code, msg); 
				}
				JSONObject js = JSON.parseObject(resObj.getString("extrDatas"));
				//此处又从新请求了一次socket-server，原因：socket-server有时候会返回{"extrDatas":null,"code":0,"msg":"操作成功"}
				if(js == null){
					Thread.sleep(700);
					result = HttpUtil.doHttpPost(SOCKET_URL, param);
					log.info("获取停车费信息fetchParkingFee--FRTK，道闸返回信息：" + result);
					if(StringUtils.isBlank(result)){
						return new ParkingFeeDto(ParkingFeeDto.SOCKET_SERVER_EXCEPTION, "服务器返回数据为空"); 
					}
					resObj = JSON.parseObject(result);
					code = resObj.getIntValue("code");
					msg = resObj.getString("msg");
					if (code != 0) {
						return new ParkingFeeDto(code, msg); 
					}
					js = JSON.parseObject(resObj.getString("extrDatas"));
					if(js == null){
						return new ParkingFeeDto(ParkingFeeDto.SOCKET_SERVER_EXCEPTION, "服务器返回数据为空"); 
					}
				}
				Long intime = js.getLongValue("intime");//停车时长
				//Long parkingTime = new Date().getTime() / 1000 - intime;
				String barrierGateOrderNo = js.getString("orderNum");
				ParkingFeeDto feeDto = new ParkingFeeDto(code, msg, intime, parkingCar.getArriveTime().getTime() / 1000,
						communityNo, communityName, license, barrierGateOrderNo,js.getBigDecimal("fee").divide(new BigDecimal(100)));
				
				List<RStoprecord> paidRecords = stoprecordService.findAllPaidRecord(communityNo, license, arrivedTime);
				BigDecimal paidTotalFee = new BigDecimal(0);
				if(!CollectionUtils.isEmpty(paidRecords)){
					for(RStoprecord paidRecord : paidRecords){
						if(paidRecord.getFee() != null){
							paidTotalFee = paidTotalFee.add(paidRecord.getFee());
						}
						if (paidRecord.getDeductibleFee()!=null) {
							paidTotalFee=paidTotalFee.add(paidRecord.getDeductibleFee());
						}
					}
				}
				
				feeDto.setPaidFee(paidTotalFee);
				if(feeDto.getRet() == ParkingFeeDto.RET_SUCCESS && feeDto.getUnpaidFee() != null){
					feeDto.setTotalFee(paidTotalFee.add(feeDto.getUnpaidFee()));
				}else{
					feeDto.setTotalFee(null);
				}
				feeDto.setParkingCarId(parkingCar.getId());
				return feeDto;
			} catch (Exception e) {
				log.error("获取临时停车费信息时出现异常,参数" + JSON.toJSONString(param),e);
				return new ParkingFeeDto(ParkingFeeDto.EXCEPTION,e.getMessage());
			}
//			break;
		case 2:
			String url = DWON_REQUEST_URL;
			// Map<String, Object> map_parm = new HashMap<String, Object>();
			Map<String, String> mapLP = new HashMap<String, String>();
			JSONObject bizData2 = new JSONObject();
			bizData2.put("version", "1");
			bizData2.put("license", license);
			bizData2.put("userId", userName);
			bizData2.put("leaveTime", new Date().getTime() / 1000 + ""); //单位:秒  unix时间戳
			bizData2.put("parkingType", parkingCar.getParkingType());
			mapLP.put("interface_lanpeng", "51");
			mapLP.put("bizData", bizData2.toJSONString());
			mapLP.put("communityNo", communityNo);
			String result2 = null;
			try {
				result2 = HttpUtil.doHttpPost(url, mapLP);
				log.info("获取停车费信息fetchParkingFee--LP，道闸返回信息：" + result2);
				if(StringUtils.isBlank(result2)){
					return new ParkingFeeDto(ParkingFeeDto.SOCKET_SERVER_EXCEPTION, "服务器返回数据为空"); 
				}else{
					log.info("查询临时停车费信息(蓝鹏)--->result="+result2);
				}
				JSONObject resObj = JSON.parseObject(result2);
				int code = resObj.getIntValue("code");
				String msg = resObj.getString("msg");
				if (code != 0) {
					return new ParkingFeeDto(code, msg); 
				}
				
				JSONArray jsonArray = resObj.getJSONArray("extrDatas");
//				jsonObject.getLong("arriveTime");//停车开始时间
				if(CollectionUtils.isEmpty(jsonArray)){
					return new ParkingFeeDto(ParkingFeeDto.SOCKET_SERVER_EXCEPTION, "服务器返回数据为空"); 
				}
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				ParkingFeeDto feeDto = new ParkingFeeDto(code, msg, jsonObject.getLong("parkingHour"),parkingCar.getArriveTime().getTime() / 1000, communityNo, communityName, license,"",jsonObject.getBigDecimal("fee").divide(new BigDecimal(100)));
				//查询某次来车已经支付的订单记录
				List<RStoprecord> paidRecords = stoprecordService.findAllPaidRecord(communityNo, license, arrivedTime);
				BigDecimal paidTotalFee = new BigDecimal(0);
				if(!CollectionUtils.isEmpty(paidRecords)){
					for(RStoprecord paidRecord : paidRecords){
						if(paidRecord.getFee() != null){
							paidTotalFee = paidTotalFee.add(paidRecord.getFee());
						}
						if (paidRecord.getDeductibleFee()!=null) {
							paidTotalFee=paidTotalFee.add(paidRecord.getDeductibleFee());
						}
					}
				}
				feeDto.setPaidFee(paidTotalFee);//已经支付的费用
				if(feeDto.getRet() == ParkingFeeDto.RET_SUCCESS && feeDto.getUnpaidFee() != null){
					feeDto.setTotalFee(paidTotalFee.add(feeDto.getUnpaidFee()));
				}else{
					feeDto.setTotalFee(null);
				}
				feeDto.setParkingCarId(parkingCar.getId());
				return feeDto;
			} catch (Exception e) {
				log.error("获取临时停车费信息时出现异常,参数" + JSON.toJSONString(mapLP),e);
				return new ParkingFeeDto(ParkingFeeDto.EXCEPTION,e.getMessage());
			}
//			break;
		case 3:
			return factory.getParkingFee(community, parkingCar, license);
		default:
			String dfmsg = "小区"+communityNo+"["+communityName+"]"+"错误的executeMethod("+executeMethod+")";
			log.info(dfmsg);
			return new ParkingFeeDto(ParkingFeeDto.EXECUTE_METHOD_WRONG,dfmsg);
		}
	}
	
	public BigDecimal pointExchangeAmount(BigDecimal amount,String vouchersNum, int points,String userName) {
		BigDecimal totalAmount =new BigDecimal(0);
		if(points>0||StringUtils.isNotBlank(vouchersNum)){//本订单存在积分兑换或者代金券
			String pointsDeduction="100";// XX积分抵扣1元		
			String pointsmaxcount="0";// 订单最多抵扣XX积分(默认0 不限)	
			String pointssingleamount="0";// 订单消费满XX金额可以使用(默认0 不限)
			
			Map<String, String> pointsConfigMap = sysconfigServiceImpl.getParentValue("POINTS");//读取缓存数据	
			if(pointsConfigMap!=null&&pointsConfigMap.size()>0){
				pointsDeduction=pointsConfigMap.get("POINTSDEDUCTION");
				pointsmaxcount=pointsConfigMap.get("POINTSMAXCOUNT");
				pointssingleamount=pointsConfigMap.get("POINTSSINGLEAMOUNT");
			}
			if (amount.compareTo(new BigDecimal(pointssingleamount))==1||"0".equals(pointssingleamount)) {
				int pointsDeductionint= MStringUntils.isNumber(pointsDeduction)?Integer.valueOf(pointsDeduction):100;
				pointsDeductionint=(pointsDeductionint==0)?1:pointsDeductionint;
				BigDecimal pointsDeductionDouble=new BigDecimal(pointsDeductionint);
				BigDecimal pointsDouble=new BigDecimal(points);
				//int Amount=points/pointsDeductionint;
				//积分抵扣金额
				totalAmount=pointsDouble.divide(pointsDeductionDouble);
				if (totalAmount.compareTo(new BigDecimal(pointsmaxcount))==1&&!"0".equals(pointsmaxcount)) {
					totalAmount=new BigDecimal(pointsmaxcount);
				}
			}
			if (vouchersNum!=null) {
				AxdVouchersRecord axdVouchersRecord= axdVouchersRecordServiceImpl.findUsedDishVouchersByNum(userName,vouchersNum, UsedStatus.Available);
				if(axdVouchersRecord!=null){
					totalAmount =totalAmount.add(axdVouchersRecord.getVouchersMoney());
				}
			}
		}
		return totalAmount;
	}
	@Override
	public AppMessage genTempParkingRecord(String userName, Long parkingCarId, Integer payType,BigDecimal fee,String vouchersNum,int points) {
		
		//一、获取本次临时停车费 + 逃欠费
		
		if(payType == null || StringUtils.isEmpty(userName) || parkingCarId == null || parkingCarId <= 0){
			return AppMessage.error("请求参数为空");
		}
//		if(payType < 0 && payType > 5){
//			return AppMessage.error("请求参数错误");
//		}
		// 0钱包支付，1微信支付，2支付宝支付，3 银行卡 4、现金 5、停车卡 6、钱包-停车卡
		if(payType != 0 && payType != 1 && payType != 2 && payType != 5){
			return AppMessage.error("请求参数错误");
		}
		//来车记录
		PParkingcar parkingCar = this.find(parkingCarId);
		if(parkingCar == null){
			return AppMessage.codeMessage(60001, "没有查询到相应的来车记录(ID=" + parkingCarId + ")");
		}
		Date arrivedTime = parkingCar.getArriveTime();
		String communityNo = parkingCar.getCommunityNo();
		String license = parkingCar.getLicense();
		CommunityModel community = communityService.findByNumber(communityNo);
		if(community == null){
			return AppMessage.codeMessage(60004, "本小区未开通临时车缴费功能");
		}
		try {
			if(community != null){
				byte disableTmpParkingFeeAgr = community.getDisableTmpParkingFeeAgr();
				if(disableTmpParkingFeeAgr == 1){// 0开通协议 1关闭协议
					return AppMessage.codeMessage(60004, "本小区未开通临时车缴费功能");
				}
			}
		} catch (Exception e) {
			log.error("临时停车生成订单时，获取是否开通临时车缴费功能协议时异常 genTempParkingRecord", e);
			return AppMessage.codeMessage(60004, "本小区未开通临时车缴费功能");
		}
		
		//查找逃欠费记录
		List<EscapeRecord> escapeRecordList = escapeRecordServiceImpl.findByUserNameAndRecoveryStatus(userName,EscapeRecord.RecoveryStatus.no_recovery);
		BigDecimal escapeFeeTotal = new BigDecimal(0);
		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(escapeRecordList)){
			for (EscapeRecord escapeRecord : escapeRecordList) {
				BigDecimal escapeFee = escapeRecord.getEscapeFee();
				if(escapeFee != null){
					escapeFeeTotal = escapeFeeTotal.add(escapeFee);
				}
				break;
			}
		}
		//临时车 停车费 + 欠费
		BigDecimal unpaidFee=new BigDecimal(0);
		ParkingFeeDto feeDto=null;
		//临时车 停车费
		BigDecimal stopFee=new BigDecimal(0);
		if(fee!=null){
			unpaidFee=fee;
			feeDto=new ParkingFeeDto(0, "", 0l, 0l, parkingCar.getCommunityNo(), community.getCommunityName(), parkingCar.getLicense(), null, unpaidFee);
			feeDto.setTotalFee(unpaidFee);
			feeDto.setUnpaidFee(unpaidFee);
			feeDto.setStopType(PParkingcar.StopType.Temp_Parking.ordinal());
		}else{
			feeDto = this.getParkingFee(userName, license, community,parkingCar);
			if(feeDto.getRet() != ParkingFeeDto.RET_SUCCESS){
				return AppMessage.codeMessage(60002, "无法获取停车费用(license:"+parkingCar.getLicense()+"),请重试");
			}
			feeDto.setEscapeFee(escapeFeeTotal);
			//临时车 停车费
			stopFee = feeDto.getTotalFee();
			feeDto.setTotalFee(feeDto.getTotalFee().add(escapeFeeTotal));
			feeDto.setUnpaidFee(feeDto.getUnpaidFee().add(escapeFeeTotal));
			//临时车 停车费 + 欠费
			unpaidFee = feeDto.getUnpaidFee();
		}
		if(unpaidFee == null){
			return AppMessage.codeMessage(60002, "无法获取停车费用(license:"+parkingCar.getLicense()+"),请重试");
		}
		if(unpaidFee.doubleValue() == 0){
			return AppMessage.codeMessage(60003, "无需支付");
		}
		String orderNo = OrderNumPrefixConstraint.getInstance().genOrderNo(OrderNumPrefixConstraint.PREFIX_TEMP_PARKING_FEE_PAY, 10);//长度<=32
		
		String barrierGateOrderNo = feeDto.getBarrierGateOrderNo();//道闸生成的orderNo,存入缓存.(新福瑞泰克)
		if(StringUtils.isNotBlank(barrierGateOrderNo)){
			redisService.set("PParkingcarServiceImpl.genTempParkingRecord.barrierGateOrderNo:"+orderNo, barrierGateOrderNo,60 * 60 * 24);
		}
		
		//2.0版本修改 积分代金券抵扣金额 计算抵扣金额
		BigDecimal exchangeAmount = pointExchangeAmount(unpaidFee,vouchersNum, points,userName);
		unpaidFee=unpaidFee.subtract(exchangeAmount);
		if (unpaidFee.compareTo(new BigDecimal(0))==-1) {
			unpaidFee=new BigDecimal(0);
		}
		feeDto.setUnpaidFee(unpaidFee);
		//二、根据支付方式生成停车收费记录（停车卡、电子账户需通知道闸）
		
		UserGoldCardAccountDto userGoldCardAccountDto = null;
		SMemberAccount account = sMemberAccountServiceImpl.findByUserNameUserTypeStatus(userName,(byte)Member.Type.member.ordinal(),(byte)SMemberAccount.Status.usable.ordinal());
		Date now = new Date();
		//支付宝、微信 支付
		if(payType == RStoprecord.PayType.Ali_Pay.ordinal() || payType == RStoprecord.PayType.Weixin_Pay.ordinal()){
			
		}else if(payType == RStoprecord.PayType.Gold_Card.ordinal() || payType == RStoprecord.PayType.Member_Account.ordinal()){//停车卡、电子钱包 支付
			if(payType == RStoprecord.PayType.Gold_Card.ordinal()){
				userGoldCardAccountDto = userGoldCardAccountServiceImpl.findByUserName(userName);
				if(userGoldCardAccountDto == null){
					return AppMessage.codeMessage(60011, "停车卡账户不存在");
				}
				//停车卡账户余额
				BigDecimal balance = userGoldCardAccountDto.getBalance();
				if(balance == null || balance.compareTo(unpaidFee) == -1){//停车卡余额小于停车费，提示前端余额不足
					return AppMessage.codeMessage(60012, "停车卡账户余额不足");
				}
			}else if(payType == RStoprecord.PayType.Member_Account.ordinal()){
				if(account == null){
					return AppMessage.codeMessage(60013, "电子钱包不存在");
				}
				//电子账户余额
				BigDecimal basicAccount = account.getBasicAccount();
				if(basicAccount == null || basicAccount.compareTo(unpaidFee) == -1){//电子余额小于停车费，提示前端余额不足
					return AppMessage.codeMessage(60014, "电子钱包余额不足");
				}
			}
			//手动输入也要通知道闸，但是不已道闸为准
			try {
				//临时停车费通知道闸系统
				JSONObject resObj = this.tempParkingFeeNotifyGateway(stopFee, orderNo,arrivedTime.getTime() / 1000L, now.getTime() / 1000L, license,community);
				if(fee == null){
					if(resObj == null){
						log.info("临时停车费停车卡支付出现返回结果字符串空值异常");
						return AppMessage.codeMessage(60010, "支付失败");
					}
					if((StringUtils.isBlank(resObj.getString("code")) || !"0".equals(resObj.getString("code")))){
						log.info("临时停车费停车卡支付返回结果不成功" + resObj.toJSONString());
						return AppMessage.codeMessage(60010, "支付失败");
					}
				}
			} catch (Exception e) {
				log.error("临时停车费停车卡支付异常", e);
				return AppMessage.codeMessage(60010, "支付失败");
			}
			
		}
		RStoprecord stopRecord = stoprecordService.findLatestNotPaidRecord(communityNo, license, arrivedTime);
		if(stopRecord == null){
//			RStoprecord rstopRe= stoprecordService.findRecentPaidRecord(communityNo, license, arrivedTime);
//			if(rstopRe!=null){
//				arrivedTime=rstopRe.getLeaveTime();
//			}		
			stopRecord = new RStoprecord();
		}
		stopRecord.setAreaId(community.getAreano());
		stopRecord.setParkingId(parkingCar.getParkingId() == null ? (community.getId().intValue()):parkingCar.getParkingId());
		stopRecord.setParkingNumber(parkingCar.getCommunityNo());
		stopRecord.setParkingName(community.getCommunityName());
//		stopRecord.setBerthId(berthId);
//		stopRecord.setBerthNo(berthNo);
		stopRecord.setOutTradeNo(orderNo);
		stopRecord.setVipOrderNo(null);
		stopRecord.setLicense(parkingCar.getLicense());
		stopRecord.setArriveTime(arrivedTime);
		int duration = (int)(now.getTime() / 1000L - arrivedTime.getTime() / 1000L);
		stopRecord.setLeaveTime(now);// TODO 走车时有走车汇报来修改
		stopRecord.setDeration(duration);//停车时长(秒)
		stopRecord.setReceivedMoney(new BigDecimal(0));//预收,单位元
		stopRecord.setReceivablesMoney(feeDto.getTotalFee());//应收,单位元
		stopRecord.setFee(feeDto.getUnpaidFee());
//		stopRecord.setFeeType(0);
		stopRecord.setStopType(PParkingcar.StopType.Temp_Parking.ordinal());
		stopRecord.setPayType(payType);//0钱包支付，1微信支付，2支付宝支付，3 银行卡 4、现金 5、停车卡 6、钱包-停车卡
		stopRecord.setCreateTime(now);
		stopRecord.setArriveCarImgUrl(parkingCar.getArriveCarImgUrl());
//		stopRecord.setLeaveCarImgUrl();
		stopRecord.setUserName(userName);
//		stopRecord.setRealName("");
		stopRecord.setBranchId(null);
//		stopRecord.setHeadOfficeId();
		stopRecord.setPopertyNumber(community.getProperty_number());
//		stopRecord.setFeePayStatus(RStoprecord.FeePayStatus.Not_Paid.ordinal());
		stopRecord.setDeductionsType(0);//扣费方式 :默认0;手动支付1;自动扣费 2;微信公众号3;扫二维码
		stopRecord.setPoints(points);
		stopRecord.setVouchersNum(vouchersNum);
		stopRecord.setIsReturn(RStoprecord.IsReturn.NotReturn.ordinal());
		stopRecord.setDeductibleFee(exchangeAmount);
		if (fee!=null) {
			stopRecord.setOverTimeFee(fee);
		}
		
		//三、停车卡、电子账户需通知道闸成功，扣费生成相应记录
		
		//根据支付方式修改支付状态
		if(payType == RStoprecord.PayType.Ali_Pay.ordinal() || payType == RStoprecord.PayType.Weixin_Pay.ordinal()){
			stopRecord.setFeePayStatus(RStoprecord.FeePayStatus.Not_Paid.ordinal());
		}else if(payType == RStoprecord.PayType.Gold_Card.ordinal()){//停车卡支付成功SUCCESS
			stopRecord.setFeePayStatus(RStoprecord.FeePayStatus.Paid.ordinal());
			stopRecord.setPaidDate(new Date());
			
			BigDecimal balance = userGoldCardAccountDto.getBalance();
			userGoldCardAccountDto.setBalance(balance.subtract(unpaidFee).setScale(2,BigDecimal.ROUND_HALF_UP));
			UserGoldCardAccount userGoldCardAccount = new UserGoldCardAccount();
			BeanUtils.copyProperties(userGoldCardAccountDto, userGoldCardAccount);
			userGoldCardAccountServiceImpl.update(userGoldCardAccount);
			
			GoldCardTransferRecord goldCardTransferRecord = goldCardTransferRecordServiceImpl.genGoldCardTransferRecord(
					userGoldCardAccount.getBalance(), unpaidFee, "停车扣费", GoldCardTransferRecord.Type.Temp_Parking_Fee.ordinal(), userName, 0, orderNo);
			goldCardTransferRecordServiceImpl.save(goldCardTransferRecord);
			
			//存在欠费，将其追缴
			if(!CollectionUtils.isEmpty(escapeRecordList)){
				for (int i = 0; i < escapeRecordList.size(); i++) {
					EscapeRecord escapeRecord = escapeRecordList.get(i);
					//将此条逃欠费追缴
					escapeRecord.setRecoveryStatus((byte)EscapeRecord.RecoveryStatus.recoveryed.ordinal());
					escapeRecord.setClearTime(new Date());
					escapeRecord.setRecoveryMode((byte)EscapeRecord.RecoveryMode.goldcard.ordinal());//停车卡追缴
					escapeRecord.setRecoveryFee(escapeRecord.getEscapeFee());
					escapeRecordServiceImpl.update(escapeRecord);
					//只操作最新一条记录
					if(i == 0){
						//账户余额
						account.setBasicAccount(account.getBasicAccount().add(escapeRecord.getEscapeFee()));
						sMemberAccountServiceImpl.update(account);
						//流转记录
						RAccountTransferRecord rAccountTransferRecord = rAccountTransferRecordServiceImpl.genAcoountRecords(account, null, 
								escapeRecord.getEscapeFee().doubleValue(),community.getCommunity_number(),(byte)RAccountTransferRecord.UserType.Member.ordinal(),
								RAccountTransferRecord.Type.chargeEscape , "停车欠费停车卡追缴");
						rAccountTransferRecordServiceImpl.save(rAccountTransferRecord);
					}
				}
			}
				
		}else if(payType == RStoprecord.PayType.Member_Account.ordinal()){//电子钱包支付成功SUCCESS
			stopRecord.setFeePayStatus(RStoprecord.FeePayStatus.Paid.ordinal());
			stopRecord.setPaidDate(new Date());
			
			//电子钱包扣钱
			account.setBasicAccount(account.getBasicAccount().subtract(unpaidFee).setScale(2, BigDecimal.ROUND_HALF_UP));
			account.setConsumAmount(account.getConsumAmount().add(unpaidFee).setScale(2, BigDecimal.ROUND_HALF_UP));
			sMemberAccountServiceImpl.update(account);
			
			//流转记录
			RAccountTransferRecord rAccountTransferRecord = rAccountTransferRecordServiceImpl.genAcoountRecords(account, orderNo, unpaidFee.doubleValue(), 
					community.getCommunity_number(),(byte)RAccountTransferRecord.UserType.Member.ordinal(), RAccountTransferRecord.Type.expenseCarStop , "停车消费");
			rAccountTransferRecordServiceImpl.save(rAccountTransferRecord);
			
		}
		
		if(payType == RStoprecord.PayType.Gold_Card.ordinal() || payType == RStoprecord.PayType.Member_Account.ordinal()){
			//获取停车滞留时间
			Integer stayTime = this.getStayTime(community, license);
			if(stayTime==null){
				stayTime=15;
			}
			//添加公告信息
			NoticeSource source = new NoticeSource();
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//设置日期格式
			source.createNotice(userName, "您的车【"+license+"】已经支付停车费用，请在"+stayTime+"分钟内出场，否则会二次计费！",df.format(new Date()), NoticeType.TEMPORARY_PARKING_NOTICE, redisService);
			
			//调用积分获得公共方法 start
			PointGiftVo vo = new PointGiftVo(PointGiftBusinessType.PURCHASE,userName,community.getCommunity_number(),
					unpaidFee.subtract(escapeFeeTotal),PointTransferRecord.SubType.Temp_Parking);
			PointGiftDto pointGiftDto = pointTransferRecordServiceImpl.pointGiftByBusiness(vo);
			boolean giftResult = pointGiftDto.isGiftResult();
			Integer addPoint = pointGiftDto.getGiftPoint();
			if(!giftResult){
				addPoint = 0;
			}
			//调用积分获得公共方法 end
			if(addPoint>=0){
				//TODO 赠送积分
//				Integer addpoint=sMemberAccountServiceImpl.addUserPointByPayType(userName,unpaidFee.subtract(escapeFeeTotal), PointTransferRecord.SubType.Temp_Parking,community.getCommunity_number(), community.getProperty_number(), community.getBranchId());
				this.gainGiftPointByPayTempParking(orderNo, feeDto.getUnpaidFee(), addPoint,stayTime);
			}
		}
		
		// 扣除积分 代金券抵扣了变为不可用 （同理定时器方法相反操作）
		RefundDeductedPointVouchers pointVouchers=new RefundDeductedPointVouchers();	
//		feePayType.ordinal()
		pointVouchers.refundDeductedPoint("临时车订单支付",userName, 2, points, memberService, sMemberAccountServiceImpl, communityModelService, pointTransferRecordServiceImpl);
		pointVouchers.refundDeductedVouchers(userName,vouchersNum, 2, axdVouchersRecordService);

		stoprecordService.saveOrUpdate(stopRecord);
		
		feeDto.setOutTradeNo(orderNo);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("data", feeDto);
		map.put("serverTime", DateFormatUtils.format(new Date(), DEFAULT_DATE_FORMATTER));
		return AppMessage.success("ok", map);
	}
	
	/**
	 * 临时停车费微信支付回调 针对新版本app(点滴洗)
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Override
	public boolean weixiCallback(WeixinPaymentCallBackData callbackData) {
		return this.processCallback(callbackData,null,0);
	}
	
	@Override
	public void gainGiftPointByPayTempParking(String outTradeNo,BigDecimal totalFeeYuan,Integer addpoint,Integer stayTime){
		//缓存信息
		PaymentResultCacheDto paymentResult = new PaymentResultCacheDto();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		paymentResult.setPaidDate(sf.format(new Date()));
		paymentResult.setNotifyDate(sf.format(new Date()));
		paymentResult.setTotalFee(totalFeeYuan);
		paymentResult.setBalance(BigDecimal.ZERO);
		if(addpoint>0){
			paymentResult.setDescritionMsg("恭喜你，您本次消费获得了"+addpoint+"会员积分！本停车场支持月租服务，使用月租服务有更多优惠。");
		}else{
			paymentResult.setDescritionMsg("本停车场支持月租服务，使用月租服务有更多优惠。");
		}
		paymentResult.setWarnMessage("请在"+stayTime+"分钟内驶出停车场");
		paymentResult.setPaymentResult(PaymentResultCacheDto.PaymentResult.NOTIFY_SUCCESS);
		
		redisService.set(PaymentResultCacheDto.CACHE_KEY+outTradeNo, paymentResult,PaymentResultCacheDto.CACHE_EXPIRATION);
		
	}
	/**
	 * 获取停车滞留时间
	 * @param communityNo 停车场编号
	 * @param license 车牌号
	 * @return
	 * @throws Exception
	 */
	public Integer getStayTime(CommunityModel community,String license){
		Integer stayTime=null;//停车滞留时间
		String communityNo=community.getCommunity_number();
		stayTime = redisService.get(String.format(CacheNameDefinition.Parking_Retention_Time, communityNo),Integer.class);
		if(stayTime==null){
			int executeMethod = community.getExecuteMethod();
			HttpHeaders headers = new HttpHeaders();
		    headers.add("U-Authorization", "68EE908C6365B641DC449B909D405870");
		    MultiValueMap<String, String> map_parm = new LinkedMultiValueMap<String, String>();
			switch(executeMethod){
				case 0:
				case 1:
					Map<String, String> data = new HashMap<String, String>();
					data.put("licensePlateNumber", license);
					String bizData = new Gson().toJson(data);
					map_parm.add("interfaceType", "11");
					map_parm.add("bizData", bizData);
					map_parm.add("communityNumber", communityNo);

				    HttpEntity<MultiValueMap<String, String>> requestEntity  = new HttpEntity<MultiValueMap<String, String>>(map_parm,headers);
					com.alibaba.fastjson.JSONObject resObj = null;
					try {
						SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
				        requestFactory.setReadTimeout(5000);
				        requestFactory.setConnectTimeout(5000);
						org.springframework.web.client.RestTemplate rs = new org.springframework.web.client.RestTemplate(requestFactory);
						String jsonStr = rs.postForObject(SOCKET_URL, requestEntity, String.class);
						log.info("[生成临时停车待支付订单接口genTempParkingRecord--FRTK]道闸返回信息：" + jsonStr);
						resObj = JSON.parseObject(jsonStr);
						if (0 != resObj.getIntValue("code")) {
							log.info("[生成临时停车待支付订单接口genTempParkingRecord--FRTK]获取车辆离场滞留时间失败");
						}else{
							String resultStr = resObj.getString("extrDatas");
							com.alibaba.fastjson.JSONObject result = JSON.parseObject(resultStr);
							stayTime=result.getInteger("delayTime");
						}
					} catch (Exception e) {
						log.error("[生成临时停车待支付订单接口genTempParkingRecord--FRTK]获取车辆离场滞留时间异常", e);
					}
					break;
				case 3:
					stayTime=factory.getStayTime(community, license);
					break;
			}
		}
		return stayTime;
	}
	
	/**
	 * 临时停车费通知道闸系统
	 * @param totalFee 停车费
	 * @param outTradeNo 订单号
	 * @param arrivedTimeStamp 来车时间戳
	 * @param nowTimeStamp 当期时间戳
	 * @param license 车牌号
	 * @param community 小区
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	private JSONObject tempParkingFeeNotifyDaozha(BigDecimal totalFee,String outTradeNo,long arrivedTimeStamp,long nowTimeStamp, String license,CommunityModel community) throws Exception{
		String communityNum = community.getCommunity_number();
		int executeMethod = community.getExecuteMethod();
		String resJson = "";
		JSONObject bizData = new JSONObject();
		switch(executeMethod){
			case 1:
				
//				private String communityNumber;// 小区编号
//				private String licensePlateId;// 车牌ID
//				private String licensePlateNumber;// 车牌号
//				private Integer licensePlateCommand;// 命令
//				private Integer commandParameter;// 命令参数
//				private Integer timeoutPeroid;
				
				String barrierGateOrderNo = redisService.get("PParkingcarServiceImpl.genTempParkingRecord.barrierGateOrderNo:" + outTradeNo);
				log.info("临时停车费通知道闸系统获取到的"+"PParkingcarServiceImpl.genTempParkingRecord.barrierGateOrderNo:" + outTradeNo +
						license + "-" + communityNum+"为:"+barrierGateOrderNo);
				Map<String, String> map_parm = new HashMap<String, String>();
				bizData.put("licensePlateId", barrierGateOrderNo);
				bizData.put("licensePlateNumber", license);
				bizData.put("licensePlateCommand", 4105);
				bizData.put("commandParameter", arrivedTimeStamp);//lparam
				bizData.put("timeoutPeroid", nowTimeStamp);//wpram
				
				map_parm.put("interfaceType", "19"); //传递一个大于8的interfaceType即可
				map_parm.put("bizData", bizData.toJSONString());
				map_parm.put("communityNumber", community.getCommunity_number());
				resJson = HttpUtil.doHttpPost(this.SOCKET_URL, map_parm);
				log.info("[临时停车费通知道闸系统 tempParkingFeeNotifyDaozha--FRTK]道闸返回信息：" + resJson);
				break;
			case 2:
				String url = DWON_REQUEST_URL;
				Map<String, String> mapLP = new HashMap<String, String>();
				bizData.put("version", 1);
				bizData.put("amount", totalFee);
				bizData.put("payType", RStoprecord.PayType.Weixin_Pay.ordinal());
				bizData.put("license", license);
				mapLP.put("bizData", bizData.toJSONString());
				mapLP.put("communityNo", communityNum);
				mapLP.put("interface_lanpeng", "21");
				resJson = HttpUtil.doHttpPost(url, mapLP);
				log.info("[临时停车费通知道闸系统 tempParkingFeeNotifyDaozha--LP]道闸返回信息：" + resJson);
				break;
			default : 
				break;
		}
		if(StringUtils.isBlank(resJson)){
			return null;
		}else{
			return JSON.parseObject(resJson);
		}
	}
	
	@Override
	public JSONObject tempParkingFeeNotifyGateway(final BigDecimal totalFee,final String outTradeNo,final long arrivedTimeStamp,final long nowTimeStamp, final String license,final CommunityModel community) throws Exception{
		String communityNum = community.getCommunity_number();
		int executeMethod = community.getExecuteMethod();
		String resJson = "";
		JSONObject bizData = new JSONObject();
		switch(executeMethod){
			case 1:
				
//				private String communityNumber;// 小区编号
//				private String licensePlateId;// 车牌ID
//				private String licensePlateNumber;// 车牌号
//				private Integer licensePlateCommand;// 命令
//				private Integer commandParameter;// 命令参数
//				private Integer timeoutPeroid;
				
				String barrierGateOrderNo = redisService.get("PParkingcarServiceImpl.genTempParkingRecord.barrierGateOrderNo:" + outTradeNo);
				log.info("临时停车费通知道闸系统获取到的"+"PParkingcarServiceImpl.genTempParkingRecord.barrierGateOrderNo:" + outTradeNo +
						license + "-" + communityNum+"为:"+barrierGateOrderNo);
				Map<String, String> map_parm = new HashMap<String, String>();
				bizData.put("licensePlateId", barrierGateOrderNo);
				bizData.put("licensePlateNumber", license);
				bizData.put("licensePlateCommand", 4105);
				bizData.put("commandParameter", arrivedTimeStamp);//lparam
				bizData.put("timeoutPeroid", nowTimeStamp);//wpram
				
				map_parm.put("interfaceType", "19"); //传递一个大于8的interfaceType即可
				map_parm.put("bizData", bizData.toJSONString());
				map_parm.put("communityNumber", community.getCommunity_number());
				try{
					resJson = HttpUtil.doHttpPost(this.SOCKET_URL, map_parm);
				}catch (Exception e) {
					log.error("下发socket服务器异常",e);
				}
				log.info("[临时停车费通知道闸系统 tempParkingFeeNotifyGateway--FRTK]道闸返回信息：" + resJson);
				break;
			case 2:
				String url = DWON_REQUEST_URL;
				Map<String, String> mapLP = new HashMap<String, String>();
				bizData.put("version", 1);
				bizData.put("amount", totalFee);
				bizData.put("payType", RStoprecord.PayType.Weixin_Pay.ordinal());
				bizData.put("license", license);
				mapLP.put("bizData", bizData.toJSONString());
				mapLP.put("communityNo", communityNum);
				mapLP.put("interface_lanpeng", "21");
				try{
					resJson = HttpUtil.doHttpPost(url, mapLP);
				}catch (Exception e) {
					log.error("下发蓝鹏云服务器异常",e);
				}
				log.info("[临时停车费通知道闸系统 tempParkingFeeNotifyGateway--LP]道闸返回信息：" + resJson);
				break;
			case 3:
				resJson = factory.tempParkingFeeNotifyGateway(outTradeNo, license, totalFee, community);
				break;
			default : 
				break;
		}
		JSONObject jsObject = null;
		if(StringUtils.isBlank(resJson)){
			jsObject = null;
		}else{
			jsObject = JSON.parseObject(resJson);
		}
		if(jsObject == null || jsObject.getIntValue("code") != 0){
			if(Retry_Notify_ECC_Map.get(outTradeNo) == null){
				RetryNotifyEcc retry = new RetryNotifyEcc(totalFee, outTradeNo, arrivedTimeStamp, nowTimeStamp, license, community);
				Retry_Notify_ECC_Map.put(outTradeNo,retry);
				ThreadPoolFactory.getInstance().addTask(retry);
			}
		}
		
		return jsObject;
	}
	
	/**
	 * 临时停车费支付宝支付回调 针对新版本app(点滴洗)
	 * @param request
	 * @return
	 */
	@Override
	public boolean alipayCallback(AliPaymentCallBackData callbackData) {
		return this.processCallback(null,callbackData,1);
	}
	
	/**
	 * 
	 * @param wxCallbackData 微信回调数据 
	 * @param aliCallbackData 支付宝回调数据
	 * @param payType 0 微信 1 支付宝
	 * @return
	 */
	private boolean processCallback(WeixinPaymentCallBackData wxCallbackData,AliPaymentCallBackData aliCallbackData,int payType){
		String payTypeName = null;
		String outTradeNo = null;
		BigDecimal totalFee = null;
		if(payType == 0){//微信
			payTypeName = "微信";
			outTradeNo = wxCallbackData.getOut_trade_no();
			totalFee = new BigDecimal(wxCallbackData.getTotal_fee()).divide(new BigDecimal(100)); // 不能用cash_fee
		}else if(payType == 1){//支付宝
			payTypeName = "支付宝";
			outTradeNo = aliCallbackData.getOut_trade_no();
			totalFee = new BigDecimal(aliCallbackData.getTotal_fee()); //不能用cash_fee
		}
		RStoprecord stopRecord = stoprecordService.findByOutTradeNo(outTradeNo);

		if(stopRecord == null){
			return false;
		}
		BigDecimal receivablesMoney = stopRecord.getReceivablesMoney() == null ? new BigDecimal(0) : stopRecord.getReceivablesMoney();//应收
		BigDecimal fee = stopRecord.getFee() == null ? new BigDecimal(0) : stopRecord.getFee();//实收
		if(stopRecord.getFeePayStatus() == RStoprecord.FeePayStatus.Paid.ordinal()
				&& fee.compareTo(receivablesMoney) >= 0){
			return true;
		}

		String communityNum = stopRecord.getParkingNumber();
		CommunityModel community = communityService.findByNumber(communityNum);
		Date now = new Date();
		//BigDecimal overTimeFee = stopRecord.getOverTimeFee();//网络异常费用(此情况不以道闸为主)
		try{
			JSONObject resObj = this.tempParkingFeeNotifyGateway(totalFee, outTradeNo, stopRecord.getArriveTime().getTime() / 1000L, 
					now.getTime() / 1000L, stopRecord.getLicense(), community);
			log.info("临时停车缴费通知道闸结果:" + (resObj == null ? "无响应":resObj.toJSONString()));
		}catch(Exception e){
			log.error("通知道闸失败",e);
		}
		try{
			stopRecord.setFeePayStatus(RStoprecord.FeePayStatus.Paid.ordinal());
			stopRecord.setPaidDate(new Date());
			stoprecordService.update(stopRecord);
			
			String userName = stopRecord.getUserName();
			SMemberAccount account = sMemberAccountServiceImpl.findByUserNameUserTypeStatus(userName,(byte)Member.Type.member.ordinal(),(byte)SMemberAccount.Status.usable.ordinal());
			//存在欠费，将其追缴
			List<EscapeRecord> escapeRecordList = escapeRecordServiceImpl.findByUserNameAndRecoveryStatus(userName,EscapeRecord.RecoveryStatus.no_recovery);
			//总逃欠费
			BigDecimal escapeTotal=new BigDecimal(0);
			if(!CollectionUtils.isEmpty(escapeRecordList)){
				for (int i = 0; i < escapeRecordList.size(); i++) {
					EscapeRecord escapeRecord = escapeRecordList.get(i);
					//将此条逃欠费追缴
					escapeRecord.setRecoveryStatus((byte)EscapeRecord.RecoveryStatus.recoveryed.ordinal());
					escapeRecord.setClearTime(new Date());
					escapeRecord.setRecoveryMode((byte)EscapeRecord.RecoveryMode.goldcard.ordinal());//停车卡追缴
					escapeRecord.setRecoveryFee(escapeRecord.getEscapeFee());
					escapeRecordServiceImpl.update(escapeRecord);
					escapeTotal = escapeTotal.add(escapeRecord.getEscapeFee());
					//只操作最新一条记录
					if(i == 0){
						//账户余额
						account.setBasicAccount(account.getBasicAccount().add(escapeRecord.getEscapeFee()));
						sMemberAccountServiceImpl.update(account);
						//流转记录
						RAccountTransferRecord rAccountTransferRecord = rAccountTransferRecordServiceImpl.genAcoountRecords(account, null, 
								escapeRecord.getEscapeFee().doubleValue(),community.getCommunity_number(),(byte)RAccountTransferRecord.UserType.Member.ordinal(),
								RAccountTransferRecord.Type.chargeEscape , "停车欠费"+payTypeName+"追缴");
						rAccountTransferRecordServiceImpl.save(rAccountTransferRecord);
					}
				}
			}
			
			//获取停车滞留时间
			Integer stayTime = this.getStayTime(community, stopRecord.getLicense());
			if(stayTime==null){
				stayTime=15;
			}
			//调用积分获得公共方法 start
			PointGiftVo vo = new PointGiftVo(PointGiftBusinessType.PURCHASE,userName,communityNum,
					totalFee,PointTransferRecord.SubType.Temp_Parking);
			PointGiftDto pointGiftDto = pointTransferRecordServiceImpl.pointGiftByBusiness(vo);
			boolean giftResult = pointGiftDto.isGiftResult();
			Integer addPoint = pointGiftDto.getGiftPoint();
			if(!giftResult){
				addPoint = 0;
			}
			//调用积分获得公共方法 end
			if(addPoint >= 0){
				//TODO 赠送积分
//				Integer addpoint=sMemberAccountServiceImpl.addUserPointByPayType(userName,unpaidFee.subtract(escapeFeeTotal), PointTransferRecord.SubType.Temp_Parking,community.getCommunity_number(), community.getProperty_number(), community.getBranchId());
				this.gainGiftPointByPayTempParking(outTradeNo, totalFee, addPoint,stayTime);
			}
			//添加公告信息
			 NoticeSource source = new NoticeSource();
			 SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//设置日期格式
			 source.createNotice(userName, "您的车【"+stopRecord.getLicense()+"】已经支付停车费用，请在"+stayTime+"分钟内出场，否则会二次计费！",df.format(new Date()), NoticeType.TEMPORARY_PARKING_NOTICE, redisService);
			
			 List<ParkingFeeApplicationRecords> applicationRecords = parkingFeeApplicationRecordsService.findByBusinessNumAndOrderNo(null, outTradeNo);
			 //如果有停车费减免，更新记录
			if(applicationRecords.size()>0){
				ParkingFeeApplicationRecords applicationRecord = applicationRecords.get(0);
				applicationRecord.setFeePayStatus(ParkingFeeApplicationRecords.FeePayStatus.PaySuccess);
				applicationRecord.setPayDate(now);
				applicationRecord.setIsUseServer(ParkingFeeApplicationRecords.IsUseServer.HasUse);
				parkingFeeApplicationRecordsService.update(applicationRecord);
				//生成商户交易记录及修改商户电子钱包余额
				MerchantTransctionRecordSource MerchantTransctionRecordSource = new MerchantTransctionRecordSource(); 
				Integer totalAmount = applicationRecord.getDiscountPrice().setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
				Integer paymentAmount = totalAmount;
				Integer businessIncome = totalAmount;
				MerchantTransctionRecordSource.createMerchantTransctionRecord(totalAmount, paymentAmount, null,null, businessIncome, 
						null, MerchantTransactionRecord.RecordStatus.refunds, MerchantTransactionRecord.RecordType.TemporaryParking, MerchantTransactionRecord.SourceType.merchant,
						MerchantTransactionRecord.TransactionStatus.deduction, applicationRecord.getOrderNo(),applicationRecord.getCommunityNumber(),applicationRecord.getBusinessNum(), null, null, applicationRecord.getLicense(),
						MerchantTransactionRecord.PayType.Paid_Account,"停车费减免费用",pBusinessService, merchantTransactionRecordService, sMemberAccountServiceImpl);
			}
			return true;
		}catch(Exception e){
			log.info(payTypeName + "支付停车费异步通知道闸系统出现返回Runtime Exception",e);
			return false;
		}
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){
			RegisterPaymentCallBackService.registerService(OrderNumPrefixConstraint.PREFIX_TEMP_PARKING_FEE_PAY, getClass());
		}
		
	}


	@Override
	public AppMessage walletAccountCallback(String outTradeNo, BigDecimal totalFee) {
		//do nothing
		return AppMessage.error("未注册的服务");
	}
	
	@Override
	@Transactional
	public long clearDirtyParkingcar(int dayBefore) {
		//1.p_parkingcar表中存在的数据小区/停车场编号没有对应的community或者p_parkinginfo表记录则删除
		int sum = 0;
		Date now = new Date();
		String month = DateFormatUtils.format(now, "yyyy-MM");//当前月份
		List<Long> ids = pParkingcarDao.findParkingcarIdWithoutCommunity(null,null);
		int del1 = 0;
		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(ids)){
			del1 = pParkingcarDao.deleteByIds(ids.toArray(new Long[ids.size()]));
			sum = sum + del1;
		}
		String logStr1 = "清理掉p_parkingcar表中的脏数据-无对应小区的共" + del1 + "条,耗时--->"+(System.currentTimeMillis() - now.getTime())+"毫秒";
		log.info(logStr1);
		
		//2.对于p_parkingcar中来车记录超过一周且license + community_no重复的数据,保留最新的一条删除重复的数据
		Date startArrive = null;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -dayBefore);
		Date endArrive = calendar.getTime();
		Date now2 = new Date();
		ids = pParkingcarDao.findRepeatParkingcar(startArrive, endArrive);
		List<Long> subIds = new ArrayList<Long>();
		int del2 = 0;
		for(int i=0;i<ids.size();i++){
			subIds.add(ids.get(i));
			if(subIds.size() >= 10000){ //每次删除10000条
				del2 = del2 + pParkingcarDao.deleteByIds(ids.toArray(new Long[]{}));
				subIds.clear();
			}
		}
		sum = sum + del2;
		String logStr2 = "清理掉p_parkingcar表中的脏数据-重复的的共" + del2 + "条,耗时--->"+(System.currentTimeMillis() - now2.getTime())+"毫秒";
		log.info(logStr2);
		try {
			if(StringUtils.isNotBlank(RStoprecordJobs.Log_Notice_User)){
				smsService.send(RStoprecordJobs.Log_Notice_User, logStr1 + "," + logStr2);
			}
		} catch (Exception e) {
		}
		return sum;
	}


	@Override
	public List<PParkingcar> findByUserName(String userName) {
		return pParkingcarDao.findByUserName(userName);
	}


	@Override
	public int findAdmissionTodayByParkingId(String communityNo) {		
		return pParkingcarDao.findAdmissionTodayByParkingId(communityNo);
	}


	@Override
	public boolean deleteById(Long id) {
		return pParkingcarDao.deleteById(id);
	}


	@Override
	public boolean deleteByCommunityNumberAndLicense(String communityNo,
			String license) {
		return pParkingcarDao.deleteByCommunityNumberAndLicense(communityNo,license);
	}

	class RetryNotifyEcc implements Runnable{
		BigDecimal totalFee;
		String outTradeNo;
		long arrivedTimeStamp;
		long nowTimeStamp;
		String license;
		CommunityModel community;
		public RetryNotifyEcc(final BigDecimal totalFee,final String outTradeNo,final long arrivedTimeStamp,final long nowTimeStamp, final String license,final CommunityModel community){
			super();
			this.totalFee = totalFee;
			this.outTradeNo = outTradeNo;
			this.arrivedTimeStamp = arrivedTimeStamp;
			this.nowTimeStamp = nowTimeStamp;
			this.license = license;
			this.community = community;
		}
		
		@Override
		public void run() {
			
			//获取重发频率设置
			String retryIntervalConf = sysconfigServiceImpl.getValueByNameSpace(CommunityModel.ECCCONFIG + "." + NOTIFY_ECC_RETRY_INTERVAL);
			List<Integer> retryInterval = new ArrayList<Integer>();
			if(StringUtils.isNotBlank(retryIntervalConf)){
				String[] intervalStr = retryIntervalConf.replaceAll("\\s+", "").split(",");
				try{
					for(int i=0;i<intervalStr.length;i++){
						String s = intervalStr[i];
						if(StringUtils.isNotBlank(s)){
							retryInterval.add(Integer.parseInt(s));
						}
					}
				}catch (Exception e) {
					log.error("转换ECCCONFIG.NOTIFY_ECC_RETRY_INTERVAL配置失败",e);
				}
			}
			if(CollectionUtils.isEmpty(retryInterval)){
				retryInterval.add(1);// 按照1/2/2/3/10/这样的机制来，直到发送成功. 单位:秒
				retryInterval.add(2);
				retryInterval.add(2);
				retryInterval.add(3);
				retryInterval.add(10);
			}
			int tryAlways =retryInterval.get(retryInterval.size() - 1); //按照间隔加大平率重发依然不成功后期的稳定间隔
			int length = retryInterval.size();
			int index = 0;
			boolean success = false;
			while(true){
				String logMsg = "";
				try {
					JSONObject jres = tempParkingFeeNotifyGateway(totalFee, outTradeNo,arrivedTimeStamp,
							nowTimeStamp, license, community);
					if(jres != null && jres.getIntValue("code") == 0){
						logMsg = String.format("重新下发道闸临时停车缴费终于通知成功:totalFee=%s, outTradeNo=%s,arrivedTimeStamp=%s, "
								+ "nowTimeStamp=%s, license=%s, community=%s <> %s",totalFee, outTradeNo,arrivedTimeStamp,
								nowTimeStamp, license, community,jres.toJSONString());
						log.info(logMsg);
						success = true;
					}else{
						logMsg = String.format("重新下发道闸临时停车缴费通知又失败了:totalFee=%s, outTradeNo=%s,arrivedTimeStamp=%s, "
								+ "nowTimeStamp=%s, license=%s, community=%s <> %s",totalFee, outTradeNo,arrivedTimeStamp,
								nowTimeStamp, license, community,jres==null?"null resp":jres.toJSONString());
						log.info(logMsg);
					}
				} catch (Exception e) {
					logMsg = String.format("重新下发道闸临时停车缴费通知又异常了:totalFee=%s, outTradeNo=%s,arrivedTimeStamp=%s, "
							+ "nowTimeStamp=%s, license=%s, community=%s",totalFee, outTradeNo,arrivedTimeStamp,
							nowTimeStamp, license, community);
					log.info(logMsg);
				}
				try {
					redisService.hset(Log_Temp_Parking_Fee_Notify_Gateway_Retry_Key + outTradeNo, 
							DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS") + "-" + String.valueOf(index), logMsg);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(success){
					break;
				}
				try {
					int sleep = 0;
					if(index >= length){
						sleep = tryAlways;
					}else{
						sleep = retryInterval.get(index);
					}
					Thread.sleep(sleep * 1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				index = index + 1;
			}
		}
	}

	@Override
	public List<Map<String, Object>> findGasCardByCommunityNo(String communityNo) {
		return pParkingcarDao.findGasCardByCommunityNo(communityNo);
	}

	@Override
	public List<Map<String, Object>> findGasCardByLicenseList(List<String> licenses) {
		return pParkingcarDao.findGasCardByLicenseList(licenses);
	}
}