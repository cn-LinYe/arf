package com.arf.propertymgr.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "property_office")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "property_office")
public class PropertyOffice extends BaseEntity<Long> {

	private static final long serialVersionUID = 1733810804812300362L;
	
	private String communityNumber;//小区编号
	private String building;//栋
	private String unit;//单元（没有赋值为“无”）
	private String floor;//楼层
	private String room;//房号
	private String room_name;//房间名
	private String room_number;//房间编号
	@Column(length = 20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(length = 10)
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	@Column(length = 10)
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(length = 10)
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	@Column(length = 10)
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	@Column(length = 10)
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	@Column(length = 10)
	public String getRoom_number() {
		return room_number;
	}
	public void setRoom_number(String room_number) {
		this.room_number = room_number;
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
