package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_card_user")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_card_user_sequence")
public class AccessCardUser extends BaseEntity<Long> {

	private static final long serialVersionUID = -8342678015757438794L;
	
	public static final String is_request_getMyRooms_new = "Arfweb_is_request_getMyRooms_new:"; 

	public static final String is_request_getMyTenement_new = "Arfweb_is_request_getMyTenement_new:"; 
	
	public static final String is_request_getMyRooms_new_30_Pass = "Arfweb_is_request_getMyRooms_new_30_Pass:"; 
	public static final String is_request_getMyRooms_new_30_Refuse = "Arfweb_is_request_getMyRooms_new_30_Refuse:"; 
	public static final String is_request_getMyTenement_new_30_Pass = "Arfweb_is_request_getMyTenement_new_30_Pass:"; 
	public static final String is_request_getMyTenement_new_30_Refuse = "Arfweb_is_request_getMyTenement_new_30_Refuse:"; 
	
	public static final String is_request_getMyRooms_new_30 = "Arfweb_is_request_getMyRooms_new_30:"; 
	
	public static final String is_request_getMyRooms_new_30_householderAudit = "Arfweb_is_request_getMyRooms_new_30_householderAudit:"; 
	
	private String userName;//用户名    
	private String cardNum;//卡编号
	/**
	 * 	状态枚举:
		NORMAL 0 正常
		REPORTED_LOST 1 已挂失
		OVER_DUE 2 过期
	 */
	private Integer status;//
	private Date reportedLostDate;//挂失时间
	
	private String reportedLostUser;//挂失用户
	
	private String accessNum;//绑定的门禁编号
	private String communityNumber;//小区编号
	private Date boundDate;//绑定时间
	private Date endDate;//有效期
	private String chipNum;//芯片编号
	private String roomBoundNumber;//房间绑定编号,v2.2.4版本房屋下所有人能看到所有门禁卡,已修改为使用roomNumber来关联
	private CardType cardType = CardType.FREE;//门禁卡类型 FREE 免费卡 PAID 付费卡
	
	private ActivateType activateType;
	
	private String roomNumber;//房间编号
	
	private LostType lostType;//挂失类型
	
	private CardSource cardSource;//门禁卡来源
	
	private AuthencateType authencateType;//门禁卡认证类型 0、Internet 网络认证 1、Bluetooth 蓝牙认证
	
	private FreezeStatus freezeStatus;//冻结状态（0 Normal正常 1 Freezed已冻结）
	private Date updateDate;//更新设置时间
	private Date effectiveDate;//门禁卡设置生效时间
	private String effectiveMac;//生效门禁-MAC地址
	private Integer totalTimes;//可使用次数（-1不限 ）
	private String cardVersion;//门禁卡版本（V1旧版门禁卡 V2 新版门禁卡....）
	private Boolean isSend;//信息是否下发（0 (false)否 1 是(true)）
	private Integer laveTimes;//剩余次数
	private String receivedAccess;//信息已下发门禁[{bluetoothMac:"门禁mac地址",receivedDate:"下发时间"}]
	private String roomList;//关联房屋的编号,多个关联房屋用”,”分割
	private CardUseType cardUseType;//是否万能卡0 普通卡1 万能卡
	@Column(length=500)
	public String getRoomList() {
		return roomList;
	}
	public void setRoomList(String roomList) {
		this.roomList = roomList;
	}
	
	/**
	 *	是否万能卡
	 *	NORMAL 普通卡
	 *	UNIVERSAL 万能卡
	 */
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum CardUseType{
		NORMAL,
		UNIVERSAL;
	}
	
	

	public CardUseType getCardUseType() {
		return cardUseType;
	}
	public void setCardUseType(CardUseType cardUseType) {
		this.cardUseType = cardUseType;
	}

	/**
	 *	门禁卡类型
	 *	FREE 免费卡
	 *	PAID 付费卡
	 */
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum CardType{
		FREE,
		PAID;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ActivateType{
		Activate_Self_Service,
		Activate_Property_Office;
	}
	
	/**
	 *	NORMAL 0 正常
	 *	REPORTED_LOST 1 已挂失
	 *	OVER_DUE 2 过期
	 */
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		NORMAL,
		REPORTED_LOST,
		OVER_DUE;
	}
	
