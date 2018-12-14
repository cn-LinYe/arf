/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.arf.axd.entity.EccPackageConfig;
import com.arf.axd.entity.EccPackageConfig.PackageType;
import com.arf.axd.entity.EccPhysicalGiftRecord;
import com.arf.axd.entity.EccPhysicalGiftRecord.OpenEccType;
import com.arf.axd.service.IEccPackageConfigService;
import com.arf.axd.service.IEccPhysicalGiftRecordService;
import com.arf.carbright.entity.AxdVouchers;
import com.arf.carbright.entity.AxdVouchersRecord;
import com.arf.carbright.service.IAxdVouchersRecordService;
import com.arf.carbright.service.IAxdVouchersService;
import com.arf.core.AppMessage;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.VipRecordInformationModelDao;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.Member;
import com.arf.core.entity.VipRecordInformationModel;
import com.arf.core.oldws.PushUntils;
import com.arf.core.service.MemberService;
import com.arf.core.service.SmsService;
import com.arf.core.service.SysconfigService;
import com.arf.core.service.VipRecordInformationModelService;
import com.arf.ecc.entity.SettlementParam;
import com.arf.ecc.service.IOpenEccService;
import com.arf.goldcard.entity.GoldCardTransferRecord;
import com.arf.goldcard.entity.UserGoldCardAccount;
import com.arf.goldcard.entity.UserGoldCardRecord;
import com.arf.goldcard.service.IGoldCardTransferRecordService;
import com.arf.goldcard.service.IUserGoldCardAccountService;
import com.arf.goldcard.service.IUserGoldCardRecordService;
import com.arf.installment.smartlock.entity.LockInstallmentFundsRecord;
import com.arf.installment.smartlock.entity.LockInstallmentOrderRecord;
import com.arf.installment.smartlock.service.ILockInstallmentFundsRecordService;
import com.arf.installment.smartlock.service.ILockInstallmentOrderRecordService;
import com.arf.member.entity.RAccountTransferRecord;
import com.arf.member.service.IRAccountTransferRecordService;
import com.arf.payment.AliPaymentCallBackData;
import com.arf.payment.OrderNumPrefixConstraint;
import com.arf.payment.WeixinPaymentCallBackData;
import com.arf.payment.service.PaymentCallbackService;
import com.arf.payment.service.RegisterPaymentCallBackService;
import com.arf.platform.entity.SMemberAccount;
import com.arf.platform.service.ISMemberAccountService;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;


/**
 * Service - 开通vip订单纪录表
 * 
 * @author arf
 * @version 4.0
 */
@Service("vipRecordInformationModelServiceImpl")
public class VipRecordInformationModelServiceImpl extends BaseServiceImpl<VipRecordInformationModel, Long> implements VipRecordInformationModelService,PaymentCallbackService {
	
	/**
	 * 开通ECC返回值暂时缓存3分钟，用于做返回值返回给app
	 */
	public static final String CacheKey_OPENECC_MSG = "CacheKey_OPENECC_MSG:";

	public static final Integer CacheKey_OPENECC_TIME = 180;
	
	@Resource(name = "vipRecordInformationModelDaoImpl")
	private VipRecordInformationModelDao vipRecordInformationModelDao;
	
	@Resource(name = "eccPackageConfigServiceImpl")
	private IEccPackageConfigService eccPackageConfigServiceImpl;

	@Resource(name="sMemberAccountServiceImpl")
	private ISMemberAccountService sMemberAccountServiceImpl;
	
	@Resource(name="rAccountTransferRecordServiceImpl")
	private IRAccountTransferRecordService rAccountTransferRecordServiceImpl;
	
	@Resource(name="axdVouchersServiceImpl")
	private IAxdVouchersService axdVouchersServiceImpl;
	
	@Resource(name="axdVouchersRecordServiceImpl")
	private IAxdVouchersRecordService axdVouchersRecordServiceImpl;
	@Resource(name = "userGoldCardAccountServiceImpl")
	private IUserGoldCardAccountService userGoldCardAccountService;
	
	@Resource(name="memberServiceImpl")
	private MemberService memberServiceImpl;
	
	@Resource(name = "userGoldCardRecordService")
	private IUserGoldCardRecordService userGoldCardRecordService;
	
	@Resource(name="eccPhysicalGiftRecordServiceImpl")
	private IEccPhysicalGiftRecordService iEccPhysicalGiftRecordService;
	
