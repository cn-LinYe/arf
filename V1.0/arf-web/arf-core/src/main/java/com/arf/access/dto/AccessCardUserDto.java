package com.arf.access.dto;

import java.io.Serializable;
import java.util.Date;

import com.arf.access.entity.AccessCardUser.FreezeStatus;

public class AccessCardUserDto implements Serializable {

	private static final long serialVersionUID = 5178547472868442301L;

	private Long id;
	private String createDate;
	private String modifyDate;
	private Long version;
	private String userName;//用户名    
	private String cardNum;//卡编号
	private Integer status;//
	private String reportedLostDate;//挂失时间
	private String accessNum;//绑定的门禁编号
	private String communityNumber;//小区编号
	private String boundDate;//绑定时间
	private String endDate;//有效期
	private String chipNum;//芯片编号
	private String communityName;//小区名称
	private String accessName;//门禁名称
	private String bluetoothMac;//门禁蓝牙地址
	private String realName;
	
	private String roomBoundNumber;
	private String roomNumber;
	
	private String userTypeName;//用户类型名称
	private Integer authencateType;//门禁卡认证类型 0、Internet 网络认证 1、Bluetooth 蓝牙认证
	private Integer freezeStatus;//冻结状态（0 Normal正常 1 Freezed已冻结）
	private Date updateDate;//更新设置时间
	private Date effectiveDate;//门禁卡设置生效时间
	private String effectiveMac;//生效门禁-MAC地址
	private Integer totalTimes;//可使用次数（-1不限 ）
	private String cardVersion;//门禁卡版本（V1旧版门禁卡 V2 新版门禁卡....）
	private Integer laveTimes;//剩余次数
	private Integer cardUseType;//是否万能卡0 普通卡1 万能卡
	private String roomList;//关联房"屋的编号,多个关联房屋用”,”分割
	private String roomNameList;//关联房屋的名称,多个关联房屋用”,”分割
	private String receivedAccess;//信息已下发门禁[{bluetoothMac:"门禁mac地址",receivedDate:"下发时间"}]
	
	public static final String USER_TYPE_NAME_HOUSE_HOLDER = "业主";
	public static final String USER_TYPE_NAME_FAMILY_MEMBER = "业主家属";
	public static final String USER_TYPE_NAME_TENANT = "租客";
	public static final String USER_TYPE_NAME_MANAGER = "管理员";
	
	
	
	public Integer getCardUseType() {
		return cardUseType;
	}
	public void setCardUseType(Integer cardUseType) {
		this.cardUseType = cardUseType;
	}
	public String getRoomList() {
		return roomList;
	}
	public void setRoomList(String roomList) {
		this.roomList = roomList;
	}
	public String getRoomNameList() {
		return roomNameList;
	}
	public void setRoomNameList(String roomNameList) {
		this.roomNameList = roomNameList;
	}
	public String getBluetoothMac() {
		return bluetoothMac;
	}
	public void setBluetoothMac(String bluetoothMac) {
		this.bluetoothMac = bluetoothMac;
	}
	public Long getId() {
		return id;
	}
	public String getCreateDate() {
		return createDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public Long getVersion() {
		return version;
	}
	public String getUserName() {
		return userName;
	}
	public String getCardNum() {
		return cardNum;
	}
	public Integer getStatus() {
		return status;
	}
	public String getReportedLostDate() {
		return reportedLostDate;
	}
	public String getAccessNum() {
		return accessNum;
	}
	public String getCommunityNumber() {
		return communityNumber;
	}
	public String getBoundDate() {
		return boundDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public String getChipNum() {
		return chipNum;
	}
	public String getCommunityName() {
		return communityName;
	}
	public String getAccessName() {
		return accessName;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setReportedLostDate(String reportedLostDate) {
		this.reportedLostDate = reportedLostDate;
	}
	public void setAccessNum(String accessNum) {
		this.accessNum = accessNum;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setBoundDate(String boundDate) {
		this.boundDate = boundDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public void setChipNum(String chipNum) {
		this.chipNum = chipNum;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public void setAccessName(String accessName) {
		this.accessName = accessName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getRoomBoundNumber() {
		return roomBoundNumber;
	}
	public void setRoomBoundNumber(String roomBoundNumber) {
		this.roomBoundNumber = roomBoundNumber;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getUserTypeName() {
		return userTypeName;
	}
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	public Integer getAuthencateType() {
		return authencateType;
	}
	public void setAuthencateType(Integer authencateType) {
		this.authencateType = authencateType;
	}
	public Integer getFreezeStatus() {
		return freezeStatus;
	}
	public void setFreezeStatus(Integer freezeStatus) {
		this.freezeStatus = freezeStatus;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getEffectiveMac() {
		return effectiveMac;
	}
	public void setEffectiveMac(String effectiveMac) {
		this.effectiveMac = effectiveMac;
	}
	public Integer getTotalTimes() {
		return totalTimes;
	}
	public void setTotalTimes(Integer totalTimes) {
		this.totalTimes = totalTimes;
	}
	public String getCardVersion() {
		return cardVersion;
	}
	public void setCardVersion(String cardVersion) {
		this.cardVersion = cardVersion;
	}
	public Integer getLaveTimes() {
		return laveTimes;
	}
	public void setLaveTimes(Integer laveTimes) {
		this.laveTimes = laveTimes;
	}
	public String getReceivedAccess() {
		return receivedAccess;
	}
	public void setReceivedAccess(String receivedAccess) {
		this.receivedAccess = receivedAccess;
	}
}
