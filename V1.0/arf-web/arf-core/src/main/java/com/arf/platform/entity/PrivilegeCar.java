package com.arf.platform.entity;

import java.math.BigInteger;
import java.util.Date;

/**
 * <p><b>特权车</b></p>
 * 此类对应数据库的s_privilege_car，但是没有通过hibernate自动建表<br/>
 * 对应的service方法在ParkRateModelService
 * @author arf
 * @see com.arf.core.service.ParkRateModelService
 *
 */
public class PrivilegeCar{

	private long id;
	private Date createDate;
	private Date modifyDate;
	private String userName;
	private String license;//
	private String parkingId;
	private Integer type;//特权类型：0特殊人群 1 公职特权',
	private String remark;
	private Integer popertyNumber;
	private Integer headOfficeId;
	private Integer branchId;
	
	public PrivilegeCar() {
		super();
	}

	public PrivilegeCar(long id, Date createDate, Date modifyDate,
			String userName, String license, String parkingId, Integer type,
			String remark, Integer popertyNumber, Integer headOfficeId,
			Integer branchId) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.userName = userName;
		this.license = license;
		this.parkingId = parkingId;
		this.type = type;
		this.remark = remark;
		this.popertyNumber = popertyNumber;
		this.headOfficeId = headOfficeId;
		this.branchId = branchId;
	}

	public long getId() {
		return id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public String getUserName() {
		return userName;
	}

	public String getLicense() {
		return license;
	}

	public String getParkingId() {
		return parkingId;
	}

	public Integer getType() {
		return type;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getPopertyNumber() {
		return popertyNumber;
	}

	public Integer getHeadOfficeId() {
		return headOfficeId;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setPopertyNumber(Integer popertyNumber) {
		this.popertyNumber = popertyNumber;
	}

	public void setHeadOfficeId(Integer headOfficeId) {
		this.headOfficeId = headOfficeId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

}
