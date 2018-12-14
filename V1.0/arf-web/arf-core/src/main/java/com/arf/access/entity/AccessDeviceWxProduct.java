package com.arf.access.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_device_wx_product")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_device_wx_product_sequence")
public class AccessDeviceWxProduct extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private String accessNum;
	private String productId;
	private String deviceId;
	private String publicQrcodeUrl; //门禁公众号二维码
	private Type type;//类型 0 安心点公众号 1 俺家公众号（默认安心点公众号）
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Type{
		ANXINDIAN_PUBLIC,
		ANJIA_PUBLIC;
	}
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getAccessNum() {
		return accessNum;
	}
	public String getProductId() {
		return productId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setAccessNum(String accessNum) {
		this.accessNum = accessNum;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getPublicQrcodeUrl() {
		return publicQrcodeUrl;
	}
	public void setPublicQrcodeUrl(String publicQrcodeUrl) {
		this.publicQrcodeUrl = publicQrcodeUrl;
	}
	
}
