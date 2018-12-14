package com.arf.platform.service.impl;

import java.math.BigDecimal;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.LicensePlateModel;
import com.arf.core.entity.Member;
import com.arf.core.entity.ParkRateModel;
import com.arf.core.entity.TemporaryLicensePlate;
import com.arf.core.service.CommunityModelService;
import com.arf.core.service.LicensePlateModelService;
import com.arf.core.service.ParkRateModelService;
import com.arf.core.service.RBookrecordService;
import com.arf.core.service.SysconfigService;
import com.arf.core.service.TemporaryLicensePlateService;
import com.arf.core.util.HttpUtil;
import com.arf.core.util.MStringUntils;
import com.arf.eparking.entity.ParkingOrderRecord;
import com.arf.eparking.service.ParkingOrderRecordService;
import com.arf.goldcard.dto.UserGoldCardAccountDto;
import com.arf.goldcard.service.IUserGoldCardAccountService;
import com.arf.platform.LPConstants;
import com.arf.platform.entity.PParkingcar;
import com.arf.platform.entity.PrivilegeCar;
import com.arf.platform.entity.SMemberAccount;
import com.arf.platform.service.BaseService;
import com.arf.platform.service.IParkingUpReportBusinessDealService;
import com.arf.platform.service.IPayModeNotifyService;
import com.arf.platform.service.ISMemberAccountService;
import com.arf.platform.service.PParkingcarService;
import com.arf.platform.utils.DateUtils;
import com.arf.platform.vo.ArriveReportVo;
import com.arf.platform.vo.RequestDataVo;
import com.arf.traffic.TrafficInterface;
import com.google.gson.Gson;

@Transactional
@Service("arriveReportBusinessDealServiceImpl")
public class ArriveReportBusinessDealServiceImpl extends BaseService implements IParkingUpReportBusinessDealService {
	
	private static Logger log = LoggerFactory.getLogger(ArriveReportBusinessDealServiceImpl.class);
	
	@Resource(name = "RBookrecordServiceImpl")
	private RBookrecordService rBookrecordServiceImpl;
	
	@Resource(name = "PParkingcarServiceImpl")
	private PParkingcarService pParkingcarServiceImpl;
	
	@Resource(name = "parkingOrderRecordServiceImpl")
	private ParkingOrderRecordService parkingOrderRecordServiceImpl;
	
	@Resource(name = "payModeNotifyHttpServiceImpl")
	private IPayModeNotifyService payModeNotifyHttpServiceImpl;
	
	@Resource(name="sMemberAccountServiceImpl")
	private ISMemberAccountService sMemberAccountServiceImpl;
	
