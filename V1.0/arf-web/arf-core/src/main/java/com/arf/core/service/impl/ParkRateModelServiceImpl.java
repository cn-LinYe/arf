/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arf.activity.parkrate.service.IActivityRecordService;
import com.arf.base.PageResult;
import com.arf.core.AppMessage;
import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.Order;
import com.arf.core.Order.Direction;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.ParkRateModelDao;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.LicensePlateModel;
import com.arf.core.entity.Member;
import com.arf.core.entity.ParkRateModel;
import com.arf.core.entity.ParkRateRecordModel;
import com.arf.core.entity.ParkRateTime;
import com.arf.core.oldws.PushUntils;
import com.arf.core.service.CommunityModelService;
import com.arf.core.service.LicensePlateModelService;
import com.arf.core.service.MemberService;
import com.arf.core.service.ParkRateModelService;
import com.arf.core.service.ParkRateRecordModelService;
import com.arf.core.service.ParkRateTimeService;
import com.arf.core.service.PaymentSetingModelService;
import com.arf.core.service.SysconfigService;
import com.arf.core.thread.ThreadPoolFactory;
import com.arf.core.util.HttpUtil;
import com.arf.core.util.MStringUntils;
import com.arf.eparking.AkParkingFactory;
import com.arf.gift.entity.GiftVoucherInfoRecord;
import com.arf.gift.service.CommunityPromotionsService;
import com.arf.gift.service.IGiftVoucherInfoRecordService;
import com.arf.gift.service.RebateRatioService;
import com.arf.member.dto.PointGiftDto;
import com.arf.member.entity.PointTransferRecord;
import com.arf.member.entity.PointTransferRecord.PointGiftBusinessType;
import com.arf.member.service.IPointTransferRecordService;
import com.arf.member.vo.PointGiftVo;
import com.arf.payment.AliPaymentCallBackData;
import com.arf.payment.OrderNumPrefixConstraint;
import com.arf.payment.WeixinPaymentCallBackData;
import com.arf.payment.dto.PaymentResultCacheDto;
import com.arf.payment.service.PaymentCallbackService;
import com.arf.payment.service.RegisterPaymentCallBackService;
import com.arf.platform.entity.PrivilegeCar;
import com.arf.platform.service.ISMemberAccountService;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;


/**
 * Service - ParkRate表
 * 
 * @author arf
 * @version 4.0
 */
@Service("parkRateModelServiceImpl")
public class ParkRateModelServiceImpl extends BaseServiceImpl<ParkRateModel, Long> implements ParkRateModelService, PaymentCallbackService {

	Logger log = LoggerFactory.getLogger(ParkRateModelServiceImpl.class);
	@Value("${DWON_REQUEST_URL}")
	protected String DWON_REQUEST_URL;
	@Value("${SOCKET_SERVER_URL}")
	protected String SOCKET_URL;
	
