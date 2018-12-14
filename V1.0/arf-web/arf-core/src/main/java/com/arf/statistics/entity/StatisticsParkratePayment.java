package com.arf.statistics.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="statistics_parkrate_payment")
@SequenceGenerator(name="sequenceGenerator",sequenceName="statistics_parkrate_payment_sequence")
public class StatisticsParkratePayment extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 308031821846297980L;
	
	private String communityNumber;//小区编号
	private Integer parkrateHumanCount;//月租缴费总人数
	private Integer parkrateTotallAmount;//月租缴费总金额
	private String parkratePercent;//缴费月数百分比
	private Integer parkrateMonths;//月租缴费月数
	private Integer aliPayCount;//支付宝支付总人数
	private Integer wechatPayCount;//微信支付总人数
	private Integer accountPayCount;//电子浅包支付总人数
	private Integer goldPayCount;//停车卡支付人数
	private Date statisticalDate;//统计日期
	
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public Integer getParkrateHumanCount() {
		return parkrateHumanCount;
	}
	public void setParkrateHumanCount(Integer parkrateHumanCount) {
		this.parkrateHumanCount = parkrateHumanCount;
	}
	public Integer getParkrateTotallAmount() {
		return parkrateTotallAmount;
	}
	public void setParkrateTotallAmount(Integer parkrateTotallAmount) {
		this.parkrateTotallAmount = parkrateTotallAmount;
	}
	@Column(length=10)
	public String getParkratePercent() {
		return parkratePercent;
	}
	public void setParkratePercent(String parkratePercent) {
		this.parkratePercent = parkratePercent;
	}
	public Integer getParkrateMonths() {
		return parkrateMonths;
	}
	public void setParkrateMonths(Integer parkrateMonths) {
		this.parkrateMonths = parkrateMonths;
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
