package com.arf.carbright.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_axd_vouchers_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_axd_vouchers_record_sequence")
public class AxdVouchersRecord extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1543803653264931619L;
	// 代金券类型(0点滴洗服务1安心点充值服务2安心点停车服务3汽车保险服务4安心停车卡6:扫码抵扣代金券)
	private Integer vouchersType;
	// 代金券金额
	private BigDecimal vouchersMoney;
	// 代金券销售金额
	private BigDecimal vouchersSalesMoney;
	// 代金券状态(0可用 1用完 2已过期)
	private Integer usedStatus;
	// 代金券有效开始时间
	private Date effectiveStartDate;
	// 代金券有效结束时间
	private Date effectiveEndDate;
	// 代金券编号（DJQ开头）
	private String vouchersNum;
	// 代金券名称
	private String vouchersName;
	// 使用规则(带替换符合)
	private String useRules;
	// 适用范围(带替换符合) 本代金券适用抵扣点滴洗服务费用
	private String scope;
	// 使用范围(带替换符合) 仅限安心点客户端使用
	private String useScope;
	// 安心点APP（0可以 1不可以）
	private Integer axdUsed;
	// 微信端(0 可以 1 不可以)
	private Integer wechatUsed;
	// 会员登录名
	private String userName;
	// 商户编号
	private String businessNum;
//	支付方式1:微信2:支付宝3:电子钱包/在线账户4：开通ECC赠送5：他人转赠 6:停车卡
	private Integer feePayType ;
//	支付订单编号 
	private String outTradeNo ;
//	 支付状态0;未支付1;已支付 2;支付失败
    private Integer feePayStatus ;
    
    private String useCity;//使用条件为0全部省可用（市编号）
    private String useBusinessNum	;//	使用条件商户编号,隔开
    private String vouchersPic	;//	代金券图片
    private BigDecimal useAmount;//满多少钱可用
    private Date  useTime;//使用时间
    
    private Integer vouchersSource	;//int	代金券类型（0：平台1：商家）
    
    
    public Integer getVouchersSource() {
		return vouchersSource;
	}

	public void setVouchersSource(Integer vouchersSource) {
		this.vouchersSource = vouchersSource;
	}
	public enum VouchersSource{
		platform,business
	}

	@Column(precision = 10, scale = 2)
    public BigDecimal getUseAmount() {
		return useAmount;
	}

	public void setUseAmount(BigDecimal useAmount) {
		this.useAmount = useAmount;
	}

	@Column(length = 512)
    public String getUseCity() {
		return useCity;
	}

	public void setUseCity(String useCity) {
		this.useCity = useCity;
	}
	@Column(length = 512)
	public String getUseBusinessNum() {
		return useBusinessNum;
	}

	public void setUseBusinessNum(String useBusinessNum) {
		this.useBusinessNum = useBusinessNum;
	}
	@Column(length = 40)
	public String getVouchersPic() {
		return vouchersPic;
	}

	public void setVouchersPic(String vouchersPic) {
		this.vouchersPic = vouchersPic;
	}

	public enum FeePayType{
		//支付方式1:微信2:支付宝3:电子钱包/在线账户4：开通ECC赠送5：他人转赠 6:停车卡
		//TODO 和上面描述字段类型对不上增加none 占用0位，微信支付从1开始
    	None,WeiXin,Alipay,ElectronicWallet,EccGive,Donation,ParkingCard
    }
    
    public enum FeePayStatus{
    	NotPay,PaySuccess,PayFail
    }

	public enum VouchersType {
		DripService, AxdRechargeService, AxdParkingService,CarInsuranceServices,AxdParkingCard,gift,SweepCode;		
		public static VouchersType get(int ordinal){
			VouchersType statuss[] = VouchersType.values();
			if(ordinal > statuss.length - 1){
				return null;
			}else{
				return statuss[ordinal];
			}
		}
	}

	public enum UsedStatus{
		Available,Finished,Expired,FEFail;
		public static UsedStatus get(int ordinal){			
			UsedStatus statuss[] =UsedStatus.values();
			if(ordinal > statuss.length - 1){
				return null;
			}else{
				return statuss[ordinal];
			}
		}
	}
	public enum AxdUsed {
		IsAxd, IsNotAxd;
	}

	public enum WechatUsed {
		OK, NotOk;
	}

	public Integer getVouchersType() {
		return vouchersType;
	}

	public void setVouchersType(Integer vouchersType) {
		this.vouchersType = vouchersType;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getVouchersMoney() {
		return vouchersMoney;
	}

	public void setVouchersMoney(BigDecimal vouchersMoney) {
		this.vouchersMoney = vouchersMoney;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getVouchersSalesMoney() {
		return vouchersSalesMoney;
	}

	public void setVouchersSalesMoney(BigDecimal vouchersSalesMoney) {
		this.vouchersSalesMoney = vouchersSalesMoney;
	}

	public Integer getUsedStatus() {
		return usedStatus;
	}

	public void setUsedStatus(Integer usedStatus) {
		this.usedStatus = usedStatus;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	@Column(length = 20)
	public String getVouchersNum() {
		return vouchersNum;
	}

	public void setVouchersNum(String vouchersNum) {
		this.vouchersNum = vouchersNum;
	}

	@Column(length = 40)
	public String getVouchersName() {
		return vouchersName;
	}

	public void setVouchersName(String vouchersName) {
		this.vouchersName = vouchersName;
	}

	@Column(length = 1000)
	public String getUseRules() {
		return useRules;
	}

	public void setUseRules(String useRules) {
		this.useRules = useRules;
	}

	@Column(length = 1000)
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Column(length = 1000)
	public String getUseScope() {
		return useScope;
	}

	public void setUseScope(String useScope) {
		this.useScope = useScope;
	}

	public Integer getAxdUsed() {
		return axdUsed;
	}

	public void setAxdUsed(Integer axdUsed) {
		this.axdUsed = axdUsed;
	}

	public Integer getWechatUsed() {
		return wechatUsed;
	}

	public void setWechatUsed(Integer wechatUsed) {
		this.wechatUsed = wechatUsed;
	}

	@Column(length = 40)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(length = 40)
	public String getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}

	public Integer getFeePayType() {
		return feePayType;
	}

	public void setFeePayType(Integer feePayType) {
		this.feePayType = feePayType;
	}
	@Column(length = 40)
	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public Integer getFeePayStatus() {
		return feePayStatus;
	}

	public void setFeePayStatus(Integer feePayStatus) {
		this.feePayStatus = feePayStatus;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	
}
