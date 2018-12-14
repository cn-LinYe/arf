package com.arf.eparking.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name = "license_plate_appeal")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "license_plate_appeal")
public class LicensePlateAppeal extends BaseEntity<Long>{

	/**
	 * 车辆绑定申诉表
	 */
	private static final long serialVersionUID = 1L;
	//手机号
	private String userName;
	//车牌号
	private String licenseNumber;
	//车驾号
	private String driverNumber;
	//发动机号
	private String engineNumber;
	//资料状态
	private DataStatus dataStatus;
	//状态
	private Status status;
	//绑定时间
	private Date bindingDate;
	//最后一次缴费时间
	private Date paymentDate;
	//状态
	private Type type;
	
	
	/**资料状态
	 * 0 Complete已完善 
	 * 1 NotComplete未完善
	 * */
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum DataStatus{
		Complete,NotComplete;
	}
	/**状态
	 *0 Normal正常
	 *1 Appeal被申诉
	 *2 Unbound已解绑
	 *3 Reject已驳回
	 **/
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		Normal,Appeal,Unbound,Reject;
	}
	/**资料状态
	 * 0 Bind主动绑定 
	 * 1 Authorized被授权
	 * */
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Type{
		Bind,Authorized;
	}
	@Column(length = 20)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 32)
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	@Column(length = 20)
	public String getDriverNumber() {
		return driverNumber;
	}
	public void setDriverNumber(String driverNumber) {
		this.driverNumber = driverNumber;
	}
	@Column(length = 20)
	public String getEngineNumber() {
		return engineNumber;
	}
	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}
	public DataStatus getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(DataStatus dataStatus) {
		this.dataStatus = dataStatus;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Date getBindingDate() {
		return bindingDate;
	}
	public void setBindingDate(Date bindingDate) {
		this.bindingDate = bindingDate;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	
	


}
