package com.arf.installment.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="i_installment_type")
@SequenceGenerator(name="sequenceGenerator",sequenceName="i_installment_type_sequence")
public class InstallmentType extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4599780690954972057L;

	private String typeNum;
	private String optionalPeriods	;// 	可选分期数 英文逗号分割的数字字符串
	private BigDecimal installationFee	;//		安装费
	private BigDecimal marketPrice	;//		市场价
	private BigDecimal installmentPrice	;//	分期总价
	private Status status	;// 	not null	状态枚举:OVER ;//0活动结束(售罄)UNDERWAY ;//1 进行中
	private Date outOfDate ;//		not null	过期时间
	private Model model			;//FIRST_ZERO 0 首款为零,FIRST_NOT_ZERO ;//1 首款不为零
	private BigDecimal firstFunds	;//	首款
	private Integer prePayPoint	;//		提前支付的积分奖励
	private String  interest	;//可选分期对应的利息数,多个通过英文逗号分隔(decimal(10,2)),提前还款的不可以算利息
	private String typeName	;//		类型名称
	private Type type	;//	not null	类型枚举:SMART_LOCK ;//0 智能门锁分期乐
	private BigDecimal overdueFineDay	;//not null,默认为0	每天产生的滞纳金
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		OVER,UNDERWAY
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Model{
		FIRST_ZERO,FIRST_NOT_ZERO
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Type{
		SMART_LOCK
	}
	@Column(length=40,nullable=false,unique=true)
	public String getTypeNum() {
		return typeNum;
	}
	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}
	@Column(length=100,nullable=false)
	public String getOptionalPeriods() {
		return optionalPeriods;
	}
	public void setOptionalPeriods(String optionalPeriods) {
		this.optionalPeriods = optionalPeriods;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getInstallationFee() {
		return installationFee;
	}
	public void setInstallationFee(BigDecimal installationFee) {
		this.installationFee = installationFee;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getInstallmentPrice() {
		return installmentPrice;
	}
	public void setInstallmentPrice(BigDecimal installmentPrice) {
		this.installmentPrice = installmentPrice;
	}
	@Column(nullable=false)
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Column(nullable=false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getOutOfDate() {
		return outOfDate;
	}
	public void setOutOfDate(Date outOfDate) {
		this.outOfDate = outOfDate;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getFirstFunds() {
		return firstFunds;
	}
	public void setFirstFunds(BigDecimal firstFunds) {
		this.firstFunds = firstFunds;
	}
	public Integer getPrePayPoint() {
		return prePayPoint;
	}
	public void setPrePayPoint(Integer prePayPoint) {
		this.prePayPoint = prePayPoint;
	}
	
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	@Column(length=20)
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	@Column(nullable=false)
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	@Column(precision=10,scale=2,nullable=false)
	public BigDecimal getOverdueFineDay() {
		return overdueFineDay;
	}
	public void setOverdueFineDay(BigDecimal overdueFineDay) {
		this.overdueFineDay = overdueFineDay;
	}
	
	
}
