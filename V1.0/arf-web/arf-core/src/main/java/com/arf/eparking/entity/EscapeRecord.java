package com.arf.eparking.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_escape_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_escape_record_sequence")
public class EscapeRecord extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	public enum Flag{
		escape,arrearages;
	}
	
	public enum RecoveryStatus{
		no_recovery,recoveryed;
	}
	
	public enum RecoveryMode{
		client,system,cash,goldcard
	}

	private Byte flag;//类型:0逃费,1欠费	
	private String license;//车牌号码	
	private String userName;//业主电话/用户名
	private String parkingId;//停车场id	
	private String parkingName;//停车场名	
	private Integer berthId;//车位id
	private String berthNo;//车位编号	
	private Integer areaId;//区域id
	private Date arriveTime;//来车时间	
	private Date leaveTime;//走车时间	
	private String url;//取证图片的url
	private BigDecimal fee;//消费金额(单位元)
	private BigDecimal escapeFee;//逃欠费金额(单位元)
	private BigDecimal realPay;//实缴金额(单位元)	
	private Date time;//记录的生成时间	
	private Byte recoveryStatus;//追缴状态: 0未追缴,1已追缴	
	private Date clearTime;//追缴时间	
	private Byte  recoveryMode;//追缴方式:0客户端追缴,1系统追缴，默认0客户端追缴 2现金追缴
	private Integer branchId;//子公司或物业特有信息子公司唯一标识id
	private Integer headOfficeId;//总公司id	
	private Integer popertyNumber;//物业唯一标识id	
	
	private BigDecimal recoveryFee;//追缴金额(单位元)
	
	@Column(name = "flag",length = 4)
	public Byte getFlag() {
		return flag;
	}
	@Column(name = "license",length = 20)
	public String getLicense() {
		return license;
	}
	@Column(name = "user_name",length = 32)
	public String getUserName() {
		return userName;
	}
	@Column(name = "parking_id",length = 20)
	public String getParkingId() {
		return parkingId;
	}
	@Column(name = "parking_name",length = 50)
	public String getParkingName() {
		return parkingName;
	}
	@Column(name = "berth_id",length = 11)
	public Integer getBerthId() {
		return berthId;
	}
	@Column(name = "berth_no",length = 20)
	public String getBerthNo() {
		return berthNo;
	}
	@Column(name = "area_id",length = 11)
	public Integer getAreaId() {
		return areaId;
	}
	@Column(name = "arrive_time")
	public Date getArriveTime() {
		return arriveTime;
	}
	@Column(name = "leave_time")
	public Date getLeaveTime() {
		return leaveTime;
	}
	@Column(name = "url",length = 500)
	public String getUrl() {
		return url;
	}
	@Column(name = "fee",precision=10,scale=2)
	public BigDecimal getFee() {
		return fee;
	}
	@Column(name = "escape_fee",precision=10,scale=2)
	public BigDecimal getEscapeFee() {
		return escapeFee;
	}
	@Column(name = "real_pay",precision=10,scale=2)
	public BigDecimal getRealPay() {
		return realPay;
	}
	@Column(name = "time")
	public Date getTime() {
		return time;
	}
	@Column(name = "recovery_status",length = 4)
	public Byte getRecoveryStatus() {
		return recoveryStatus;
	}
	@Column(name = "clear_time")
	public Date getClearTime() {
		return clearTime;
	}
	@Column(name = "recovery_mode",length = 4)
	public Byte getRecoveryMode() {
		return recoveryMode;
	}
	@Column(name = "branch_id",length = 11)
	public Integer getBranchId() {
		return branchId;
	}
	@Column(name = "head_office_id",length = 11)
	public Integer getHeadOfficeId() {
		return headOfficeId;
	}
	@Column(name = "poperty_number",length = 11)
	public Integer getPopertyNumber() {
		return popertyNumber;
	}
	public void setFlag(Byte flag) {
		this.flag = flag;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	public void setBerthId(Integer berthId) {
		this.berthId = berthId;
	}
	public void setBerthNo(String berthNo) {
		this.berthNo = berthNo;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public void setEscapeFee(BigDecimal escapeFee) {
		this.escapeFee = escapeFee;
	}
	public void setRealPay(BigDecimal realPay) {
		this.realPay = realPay;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public void setRecoveryStatus(Byte recoveryStatus) {
		this.recoveryStatus = recoveryStatus;
	}
	public void setClearTime(Date clearTime) {
		this.clearTime = clearTime;
	}
	public void setRecoveryMode(Byte recoveryMode) {
		this.recoveryMode = recoveryMode;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public void setHeadOfficeId(Integer headOfficeId) {
		this.headOfficeId = headOfficeId;
	}
	public void setPopertyNumber(Integer popertyNumber) {
		this.popertyNumber = popertyNumber;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getRecoveryFee() {
		return recoveryFee;
	}
	public void setRecoveryFee(BigDecimal recoveryFee) {
		this.recoveryFee = recoveryFee;
	}
	
	
}