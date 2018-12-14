package com.arf.carbright;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arf.carbright.entity.MerchantTransactionRecord;
import com.arf.carbright.entity.MerchantTransactionRecord.PayType;
import com.arf.carbright.entity.MerchantTransactionRecord.RecordStatus;
import com.arf.carbright.entity.MerchantTransactionRecord.RecordType;
import com.arf.carbright.entity.MerchantTransactionRecord.SourceType;
import com.arf.carbright.entity.MerchantTransactionRecord.TransactionStatus;
import com.arf.carbright.entity.PBusiness;
import com.arf.carbright.service.MerchantTransactionRecordService;
import com.arf.carbright.service.PbusinessService;
import com.arf.core.entity.Member;
import com.arf.core.util.MD5Util;
import com.arf.platform.entity.SMemberAccount;
import com.arf.platform.service.ISMemberAccountService;

public class MerchantTransctionRecordSource {
	
	private static Logger log = LoggerFactory.getLogger(MerchantTransctionRecordSource.class);

	
	
	/**生成商户交易记录
	 * @param recordType 记录类型枚举 1、物业缴费 2、月租缴费  3、临时停车及减免4、车保超市 5、洗衣帮帮 6、扫码支付 7、点滴洗
	 * @param totalAmount 消费总金额（单位:分）
	 * @param paymentAmount 支付金额（单位：分）
	 * @param platformVouchers 平台代金券（单位：分）
	 * @param businessVouchers 商家代金券（单位：分）
	 * @param businessIncome 商家进账（单位：分）
	 * @param dailyDeductions 日常抵扣金额（单位：分）
	 * @param recordStatus 记录状态（0 已支付 1 退款  ）
	 * @param sourceType 来源（ 0 用户  1  商户）
	 * @param transactionStatus 交易状态（0 进账 1 扣除）
	 * @param orderNo 订单编号
	 * @param communityNumber 小区编号
	 * @param businessNum 商户编号
	 * @param merchantTransactionRecordService 商户交易记录service
	 * @param businessService 商户service
	 * @param sMemberAccountService 电子账户service
	 * @param platformPoint 平台积分抵扣金额（单位：分）
	 * @param businessPoint 商家积分抵扣金额（单位：分）
	 * @param consumeUser 实际消费者
	 * @param payType 支付方式
	 * @param remark 备注
	 */
	public void createMerchantTransctionRecord(Integer totalAmount,Integer paymentAmount,Integer platformVouchers,Integer businessVouchers,Integer businessIncome,
			Integer dailyDeductions,RecordStatus recordStatus,RecordType recordType,SourceType sourceType,TransactionStatus transactionStatus,String orderNo,
			String communityNumber,String businessNum,Integer platformPoint,Integer businessPoint,String consumeUser,PayType payType,String remark,PbusinessService businessService,
			MerchantTransactionRecordService merchantTransactionRecordService,ISMemberAccountService sMemberAccountService){
		
		try {
			//根据商户编号查询商户登录名，然后查询电子账户
			PBusiness business = businessService.findByBusinessId(Integer.parseInt(businessNum));
			String loginName = business.getLoginName();//商户登录名
			SMemberAccount smemberAccount = sMemberAccountService.findByUserNameUserType(loginName, (byte)Member.Type.memberBip.ordinal());

			if(smemberAccount==null){//如果没有电子商户，则生成一个
				smemberAccount = new SMemberAccount();
				smemberAccount.setAccountNumber(sMemberAccountService.genAccountNumber(loginName));
				smemberAccount.setUserId(Integer.valueOf(business.getId().toString()));
				smemberAccount.setUserName(loginName);
				smemberAccount.setCredit(0);//信用
				smemberAccount.setBasicAccount(new BigDecimal(0));
				smemberAccount.setBasicAvaliableAccount(new BigDecimal(0));
				smemberAccount.setGiftAccount(new BigDecimal(0));
				smemberAccount.setConsumAmount(new BigDecimal(0));
				smemberAccount.setPoint(0);//积分
				smemberAccount.setStatus((byte)SMemberAccount.Status.usable.ordinal());
				smemberAccount.setActPwd(SMemberAccount.actPwdDefault);//账户明文密码
				String pwd = MD5Util.getMD5(loginName.subSequence(4, 9) + MD5Util.getMD5(SMemberAccount.actPwdDefault));
				smemberAccount.setPwd(pwd);
				smemberAccount.setType((byte)Member.Type.memberBip.ordinal());
				sMemberAccountService.save(smemberAccount);
				
			}
			DecimalFormat df = new DecimalFormat("#.00");
			//更改电子账户余额
			BigDecimal balance = smemberAccount.getBasicAccount();
			//保留精度
			BigDecimal income = new BigDecimal(df.format(new BigDecimal(businessIncome).divide(new BigDecimal(100))));
			if(recordStatus==MerchantTransactionRecord.RecordStatus.pay&&businessIncome!=null){
				smemberAccount.setBasicAccount(new BigDecimal(df.format(balance.add(income))));
			}
			if(recordStatus==MerchantTransactionRecord.RecordStatus.refunds&&businessIncome!=null){
				smemberAccount.setBasicAccount(new BigDecimal(df.format(balance.subtract(income))));
			}
			sMemberAccountService.update(smemberAccount);
			//生成商户交易记录
			MerchantTransactionRecord record = new MerchantTransactionRecord();
			record.setTotalAmount(totalAmount);
			record.setBalance(new BigDecimal(df.format(smemberAccount.getBasicAccount())).multiply(new BigDecimal(100)).intValue());
			record.setBusinessIncome(businessIncome);
			record.setBusinessNum(businessNum);
			record.setBusinessVouchers(businessVouchers);
			record.setCommunityNumber(communityNumber);
			record.setDailyDeductions(dailyDeductions);
			record.setOrderNo(orderNo);
			record.setPaymentAmount(paymentAmount);
			record.setPlatformVouchers(platformVouchers);
			record.setRecordStatus(recordStatus);
			record.setRecordType(recordType);
			record.setSourceType(sourceType);
			record.setTransactionStatus(transactionStatus);
			record.setPlatformPoint(platformPoint);
			record.setBusinessPoint(businessPoint);
			record.setConsumeUser(consumeUser);
			record.setRemark(remark);
			record.setPayType(payType);
			merchantTransactionRecordService.save(record);
			log.info(">>>>>>>>>>>>[生成商户交易记录方法[createMerchantTransctionRecord]成功");
		} catch (Exception e) {
			log.error(String.format(">>>>>>>>>>>>[生成商户交易记录方法[createMerchantTransctionRecord]服务异常"),e);
			e.printStackTrace();
		}
	}

}
