package com.arf.carbright.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.carbright.MerchantTransctionRecordSource;
import com.arf.carbright.dao.PBusinessDao;
import com.arf.carbright.dao.PUserRangeDao;
import com.arf.carbright.dao.PackageTypeDao;
import com.arf.carbright.dao.RPackageRecordDao;
import com.arf.carbright.dto.SaleroomOfPeriodDto;
import com.arf.carbright.entity.AxdVouchersRecord;
import com.arf.carbright.entity.AxdVouchersRecord.UsedStatus;
import com.arf.carbright.entity.MerchantTransactionRecord;
import com.arf.carbright.entity.PBusiness;
import com.arf.carbright.entity.PUserRange;
import com.arf.carbright.entity.PackageType;
import com.arf.carbright.entity.PackageType.IsEnabled;
import com.arf.carbright.entity.RPackageRecord;
import com.arf.carbright.entity.RPackageRecord.FeePayStatus;
import com.arf.carbright.entity.RPackageRecord.FeePayType;
import com.arf.carbright.entity.RPackageRecord.ReturnPointVoucher;
import com.arf.carbright.entity.RPackageRecord.Status;
import com.arf.carbright.service.IAxdVouchersRecordService;
import com.arf.carbright.service.MerchantTransactionRecordService;
import com.arf.carbright.service.PUserRangService;
import com.arf.carbright.service.PackageTypeService;
import com.arf.carbright.service.PbusinessService;
import com.arf.carbright.service.RPackageRecordService;
import com.arf.core.AppMessage;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.Member;
import com.arf.core.oldws.PushMessage;
import com.arf.core.oldws.PushUntils;
import com.arf.core.service.CommunityModelService;
import com.arf.core.service.MemberService;
import com.arf.core.service.SysconfigService;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.core.util.BeanUtils;
import com.arf.core.util.MStringUntils;
import com.arf.member.entity.RAccountTransferRecord;
import com.arf.member.service.IPointTransferRecordService;
import com.arf.member.service.IRAccountTransferRecordService;
import com.arf.payment.AliPaymentCallBackData;
import com.arf.payment.OrderNumPrefixConstraint;
import com.arf.payment.WeixinPaymentCallBackData;
import com.arf.payment.service.PaymentCallbackService;
import com.arf.payment.service.RegisterPaymentCallBackService;
import com.arf.platform.entity.SMemberAccount;
import com.arf.platform.service.ISMemberAccountService;
import com.google.common.base.Function;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;

