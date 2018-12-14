package com.arf.carbright.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.arf.carbright.entity.Box;
import com.arf.carbright.entity.CarWashingRecord;
import com.arf.carbright.entity.PBusiness;
import com.arf.carbright.entity.PackageType;
import com.arf.carbright.entity.RPackageRecord;
import com.arf.carbright.service.BoxService;
import com.arf.carbright.service.CarWashingRecordService;
import com.arf.carbright.service.PbusinessService;
import com.arf.carbright.service.RPackageRecordService;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.Member;
import com.arf.core.oldws.PushMessage;
import com.arf.core.oldws.PushUntils;
import com.arf.core.service.CommunityModelService;
import com.arf.core.service.MemberService;
import com.arf.core.util.MStringUntils;
import com.arf.member.entity.RAccountTransferRecord;
import com.arf.member.service.IRAccountTransferRecordService;
import com.arf.platform.entity.SMemberAccount;
import com.arf.platform.service.ISMemberAccountService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;

public class CarWashingRecordCancelOrder {
	
	private BoxService boxService;	
	private IRAccountTransferRecordService accountTransferRecordService;	
	private ISMemberAccountService memberAccountService;	
	private RPackageRecordService packageRecordService;	
	private MemberService memberService;
	private PbusinessService businessService;	
	private RedisService redisService;	
	private CommunityModelService communityModelService;
	private CarWashingRecordService carWashingRecordService;
	
	public final PushUntils userPushUntils = PushUntils.getUserPushUntils();
	public final PushUntils merchantPushUntils = PushUntils.getMerchantPushUntils();
	
	public BoxService getBoxService() {
		return boxService;
	}
	public void setBoxService(BoxService boxService) {
		this.boxService = boxService;
	}
	public IRAccountTransferRecordService getAccountTransferRecordService() {
		return accountTransferRecordService;
	}
	public void setAccountTransferRecordService(IRAccountTransferRecordService accountTransferRecordService) {
		this.accountTransferRecordService = accountTransferRecordService;
	}
	public ISMemberAccountService getMemberAccountService() {
		return memberAccountService;
	}
	public void setMemberAccountService(ISMemberAccountService memberAccountService) {
		this.memberAccountService = memberAccountService;
	}
	public RPackageRecordService getPackageRecordService() {
		return packageRecordService;
	}
	public void setPackageRecordService(RPackageRecordService packageRecordService) {
		this.packageRecordService = packageRecordService;
	}
	public MemberService getMemberService() {
		return memberService;
	}
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	public PbusinessService getBusinessService() {
		return businessService;
	}
	public void setBusinessService(PbusinessService businessService) {
		this.businessService = businessService;
	}
	public RedisService getRedisService() {
		return redisService;
	}
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
	public CommunityModelService getCommunityModelService() {
		return communityModelService;
	}
	public void setCommunityModelService(CommunityModelService communityModelService) {
		this.communityModelService = communityModelService;
	}
	public CarWashingRecordService getCarWashingRecordService() {
		return carWashingRecordService;
	}
	public void setCarWashingRecordService(CarWashingRecordService carWashingRecordService) {
		this.carWashingRecordService = carWashingRecordService;
	}
	
