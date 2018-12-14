package com.arf.plugin.vo;

public class BolinKadVo {

	private String unionId;//厂商编号
	private String userId;	//厂商账号
	private String parkId;	//车场id
	private String parkName;	//车场名称
	private Integer parkType;//车场类型(1:商超。2:办公。3:住宅。4:医院。5:酒店。6:交通枢纽。7:事业单位。)
	private Integer carType;		//汽车类型(1:汽油车。2:新能源车。)
	private String address;	//车场地址	
	private String cityCode;	//车场城市id
	private Double lon;	//经度
	private Double lat;	//纬度
	private Integer adPosId;		//广告位id广告位id(1:支付成功页底部广告。2:粉丝关注广告。3:车辆入场推送广告。4:小程序首页banner。
	private String plateNumber;	//车牌号
	private String clientId;		//用户id，写入用户里的随机cookieid，用来计算uv
	private String orderNo;		//订单id，能反查出来订单号，如果没有就不写
	private Integer businessType;	//业务类型 0 月租停车 1 临时停车
	private boolean addPv = false;	//是否写一条pv
	private String communityNumber;//小区编号
	
	
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	public Integer getCarType() {
		return carType;
	}
	public void setCarType(Integer carType) {
		this.carType = carType;
	}
	public Integer getAdPosId() {
		return adPosId;
	}
	public void setAdPosId(Integer adPosId) {
		this.adPosId = adPosId;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public boolean isAddPv() {
		return addPv;
	}
	public boolean getAddPv() {
		return addPv;
	}
	public void setAddPv(boolean addPv) {
		this.addPv = addPv;
	}
	public String getUnionId() {
		return unionId;
	}
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getParkId() {
		return parkId;
	}
	public void setParkId(String parkId) {
		this.parkId = parkId;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public Integer getParkType() {
		return parkType;
	}
	public void setParkType(Integer parkType) {
		this.parkType = parkType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
}
