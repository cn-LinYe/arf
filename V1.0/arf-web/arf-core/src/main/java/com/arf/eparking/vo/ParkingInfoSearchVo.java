package com.arf.eparking.vo;

public class ParkingInfoSearchVo{
	private Integer pageSize;
	private Integer pageNo;
	private String keyWords;//关键字
	private Double longitude;//经度
	private Double latiude;//纬度
	private Integer searchDistance;//所搜范围
	private Integer branchId;//用户绑定小区所属子公司
	/**
	 * 停车场类型 0全部 1医院
	 * add 2016-09-12 17:00:00
	 */
	private Integer subType;//停车场类型 0全部 1医院
	
	public Integer getPageSize() {
		return pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public Double getLongitude() {
		return longitude;
	}
	public Double getLatiude() {
		return latiude;
	}
	public Integer getSearchDistance() {
		return searchDistance;
	}
	public Integer getSubType() {
		return subType;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public void setLatiude(Double latiude) {
		this.latiude = latiude;
	}
	public void setSearchDistance(Integer searchDistance) {
		this.searchDistance = searchDistance;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public void setSubType(Integer subType) {
		this.subType = subType;
	}
}