@Service("rPackageRecordService")
public class RPackageRecordServiceImpl extends BaseServiceImpl<RPackageRecord, Long>
		implements RPackageRecordService, PaymentCallbackService {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Resource(name = "packageRecordDao")
	private RPackageRecordDao packageRecordDao;

	@Resource(name = "packageTypeDao")
	private PackageTypeDao packageTypeDao;

	@Resource(name = "pUserRangeDao")
	private PUserRangeDao pUserRangeDao;
	
	@Resource(name = "puserRangService")
	private PUserRangService pUserRangeService;

	@Resource(name = "pBusinessDao")
	private PBusinessDao businessDao;

	@Resource(name = "pBusinessService")
	private PbusinessService businessService;

	@Resource(name = "rPackageRecordService")
	private RPackageRecordService packageRecordService;

	@Resource(name = "sMemberAccountServiceImpl")
	private ISMemberAccountService memberAccountService;

	@Resource(name = "rAccountTransferRecordServiceImpl")
	private IRAccountTransferRecordService accountTransferRecordService;

	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "communityModelServiceImpl")
	private CommunityModelService communityModelService;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "axdVouchersRecordServiceImpl")
	private IAxdVouchersRecordService axdVouchersRecordService;
	
	@Resource( name = "pointTransferRecordServiceImpl")
	private IPointTransferRecordService pointTransferRecordServiceImpl;	
	
	@Resource(name="sMemberAccountServiceImpl")
	private ISMemberAccountService sMemberAccountServiceImpl;
	
	@Resource(name ="axdVouchersRecordServiceImpl")
	private IAxdVouchersRecordService axdVouchersRecordServiceImpl;
	
	@Resource(name = "sysconfigServiceImpl")
	private SysconfigService sysconfigServiceImpl;
	
	@Resource(name = "packageTypeService")
	private PackageTypeService packageTypeService;
	
	@Resource(name = "merchantTransactionRecordService")
	private MerchantTransactionRecordService merchantTransactionRecordService;
	
	public final PushUntils userPushUntils = PushUntils.getUserPushUntils();
	
	public static final String DAY_OF_EXPIRATION = "DAY_OF_EXPIRATION";//套餐过期提醒设置提前提醒时间
	
	@Override
	protected BaseDao<RPackageRecord, Long> getBaseDao() {
		return packageRecordDao;
	}
	

	@Override
	public AppMessage generatePkgOrder(String userName, String license, RPackageRecord.FeePayType feePayType,
			List<String> packageTypeNum,List<String> vouchersNums,Integer points) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		AppMessage appMessage = new AppMessage();
		String odrerNumber = OrderNumPrefixConstraint.getInstance()
				.genOrderNo(OrderNumPrefixConstraint.PREFIX_CARBRIGHTER_PKG_BUY, 10);
		List<PackageType> packageTypes = new ArrayList<PackageType>();
		BigDecimal totlaFees = new BigDecimal(0);
		totlaFees=totlaFees.setScale(2);		
		for (int i = 0; i < packageTypeNum.size(); i++) {
			PackageType packageType = packageTypeDao.findbyPackageTypeNum(packageTypeNum.get(i),
					IsEnabled.enablem.ordinal());
			if (packageType == null) {
				list.add(packageTypeNum.get(i));
				appMessage.setCode(101);
				appMessage.setMsg("此套餐已经不能使用了");
				map.put("packageTypeNum", packageTypeNum.get(i));
				appMessage.setExtrDatas(map);
				return appMessage;
			}
			
			// 查看限制购买次数
			int limitTimes = packageType.getLimitTimes();			
			if (limitTimes > 0) {
				int boughtCount = packageRecordDao.findCountByPkgNum(userName, packageType.getPackageTypeNum());
				if (boughtCount >= limitTimes) {
					return AppMessage.error("此套餐只允许购买" + limitTimes + "次");
				}
			}
			Integer packageCirculation=packageType.getPackageCirculation();//获取限量发行次数
			if(packageCirculation!=null&&packageCirculation>0){//判断发行数量
				int boughtCount = packageRecordDao.findCountByPkgNum(null, packageType.getPackageTypeNum());
				if (boughtCount >= packageCirculation) {
					return AppMessage.error("此套餐已售罄，下次早点下手！");
				}
				/*if (boughtCount+1>=packageCirculation) {
					packageType.setSalesVolume(packageCirculation);
					packageTypeService.update(packageType);
				}*/
			}

			if (packageType.getPackagePrice() != null) {
				totlaFees = totlaFees.add(packageType.getPackagePrice().subtract(packageType.getGiftAmount()));
			}
			packageTypes.add(packageType);

		}
		String vouchersNum="";String segment="";
		BigDecimal vouchersFees=new BigDecimal(0);
		for (int i = 0; i < vouchersNums.size(); i++) {
			AxdVouchersRecord vouchersRecord=axdVouchersRecordService.findUsedDishVouchersByNum(userName,vouchersNums.get(i), UsedStatus.Available);
			if(vouchersRecord==null){
				appMessage.setCode(101);
				appMessage.setMsg("此代金券不存在,请换张再试");	
				return appMessage;
			}
			vouchersNum=vouchersNum.concat(vouchersNums.get(i)).concat(segment);
			segment=",";
			vouchersFees=vouchersFees.add(vouchersRecord.getVouchersMoney());
		}
		totlaFees=totlaFees.subtract(vouchersFees);		
		BigDecimal basicAccount = null;
		SMemberAccount memberAccount = null;
		memberAccount = memberAccountService.findByUserNameUserType(userName, (byte) SMemberAccount.Type.member.ordinal());
		points=(points!=null)?points:0;//积分
		if(memberAccount != null && points > 0){
			Integer point= memberAccount.getPoint();//获取用户积分
			point=(point!=null)?point:0;
			if(points>point){//使用积分小于用户剩余积分
				return AppMessage.error("积分不足,请重新提交订单");
			}
			String pointsDeduction="100";// XX积分抵扣1元		
			Map<String, String> pointsConfigMap = sysconfigServiceImpl.getParentValue("POINTS");//读取缓存数据	
			if(pointsConfigMap!=null&&pointsConfigMap.size()>0){
				pointsDeduction=pointsConfigMap.get("POINTSDEDUCTION");
			}
			int pointsDeductionint= MStringUntils.isNumber(pointsDeduction)?Integer.valueOf(pointsDeduction):100;
			pointsDeductionint=(pointsDeductionint==0)?1:pointsDeductionint;
			BigDecimal pointsDeductionDouble=new BigDecimal(pointsDeductionint);
			BigDecimal pointsDouble=new BigDecimal(points);
			//int Amount=points/pointsDeductionint;
			//积分抵扣金额
			BigDecimal pointsMoney=pointsDouble.divide(pointsDeductionDouble);
			totlaFees=totlaFees.subtract(pointsMoney);	
		}
		if (feePayType == RPackageRecord.FeePayType.Member_Account) {// 查看会员账户余额够不够
			if (memberAccount == null) {
				return AppMessage.error("在线账户不存在");
			}
			
			if(memberAccount.getStatus() == SMemberAccount.Status.unusable.ordinal()){
				return AppMessage.error("您的钱包账户已被禁用,请联系客服");
			}
			
			if (memberAccount.getBasicAccount() == null) {
				basicAccount = new BigDecimal(0);
			} else {
				basicAccount = memberAccount.getBasicAccount();
			}
			if (totlaFees.compareTo(basicAccount) == 1) {
				return AppMessage.error("在线账户余额不足,请充值后再购买");
			}			
		}		
		if (CollectionUtils.isNotEmpty(packageTypes)) {			
			generatePkgOrder(points,vouchersNum,userName, license, feePayType, packageTypes, memberAccount, odrerNumber, totlaFees, basicAccount,null);
			RefundDeductedPointVouchers pointVouchers=new RefundDeductedPointVouchers();			
			pointVouchers.refundDeductedPoint(userName, (feePayType.ordinal()== FeePayType.Member_Account.ordinal())?1:2, points, memberService, sMemberAccountServiceImpl, communityModelService, pointTransferRecordServiceImpl);
			pointVouchers.refundDeductedVouchers(userName,vouchersNum, 2, axdVouchersRecordService);
			appMessage.setCode(102);
			appMessage.setMsg("成功生成订单");
			map.put("outTradeNo", odrerNumber);
			appMessage.setExtrDatas(map);
			return appMessage;
		} else {
			return AppMessage.error("所选套餐类型不存在");
		}

	}
	@Override
	public void generatePkgOrder(String userName,String license,RPackageRecord.FeePayType feePayType,List<PackageType> packageTypes,SMemberAccount memberAccount,String odrerNumber,BigDecimal totlaFees,BigDecimal basicAccount,BigDecimal paymentPrice) throws Exception{
		generatePkgOrder(0, null, userName, license, feePayType, packageTypes, memberAccount, odrerNumber, totlaFees, basicAccount, paymentPrice);
	}
	/***
	 * 调用此方法前提
	 * 1、如果是电子钱包支付必须判断电子钱包余额大于待支付金额
	 * */
	public void generatePkgOrder(int points,String vouchersNum,String userName,String license,RPackageRecord.FeePayType feePayType,List<PackageType> packageTypes,SMemberAccount memberAccount,String odrerNumber,BigDecimal totlaFees,BigDecimal basicAccount,BigDecimal paymentPrice) throws Exception{		
		odrerNumber =(odrerNumber==null)?OrderNumPrefixConstraint.getInstance().genOrderNo(OrderNumPrefixConstraint.PREFIX_CARBRIGHTER_PKG_BUY, 10):odrerNumber;		
		Date now = new Date();//当前时间
		long currentTime=System.currentTimeMillis()/1000L;//当前时间戳
		BigDecimal pointsMoney=new BigDecimal(0);
		BigDecimal vouchersMoney=new BigDecimal(0);
		BigDecimal priceCount=new BigDecimal(0);
		Integer businessId =null;
		for (PackageType packageType : packageTypes) {
			RPackageRecord packageRecord = new RPackageRecord();
			BigDecimal packagePrice =packageType.getPackagePrice();	//套餐价格
			if(packagePrice!=null){
				priceCount = priceCount.add(packagePrice);
			}
			BigDecimal giftAmount=packageType.getGiftAmount();
			if (feePayType == RPackageRecord.FeePayType.Member_Account) {//电子钱包支付需要保证余额足即可
				packageRecord.setStatus(Status.available.ordinal());
				packageRecord.setFeePayStatus(FeePayStatus.pay_success.ordinal());
			} else if (feePayType == RPackageRecord.FeePayType.Alipay
					|| feePayType == RPackageRecord.FeePayType.Weixin_Pay) {//微信或者支付宝付款需要回调确认才能保证订单支付成功
				packageRecord.setStatus(Status.not_available.ordinal());
				packageRecord.setFeePayStatus(FeePayStatus.not_pay.ordinal());
			}else if(feePayType==RPackageRecord.FeePayType.Property_Donation){//物业转赠
				packageRecord.setStatus(Status.available.ordinal());
				packageRecord.setFeePayStatus(FeePayStatus.pay_success.ordinal()); 
			    paymentPrice=(paymentPrice!=null)?paymentPrice:new BigDecimal(0);
			    giftAmount=(giftAmount!=null)?giftAmount:new BigDecimal(0);
			    packagePrice=packagePrice!=null?packagePrice:new BigDecimal(0);
			    giftAmount=packagePrice.subtract(paymentPrice);
			}else if(feePayType==RPackageRecord.FeePayType.Property_Donation){//他人转赠
				packageRecord.setStatus(Status.available.ordinal());
				packageRecord.setFeePayStatus(FeePayStatus.pay_success.ordinal());
			}
			Integer effectiveTime=packageType.getEffectiveTime();//获取到套餐中用户使用时间
			Date startTime=now,endTime=now;//起始时间和结束时间
			long starLongtTime=currentTime,endLongTime=currentTime;//起始时间和结束时间long类型
			if(effectiveTime!=null&&effectiveTime>0){
				endTime=getDate(effectiveTime);
				endLongTime=getDateLong(effectiveTime);			
			}else{
				endTime=getDate(RPackageRecord.default_Times);
				endLongTime=RPackageRecord.default_Timestamp;	
			}
			if(points>0||StringUtils.isNotBlank(vouchersNum)){//本订单存在积分兑换或者代金券
				String pointsDeduction="100";// XX积分抵扣1元		
				Map<String, String> pointsConfigMap = sysconfigServiceImpl.getParentValue("POINTS");//读取缓存数据	
				if(pointsConfigMap!=null&&pointsConfigMap.size()>0){
					pointsDeduction=pointsConfigMap.get("POINTSDEDUCTION");
				}
				int pointsDeductionint= MStringUntils.isNumber(pointsDeduction)?Integer.valueOf(pointsDeduction):100;
				pointsDeductionint=(pointsDeductionint==0)?1:pointsDeductionint;
				BigDecimal pointsDeductionDouble=new BigDecimal(pointsDeductionint);
				BigDecimal pointsDouble=new BigDecimal(points);
				//int Amount=points/pointsDeductionint;
				//积分抵扣金额
				pointsMoney=pointsDouble.divide(pointsDeductionDouble);
					
				if(vouchersNum!=null){
					Map<String, String> washConfigMap = sysconfigServiceImpl.getParentValue("WASHVOUCHERS");//读取缓存数据
					String washMaxAmount="0";//洗车券最多抵扣XX金额
					if(washConfigMap!=null&&washConfigMap.size()>0){
						 washMaxAmount=washConfigMap.get("WASHMAXAMOUNT");//洗车券最多抵扣XX金额
					}
					int maxAmount =MStringUntils.isNumber(washMaxAmount)?Integer.valueOf(washMaxAmount):Integer.MAX_VALUE;
					BigDecimal voucherMoney=new BigDecimal(maxAmount);
					String[] vouchersNums=vouchersNum.split(",");
					String vouchersNames="";String segment="";String vouchersNumber="";				
					for (String vouchers : vouchersNums) {
						AxdVouchersRecord axdVouchersRecord= axdVouchersRecordServiceImpl.findUsedDishVouchersByNum(userName,vouchers, UsedStatus.Available);
						if(axdVouchersRecord!=null){
							vouchersNumber=vouchersNumber.concat(axdVouchersRecord.getVouchersNum()).concat(segment);
							vouchersNames=vouchersNames.concat(axdVouchersRecord.getVouchersName()).concat(segment);
							segment=",";
							BigDecimal wachVouchersMoney =axdVouchersRecord.getVouchersMoney();
							if(voucherMoney.compareTo(wachVouchersMoney)<=0){
								wachVouchersMoney=voucherMoney;
							}
							if(wachVouchersMoney!=null){
								vouchersMoney=vouchersMoney.add(wachVouchersMoney);
							}
						}
					}
					packageRecord.setVouchersNums(vouchersNumber);
					packageRecord.setVouchersNames(vouchersNames);
					
				}
				BigDecimal preferentialMoney=vouchersMoney.add(pointsMoney).add(giftAmount);
				if((preferentialMoney).compareTo(packagePrice) >=0){
					giftAmount=packagePrice;
				}else{
					giftAmount=preferentialMoney;
				}
			}
			if(points>0||StringUtils.isNotBlank(vouchersNum)){
				if(feePayType == RPackageRecord.FeePayType.Alipay||feePayType == RPackageRecord.FeePayType.Weixin_Pay){
					packageRecord.setReturnPoint(ReturnPointVoucher.Possible_Return.ordinal());
				}else{
					packageRecord.setReturnPoint(ReturnPointVoucher.Not_Return.ordinal());
				}
			}else{
				packageRecord.setReturnPoint(ReturnPointVoucher.Not_Return.ordinal());
			}
			
			
			totlaFees=packagePrice.subtract(giftAmount);			
			packageType.setStartTime(startTime);
			packageType.setStartTimeNum(starLongtTime);
			packageType.setEndTime(endTime);
			packageType.setEndTimeNum(endLongTime);
			packageRecord.setDeductions(pointsMoney);
			packageRecord.setVouchersMoney(vouchersMoney);
			packageRecord.setUserName(userName);
			packageRecord.setFeePayType(feePayType.ordinal());
			packageRecord.setPackageTypeNum(packageType.getPackageTypeNum());
			packageRecord.setPackageTypeId(Integer.parseInt(packageType.getId().toString()));
			packageRecord.setPackageName(packageType.getPackageName());
			packageRecord.setPackagePrice(packageType.getPackagePrice());
			packageRecord.setGiftAmount(giftAmount);
			packageRecord.setBuyingDate(now);
			packageRecord.setStartTime(packageType.getStartTime());
			packageRecord.setStartTimeNum(packageType.getStartTimeNum());
			packageRecord.setEndTime(packageType.getEndTime());
			packageRecord.setEndTimeNum(packageType.getEndTimeNum());
			packageRecord.setPackageDesc(packageType.getPackageDesc());
			packageRecord.setTimes(packageType.getTimes());
			packageRecord.setOrderNo(OrderNumPrefixConstraint.getInstance().genOrderNo("", 10));
			packageRecord.setOutTradeNo(odrerNumber);
			packageRecord.setUseRangeNum(packageType.getUseRangeNum());
			packageRecord.setLongService(packageType.getLongService()!=null?Integer.valueOf(packageType.getLongService()):20);
			packageRecord.setLimitTimesUse(packageType.getLimitTimesUse());
			packageRecord.setLimitUseTime(packageType.getLimitUseTime());
			packageRecord.setPackageCirculation(packageType.getPackageCirculation());
			PUserRange userRange = pUserRangeDao.findByUseRangeNum(packageType.getUseRangeNum());
			if (userRange == null) {
				// 使用范围无效必须抛出异常, 事务回滚, 若是直接返回AppMessage则会导致部分成功的提交事务,部分购买不成功
				throw new Exception("套餐(" + packageType.getPackageName() + ")" + "使用范围无效");
			}
			packageRecord.setParkingId(userRange.getParkingId());
			businessId = userRange.getBusinessId();
			packageRecord.setBusinessId(businessId);
			
			Integer propertyNumber = null;
	    	Integer branchId = null;
	    	if(MStringUntils.isNotEmptyOrNull(userRange.getParkingId())){
		    	List<CommunityModel> communityList = communityModelService.checkByCommunity_number(userRange.getParkingId());
		    	if(communityList != null && communityList.size()>0){
		    		propertyNumber = communityList.get(0).getProperty_number();
		    		branchId = communityList.get(0).getBranchId();
		    	}
	    	}
	    	packageRecord.setPropertyNumber(propertyNumber);
	    	packageRecord.setBranchId(branchId);

			packageRecord.setLicense(license);

			this.save(packageRecord);
		}

		// 扣款
		if (feePayType == RPackageRecord.FeePayType.Member_Account) {
			basicAccount=(basicAccount==null)?memberAccount.getBasicAccount():basicAccount;
			memberAccount=(memberAccount==null)?memberAccountService.findByUserNameUserType(userName, (byte) SMemberAccount.Type.member.ordinal()):memberAccount;
			RAccountTransferRecord accountRecord = new RAccountTransferRecord();
			accountRecord.setAccountId(memberAccount.getId().toString());
			accountRecord.setAccountNumber(memberAccount.getAccountNumber());
			accountRecord.setIdentify(RAccountTransferRecord.genIdentify(memberAccount.getAccountNumber()));
			accountRecord.setOrderNo(odrerNumber);
			accountRecord.setSerialNumber(RAccountTransferRecord.genSerialNumber(memberAccount.getAccountNumber()));
			accountRecord.setUserName(userName);
			accountRecord.setAmount(totlaFees);
			
			BigDecimal basicBalance = basicAccount.subtract(totlaFees);
			accountRecord.setAccountBalance(basicBalance);
			accountRecord.setAccountType((byte) RAccountTransferRecord.AccountType.basicAccount.ordinal());
			accountRecord.setOperateTime(now);
			accountRecord.setOperateType((byte) (RAccountTransferRecord.OperateType.inter.ordinal()));
			accountRecord.setType(RAccountTransferRecord.Type.expenseCarLight);
			accountRecord.setComment("购买点滴洗套餐支出");
			accountRecord.setIsConfirmed((byte) RAccountTransferRecord.IsConfirmed.ok.ordinal());
			accountRecord.setStatus((byte) (RAccountTransferRecord.Status.finished.ordinal()));
			accountRecord.setUserType((byte) 0);// 0、会员 1、商户
			
			Member member = memberService.findByUsername(userName);
			Integer propertyNumber = null;
	    	Integer branchId = null;
	    	if(MStringUntils.isNotEmptyOrNull(member.getCommunityNumber())){
		    	List<CommunityModel> communityList = communityModelService.checkByCommunity_number(member.getCommunityNumber());
		    	if(communityList != null && communityList.size()>0){
		    		propertyNumber = communityList.get(0).getProperty_number();
		    		branchId = communityList.get(0).getBranchId();
		    	}
	    	}
	    	accountRecord.setCommunityNumber(member.getCommunityNumber());
	    	accountRecord.setPropertyNumber(propertyNumber);
	    	accountRecord.setBranchId(branchId);

			accountTransferRecordService.save(accountRecord);

			memberAccount.setBasicAccount(basicBalance);
			BigDecimal consumAmount = memberAccount.getConsumAmount();
			if (consumAmount == null) {
				consumAmount = new BigDecimal(0);
			}
			memberAccount.setConsumAmount(consumAmount.add(totlaFees));// 消费金额累加
			memberAccountService.update(memberAccount);
			
			//生成商户交易记录及修改商户电子钱包余额
			PBusiness pBusiness = businessService.find((long)businessId);
			MerchantTransctionRecordSource MerchantTransctionRecordSource = new MerchantTransctionRecordSource(); 
			Integer totalAmount = priceCount.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
			Integer paymentAmount = totlaFees.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
			Integer businessVouchers = vouchersMoney.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
			Integer businessPoint = pointsMoney.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
			Integer businessIncome = paymentAmount;
			MerchantTransctionRecordSource.createMerchantTransctionRecord(totalAmount, paymentAmount, null,businessVouchers, businessIncome, 
					null, MerchantTransactionRecord.RecordStatus.pay, MerchantTransactionRecord.RecordType.dripCar, MerchantTransactionRecord.SourceType.user,
					MerchantTransactionRecord.TransactionStatus.credited, odrerNumber,null, pBusiness.getBusinessNum().toString(), null, businessPoint,userName,
					MerchantTransactionRecord.PayType.Paid_Account,"购买点滴洗",businessService, merchantTransactionRecordService, sMemberAccountServiceImpl);
		}
	
	}	
	
	public Date getDate(int indexDay){
		Calendar ca=getCalendar(Calendar.MINUTE, indexDay);//获取处理后日期
		return ca.getTime();
	}
	public Long getDateLong(int indexDay){		
		Calendar ca=getCalendar(Calendar.MINUTE, indexDay);//获取处理后日期
		return ca.getTimeInMillis()/1000L;
	}
	
	public Calendar getCalendar(int calendarType,int indexDay){
		Calendar ca = Calendar.getInstance();
		Date curDate = new Date();
		ca.setTime(curDate);
		ca.add(calendarType, indexDay);
		return ca;
	}
	//获取动态时间
	public Date getDate(String times) throws Exception{
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.parse(times);
		} catch (Exception e) {
			
		}
		return new Date();
	}
	/**
	 * 对订单编号做一个简单短时缓存
	 */
	@Deprecated // com.arf.core.OrderNumPrefixConstraint
	private final LoadingCache<String, String> orderNoCache = CacheBuilder.newBuilder()
			.expireAfterWrite(3L, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
				Map<String, String> map = new ConcurrentHashMap<>();

				@Override
				public String load(String key) {
					String v = map.get(key);
					return v == null ? "" : v;
				}

			});

	@Deprecated // com.arf.core.OrderNumPrefixConstraint
	private String genOrderNo() {
		Date now = new Date();
		String timeStr = DateFormatUtils.format(now, "yyyyMMddHHmmssSSS");
		String no = "";
		while (true) {
			no = timeStr + RandomStringUtils.randomNumeric(12);
			String exist = "";
			try {
				exist = orderNoCache.get(no);
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			if (StringUtils.isBlank(exist)) {
				orderNoCache.put(no, no);
				break;
			}
		}
		return no;
	}

	@Override
	public AppMessage findPackages(String userName, RPackageRecord.Status status, int pageSize, int pageNo) {
		return this.findPackages(userName,0,status, null, pageSize, pageNo);
	}

	@Override
	public AppMessage findPackages(final String userName,int businessNum,Status status, FeePayStatus feePayStatus, int pageSize,
			int pageNo) {
		if (StringUtils.isBlank(userName)) {
			return AppMessage.codeMessage(60001, "查询用户名不能为空");
		}
		List<String> useRangeNum=null;
		if(businessNum>0){//商户Num大于0时
			PBusiness  pBusiness =businessService.findRedisToDbByBusinessId(businessNum);
			useRangeNum=new ArrayList<String>();
			if(pBusiness!=null){
				long pBusinessId=pBusiness.getId();				
				List<PUserRange> listPUserRange=pUserRangeService.findbyBusinessId((int)pBusinessId);
				if(listPUserRange!=null&&listPUserRange.size()>0){
					for (PUserRange pUserRange : listPUserRange) {
						useRangeNum.add(pUserRange.getUseRangeNum());
					}
				}			
			}
			if(useRangeNum==null||useRangeNum.size()==0){
				useRangeNum.add("");
			}
		}//跟根据小区对应的
		PageResult<RPackageRecord> page = packageRecordDao.findPageByUserName(userName,useRangeNum,
				status == null ? null : status.ordinal(), feePayStatus == null ? null : feePayStatus.ordinal(),
				pageSize, pageNo);
		List<RPackageRecord> pageData = page.getList();

		List<Map<String, Object>> dataList = Lists.transform(pageData,
				new Function<RPackageRecord, Map<String, Object>>() {

					@Override
					public Map<String, Object> apply(RPackageRecord input) {
						Map<String, Object> mapBean = BeanUtils.bean2Map(input);
						// 查询商户信息
						long businessId = input.getBusinessId();
						PBusiness business = businessService.findRedisToDbById(businessId);// 商户
						Integer packageCirculation=input.getPackageCirculation();//获取限量发行次数
						packageCirculation=(packageCirculation!=null&&packageCirculation>0)?packageCirculation:0;//判断发行数量						
						mapBean.put("packageCirculation", packageCirculation);
						if(business==null){
							business=new PBusiness();
							business.setBusinessName("未知商户");
							business.setBusinessNum(0);
						}else{
							mapBean.put("businessName", business.getBusinessName());
							mapBean.put("businessNum", business.getBusinessNum());
							mapBean.put("businessAddress", business.getAddress());
							mapBean.put("businessPic", business.getBusinessPic());
							String phone = business.getContactPhone();							
							phone=(StringUtils.isNotBlank(phone))?phone:business.getLoginName();
							mapBean.put("businessPhone", phone);
							mapBean.put("businessHours", business.getBusinessHours());
							mapBean.put("businessGrade", business.getBusinessGrade());
							mapBean.put("businessComment",business.getBusinessComment());
							mapBean.put("businessStatus", business.getBusinessStatus());
							mapBean.put("businessUsualTime", business.getBusinessUsualTime());
							mapBean.put("businessServiceRange", business.getBusinessServicePackageNums());
							mapBean.put("businessDescription ", business.getBusinessDescription());
						}
						return mapBean;
					}

				});

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", dataList);
		map.put("totalNum", page.getTotalNum());
		
		//获取设置的过期提醒提前天数
		String expiration= sysconfigServiceImpl.getValue(DAY_OF_EXPIRATION);
		 Calendar c = Calendar.getInstance();
		 c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(expiration));
		 Date endTime = c.getTime();
		 //查询即将过期的套餐
		List<RPackageRecord> packageList = packageRecordService.findPackageByUserName(userName, endTime);
		String orderNo = "";
		Date pushDate = null;
		List<String> list = new ArrayList<String>();
		for(RPackageRecord packageRecord:packageList){
			pushDate = packageRecord.getPushTime();
			//判断今天是否推送过
			if(pushDate!=null){
				//今天0时
				Calendar current = Calendar.getInstance();
				current.set(Calendar.YEAR, current.get(Calendar.YEAR));
				current.set(Calendar.MONTH, current.get(Calendar.MONTH));
				current.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
				current.set(Calendar.HOUR_OF_DAY, 0);
				current.set(Calendar.MINUTE, 0);
				current.set(Calendar.SECOND, 0);
				Date todayStart = current.getTime();
				if(pushDate.compareTo(todayStart)<0 ){
					orderNo = orderNo + "," + packageRecord.getOrderNo();
					list.add(packageRecord.getOrderNo());
				}
			}else{
				orderNo = orderNo + "," + packageRecord.getOrderNo();
				list.add(packageRecord.getOrderNo());
			}
		}
		Member member = memberService.findByUsername(userName);
		//判断今天是否推送过
		if(!"".equals(orderNo)){
			
				//检查用户是否有开启接受信息
				if("0".equals(member.getIsSendPush())){
					// 推送给用户信息
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								String msgContent = "尊敬的"+userName+"用户，您有未使用卡券即将过期，请尽快使用";
								PushMessage pushMessage = new PushMessage("卡券过期提醒",PushMessage.ContentType.PACKAGE_EXPIRES, msgContent);
								userPushUntils.pushNotificationMsgToUser(userName,
										msgContent, 
										"卡券过期提醒", 
										pushMessage, 
										null);
							} catch (APIConnectionException | APIRequestException e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
				packageRecordService.updatePackageByOrderNo(list);
		}
		return AppMessage.success("ok", map);
	}

	@Override
	@Transactional
	public boolean boughtPkgSuccess(String outTradeNo, int totalFee,Integer feePayType) {
		if (StringUtils.isBlank(outTradeNo) || totalFee <= 0) {
			return false;
		}
		MerchantTransactionRecord.PayType payType=null;
		if(feePayType==1){
			payType=MerchantTransactionRecord.PayType.Paid_WeiXin;
		}
		if(feePayType==2){
			payType=MerchantTransactionRecord.PayType.Paid_Alipay;
		}

		BigDecimal totalFees = packageRecordDao.findSumTotalFee(outTradeNo);
		BigDecimal giftAmounts = packageRecordDao.findSumGiftAmount(outTradeNo);
		if (totalFees == null || totalFees.doubleValue() < 0D) {
			return false;
		}
		if (giftAmounts == null) {
			giftAmounts = BigDecimal.ZERO;
		}
		BigDecimal receivable = totalFees.subtract(giftAmounts);// 应收
		if (receivable.multiply(new BigDecimal(100D)).intValue() != totalFee) {
			log.info("在线支付套餐购买的时候出现回调查询总价与支付总价价格不一样的问题," + "outTradeNo=" + outTradeNo + "DB totalFees=" + totalFees
					+ ",giftAmounts=" + giftAmounts + ",callback toalfee=" + totalFee);
			return false;
		}
		List<RPackageRecord> records = packageRecordDao.findByOutTradeNo(outTradeNo);
		if (CollectionUtils.isEmpty(records)) {
			return false;
		}

		BigDecimal vouchersMoney = new BigDecimal(0);//代金券总金额
		BigDecimal pointsMoney = new BigDecimal(0);//平台积分抵扣总金额
		Integer businessId = null;
		String userName = null;
		for (RPackageRecord record : records) {
			if (record.getFeePayStatus() != RPackageRecord.FeePayStatus.pay_success.ordinal()) {
				record.setFeePayStatus(RPackageRecord.FeePayStatus.pay_success.ordinal());
				record.setStatus(RPackageRecord.Status.available.ordinal());
				record.setReturnPoint(ReturnPointVoucher.Not_Return.ordinal());
				this.update(record);
				if(businessId==null){
					businessId=record.getBusinessId();
				}
				if(userName==null){
					userName=record.getUserName();
				}
				if(record.getVouchersMoney()!=null){
					vouchersMoney=vouchersMoney.add(record.getVouchersMoney());
				}
				if(record.getDeductions()!=null){
					pointsMoney=pointsMoney.add(record.getDeductions());
				}
				
			}else{
				return true;
			}
		}
		//生成商户交易记录及修改商户电子钱包余额
		PBusiness pBusiness = businessService.find((long)businessId);
		MerchantTransctionRecordSource MerchantTransctionRecordSource = new MerchantTransctionRecordSource(); 
		Integer totalAmount = totalFees.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
		Integer paymentAmount = totalFee;
		Integer businessVouchers = vouchersMoney.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
		Integer businessPoint = pointsMoney.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
		Integer businessIncome = paymentAmount;
		MerchantTransctionRecordSource.createMerchantTransctionRecord(totalAmount, paymentAmount, null,businessVouchers, businessIncome, 
				null, MerchantTransactionRecord.RecordStatus.pay, MerchantTransactionRecord.RecordType.dripCar, MerchantTransactionRecord.SourceType.user,
				MerchantTransactionRecord.TransactionStatus.credited, outTradeNo,null, pBusiness.getBusinessNum().toString(), null, businessPoint, userName,
				payType,"购买点滴洗",businessService, merchantTransactionRecordService, sMemberAccountServiceImpl);
		return true;
	}

	@Override
	public SaleroomOfPeriodDto findSaleroomOfPeriod(long businessId, Date startBuyingTime, Date endBuyingTime) {
		return packageRecordDao.findSaleroomOfPeriod(businessId, startBuyingTime, endBuyingTime);
	}

	@Override
	public boolean weixiCallback(WeixinPaymentCallBackData callbackData) {
		return boughtPkgSuccess(callbackData.getOut_trade_no(), callbackData.getTotal_fee(),1);
	}

	@Override
	public boolean alipayCallback(AliPaymentCallBackData callbackData) {
		return boughtPkgSuccess(callbackData.getOut_trade_no(), (int) (callbackData.getTotal_fee() * 100D),2);
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			//注册本service为支付回调业务逻辑
			RegisterPaymentCallBackService.registerService(OrderNumPrefixConstraint.PREFIX_CARBRIGHTER_PKG_BUY, getClass());
		}
	}


	@Override
	public BigDecimal salesInfoStatistics(Date startTime, Date endTime, Integer businessId, Integer packageId) {
		return packageRecordDao.salesInfoStatistics(startTime, endTime, businessId, packageId);
	}


	@Override
	public List<HashMap<String,Object>> salesInfoGroupDay(Date startTime, Date endTime, Integer businessId, Integer packageId,
			Integer limitCount) {
		return packageRecordDao.salesInfoGroupDay(startTime, endTime, businessId, packageId, limitCount);
	}


	@Override
	public List<RPackageRecord> findByFailureRecordToday(String buyingdate, int minute) {
		return packageRecordDao.findByFailureRecordToday(buyingdate, minute);
	}


	@Override
	public List<HashMap<String,Object>> findByUserName(String userName) {
		return packageRecordDao.findByUserName(userName);
	}


	@Override
	public RPackageRecord findBypackageId(String userName,Long packageId) {
		return packageRecordDao.findBypackageId(userName,packageId);
	}


	@Override
	public AppMessage walletAccountCallback(String outTradeNo, BigDecimal totalFee) {
		//do nothing
		return AppMessage.error("未注册的服务");
	}


	@Override
	public List<RPackageRecord> findPackageByUserName(String userName, Date endTime) {
		return packageRecordDao.findPackageByUserName(userName, endTime);
	}


	@Override
	public void updatePackageRecord(List<String> list) {
		 packageRecordDao.updatePackageRecord(list);
	}


	@Override
	public void updatePackageByOrderNo(List<String> list) {
		packageRecordDao.updatePackageByOrderNo(list);
	}


	@Override
	public int findCountByPkgNum(String userName, String packageTypeNum,RPackageRecord.Status status) {		
		return packageRecordDao.findCountByPkgNum(userName, packageTypeNum,status);
	}


	@Override
	public int findCountByPkgNum(String userName, String packageTypeNum) {
		return packageRecordDao.findCountByPkgNum(userName, packageTypeNum);
	}	
}
