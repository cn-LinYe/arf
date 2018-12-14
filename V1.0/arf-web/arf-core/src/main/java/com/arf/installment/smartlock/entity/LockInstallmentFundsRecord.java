package com.arf.installment.smartlock.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="lock_installment_funds_record")
@SequenceGenerator(name="sequenceGenerator",sequenceName="lock_installment_funds_record_sequence")
public class LockInstallmentFundsRecord extends BaseEntity<Long>{

	private static final long serialVersionUID = 8384784517176637230L;
	
	
	private String typeNum;//分期购类型编码
	private FundsType fundsType;//0: INSTALLMENT_FUNDS分期款,1:INSTALLATION_FUNDS安装款2:FIRST_FUNDS 首款
	private BigDecimal amount;//账款金额
	private Date paidDate;//支付(实际还款)日期
	private Date payDeadline;//待支付截止日期
	private PayStatus payStatus;//支付状态枚举:0: UNPAID未支付,1: PAID已支付2:已关闭
	private LockInstallmentOrderRecord.PayType payType;//支付类型枚举
	private DeadlineStatus deadlineStatus;//过期状态:0:NORMAL 正常1: OVER_DUE
	private Integer currentPeriod;//当前期数
	private Integer totalPeriods;//总期数
	private String fundsNo;//款项编号,用于支付款项唯一标识
	private String orderNo;//购买编号
	private BigDecimal overdueFine=new BigDecimal(0);//滞纳金默认0
	private Integer rewardPoint=0;//提前还款奖励的积分默认0
	private RefundStatus refundStatus;//退款状态
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PayStatus{
		UNPAID,PAID,CLOSED;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum FundsType{
		INSTALLMENT_FUNDS,INSTALLATION_FUNDS,FIRST_FUNDS;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum DeadlineStatus{
		NORMAL,OVER_DUE;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum RefundStatus{
		REFUND_AUDIT,REFUND_FALLED,REFUNDING,REFUNDED;
	}
	@Column(nullable = false,length=30)
	public String getTypeNum() {
		return typeNum;
	}
	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}
	@Column(nullable = false)
	public FundsType getFundsType() {
		return fundsType;
	}
	public void setFundsType(FundsType fundsType) {
		this.fundsType = fundsType;
	}
	@Column(nullable = false, precision = 10, scale = 2)
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getPayDeadline() {
		return payDeadline;
	}
	public void setPayDeadline(Date payDeadline) {
		this.payDeadline = payDeadline;
	}
	@Column(nullable = false)
	public PayStatus getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}
	public LockInstallmentOrderRecord.PayType getPayType() {
		return payType;
	}
	public void setPayType(LockInstallmentOrderRecord.PayType payType) {
		this.payType = payType;
	}
	@Column(nullable = false)
	public DeadlineStatus getDeadlineStatus() {
		return deadlineStatus;
	}
	public void setDeadlineStatus(DeadlineStatus deadlineStatus) {
		this.deadlineStatus = deadlineStatus;
	}
	@Column(length=11)
	public Integer getCurrentPeriod() {
		return currentPeriod;
	}
	public void setCurrentPeriod(Integer currentPeriod) {
		this.currentPeriod = currentPeriod;
	}
	@Column(length=11)
	public Integer getTotalPeriods() {
		return totalPeriods;
	}
	public void setTotalPeriods(Integer totalPeriods) {
		this.totalPeriods = totalPeriods;
	}
	@Column(nullable=false,unique=true,length=40)
	public String getFundsNo() {
		return fundsNo;
	}
	public void setFundsNo(String fundsNo) {
		this.fundsNo = fundsNo;
	}
	@Column(nullable = false,length=40)
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	@Column(precision = 10, scale = 2)
	public BigDecimal getOverdueFine() {
		return overdueFine;
	}
	public void setOverdueFine(BigDecimal overdueFine) {
		this.overdueFine = overdueFine;
	}
	@Column(length=11)
	public Integer getRewardPoint() {
		return rewardPoint;
	}
	public void setRewardPoint(Integer rewardPoint) {
		this.rewardPoint = rewardPoint;
	}
	public RefundStatus getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(RefundStatus refundStatus) {
		this.refundStatus = refundStatus;
	}
	
}
