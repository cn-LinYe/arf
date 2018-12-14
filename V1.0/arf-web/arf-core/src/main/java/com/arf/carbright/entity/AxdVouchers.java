package com.arf.carbright.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_axd_vouchers")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_axd_vouchers_sequence")
public class AxdVouchers extends BaseEntity<Long> {

	/**
	 * 代金券
	 */
	private static final long serialVersionUID = 8767446783077744063L;
	// 代金券类型(0点滴洗服务1安心点充值服务2安心点停车服务3汽车保险服务4安心停车卡6:扫码抵扣代金券)
	private Integer vouchersType;
	// 代金券金额
	private BigDecimal vouchersMoney;
	// 代金券销售金额
	private BigDecimal vouchersSalesMoney;
	// 有效期时间（1月季月年）单位分钟 ,0表示永远可用
	private Integer effectiveDate;
	// 代金券状态(0启用 1禁用2 删除)
	private Integer vouchersStatus;
	// 代金券发行开始时间
	private Date vouchersStartDate;
	// 代金券发行结束时间
	private Date vouchersEndDate;
	// 代金券编号（DJQ）
	private String vouchersNum;
	// 代金券名称
	private String vouchersName;
	// 使用范围编码
	private String useRangeNum;
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
	
	private String queryCity;//查询条件1（市编号,隔开）
	private Integer queryType;//查询条件2（1月租车可见2：所有车均可见）
	private Integer isMeanwhileQuery;//是否同时满足查询条件才可以查询（0：否1：是）
	private Integer receivePoint;//	int	领取条件1（积分超过xxx）
	private String receiveCommunity;//	Varchar(2000)	领取条件2（指定小区月租车可见小区编号用,隔开不限制则为0）注：此条件需要包含在查询条件1的范围内
	private Integer isMeanwhileReceive;//	int	是否同时满足获取条件才可获取（0否1：是）
	private String useCity;//	int	使用条件为0全部省可用（市编号） 
	private String useBusinessNum;//	Varchar(2000)	使用条件商户编号,隔开
	private String vouchersPic	;//Varchar(200)	代金券图片
	private Integer vouchersNumber;//	int	发放数量
	private Integer surplusNumber;//	int	领取数量 
	private BigDecimal useAmount;//满多少钱可用
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
	public String getQueryCity() {
		return queryCity;
	}

	public void setQueryCity(String queryCity) {
		this.queryCity = queryCity;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	public Integer getIsMeanwhileQuery() {
		return isMeanwhileQuery;
	}

	public void setIsMeanwhileQuery(Integer isMeanwhileQuery) {
		this.isMeanwhileQuery = isMeanwhileQuery;
	}

	public Integer getReceivePoint() {
		return receivePoint;
	}

	public void setReceivePoint(Integer receivePoint) {
		this.receivePoint = receivePoint;
	}

	@Column(length = 4000)
	public String getReceiveCommunity() {
		return receiveCommunity;
	}

	public void setReceiveCommunity(String receiveCommunity) {
		this.receiveCommunity = receiveCommunity;
	}

	public Integer getIsMeanwhileReceive() {
		return isMeanwhileReceive;
	}

	public void setIsMeanwhileReceive(Integer isMeanwhileReceive) {
		this.isMeanwhileReceive = isMeanwhileReceive;
	}
	@Column(length = 128)
	public String getUseCity() {
		return useCity;
	}

	public void setUseCity(String useCity) {
		this.useCity = useCity;
	}
	@Column(length = 4000)
	public String getUseBusinessNum() {
		return useBusinessNum;
	}

	public void setUseBusinessNum(String useBusinessNum) {
		this.useBusinessNum = useBusinessNum;
	}
	@Column(length = 200)
	public String getVouchersPic() {
		return vouchersPic;
	}

	public void setVouchersPic(String vouchersPic) {
		this.vouchersPic = vouchersPic;
	}

	public Integer getVouchersNumber() {
		return vouchersNumber;
	}

	public void setVouchersNumber(Integer vouchersNumber) {
		this.vouchersNumber = vouchersNumber;
	}

	public Integer getSurplusNumber() {
		return surplusNumber;
	}

	public void setSurplusNumber(Integer surplusNumber) {
		this.surplusNumber = surplusNumber;
	}

	public enum VouchersType {
		DripService, AxdRechargeService, AxdParkingService,CarInsuranceServices,AxdParkingCard;
	}

	public enum AxdUsed {
		IsAxd, IsNotAxd;
	}

	public enum WechatUsed {
		OK, NotOk;
	}
	public enum VouchersStatus{
		enablem, not_Enable,delete;
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

	public Integer getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Integer effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Integer getVouchersStatus() {
		return vouchersStatus;
	}

	public void setVouchersStatus(Integer vouchersStatus) {
		this.vouchersStatus = vouchersStatus;
	}

	public Date getVouchersStartDate() {
		return vouchersStartDate;
	}

	public void setVouchersStartDate(Date vouchersStartDate) {
		this.vouchersStartDate = vouchersStartDate;
	}

	public Date getVouchersEndDate() {
		return vouchersEndDate;
	}

	public void setVouchersEndDate(Date vouchersEndDate) {
		this.vouchersEndDate = vouchersEndDate;
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

	@Column(length = 40)
	public String getUseRangeNum() {
		return useRangeNum;
	}

	public void setUseRangeNum(String useRangeNum) {
		this.useRangeNum = useRangeNum;
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

}
