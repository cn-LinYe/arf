package com.arf.eparking.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
@Entity
@Table(name = "p_parkinginfo")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_parkinginfo_sequence")
public class ParkingInfo extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	停车场编号、唯一
	private String parkingNo;
//	停车场名
	private String parkingName;
//	公司完整名：申办单位；关联停车场用户表中的公司名称
	private String companyFullName;
//	停车场所在地址
	private String address;
//	单位法人
	private String legalPerson;
//	单位法人身份证号码
	private String legalId;
//	法人手机号码
	private String legalMobile;
//	法人电话
	private String legalTelephone;
//	法人Email
	private String legalEmail;
//	停车场责任人
	private String managerName;
//	停车场责任人身份证号码
	private String managerId;
//	停车场责任人手机号码
	private String mobile;
//	停车场责任人电话
	private String telephone;
//	停车场责任人Email
	private String email;
	
	/*社会公共类：00宾馆、酒家,01商业大厦,02写字楼,03专业场,04工业仓库、物流园区,05码头、口岸、车站,06旅游景点,07医院、博物馆、图书馆；
	住宅类：10多高层住宅区，11混合型住宅区；
	临时类：20临时场地，21其它场地，22路内场地*/
	private String subType;
	
//	准停车型:多个以逗号分开
//	01大型车,02中型车,03小型车,04微型车
	private String quasiCarType;
//	停车场运营商登录用户id
	private Integer parkingUserId;
//	对应于区域表的街道ID
	private Integer areaId;
//	总车位数量
	private Integer berth;
//	机动车车位数
	private Integer motorVehicleLotsNum;
//	非机动车车位数
	private Integer nonMotorVehicleLotsNum;
//	露天车位数量
	private Integer outDoorBerth;
//	室内车位数量
	private Integer insideBerth;
//	立体机械车位数量
	private Integer solidBerth;
//	总内部使用的泊位数量(私有泊位数)
	private Integer internalBerth;
//	总可预定的车位数
	private Integer scheduledTotal;
//	结构形式：0露天，1室内，2露天、室内混合，3路边
	private Integer structType;
//	土地性质：0国有 、1非国有，部分国有
	private Integer quality;
//	开办主体：0政府投资办(国有资产投办)，1企、事业自办，2合办，3租用场地办
	private Integer offerSubject;
	
//	收费方式：0自动、1人工、2混合
	private Integer feeMode;
//	类别：0社会公共类、1住宅类、2临时类
	private Integer classType;
//	停车场性质：0永久场；1临时场
	private Integer nature;
//	状态:-1:已删除：0:新建（草稿）状态；1:运营中；
	private Integer parkingState;
//	停车场等级：0、1、2、3、4
	private Integer parkingLevel;
	
//	停车场所处经度
	private Double longitude;
//	停车场所处纬度
	private Double latiude;
