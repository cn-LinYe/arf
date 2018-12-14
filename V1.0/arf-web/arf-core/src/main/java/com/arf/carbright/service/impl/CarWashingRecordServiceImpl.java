package com.arf.carbright.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.arf.base.PageResult;
import com.arf.carbright.dao.CarWashingRecordDao;
import com.arf.carbright.dao.PBusinessDao;
import com.arf.carbright.dao.PackageTypeDao;
import com.arf.carbright.dto.PackageTypeSettingsDto;
import com.arf.carbright.dto.SaleroomOfPeriodDto;
import com.arf.carbright.entity.Box;
import com.arf.carbright.entity.BusinessServiceLimit;
import com.arf.carbright.entity.Cabinet;
import com.arf.carbright.entity.CarWashingRecord;
import com.arf.carbright.entity.CarWashingRecord.FeePayType;
import com.arf.carbright.entity.CarWashingRecord.Status;
import com.arf.carbright.entity.PBusiness;
import com.arf.carbright.entity.PackageType;
import com.arf.carbright.entity.RPackageRecord;
import com.arf.carbright.service.BoxService;
import com.arf.carbright.service.CabinetService;
import com.arf.carbright.service.CarWashingRecordService;
import com.arf.carbright.service.IBusinessServiceLimitService;
import com.arf.carbright.service.PUserRangService;
import com.arf.carbright.service.PackageTypeService;
import com.arf.carbright.service.PbusinessService;
import com.arf.carbright.service.RPackageRecordService;
import com.arf.carbright.service.impl.CarWashingRecordCancelOrder.CanceledType;
import com.arf.core.AppMessage;
import com.arf.core.AppMessageCode;
import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.Member;
import com.arf.core.file.FileServerService;
import com.arf.core.file.FileStoreService;
import com.arf.core.file.FileType;
import com.arf.core.oldws.PushMessage;
import com.arf.core.oldws.PushUntils;
import com.arf.core.service.CommunityModelService;
import com.arf.core.service.MemberService;
import com.arf.core.service.SmsService;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.core.util.BeanUtils;
import com.arf.member.service.IRAccountTransferRecordService;
import com.arf.notice.NoticeSource;
import com.arf.payment.OrderNumPrefixConstraint;
import com.arf.platform.service.ISMemberAccountService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;

@Service("carWashingRecordService")
public class CarWashingRecordServiceImpl extends BaseServiceImpl<CarWashingRecord, Long> implements CarWashingRecordService{
	
	@Resource(name = "carWashingRecordDao")
	private CarWashingRecordDao carWashingRecordDao;
	
	@Resource(name = "boxService")
	private BoxService boxService;
	
	@Resource(name = "fileStoreService")
	private FileStoreService fileStoreService;
	
	@Resource(name = "rAccountTransferRecordServiceImpl")
	private IRAccountTransferRecordService accountTransferRecordService;
	
	@Resource(name = "sMemberAccountServiceImpl")
	private ISMemberAccountService memberAccountService;
	
