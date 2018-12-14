package com.arf.laundry.search;

import com.arf.base.BaseSearchForm;

public class LaundryOrderCondition extends BaseSearchForm {

	private Integer businessNum;// 商户编码
	private String userName;//用户名（手机号）
	private Integer status;//订单状态
	private Integer refundStatus;//退款状态
	public Integer getBusinessNum() {
		return businessNum;
	}
	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
}
