package com.arf.platform.vo;

public class DownCreateOrderVo extends DownRequestDataVo {

	private String version;//接口版本号
	private String oderSn;//订单编号
	private String berthNo;//泊位号
	private String license;//车牌
	private String userTel;//手机号码
	private String OrderTime;//下单时间
	private String arriveTime;//预计到达时间
	private String waitComeTime;//等待车主到达延长时间
	private String leaveTime;//预计离开时间
	private String userName;//车主用户
	private String isPayAgent;//是否代付
	private Integer parkingType;//停车场类型 0小区 1 紧急场所
	
	public Integer getParkingType() {
		return parkingType;
	}
	public void setParkingType(Integer parkingType) {
		this.parkingType = parkingType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getOderSn() {
		return oderSn;
	}
	public void setOderSn(String oderSn) {
		this.oderSn = oderSn;
	}
	public String getBerthNo() {
		return berthNo;
	}
	public void setBerthNo(String berthNo) {
		this.berthNo = berthNo;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getOrderTime() {
		return OrderTime;
	}
	public void setOrderTime(String orderTime) {
		OrderTime = orderTime;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getWaitComeTime() {
		return waitComeTime;
	}
	public void setWaitComeTime(String waitComeTime) {
		this.waitComeTime = waitComeTime;
	}
	public String getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIsPayAgent() {
		return isPayAgent;
	}
	public void setIsPayAgent(String isPayAgent) {
		this.isPayAgent = isPayAgent;
	}
	
}
