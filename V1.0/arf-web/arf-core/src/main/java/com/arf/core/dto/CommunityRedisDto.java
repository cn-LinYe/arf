package com.arf.core.dto;

import java.io.Serializable;

import com.arf.core.entity.CommunityModel.ParkingType;

public class CommunityRedisDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1534488809225956734L;
	
	private int no;// 省市区外键
	private Integer provinceno = 0;//省
	private Integer cityno     = 0;//市
	private Integer areano     = 0;//区
	
	private String communityName;// 小区名
	private String community_number;// 唯一标识id
	private String communityAddress = "";//地址
	/**
	 * 执行方法
	 * 0：默认执行JNI
	 * 1：socket
	 * 2：可以改成执行蓝鹏
	 */
	private Integer executeMethod=0;
	 /**
     * 类型
     */
    private ParkingType parkingType = ParkingType.Community;
    private String propertyOfficePhone;//物业管理处电话
    private Double lat;// 纬度
	private Double lng;// 经度
	private Integer propertyNumber;// 物业编号
	
	public Integer getPropertyNumber() {
		return propertyNumber;
	}
	public void setPropertyNumber(Integer propertyNumber) {
		this.propertyNumber = propertyNumber;
	}
	public int getNo() {
		return no;
	}
	public Integer getProvinceno() {
		return provinceno;
	}
	public Integer getCityno() {
		return cityno;
	}
	public Integer getAreano() {
		return areano;
	}
	public String getCommunityName() {
		return communityName;
	}
	public String getCommunity_number() {
		return community_number;
	}
	public String getCommunityAddress() {
		return communityAddress;
	}
	public Integer getExecuteMethod() {
		return executeMethod;
	}
	public ParkingType getParkingType() {
		return parkingType;
	}
	public String getPropertyOfficePhone() {
		return propertyOfficePhone;
	}
	public Double getLat() {
		return lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public void setProvinceno(Integer provinceno) {
		this.provinceno = provinceno;
	}
	public void setCityno(Integer cityno) {
		this.cityno = cityno;
	}
	public void setAreano(Integer areano) {
		this.areano = areano;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public void setCommunity_number(String community_number) {
		this.community_number = community_number;
	}
	public void setCommunityAddress(String communityAddress) {
		this.communityAddress = communityAddress;
	}
	public void setExecuteMethod(Integer executeMethod) {
		this.executeMethod = executeMethod;
	}
	public void setParkingType(ParkingType parkingType) {
		this.parkingType = parkingType;
	}
	public void setPropertyOfficePhone(String propertyOfficePhone) {
		this.propertyOfficePhone = propertyOfficePhone;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
}
