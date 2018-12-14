
package com.arf.propertymgr.condition;

import java.util.List;
import java.util.Map;

import com.arf.propertymgr.entity.PropertyFeeBill.PayStatus;

public class PropertyFeeBillCondition{
	private String userName;
	private String agentUserName;
	private Integer pageSize;
	private Integer pageNo;
	private List<Map<String,String>> rooms;
	
	private String status;//查询状态 通过英文都好分割多个状态
	
	private PayStatus payStatus;//查询支付状态 0 未支付，1 支付成功

	public String getUserName() {
		return userName;
	}

	public String getAgentUserName() {
		return agentUserName;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public List<Map<String,String>> getRooms() {
		return rooms;
	}

	public String getStatus() {
		return status;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setAgentUserName(String agentUserName) {
		this.agentUserName = agentUserName;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public void setRooms(List<Map<String,String>> rooms) {
		this.rooms = rooms;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}
	
}