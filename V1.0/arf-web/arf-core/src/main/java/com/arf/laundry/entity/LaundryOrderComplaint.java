package com.arf.laundry.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "l_laundry_order_complaint")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "l_laundry_order_complaint_sequence")
public class LaundryOrderComplaint extends BaseEntity<Long>{

	private static final long serialVersionUID = 4776528463101752780L;

	/**
	 * 1:投诉中,2:投诉处理完毕
	 */
	public enum Status{
		Nouse,
		Complaining,
		Complained,
	}
	
	private String outTradeNo;//订单编号
	private Byte status;//1:投诉中,2:投诉处理完毕
	private String content;//投诉内容
	private Date endTime;//处理完毕时间
	private String remark;//处理备注
	
	@Column(length = 30)
	public String getOutTradeNo() {
		return outTradeNo;
	}
	@Column(length = 4)
	public Byte getStatus() {
		return status;
	}
	@Column(length = 512)
	public String getContent() {
		return content;
	}
	public Date getEndTime() {
		return endTime;
	}
	@Column(length = 512)
	public String getRemark() {
		return remark;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
