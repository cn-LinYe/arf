package com.arf.violation.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "v_traffic_violation_order_refund")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "v_traffic_violation_order_refund_sequence")
public class TrafficViolationOrderRefund extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3805970709919399180L;
	
	private String orderNo;//订单编号
	private Integer status;//1:退款申请中,2:退款完成,3:退款被拒绝
	private String reason;//退款理由
	private Date endTime;//退款/被拒处理时间
	private String remark;//处理备注
	private BigDecimal amount;//退款金额
	
	public enum RefundStatus{
		Notuse,
		Application,Finish,Refuse
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
