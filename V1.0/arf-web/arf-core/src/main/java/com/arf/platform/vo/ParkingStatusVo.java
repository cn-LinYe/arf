package com.arf.platform.vo;

public class ParkingStatusVo extends RequestDataVo {

	//业务数据
	private Integer total;//总车位数
	private Integer empty;//总空车位数
	private Integer sheduled;//总可预定车位数,如无可预定车位用0表示
	private Integer rscheduled;//空可预定车位,如无空可预定车位用0表示
	private Integer internal;//内部总车位数量,如车位不分内外车位填0
	private Integer rinternal;//内部空车位数量,如车位不分内外车位填0
	private Integer status;//车位使用状态 0充足；1紧张；2已满
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getEmpty() {
		return empty;
	}
	public void setEmpty(Integer empty) {
		this.empty = empty;
	}
	public Integer getSheduled() {
		return sheduled;
	}
	public void setSheduled(Integer sheduled) {
		this.sheduled = sheduled;
	}
	public Integer getRscheduled() {
		return rscheduled;
	}
	public void setRscheduled(Integer rscheduled) {
		this.rscheduled = rscheduled;
	}
	public Integer getInternal() {
		return internal;
	}
	public void setInternal(Integer internal) {
		this.internal = internal;
	}
	public Integer getRinternal() {
		return rinternal;
	}
	public void setRinternal(Integer rinternal) {
		this.rinternal = rinternal;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ParkingStatusVo [total=" + total + ", empty=" + empty
				+ ", sheduled=" + sheduled + ", rscheduled=" + rscheduled
				+ ", internal=" + internal + ", rinternal=" + rinternal
				+ ", status=" + status + ", getVer()=" + getVer()
				+ ", getCommunityNo()=" + getCommunityNo()
				+ ", getParkingType()=" + getParkingType() + "]";
	}
		
}