//	停车场面积(平方米)
	private Float  square;
	//物业编号 
	private Integer propertyNumber;
	//子公司编号 
	private Integer branchId;
	//新建停车场要新增小区，停车场需要知道摄像机厂家
	/**
	 * 执行方法
	 * 0默认执行JNI
	 * 1：socket
	 * 2：可以改成执行蓝鹏
	 */
	private Integer executeMethod;
	
	private String parkingPicUrl;//停车场图片
	
	/**
	 * 所在商圈
	 * 多个使用【英文逗号分隔】
	 */
	private String businessArea;
	
	private Integer fieldType;//场中场类型 0场外 1场内
	
	private String superParkingId;//场外停车场编码
	
	@Column(length = 11)
	public Integer getFieldType() {
		return fieldType;
	}
	@Column(length = 20)
	public String getSuperParkingId() {
		return superParkingId;
	}
	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}
	public void setSuperParkingId(String superParkingId) {
		this.superParkingId = superParkingId;
	}
	@Column(length = 2000)
	public String getParkingPicUrl() {
		return parkingPicUrl;
	}
	public void setParkingPicUrl(String parkingPicUrl) {
		this.parkingPicUrl = parkingPicUrl;
	}
	public Integer getExecuteMethod() {
		return executeMethod;
	}
	public void setExecuteMethod(Integer executeMethod) {
		this.executeMethod = executeMethod;
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
	@Column(length = 12)
	public String getParkingNo() {
		return parkingNo;
	}
	public void setParkingNo(String parkingNo) {
		this.parkingNo = parkingNo;
	}
	@Column(length = 100)
	public String getParkingName() {
		return parkingName;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	
	@Column(length = 50)
	public String getCompanyFullName() {
		return companyFullName;
	}
	public void setCompanyFullName(String companyFullName) {
		this.companyFullName = companyFullName;
	}
	
	@Column(length = 200)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(length = 50)
	public String getLegalPerson() {
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	@Column(length = 20)
	public String getLegalId() {
		return legalId;
	}
	public void setLegalId(String legalId) {
		this.legalId = legalId;
	}
	@Column(length = 20)
	public String getLegalMobile() {
		return legalMobile;
	}
	public void setLegalMobile(String legalMobile) {
		this.legalMobile = legalMobile;
	}
	@Column(length = 30)
	public String getLegalTelephone() {
		return legalTelephone;
	}
	public void setLegalTelephone(String legalTelephone) {
		this.legalTelephone = legalTelephone;
	}
	@Column(length = 50)
	public String getLegalEmail() {
		return legalEmail;
	}
	public void setLegalEmail(String legalEmail) {
		this.legalEmail = legalEmail;
	}
	@Column(length = 50)
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	@Column(length = 20)
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	@Column(length = 20)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(length = 20)
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Column(length = 20)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(length = 20)
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	@Column(length = 50)
	public String getQuasiCarType() {
		return quasiCarType;
	}
	public void setQuasiCarType(String quasiCarType) {
		this.quasiCarType = quasiCarType;
	}
	
	public Integer getParkingUserId() {
		return parkingUserId;
	}
	public void setParkingUserId(Integer parkingUserId) {
		this.parkingUserId = parkingUserId;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public Integer getBerth() {
		return berth;
	}
	public void setBerth(Integer berth) {
		this.berth = berth;
	}
	public Integer getMotorVehicleLotsNum() {
		return motorVehicleLotsNum;
	}
	public void setMotorVehicleLotsNum(Integer motorVehicleLotsNum) {
		this.motorVehicleLotsNum = motorVehicleLotsNum;
	}
	public Integer getNonMotorVehicleLotsNum() {
		return nonMotorVehicleLotsNum;
	}
	public void setNonMotorVehicleLotsNum(Integer nonMotorVehicleLotsNum) {
		this.nonMotorVehicleLotsNum = nonMotorVehicleLotsNum;
	}
	public Integer getOutDoorBerth() {
		return outDoorBerth;
	}
	public void setOutDoorBerth(Integer outDoorBerth) {
		this.outDoorBerth = outDoorBerth;
	}
	public Integer getInsideBerth() {
		return insideBerth;
	}
	public void setInsideBerth(Integer insideBerth) {
		this.insideBerth = insideBerth;
	}
	public Integer getSolidBerth() {
		return solidBerth;
	}
	public void setSolidBerth(Integer solidBerth) {
		this.solidBerth = solidBerth;
	}
	public Integer getInternalBerth() {
		return internalBerth;
	}
	public void setInternalBerth(Integer internalBerth) {
		this.internalBerth = internalBerth;
	}
	public Integer getScheduledTotal() {
		return scheduledTotal;
	}
	public void setScheduledTotal(Integer scheduledTotal) {
		this.scheduledTotal = scheduledTotal;
	}
	public Integer getStructType() {
		return structType;
	}
	public void setStructType(Integer structType) {
		this.structType = structType;
	}
	public Integer getQuality() {
		return quality;
	}
	public void setQuality(Integer quality) {
		this.quality = quality;
	}
	public Integer getOfferSubject() {
		return offerSubject;
	}
	public void setOfferSubject(Integer offerSubject) {
		this.offerSubject = offerSubject;
	}
	public Integer getFeeMode() {
		return feeMode;
	}
	public void setFeeMode(Integer feeMode) {
		this.feeMode = feeMode;
	}
	public Integer getClassType() {
		return classType;
	}
	public void setClassType(Integer classType) {
		this.classType = classType;
	}
	public Integer getNature() {
		return nature;
	}
	public void setNature(Integer nature) {
		this.nature = nature;
	}
	public Integer getParkingState() {
		return parkingState;
	}
	public void setParkingState(Integer parkingState) {
		this.parkingState = parkingState;
	}
	public Integer getParkingLevel() {
		return parkingLevel;
	}
	public void setParkingLevel(Integer parkingLevel) {
		this.parkingLevel = parkingLevel;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatiude() {
		return latiude;
	}
	public void setLatiude(Double latiude) {
		this.latiude = latiude;
	}
	public Float getSquare() {
		return square;
	}
	public void setSquare(Float square) {
		this.square = square;
	}
	
	@Column(length=256)
	public String getBusinessArea() {
		return businessArea;
	}
	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}
}
