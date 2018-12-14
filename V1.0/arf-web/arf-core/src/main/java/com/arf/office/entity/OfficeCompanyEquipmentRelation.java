package com.arf.office.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "office_company_equipment_relation")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "office_company_equipment_relation_sequence")
public class OfficeCompanyEquipmentRelation extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 818235978733905821L;
	
	private String bluetoothMac;//蓝牙Mac地址
	private String companyNumber;//公司编号
	private String building;//楼栋
	private String unit;//单元
	private String floor;//楼层
	private String room;//房号
	private String roomNumber;//房屋编号
	private Long accessId;//门禁设备id
	
	@Column(length = 32)
	public String getBluetoothMac() {
		return bluetoothMac;
	}
	public void setBluetoothMac(String bluetoothMac) {
		this.bluetoothMac = bluetoothMac;
	}
	@Column(length = 20)
	public String getCompanyNumber() {
		return companyNumber;
	}
	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	@Column(length = 20)
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	@Column(length = 20)
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(length = 20)
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	@Column(length = 20)
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	@Column(length = 40)
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public Long getAccessId() {
		return accessId;
	}
	public void setAccessId(Long accessId) {
		this.accessId = accessId;
	}

}
