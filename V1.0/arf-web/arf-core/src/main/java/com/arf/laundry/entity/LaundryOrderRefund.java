package com.arf.laundry.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "l_laundry_order_refund")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "l_laundry_order_refund_sequence")
public class LaundryOrderRefund extends BaseEntity<Long>{

	private static final long serialVersionUID = -7847046956254200301L;

	/**
	 * 1:退款申请中,2:退款完成,3:退款被拒绝
	 */
	public enum Status{
		Nouse,
		Refund_Apply,
		Refunded,
		Refund_Refused,
	}
	
	private String outTradeNo;//订单编号
	private Byte status;//1:退款申请中,2:退款完成,3:退款被拒绝
	private String reason;//退款理由
	private Date endTime;//退款/被拒处理时间
	private String remark;//处理备注
	private BigDecimal amount;//退款金额
	
	@Column(length = 30)
	public String getOutTradeNo() {
		return outTradeNo;
	}
	@Column(length = 4)
	public Byte getStatus() {
		return status;
	}
	@Column(length = 512)
	public String getReason() {
		return reason;
	}
	public Date getEndTime() {
		return endTime;
	}
	@Column(length = 512)
	public String getRemark() {
		return remark;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getAmount() {
		return amount;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