	/**
	 *	挂失类型
	 *	USER_LOST 用户挂失
	 *	PROPERTY_LOST 物业挂失
	 */
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum LostType{
		USER_LOST,
		PROPERTY_LOST;
	}
	
	/**
	 *	门禁卡来源
	 *	PROPERTY_BUY 物业购买
	 *	OLD_EXCHANGE_NEW 以旧换新
	 */
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum CardSource{
		PROPERTY_BUY,
		OLD_EXCHANGE_NEW;
	}
	
	/**
	 *	门禁卡认证类型 
	 *	Internet 网络认证
	 *	Bluetooth 蓝牙认证
	 */
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AuthencateType{
		Internet,
		Bluetooth;
	}
	
	/**
	 *	冻结状态（0 Normal正常 1 Freezed已冻结）
	 *	Internet 网络认证
	 *	Bluetooth 蓝牙认证
	 */
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum FreezeStatus{
		Normal,
		Freezed;
	}
	@Column(nullable = false)
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	@Column(length=20)
	public String getUserName() {
		return userName;
	}
	@Column(length=20)
	public String getCardNum() {
		return cardNum;
	}
	public Integer getStatus() {
		return status;
	}
	public Date getReportedLostDate() {
		return reportedLostDate;
	}
	@Column(length=10)
	public String getAccessNum() {
		return accessNum;
	}
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public Date getBoundDate() {
		return boundDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	@Column(length=20)
	public String getChipNum() {
		return chipNum;
	}
	/**
	 * v2.2.4版本房屋下所有人能看到所有门禁卡,已修改为使用roomNumber来关联
	 * @return
	 */
	@Column(length=40)
	public String getRoomBoundNumber() {
		return roomBoundNumber;
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
	public void setReportedLostDate(Date reportedLostDate) {
		this.reportedLostDate = reportedLostDate;
	}
	public void setAccessNum(String accessNum) {
		this.accessNum = accessNum;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setBoundDate(Date boundDate) {
		this.boundDate = boundDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setChipNum(String chipNum) {
		this.chipNum = chipNum;
	}
	/**
	 * v2.2.4版本房屋下所有人能看到所有门禁卡,已修改为使用roomNumber来关联,但该字段依然保留
	 * @param roomBoundNumber
	 */
	public void setRoomBoundNumber(String roomBoundNumber) {
		this.roomBoundNumber = roomBoundNumber;
	}
	public ActivateType getActivateType() {
		return activateType;
	}
	public void setActivateType(ActivateType activateType) {
		this.activateType = activateType;
	}
	@Column(length = 40)
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public LostType getLostType() {
		return lostType;
	}
	public void setLostType(LostType lostType) {
		this.lostType = lostType;
	}
	public CardSource getCardSource() {
		return cardSource;
	}
	public void setCardSource(CardSource cardSource) {
		this.cardSource = cardSource;
	}
	@Column(length = 20)
	public String getReportedLostUser() {
		return reportedLostUser;
	}
	public void setReportedLostUser(String reportedLostUser) {
		this.reportedLostUser = reportedLostUser;
	}
	public AuthencateType getAuthencateType() {
		return authencateType;
	}
	public void setAuthencateType(AuthencateType authencateType) {
		this.authencateType = authencateType;
	}
	public FreezeStatus getFreezeStatus() {
		return freezeStatus;
	}
	public void setFreezeStatus(FreezeStatus freezeStatus) {
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
	@Column(length=20)
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
	@Column(length=10)
	public String getCardVersion() {
		return cardVersion;
	}
	public void setCardVersion(String cardVersion) {
		this.cardVersion = cardVersion;
	}
	public Boolean getIsSend() {
		return isSend;
	}
	public void setIsSend(Boolean isSend) {
		this.isSend = isSend;
	}
	public Integer getLaveTimes() {
		return laveTimes;
	}
	public void setLaveTimes(Integer laveTimes) {
		this.laveTimes = laveTimes;
	}
	@Column(length=400)
	public String getReceivedAccess() {
		return receivedAccess;
	}
	public void setReceivedAccess(String receivedAccess) {
		this.receivedAccess = receivedAccess;
	}
}
