package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

/**
 * 门禁密码开门记录,用于记录工程机同步的数据
 * @author Caolibao
 *
 */
@Entity
@Table(name = "access_pwd_unlock_log",
	indexes={
		@Index(name="index_community_number",columnList="communityNumber")}
)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_pwd_unlock_log_sequence")
public class AccessPwdUnlockLog extends BaseEntity<Long>{
	private static final long serialVersionUID = 1L;

	private Date unlockTime;//开门时间 	    
    private String bluetoothMac;// 	蓝牙mac地址	
    private Integer passwdType;//密码类型,0:用户密码;1:临时密码
    private String communityNumber;// 	门禁所在小区
    private String buildName;//楼栋   
    private String unitName;//单元
    
    private String pwd;//开门密码
    
    private String syncUserName;//同步人用户名/工程机用户名
    
	public Date getUnlockTime() {
		return unlockTime;
	}
	public String getBluetoothMac() {
		return bluetoothMac;
	}
	public Integer getPasswdType() {
		return passwdType;
	}
	public String getCommunityNumber() {
		return communityNumber;
	}
	public String getBuildName() {
		return buildName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}
	public void setBluetoothMac(String bluetoothMac) {
		this.bluetoothMac = bluetoothMac;
	}
	public void setPasswdType(Integer passwdType) {
		this.passwdType = passwdType;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	@Column(length=20)
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Column(length=30)
	public String getSyncUserName() {
		return syncUserName;
	}
	public void setSyncUserName(String syncUserName) {
		this.syncUserName = syncUserName;
	}
	
	
}         