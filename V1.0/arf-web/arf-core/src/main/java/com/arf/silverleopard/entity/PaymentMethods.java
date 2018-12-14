package com.arf.silverleopard.entity;

import com.arf.core.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "p_payment_methods")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_payment_methods_sequence")
public class PaymentMethods extends BaseEntity<Long>{

	private static final long serialVersionUID = 5532373732253982438L;
	
	private String orignCode;		//支付方式编号
	private String code;	//支付方式代码
	private String name;	//支付方式名称
	private String appId;	//门店凭证
	private String amount;

	@Transient
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrignCode() {
		return orignCode;
	}

	public void setOrignCode(String orignCode) {
		this.orignCode = orignCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
}
