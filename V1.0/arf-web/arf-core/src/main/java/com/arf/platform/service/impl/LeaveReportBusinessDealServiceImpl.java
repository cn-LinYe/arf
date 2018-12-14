package com.arf.platform.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arf.core.cache.redis.RedisService;
import com.arf.core.entity.AdministrativeModel;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.LicensePlateModel;
import com.arf.core.entity.Member;
import com.arf.core.entity.ParkRateModel;
import com.arf.core.entity.TemporaryLicensePlate;
import com.arf.core.service.AdministrativeModelService;
import com.arf.core.service.CommunityModelService;
import com.arf.core.service.LicensePlateModelService;
import com.arf.core.service.MemberService;
import com.arf.core.service.ParkRateModelService;
import com.arf.core.service.SmsService;
import com.arf.core.service.SysconfigService;
import com.arf.core.service.TemporaryLicensePlateService;
import com.arf.core.thread.ThreadPoolFactory;
import com.arf.core.util.MStringUntils;
import com.arf.eparking.dto.ParKingRealTimeStatusDto;
import com.arf.eparking.entity.EscapeRecord;
import com.arf.eparking.entity.ParkingOrderRecord;
import com.arf.eparking.service.EscapeRecordService;
import com.arf.eparking.service.ParkingOrderRecordService;
import com.arf.goldcard.dto.UserGoldCardAccountDto;
import com.arf.goldcard.entity.GoldCardTransferRecord;
import com.arf.goldcard.entity.UserGoldCardAccount;
import com.arf.goldcard.service.IGoldCardTransferRecordService;
import com.arf.goldcard.service.IUserGoldCardAccountService;
import com.arf.member.entity.RAccountTransferRecord;
import com.arf.member.service.IRAccountTransferRecordService;
import com.arf.platform.HttpRequestDealBusinessMsg;
import com.arf.platform.LPConstants;
import com.arf.platform.entity.PParKingrealTimeStatus;
import com.arf.platform.entity.PParkingcar;
import com.arf.platform.entity.RStoprecord;
import com.arf.platform.entity.SMemberAccount;
import com.arf.platform.service.BaseService;
import com.arf.platform.service.IParkingUpReportBusinessDealService;
import com.arf.platform.service.IPayModeNotifyService;
import com.arf.platform.service.ISMemberAccountService;
import com.arf.platform.service.PParkingcarService;
import com.arf.platform.service.RStoprecordService;
import com.arf.platform.utils.DateUtils;
import com.arf.platform.vo.DownPayModeNotifyVo;
import com.arf.platform.vo.LeaveReportVo;
import com.arf.platform.vo.RequestDataVo;
import com.arf.traffic.TrafficInterface;

@Transactional
@Service("leaveReportBusinessDealServiceImpl")
public class LeaveReportBusinessDealServiceImpl extends BaseService implements IParkingUpReportBusinessDealService {
	
	private static Logger log = LoggerFactory.getLogger(LeaveReportBusinessDealServiceImpl.class);

	@Resource(name = "PParkingcarServiceImpl")
	private PParkingcarService pParkingcarServiceImpl;
	
	@Resource(name = "RStoprecordServiceImpl")
	private RStoprecordService rStoprecordServiceImpl;
	
	/**|
	 * 白名单（停车费表）服务
	 */
	@Resource(name = "parkRateModelServiceImpl")
	private ParkRateModelService parkRateModelServiceImpl;
	
	/**|
	 * 车牌表服务
	 */
	@Resource(name = "licensePlateModelServiceImpl")
	private LicensePlateModelService licensePlateModelServiceImpl;
	
	/**|
	 * 临时停车表服务
	 */
	@Resource(name = "temporaryLicensePlateServiceImpl")
	private TemporaryLicensePlateService temporaryLicensePlateServiceImpl;
	
	@Resource(name = "administrativeModelServiceImpl")
	private AdministrativeModelService administrativeModelServiceImpl;
	
	@Resource(name="sMemberAccountServiceImpl")
	private ISMemberAccountService sMemberAccountServiceImpl;
	
	@Resource(name = "smsServiceImpl")
	private SmsService smsService;
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource( name = "rAccountTransferRecordServiceImpl")
	private IRAccountTransferRecordService rAccountTransferRecordServiceImpl;
	
	@Resource(name = "parkingOrderRecordServiceImpl")
	private ParkingOrderRecordService parkingOrderRecordServiceImpl;
	
	@Resource(name = "payModeNotifyHttpServiceImpl")
	private IPayModeNotifyService payModeNotifyHttpServiceImpl;
	
