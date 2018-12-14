package com.arf.statistics.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="statistics_property_payment")
@SequenceGenerator(name="sequenceGenerator",sequenceName="statistics_property_payment_sequence")
public class StatisticsPropertyPayment  extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5351061628063386139L;
	
	private String communityNumber;//小区编号
	private Integer propertyTotalFee;//物业缴费总费用
	private Integer propertyUserCount;//缴费总人数
	private PropertyBillType propertyBillType;//计费周期枚举: 0:MONTH 月 1:QUARTER 季度 2:YEAR 年
	private Integer aliPayCount;//支付宝支付人数
	private Integer wechatPayCount;//微信支付人数
	private Integer accountPayCount;//电子钱包支付人数
	private Integer goldPayCount;//停车卡支付人数
	private Date statisticalDate;//统计日期
	
	public enum PropertyBillType{
		MONTH,//0 月 
		QUARTER,//1 季度 
		YEAR ;//2年
	}

	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public Integer getPropertyTotalFee() {
		return propertyTotalFee;
	}

	public void setPropertyTotalFee(Integer propertyTotalFee) {
		this.propertyTotalFee = propertyTotalFee;
	}

	public Integer getPropertyUserCount() {
		return propertyUserCount;
	}

	public void setPropertyUserCount(Integer propertyUserCount) {
		this.propertyUserCount = propertyUserCount;
	}

	public PropertyBillType getPropertyBillType() {
		return propertyBillType;
	}

	public void setPropertyBillType(PropertyBillType propertyBillType) {
		this.propertyBillType = propertyBillType;
	}

	public Integer getAliPayCount() {
		return aliPayCount;
	}

	public void setAliPayCount(Integer aliPayCount) {
		this.aliPayCount = aliPayCount;
	}

	public Integer getWechatPayCount() {
		return wechatPayCount;
	}

	public void setWechatPayCount(Integer wechatPayCount) {
		this.wechatPayCount = wechatPayCount;
	}

	public Integer getAccountPayCount() {
		return accountPayCount;
	}

	public void setAccountPayCount(Integer accountPayCount) {
		this.accountPayCount = accountPayCount;
	}
	public Integer getGoldPayCount() {
		return goldPayCount;
	}

	public void setGoldPayCount(Integer goldPayCount) {
		this.goldPayCount = goldPayCount;
	}

	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getStatisticalDate() {
		return statisticalDate;
	}

	public void setStatisticalDate(Date statisticalDate) {
		this.statisticalDate = statisticalDate;
	}

}
