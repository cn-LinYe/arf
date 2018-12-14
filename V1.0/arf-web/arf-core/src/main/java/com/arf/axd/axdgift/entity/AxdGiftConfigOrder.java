package com.arf.axd.axdgift.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "s_axd_gift_config_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "s_axd_gift_config_order_sequence")
public class AxdGiftConfigOrder extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private String communityNo;//小区编号
	private String orderNo;//订单号
	private BigDecimal fee;//充值金额
	private Byte status;//状态（0、生成订单；1、充值完成；2、充值失败）
	
	public String getCommunityNo(){
		return communityNo;
	}
	@Column(name="order_no",length=50)
	public String getOrderNo(){
		return orderNo;
	}
	@Column(name = "fee",precision=10,scale=2)
	public BigDecimal getFee(){
		return fee;
	}
	@Column(name="status",length=4)
	public Byte getStatus() {
		return status;
	}
	public void setCommunityNo(String communityNo) {
		this.communityNo = communityNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
}
