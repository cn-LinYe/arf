/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.entity;


import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * Entity - 用户物业信息记录表
 * 
 * @author arf
 * @version 4.0
 */
@Entity
@Table(name = "user_proprety_inforRecord")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "user_proprety_inforRecord_sequence")
public class UserPropretyInforRecord extends OrderEntity<Long> {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 291033766887357093L;

	/** 用户名*/
	private String userName;
	/** 小区号*/
	private String communityNumber;
	/** 小区名*/
	private String communityName;
	/** 楼栋名称*/
	private String buildingNumber;
	/** 单元号*/
	private String unitNumber;
	/** 楼层数 */
	private String floorNumber;
	/**房间号*/
	private String houseNmuber;
	/** 房间id*/
	private String houseId;
	//物业编号 
	private Integer propertyNumber;
	//子公司编号 
	private Integer branchId;
	/**  
	 * 获取楼栋名称  
	 * @return buildingNumber 楼栋名称  
	 */
	public String getBuildingNumber() {
		return buildingNumber;
	}
	


	/**  
	 * 设置楼栋名称  
	 * @param buildingNumber 楼栋名称  
	 */
	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}
	


	/**  
	 * 获取单元号  
	 * @return unitNumber 单元号  
	 */
	public String getUnitNumber() {
		return unitNumber;
	}
	


	/**  
	 * 设置单元号  
	 * @param unitNumber 单元号  
	 */
	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}
	


	/**  
	 * 获取楼层数  
	 * @return floorNumber 楼层数  
	 */
	public String getFloorNumber() {
		return floorNumber;
	}
	


	/**  
	 * 设置楼层数  
	 * @param floorNumber 楼层数  
	 */
	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber;
	}
	


	/**  
	 * 获取房间号  
	 * @return houseNmuber 房间号  
	 */
	public String getHouseNmuber() {
		return houseNmuber;
	}
	


	/**  
	 * 设置房间号  
	 * @param houseNmuber 房间号  
	 */
	public void setHouseNmuber(String houseNmuber) {
		this.houseNmuber = houseNmuber;
	}
	


	/**  
	 * 获取小区名  
	 * @return communityName 小区名  
	 */
	public String getCommunityName() {
		return communityName;
	}
	

	/**  
	 * 设置小区名  
	 * @param communityName 小区名  
	 */
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	

	
	
	/**  
	 * 获取用户名  
	 * @return userName 用户名  
	 */
	public String getUserName() {
		return userName;
	}
	
	/**  
	 * 设置用户名  
	 * @param userName 用户名  
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**  
	 * 获取小区号  
	 * @return communityNumber 小区号  
	 */
	public String getCommunityNumber() {
		return communityNumber;
	}
	
	/**  
	 * 设置小区号  
	 * @param communityNumber 小区号  
	 */
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	
	/**  
	 * 获取房间id  
	 * @return houseId 房间id  
	 */
	public String getHouseId() {
		return houseId;
	}
	
	/**  
	 * 设置房间id  
	 * @param houseId 房间id  
	 */
	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}



	public Integer getPropertyNumber() {
		return propertyNumber;
	}



	public void setPropertyNumber(Integer propertyNumber) {
		this.propertyNumber = propertyNumber;
	}



	public Integer getBranchId() {
		return branchId;
	}



	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	


}