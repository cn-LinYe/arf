package com.arf.violation.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "v_traffic_violation_order_complaint")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "v_traffic_violation_order_complaint_sequence")
public class TrafficViolationOrderComplaint extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8646920258945027176L;
	
	private String orderNo;//订单编号
	private Integer complaintStatus;//投诉状态,1:投诉中,2:投诉处理完毕
	private String content;//投诉内容
	private Date endTime;//处理完毕时间
	private String remark;//处理备注
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getComplaintStatus() {
		return complaintStatus;
	}
	public void setComplaintStatus(Integer complaintStatus) {
		this.complaintStatus = complaintStatus;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	
}
