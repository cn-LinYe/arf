package com.arf.smart.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.arf.smart.entity.PCustomIcon.IconType;

@Entity
@Table(name = "lock_smartlock")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lock_smartlock_sequence")
public class Smartlock extends BaseEntity<Long>{
	/**
	 * 此实体仅供查询所用 不予操作
	 */
	private static final long serialVersionUID = -5964320812569301071L;
	
	private String sn;			
	private String tempPassword	;		
	private String managerPassword;			
	private String initialManagerPassword;			
	private String unlockPassword;			
	private String initialUnlockPassword;			
	private Date createTime;			
	private int battery;			
	private Date lastSynchTime;			
	private String productModel;			
	private String firmwareVersion;			
	private String lockName;			
	private String bluetoothMac;		
	private String certificateCode;			
	private int status;
	
	
	
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getTempPassword() {
		return tempPassword;
	}
	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}
	public String getManagerPassword() {
		return managerPassword;
	}
	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}
	public String getInitialManagerPassword() {
		return initialManagerPassword;
	}
	public void setInitialManagerPassword(String initialManagerPassword) {
		this.initialManagerPassword = initialManagerPassword;
	}
	public String getUnlockPassword() {
		return unlockPassword;
	}
	public void setUnlockPassword(String unlockPassword) {
		this.unlockPassword = unlockPassword;
	}
	public String getInitialUnlockPassword() {
		return initialUnlockPassword;
	}
	public void setInitialUnlockPassword(String initialUnlockPassword) {
		this.initialUnlockPassword = initialUnlockPassword;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getBattery() {
		return battery;
	}
	public void setBattery(int battery) {
		this.battery = battery;
	}
	public Date getLastSynchTime() {
		return lastSynchTime;
	}
	public void setLastSynchTime(Date lastSynchTime) {
		this.lastSynchTime = lastSynchTime;
	}
	public String getProductModel() {
		return productModel;
	}
	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}
	public String getFirmwareVersion() {
		return firmwareVersion;
	}
	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}
	public String getLockName() {
		return lockName;
	}
	public void setLockName(String lockName) {
		this.lockName = lockName;
	}
	public String getBluetoothMac() {
		return bluetoothMac;
	}
	public void setBluetoothMac(String bluetoothMac) {
		this.bluetoothMac = bluetoothMac;
	}
	public String getCertificateCode() {
		return certificateCode;
	}
	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