	@Resource(name = "sysconfigServiceImpl")
	private SysconfigService sysconfigService;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "escapeRecordServiceImpl")
	private EscapeRecordService escapeRecordServiceImpl;
	
	@Resource(name = "communityModelServiceImpl")
	private CommunityModelService communityModelService;
	
	@Resource(name = "userGoldCardAccountServiceImpl")
	private IUserGoldCardAccountService userGoldCardAccountServiceImpl;
	
	@Resource(name = "goldCardTransferRecordServiceImpl")
	private IGoldCardTransferRecordService goldCardTransferRecordServiceImpl;
	
	public static final String Message_Escape_Notify = "亲爱的安心点用户,您的本次停车费由于电子账户结算时余额不足,已经产生了欠费%s元,请尽快充值,祝您生活愉快!";
	
	@Override
	public Map<String,Object> process(String version, String communityNo, RequestDataVo vo) {

		Map<String,Object> map = null;
		
		int ver = Integer.parseInt(version.trim());
		
		//判断版本号
		if(ver == 1){
			//判断停车场类型
			if(vo.getParkingType() == BaseService.PARKING_TYPE_COMMUNITY){
				map = processVersion1((LeaveReportVo)vo);
			}else if(vo.getParkingType() == BaseService.PARKING_TYPE_EMERGENCY){
				map = processVersion1Emergency((LeaveReportVo)vo);
			}
			TrafficInterface inf=new TrafficInterface();//调用数据汇报接口（E停车珠海妇幼保健医院到交管局）出场汇报
			inf.parkingLeaveReport((LeaveReportVo)vo, sysconfigService);
			if(map != null){
				return map;
			}
		}
		map = new HashMap<String,Object>();
		map.put(LPConstants.RESULTMAP_KEY_STATUS,LPConstants.RETURNCODE_PARAM_ERROR );
		map.put(LPConstants.RESULTMAP_KEY_MSG, "Param Error!");
		map.put(LPConstants.RESULTMAP_KEY_DATA, null);
		return map;
		
	}

	/**
	 * 版本1的处理方式(小区)
	 * @param vo
	 * @return
	 */
	private Map<String,Object> processVersion1(LeaveReportVo vo) {
		Map<String,Object> map = null;
		//1、取出业务数据
		String recordId = vo.getRecordId();//在停车场中，能唯一标记条停车记录
		String isBook = vo.getIsBook();//0没有，1有
		String isEscape = vo.getIsEscape();//0没有逃欠费，1逃费，2欠费，为0时没有escape为0
		Integer escape = vo.getEscape();//逃欠费金额，单位：分
		String license = vo.getLicense();//车牌号码，UTF8编码
		String arriveTime = vo.getArriveTime();//时间戳(秒)
		String leaveTime = vo.getLeaveTime();//时间戳(秒)
		Integer fee = vo.getFee();//停车费，单位：分
		String stopType = vo.getStopType();//0月卡车,1临时车,3免费车,4电子账户车
		Integer payType = vo.getPayType();//支付方式:0钱包支付，1微信支付，2支付宝支付，3 银行卡 4、现金
		String arriveUrl = vo.getArriveCarImgUrl();//来车图片url，utf8编码
		String leaveUrl = vo.getLeaveCarImgUrl();//走车图片url，utf8编码
		
		//2、创建 停车收费记录 实体类 保存
		RStoprecord rStoprecord = null;
		boolean saveStoprecord = true;
		rStoprecord = rStoprecordServiceImpl.findByComLicArrLeav(vo.getCommunityNo(), license,new Date(Long.valueOf(arriveTime)*1000l),null);
		if(rStoprecord != null){
			Date recordLeaveTime = rStoprecord.getLeaveTime();
			//如果记录的走车时间和当前汇报的走车时间相等，说明是重复汇报
			if(recordLeaveTime!=null && recordLeaveTime.getTime() == Long.valueOf(leaveTime)*1000l){
				map = new HashMap<String,Object>();
				map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_DUPLICATET_REPORT_LEAVE);
				map.put(LPConstants.RESULTMAP_KEY_MSG, "Duplicated Leave!");
				map.put(LPConstants.RESULTMAP_KEY_DATA, null);
				return map;
//				如果记录的走车时间和当前汇报的走车时间不相等/记录的走车时间为空，说明该车可能是通过app结算（微信支付或支付宝支付）生成的，此时只需更新记录的走车时间
//				两种符合情况
//					1、(recordLeaveTime!=null && recordLeaveTime.getTime() != Long.valueOf(leaveTime)*1000l)
//					2、recordLeaveTime==null
			}else{
				saveStoprecord = false;
			}
		}
		//3、查询对应 实时停车信息 删除
		PParkingcar pParkingcar = null;
		pParkingcar = pParkingcarServiceImpl.findByCommLicArr(vo.getCommunityNo(), license, new Date(Long.valueOf(arriveTime)*1000l));