	public enum CanceledType{
		System,User;
	}
	/**
	 * @param orderId 订单ID
	 * @param system 0系统取消 1用户取消
	 * @return
	 */
	public int cancelOrder(long orderId,final CanceledType canceledType) {
		//根据订单的状态判断是否允许取消订单，
		//允许取消则直接更改订单状态，不能取消订单，提示原因；
		final CarWashingRecord order = carWashingRecordService.find(orderId);
		if(order == null){
			return 6001;
		}
		int status = order.getStatus();
		int paidStatus = order.getFeePayStatus();
		if(status == CarWashingRecord.Status.Canceled.ordinal()){//已经取消的直接返回
			return 201;
		}
		
		if(((status == CarWashingRecord.Status.Not_Confirm.ordinal()|| status == CarWashingRecord.Status.Confirmed.ordinal() ) && order.getOrderType()==CarWashingRecord.OrderType.Carbrighter_Appearance.ordinal())
				|| (order.getOrderType()==CarWashingRecord.OrderType.Carbrighter_Appearance_Interior.ordinal() && status == CarWashingRecord.Status.Not_Confirm.ordinal())){//可以取消
			final RPackageRecord packageRecord = packageRecordService.find(order.getPackageRecordId());
			//如果是已经支付的订单(非套餐支付)需要将用户的钱退回到在线账户中
			if(paidStatus == CarWashingRecord.PaidStatus.Paid.ordinal() && 
					order.getFeePayType() != CarWashingRecord.FeePayType.Paid_Package.ordinal()){ //已经支付成功
				BigDecimal feeBd = order.getFee();
				if(feeBd != null && feeBd.compareTo(new BigDecimal(0)) > 0){
					//退款
					SMemberAccount account = memberAccountService.findByUserNameUserType(order.getUserName(),(byte) SMemberAccount.Type.member.ordinal());
					if(account != null){
						BigDecimal basicAccount = account.getBasicAccount();
						if(basicAccount == null){
							basicAccount = new BigDecimal(0);
						}
						account.setBasicAccount(basicAccount.add(feeBd));
						
						RAccountTransferRecord record = new RAccountTransferRecord();
						record.setAccountBalance(account.getBasicAccount());
						record.setAccountId(account.getId()+"");
						record.setAccountNumber(account.getAccountNumber());
						record.setAccountType((byte) com.arf.member.entity.RAccountTransferRecord.AccountType.basicAccount.ordinal());
						record.setAmount(feeBd);
						record.setComment("点滴洗订单退款到在线账户");
						record.setIdentify(genOrderNo(10));
						record.setIsConfirmed((byte) com.arf.member.entity.RAccountTransferRecord.IsConfirmed.ok.ordinal());
						record.setOperateTime(new Date());
						record.setOperateType((byte) com.arf.member.entity.RAccountTransferRecord.OperateType.auto.ordinal());
						record.setOrderNo(order.getOrderNo());
						record.setSerialNumber(record.getIdentify());
						record.setStatus((byte) com.arf.member.entity.RAccountTransferRecord.Status.finished.ordinal());
						record.setType(com.arf.member.entity.RAccountTransferRecord.Type.chargeSystem);
						record.setUserName(order.getUserName());
						record.setUserType((byte) com.arf.member.entity.RAccountTransferRecord.UserType.Member.ordinal());
						
						Member member = memberService.findByUsername(order.getUserName());//可从member中获取community_number
						Integer propertyNumber = null;
				    	Integer branchId = null;
				    	if(MStringUntils.isNotEmptyOrNull(member.getCommunityNumber())){
					    	List<CommunityModel> communityList = communityModelService.checkByCommunity_number(member.getCommunityNumber());
					    	if(communityList != null && communityList.size()>0){
					    		propertyNumber = communityList.get(0).getProperty_number();
					    		branchId = communityList.get(0).getBranchId();
					    	}
				    	}
				    	record.setCommunityNumber(member.getCommunityNumber());
				    	record.setPropertyNumber(propertyNumber);
				    	record.setBranchId(branchId);
						
						accountTransferRecordService.save(record);
						memberAccountService.update(account);
					}
				}
			}else if(paidStatus == CarWashingRecord.PaidStatus.Paid.ordinal()
					&& order.getFeePayType() == CarWashingRecord.FeePayType.Paid_Package.ordinal()
					&& order.getPackageRecordId() != null ){//套餐支付的返还套餐次数
				
				int usedTimes = packageRecord.getUsedTimes();//已经使用的次数
				//判断次数是否用尽,用尽的话回复状态为可用
				Date endTime = packageRecord.getEndTime();
				if(endTime == null){
					packageRecord.setStatus(RPackageRecord.Status.available.ordinal());
				}else{
					if(packageRecord.getUsedTimes() >= packageRecord.getTimes() && endTime.getTime() > new Date().getTime()){
						packageRecord.setStatus(RPackageRecord.Status.available.ordinal());
					}
				}
				int useTimes=--usedTimes<0?0:usedTimes;
				packageRecord.setUsedTimes(useTimes);
				packageRecordService.update(packageRecord);
			}
			
			//修改小箱子状态
			String boxNum = order.getBoxNum();
			if(StringUtils.isNotBlank(boxNum)){
				Box box = boxService.findByNumber(order.getParkingId(),order.getCabinetNum(),boxNum);
				if(box != null){
					box.setUseStatus(Box.UseStatus.Not_Used.ordinal());
					box.setLockedDate(null);//锁定时间置null
					boxService.update(box);
				}
			}
			order.setStatus((canceledType.ordinal()==CanceledType.User.ordinal())?CarWashingRecord.Status.Canceled.ordinal():CarWashingRecord.Status.System_Canceled.ordinal());
			order.setBluetoothCertificate("已取消");
			order.setCancelDate(new Date());
			carWashingRecordService.update(order);
			if(canceledType.ordinal()==CanceledType.User.ordinal()){
				// 推送给商家信息
				final PBusiness business = businessService.findRedisToDbById(order.getBusinessId());
				new Thread(new Runnable() {
					@SuppressWarnings({ "unchecked", "rawtypes" })
					@Override
					public void run() {
						try {
							String msgContent = "用户已取消"+packageRecord.getPackageName()+"洗车订单(" + order.getLicense() + ")";
							PushMessage pushMessage = new PushMessage("订单已取消",PushMessage.ContentType.CARBRIGHTER_ORDER, msgContent);
							merchantPushUntils.pushNotificationMsgToUser(business.getLoginName(),
									msgContent,
									"订单已取消", 
									(Map) pushMessage, 
									null);
						} catch (APIConnectionException | APIRequestException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
			
			//对该订单的今日下单统计数-1
			if(DateFormatUtils.format(new Date(), "yyyy-MM-dd").equals(DateFormatUtils.format(order.getCreateDate(), "yyyy-MM-dd"))){
				Integer bookingCountOfDay = redisService.get(PackageType.Prefix_Cache_Package_Type_Booking_Count_Of_Day + packageRecord.getPackageTypeNum(), Integer.class);
				bookingCountOfDay = (bookingCountOfDay == null ? 0:(bookingCountOfDay - 1));
				if(bookingCountOfDay != null && bookingCountOfDay > 0){
					bookingCountOfDay = bookingCountOfDay - 1;
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DAY_OF_MONTH, +1);
					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					Date createStart = calendar.getTime();
					long interval = createStart.getTime() / 1000L - new Date().getTime() / 1000L;
					redisService.set(PackageType.Prefix_Cache_Package_Type_Booking_Count_Of_Day
							+ packageRecord.getPackageTypeNum(), bookingCountOfDay,interval);
				}
			}			
			return 200;
		}else{
			return 6002;
		}
	}
	/**
	 * 对订单编号做一个简单短时缓存
	 */
	@Deprecated //使用com.arf.core.OrderNumPrefixConstraint
	private final LoadingCache<String, String> orderNoCache = CacheBuilder.newBuilder().
			expireAfterWrite(3L, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
		Map<String,String> map = new ConcurrentHashMap<>();
		@Override
		public String load(String key) {
			String v = map.get(key);
			return v == null ? "": v;
		}

	});
	/**
	 *  "yyyyMMddHHmmssSSS" + randomCount位的随机唯一码生成策略
	 * @param randomCount
	 * @return
	 */
	@Deprecated
	private String genOrderNo(int randomCount){
		Date now = new Date();
		String timeStr = DateFormatUtils.format(now, "yyyyMMddHHmmssSSS");
		String no = "";
		while(true){
			no =  timeStr + RandomStringUtils.randomNumeric(randomCount);
			String exist = "";
			try {
				exist = orderNoCache.get(no);
			} catch (ExecutionException e) {
				 e.printStackTrace();
			}
			if(StringUtils.isBlank(exist)){
				orderNoCache.put(no, no);
				break;
			}
		}
		return no;
	}
}