	@Resource(name = "parkRateModelDaoImpl")
	private ParkRateModelDao parkRateModelDao;
	@Resource(name = "parkRateRecordModelServiceImpl")
	private ParkRateRecordModelService parkRateRecordModelService;
	@Resource(name = "paymentSetingModelServiceImpl")
	private PaymentSetingModelService paymentSetingModelService;
	@Resource(name = "communityModelServiceImpl")
	private CommunityModelService communityModelService;
	@Resource(name = "redisService")
	private RedisService redisService;
	@Resource(name = "parkRateTimeServiceImpl")
	private ParkRateTimeService parkRateTimeService;
	@Resource(name = "licensePlateModelServiceImpl")
	private LicensePlateModelService licensePlateModelService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "rebateRatioServiceImpl")
	private RebateRatioService rebateRatioService;
	@Resource(name = "communityPromotionsServiceImpl")
	private CommunityPromotionsService communityPromotionsService;
	@Resource(name = "giftVoucherInfoRecordServiceImpl")
	private IGiftVoucherInfoRecordService giftVoucherInfoRecordService;
	@Resource(name = "sMemberAccountServiceImpl")
	private ISMemberAccountService memberAccountService;
	@Resource(name = "sysconfigServiceImpl")
	private SysconfigService sysconfigService;
	@Resource( name = "pointTransferRecordServiceImpl")
	private IPointTransferRecordService pointTransferRecordServiceImpl;
	@Resource(name = "activityRecordService")
	private IActivityRecordService activityRecordService;
	@Autowired
	private AkParkingFactory factory;
	
	@Override
	protected BaseDao<ParkRateModel, Long> getBaseDao() {
		return parkRateModelDao;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public ParkRateModel selectByLicenseComunity(String communityNumber, String licensePlateNumber) {
		return parkRateModelDao.selectByLicenseComunity(communityNumber, licensePlateNumber);
	}

    @Override
    public ParkRateModel selectByLicenseComunity(String userName,
            String licensePlateNumber, String communityNumber) {
        return parkRateModelDao.selectByLicenseComunity(userName, licensePlateNumber, communityNumber);
    }

    @Override
    public List<ParkRateModel> selectListByPlateNumber(String licensePlateNumber,String userName) {
        
        return parkRateModelDao.selectListByPlateNumber(licensePlateNumber,userName);
    }
    /***
     * 根据用户和他关联的车牌获取小区
     * @param licensePlateNumber
     * @return
     */
    @Override
	public List<ParkRateModel> selectListByUser(String userName){
        return parkRateModelDao.selectListByUser(userName);
    }

	@Override
	public List<ParkRateModel> selectListByCommunityNumber(String communityNumber) {
		// TODO Auto-generated method stub
		return parkRateModelDao.selectListByCommunityNumber(communityNumber);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<ParkRateModel> selectListByLicensePlate(String licensePlate) {
		return parkRateModelDao.selectListByLicensePlate(licensePlate);
	}

	
	/***
     * 根据车牌获取小区信息和是否开启安心点
     * @param licensePlateNumber
     * @return
     */
    @Override
	public List<ParkRateModel> selectListByPlateNumberAxd(String licensePlateNumber){
        return parkRateModelDao.selectListByPlateNumberAxd(licensePlateNumber);
    }
    /***
     * 根据车牌获取小区信息和是否开启安心点
     * @param licensePlateNumber
     * @return
     */
    @Override
	public List<ParkRateModel> selectAllListByPlateNumberAxd(String userName){
        return parkRateModelDao.selectAllListByPlateNumberAxd(userName);
    }


	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<ParkRateModel> selectListByLicensePlateIn(String licensePlate, Integer inStatus) {
		return parkRateModelDao.selectListByLicensePlateIn(licensePlate, inStatus);
	}

	@Override
	public List<ParkRateModel> selectAllUserNameAxd(){
	    return parkRateModelDao.selectAllUserNameAxd();
	}
	
	/***
     * 获取所有开通了安心点的小区
     * @return
     */
    @Override
	public List<ParkRateModel> selectAllCommunityAxd(){
        return parkRateModelDao.selectAllCommunityAxd();
    }
    
    /***
     * 获取所有需要锁定的车牌
     * @return
     */
    @Override
	public List<ParkRateModel> selectAllNeedLicensePlate(Integer times){
        return parkRateModelDao.selectAllNeedLicensePlate(times);
    }
    
    /**
     * 更新所有自动锁闸的车的状态
     * @param ids
     */
    @Override
	public void updateParkRate(String ids){
        parkRateModelDao.updateParkRate(ids);
    }
    
    /**
     * 获取用户所有的被自动锁闸的车牌
     * @param user_name
     * @return
     */
    @Override
	public List<ParkRateModel> selectAllLockLicensePlateByUser(String user_name){
        return parkRateModelDao.selectAllLockLicensePlateByUser(user_name);
    }
    
    /**
     * 获取用户所有在白名单表的车牌
     * @param user_name
     * @return
     */
    @Override
	public List<ParkRateModel> selectAllLicenseByParkRate(String user_name){
        return parkRateModelDao.selectAllLicenseByParkRate(user_name);
    }
    /**
     * 更新白名单用户名（根据车牌）
     * @param license
     * @param userNames
     */
    @Override
	public void updateUserName(String userNames,String ids){
    	  parkRateModelDao.updateUserName(userNames,ids);
    }

	@Override
	public List<ParkRateModel> findByUserName(@NotBlank String userName,String communityNumber) {
		List<LicensePlateModel> licensePlateModels=licensePlateModelService.CheckUser_name(userName);
		List<String> list =new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(licensePlateModels)) {
			for (LicensePlateModel licensePlateModel : licensePlateModels) {
					list.add(licensePlateModel.getLicense_plate_number());
			}
		}
		if(list.size()==0){
			return new ArrayList<ParkRateModel>();
		}
		List<Order> orders = new ArrayList<Order>();
		List<Filter> filters = new ArrayList<Filter>();
		orders.add(new Order("communityNumber", Direction.asc));
		filters.add(new Filter("licensePlateNumber", Operator.in, list, true));
		if(communityNumber != null){
			filters.add(new Filter("communityNumber", Operator.eq, communityNumber, true));
		}
		return this.findList(null, filters,orders);
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
       		//注册本service为支付回调业务逻辑
     		RegisterPaymentCallBackService.registerService(OrderNumPrefixConstraint.PREFIX_PARKING_MONTH_RENT_FEE_PAY, getClass()); 
       	}
	}
	
	/**
	 * 月租车缴费成功后的执行逻辑(微信、支付宝)
	 * @param outTradeNo
	 * @param totalFee
	 * @return
	 */
	private boolean paymentCallback(String outTradeNo,int totalFee,Integer payType){
		if(StringUtils.isBlank(outTradeNo)){
			return false;
		}
		final ParkRateRecordModel parkRateRecordModel = parkRateRecordModelService
				.selectByouttradeno(outTradeNo);
		/*PaymentSetingModel paymentSetingModel = paymentSetingModelService
				.findByCommunityNumber(parkRateRecordModel.getCommunityNumber());
		String partner = "";
		if (paymentSetingModel != null) {
			if (paymentSetingModel.getType() == 1 || paymentSetingModel.getType() == 2) {
				partner = paymentSetingModel.getAli_partner();
			}
		}*/
		if(parkRateRecordModel == null){
			log.info("在线支付月租车费用回调时获取到的parkRateRecordModel对象为null(outTradeNo="+outTradeNo+")");
			return false;
		}
		final String outTradeNoFinal = outTradeNo;
		ThreadPoolFactory.getInstance().addTask(new Runnable() {
			@Override
			public void run() {
				activityRecordService.parkRateActivity(parkRateRecordModel.getUserName(), outTradeNoFinal);
			}
		});
		if(totalFee != parkRateRecordModel.getAmount().multiply(BigDecimal.TEN.multiply(BigDecimal.TEN)).intValue()){
			log.info("在线支付月租车费用回调时获取到的parkRateRecordModel对象为的费用与回调请求中费用不相等(outTradeNo="+
					outTradeNo+";totalFee="+totalFee+")");
			return false;
		}
		if("9000".equals(parkRateRecordModel.getTradeStatus())){
			return true;
		}
		String communityNumber = parkRateRecordModel.getCommunityNumber();
		CommunityModel community = communityModelService.findByNumber(communityNumber);
		
		if(payType != null){
			if(payType == 1){
				parkRateRecordModel.setFeePayType(ParkRateRecordModel.FeePayType.Paid_WeiXin);
			}else if(payType == 2){
				parkRateRecordModel.setFeePayType(ParkRateRecordModel.FeePayType.Paid_Alipay);
			}
		}
		ParkRateModel parkRate = parkRateModelDao.selectByLicenseComunity(
				parkRateRecordModel.getCommunityNumber(), parkRateRecordModel.getLicensePlateNumber());
		if(parkRate != null){
			Date date = new Date(parkRateRecordModel.getEndTime() * 1000);
			parkRate.setEndTime(date);
			this.update(parkRate);
		}
		boolean notifyEcc = manualNotifyEcc(parkRateRecordModel, community);
		if(!notifyEcc){
			parkRateRecordModel.setIsSubmitSuccess(0);
			parkRateRecordModelService.update(parkRateRecordModel);
		}else{
			ParkRateTime parkRateTimeModel = new ParkRateTime();
			parkRateTimeModel.setCommand(4101);
			parkRateTimeModel.setLicensePlateNumber(parkRateRecordModel.getLicensePlateNumber());
			parkRateTimeModel.setCommunityNumber(communityNumber);
			parkRateTimeModel.setStartTime(parkRateRecordModel.getStartTime());
			parkRateTimeModel.setEndTime(parkRateRecordModel.getEndTime());

			parkRateTimeModel.setAmount(parkRateRecordModel.getAmount()); //单位元
			parkRateTimeModel.setType(1);
			parkRateTimeModel.setOrderid(parkRateRecordModel.getId());
			parkRateTimeModel.setPropertyNumber(parkRateRecordModel.getPropretyId());
			parkRateTimeModel.setBranchId(parkRateRecordModel.getBranchId());
			parkRateTimeService.save(parkRateTimeModel);

			
			parkRateRecordModel.setTradeStatus("9000");
			parkRateRecordModel.setIsSubmitSuccess(1);
			parkRateRecordModel.setPaidDate(new Date());
			parkRateRecordModelService.update(parkRateRecordModel);
		}
		
		
		try {
			//月租缴费赠送礼品卷
			//1、查询缴费的小区是否开通缴费赠送礼品卷（先查询小区活动表，再查返利优惠比例表）
			//2、如是：获得缴费时长类型与赠送等级匹配，并生成一条赠送礼品卷记录
//			BigDecimal totalFeeYuan = new BigDecimal(String.valueOf(totalFee)).divide(BigDecimal.TEN).divide(BigDecimal.TEN);//分转圆
//			BigDecimal giftAmount = BigDecimal.ZERO;
//			CommunityPromotions communityPromotions = communityPromotionsService.findByCommunityNumberMonthlyStatus(
//					communityNumber, CommunityPromotions.MonthlyPaymentStatus.PARTICIPATE);
//			RebateRatio rebateRatio = null;
//			if(communityPromotions != null){
//				rebateRatio = rebateRatioService.findByCommunityNumber(communityNumber,RebateRatio.RebateType.ECC_MONTH_PAY);
//			}
//			Integer effectiveDate = 0;//有效期（分钟）
//			if(rebateRatio!=null){
//				effectiveDate = rebateRatio.getEffectiveDate();
//				Integer rebate = 0;
//				long diffDays = (parkRateRecordModel.getEndTime()-parkRateRecordModel.getStartTime())/24*60*60;
//				if(diffDays<32){
//					//1个月
//					rebate = rebateRatio.getOneMonthRebate();
//				}else if(diffDays<93){
//					//3个月
//					rebate = rebateRatio.getThreeMonthRebate();
//				}else if(diffDays<190){
//					//6个年
//					rebate = rebateRatio.getSixMonthRebate();
//				}else if(diffDays<366){
//					//1年
//					rebate = rebateRatio.getOneYearRebate();
//				}else{
//					//2年
//					rebate = rebateRatio.getTwoYearRebate();
//				}
//				Double rebateDouble = rebate==null?0:rebate/100D;
//				giftAmount = totalFeeYuan.multiply(new BigDecimal(rebateDouble.toString()));
//				if(rebateDouble>0){
//					GiftVoucherInfoRecord gift = giftVoucherInfoRecordService.genRecordAboutParkrate(rebateRatio.getEffectiveDate(), 
//							GiftVoucherInfoRecord.Status.UNUSED,GiftVoucherInfoRecord.GetChannel.ECC_MONTH_PAY, parkRateRecordModel.getUserName(), 
//							communityNumber, giftAmount,outTradeNo);
//					giftVoucherInfoRecordService.save(gift);
//					parkRateRecordModel.setVoucherName(gift.getVoucherName());
//				}
//			}
//			
//			//赠送积分
//			//个人账户
//			SMemberAccount sMemberAccount = memberAccountService.findByUserNameUserTypeStatus(parkRateRecordModel.getUserName(), (byte)SMemberAccount.Type.member.ordinal(),
//					(byte)SMemberAccount.Status.usable.ordinal());
//			String consumePointPercent = sysconfigService.getParentValue(CommunityModel.ECCCONFIG).get(SMemberAccount.CONSUME_POINT_PERCENT);//消费赠送积分比例
//			Integer acquisitionPoint = 0;
//			if(consumePointPercent!=null && !consumePointPercent.equals("0")){
//				Integer point = sMemberAccount.getPoint();
//				point = point==null?0:point;
//				acquisitionPoint = totalFeeYuan.multiply(new BigDecimal(consumePointPercent)).intValue();
//				sMemberAccount.setPoint(point+acquisitionPoint);
//				memberAccountService.update(sMemberAccount);
//				parkRateRecordModel.setPoint(acquisitionPoint);
//				parkRateRecordModelService.update(parkRateRecordModel);
//			}
//			//积分账户流转记录生成 save
//			PointTransferRecord pointTransferRecord = new PointTransferRecord();
//			pointTransferRecord.setAccountId(sMemberAccount.getId().toString());
//			pointTransferRecord.setAccountNumber(sMemberAccount.getAccountNumber());
//			pointTransferRecord.setIdentify(RAccountTransferRecord.genIdentify(sMemberAccount.getAccountNumber()));
//			pointTransferRecord.setPointBalance(sMemberAccount.getPoint());
//			pointTransferRecord.setPointSub(acquisitionPoint);
//			pointTransferRecord.setOperateTime(new Date());
//			pointTransferRecord.setOperateType((byte)(PointTransferRecord.OperateType.inter.ordinal()));
//			pointTransferRecord.setPointType((byte)PointTransferRecord.PointType.gain.ordinal());
//			pointTransferRecord.setStatus((byte)(PointTransferRecord.Status.finished.ordinal()));
//			pointTransferRecord.setComment("月租续费赠送");
//			pointTransferRecord.setSubType((int)SubType.ECC_MONTH_PAY);
//			pointTransferRecord.setCommunityNumber(communityNumber);
//			pointTransferRecord.setPropertyNumber(parkRateRecordModel.getPropretyId());
//			pointTransferRecord.setBranchId(parkRateRecordModel.getBranchId());
//			pointTransferRecordServiceImpl.save(pointTransferRecord);
//			
//			//缓存信息
//			PaymentResultCacheDto paymentResult = new PaymentResultCacheDto();
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			paymentResult.setPaidDate(sf.format(new Date()));
//			paymentResult.setNotifyDate(sf.format(new Date()));
//			paymentResult.setTotalFee(totalFeeYuan);
//			paymentResult.setBalance(sMemberAccount.getBasicAccount());
//			paymentResult.setDescritionMsg("您本次消费获得了"+acquisitionPoint+"会员积分。");
//			if(effectiveDate > (365 * 24 * 60)){
//				int effectivetime = effectiveDate==0?0:(effectiveDate / (365 * 24 * 60));
//				paymentResult.setValidDate(effectivetime+"年");
//			}else{
//				int effectivetime = effectiveDate==0?0:(effectiveDate / (30 * 24 * 60));
//				paymentResult.setValidDate(effectivetime+"个月");
//			}
//			paymentResult.setGiftVoucher(giftAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
//			redisService.set(PaymentResultCacheDto.CACHE_KEY+parkRateRecordModel.getUserName(), paymentResult,PaymentResultCacheDto.CACHE_EXPIRATION);
			
			/*
			//赠送礼品卷
			BigDecimal totalFeeYuan = new BigDecimal(String.valueOf(totalFee)).divide(BigDecimal.TEN).divide(BigDecimal.TEN);//分转圆
			GiftVoucherInfoRecord gift = giftVoucherInfoRecordService.genRecordByPayParkRate(outTradeNo, totalFeeYuan,parkRateRecordModel);
			if(gift != null){
				parkRateRecordModel.setVoucherName(gift.getVoucherName());
			}
			
			//赠送积分
			Integer addPoint = memberAccountService.addUserPointByPayType(parkRateRecordModel.getUserName(), totalFeeYuan, 
					PointTransferRecord.SubType.ECC_MONTH_PAY, communityNumber, parkRateRecordModel.getPropretyId(), parkRateRecordModel.getBranchId());
			if(0<addPoint){
				parkRateRecordModel.setPoint(addPoint);
				parkRateRecordModelService.update(parkRateRecordModel);
			}
			
			//缓存信息
			PaymentResultCacheDto paymentResult = new PaymentResultCacheDto();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			paymentResult.setPaidDate(sf.format(new Date()));
			paymentResult.setNotifyDate(sf.format(new Date()));
			paymentResult.setTotalFee(totalFeeYuan);
			paymentResult.setBalance(BigDecimal.ZERO);
			paymentResult.setDescritionMsg("您本次消费获得了"+addPoint+"会员积分。");
			paymentResult.setPaymentResult(PaymentResultCacheDto.PaymentResult.NOTIFY_SUCCESS);
			long effectiveDate = (parkRateRecordModel.getEndTime()-parkRateRecordModel.getStartTime())/60;//有效期（分钟）
			
			if(effectiveDate > (365 * 24 * 60)){
				long effectivetime = effectiveDate==0?0:(effectiveDate / (365 * 24 * 60));
				paymentResult.setValidDate(effectivetime+"年");
			}else{
				long effectivetime = effectiveDate==0?0:(effectiveDate / (30 * 24 * 60));
				paymentResult.setValidDate(effectivetime+"个月");
			}
			if(gift!=null){
				paymentResult.setGiftVoucher(gift.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
			}else{
				paymentResult.setGiftVoucher(BigDecimal.ZERO);
			}
			redisService.set(PaymentResultCacheDto.CACHE_KEY+outTradeNo, paymentResult,PaymentResultCacheDto.CACHE_EXPIRATION);
			*/
			
			BigDecimal totalFeeYuan = new BigDecimal(String.valueOf(totalFee)).divide(BigDecimal.TEN).divide(BigDecimal.TEN);//分转圆
			this.gainGiftPointByPayParkRate(outTradeNo, totalFeeYuan, parkRateRecordModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notifyEcc;
	}
	
	
	@Override
	public void gainGiftPointByPayParkRate(String outTradeNo,BigDecimal totalFeeYuan,ParkRateRecordModel parkRateRecordModel){
		//赠送礼品卷
		GiftVoucherInfoRecord gift = giftVoucherInfoRecordService.genRecordByPayParkRate(outTradeNo, totalFeeYuan,parkRateRecordModel);
		if(gift != null){
			parkRateRecordModel.setVoucherName(gift.getVoucherName());
		}
		
		//赠送积分（161228 已用公共方法替换）
//		Integer addPoint = memberAccountService.addUserPointByPayType(parkRateRecordModel.getUserName(), totalFeeYuan, 
//				PointTransferRecord.SubType.ECC_MONTH_PAY,parkRateRecordModel.getCommunityNumber(), parkRateRecordModel.getPropretyId(), parkRateRecordModel.getBranchId());
		
		//调用积分获得公共方法 start
		PointGiftVo vo = new PointGiftVo(PointGiftBusinessType.PURCHASE,parkRateRecordModel.getUserName(),parkRateRecordModel.getCommunityNumber()
				,totalFeeYuan,PointTransferRecord.SubType.ECC_MONTH_PAY);
		PointGiftDto pointGiftDto = pointTransferRecordServiceImpl.pointGiftByBusiness(vo);
		boolean giftResult = pointGiftDto.isGiftResult();
		Integer addPoint = pointGiftDto.getGiftPoint();
		if(!giftResult){
			addPoint = 0;
		}
		//调用积分获得公共方法 end
		
		if(0<addPoint){
			parkRateRecordModel.setPoint(addPoint);
			parkRateRecordModelService.update(parkRateRecordModel);
		}
		
		//缓存信息
		PaymentResultCacheDto paymentResult = new PaymentResultCacheDto();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		paymentResult.setPaidDate(sf.format(new Date()));
		paymentResult.setNotifyDate(sf.format(new Date()));
		paymentResult.setTotalFee(totalFeeYuan);
		paymentResult.setBalance(BigDecimal.ZERO);
		if(0<addPoint){
			paymentResult.setDescritionMsg("您本次消费获得了"+addPoint+"会员积分。");
		}else{
			paymentResult.setDescritionMsg("");
		}
		paymentResult.setPaymentResult(PaymentResultCacheDto.PaymentResult.NOTIFY_SUCCESS);
		if(gift!=null){
			Calendar endDate =  Calendar.getInstance();
			endDate.setTime(gift.getEndDate());
			long effectiveDate = (endDate.getTimeInMillis()-Calendar.getInstance().getTimeInMillis())/(60*1000);//礼品劵有效期（分钟）
			effectiveDate += 5;//增加五分钟，抵消误差
			if(effectiveDate > (365 * 24 * 60)){
				long effectivetime = effectiveDate==0?0:(effectiveDate / (365 * 24 * 60));
				paymentResult.setValidDate(effectivetime+"年");
			}else{
				long effectivetime = effectiveDate==0?0:(effectiveDate / (30 * 24 * 60));
				paymentResult.setValidDate(effectivetime+"个月");
			}
			paymentResult.setGiftVoucher(gift.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
			paymentResult.setVoucherNumber(gift.getVoucherNumber());
		}else{
			paymentResult.setGiftVoucher(BigDecimal.ZERO);
		}
		
		redisService.set(PaymentResultCacheDto.CACHE_KEY+outTradeNo, paymentResult,PaymentResultCacheDto.CACHE_EXPIRATION);
		
	}
	
	@Override
	public boolean weixiCallback(WeixinPaymentCallBackData callbackData) {
		return paymentCallback(callbackData.getOut_trade_no(),callbackData.getTotal_fee(),1);
	}

	@Override
	public boolean alipayCallback(AliPaymentCallBackData callbackData) {
		return paymentCallback(callbackData.getOut_trade_no(),(int)(callbackData.getTotal_fee() * 100D),2);
	}

	@Override
	public boolean manualNotifyEcc(ParkRateRecordModel parkRateRecordModel,CommunityModel community) {
		String communityNumber = community.getCommunity_number();
		String outTradeNo = parkRateRecordModel.getOut_trade_no();
		//0 微信支付，1支付宝支付, 2钱包支付,  3银行卡, 4现金,  5套餐支付 6停车卡支付
		ParkRateRecordModel.FeePayType feePayType = parkRateRecordModel.getFeePayType();
		//蓝鹏月卡车续费接口，支付方式:0钱包支付，1微信支付，2支付宝支付，3 银行卡 4、现金
		Integer payType = null;
		if(feePayType != null){
			switch (feePayType.ordinal()) {
			case 0://微信支付
				payType = 1;
				break;
			case 1://支付宝支付
				payType = 2;
				break;
			case 2://钱包支付
				payType = 0;
				break;
			case 3://银行卡
				payType = 3;
				break;
			case 4://现金
				payType = 4;
				break;
			case 5://套餐支付
				payType = 5;
				break;
			case 6://停车卡
				payType = 0;
				break;
			default:
				payType = 0;
				break;
			}
		}else{
			payType = 0;
		}
		Integer totalFee = (int) (parkRateRecordModel.getAmount().doubleValue() * 100);
		int executeMethod = community.getExecuteMethod();
		boolean notifyEcc = false;
		switch(executeMethod){
		case 0:
			log.info("在线支付月租车费用回调通知道闸系统时出现小区executeMethod为0(使用已过时的JNI SDK),"
					+ "通知失败(communityNumber="+communityNumber+";outTradeNo="+outTradeNo+")");
		break;
		case 1:
			Map<String, String> map_parm = new HashMap<String, String>();
			JSONObject bizData = new JSONObject();
			String barrierGateOrderNo = redisService.get("ParkRateController.parkRateService.barrierGateOrderNo:" + parkRateRecordModel.getLicensePlateNumber() + "-" + communityNumber);
			log.info("在线支付月租车费用回调时获取到的"+"ParkRateController.parkRateService.barrierGateOrderNo:" +
			parkRateRecordModel.getLicensePlateNumber() + "-" + communityNumber+"为:"+barrierGateOrderNo);
			
			bizData.put("licensePlateId", barrierGateOrderNo);
			bizData.put("licensePlateNumber", parkRateRecordModel.getLicensePlateNumber());
			bizData.put("licensePlateCommand", 4101);
			bizData.put("commandParameter", parkRateRecordModel.getStartTime());//lparam
			bizData.put("timeoutPeroid", parkRateRecordModel.getEndTime());//wpram
//			bizData.put("amount", totalFee);//  此处等唐经理确定参数名和协议之后再填写
			
			map_parm.put("interfaceType", "29"); //传递一个大于8的interfaceType即可
			map_parm.put("bizData", bizData.toJSONString());
			map_parm.put("communityNumber", community.getCommunity_number());
			String resJsonstr = null;
			try {
				resJsonstr = HttpUtil.doHttpPost(this.SOCKET_URL, map_parm);
				log.info("[月租续费通知道闸manualNotifyEcc--FRTK]道闸返回信息：" + resJsonstr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(StringUtils.isBlank(resJsonstr)){
				notifyEcc = false;
			}else{
				JSONObject resJson = JSON.parseObject(resJsonstr);
				notifyEcc = resJson.getIntValue("code") == 0;
			}
		break;
		case 2:
			Map<String, Object> mapParm = new HashMap<String, Object>();
			Map<String, String> mapLP = new HashMap<String, String>();
			mapParm.put("version", 1);
			mapParm.put("amount", totalFee);//支付金额，根据接口协议,单位为分
			mapParm.put("payType", payType);
			mapParm.put("license", parkRateRecordModel.getLicensePlateNumber());
			String mapIn = JSON.toJSONString(mapParm);

			mapLP.put("interface_lanpeng", "20");
			mapLP.put("bizData", mapIn);
			mapLP.put("communityNo", parkRateRecordModel.getCommunityNumber());
			String result = "";
			try {
				result = HttpUtil.doHttpPost(DWON_REQUEST_URL, mapLP);
				log.info("[月租续费通知道闸manualNotifyEcc--LP]道闸返回信息：" + result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(StringUtils.isBlank(result)){
				notifyEcc = false;
			}else{
				JSONObject resObj = JSON.parseObject(result);
				if (resObj.getIntValue("code") == 0) {
					notifyEcc = true;
				} else {
					notifyEcc = false;
				}
			}
		break;
		case 3:
			notifyEcc = factory.manualNotifyEcc(parkRateRecordModel, community);
		break;
		}
		return notifyEcc;
	}
	
	@Override
	public List<Map<String,Object>> findNearDeadlime(Calendar date,Integer distanceDay,String beforeOrEnd){
		return parkRateModelDao.findNearDeadlime(date, distanceDay, beforeOrEnd);
	}

	@Override
	public PrivilegeCar findPrivilegeCarByLicenseCommunity(String license, String community) {
		return parkRateModelDao.findPrivilegeCarByLicenseCommunity(license,community);
	}

	@Override
	public PageResult<Map<String,Object>> findByCondition(String userName, Integer pageSize, Integer pageNo) {
		return parkRateModelDao.findByCondition(userName, pageSize, pageNo);
	}

	@Override
	public void modifyParkRate(String userName, String license) {
		List<ParkRateModel> parkRateLicense = this.selectListByLicensePlate(license);
		List<Long> parks = new ArrayList<Long>();
		if (!CollectionUtils.isEmpty(parkRateLicense)) {
			for (int i = 0; i < parkRateLicense.size(); i++) {
				String user=parkRateLicense.get(i).getUserName();
				if ("nullUser".equals(user)||StringUtils.isBlank(user)) {
					parks.add(parkRateLicense.get(i).getId());
				}
			}
		}
		this.updateUserName(userName, MStringUntils.join(parks, ","));
	}

	@Override
	public void isInWhiteList(String userName, String licensePlateNumber) {
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(licensePlateNumber)) {
			return;
		}
		List<ParkRateModel> parkRateModels = parkRateModelDao.selectListByLicensePlate(licensePlateNumber);
		if (!CollectionUtils.isEmpty(parkRateModels)) {
			int commSize = parkRateModels.size();
			List<String> commNames = new ArrayList<String>();
			for (int i=0;i<commSize;i++) {
				ParkRateModel parkRateModel = parkRateModels.get(i);
				CommunityModel communityModel = communityModelService.findByNumber(parkRateModel.getCommunityNumber());
				if (communityModel != null) {
					commNames.add(communityModel.getCommunityName());
				}
			}
			pushMessage("恭喜，您的车牌已录入("+MStringUntils.join(commNames, ",")+")小区车闸系统，可以使用安心点进出车闸。", userName);
		}else{
			pushMessage("车牌号("+licensePlateNumber+")还没有录入小区停车系统，请联系物业录入", userName);
		}
	}
	
	private void pushMessage(final String content, final String userName) {
		ThreadPoolFactory.getInstance().addTask(new Runnable() {
			
			@Override
			public void run() {
				PushUntils pushUntils = PushUntils.getUserPushUntils();

				Member member = memberService.findByUsername(userName);
				if (member != null && member.getCurrentEquipment() != null && member.getCurrentEquipment() == 1 && "0".equals(member.getIsSendPush())) {
					try {
						PushResult res = pushUntils.pushNotificationMsgToAndroidUser(member.getUsername(), "安心点", content, null,null);
					} catch (APIConnectionException e) {
						e.printStackTrace();
					} catch (APIRequestException e) {
						e.printStackTrace();
					}
				}
				// 设备是ios
				if (member != null && member.getCurrentEquipment() != null && member.getCurrentEquipment() == 2 && "0".equals(member.getIsSendPush())) {
					try {
						PushResult res = pushUntils.pushNotificationMsgToIOSUser(member.getUsername(), content, null, null);
					} catch (APIConnectionException e) {
						e.printStackTrace();
					} catch (APIRequestException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public AppMessage walletAccountCallback(String outTradeNo, BigDecimal totalFee) {
		//do nothing
		return AppMessage.error("未注册的服务");
	}

	@Override
	public List<Map<String,Object>> findUserParkRate(String userName) {
		return parkRateModelDao.findUserParkRate(userName);
	}

	@Override
	public List<ParkRateModel> findParkRateByUserLicense(String userName) {
		return parkRateModelDao.findParkRateByUserLicense(userName);
	}
	
	@Override
	public List<ParkRateModel> findByLicenseNotOutofdate(String license,boolean monthlyExpired) {
		return parkRateModelDao.findByLicenseNotOutofdate(license,monthlyExpired);
	}
	
	@Override
	public List<Map<String,Object>> findCommunitiesByLicensePlate(String licensePlateNumber,Boolean isNotOutOfDate){
		return parkRateModelDao.findCommunitiesByLicensePlate(licensePlateNumber, isNotOutOfDate);
	}

	@Override
	public void updatLockStatus(Long id,Integer lockStatus, Integer userLocks) {
		parkRateModelDao.updatLockStatus(id,lockStatus, userLocks);
	}
	
}