//		//走车重复汇报(实时停车信息 已删除)
//		if(pParkingcar == null){
//			map = new HashMap<String,Object>();
//			map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_NO_ARRIVE_REPORT);
//			map.put(LPConstants.RESULTMAP_KEY_MSG, "no arrive report!");
//			map.put(LPConstants.RESULTMAP_KEY_DATA, null);
//			return map;
//		}
		if(saveStoprecord){
			rStoprecord = new RStoprecord();
			rStoprecord.setAreaId(null);
			rStoprecord.setParkingId(Integer.valueOf(vo.getCommunity().getId().toString()));
			rStoprecord.setParkingNumber(vo.getCommunityNo());
			rStoprecord.setParkingName(vo.getCommunity().getCommunityName() == null ? "":vo.getCommunity().getCommunityName());
			rStoprecord.setBerthId(null);
			if(pParkingcar != null){
				rStoprecord.setBerthNo(pParkingcar.getBerthNo());
			}else{
				rStoprecord.setBerthNo("");
			}
			rStoprecord.setOutTradeNo(null);
			rStoprecord.setLicense(license);
			rStoprecord.setArriveTime(new Date(Long.valueOf(arriveTime) * 1000L));
			rStoprecord.setLeaveTime(new Date(Long.valueOf(leaveTime) * 1000L));
			rStoprecord.setDeration(Integer.valueOf(leaveTime) - Integer.valueOf(arriveTime));//停车时长(秒)
			rStoprecord.setReceivedMoney(new BigDecimal(0));//预收,单位元
			BigDecimal bigDecimalFee = new BigDecimal(fee);
			bigDecimalFee = bigDecimalFee.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
			rStoprecord.setReceivablesMoney(bigDecimalFee);//应收,单位元
			rStoprecord.setFee(bigDecimalFee);//停车费用（实收）,单位元
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
			rStoprecord.setFeePayStatus(RStoprecord.FeePayStatus.Paid.ordinal());//支付状态0;未支付1;已支付 2;支付失败 
			rStoprecord.setPaidDate(new Date());
			rStoprecord.setCreateTime(new Date());
			rStoprecord.setArriveCarImgUrl(arriveUrl);
			rStoprecord.setLeaveCarImgUrl(leaveUrl);
			rStoprecord.setUserName(null);
			rStoprecord.setRealName(null);
			//查询小区物业信息
			Integer popertyNumber = null;
			if(vo.getCommunity()!=null){
				popertyNumber = vo.getCommunity().getProperty_number();
			}
			AdministrativeModel administrativeModel = null;
			if(popertyNumber != null){
				administrativeModel = administrativeModelServiceImpl.sellectByPropretyNumber(popertyNumber);
			}
			if(administrativeModel != null){
				rStoprecord.setBranchId(administrativeModel.getBranch_id());
				rStoprecord.setHeadOfficeId(administrativeModel.getHeadoffice_id());
			}
			rStoprecord.setPopertyNumber(popertyNumber);
			//保存 停车收费记录
			rStoprecordServiceImpl.save(rStoprecord);
		}else{
			rStoprecord.setParkingName(vo.getCommunity().getCommunityName() == null ? "":vo.getCommunity().getCommunityName());
			rStoprecord.setArriveCarImgUrl(arriveUrl);
			rStoprecord.setLeaveCarImgUrl(leaveUrl);
			rStoprecord.setDeration(Integer.valueOf(leaveTime) - Integer.valueOf(arriveTime));//停车时长(秒)
			rStoprecord.setLeaveTime(new Date(Long.valueOf(leaveTime) * 1000L));
			//更新 停车收费记录
			rStoprecordServiceImpl.update(rStoprecord);
		}
		
		LicensePlateModel licensePlateModel = null;//车牌表
		ParkRateModel parkRateModel = null;//白名单
		TemporaryLicensePlate temporaryLicensePlate = null;//临时停车表
		
		//月租车还是临时车操作？
		if(Integer.valueOf(stopType) == 0){//月租车
			parkRateModel = parkRateModelServiceImpl.selectByLicenseComunity(vo.getCommunityNo(), license);
			if(parkRateModel != null){
//				String username = parkRateModel.getUserName()==null?"":parkRateModel.getUserName();
//				if("nullUser".equals(username)){
//					log.info(">>>>>:[停车场走车汇报processVersion1]parkRate白名单表未找到此车牌信息 \"" + license + "\"！！！！！");
//				}else{
//				}
				List<LicensePlateModel> listLicensePlateModel = licensePlateModelServiceImpl.findByLicenseAndNotNulluser(license);
				if(CollectionUtils.isNotEmpty(listLicensePlateModel)){
					licensePlateModel = listLicensePlateModel.get(0);
					rStoprecord.setUserName(licensePlateModel.getUser_name());
				}else{
					rStoprecord.setUserName("");
				}
				parkRateModel.setInStatus(0);//进出状态修改 1:入 0:出
				parkRateModel.setOutTime(Integer.valueOf(leaveTime));//出场时间
				parkRateModel.setModifyDate(new Date());
				log.info(">>>>>:[停车场走车汇报processVersion1]parkRate白名单车牌 \"" + license + "\"走车汇报！！！！！");
				parkRateModelServiceImpl.update(parkRateModel);
			}else{
				log.info(">>>>>:[停车场走车汇报processVersion1]parkRate白名单表未找到此车牌信息 : " + license + "！！！！！");
			}
		}else if(Integer.valueOf(stopType) == 1){//临时车
			List<LicensePlateModel> listLicensePlateModel = licensePlateModelServiceImpl.findByLicenseAndNotNulluser(license);
			if(CollectionUtils.isEmpty(listLicensePlateModel)){//车牌表license_plate 为空（未在平台绑定的临时车）
				log.info(">>>>>:[停车场走车汇报processVersion1]此车牌 \"" + license + "\" 未在平台绑定！！！！！");
			}else{//车牌表license_plate 不为空
				licensePlateModel = listLicensePlateModel.get(0);
				String username = licensePlateModel.getUser_name();
				if("nullUser".equals(username.trim())){
					log.info(">>>>>:[停车场走车汇报processVersion1]此车牌 \"" + license + "\" 未在平台绑定！！！！！");
				}else{
					rStoprecord.setUserName(username);
					try {
						temporaryLicensePlate = temporaryLicensePlateServiceImpl.selectByLicensePlateAndCommunityNumber(license,vo.getCommunityNo());
						if(temporaryLicensePlate == null){//查询临时停车表temporary_license_plate 为空
							
						}else{//查询临时停车表temporary_license_plate 不为空
							log.info(">>>>>:[停车场走车汇报processVersion1]临时车牌 \"" + license + "\"走车汇报！！！！！");
							temporaryLicensePlateServiceImpl.delete(temporaryLicensePlate);
						}
					} catch (Exception e) {
						log.error(">>>>>:[停车场走车汇报processVersion1]temporaryLicensePlateServiceImpl数据库操作异常"
								+ "，此操作不影响主业务，所以单独抛出异常 license = " + license,e);
					}
				}
			}
		}
		//删除 实时停车信息
		if(pParkingcar != null){
//			pParkingcarServiceImpl.delete(pParkingcar.getId());
			boolean result = pParkingcarServiceImpl.deleteById(pParkingcar.getId());
		}
		//删除其他实时停车记录
		List<PParkingcar> illegalParkingcarList = pParkingcarServiceImpl.findByLic(license);
		if(CollectionUtils.isNotEmpty(illegalParkingcarList)){
			boolean result = pParkingcarServiceImpl.deleteByCommunityNumberAndLicense(vo.getCommunityNo(),license);
//			for (PParkingcar illegalParkingcar : illegalParkingcarList) {
//				if(illegalParkingcar != null && vo.getCommunityNo().equals(illegalParkingcar.getCommunityNo())){
//					pParkingcarServiceImpl.delete(illegalParkingcar);
//				}
//			}
		}
		map = new HashMap<String,Object>();
		map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SUCCESS);
		map.put(LPConstants.RESULTMAP_KEY_MSG, "OK");
		map.put(LPConstants.RESULTMAP_KEY_DATA, null);
		return map;
	}
	
	/**
	 * 版本1的处理方式(紧急场所)
	 * @param vo
	 * @return
	 */
	private Map<String,Object> processVersion1Emergency(LeaveReportVo vo) {
		Map<String,Object> map = null;
		//取出业务数据
		String recordId = vo.getRecordId();//在停车场中，能唯一标记条停车记录
		String isBook = vo.getIsBook();//0没有，1有
		String isEscape = vo.getIsEscape();//0没有逃欠费，1逃费，2欠费，为0时没有escape位0
		Integer escape = vo.getEscape();//逃欠费金额，单位：分
		String license = vo.getLicense();//车牌号码，UTF8编码
		String arriveTime = vo.getArriveTime();//时间戳(秒)
		String leaveTime = vo.getLeaveTime();//时间戳(秒)
		String leaveTimeStr = DateUtils.getDateStrByTimestamp(Long.valueOf(leaveTime.trim()), null);
		Integer fee = vo.getFee();//停车费，单位：分
		int appPaidFee = 0;//app已支付，单位：分
		int appPaidFeeLeft = 0;//app支付后剩余，单位：分
		
		String stopType = vo.getStopType();//0月卡车,1临时车,3免费车,4电子账户车
		Integer payType = vo.getPayType();//支付方式:0钱包支付，1微信支付，2支付宝支付，3 银行卡 4、现金
		String arriveUrl = vo.getArriveCarImgUrl();//来车图片url，utf8编码
		String leaveUrl = vo.getLeaveCarImgUrl();//走车图片url，utf8编码
		
		Integer fieldType = vo.getFieldType();//场中场类型 0场外 1场内
		if(fieldType == null || fieldType == 0){//场中场为：外场，进行走车汇报处理
			//查询对应 实时停车信息 删除
			PParkingcar pParkingcar = pParkingcarServiceImpl.findByCommLicArr(vo.getCommunityNo(), license, new Date(Long.valueOf(arriveTime)*1000l));
			//走车重复汇报(实时停车信息 已删除)
//			if(pParkingcar == null){
//				map = new HashMap<String,Object>();
//				map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_NO_ARRIVE_REPORT);
//				map.put(LPConstants.RESULTMAP_KEY_MSG, "no arrive report!");
//				map.put(LPConstants.RESULTMAP_KEY_DATA, null);
//				return map;
//			}
			//创建 停车收费记录 实体类 保存
			boolean saveStoprecord = true;
			//同一来车时间已支付费用
			BigDecimal appPaidFeeDe = new BigDecimal(0);
			List<RStoprecord> stoprecordList = rStoprecordServiceImpl.findAllPaidRecord(vo.getCommunityNo(), license,new Date(Long.valueOf(arriveTime)*1000l));
			RStoprecord rStoprecord = null;
			if(CollectionUtils.isNotEmpty(stoprecordList)){
				rStoprecord = stoprecordList.get(0);
				for (RStoprecord stoprecord : stoprecordList) {
					Date recordLeaveTime = stoprecord.getLeaveTime();
					//如果记录的走车时间和当前汇报的走车时间相等，说明是重复汇报
					if(recordLeaveTime!=null && recordLeaveTime.getTime() == Long.valueOf(leaveTime)*1000l){
						map = new HashMap<String,Object>();
						map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_DUPLICATET_REPORT_LEAVE);
						map.put(LPConstants.RESULTMAP_KEY_MSG, "Duplicated Leave!");
						map.put(LPConstants.RESULTMAP_KEY_DATA, null);
						return map;
//						如果记录的走车时间和当前汇报的走车时间不相等/记录的走车时间为空，说明该车可能是通过app结算（微信支付或支付宝支付）生成的，此时只需更新记录的走车时间
//						两种符合情况
//							1、(recordLeaveTime!=null && recordLeaveTime.getTime() != Long.valueOf(leaveTime)*1000l)
//							2、recordLeaveTime==null
					}else{
						BigDecimal recordFee = stoprecord.getFee();
						appPaidFeeDe = appPaidFeeDe.add(recordFee==null?new BigDecimal(0):recordFee);
					}	
				}
			}
			appPaidFee = (int) (appPaidFeeDe.doubleValue()*100);
			//同一来车时间已支付费用 大于等于 需支付的费用
			if(appPaidFee >= fee){
				if(fee > 0){
					saveStoprecord = false;
				}
			}else{
				appPaidFeeLeft = fee - appPaidFee;
			}
			
			//判断此车是否是会员车
			String userName = null;//会员名称
			boolean hasUserName = false; 
			LicensePlateModel licensePlateModel = null;//车牌表
			List<LicensePlateModel> listLicensePlateModel = licensePlateModelServiceImpl.CheckLicensePlate(license);
			if(CollectionUtils.isEmpty(listLicensePlateModel)){//车牌表license_plate 为空（未在平台绑定的临时车）
				log.info(">>>>>:[停车场走车汇报processVersion1Emergency]此车牌 \"" + license + "\" 未在平台绑定!");
			}else{//车牌表license_plate 不为空
				licensePlateModel = listLicensePlateModel.get(0);
				userName = licensePlateModel.getUser_name();
				if("nullUser".equals(userName.trim())){//已解绑
					log.info(">>>>>:[停车场走车汇报processVersion1Emergency]此车牌 \"" + license + "\" 未在平台绑定!");
				}else{
					hasUserName = true;
				}
			}
			//查询订单
			ParkingOrderRecord parkingOrderRecord = null;
			//根据来车时间和用户名查询订单
			if(hasUserName){
				parkingOrderRecord = parkingOrderRecordServiceImpl.findByUserNameAndArriveTime(userName,new Date(Long.valueOf(arriveTime)*1000l));
			}
			//获取订单号（预约停车订单编号） 线下支付不关联约定车位的订单编号
			String orderNo = null;
			if(parkingOrderRecord != null){
				orderNo = parkingOrderRecord.getOrderNo();
			}
			//本次停车费应该收取的费用(停车费 - 网络支付)
			BigDecimal bigDecimalFee = new BigDecimal(appPaidFeeLeft);
			if(saveStoprecord){
				//停车收费记录
				rStoprecord = new RStoprecord();
				rStoprecord.setAreaId(null);
				rStoprecord.setParkingId(Integer.valueOf(vo.getParkingInfo().getId().toString()));
				rStoprecord.setParkingNumber(vo.getCommunityNo());
				rStoprecord.setParkingName(vo.getParkingInfo().getParkingName() == null ? "":vo.getParkingInfo().getParkingName());
				rStoprecord.setBerthId(null);
				if(pParkingcar != null){
					rStoprecord.setBerthNo(pParkingcar.getBerthNo());
				}else{
					rStoprecord.setBerthNo("");
				}
				rStoprecord.setOutTradeNo(null);
				rStoprecord.setLicense(license);
				rStoprecord.setArriveTime(new Date(Long.valueOf(arriveTime) * 1000L));
				rStoprecord.setLeaveTime(new Date(Long.valueOf(leaveTime) * 1000L));
				rStoprecord.setDeration(Integer.valueOf(leaveTime) - Integer.valueOf(arriveTime));//停车时长(秒)
				rStoprecord.setReceivedMoney(null);//预收,单位元
				bigDecimalFee = bigDecimalFee.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
				rStoprecord.setReceivablesMoney(bigDecimalFee);//应收,单位元
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
				rStoprecord.setCreateTime(new Date());
				rStoprecord.setArriveCarImgUrl(arriveUrl);
				rStoprecord.setLeaveCarImgUrl(leaveUrl);
				if(hasUserName){
					rStoprecord.setUserName(userName);
				}
				rStoprecord.setRealName(null);
				rStoprecord.setBranchId(null);
				rStoprecord.setHeadOfficeId(null);
				rStoprecord.setPopertyNumber(null);
				
				//保存 停车收费记录
				rStoprecord.setFee(bigDecimalFee);//实收
				rStoprecord.setFeePayStatus(RStoprecord.FeePayStatus.Paid.ordinal());//支付状态
				rStoprecord.setPaidDate(new Date());
				if("0".equals(stopType) && "3".equals(stopType)){
					rStoprecord.setDeductionsType(0);//扣费方式 :默认0;手动支付1;自动扣费 2;微信公众号3;扫二维码
				}	
				
				//16.07.22 紧急场所也有月租车，可同步白名单，可点安心点(加上过期的逻辑)
				ParkRateModel parkRateModel = null;//白名单
				parkRateModel = parkRateModelServiceImpl.selectByLicenseComunity(vo.getCommunityNo(), license);
				if(parkRateModel != null){
					parkRateModel.setInStatus(0);//进出状态修改 1:入 0:出
					parkRateModel.setOutTime(Integer.valueOf(leaveTime));//出场时间
					parkRateModel.setModifyDate(new Date());
					parkRateModelServiceImpl.update(parkRateModel);
				}
				
				//查询电子钱包
				SMemberAccount account = null;
				if(hasUserName){
					account = sMemberAccountServiceImpl.findByUserNameUserTypeStatus(userName,(byte)Member.Type.member.ordinal(),(byte)SMemberAccount.Status.usable.ordinal());
				}
				//是否使用电子钱包支付
				boolean useAccount = false;
				double basicAccount = 0d;
				//电子账户存在、支付方式:0 钱包支付、停车类型:4 电子账户车
				if(account != null && payType == 0 && Integer.valueOf(stopType) == 4){
					useAccount = true;
					BigDecimal basicAccountDb = account.getBasicAccount();
					if(basicAccountDb != null){
						basicAccount = basicAccountDb.doubleValue();
					}
				}
				//电子账户存在、支付方式:4 现金支付、停车类型:4 电子账户车；追缴其逃欠费，并充值电子钱包相应金额
				if(account != null && payType == 4 && Integer.valueOf(stopType) == 4){
					List<EscapeRecord> escapeRecordList = escapeRecordServiceImpl.findByUserNameAndRecoveryStatus(userName,EscapeRecord.RecoveryStatus.no_recovery);
					if(CollectionUtils.isNotEmpty(escapeRecordList)){
						for (int i = 0; i < escapeRecordList.size(); i++) {
							EscapeRecord escapeRecord = escapeRecordList.get(i);
							//将逃欠费追缴
							escapeRecord.setRecoveryStatus((byte)EscapeRecord.RecoveryStatus.recoveryed.ordinal());
							escapeRecord.setClearTime(new Date());
							escapeRecord.setRecoveryMode((byte)EscapeRecord.RecoveryMode.cash.ordinal());//现金追缴
							escapeRecord.setRecoveryFee(escapeRecord.getEscapeFee());
							escapeRecordServiceImpl.update(escapeRecord);
							//只操作最新一条记录
							if(i == 0){
								//账户余额
								account.setBasicAccount(account.getBasicAccount().add(escapeRecord.getEscapeFee()));
								sMemberAccountServiceImpl.update(account);
								//流转记录
								RAccountTransferRecord rAccountTransferRecord = rAccountTransferRecordServiceImpl.genAcoountRecords(account, null, 
										escapeRecord.getEscapeFee().doubleValue(),vo.getCommunityNo(),(byte)RAccountTransferRecord.UserType.Member.ordinal(),
										RAccountTransferRecord.Type.chargeEscape , "停车欠费现金追缴");
								rAccountTransferRecordServiceImpl.save(rAccountTransferRecord);
							}
						}
					}
					
				}
				double amoutLeft = 0;
				//====================== 是会员且有电子钱包，才执行以下业务   start
				//TODO 暂时不用电子钱包支付  2016-08-05 by dengsongping
				if(useAccount){
					rStoprecord.setDeductionsType(1);//扣费方式 :默认0;手动支付1;自动扣费 2;微信公众号3;扫二维码
					//TODO 使用电子钱包支付  	2016-09-26 by dengsongping
					//逻辑
					//1、判断是否有欠费
					//	有欠费需要交纳相关的欠费及本次停车费用才开闸
					//	无欠费则直接在账户余额扣除相应的停车费用
					//查看有没有未追缴的逃欠费记录
					List<EscapeRecord> escapeRecordList = escapeRecordServiceImpl.findByUserNameAndRecoveryStatus(userName,EscapeRecord.RecoveryStatus.no_recovery);
					if(CollectionUtils.isNotEmpty(escapeRecordList)){
						map = new HashMap<String,Object>();
						map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_LEAVE_FORBIDDEN);
						map.put(LPConstants.RESULTMAP_KEY_MSG, "Leave Forbidden");
						map.put(LPConstants.RESULTMAP_KEY_DATA, null);
						return map;
					}
					//此次停车费除去已支付没再产生费用
					if((appPaidFeeLeft) <= 0){
						rStoprecord.setFee(new BigDecimal(0));//实收
						rStoprecord.setFeePayStatus(RStoprecord.FeePayStatus.Paid.ordinal());//支付状态
						rStoprecord.setPaidDate(new Date());
					}else{
						//>>>>>停车卡支付优先 Gold_Card
						int feeLeft = appPaidFeeLeft;//停车卡支付后余下的(电子钱包支付)
						int goldCardPaid = 0;
						UserGoldCardAccountDto userGoldCardAccountDto = userGoldCardAccountServiceImpl.findByUserName(userName);
						if(userGoldCardAccountDto != null){
							//停车卡账户余额
							BigDecimal balance = userGoldCardAccountDto.getBalance();
							if(balance != null){
								//元转为分
								int balanceInt = (int) (balance.doubleValue()*100);
								if(balanceInt >= appPaidFeeLeft){//停车卡余额充足
									goldCardPaid = appPaidFeeLeft;
									feeLeft = 0;
									userGoldCardAccountDto.setBalance(new BigDecimal((balanceInt - appPaidFeeLeft)/100d).setScale(2,BigDecimal.ROUND_HALF_UP));
								}else if(balanceInt < appPaidFeeLeft && balanceInt > 0){//停车卡余额不足，支付一部分
									goldCardPaid = balanceInt;
									feeLeft = appPaidFeeLeft - balanceInt;
									userGoldCardAccountDto.setBalance(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP));
								}
								if(goldCardPaid > 0){
									//修改停车卡账户
									UserGoldCardAccount userGoldCardAccount = new UserGoldCardAccount();
									BeanUtils.copyProperties(userGoldCardAccountDto, userGoldCardAccount);
									userGoldCardAccountServiceImpl.update(userGoldCardAccount);
									//生成停车卡账户流转记录
									GoldCardTransferRecord goldCardTransferRecord = goldCardTransferRecordServiceImpl.genGoldCardTransferRecord(
											userGoldCardAccount.getBalance(), new BigDecimal(goldCardPaid / 100d), "停车扣费", GoldCardTransferRecord.Type.Temp_Parking_Fee.ordinal(), userName, 0, null);
									goldCardTransferRecordServiceImpl.save(goldCardTransferRecord);
								}
								amoutLeft = amoutLeft + userGoldCardAccountDto.getBalance().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							}
						}
						if(goldCardPaid == appPaidFeeLeft){
							rStoprecord.setPayType(RStoprecord.PayType.Gold_Card.ordinal());
						}else if(goldCardPaid > 0){
							rStoprecord.setPayType(RStoprecord.PayType.Member_Account_Gold_Card.ordinal());
						}
						if(feeLeft > 0){
							//2、如果余额不足允许欠费一次,计入欠费记录,返回蓝鹏道闸系统开闸.
							if(basicAccount >= feeLeft / 100d){//====================== 余额充足
//								rStoprecord.setFee(new BigDecimal(feeLeft / 100d));//实收
								rStoprecord.setFeePayStatus(RStoprecord.FeePayStatus.Paid.ordinal());//支付状态
								rStoprecord.setPaidDate(new Date());
								//电子钱包扣钱
								account.setBasicAccount(new BigDecimal(basicAccount - (feeLeft / 100d)).setScale(2, BigDecimal.ROUND_HALF_UP));
								account.setConsumAmount(account.getConsumAmount().add(new BigDecimal((feeLeft / 100d)).setScale(2, BigDecimal.ROUND_HALF_UP)));
								sMemberAccountServiceImpl.update(account);
								//电子钱包流转记录
								RAccountTransferRecord record = genAcoountRecords(account,feeLeft / 100d,null,vo.getCommunityNo());
								rAccountTransferRecordServiceImpl.save(record);
								//推送不是主业务，可以失败
//								try {
//									//走车短信推送
//									sendSMSMessage(userName, "您的车【"+vo.getLicense()+"】于"+leaveTimeStr+"在\""+vo.getParkingInfo().getParkingName()+"\"出闸，本次停车消费："+fee / 100d+"元");
//									//走车app推送
//									pushMessage(userName, "您的车【"+vo.getLicense()+"】于"+leaveTimeStr+"在"+vo.getParkingInfo().getParkingName()+"出闸，本次停车消费："+fee / 100d+"元", null);
//								} catch (Exception e) {
//									e.printStackTrace();
//								}
//							}else if(basicAccount == 0){//====================== 余额为0
//								//修改停车收费记录状态
//								rStoprecord.setFee(new BigDecimal(0));//实收
//								rStoprecord.setFeePayStatus(RStoprecord.FeePayStatus.Paid.ordinal());//支付状态
//								rStoprecord.setPaidDate(new Date());
//								//生成逃费记录
//								EscapeRecord escapeRecord = new EscapeRecord();
//								escapeRecord.setFlag((byte)EscapeRecord.Flag.arrearages.ordinal());//逃欠费
//								escapeRecord.setLicense(license);
//								escapeRecord.setUserName(userName);
//								escapeRecord.setParkingId(vo.getCommunityNo());
//								escapeRecord.setParkingName(vo.getParkingInfo().getParkingName() == null ? "":vo.getParkingInfo().getParkingName());
//								escapeRecord.setBerthId(null);
//								escapeRecord.setBerthNo(null);
//								escapeRecord.setAreaId(null);;
//								escapeRecord.setArriveTime(DateUtils.longPaseDate(Long.valueOf(arriveTime)*1000L));
//								escapeRecord.setLeaveTime(DateUtils.longPaseDate(Long.valueOf(leaveTime)*1000L));
//								escapeRecord.setUrl(null);
//								escapeRecord.setFee(bigDecimalFee);//消费金额
//								escapeRecord.setEscapeFee(bigDecimalFee);//逃欠金额
//								escapeRecord.setRealPay(new BigDecimal(0));//实缴金额 0
//								escapeRecord.setTime(new Date());
//								escapeRecord.setRecoveryStatus((byte)EscapeRecord.RecoveryStatus.no_recovery.ordinal());
//								escapeRecord.setClearTime(null);
//								escapeRecord.setBranchId(null);
//								escapeRecord.setHeadOfficeId(null);
//								escapeRecord.setPopertyNumber(null);
//								escapeRecordServiceImpl.save(escapeRecord);
//								
//								//推送不是主业务，可以失败
////								try {
////									//走车短信推送
////									sendSMSMessage(userName, "您的车【"+vo.getLicense()+"】于"+leaveTimeStr+"在\""+vo.getParkingInfo().getParkingName()+"\"出闸，本次停车消费："+fee / 100d+"元，欠费："+fee / 100d+"元");
////									//走车app推送
////									pushMessage(userName, "您的车【"+vo.getLicense()+"】于"+leaveTimeStr+"在"+vo.getParkingInfo().getParkingName()+"出闸，本次停车消费："+fee / 100d+"元，欠费："+fee / 100d+"元", null);
////								} catch (Exception e) {
////									e.printStackTrace();
////								}
							}else{//====================== 余额不足
								//修改停车收费记录状态(停车卡支付 + 电子钱包支付)
								rStoprecord.setFee(new BigDecimal(basicAccount).add(new BigDecimal(goldCardPaid / 100d)).setScale(2, BigDecimal.ROUND_HALF_UP));//实收
								rStoprecord.setFeePayStatus(RStoprecord.FeePayStatus.Paid.ordinal());//支付状态
								rStoprecord.setPaidDate(new Date());
								//电子钱包扣完（可为负数）
								account.setBasicAccount(new BigDecimal(basicAccount - (feeLeft / 100d)).setScale(2, BigDecimal.ROUND_HALF_UP));
								sMemberAccountServiceImpl.update(account);
								//电子钱包流转记录
								RAccountTransferRecord record = genAcoountRecords(account,feeLeft / 100d,null,vo.getCommunityNo());
								rAccountTransferRecordServiceImpl.save(record);
								//生成欠费记录
								EscapeRecord escapeRecord = new EscapeRecord();
								escapeRecord.setFlag((byte)EscapeRecord.Flag.arrearages.ordinal());//逃费
								escapeRecord.setLicense(license);
								escapeRecord.setUserName(userName);
								escapeRecord.setParkingId(vo.getCommunityNo());
								escapeRecord.setParkingName(vo.getParkingInfo().getParkingName() == null ? "":vo.getParkingInfo().getParkingName());
								escapeRecord.setBerthId(null);
								escapeRecord.setBerthNo(null);
								escapeRecord.setAreaId(null);;
								escapeRecord.setArriveTime(DateUtils.longPaseDate(Long.valueOf(arriveTime)*1000L));
								escapeRecord.setLeaveTime(DateUtils.longPaseDate(Long.valueOf(leaveTime)*1000L));
								escapeRecord.setUrl(null);
								escapeRecord.setFee(bigDecimalFee);//消费金额
								escapeRecord.setEscapeFee(new BigDecimal(feeLeft / 100d - basicAccount).setScale(2, BigDecimal.ROUND_HALF_UP));//逃欠金额
								escapeRecord.setRealPay(new BigDecimal(basicAccount).setScale(2, BigDecimal.ROUND_HALF_UP));//实缴金额
								escapeRecord.setTime(new Date());
								escapeRecord.setRecoveryStatus((byte)EscapeRecord.RecoveryStatus.no_recovery.ordinal());
								escapeRecord.setClearTime(null);
								escapeRecord.setBranchId(null);
								escapeRecord.setHeadOfficeId(null);
								escapeRecord.setPopertyNumber(null);
								escapeRecord.setRecoveryFee(new BigDecimal(0));
								escapeRecordServiceImpl.save(escapeRecord);
								//欠费推送
								try {
									//走车短信推送
									sendSMSMessage(userName,String.format(Message_Escape_Notify,new BigDecimal((feeLeft / 100d) - basicAccount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
									//走车app推送
									pushMessage(userName,String.format(Message_Escape_Notify,new BigDecimal((feeLeft / 100d) - basicAccount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()),null);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
//					//======================收费方式下发停车场
					try {
						if(account != null){
							BigDecimal basicAfter = account.getBasicAccount();
							if(basicAfter != null){
								double basicAfterDouble = basicAfter.doubleValue();
								amoutLeft = new BigDecimal(amoutLeft + basicAfterDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
							}
						}
//						DownPayModeNotifyVo downPayModeNotifyVo = new DownPayModeNotifyVo();
//						downPayModeNotifyVo.setVersion("1");
//						downPayModeNotifyVo.setLicense(license);
//						downPayModeNotifyVo.setPayMode(1);
//						downPayModeNotifyVo.setBalance(account.getBasicAccount().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue());
//						downPayModeNotifyVo.setOrderNo(orderNo==null?"":orderNo);
//						if(pParkingcar != null){
//							downPayModeNotifyVo.setBerthNo(pParkingcar.getBerthNo()==null?"":pParkingcar.getBerthNo());
//						}else{
//							downPayModeNotifyVo.setBerthNo("");
//						}
//						downPayModeNotifyVo.setIsOrder(Integer.parseInt(isBook.trim()));
//						//收费方式下发停车场
//						HttpRequestDealBusinessMsg message = payModeNotifyHttpServiceImpl.processV1(vo.getCommunityNo(), downPayModeNotifyVo, null);
//						//如果第一次不成功，将任务加入到线程池
//						if(message.getCode() == null || message.getCode() != 0){
//							final String communityNo = vo.getCommunityNo();
//							final DownPayModeNotifyVo voCopy = downPayModeNotifyVo;
//							ThreadPoolFactory.getInstance().addTask(new Runnable(){
//								private long defaultSleep = 10 * 1000l;
//								@Override
//								public void run() {
//									int count = 1;
//									while(true){
//										Double d = Math.pow(2d, (count-1));// 1、2、4、8、16
//										Integer times = d.intValue();
//										try{
//											if(count > 4){
//												//缓存本次任务参数 TODO
//												break;
//											}
//											Thread.sleep(defaultSleep * times);
//											log.info(">>>>>:[停车场走车汇报processVersion1Emergency]收费方式下发停车场：第"+(count+1)+"汇报");
//											System.out.println("==========收费方式下发停车场：第"+(count+1)+"汇报");
//											HttpRequestDealBusinessMsg message = payModeNotifyHttpServiceImpl.processV1(communityNo, voCopy, null);
//											if(message.getCode() == 0){
//												break;
//											}
//											count++;
//										}catch(Exception e){
//											e.printStackTrace();
//											log.error(">>>>>:[停车场走车汇报processVersion1Emergency]收费方式下发停车场异常", e);
//											count++;
//										}
//									}
//								}
//							});
//						}
//						//余额不足推送
						if(amoutLeft < Double.valueOf(balanceNotify)){
							//短信推送
							sendSMSMessage(userName, "您的电子账户余额已不足"+ balanceNotify +"元,请及时充值");
							//app推送
							pushMessage(userName, "您的电子账户余额已不足"+ balanceNotify +"元,请及时充值", null);
						}
					} catch (Exception e) {
						e.printStackTrace();
						log.error(">>>>>:[停车场走车汇报processVersion1Emergency]推送异常", e);
					}
				}
				//====================== 是会员且有电子钱包，才执行以下业务   end
				rStoprecordServiceImpl.save(rStoprecord);
			}else{
				//将最近一条停车收费记录更新
				rStoprecord.setParkingName(vo.getParkingInfo().getParkingName() == null ? "":vo.getParkingInfo().getParkingName());
				rStoprecord.setArriveCarImgUrl(arriveUrl);
				rStoprecord.setLeaveCarImgUrl(leaveUrl);
				rStoprecord.setDeration(Integer.valueOf(leaveTime) - Integer.valueOf(arriveTime));//停车时长(秒)
				rStoprecord.setLeaveTime(new Date(Long.valueOf(leaveTime) * 1000L));
				//更新 停车收费记录
				rStoprecordServiceImpl.update(rStoprecord);
			}
			
			//删除 实时停车信息
			if(pParkingcar != null){
//				pParkingcarServiceImpl.delete(pParkingcar.getId());
				boolean result = pParkingcarServiceImpl.deleteById(pParkingcar.getId());
			}
			//删除其他实时停车记录
			List<PParkingcar> illegalParkingcarList = pParkingcarServiceImpl.findByLic(license);
			if(CollectionUtils.isNotEmpty(illegalParkingcarList)){
				boolean result = pParkingcarServiceImpl.deleteByCommunityNumberAndLicense(vo.getCommunityNo(),license);
//				for (PParkingcar illegalParkingcar : illegalParkingcarList) {
//					if(illegalParkingcar != null && vo.getCommunityNo().equals(illegalParkingcar.getCommunityNo())){
//						pParkingcarServiceImpl.delete(illegalParkingcar);
//					}
//				}
			}
			try {
				if(parkingOrderRecord != null){
					//查询缓存
//					ParKingRealTimeStatusDto cachedObj = redisService.get(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status 
//							+parkingOrderRecord.getParkingId(),ParKingRealTimeStatusDto.class);
					ParKingRealTimeStatusDto cachedObj = redisService.hGet(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status_Map,
							vo.getCommunityNo(),ParKingRealTimeStatusDto.class);
					Integer rscheduled = cachedObj.getRscheduled();
					cachedObj.setRscheduled(rscheduled + 1);	
//					redisService.set(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status + parkingOrderRecord.getParkingId(), cachedObj);
					redisService.hset(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status_Map,vo.getCommunityNo(), cachedObj);
					log.info(String.format("[停车场走车汇报processVersion1Emergency]可预约车位的缓存数据+1"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		map = new HashMap<String,Object>();
		map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SUCCESS);
		map.put(LPConstants.RESULTMAP_KEY_MSG, "OK");
		map.put(LPConstants.RESULTMAP_KEY_DATA, null);
		return map;
	}
	
	private RAccountTransferRecord genAcoountRecords(SMemberAccount sMemberAccount,double fee,String out_trade_no,String communityNo){
		RAccountTransferRecord rAccountTransferRecord = new RAccountTransferRecord();
		rAccountTransferRecord.setAccountId(sMemberAccount.getId().toString());
		rAccountTransferRecord.setAccountNumber(sMemberAccount.getAccountNumber());
		rAccountTransferRecord.setIdentify(RAccountTransferRecord.genIdentify(sMemberAccount.getAccountNumber()));
		rAccountTransferRecord.setOrderNo(out_trade_no);
		rAccountTransferRecord.setSerialNumber(RAccountTransferRecord.genSerialNumber(sMemberAccount.getAccountNumber()));
		rAccountTransferRecord.setUserName(sMemberAccount.getUserName());
		rAccountTransferRecord.setAmount(new BigDecimal(fee).setScale(2, BigDecimal.ROUND_HALF_UP));
		rAccountTransferRecord.setAccountBalance(sMemberAccount.getBasicAccount());
		rAccountTransferRecord.setAccountType((byte)RAccountTransferRecord.AccountType.basicAccount.ordinal());
		rAccountTransferRecord.setOperateTime(new Date());
		rAccountTransferRecord.setOperateType((byte)RAccountTransferRecord.OperateType.inter.ordinal());
		rAccountTransferRecord.setType(RAccountTransferRecord.Type.expenseCarStop);
		rAccountTransferRecord.setComment("停车消费");
		rAccountTransferRecord.setIsConfirmed((byte)RAccountTransferRecord.IsConfirmed.ok.ordinal());
		rAccountTransferRecord.setStatus((byte)RAccountTransferRecord.Status.finished.ordinal());
		rAccountTransferRecord.setUserType((byte)Member.Type.member.ordinal());//0、会员 1、商户
		
		Integer propertyNumber = null;
    	Integer branchId = null;
    	if(MStringUntils.isNotEmptyOrNull(communityNo)){
	    	List<CommunityModel> communityList = communityModelService.checkByCommunity_number(communityNo);
	    	if(communityList != null && communityList.size()>0){
	    		propertyNumber = communityList.get(0).getProperty_number();
	    		branchId = communityList.get(0).getBranchId();
	    	}
    	}
    	rAccountTransferRecord.setCommunityNumber(communityNo);
    	rAccountTransferRecord.setPropertyNumber(propertyNumber);
    	rAccountTransferRecord.setBranchId(branchId);
		
		return rAccountTransferRecord;
	}

}
