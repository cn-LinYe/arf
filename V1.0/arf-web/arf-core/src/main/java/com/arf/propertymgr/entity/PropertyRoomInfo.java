package com.arf.propertymgr.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "pty_property_room_info",uniqueConstraints = {
		@UniqueConstraint(columnNames = { "communityNumber","building","unit","floor","room" }, name = "unique_property_room_info") ,
		@UniqueConstraint(columnNames = { "roomNumber" }, name = "unique_room_number") })
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "pty_property_room_info_sequence")
public class PropertyRoomInfo extends BaseEntity<Long> {

	private static final long serialVersionUID = 1733810804812300362L;
	
	public static final String PROPERTYMGR = "PROPERTYMGR";//redis配置参数父节点
	public static final String COMMUNITY_SEARCH_RANGE = "COMMUNITY_SEARCH_RANGE";//物业缴费选择小区时，根据经纬度搜索小区的范围（单位：km）
	
	private String communityNumber;//小区编号
	private String building;//栋
	private String unit;//单元（没有赋值为“无”）
	private String floor;//楼层
	private String room;//房号
	private String roomIndex;//房号int编号,纯数字编号,统一楼栋单元下的房间信息从0001递增直到9999
	private BigDecimal acreage;//物业面积
	/**
	 * 计费周期枚举:<br/>
	 * 0:MONTH 月<br/>
	 * 1:QUARTER 季度<br/>
	 * 2:YEAR 年
	 */
	private byte billingPeriod;
	private BigDecimal pricePerM2;//每平米单价
	/**
	 * 计费类型枚举:<br/>
	 * 0:ACCORD_ACREAGE 按照面积 如果是按照面积则price_per_m2不可空<br/>
	 * 1:FIXED_PRICE 一口价
	 */
	private byte chargeType;
	/**
	 * 
<pre>编号规则为:
int hash = (building
       + unit
       + floor
       + room).hashCode();
hash<0 hash取正值左补0
room_number = community_number + hash</pre>
	 */
	private String roomNumber;//房间唯一编号
	private Date endTime;//最后一个账单时间//标识账单生成日期
	private Date pryEndTime;//最后缴费截止日期（标识缴费截止日期）
	
	private RoomType roomType;
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum RoomType{
		HOUSEHOLD,SHOPS
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum BillingPeriod{
		MONTH,
		QUARTER,
		YEAR;
		
		public static BillingPeriod get(int ordinal){
			BillingPeriod billingPeriod[] = BillingPeriod.values();
			if(ordinal<0 || ordinal>billingPeriod.length-1){
				return null;
			}else{
				return billingPeriod[ordinal];
			}
		}
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ChargeType{
		ACCORD_ACREAGE,
		FIXED_PRICE;
		
		public static ChargeType get(int ordinal){
			ChargeType chargeType[] = ChargeType.values();
			if(ordinal<0 || ordinal>chargeType.length-1){
				return null;
			}else{
				return chargeType[ordinal];
			}
		}
	}
	
	
	
	public Date getPryEndTime() {
		return pryEndTime;
	}
	public void setPryEndTime(Date pryEndTime) {
		this.pryEndTime = pryEndTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public RoomType getRoomType() {
		return roomType;
	}
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	@Column(length = 20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	@Column(length = 20)
	public String getBuilding() {
		return building;
	}
	@Column(length = 20)
	public String getUnit() {
		return unit;
	}
	@Column(length = 20)
	public String getFloor() {
		return floor;
	}
	@Column(length = 20)
	public String getRoom() {
		return room;
	}
	@Column(precision = 10, scale = 2)
	public BigDecimal getAcreage() {
		return acreage;
	}
	@Column(nullable = false)
	public byte getBillingPeriod() {
		return billingPeriod;
	}
	@Column(precision = 10, scale = 2)
	public BigDecimal getPricePerM2() {
		return pricePerM2;
	}
	@Column(nullable = false)
	public byte getChargeType() {
		return chargeType;
	}
	@Column(length = 40)
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public void setAcreage(BigDecimal acreage) {
		this.acreage = acreage;
	}
	public void setBillingPeriod(byte billingPeriod) {
		this.billingPeriod = billingPeriod;
	}
	public void setPricePerM2(BigDecimal pricePerM2) {
		this.pricePerM2 = pricePerM2;
	}
	public void setChargeType(byte chargeType) {
		this.chargeType = chargeType;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	@Column(length = 5,nullable = false)
	public String getRoomIndex() {
		return roomIndex;
	}
	public void setRoomIndex(String roomIndex) {
		this.roomIndex = roomIndex;
	}
	/**
	 * 
<pre>编号规则为:
int hash = (building
       + unit
       + floor
       + room).hashCode();
hash<0 hash取正值左补0
room_number = community_number + hash</pre>
	 */
	public static String genRoomNumber(String communityNumber,String building,String unit,String floor,String room){
		int hash = (building + unit + floor + room).hashCode();
		if(hash < 0){
			hash = 0 - hash;
			return communityNumber + "0" + hash;
		}else{
			return communityNumber + hash;
		}
	}
	
}
