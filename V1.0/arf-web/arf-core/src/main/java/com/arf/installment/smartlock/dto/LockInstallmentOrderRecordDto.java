package com.arf.installment.smartlock.dto;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.format.annotation.NumberFormat;

import com.arf.installment.smartlock.entity.LockInstallmentFundsRecord;
import com.arf.installment.smartlock.entity.LockInstallmentOrderRecord;

public class LockInstallmentOrderRecordDto extends LockInstallmentOrderRecord {

	private static final long serialVersionUID = -3069674029169373212L;
	
	private String typeNum;
	private String optionalPeriods	;// 	可选分期数 英文逗号分割的数字字符串
	private BigDecimal installationFee	;//		安装费
	private BigDecimal marketPrice	;//		市场价
	private BigDecimal installmentPrice	;//	分期总价
	private Byte model			;//FIRST_ZERO 0 首款为零,FIRST_NOT_ZERO ;//1 首款不为零
	private BigDecimal firstFunds	;//	首款
	private Integer prePayPoint	;//		提前支付的积分奖励
	private String  interest	;//可选分期对应的利息数,多个通过英文逗号分隔(decimal(10,2)),提前还款的不可以算利息
	private String typeName	;//		类型名称
	private Byte type	;//	not null	类型枚举:SMART_LOCK ;//0 智能门锁分期乐
	private BigDecimal overdueFineDay	;//not null,默认为0	每天产生的滞纳金
	private List<LockInstallmentFundsRecord> fundsRecord;
	
	@Override
	public String getTypeNum() {
		return typeNum;
	}
	public String getOptionalPeriods() {
		return optionalPeriods;
	}
	@NumberFormat(pattern="###.##") 
	public BigDecimal getInstallationFee() {
		return installationFee;
	}
	@NumberFormat(pattern="###.##") 
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	@NumberFormat(pattern="###.##") 
	public BigDecimal getInstallmentPrice() {
		return installmentPrice;
	}
	public Byte getModel() {
		return model;
	}
	@Override
	@NumberFormat(pattern="###.##") 
	public BigDecimal getFirstFunds() {
		return firstFunds;
	}
	public Integer getPrePayPoint() {
		return prePayPoint;
	}
	public String getInterest() {
		return interest;
	}
	public String getTypeName() {
		return typeName;
	}
	public Byte getType() {
		return type;
	}
	@NumberFormat(pattern="###.##") 
	public BigDecimal getOverdueFineDay() {
		return overdueFineDay;
	}
	@Override
	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}
	public void setOptionalPeriods(String optionalPeriods) {
		this.optionalPeriods = optionalPeriods;
	}
	public void setInstallationFee(BigDecimal installationFee) {
		this.installationFee = installationFee;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public void setInstallmentPrice(BigDecimal installmentPrice) {
		this.installmentPrice = installmentPrice;
	}
	public void setModel(Byte model) {
		this.model = model;
	}
	@Override
	public void setFirstFunds(BigDecimal firstFunds) {
		this.firstFunds = firstFunds;
	}
	public void setPrePayPoint(Integer prePayPoint) {
		this.prePayPoint = prePayPoint;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public void setOverdueFineDay(BigDecimal overdueFineDay) {
		this.overdueFineDay = overdueFineDay;
	}
	public List<LockInstallmentFundsRecord> getFundsRecord() {
		return fundsRecord;
	}
	public void setFundsRecord(List<LockInstallmentFundsRecord> fundsRecord) {
		this.fundsRecord = fundsRecord;
	}
}
