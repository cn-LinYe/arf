package com.arf.carbright.condition;

import org.hibernate.search.spatial.impl.Point;

import com.arf.carbright.entity.PBusiness;

public class BusinessCondition extends PBusiness{
	private static final long serialVersionUID = -972807970044091229L;
	private Point location;
	private int searchRadius;//位置搜索半径
	private String keyword;
	private String serviceRange;//商户服务范围
	private Integer pageSize;
	private Integer pageNo;
	
	public Point getLocation() {
		return location;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getServiceRange() {
		return serviceRange;
	}
	public void setServiceRange(String serviceRange) {
		this.serviceRange = serviceRange;
	}
	public int getSearchRadius() {
		return searchRadius;
	}
	public void setSearchRadius(int searchRadius) {
		this.searchRadius = searchRadius;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
}