	@Resource(name = "communityModelServiceImpl")
	private CommunityModelService communityModelService;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "sysconfigServiceImpl")
	private SysconfigService sysconfigService;
	
	@Value("${DWON_REQUEST_URL}")
	protected String DWON_REQUEST_URL;
	
	@Resource(name = "userGoldCardAccountServiceImpl")
	private IUserGoldCardAccountService userGoldCardAccountServiceImpl;
	
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

	@Override
	public Map<String,Object> process(String version, String communityNo, RequestDataVo vo) {
		Map<String,Object> map = null;
		int ver = Integer.parseInt(version.trim());
		//判断版本号
		if(ver == 1){
			//判断停车场类型
			if(vo.getParkingType() == BaseService.PARKING_TYPE_COMMUNITY){
				map = processVersion1((ArriveReportVo)vo);
			}else if(vo.getParkingType() == BaseService.PARKING_TYPE_EMERGENCY){
				map = processVersion1Emergency((ArriveReportVo)vo);
			}
			TrafficInterface inf=new TrafficInterface();//调用数据汇报接口（E停车珠海妇幼保健医院到交管局）入场汇报
			inf.parkingArriveReport((ArriveReportVo)vo, sysconfigService);
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
	private Map<String,Object> processVersion1(ArriveReportVo vo) {
		Map<String,Object> map = null;
		//1、取出业务数据
//		String recordId = vo.getRecordId();//在停车场下，能唯一标记一次来车信息
//		String licenseColor = vo.getLicenseColor();//车牌颜色
//		String carColor = vo.getCarColor();//车身颜色
//		String carType = vo.getCarType();//车型
		Integer isBook = vo.getIsBook();//是否有预定停车？ 0没有，1有
		String orderSn = vo.getOrderSn();//订单号
		//如果有预定，查询订单号是否存在
		if(isBook != null && isBook == 1){
			ParkingOrderRecord parkingOrderRecord = parkingOrderRecordServiceImpl.findByOrderNo(orderSn);
			if(parkingOrderRecord == null){
				map = new HashMap<String,Object>();
				map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_ORDER_ON_EXIST);
				map.put(LPConstants.RESULTMAP_KEY_MSG, "Order no exist");
				map.put(LPConstants.RESULTMAP_KEY_DATA, null);
				return map;
			}
			Integer status = parkingOrderRecord.getStatus();
			if(status == null){
				map = new HashMap<String,Object>();
				map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_ORDER_ON_EXIST);
				map.put(LPConstants.RESULTMAP_KEY_MSG, "Order no exist");
				map.put(LPConstants.RESULTMAP_KEY_DATA, null);
				return map;
			}
			if(status != null && (status != ParkingOrderRecord.Status.Waiting.ordinal() || status != ParkingOrderRecord.Status.Delayed.ordinal())){
				map = new HashMap<String,Object>();
				map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_ORDER_ON_EXIST);
				map.put(LPConstants.RESULTMAP_KEY_MSG, "Order no exist");
				map.put(LPConstants.RESULTMAP_KEY_DATA, null);
				return map;
			}
		}
		String license = vo.getLicense();//车牌号码，UTF8编码
		String arriveCarImgUrl = vo.getArriveCarImgUrl();//来车图片url，utf8编码
		String arriveTime = vo.getArriveTime();//时间戳(秒)
		Integer stopType = vo.getStopType();//停车类型 0月卡车,1临时车,2免费车,3电子账户车
		String berthNo = vo.getBerthNo();//车位编号，UTF8编码
		
		//2、创建 实时停车信息表 实体类
		PParkingcar pParkingcar = null;
		
		//3、查询是否重复汇报（车牌号，车来时间）
		pParkingcar = pParkingcarServiceImpl.findByCommLicArr(vo.getCommunityNo(), license, new Date(Long.valueOf(arriveTime)*1000l));
		if(pParkingcar != null){
			map = new HashMap<String,Object>();
			map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_DUPLICATET_REPORT_ARRIVE);
			map.put(LPConstants.RESULTMAP_KEY_MSG, "Duplicatet Report!");
			map.put(LPConstants.RESULTMAP_KEY_DATA, null);
			log.info(">>>>>:[停车场来车汇报processVersion1]此车牌 \"" + license + "\"来车重复汇报！");
			return map;
		}
		//蓝鹏入场摄像机多次识别导致多次汇报，以最后一次为准
		//查询是否重复汇报（根据小区号，车牌号）
		pParkingcar = pParkingcarServiceImpl.findByCommLic(vo.getCommunityNo(), license);
//		if(pParkingcar != null){
//			map = new HashMap<String,Object>();
//			map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_DUPLICATET_REPORT_ARRIVE);
//			map.put(LPConstants.RESULTMAP_KEY_MSG, "Duplicatet Report!");
//			map.put(LPConstants.RESULTMAP_KEY_DATA, null);
//			log.info(">>>>>:[停车场来车汇报processVersion1]此车牌 \"" + license + "\"未出场，不能来车汇报！");
//			return map;
//		}
		//查询是否重复汇报（车牌号）（同一车牌，同一时间，不可能在多个小区有来车汇报）
