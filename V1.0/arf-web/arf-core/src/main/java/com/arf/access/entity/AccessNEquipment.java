package com.arf.access.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_n_equipment")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_n_equipment_sequence")
public class AccessNEquipment  extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6836243660345687004L;
	
	private String communityNumber	;//varchar(32)		小区编号	
	private String midCommunityNumber;//设备小区编号（新门禁）
	private Integer communityBuildingId;//关联楼栋单元
	private String accessName	;//门禁名称	
	private String accessNumber;//门禁设备编号
	private Integer equipmentType;//设备类型枚举 0 Wall 墙围机 1 Unit 单元机
	private String equipmentName	;//设备名称	
	private String equipmentVersion;//设备版本
	private String equipmentMac;//设备MAC地址
	private Integer equipmentStatus	;//设备状态（-1未初始化，0正常）
	private String buildingName; //楼栋门禁对应的大楼名
	private String unitName; //楼栋门禁对应的单元名
	private Integer building; //楼栋门禁对应的楼栋编号,后台自动生成
	private Integer unit; //楼栋门禁对应的单元编号,后台自动生成
	private String qrcodeUrl; //门禁二维码
	/**
		门禁用途枚举:
		IN 0 进
		OUT 1 出
	 */
	private UseType useType;
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum UseType{
		IN,
		OUT;
	}
	
	@Column(length=32)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(length=32)
	public String getMidCommunityNumber() {
		return midCommunityNumber;
	}
	public void setMidCommunityNumber(String midCommunityNumber) {
		this.midCommunityNumber = midCommunityNumber;
	}
	public Integer getCommunityBuildingId() {
		return communityBuildingId;
	}
	public void setCommunityBuildingId(Integer communityBuildingId) {
		this.communityBuildingId = communityBuildingId;
	}
	@Column(length=30)
	public String getAccessName() {
		return accessName;
	}
	public void setAccessName(String accessName) {
		this.accessName = accessName;
	}
	@Column(length=32)
	public String getAccessNumber() {
		return accessNumber;
	}
	public void setAccessNumber(String accessNumber) {
		this.accessNumber = accessNumber;
	}
	public Integer getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}
	@Column(length=32)
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public String getEquipmentVersion() {
		return equipmentVersion;
	}
	public void setEquipmentVersion(String equipmentVersion) {
		this.equipmentVersion = equipmentVersion;
	}
	@Column(length=32)
	public String getEquipmentMac() {
		return equipmentMac;
	}
	public void setEquipmentMac(String equipmentMac) {
		this.equipmentMac = equipmentMac;
	}
	public Integer getEquipmentStatus() {
		return equipmentStatus;
	}
	public void setEquipmentStatus(Integer equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}
	@Column(length=32)
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	@Column(length=32)
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Integer getBuilding() {
		return building;
	}
	public void setBuilding(Integer building) {
		this.building = building;
	}
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	public UseType getUseType() {
		return useType;
	}
	public void setUseType(UseType useType) {
		this.useType = useType;
	}
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

}
