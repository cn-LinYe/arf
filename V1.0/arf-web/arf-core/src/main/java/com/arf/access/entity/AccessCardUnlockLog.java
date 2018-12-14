package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

/**
 * 门禁卡开门记录,用于记录工程机同步的数据
 * @author Caolibao
 *
 */
@Entity
@Table(name = "access_card_unlock_log",
	indexes={
		@Index(name="index_community_number",columnList="communityNumber")}
)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_card_unlock_log_sequence")
public class AccessCardUnlockLog extends BaseEntity<Long>{
	private static final long serialVersionUID = 1L;
	
	private Date unlockTime;//开门时间
	private String chipNum;//门禁卡芯片编号
	private String bluetoothMac;//门禁蓝牙mac地址
	private String buildName;//楼栋
	private String unitName;//单元
	private String communityNumber;//门禁所在小区
	private Integer accessType;//门禁类型,小区大门:0,楼栋门:1
	private String cardUserName;//门禁卡持有人用户名
	private String cardUserRealname;//门禁卡持有人姓名名
	private String cardUserRoom;//门禁卡持有人房间信息,eg.3栋2单元7楼706
	private String syncUserName;//同步人用户名/工程机用户名
	public Date getUnlockTime() {
		return unlockTime;
	}
	@Column(length=20)
	public String getChipNum() {
		return chipNum;
	}
	@Column(length=20)
	public String getBluetoothMac() {
		return bluetoothMac;
	}
	@Column(length=20)
	public String getBuildName() {
		return buildName;
	}
	@Column(length=20)
	public String getUnitName() {
		return unitName;
	}
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public Integer getAccessType() {
		return accessType;
	}
	@Column(length=20)
	public String getCardUserName() {
		return cardUserName;
	}
	@Column(length=10)
	public String getCardUserRealname() {
		return cardUserRealname;
	}
	@Column(length=50)
	public String getCardUserRoom() {
		return cardUserRoom;
	}
	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}
	public void setChipNum(String chipNum) {
		this.chipNum = chipNum;
	}
	public void setBluetoothMac(String bluetoothMac) {
		this.bluetoothMac = bluetoothMac;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setAccessType(Integer accessType) {
		this.accessType = accessType;
	}
	public void setCardUserName(String cardUserName) {
		this.cardUserName = cardUserName;
	}
	public void setCardUserRealname(String cardUserRealname) {
		this.cardUserRealname = cardUserRealname;
	}
	public void setCardUserRoom(String cardUserRoom) {
		this.cardUserRoom = cardUserRoom;
	}
	
	@Column(length=30)
	public String getSyncUserName() {
		return syncUserName;
	}
	public void setSyncUserName(String syncUserName) {
		this.syncUserName = syncUserName;
	}
	
}