//		List<PParkingcar> illegalParkingcarList = pParkingcarServiceImpl.findByLic(license);
//		if(CollectionUtils.isNotEmpty(illegalParkingcarList)){
//			pParkingcar = illegalParkingcarList.get(0);
//		}
//		if(pParkingcar != null){
//			map = new HashMap<String,Object>();
//			map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_DUPLICATET_REPORT_ARRIVE);
//			map.put(LPConstants.RESULTMAP_KEY_MSG, "Duplicatet Report!");
//			map.put(LPConstants.RESULTMAP_KEY_DATA, null);
//			log.info(">>>>>:[停车场来车汇报processVersion1]此车牌 \"" + license + "\"已在其他小区入场，不能来车汇报！");
//			return map;
//		}
		if(pParkingcar == null){
			pParkingcar = new PParkingcar();
		}
		pParkingcar.setParkingId(Integer.valueOf(vo.getCommunity().getId().toString()));
		pParkingcar.setCommunityNo(vo.getCommunityNo());
		pParkingcar.setBerthNo(berthNo);
		pParkingcar.setArriveTime(new Date(Long.valueOf(arriveTime.trim()) * 1000L));
		pParkingcar.setLicense(license);
		pParkingcar.setReportTime(new Date());
		pParkingcar.setStopType(Short.valueOf(stopType.toString()));
		pParkingcar.setArriveCarImgUrl(arriveCarImgUrl);
		pParkingcar.setParkingType((short)PParkingcar.ParkingType.Community.ordinal());
		
		Integer propertyNumber = null;
    	Integer branchId = null;
    	if(MStringUntils.isNotEmptyOrNull(vo.getCommunityNo())){
	    	List<CommunityModel> communityList = communityModelService.checkByCommunity_number(vo.getCommunityNo());
	    	if(communityList != null && communityList.size()>0){
	    		propertyNumber = communityList.get(0).getProperty_number();
	    		branchId = communityList.get(0).getBranchId();
	    	}
    	}
    	pParkingcar.setPropertyNumber(propertyNumber);
    	pParkingcar.setBranchId(branchId);
		
		ParkRateModel parkRateModel = null;//白名单
		LicensePlateModel licensePlateModel = null;//车牌表
		TemporaryLicensePlate temporaryLicensePlate = null;//临时停车表
		
		String username = null;
		//4、月租车还是临时车操作？
		if(stopType == 0){//月租车
			parkRateModel = parkRateModelServiceImpl.selectByLicenseComunity(vo.getCommunityNo(), license);
			if(parkRateModel != null){
//				String username = parkRateModel.getUserName()==null?"":parkRateModel.getUserName();
//				if("nullUser".equals(username)){
//					log.info(">>>>>:[停车场来车汇报processVersion1]parkRate白名单表未找到此车牌信息 \"" + license + "\",小区\""+ vo.getCommunityNo() +"\"!");
//				}else{
//				}
				parkRateModel.setInStatus(1);//进出状态修改 1:入 0:出
				parkRateModel.setInTime(Integer.valueOf(arriveTime));//进场时间
				parkRateModel.setModifyDate(new Date());
				log.info(">>>>>:[停车场来车汇报processVersion1]parkRate白名单车牌 \"" + license + "\"，小区\""+ vo.getCommunityNo() +"\"来车汇报!");
				parkRateModelServiceImpl.update(parkRateModel);
			}else{
				log.info(">>>>>:[停车场来车汇报processVersion1]parkRate白名单表未找到此车牌信息 \"" + license + "\",小区\""+ vo.getCommunityNo() +"\"!");
			}
		} else if(stopType == 1){//临时车
			List<LicensePlateModel> listLicensePlateModel = licensePlateModelServiceImpl.findByLicenseAndNotNulluser(license);
			if(CollectionUtils.isEmpty(listLicensePlateModel)){//车牌表license_plate 为空（未在平台绑定的临时车）
				log.info(">>>>>:[停车场来车汇报processVersion1]此车牌 \"" + license + "\" 未在平台绑定!");
			}else{//车牌表license_plate 不为空
				licensePlateModel = listLicensePlateModel.get(0);
				username = licensePlateModel.getUser_name();
				if("nullUser".equals(username.trim())){
					log.info(">>>>>:[停车场来车汇报processVersion1]此车牌 \"" + license + "\" 未在平台绑定!");
				}else{//平台的临时车（紧急场所称之为：电子账户车）
					try {
						temporaryLicensePlate = temporaryLicensePlateServiceImpl.selectByLicensePlateAndCommunityNumber(license,vo.getCommunityNo());
						if(temporaryLicensePlate == null){//查询临时停车表temporary_license_plate 为空
							temporaryLicensePlate = new TemporaryLicensePlate();
							temporaryLicensePlate.setCommunityNumber(vo.getCommunityNo());
							temporaryLicensePlate.setLicensePlateNumber(license);
							temporaryLicensePlate.setCommandParameter(0);
							temporaryLicensePlate.setStartTime(Long.valueOf(arriveTime));
							temporaryLicensePlate.setParkTime(new BigDecimal(0));
							temporaryLicensePlate.setState(1);
							temporaryLicensePlate.setFailReason("0");
							log.info(">>>>>:[停车场来车汇报processVersion1]临时车牌 \"" + license + "\",小区\""+ vo.getCommunityNo() +"\"来车汇报!");
							temporaryLicensePlateServiceImpl.save(temporaryLicensePlate);
						}else{// 查询临时停车表temporary_license_plate 不为空
							temporaryLicensePlate.setStartTime(Long.valueOf(arriveTime));
							temporaryLicensePlate.setState(1);
							temporaryLicensePlate.setFailReason("0");
							log.info(">>>>>:[停车场来车汇报processVersion1]临时车牌 \"" + license + "\",小区\""+ vo.getCommunityNo() +"\"来车汇报!");
							temporaryLicensePlateServiceImpl.update(temporaryLicensePlate);
						}
					} catch (Exception e) {
						log.error(">>>>>:[停车场来车汇报processVersion1]temporaryLicensePlateServiceImpl数据库操作异常"
								+ "，此操作不影响主业务，所以单独抛出异常 license = " + license,e);
					}
				}
			}
		}
		
		//5、数据库操作
		if(pParkingcar.getId() == null){
    		pParkingcarServiceImpl.save(pParkingcar);
    	}else{
    		pParkingcarServiceImpl.update(pParkingcar);
    	}
		saveAxd(vo, parkRateModel, username);
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
	private Map<String,Object> processVersion1Emergency(ArriveReportVo vo) {
		Map<String,Object> map = null;
		//取出业务数据
//		String recordId = vo.getRecordId();//在停车场下，能唯一标记一次来车信息
//		String licenseColor = vo.getLicenseColor();//车牌颜色
//		String carColor = vo.getCarColor();//车身颜色
//		String carType = vo.getCarType();//车型
		Integer isBook = vo.getIsBook();//是否有预定停车？ 0没有，1有
		String orderSn = vo.getOrderSn();//订单号
		String license = vo.getLicense();//车牌号码，UTF8编码
		String arriveCarImgUrl = vo.getArriveCarImgUrl();//来车图片url，utf8编码
		String arriveTime = vo.getArriveTime();//时间戳(秒)
		String arriveTimeStr = DateUtils.getDateStrByTimestamp(Long.valueOf(arriveTime.trim()), null);
		Integer stopType = vo.getStopType();//停车类型 0月卡车,1临时车,2免费车,3电子账户车
		String berthNo = vo.getBerthNo();//车位编号，UTF8编码
		Integer parkingType = vo.getParkingType();
		
		//如果有预定，查询订单号是否存在
		if(isBook != null && isBook == 1){
			ParkingOrderRecord parkingOrderRecord = parkingOrderRecordServiceImpl.findByOrderNo(orderSn);
			if(parkingOrderRecord != null){//订单存在，修改订单状态
				if(parkingOrderRecord.getStatus() == ParkingOrderRecord.Status.Waiting.ordinal()){
					parkingOrderRecord.setStatus(ParkingOrderRecord.Status.Finished.ordinal());
					parkingOrderRecord.setArriveTime(new Date(Long.valueOf(arriveTime.trim()) * 1000L));
					parkingOrderRecordServiceImpl.update(parkingOrderRecord);
				}
			}
		}else{
			ParkingOrderRecord parkingOrderRecord = parkingOrderRecordServiceImpl.findByLicenArriveTime(license,new Date());
			if(parkingOrderRecord != null){//订单存在，修改订单状态
				if(parkingOrderRecord.getStatus() == ParkingOrderRecord.Status.Waiting.ordinal()){
					parkingOrderRecord.setStatus(ParkingOrderRecord.Status.Finished.ordinal());
					parkingOrderRecord.setArriveTime(new Date(Long.valueOf(arriveTime.trim()) * 1000L));
					parkingOrderRecordServiceImpl.update(parkingOrderRecord);
				}
			}
		}
		
		ParkRateModel parkRateModel = null;//白名单
		String userName = null;//会员名称
		Integer fieldType = vo.getFieldType();//场中场类型 0场外 1场内
		if(fieldType == null || fieldType == 0){//场中场为：外场，进行来车汇报处理
			//查询是否重复汇报（根据小区号，车牌号，车来时间）
			PParkingcar pParkingcar = pParkingcarServiceImpl.findByCommLicArr(vo.getCommunityNo(), license, new Date(Long.valueOf(arriveTime)*1000l));
			if(pParkingcar != null){
				map = new HashMap<String,Object>();
				map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_DUPLICATET_REPORT_ARRIVE);
				map.put(LPConstants.RESULTMAP_KEY_MSG, "Duplicate Report!");
				map.put(LPConstants.RESULTMAP_KEY_DATA, null);
				log.info(">>>>>:[停车场来车汇报processVersion1]此车牌 \"" + license + "\"来车重复汇报！");
				return map;
			}
			//蓝鹏入场摄像机多次识别导致多次汇报，以最后一次为准
			//查询是否重复汇报（根据小区号，车牌号）
			pParkingcar = pParkingcarServiceImpl.findByCommLic(vo.getCommunityNo(), license);
//			if(pParkingcar != null){
//				map = new HashMap<String,Object>();
//				map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_DUPLICATET_REPORT_ARRIVE);
//				map.put(LPConstants.RESULTMAP_KEY_MSG, "Duplicate Report!");
//				map.put(LPConstants.RESULTMAP_KEY_DATA, null);
//				log.info(">>>>>:[停车场来车汇报processVersion1]此车牌 \"" + license + "\"未出场，不能来车汇报！");
//				return map;
//			}
			//查询是否重复汇报（车牌号）（同一车牌，同一时间，不可能在多个小区有来车汇报）
//			List<PParkingcar> illegalParkingcarList = pParkingcarServiceImpl.findByLic(license);
//			if(CollectionUtils.isNotEmpty(illegalParkingcarList)){
//				pParkingcar = illegalParkingcarList.get(0);
//			}
//			if(pParkingcar != null){
//				map = new HashMap<String,Object>();
//				map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_DUPLICATET_REPORT_ARRIVE);
//				map.put(LPConstants.RESULTMAP_KEY_MSG, "Duplicate Report!");
//				map.put(LPConstants.RESULTMAP_KEY_DATA, null);
//				log.info(">>>>>:[停车场来车汇报processVersion1]此车牌 \"" + license + "\"已在其他小区入场，不能来车汇报！");
//				return map;
//			}
			if(pParkingcar == null){
				pParkingcar = new PParkingcar();
			}
			pParkingcar.setParkingId(Integer.valueOf(vo.getParkingInfo().getId().toString()));
			pParkingcar.setCommunityNo(vo.getCommunityNo());
			pParkingcar.setBerthNo(berthNo);
			pParkingcar.setArriveTime(new Date(Long.valueOf(arriveTime.trim()) * 1000L));
			pParkingcar.setLicense(license);
			pParkingcar.setReportTime(new Date());
//			pParkingcar.setStopType(Short.valueOf(stopType.toString()));
			//停车类型：0月卡车,1临时车,3免费车,4电子账户车
			if(stopType == 3){
				pParkingcar.setStopType((short)2);
			}else if(stopType == 4){
				pParkingcar.setStopType((short)3);
			}else{
				pParkingcar.setStopType(Short.valueOf(stopType.toString()));
			}
			pParkingcar.setArriveCarImgUrl(arriveCarImgUrl);
			pParkingcar.setParkingType((short)PParkingcar.ParkingType.Parking.ordinal());
			Integer propertyNumber = null;
	    	Integer branchId = null;
	    	if(MStringUntils.isNotEmptyOrNull(vo.getCommunityNo())){
		    	List<CommunityModel> communityList = communityModelService.checkByCommunity_number(vo.getCommunityNo());
		    	if(communityList != null && communityList.size()>0){
		    		propertyNumber = communityList.get(0).getProperty_number();
		    		branchId = communityList.get(0).getBranchId();
		    	}
	    	}
	    	pParkingcar.setPropertyNumber(propertyNumber);
	    	pParkingcar.setBranchId(branchId);
			//实时停车信息 数据库操作
	    	if(pParkingcar.getId() == null){
	    		pParkingcarServiceImpl.save(pParkingcar);
	    	}else{
	    		pParkingcarServiceImpl.update(pParkingcar);
	    	}
			
			//判断此车是否是会员车
			LicensePlateModel licensePlateModel = null;//车牌表
			parkRateModel = parkRateModelServiceImpl.selectByLicenseComunity(vo.getCommunityNo(), license);
			boolean hasUserName = false;
			List<LicensePlateModel> listLicensePlateModel = licensePlateModelServiceImpl.findByLicenseAndNotNulluser(license);
			if(CollectionUtils.isEmpty(listLicensePlateModel)){//车牌表license_plate 为空（未在平台绑定的临时车）
				log.info(">>>>>:[停车场来车汇报processVersion1Emergency]此车牌 \"" + license + "\" 未在平台绑定!");
			}else{//车牌表license_plate 不为空
				licensePlateModel = listLicensePlateModel.get(0);
				userName = licensePlateModel.getUser_name();
				if("nullUser".equals(userName.trim())){//已解绑
					log.info(">>>>>:[停车场来车汇报processVersion1Emergency]此车牌 \"" + license + "\" 未在平台绑定!");
				}else if(parkRateModel == null && stopType != 0 && stopType!= 2){
					hasUserName = true;//查询到绑定信息
				}
			}
			if(parkRateModel != null){
				parkRateModel.setInStatus(1);//进出状态修改 1:入 0:出
				parkRateModel.setInTime(Integer.valueOf(arriveTime));//进场时间
				parkRateModel.setModifyDate(new Date());
				parkRateModelServiceImpl.update(parkRateModel);
			}
			//TODO 暂时不做电子钱包支付 2016-08-05 by dengsongping
			//TODO 使用电子钱包支付  	2016-09-26 by dengsongping
			//收费方式下发停车场、推送消息给车主（短信、app、电子钱包余额不足提醒），非主要业务，可以失败
			if(hasUserName){
				try {
					double amoutLeft = 0;
					//停车卡
					UserGoldCardAccountDto userGoldCardAccountDto = userGoldCardAccountServiceImpl.findByUserName(userName);
					if(userGoldCardAccountDto != null){
						BigDecimal balance = userGoldCardAccountDto.getBalance();
						if(balance != null){
							amoutLeft = amoutLeft + balance.doubleValue();
						}
					}
//					//查询电子账户余额
					SMemberAccount account = sMemberAccountServiceImpl.findByUserNameUserType(userName,(byte)Member.Type.member.ordinal());
					double basicAccount = 0d;
					if(account != null){
						BigDecimal basic = account.getBasicAccount();
						if(basic != null){
							basicAccount = basic.doubleValue();
						}
//						DownPayModeNotifyVo downPayModeNotifyVo = new DownPayModeNotifyVo();
//						downPayModeNotifyVo.setVersion("1");
//						downPayModeNotifyVo.setLicense(license);
//						downPayModeNotifyVo.setPayMode(1);
//						downPayModeNotifyVo.setBalance(basic.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue());
//						downPayModeNotifyVo.setOrderNo(orderSn==null?"":orderSn);
//						downPayModeNotifyVo.setBerthNo(berthNo==null?"":berthNo);
//						downPayModeNotifyVo.setIsOrder(isBook);
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
//											log.info(">>>>>:[停车场来车汇报processVersion1Emergency]收费方式下发停车场：第"+(count+1)+"汇报");
//											System.out.println("==========收费方式下发停车场：第"+(count+1)+"汇报");
//											HttpRequestDealBusinessMsg message = payModeNotifyHttpServiceImpl.processV1(communityNo, voCopy, null);
//											if(message.getCode() == 0){
//												break;
//											}
//											count++;
//										}catch(Exception e){
//											e.printStackTrace();
//											log.error(">>>>>:[停车场来车汇报processVersion1Emergency]收费方式下发停车场异常", e);
//											count++;
//										}
//									}
//								}
//							});
//						}
						amoutLeft = new BigDecimal(amoutLeft + basicAccount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						//余额不足推送（停车卡 + 电子账户）
						if(amoutLeft < Double.valueOf(balanceNotify)){
							//短信推送
							sendSMSMessage(userName, "您的电子账户余额已不足"+ balanceNotify +"元,请及时充值");
							//app推送
							pushMessage(userName, "您的电子账户余额已不足"+ balanceNotify +"元,请及时充值", null);
						}
					}
				} catch (Exception e) {
					log.error(">>>>>:[停车场来车汇报processVersion1Emergency]推送异常", e);
				}
			}
		}
		saveAxd(vo, parkRateModel, userName);
		map = new HashMap<String,Object>();
		map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SUCCESS);
		map.put(LPConstants.RESULTMAP_KEY_MSG, "OK");
		map.put(LPConstants.RESULTMAP_KEY_DATA, null);
		return map;
	}
	
	private void saveAxd(ArriveReportVo vo,ParkRateModel parkRateModel,String userName){
		Integer stopType = vo.getStopType();
		String license = vo.getLicense();
		try {
			boolean isCommand = false;
			String isOpening = null;
			String axdTime = redisService.get(ParkRateModel.Key_Cache_Inout_Free_List_Prefix + ":" + vo.getCommunityNo() + "_" + license);
			if(StringUtils.isNotBlank(axdTime)){//缓存中存在，说明在自由出入范围内(用户使用过安心点)
				if(stopType == 0 && parkRateModel != null){//月租车
					//发送开闸指令
					isCommand = true;
					isOpening = "1";
				}
			}else{//缓存中不存在，说明在自由出入范围外(用户很久没使用安心点；或者是：用户没有下载安心点，或下载了安心点没有绑定车辆)
				if(stopType == 0 && parkRateModel != null){//月租车
					String community = parkRateModel.getCommunityNumber();
					PrivilegeCar privilegeCar = parkRateModelServiceImpl.findPrivilegeCarByLicenseCommunity(license, community);
					if(privilegeCar != null){//特权车
						//发送开闸指令
						isCommand = true;
						isOpening = "1";
					}else{
						//发送锁闸指令
						isCommand = true;
						isOpening = "3";
					}
				}
			}
			if(userName == null || "nullUser".equals(userName)){
				userName = "";
			}
			if(isCommand){
				Map<String, Object> mapReq = new HashMap<String, Object>();
				final Map<String, String> mapLP = new HashMap<String, String>();
				final String url = DWON_REQUEST_URL;
				mapReq.put("version", 1);
				mapReq.put("userId", userName);
				mapReq.put("license", license);
				mapReq.put("isOpening", isOpening);
				String mapIn = new Gson().toJson(mapReq);
				mapLP.put("interface_lanpeng", "61");
				mapLP.put("communityNo", vo.getCommunityNo());
				mapLP.put("bizData", mapIn);
				String result = HttpUtil.doHttpPost(url, mapLP);
				log.info(String.format(">>>>>:[停车场来车汇报processVersion1]调用蓝鹏开闸/锁闸授权指令"+url+"成功,参数"+JSON.toJSONString(mapLP)+",返回值:" + result));
				JSONObject jsonObject = JSONObject.parseObject(result);
				if(jsonObject.getInteger("code") == 0){
					if("1".equals(isOpening)){
						parkRateModel.setUserLocks(1);
						parkRateModel.setLock_status(1);
					}else if("3".equals(isOpening)){
						parkRateModel.setUserLocks(3);
						parkRateModel.setLock_status(3);
					}
					parkRateModelServiceImpl.update(parkRateModel);
				}
			}
		} catch (Exception e) {
			log.error(">>>>>:[停车场来车汇报processVersion1]自由出入配置异常", e);
		}
	}
	
}
