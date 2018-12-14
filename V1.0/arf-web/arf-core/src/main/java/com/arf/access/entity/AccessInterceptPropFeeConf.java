package com.arf.access.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
@Table(name = "access_intercept_prop_fee_conf")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_intercept_prop_fee_conf_sequence")
public class AccessInterceptPropFeeConf extends BaseEntity<Long> {

	private static final long serialVersionUID = 5162165305770865284L;
	public static final int Unpaid_Property_Fee_Alert_Second_Default = 5;//默认5s就结束弹窗
	
	private String communityNumber;//小区编号
	private Status status;//状态
	private Integer hideAlert;//自动隐藏提示的秒数
	private AlertType alertType;//提示方式
	
	/**
	 *	状态枚举:DISABLE 0 关闭;ENABLE 1 开启;
	 */
	@JsonFormat(shape = Shape.NUMBER_INT)
	public enum Status{
		DISABLE,//0 关闭
		ENABLE;//1 开启
	}
	
	/**
	 *	提示方式枚举:ALERT_ONCE_EVERYDAY 0 每天一次;ALERT_EVERY_TIME 1 每次都提示;
	 */
	@JsonFormat(shape = Shape.NUMBER_INT)
	public enum AlertType{
		ALERT_ONCE_EVERYDAY,//0 每天一次
		ALERT_EVERY_TIME;//1 每次都提示
	}

	@Column(length=32)	
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public Status getStatus() {
		return status;
	}

	public Integer getHideAlert() {
		return hideAlert;
	}

	public AlertType getAlertType() {
		return alertType;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setHideAlert(Integer hideAlert) {
		if(hideAlert != null){
			this.hideAlert = hideAlert;
		}else{
			this.hideAlert = Unpaid_Property_Fee_Alert_Second_Default;
		}
	}

	public void setAlertType(AlertType alertType) {
		this.alertType = alertType;
	}
	
}
