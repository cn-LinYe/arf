package com.arf.activity.parkrate.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "pr_activity_record",uniqueConstraints = {
		@UniqueConstraint(columnNames = { "userName","outTradeNo"}, name = "unique_username_outtradeno")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "pr_activity_record_sequence")
public class ActivityRecord extends BaseEntity<Long> {

	private static final long serialVersionUID = 2991201970594862487L;

	private String userName;//用户名
	private String axdVoucherNum;//代金券编号
	private BigDecimal redPackAmount;//红包金额
	/**
	 * 红包状态,
	 * NOT_OBTAIN 0 未中奖;
	 * NOT_RAFFLE 1 未刮奖;
	 * SENDING 2 发放中; 
	 * SENT 3 已发放待领取; 
	 * FAILED 4 发放失败; 
	 * RECEIVED 5 已领取; 
	 * RFUND_ING 6 退款中; 
	 * REFUND 7 已退款
	 */
	private RedPackStatus redPackStatus;//
	private Date redPackSentDate;//红包发送时间
	private Date redPackReceivedDate;//领取时间
	private String openId;//领取人open_id
	private String redPackSendListid;//发送单号
	private String redPackFailedDetail;//失败详情
	private Integer redPackReSent;//重发次数
	private String outTradeNo;//订单号（月租缴费）
	private Long axdVouchersRecordId;//代金券记录id
	private String mchBillno;//商户订单号（每个订单号必须唯一。取值范围：0~9，a~z，A~Z） 接口根据商户订单号支持重入，如出现超时可再调用。
	private String communityNumber;//参与小区
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum RedPackStatus{
		NOT_OBTAIN, //0 未获得刮奖机会;
		NOT_RAFFLE, // 1 未刮奖;
		SENDING, // 2 发放中; 
		SENT, // 3 已发放待领取; 
		FAILED, // 4 发放失败; 
		RECEIVED, // 5 已领取; 
		RFUND_ING, // 6 退款中; 
		REFUND; // 7 已退款
	}
	
	@Column(length = 20)
	public String getUserName() {
		return userName;
	}
	@Column(length = 20)
	public String getAxdVoucherNum() {
		return axdVoucherNum;
	}
	@Column(precision = 8, scale = 2)
	public BigDecimal getRedPackAmount() {
		return redPackAmount == null ? null : redPackAmount.setScale(2, RoundingMode.DOWN);
	}
	public RedPackStatus getRedPackStatus() {
		return redPackStatus;
	}
	public Date getRedPackSentDate() {
		return redPackSentDate;
	}
	public Date getRedPackReceivedDate() {
		return redPackReceivedDate;
	}
	@Column(length = 40)
	public String getOpenId() {
		return openId;
	}
	@Column(length = 40)
	public String getRedPackSendListid() {
		return redPackSendListid;
	}
	@Column(length = 1024)
	public String getRedPackFailedDetail() {
		return redPackFailedDetail;
	}
	public Integer getRedPackReSent() {
		return redPackReSent;
	}
	@Column(length = 32)
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public Long getAxdVouchersRecordId() {
		return axdVouchersRecordId;
	}
	@Column(length = 40)
	public String getMchBillno() {
		return mchBillno;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setAxdVoucherNum(String axdVoucherNum) {
		this.axdVoucherNum = axdVoucherNum;
	}
	public void setRedPackAmount(BigDecimal redPackAmount) {
		this.redPackAmount = redPackAmount == null ? null : redPackAmount.setScale(2, RoundingMode.DOWN);
	}
	public void setRedPackStatus(RedPackStatus redPackStatus) {
		this.redPackStatus = redPackStatus;
	}
	public void setRedPackSentDate(Date redPackSentDate) {
		this.redPackSentDate = redPackSentDate;
	}
	public void setRedPackReceivedDate(Date redPackReceivedDate) {
		this.redPackReceivedDate = redPackReceivedDate;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setRedPackSendListid(String redPackSendListid) {
		this.redPackSendListid = redPackSendListid;
	}
	public void setRedPackFailedDetail(String redPackFailedDetail) {
		this.redPackFailedDetail = redPackFailedDetail;
	}
	public void setRedPackReSent(Integer redPackReSent) {
		this.redPackReSent = redPackReSent;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public void setAxdVouchersRecordId(Long axdVouchersRecordId) {
		this.axdVouchersRecordId = axdVouchersRecordId;
	}
	public void setMchBillno(String mchBillno) {
		this.mchBillno = mchBillno;
	}
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
}