	@Resource(name="openEccServiceImpl")
	private IOpenEccService openEccService;
	
	@Resource(name = "vipRecordInformationModelServiceImpl")
	private VipRecordInformationModelService vipRecordInformationModelService;
	
	@Resource(name = "smsServiceImpl")
	private SmsService smsService;
	@Resource(name = "redisService")
	private RedisService redisService;
	@Resource(name = "goldCardTransferRecordServiceImpl")
	private IGoldCardTransferRecordService goldCardTransferRecordService;
	
	@Resource(name = "lockInstallmentFundsRecordService")
	private ILockInstallmentFundsRecordService lockInstallmentFundsRecordService;
	
	@Resource(name = "lockInstallmentOrderRecordService")
	private ILockInstallmentOrderRecordService lockInstallmentOrderRecordService;
	
	@Resource(name = "sysconfigServiceImpl")
	private SysconfigService sysconfigService;
	
	public static final String OPEN_ECC_SMARTLOCK = "OPEN_ECC_SMARTLOCK";//开通ECC购买门锁对应的分期乐中的门锁分期类型编码
	
	public final PushUntils userPushUntils = PushUntils.getUserPushUntils();
	
	@Override
	protected BaseDao<VipRecordInformationModel, Long> getBaseDao() {
		return vipRecordInformationModelDao;
	}

	@Override
	public VipRecordInformationModel selectByouttradeno(String out_trade_no) {
		return vipRecordInformationModelDao.selectByouttradeno(out_trade_no);
	}

	@Override
	public List<VipRecordInformationModel> selectUnionID(String trade_status, int isRed, int pay_choice) {
		return vipRecordInformationModelDao.selectUnionID(trade_status, isRed, pay_choice);
	}

	@Override
	public List<VipRecordInformationModel> selectNotSendRedPkgByUnionID(String unionId) {
		return vipRecordInformationModelDao.selectNotSendRedPkgByUnionID(unionId);
	}

