package com.arf.member.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_account_transfer_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_account_transfer_record_sequence")
public class RAccountTransferRecord extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 账户类型：<br/>
	 * 0、基本账户 1、基本可用账户 2、赠送账户 
	 * @author arf
	 *
	 */
	public enum AccountType{
		basicAccount,basicAvaliableAccount,giftAccount
	}
	/**
	 * 0、会员 1、商户
	 * @author arf
	 *
	 */
	public enum UserType{
		Member,Business
	}
	/**
	 * 操作方式（1、接口2、人工3、自动）
	 * @author arf
	 *
	 */
	public enum OperateType{
		nouse,inter,manul,auto
	}
	/**
	 * 状态（1、完成 2、冻结 3、失败）
	 * @author arf
	 *
	 */
	public enum Status{
		nouse,finished,frozen,failed
	}
	/**
	 * (0、未确认 1、已确认)
	 * @author arf
	 *
	 */
	public enum IsConfirmed{
		not,ok
	}
	public class Type{
		public static final byte chargeAliPay = 1;//支付宝充值入账
		public static final byte chargeWeChat = 2;//微信充值入账
		public static final byte chargeUnionPay = 3;//银联充值入账
		public static final byte chargeTransfer = 4;//转账(入账)
		public static final byte chargeSystem = 5;//系统充值
		public static final byte chargeParkingOrder = 6;//预约停车位失败，费用返还（主要用于微信回调和支付宝回调）
		public static final byte chargeGift = 7; //充值赠送
		public static final byte chargeAxdGift = 8; //安心点赠送
		public static final byte chargeCarInsuranceOrder = 9;//车保车险返利
		public static final byte chargeEscape = 10;//逃欠费追缴
		public static final byte chargeViolation = 11;//违章订单被拒绝费用返还
		public static final byte chargeShopMallRefunds = 12;//商城退款入账
		public static final byte WITHDRAWALSFAILDREFUND = 13;//提现失败退款
		
		public static final byte expenseRecharge = 50;//圈存
		public static final byte expenseWithdraw = 51;//提现
		public static final byte expenseTransfer = 52;//给他人转账出账
		public static final byte expenseCreditCard = 53;//信用卡还款
		public static final byte expenseCarLight = 54;//点滴洗套餐
		public static final byte expenseCarStop = 55;//停车消费
		public static final byte expenseParkingOrder = 56;//E停车订单
		public static final byte expenseCrowdOrder = 57;//众筹订单
		public static final byte expenseEscapeRecovery = 58;//缴纳逃欠费
		public static final byte expenseBreakContract = 59;//E停车违约扣费
		public static final byte expenseOpenECC = 60;//开通ecc扣款
		public static final byte expenseLaundryOrder = 61;//洗衣订单
		public static final byte expenseCarInsuranceOrder=62;//车保车险扣款
		public static final byte expenseBuyGoldCard=63;//购买金卡消费
		public static final byte expenseParkRate = 64;//月租扣费
		
		public static final byte violationOrder = 65;//违章订单支付
		
		public static final byte expensePropertyFeeOrder = 66;//物业费订单支付
		
		public static final byte expenseSmartLockInstallment = 67;//安心锁分期乐
		public static final byte CarwashingDevicePay = 68;//洗车机支付
		
		//public static final byte WITHDRAWALSFAILDREFUND = 69;//提现失败退款,错误的
		
		public static final byte expenseParkingToPay = 70;//停车减免代付
		
		public static final byte SCANCODE = 71;//扫码支付
		
		public static final byte access_user_card = 71;//门禁卡
		
		public static final byte expenseAxdFresh = 72;//生鲜商城订单支付
	}
	
	/**
	 * 账户id
	 */
	private String accountId;
	/**
	 * 账户编码
	 */
	private String accountNumber;
	/**
	 * 唯一标识
	 */
	private String identify;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 流水号
	 */
	private String serialNumber;
	/**
	 * 用户登录账户
	 */
	private String userName;
	/**
	 * 本次操作金额,单位:元
	 */
	private BigDecimal amount;
	/**
	 * 账户余额,单位:元
	 */
	private BigDecimal accountBalance;
	/**
	 * 账户类型：<br/>
	 * 0、基本账户 1、基本可用账户 2、赠送账户 
	 */
	private Byte accountType;
	
	/**
	 * 账户类型：0、会员 1、商户
	 */
	private Byte userType;
	/**
	 * 操作时间
	 */
	private Date operateTime;
	/**
	 * 操作方式（1、接口2、人工3、自动）
	 */
	private Byte operateType;
	/**
	 * 状态（1、完成 2、冻结 3、失败）
	 */
	private Byte status;
	/**
	 * 类型<br/>
	 * 1-49为充值（1、支付宝充值 2、微信充值 3、银行卡充值 4、他人转账入账 5、系统充值）<br/>
	 * 50-99为消费（50、圈存 51、提现 52、给他人转账出账 53、信用卡还款 54、点滴洗套餐购买 55、停车消费）
	 */
	private Byte type;
	/**
	 * 操作说明/备注
	 */
	private String comment;
	/**
	 * 是否确认发放积分/返现 (0、未确认 1、已确认)
	 */
	private Byte isConfirmed;
	
	//小区编号
	private String communityNumber;
	//物业编号 
	private Integer propertyNumber;
	//子公司编号 
	private Integer branchId;
	
	@Column(name="account_id",length=50)
	public String getAccountId() {
		return accountId;
	}
	@Column(name="account_number",length=50)
	public String getAccountNumber() {
		return accountNumber;
	}
	@Column(name="identify",length=50)
	public String getIdentify() {
		return identify;
	}
	@Column(name="order_no",length=50)
	public String getOrderNo() {
		return orderNo;
	}
	@Column(name="serial_number",length=50)
	public String getSerialNumber() {
		return serialNumber;
	}
	@Column(name="user_name",length=50)
	public String getUserName() {
		return userName;
	}
	@Column(name="amount",precision=10,scale=2)
	public BigDecimal getAmount() {
		return amount;
	}
	@Column(name="account_balance",precision=10,scale=2)
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	@Column(name="account_type",length=4)
	public Byte getAccountType() {
		return accountType;
	}
	@Column(name="user_type",length=4)
	public Byte getUserType() {
		return userType;
	}
	@Column(name="operate_time")
	public Date getOperateTime() {
		return operateTime;
	}
	@Column(name="operate_type",length=4)
	public Byte getOperateType() {
		return operateType;
	}
	@Column(name="status",length=4)
	public Byte getStatus() {
		return status;
	}
	@Column(name="type",length=4)
	public Byte getType() {
		return type;
	}
	@Column(name="comment",length=50)
	public String getComment() {
		return comment;
	}
	@Column(name="is_confirmed",length=4)
	public Byte getIsConfirmed() {
		return isConfirmed;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
	public void setAccountType(Byte accountType) {
		this.accountType = accountType;
	}
	public void setUserType(Byte userType) {
		this.userType = userType;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public void setOperateType(Byte operateType) {
		this.operateType = operateType;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setIsConfirmed(Byte isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	
	/**
	 * 唯一标识 生成规则
	 * @return
	 */
	public static String genIdentify(String accountNumber){
		// TODO 生成唯一标识 待定
		return accountNumber + Long.valueOf(System.currentTimeMillis()).toString();
	}
	/**
	 * 流水号 生成规则
	 * @return
	 */
	public static String genSerialNumber(String accountNumber) {
		// TODO 生成流水号 待定
		return accountNumber + Long.valueOf(System.currentTimeMillis()).toString();
	}
	
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public Integer getPropertyNumber() {
		return propertyNumber;
	}
	public void setPropertyNumber(Integer propertyNumber) {
		this.propertyNumber = propertyNumber;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

}