	@Resource(name = "rPackageRecordService")
	private RPackageRecordService packageRecordService;
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "pBusinessDao")
	private PBusinessDao businessDao;
	
	@Resource(name = "pBusinessService")
	private PbusinessService businessService;
	
	@Resource(name = "packageTypeDao")
	private PackageTypeDao packageTypeDao;
	
	@Resource(name = "cabinetService")
	private CabinetService cabinetService;
	
	@Resource(name = "puserRangService")
	private PUserRangService userRangService;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "communityModelServiceImpl")
	private CommunityModelService communityModelService;
	
	@Resource(name = "packageTypeService")
	private PackageTypeService packageTypeServiceImpl;
	
	@Resource(name = "fileServerService")
	private FileServerService fileServerService;
	
	@Resource(name = "smsServiceImpl")
	private SmsService smsService;
	
	@Resource(name="businessServiceLimitServiceImpl")
	private IBusinessServiceLimitService businessServiceLimitService;
	
	public final int IMAGEURL_LENGH = 512;
	public final int IMAGE_ARRAY_LENGH = 4;
	
	public final PushUntils userPushUntils = PushUntils.getUserPushUntils();
	public final PushUntils merchantPushUntils = PushUntils.getMerchantPushUntils();
	
	@Override
	protected BaseDao<CarWashingRecord, Long> getBaseDao() {
		return carWashingRecordDao;
	}

	/**
	 * 检查商户对订单的下单限制设置
	 * @param order
	 * @param pkgRecord
	 * @return 根据套餐设置判断是否可下单
	 */
	private boolean checkBookingSettings(CarWashingRecord order,RPackageRecord pkgRecord){
		PackageTypeSettingsDto settingObj = redisService.get(PackageType.Prefix_Cache_Package_Type_Setting + pkgRecord.getPackageTypeNum(),PackageTypeSettingsDto.class);
		if(settingObj != null){
			Integer limitOfDay = settingObj.getLimitOfDay();
			if(limitOfDay != null){
				Integer bookingCountOfDay = redisService.get(PackageType.Prefix_Cache_Package_Type_Booking_Count_Of_Day + pkgRecord.getPackageTypeNum(), Integer.class);
				if(bookingCountOfDay != null && bookingCountOfDay >= limitOfDay){
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public AppMessage bookingOrder(final CarWashingRecord order,boolean genOrderNo,Integer businessNum,String cabinetNum,MultipartFile[] locationImage) {
		/**
		 * 车主下订单先要查询套餐记录是否存在并可用,不可用泽直接返回
		 * 查询“小区箱子表(e_box)”，
		 * 判断是否有空的可用箱子，
		 * 如果有锁定其中一个柜子更改其状态为“锁定”，
		 * 同时操作业务订单表r_car_washing_record，生成一条订单记录；
		 * 
		 */
		AppMessage message = new AppMessage();
		cabinetNum=("".equals(cabinetNum))?null:cabinetNum;
		if(order.getPackageRecordId() == null || order.getPackageRecordId() <= 0){
			return AppMessage.error("下单前请先选择套餐");
		}
		final RPackageRecord pkgRecord = packageRecordService.find(order.getPackageRecordId());
		if(pkgRecord == null){
			return AppMessage.codeMessage(50001, "您的套餐不存在");
		}
		int used = pkgRecord.getUsedTimes();
		if(pkgRecord.getStatus() == RPackageRecord.Status.not_available.ordinal()
				|| used >= pkgRecord.getTimes()){
			return AppMessage.codeMessage(50002, "您的套餐已失效了");
		}
		
		if(pkgRecord.getEndTime() != null && new Date().after(pkgRecord.getEndTime())){
			pkgRecord.setStatus(RPackageRecord.Status.not_available.ordinal());
			packageRecordService.update(pkgRecord);
			return AppMessage.codeMessage(50002, "用户套餐(orderNo="+order.getOrderNo()+")已失效");
		}
		String pkgTypeNum = pkgRecord.getPackageTypeNum();
		if(StringUtils.isBlank(pkgTypeNum)){
			return AppMessage.codeMessage(50002, "用户套餐编码无效");
		}
		
		if(!checkBookingSettings(order,pkgRecord)){
			return AppMessage.codeMessage(50003, "商家接待人数已满，请使用其他套餐");
		}
		
		//查询商户信息
		final PBusiness business;
		if(businessNum!=null){
			business = businessService.findRedisToDbByBusinessId(businessNum);
		}else{
			long businessId = pkgRecord.getBusinessId().longValue();
			business = businessService.findRedisToDbById(businessId);//商户
		}
		if(business ==  null){
			return AppMessage.codeMessage(50006, "商户不存在");
		}
		BusinessServiceLimit businessLimit = businessServiceLimitService.findByBusinessNum(businessNum);
		if(businessLimit != null ){
			if((order.getOrderType()==CarWashingRecord.OrderType.Booking_Parking.ordinal() || order.getOrderType()==CarWashingRecord.OrderType.Carbrighter_Appearance.ordinal()
					|| order.getOrderType()==CarWashingRecord.OrderType.Carbrighter_Appearance_Interior.ordinal()) && (businessLimit.getCarBrighterStatus()!=null && businessLimit.getCarBrighterStatus()==BusinessServiceLimit.CarBrighterStatus.StopOrders.ordinal()))
			return AppMessage.codeMessage(50007, "非常抱歉，商户已经停止接单！");
		}
		
//		套餐类型编码	编码规则:
//			OTA打头的类型编号为洗外观的套餐;
//			OTB打头的类型编号为洗外观+内饰的套餐;
		if(pkgTypeNum.startsWith(PBusiness.Prefix_Carbrighter_Appearance_Interior)){
			order.setOrderType(CarWashingRecord.OrderType.Carbrighter_Appearance_Interior.ordinal());
		}else if(pkgTypeNum.startsWith(PBusiness.Prefix_Carbrighter_Appearance)){
			order.setOrderType(CarWashingRecord.OrderType.Carbrighter_Appearance.ordinal());
		}
		
		if(order.getOrderType() == CarWashingRecord.OrderType.Carbrighter_Appearance_Interior.ordinal() && (order.getWhetherKey() !=null && order.getWhetherKey()==CarWashingRecord.WhetherKey.Deposit.ordinal())){
			Box box = boxService.findAvailableOne(order.getParkingId(),cabinetNum);
			if(box == null){ //没有可用柜子
				message.setCode(6001);
				message.setMsg("智能钥匙柜暂无空位，请稍后再试");
				return message;
			}
			//Cabinet cab = cabinetService.findByNum(order.getParkingId(), box.getCabinetNum());
			box.setUseStatus(Box.UseStatus.Locked.ordinal());
			box.setLockedDate(new Date());
			order.setCabinetNum(box.getCabinetNum());
			order.setBoxNum(box.getBoxNum());
			//锁定柜子
			boxService.update(box);
			
			order.setBluetoothCertificate(box.getBluetoothCertificate());
		}
		String orderNo = OrderNumPrefixConstraint.getInstance().genOrderNo(OrderNumPrefixConstraint.PREFIX_CARBRIGHTER_CARWASHING, 8);
		if(genOrderNo){
			order.setOrderNo(orderNo);
		}
		pkgRecord.setUsedTimes(++used);
		order.setCurrentTimes(used);
		order.setLicense(order.getLicense());//下单采用APP前台传输的车牌号码作为订单车牌
		//判断次数是否用尽,用尽的话设置为不可用
		if(pkgRecord.getUsedTimes() >= pkgRecord.getTimes()){
			pkgRecord.setStatus(RPackageRecord.Status.not_available.ordinal());
		}
		
		packageRecordService.update(pkgRecord);
		if(businessNum!=null){
			order.setBusinessId(business.getId());
		}else{
			order.setBusinessId(pkgRecord.getBusinessId().longValue());
		}
		order.setFeePayStatus(CarWashingRecord.PaidStatus.Paid.ordinal());
		
		String cabinetAddress=null;
		if(cabinetNum!=null){
			Cabinet cab = cabinetService.findByNum(order.getParkingId(), cabinetNum);
			if(cab!=null){//获取柜子地址
				cabinetAddress=cab.getCabinetAddress();
				order.setCabinetAddress(cabinetAddress);
			}			
		}
		String location = null;
		try {
			if (locationImage!=null&&locationImage.length > 0) {
				location = uploadPic(locationImage);
				order.setLocationImage(location);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		this.save(order);
		//查询商户信息
		//long businessId = order.getBusinessId();
		//final PBusiness business = businessService.find(businessId);//商户
		//if(business !=  null){
		Map<String,Object> extraData = new HashMap<String,Object>();
		Map<String,Object> orderMap = BeanUtils.bean2Map(order);
			orderMap.put("cabinetAddress", cabinetAddress);
			orderMap.put("businessName", business.getBusinessName());
			orderMap.put("businessNum", business.getBusinessNum());
			orderMap.put("orderId", order.getId());
			orderMap.put("businessAddress", business.getAddress());
			orderMap.put("businessPic", business.getBusinessPic());
			String phone = business.getContactPhone();
			if(StringUtils.isBlank(phone)){
				phone = business.getMobile();
			}
			if(StringUtils.isBlank(phone)){
				phone = business.getLoginName();
			}
			orderMap.put("businessPhone", phone);
		//}
		
		//套餐记录相关信息
		orderMap.put("packageName", pkgRecord.getPackageName());
		orderMap.put("packageDesc", pkgRecord.getPackageDesc());
		orderMap.put("packageTypeNum", pkgRecord.getPackageTypeNum());
		orderMap.put("times", pkgRecord.getTimes());//总可用次数
		orderMap.put("usedTimes", pkgRecord.getUsedTimes());//已经使用次数
		orderMap.put("packageStartTime", DateFormatUtils.format(pkgRecord.getStartTime(), "yyyy-MM-dd HH:mm:ss"));//套餐有效期开始
		orderMap.put("packageEndTime", DateFormatUtils.format(pkgRecord.getEndTime(), "yyyy-MM-dd HH:mm:ss"));//套餐有效期结束
		
		
		// 推送给商家信息
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String content="您有新的"+pkgRecord.getPackageName()+"洗车订单(" + order.getLicense() + ")，请尽快确认";
					PushMessage pushMessage = new PushMessage("新的点滴洗订单",PushMessage.ContentType.CARBRIGHTER_ORDER, content);
					merchantPushUntils.pushNotificationMsgToUser(business.getLoginName(),
							content,
							"新的点滴洗订单", 
							pushMessage, 
							null);
					String mobile=business.getMobile();
					if(StringUtils.isBlank(mobile)){
						mobile=business.getLoginName();
					}
					smsService.send(mobile, content);
				} catch (APIConnectionException | APIRequestException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		//对该订单的今日下单统计数+1
		Integer bookingCountOfDay = redisService.get(PackageType.Prefix_Cache_Package_Type_Booking_Count_Of_Day + pkgRecord.getPackageTypeNum(), Integer.class);
		bookingCountOfDay = (bookingCountOfDay == null ? 1:(bookingCountOfDay + 1));
		//TODO增加字段等待服务人数
		Integer[] status={CarWashingRecord.Status.Not_Confirm.ordinal(),CarWashingRecord.Status.Confirmed.ordinal(),CarWashingRecord.Status.User_stored_Key.ordinal()};
		List<CarWashingRecord> carWashingRecords= carWashingRecordDao.findByStatus(status, order.getBusinessId());
		Integer waitingNum=0;
		if (carWashingRecords!=null) {
			waitingNum=carWashingRecords.size();
			if (waitingNum>=1) {
				waitingNum=(waitingNum-1);
			}
		}
		orderMap.put("waitingNum", waitingNum);
		orderMap.put("locationImage", location);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, +1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date createStart = calendar.getTime();
		long interval = createStart.getTime() / 1000L - new Date().getTime() / 1000L;
		redisService.set(PackageType.Prefix_Cache_Package_Type_Booking_Count_Of_Day + pkgRecord.getPackageTypeNum(), bookingCountOfDay,interval);
		
		extraData.put("order", orderMap);
		return AppMessage.success("恭喜您！订单预定成功", extraData);
	}

	@Override
	public AppMessage cancelOrder(long orderId) {
		CarWashingRecordCancelOrder cancelOrder=new CarWashingRecordCancelOrder();
		cancelOrder.setAccountTransferRecordService(accountTransferRecordService);
		cancelOrder.setBoxService(boxService);
		cancelOrder.setBusinessService(businessService);
		cancelOrder.setCarWashingRecordService(this);
		cancelOrder.setCommunityModelService(communityModelService);
		cancelOrder.setMemberAccountService(memberAccountService);
		cancelOrder.setMemberService(memberService);
		cancelOrder.setPackageRecordService(packageRecordService);
		cancelOrder.setRedisService(redisService);
		//填充取消订单时间
		final CarWashingRecord order = this.find(orderId);
		this.update(order);
		
		int messcode=cancelOrder.cancelOrder(orderId,CanceledType.User);
		AppMessage message = new AppMessage();
		if(messcode== 6001){
			message.setCode(6001);
			message.setMsg("订单不存在,取消失败!");
			return message;
		}
		if(messcode== 200){
			return AppMessage.success("恭喜您,订单取消成功!", null);
		}
		if(messcode== 201){
			return AppMessage.success("订单取消成功", null);
		}
		if(messcode== 6002){
			message.setCode(6002);
			message.setMsg("商家已接单，不能取消订单");
			return message;
		}
		return message;		
	}

	@Override
	public AppMessage merchantOperateOrder(long id, OperateType operateType,final Double startMileage,final Double endMileage) {
		if(operateType == null){
			return AppMessage.codeMessage(6009,"抱歉,操作不可用!");
		}
		final CarWashingRecord order = this.find(id);
		if(order == null){
			return AppMessage.error("订单不存在");
		}
		int orderStatus = order.getStatus();
		//final PBusiness business = businessService.find(order.getBusinessId());
		Member member = memberService.findByUsername(order.getUserName());
		if (OperateType.Merchant_Confirm_Order.ordinal() == operateType.ordinal()) {
				if(order.getOrderType()==CarWashingRecord.OrderType.Carbrighter_Appearance_Interior.ordinal() && (order.getWhetherKey() !=null && order.getWhetherKey() == CarWashingRecord.WhetherKey.Deposit.ordinal())
						&&orderStatus!=CarWashingRecord.Status.User_stored_Key.ordinal()){
					return AppMessage.error("用户还没有存钥匙，不能确认订单");
				}
				//判断状态是不是未确认状态
				if(orderStatus == CarWashingRecord.Status.Not_Confirm.ordinal()){
					//判断订单是否已经支付
					if(order.getFeePayStatus() == CarWashingRecord.PaidStatus.Paid.ordinal()){
						order.setStatus(CarWashingRecord.Status.Confirmed.ordinal());
						if(order.getOrderType()==CarWashingRecord.OrderType.Carbrighter_Appearance.ordinal()
							|| (order.getOrderType()==CarWashingRecord.OrderType.Carbrighter_Appearance_Interior.ordinal()&& (order.getWhetherKey() !=null &&order.getWhetherKey() == CarWashingRecord.WhetherKey.NotDeposit.ordinal())))
						{
							order.setServiceDate(new Date());
						}
						this.update(order);
						return AppMessage.success("恭喜您,确认成功!", null);
					}else{
						return AppMessage.codeMessage(6001,"抱歉,该订单还未支付成功!");
					}
				}else if(orderStatus == CarWashingRecord.Status.User_stored_Key.ordinal()){
					NoticeSource notoce=new NoticeSource();
					final String noticeContent="商家已确认您的点滴洗订单(" + order.getLicense() + ")";
					notoce.createNotice(order.getUserName(), noticeContent,null,NoticeSource.NoticeType.DRIP_WASH_NOTICE, redisService);
					//检查用户是否有开启接受信息
					if("0".equals(member.getIsSendPush())){						
						// 推送给用户信息
						new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									PushMessage pushMessage = new PushMessage("订单已被确认",PushMessage.ContentType.CARBRIGHTER_ORDER, noticeContent);
									userPushUntils.pushNotificationMsgToUser(order.getUserName(),
											noticeContent,
											"订单已被确认", 
											pushMessage, 
											null);
								} catch (APIConnectionException | APIRequestException e) {
									e.printStackTrace();
								}
							}
						}).start();
					}
				}else if(orderStatus == CarWashingRecord.Status.Canceled.ordinal() || orderStatus == CarWashingRecord.Status.System_Canceled.ordinal()){
					return AppMessage.codeMessage(6002,"抱歉,用户已取消该订单！");
				}else{
					return AppMessage.codeMessage(6002,"抱歉,该订单已经确认过了!");
				}
			
		}
		
		
		
		if (OperateType.Merchant_Cancel_Order.ordinal() == operateType.ordinal()) {
			// TODO 待定
			return AppMessage.codeMessage(6009,"抱歉,操作不可用!");
		}

		
		if (OperateType.Merchant_Confirm_Fetch_Key.ordinal() == operateType.ordinal()) {
			//如果商户已经确认了订单,则可以进行该操作,确认已经取了钥匙,服务状态更改为Servcing
			if(orderStatus == CarWashingRecord.Status.Confirmed.ordinal() || orderStatus == CarWashingRecord.Status.User_stored_Key.ordinal()){
				order.setStatus(CarWashingRecord.Status.Servicing.ordinal());
				order.setArriveTime(new Date());
				order.setStartMileage(startMileage);
				if(order.getOrderType()==CarWashingRecord.OrderType.Carbrighter_Appearance_Interior.ordinal() && (order.getWhetherKey() !=null && order.getWhetherKey() == CarWashingRecord.WhetherKey.Deposit.ordinal()))
				{
					order.setServiceDate(new Date());
				}
				this.update(order);
				String message ="";
				if(startMileage==null){
					 message = "点滴洗商家已取走您的车钥匙，即将为您的爱车 " + order.getLicense() + "提供服务";
				}else{
					 message = "点滴洗商家已取走您的车钥匙，即将为您的爱车 " + order.getLicense() + "提供服务"+"，您的爱车当前里程数为"+startMileage;
				}
				final String content = message;
				final NoticeSource notoce=new NoticeSource();	
				//检查用户是否有开启接受信息
				if("0".equals(member.getIsSendPush())){
					// 推送给用户信息
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {								
								if(order.getOrderType() == CarWashingRecord.OrderType.Carbrighter_Appearance_Interior.ordinal() && (order.getWhetherKey() != null && order.getWhetherKey() == CarWashingRecord.WhetherKey.Deposit.ordinal())){
									PushMessage pushMessage = new PushMessage("钥匙已被取出",PushMessage.ContentType.CARBRIGHTER_ORDER, content);
									userPushUntils.pushNotificationMsgToUser(order.getUserName(),
											content, 
											"钥匙已被取出", 
											pushMessage, 
											null);
									notoce.createNotice(order.getUserName(), content, null,NoticeSource.NoticeType.DRIP_WASH_NOTICE, redisService);
								}else{
									String contentMessage="点滴洗商家正在为您的爱车 " + order.getLicense() + " 提供服务，请稍候";
									PushMessage pushMessage = new PushMessage("商户正在为您服务",PushMessage.ContentType.CARBRIGHTER_ORDER, contentMessage);
									userPushUntils.pushNotificationMsgToUser(order.getUserName(),
											contentMessage, 
											"商户正在为您服务", 
											pushMessage, 
											null);
									notoce.createNotice(order.getUserName(), contentMessage, null,NoticeSource.NoticeType.DRIP_WASH_NOTICE, redisService);
								}
							} catch (APIConnectionException | APIRequestException e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
				
				return AppMessage.success("恭喜您,操作成功!", null);
			}else if(orderStatus == CarWashingRecord.Status.Not_Confirm.ordinal()){
				return AppMessage.codeMessage(6003,"您还没有确认该订单,请先确认!");
			}else if(orderStatus == CarWashingRecord.Status.Servicing.ordinal()){
				return AppMessage.success("您已经确认过了,无需重复确认!");
			}else if(orderStatus == CarWashingRecord.Status.Canceled.ordinal()){
				return AppMessage.codeMessage(6009,"抱歉，用户已取消订单!");
			}
		}

		if (OperateType.Merchant_Finished_Order.ordinal() == operateType.ordinal()) { //商户存入车钥匙后，更新订单状态为“已完成” 
			order.setStatus(CarWashingRecord.Status.Merchant_Completed.ordinal());
			order.setLeaveTime(new Date());
			order.setCompleteDate(new Date());
			order.setEndMileage(endMileage);
			this.update(order);
			
			String message ="";
			if(endMileage==null){
				 message = "您的点滴洗订单(" + order.getLicense() + ")已完成服务，商家已将钥匙送还到钥匙柜，您可以随时取钥匙拿车";
			}else{
				 message = "您的点滴洗订单(" + order.getLicense() + ")已完成服务，您的爱车当前里程数为"+endMileage+"，商家已将钥匙送还到钥匙柜，您可以随时取钥匙拿车";
			}
			final String content = message;
			final NoticeSource notoce=new NoticeSource();	
			//检查用户是否有开启接受信息
			if("0".equals(member.getIsSendPush())){
				// 推送给用户信息
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {							
							if(order.getOrderType() == CarWashingRecord.OrderType.Carbrighter_Appearance_Interior.ordinal() && (order.getWhetherKey() != null && order.getWhetherKey() == CarWashingRecord.WhetherKey.Deposit.ordinal())){
								PushMessage pushMessage = new PushMessage("钥匙已送回",PushMessage.ContentType.CARBRIGHTER_ORDER, content);
								userPushUntils.pushNotificationMsgToUser(order.getUserName(),
										content, 
										"钥匙已送回", 
										pushMessage, 
										null);
								String mobile=order.getPhone();
								if(StringUtils.isBlank(mobile)){
									mobile=order.getUserName();
								}
								notoce.createNotice(order.getUserName(), content, null,NoticeSource.NoticeType.DRIP_WASH_NOTICE, redisService);
								smsService.send(mobile, content);								
							}else{
								String content="您的点滴洗订单(" + order.getLicense() + ")已完成服务，您可以随时取车";
								PushMessage pushMessage = new PushMessage("商户已完成",PushMessage.ContentType.CARBRIGHTER_ORDER, content);
								userPushUntils.pushNotificationMsgToUser(order.getUserName(),
										content, 
										"商户已完成", 
										pushMessage, 
										null);
								String mobile=order.getPhone();
								if(StringUtils.isBlank(mobile)){
									mobile=order.getUserName();
								}
								notoce.createNotice(order.getUserName(), content, null,NoticeSource.NoticeType.DRIP_WASH_NOTICE, redisService);
								smsService.send(mobile, content);
							}
						} catch (APIConnectionException | APIRequestException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
			
			return AppMessage.success("恭喜您,确认成功!", null);
		}
		return AppMessage.codeMessage(6009,"抱歉,操作不可用!");
	}
	
	
	@Override
	public AppMessage storeKey(long orderId) {
		final CarWashingRecord order = this.find(orderId);
		if(order == null){
			return AppMessage.codeMessage(60001, "订单不存在(orderId="+orderId+")");
		}
		//Box box = boxService.findByNumber(order.getCabinetNum());
		//String bluetoothCer = box.getBluetoothCertificate();
		//order.setBluetoothCertificate(bluetoothCer);
		order.setStatus(CarWashingRecord.Status.User_stored_Key.ordinal());
		this.update(order);
		
		// 推送给商户信息
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Long businessId= order.getBusinessId();
					PBusiness business = businessService.findRedisToDbById(businessId);//商户
					String content="" + order.getLicense() + "车主已将钥匙存放到钥匙柜，请尽快取钥匙提供服务";
					if(order.getOrderType() == CarWashingRecord.OrderType.Carbrighter_Appearance_Interior.ordinal()){
						PushMessage pushMessage = new PushMessage("用户钥匙已存入",PushMessage.ContentType.CARBRIGHTER_ORDER, content);
						merchantPushUntils.pushNotificationMsgToUser(business.getLoginName(),
								content, 
								"用户钥匙已存入", 
								pushMessage, 
								null);
						String mobile=business.getMobile();
						if(StringUtils.isBlank(mobile)){
							mobile=business.getLoginName();
						}
						smsService.send(mobile, content);
					}
				} catch (APIConnectionException | APIRequestException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("order", order);
		return AppMessage.success("ok", map);
	}

	@Override
	public AppMessage fetchKey(long orderId) {
		CarWashingRecord order = this.find(orderId);
		if(order == null){
			return AppMessage.codeMessage(60001, "订单不存在(orderId="+orderId+")");
		}
		if(StringUtils.isNotBlank(order.getBoxNum())){
			Box box = boxService.findByNumber(order.getParkingId(),order.getCabinetNum(),order.getBoxNum());
			if(box != null){
				box.setUseStatus(Box.UseStatus.Not_Used.ordinal());
				box.setLockedDate(null);
				boxService.update(box);
			}
		}
		
		order.setBluetoothCertificate("已失效");
		order.setStatus(CarWashingRecord.Status.Member_Completed.ordinal());
		this.update(order);
		return AppMessage.success("ok");
	}

	@Override
	public AppMessage uploadImages(Long id, ImageType imgType,Integer osName,Integer appVersionCode,MultipartFile ...files) throws Exception {
		int type = imgType.ordinal();
		CarWashingRecord order = this.find(id);
		if(order == null){
			return AppMessage.codeMessage(6001, "订单不存在");
		}
		JSONArray array = new JSONArray();
		String imges = null;
		if(type == ImageType.Car_Washing_Pre.ordinal()){//洗车前
			imges = order.getOrderPrePic();
		}else if(type == ImageType.Car_Washing_Post.ordinal()){//洗车后
			imges = order.getOrderAfterPic();
		}
		if(StringUtils.isNotBlank(imges)){
			array = JSON.parseArray(imges);
		}
		if(array != null && array.size() >= IMAGE_ARRAY_LENGH){
			return AppMessage.error("您上传的图片已超出上限");
		}
		String paths[] =  new String[files.length];

		//将MultipartFile转成io.File
		File tmps[] = new File[files.length];
		int index = 0;
		for (MultipartFile file : files) {
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			File newFile = File.createTempFile(UUID.randomUUID().toString(), "."+extension);
			file.transferTo(newFile);
			tmps[index] = newFile;
			index++;
		} 
		
		if((osName != null && osName == 0 && appVersionCode > 26)
				||(osName != null && osName == 1 && appVersionCode > 32)){
			for (int i=0;i<files.length;i++) {
				paths[i] = fileServerService.upload2Server(tmps[i], FileType.IMAGE, null, null, null);
			}
		}else{
			//安卓的app版本小于27，ios的app版本小于36需要保持在两个地方
			paths = fileStoreService.storeFiles(FileType.IMAGE, tmps);
			for (int i=0;i<files.length;i++) {
				String fileName = FilenameUtils.getName(paths[i]);
				fileServerService.upload2Server(tmps[i], FileType.IMAGE, null, fileName, null);
			}
		}
		
		
		for(File tempFile : tmps){
			try{
				if(tempFile != null && tempFile.exists())
					tempFile.delete();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		if(paths != null && paths.length > 0){
			Collections.addAll(array, paths);
		}
		String imageUrl =null;
		int size=0;
		if (array!=null&&array.size()>0) {
			 imageUrl = array.toJSONString();	
			 size = imageUrl.length();
		}
		
		if(size >= IMAGEURL_LENGH){
			return AppMessage.error("您上传的图片已超出上限");
		}
		if(type == ImageType.Car_Washing_Pre.ordinal()){ //洗车前
			order.setOrderPrePic(imageUrl);
			this.update(order);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("order", order);
			return AppMessage.success("ok", map);
		}else if(type == ImageType.Car_Washing_Post.ordinal()){ //洗车后
			order.setOrderAfterPic(imageUrl);
			this.update(order);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("order", order);
			return AppMessage.success("ok", map);
		}
		return AppMessage.error("上传图片类型不正确");
	}

	@Override
	public AppMessage payOrder(long orderId, FeePayType payType) {
//		if(orderId <= 0 || payType == null){
//			return AppMessage.error("订单Id或者payType有误");
//		}
//		if(payType.ordinal() != FeePayType.Paid_Package.ordinal()){
//			return AppMessage.codeMessage(60001,"暂时只支持套餐支付");
//		}
//		CarWashingRecord order = this.find(orderId);
//		if(order != null){
//			return AppMessage.codeMessage(60002, "根据订单Id("+orderId+")没有查询到订单记录");
//		}
//		
//		if(order.getFeePayStatus() == CarWashingRecord.PaidStatus.Paid.ordinal()){
//			return AppMessage.codeMessage(60003, "您的订单(编号:"+order.getOrderNo()+")已经支付,无需重复支付");
//		}
//		
//		Long pkgRecordId = order.getPackageRecordId();
//		if(pkgRecordId){
//			
//		}
//		return null;
		return AppMessage.error("该接口暂不使用");
	}

	@Override
	public AppMessage findMyOrders(String userName,Long packageRecordId, Status status, int pageSize, int pageNo) {
		if(packageRecordId != null && packageRecordId <= 0){
			packageRecordId = null;
		}
		PageResult<CarWashingRecord> pageResult = carWashingRecordDao.findPackageUsedRecords(userName, packageRecordId, status, pageSize, pageNo);
		Map<String,Object> map = new HashMap<String,Object>();
		List<CarWashingRecord> orderList = pageResult.getList();
		List<Map<String,Object>> dataList = Lists.transform(orderList, new Function<CarWashingRecord, Map<String,Object>>() {

			@Override
			public Map<String,Object> apply(CarWashingRecord input) {
				Integer status=input.getStatus();
				if(status!=null&&status==CarWashingRecord.Status.System_Canceled.ordinal()){//将系统取消状态 协议变更为取消
					input.setStatus(CarWashingRecord.Status.Canceled.ordinal());
				}
				Map<String,Object> mapBean = BeanUtils.bean2Map(input);
				//查询商户信息
				Long businessId = input.getBusinessId();
				PBusiness business=null;
				if(businessId!=null){
					business = businessService.findRedisToDbById(businessId);//商户
				}				
				if(business !=  null){
					mapBean.put("businessName", business.getBusinessName());
					mapBean.put("businessNum", business.getBusinessNum());
					mapBean.put("orderId", input.getId());
					
					mapBean.put("businessAddress", business.getAddress());
					mapBean.put("businessPic", business.getBusinessPic());
					mapBean.put("businessGrade ", business.getBusinessGrade());
					mapBean.put("businessComment ", business.getBusinessComment());
					String phone = business.getContactPhone();
					if(StringUtils.isBlank(phone)){
						phone = business.getMobile();
					}
					if(StringUtils.isBlank(phone)){
						phone = business.getLoginName();
					}
					mapBean.put("businessPhone", phone);
					
				}
				
				//套餐记录相关信息
				Long pkgRecordId = input.getPackageRecordId();
				RPackageRecord pkgRecord=null;
				if(pkgRecordId!=null){
					pkgRecord = packageRecordService.find(pkgRecordId);
				}
				if(pkgRecord != null){
					mapBean.put("packageName", pkgRecord.getPackageName());
					mapBean.put("packageDesc", pkgRecord.getPackageDesc());
					mapBean.put("packageTypeNum", pkgRecord.getPackageTypeNum());
					mapBean.put("times", pkgRecord.getTimes());//总可用次数
					mapBean.put("usedTimes", pkgRecord.getUsedTimes());//已经使用次数
					mapBean.put("packageStartTime", DateFormatUtils.format(pkgRecord.getStartTime(), "yyyy-MM-dd HH:mm:ss"));//套餐有效期开始
					mapBean.put("packageEndTime", DateFormatUtils.format(pkgRecord.getEndTime(), "yyyy-MM-dd HH:mm:ss"));//套餐有效期结束
				}
				
				return mapBean;
			}
			
		});
		
		map.put("data", dataList);
		map.put("totalNum", pageResult.getTotalNum());
		return AppMessage.success("ok", map);
	}
	
	@Override
	public List<CarWashingRecord> findOrderByUserName(String userName,String status) {
		return carWashingRecordDao.findOrderByUserName(userName,status);
	}

	@Override
	public CarWashingRecord findByOrderNo(String orderNo) {
		return carWashingRecordDao.findByOrderNo(orderNo);
	}

	@Override
	public AppMessage findMerchantOrders(String userName, Status status, int pageSize, int pageNo) {
		PBusiness businessUser = businessDao.findByUserName(userName);
		PageResult<CarWashingRecord> pageResult = carWashingRecordDao.findMerchantOrders(businessUser.getId(), status, pageSize, pageNo);
		Map<String,Object> map = new HashMap<String,Object>();
		List<CarWashingRecord> orderList = pageResult.getList();
		
		List<Map<String,Object>> dataList = Lists.transform(orderList, new Function<CarWashingRecord, Map<String,Object>>() {

			@Override
			public Map<String,Object> apply(CarWashingRecord input) {
				Integer status=input.getStatus();
				if(status!=null&&status==CarWashingRecord.Status.System_Canceled.ordinal()){//将系统取消状态 协议变更为取消
					input.setStatus(CarWashingRecord.Status.Canceled.ordinal());
				}
				Map<String,Object> mapBean = BeanUtils.bean2Map(input);
				//查询商户信息
				Long businessId = input.getBusinessId();
				 PBusiness business=null;
				if(businessId!=null){
					business = businessService.findRedisToDbById(businessId);//商户
				}
				if(business !=  null){
					//mapBean.put("businessName", business.getBusinessName());
					//mapBean.put("businessNum", business.getBusinessNum());
					mapBean.put("orderId", input.getId());
				}
				
				//套餐记录相关信息
				Long pkgRecordId = input.getPackageRecordId();
				RPackageRecord pkgRecord =null;
				if(pkgRecordId!=null){
					pkgRecord = packageRecordService.find(pkgRecordId);	
				}				 
				if(pkgRecord != null){
					mapBean.put("packageName", pkgRecord.getPackageName());
					mapBean.put("packageDesc", pkgRecord.getPackageDesc());
					mapBean.put("packageTypeNum", pkgRecord.getPackageTypeNum());
					mapBean.put("times", pkgRecord.getTimes());//总可用次数
					mapBean.put("usedTimes", pkgRecord.getUsedTimes());//已经使用次数
					mapBean.put("packageStartTime", DateFormatUtils.format(pkgRecord.getStartTime(), "yyyy-MM-dd HH:mm:ss"));//套餐有效期开始
					mapBean.put("packageEndTime", DateFormatUtils.format(pkgRecord.getEndTime(), "yyyy-MM-dd HH:mm:ss"));//套餐有效期结束
				}
				
				return mapBean;
			}
			
		});
		map.put("data", dataList);
		map.put("totalNum", pageResult.getTotalNum());
		return AppMessage.success("ok", map);
	}
	
	@Override
	public AppMessage orderMerchantAnalysis(Long businessId, HttpServletRequest request) {
		AppMessage result;
		Map<String,Object> extrDatas = new HashMap<String,Object>();
		int notComfirm = carWashingRecordDao.orderMerchantAnalysis(businessId,CarWashingRecord.Status.Not_Confirm.ordinal());
		int confirmed = carWashingRecordDao.orderMerchantAnalysis(businessId,CarWashingRecord.Status.Confirmed.ordinal());
		int servicing = carWashingRecordDao.orderMerchantAnalysis(businessId,CarWashingRecord.Status.Servicing.ordinal());
		int merchantCompleted = carWashingRecordDao.orderMerchantAnalysis(businessId,CarWashingRecord.Status.Merchant_Completed.ordinal());
		int canceled = carWashingRecordDao.orderMerchantAnalysis(businessId,CarWashingRecord.Status.Canceled.ordinal());
		
		//查询在售套餐,售出套餐记录
		int sellingCount = packageTypeDao.findByBusinessId(businessId, PackageType.IsEnabled.enablem).size();
		SaleroomOfPeriodDto saleroomOfPeriodDto = packageRecordService.findSaleroomOfPeriod(businessId, null, null);
		int soldCount  = saleroomOfPeriodDto.getCount();
		int soldTotalMoney = saleroomOfPeriodDto.getPackagePrice() - saleroomOfPeriodDto.getGiftAmount();//单位:元
		long todaySoldCount = 0;
		try {
			todaySoldCount = packageRecordService.count(
						new Filter("businessId", Operator.eq, businessId, true),
						new Filter("buyingDate", Operator.ge, DateUtils.parseDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"), new String[]{DEFAULT_DATE_FORMATTER}), true),
						new Filter("buyingDate", Operator.le, DateUtils.parseDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59"), new String[]{DEFAULT_DATE_FORMATTER}), true),
						new Filter("feePayStatus", Operator.eq, CarWashingRecord.PaidStatus.Paid.ordinal(), true)
					);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		extrDatas.put("notComfirm", notComfirm);
		extrDatas.put("confirmed", confirmed);
		extrDatas.put("servicing", servicing);
		extrDatas.put("merchantCompleted", merchantCompleted);
		extrDatas.put("canceled", canceled);
		extrDatas.put("sellingCount", sellingCount);
		extrDatas.put("soldCount", soldCount);
		extrDatas.put("soldTotalMoney", soldTotalMoney);//销售总额,单位:元
		extrDatas.put("todaySoldCount", todaySoldCount);//今日售出多少单
		result = new AppMessage(AppMessageCode.CODE_SUCCESS,AppMessageCode.MSG_SUCCESS,extrDatas);
		
		return result;
	}
	@Override
	public String uploadPic(MultipartFile ...files) throws IOException {
		JSONArray array = new JSONArray();
		String paths[] =  new String[files.length];
		//将MultipartFile转成io.File
		File tmps[] = new File[files.length];
		int index = 0;
		for (MultipartFile file : files) {
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			File newFile = File.createTempFile(UUID.randomUUID().toString(), "."+extension);
			file.transferTo(newFile);
			tmps[index] = newFile;
			index++;
		} 
		for (int i=0;i<files.length;i++) {
			paths[i] = fileServerService.upload2Server(tmps[i], FileType.IMAGE, null, null, null);
		}
		for(File tempFile : tmps){
			try{
				if(tempFile != null && tempFile.exists())
					tempFile.delete();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(paths != null && paths.length > 0){
			Collections.addAll(array, paths);
		}
		String imageUrl = array.toJSONString();
		return imageUrl;
	}

	@Override
	public CarWashingRecord findByOrderNum(String orderNo) {
		return carWashingRecordDao.findByOrderNum(orderNo);
	}

}