	@Override
	public List<VipRecordInformationModel> findNotSendRedPkg(int payChoice, Date createStart, Date createEnd) {
		return vipRecordInformationModelDao.findNotSendRedPkg(payChoice, createStart, createEnd);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){
			RegisterPaymentCallBackService.registerService(OrderNumPrefixConstraint.PREFIX_ECC_OPEN_VIP, getClass());
		}
	}
	
	/**
	 * 
	 * 开通ECC回调具体业务实现逻辑
	 * 
	 *  1.开通ECC申请订单那里现在是加了个账户余额支付的方式 所以需要判断下,如果是余额支付的就直接判断余额足不足,足就直接支付然后调用openEccPaymentCallback()
	 *	2.查询开通ECC赠送套餐表可用套餐
	 *	3.判断可用套餐集合
	 *		a.如果有返现金券的,生成一条钱包账户流转记录并添加其账户余额
	 *		b.如果有返代金券的,生成该用户一条代金券记录
	 *	4.xx_member表vip字段设置为1,user_level=2,vip_time设置为当前时间
	 *	5.推送
	 * @param outTradeNo 订单编号
	 * @param totalFee 费用,单位:元
	 * @return
	 */
	@Override
	public boolean openEccPaymentCallback(Integer payType,String outTradeNo,BigDecimal totalFee){
		//查询开通ECC赠送套餐表可用套餐
		//1.根据订单号找支付记录表vip_record_information（支付纪录表）
		VipRecordInformationModel vipRecordInfor = this.selectByouttradeno(outTradeNo);
		if(vipRecordInfor == null){
			return false;
		}
		//新增判断订单是否已支付，可能会重复回调，需判断
		if("9000".equals(vipRecordInfor.getTrade_status())){
			return true;
		}
		
		//获得开通ecc选择的套餐集合并将套餐赠送给用户
		EccPackageConfig eccPakageConfig = eccPackageConfigServiceImpl.find(vipRecordInfor.getEccPackageConfigId());
		if(eccPakageConfig.getPackageType() == EccPackageConfig.PackageType.GoldenCard.ordinal()){
			openEccService.openEcc(vipRecordInfor.getUser_name(), SettlementParam.AssignType.package_goldcard, outTradeNo, null,null);
		}else{
			openEccService.openEcc(vipRecordInfor.getUser_name(), SettlementParam.AssignType.package_ecc, outTradeNo, null,null);
		}
		//xx_member表vip字段设置为1,user_level=2,vip_time设置为当前时间
		Member member = memberServiceImpl.findByUsername(vipRecordInfor.getUser_name());
		
		/*member.setVip(Member.Vip.vip.ordinal());
		member.setUserlevel(2);
		member.setVipTime(new Date());
		memberServiceImpl.update(member);*/
				
		if(eccPakageConfig.getCashTotalAmount().compareTo(BigDecimal.ZERO)>=1){
			//有赠送现金券,生成一条钱包账户流转记录并添加其账户余额
			SMemberAccount account = sMemberAccountServiceImpl.findByUserNameUserType(vipRecordInfor.getUser_name(),(byte)SMemberAccount.Type.member.ordinal());
			account.setBasicAccount(account.getBasicAccount().add(eccPakageConfig.getCashTotalAmount()));
			sMemberAccountServiceImpl.update(account);
			
			RAccountTransferRecord accountRecord = new RAccountTransferRecord();
			accountRecord.setAccountId(String.valueOf(account.getId()));
			accountRecord.setAccountNumber(account.getAccountNumber());
			accountRecord.setIdentify(RAccountTransferRecord.genIdentify(account.getAccountNumber()));
			accountRecord.setOrderNo(outTradeNo);
			accountRecord.setSerialNumber(RAccountTransferRecord.genSerialNumber(account.getAccountNumber()));
			accountRecord.setUserName(vipRecordInfor.getUser_name());
			accountRecord.setAmount(eccPakageConfig.getCashTotalAmount());
			accountRecord.setComment("开通ECC赠送现金券");
			accountRecord.setAccountBalance(account.getBasicAccount());
			accountRecord.setAccountType((byte)RAccountTransferRecord.AccountType.basicAccount.ordinal());//基本账户
			accountRecord.setUserType((byte)RAccountTransferRecord.UserType.Member.ordinal());//会员
			accountRecord.setOperateTime(new Date());
			accountRecord.setOperateType((byte)RAccountTransferRecord.OperateType.inter.ordinal());
			accountRecord.setStatus((byte)RAccountTransferRecord.Status.finished.ordinal());
			accountRecord.setType(RAccountTransferRecord.Type.chargeAxdGift);
			accountRecord.setIsConfirmed((byte)RAccountTransferRecord.IsConfirmed.ok.ordinal());
			accountRecord.setBranchId(vipRecordInfor.getBranchId());
			accountRecord.setPropertyNumber(vipRecordInfor.getPropertyNumber());
			accountRecord.setCommunityNumber(vipRecordInfor.getCommunityNumber());
			rAccountTransferRecordServiceImpl.save(accountRecord);
		}
		String vipMsg="";
		
		//检查用户是否有开启接受信息
		StringBuilder msg = new StringBuilder("");
		redisService.del(CacheKey_OPENECC_MSG+member.getUsername());
		//检查是否有代金券信息（含停车卡）
		if(StringUtils.isNotBlank(eccPakageConfig.getVouchersNum())){
			//有返代金券,生成该用户一条代金券记录
			AxdVouchers voucher = axdVouchersServiceImpl.findByVouchersNum(eccPakageConfig.getVouchersNum());
			if(voucher == null){
				//无法找到代金券记录，数据异常
				return false;
			}
			SimpleDateFormat sim = new SimpleDateFormat("yyyy/MM/dd");
			//安心停车卡开通ecc 
			if (eccPakageConfig.getPackageType()!=null&&eccPakageConfig.getPackageType()==(byte)PackageType.GoldenCard.ordinal()) {
				AxdVouchers axdVouchers=axdVouchersServiceImpl.findByVouchersNum(eccPakageConfig.getVouchersNum());
				Date endDate = null ;
				if (axdVouchers!=null) {
					UserGoldCardRecord record = userGoldCardRecordService.findByCardNo(outTradeNo);
					if(record == null){
						return false;
					}
					if(record.getPayStatus() == UserGoldCardRecord.PayStatus.Paid){
						return true;
					}
					
					Integer month =axdVouchers.getEffectiveDate()/43200;
					if (month==0) {
						String endTime="2099/12/30";
						 try {
							endDate = sim.parse(endTime);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}else{
						Calendar calendar = Calendar.getInstance();
						calendar.add(Calendar.MONTH, month);
						calendar.set(Calendar.HOUR_OF_DAY, 23);
						calendar.set(Calendar.MINUTE, 59);
						calendar.set(Calendar.SECOND, 59);
						//有效结束时间
						 endDate = calendar.getTime();
					}
				
					UserGoldCardAccount goldCardaccount = userGoldCardAccountService.findByUserNameEntity(member.getUsername());
					if(goldCardaccount == null){ //不存在停车卡账户,本次购买为第一次购买停车卡
						goldCardaccount = new UserGoldCardAccount();
						goldCardaccount.setBalance(eccPakageConfig.getVouchersTotalAmount());
						goldCardaccount.setCardNo(outTradeNo);//停车卡账户的卡号为用户第一次购买停车卡的订单记录单号
						goldCardaccount.setEndDate(endDate);
						goldCardaccount.setUserName(member.getUsername());
						userGoldCardAccountService.save(goldCardaccount);
					}else{
						BigDecimal balance = goldCardaccount.getBalance();
						if(balance == null || (goldCardaccount.getEndDate() != null && goldCardaccount.getEndDate().before(new Date()))){//已经过期的停车卡账户 余额为0
							balance = BigDecimal.ZERO;
						}
						goldCardaccount.setBalance(balance.add(eccPakageConfig.getVouchersTotalAmount()));
						goldCardaccount.setEndDate(endDate);//以最新的订单过期时间为准
						userGoldCardAccountService.update(goldCardaccount);
					}
					
					record.setBuyDate(new Date());
					record.setPayStatus(UserGoldCardRecord.PayStatus.Paid);
					record.setUserName(member.getUsername());
					record.setEndDate(endDate);
					record.setFaceValue(axdVouchers.getVouchersMoney());
					record.setPrice(totalFee);	
					record.setStatus(UserGoldCardRecord.Status.Normal);
					if(payType == 2){
						record.setPayType(UserGoldCardRecord.PayType.Paid_WeiXin);
					}else if(payType == 1){
						record.setPayType(UserGoldCardRecord.PayType.Paid_Alipay);
					}else if(payType == 3){
						record.setPayType(UserGoldCardRecord.PayType.Paid_Account);
					}
					userGoldCardRecordService.update(record);
					
					//生成一条停车卡流转记录
					GoldCardTransferRecord goldCardTransferRecord = new GoldCardTransferRecord();
					goldCardTransferRecord.setAmount(record.getFaceValue());
					goldCardTransferRecord.setBalance(goldCardaccount.getBalance());
					goldCardTransferRecord.setOrderNo(record.getCardNo());
					goldCardTransferRecord.setRemark("购买"+record.getFaceValue()+"元停车卡套餐");
					goldCardTransferRecord.setStatus(0);
					goldCardTransferRecord.setType(GoldCardTransferRecord.Type.Buy_ParkingCard_Get.ordinal());
					goldCardTransferRecord.setUserName(member.getUsername());
					goldCardTransferRecordService.save(goldCardTransferRecord);
					
					}
				if (member!=null&&member.getVip()==0) {
					vipMsg="并成功开通ECC,";
				}
				if(eccPakageConfig.getVouchersNum()!=null){
					msg.append("您已获得价值"+eccPakageConfig.getVouchersTotalAmount()+"元安心停车卡套餐,"+vipMsg+"安心停车卡有效期至"+sim.format(endDate));
					 redisService.set(CacheKey_OPENECC_MSG+member.getUsername(), msg.toString());
					 redisService.setExpiration(CacheKey_OPENECC_MSG+member.getUsername(), CacheKey_OPENECC_TIME);
				}
				
			}else{
				//赠送代金券（可能有多张）
				int num = eccPakageConfig.getVouchersCount();
				for(int i=0; i<num; i++){
					AxdVouchersRecord vr = new AxdVouchersRecord();
					vr.setVouchersType(voucher.getVouchersType());
					vr.setVouchersMoney(voucher.getVouchersMoney());
					vr.setVouchersSalesMoney(BigDecimal.ZERO);
					vr.setUsedStatus(AxdVouchersRecord.UsedStatus.Available.ordinal());
					vr.setEffectiveStartDate(new Date());
					if(voucher.getEffectiveDate() == 0){
						//永远不失效
						vr.setEffectiveEndDate((new GregorianCalendar(9999,11,31,0,0,0)).getTime());
					}else{
						Calendar cal = Calendar.getInstance();
						cal.add(Calendar.MINUTE, voucher.getEffectiveDate()==null?0:voucher.getEffectiveDate());
						vr.setEffectiveEndDate(cal.getTime());
					}
					vr.setVouchersNum(voucher.getVouchersNum());
					vr.setVouchersName(voucher.getVouchersName());
					vr.setUseRules(voucher.getUseRules());
					vr.setScope(voucher.getScope());
					vr.setUseScope(voucher.getUseScope());
					vr.setAxdUsed(voucher.getAxdUsed());
					vr.setWechatUsed(voucher.getWechatUsed());
					vr.setUserName(vipRecordInfor.getUser_name());
					vr.setFeePayType(AxdVouchersRecord.FeePayType.EccGive.ordinal());
					vr.setOutTradeNo(vipRecordInfor.getOut_trade_no());
					vr.setFeePayStatus(AxdVouchersRecord.FeePayStatus.PaySuccess.ordinal());
					axdVouchersRecordServiceImpl.save(vr);
				}
				if(eccPakageConfig.getCashTotalAmount().compareTo(BigDecimal.ZERO)>=1){
					msg.append("您已获得现金 "+eccPakageConfig.getCashTotalAmount()+" 元");
				}
				if(eccPakageConfig.getVouchersNum()!=null){
					msg.append("您已获得代金券 "+eccPakageConfig.getVouchersCount()+" 张");
				}
			}
		}
		//这个字段填充的时候不能为空（packageType不能为空，为空数据有问题）
		if (eccPakageConfig.getPackageType()!=null) {
			if (member!=null&&member.getVip()==0) {
				vipMsg="并成功开通ECC,";
			}
			//如果是赠送实物则需要插入
			if (eccPakageConfig.getPackageType()==PackageType.Gift.ordinal()) {
				EccPhysicalGiftRecord eccPhysicalGiftRecord =new EccPhysicalGiftRecord();
				eccPhysicalGiftRecord.setPhysicalName(eccPakageConfig.getGiftName());
				eccPhysicalGiftRecord.setUserName(vipRecordInfor.getUser_name());
				eccPhysicalGiftRecord.setPayAmount(totalFee);
				eccPhysicalGiftRecord.setOpenEccType(OpenEccType.EccGive.ordinal());
				eccPhysicalGiftRecord.setStatus(EccPhysicalGiftRecord.Status.NotGive.ordinal());
				eccPhysicalGiftRecord.setOrderNo(outTradeNo);
				eccPhysicalGiftRecord.setEccPackageId(eccPakageConfig.getId());
				iEccPhysicalGiftRecordService.save(eccPhysicalGiftRecord);
				msg.append("感谢您购买智能门锁，"+vipMsg+"我们的工作人员会尽快与您电话联系。");
				 redisService.set(CacheKey_OPENECC_MSG+member.getUsername(), msg.toString());
				 redisService.setExpiration(CacheKey_OPENECC_MSG+member.getUsername(), CacheKey_OPENECC_TIME);
				 
				//只有选择赠送实物套餐才保存分期乐购买记录及生成门锁分期购款项表
					if(eccPakageConfig.getPackageType() == EccPackageConfig.PackageType.Gift.ordinal()){
						String typeNum = sysconfigService.getParentValue(CommunityModel.ECCCONFIG).get(OPEN_ECC_SMARTLOCK);
						if(typeNum==null){
							typeNum="";
						}
						
						LockInstallmentOrderRecord lockInstallmentOrderRecord = new LockInstallmentOrderRecord();
						lockInstallmentOrderRecord.setTypeNum(typeNum);
						lockInstallmentOrderRecord.setInstallmentPeriods(1);
						lockInstallmentOrderRecord.setFirstFunds(totalFee);
						lockInstallmentOrderRecord.setUserPhone(vipRecordInfor.getUser_name());
						lockInstallmentOrderRecord.setUserName(vipRecordInfor.getUser_name());
						lockInstallmentOrderRecord.setRealName("");
						lockInstallmentOrderRecord.setCityNo("");
						lockInstallmentOrderRecord.setFullCityName("");
						String address = member.getAddress();
						if(address==null){
							address="";
						}
						lockInstallmentOrderRecord.setAddress(address);
						lockInstallmentOrderRecord.setStatus(LockInstallmentOrderRecord.Status.BUY_SUCCESS);
						lockInstallmentOrderRecord.setFundsClear(LockInstallmentOrderRecord.FundsClear.PAYBACK_CLEAR);
						lockInstallmentOrderRecord.setOrderNo(outTradeNo);
						lockInstallmentOrderRecord.setBuyDate(new Date());
						switch (payType) {
						case 1:
							payType=1;
							break;
						case 2:
							payType=0;
							break;
						case 3:
							payType=2;
							break;

						default:
							break;
						}
						LockInstallmentOrderRecord.PayType pay_type = LockInstallmentOrderRecord.PayType.values()[payType];
						lockInstallmentOrderRecord.setPayType(pay_type);
						lockInstallmentOrderRecord.setApplyRefundDate(null);
						lockInstallmentOrderRecord.setRefundFinishDate(null);
						lockInstallmentOrderRecord.setBuyChannel(LockInstallmentOrderRecord.BuyChannel.OPEN_ECC);
						lockInstallmentOrderRecordService.save(lockInstallmentOrderRecord);
						
						LockInstallmentFundsRecord lockInstallmentFundsRecord = new LockInstallmentFundsRecord();
						lockInstallmentFundsRecord.setTypeNum(typeNum);
						lockInstallmentFundsRecord.setFundsType(LockInstallmentFundsRecord.FundsType.FIRST_FUNDS);
						lockInstallmentFundsRecord.setAmount(totalFee);
						lockInstallmentFundsRecord.setPaidDate(new Date());
						lockInstallmentFundsRecord.setPayDeadline(null);
						lockInstallmentFundsRecord.setPayStatus(LockInstallmentFundsRecord.PayStatus.PAID);
						lockInstallmentFundsRecord.setPayType(pay_type);
						lockInstallmentFundsRecord.setDeadlineStatus(LockInstallmentFundsRecord.DeadlineStatus.NORMAL);
						lockInstallmentFundsRecord.setCurrentPeriod(1);
						lockInstallmentFundsRecord.setTotalPeriods(1);
						lockInstallmentFundsRecord.setFundsNo(OrderNumPrefixConstraint.getInstance().genOrderNo(OrderNumPrefixConstraint.PREFIX_SMART_LOCK_INSTALLMENT_FIRSTFUNDS, 5));
						lockInstallmentFundsRecord.setOrderNo(outTradeNo);
						lockInstallmentFundsRecordService.save(lockInstallmentFundsRecord);
						
					}
			}
		}
		//更新订单状态
		VipRecordInformationModel vipRecordInformationModel=vipRecordInformationModelService.selectByouttradeno(outTradeNo);
		if (vipRecordInformationModel!=null) {
			vipRecordInformationModel.setTrade_status("9000");
			vipRecordInformationModelService.update(vipRecordInformationModel);
		}
		//推送消息
		final String msgInfor="尊敬的安心点会员,".concat(msg.toString());
		if("0".equals(member.getIsSendPush())){
			// 推送给用户信息
			final String userName = vipRecordInfor.getUser_name();
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						userPushUntils.pushNotificationMsgToUser(userName, 
								msgInfor,
								"ECC已开通", 
								new HashMap(), 
								null);
					} catch (APIConnectionException | APIRequestException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		
		//发送短信提示
		if("0".equals(member.getIsSendMessage())){
			smsService.send(vipRecordInfor.getUser_name(), msgInfor);
		}
		
		return true;
	}

	@Override
	public boolean weixiCallback(WeixinPaymentCallBackData callbackData) {
		return openEccPaymentCallback(2,callbackData.getOut_trade_no(),
				new BigDecimal(callbackData.getTotal_fee()).divide(BigDecimal.TEN.multiply(BigDecimal.TEN)));
	}

	@Override
	public boolean alipayCallback(AliPaymentCallBackData callbackData) {
		return openEccPaymentCallback(1,callbackData.getOut_trade_no(),new BigDecimal(String.valueOf(callbackData.getTotal_fee())));
	}

	@Override
	public AppMessage walletAccountCallback(String outTradeNo, BigDecimal totalFee) {
		if(this.openEccPaymentCallback(3,outTradeNo,totalFee)){
			return AppMessage.success();
		}else{
			return AppMessage.error();
		}
	}

	@Override
	public List<VipRecordInformationModel> checkUserBuyEccPackage(String trade_status, String userName,
			Long eccPackageConfigId) {
		return vipRecordInformationModelDao.checkUserBuyEccPackage(trade_status, userName, eccPackageConfigId);
	}
}