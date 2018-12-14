package com.arf.propertymgr.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class OtherFee implements Serializable{

	private static final long serialVersionUID = 5742067336257809443L;
	
	private String name;
	private BigDecimal fee;
	private String remark;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
