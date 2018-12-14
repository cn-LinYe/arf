package com.arf.activity.parkrate.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "pr_community_activity")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "pr_community_activity_sequence")
public class CommunityActivity extends BaseEntity<Long> {

	private static final long serialVersionUID = -4847920010873226913L;

	private String communityNumber;//小区编号
	private Integer status;//DISABLE 0 未开启状态;ENABLE 1 开启状态
	private Integer enableRedPkg;//是否开启红包发送
	private Integer redPkgRate;//红包中奖概率,eg. 10则中奖概率为10%
	private String redPkgInterval;//中间区间，上下限通过英文逗号分隔
	private BigDecimal redPkgMin;//红包中奖最小金额
	private BigDecimal redPkgMax;//红包中奖最大金额
	private Integer enableDripWashVoucher;//是否开启洗车代金券
	private String vouchersNum;//代金券编号
	
	private Date startDate;//活动开始日期 yyyy-MM-dd 00:00:00
	private Date endDate;//活动结束日期 yyyy-MM-dd 23:59:59
	
	@Column(length = 20,nullable=false)
	public String getCommunityNumber() {
		return communityNumber;
	}
	@Column(nullable = false)
	public Integer getStatus() {
		return status;
	}
	@Column(nullable = false)
	public Integer getEnableRedPkg() {
		return enableRedPkg;
	}
	public Integer getRedPkgRate() {
		return redPkgRate;
	}
	@Column(length = 10)
	public String getRedPkgInterval() {
		return redPkgInterval;
	}
	@Column(precision = 8, scale = 2)
	public BigDecimal getRedPkgMin() {
		return redPkgMin == null ? null : redPkgMin.setScale(2, RoundingMode.DOWN);
	}
	@Column(precision = 8, scale = 2)
	public BigDecimal getRedPkgMax() {
		return redPkgMax == null ? null : redPkgMax.setScale(2, RoundingMode.DOWN);
	}
	@Column(nullable = false)
	public Integer getEnableDripWashVoucher() {
		return enableDripWashVoucher;
	}
	@Column(length = 20)
	public String getVouchersNum() {
		return vouchersNum;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setEnableRedPkg(Integer enableRedPkg) {
		this.enableRedPkg = enableRedPkg;
	}
	public void setRedPkgRate(Integer redPkgRate) {
		this.redPkgRate = redPkgRate;
	}
	public void setRedPkgInterval(String redPkgInterval) {
		this.redPkgInterval = redPkgInterval;
	}
	public void setRedPkgMin(BigDecimal redPkgMin) {
		this.redPkgMin = redPkgMin == null ? null : redPkgMin.setScale(2, RoundingMode.DOWN);
	}
	public void setRedPkgMax(BigDecimal redPkgMax) {
		this.redPkgMax = redPkgMax == null ? null : redPkgMax.setScale(2, RoundingMode.DOWN);
	}
	public void setEnableDripWashVoucher(Integer enableDripWashVoucher) {
		this.enableDripWashVoucher = enableDripWashVoucher;
	}
	public void setVouchersNum(String vouchersNum) {
		this.vouchersNum = vouchersNum;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
