package com.arf.carbright.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "p_business", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "businessNum" }, name = "unique_business_num") })
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_business_sequence")
public class PBusiness extends BaseEntity<Long> {

	/**
	 * 商户鉴权token缓存前缀
	 */
	public static final String Prefix_Cache_Key_Business_Token = "PBusiness.businessToken:";

	/**
	 * 商户营业时间缓存前缀
	 */
	public static final String Prefix_Cache_Key_Opening_Times = "PBusiness.openingTimes:";

	public static final String Default_Opening_Times = "9:00 ~ 23:00";
	
	
	//商户服务范围
	public static final String Prefix_Carbrighter_Appearance = "OTA";// 点滴洗-洗外观
	public static final String Prefix_Carbrighter_Appearance_Interior = "OTB";// 点滴洗-洗外观+内饰
	public static final String Prefix_Laundry = "OTL";// 洗衣帮帮-洗衣
	public static final String Prefix_Car_Insurance = "PCI";// 保险
	public static final String Prefix_Cae_Violation_Dispose = "TVO";// 违章处理
	public static final String Prefix_Refuel_Gas = "RFL";// 家加油-加油
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 商户编码
	private Integer businessNum;
	// 登录名
	private String loginName;
	// 商户类型
	private Integer businessTypeId;
	// 密码(MD5加密)
	private String password;
	// 商户名称
	private String businessName;
	// 网站地址
	private String webAddress;
	// 联系人
	private String contactPerson;
	// 联系电话
	private String contactPhone;
	// 手机号码
	private String mobile;
	// 商戶地址
	private String address;
	// 默认0；0：代表尚未审批；1：代表审批通过 2：已禁用
	private Integer auditStatus;
	// 创建时间
	private Date createTime;
	// 最后登录时间
	private Date lastLoginTime;
	// 明文密码
	private String acPassword;
	// 备注
	private String remark;
	// 授权开始时间
	private Date startTime;
	// 授权截止时间
	private Date endTime;
	// 商户图片 一个JSONArray的字符串， 最新的图片的位置为0的地方存放
	private String businessPic;
	// 子公司id
	private Integer branchId;
	// 总公司
	private Integer headOfficeId;
	// 物业
	private Integer propretyNumber;
	

	public enum AuditStatus {
		Not_Audit, Audit_Through, Disabled,;
	}

	// 商户服务范围
	private String businessServicePackageNums;
	// 商户营业时间
	private String businessHours;
	// 商户描述
	private String businessDescription;
	// 店铺状态0正常营业 1休息
	private Byte businessStatus;
	// 恢复营业时间
	private Date businessUsualTime;
	// 纬度
	private Double lat;
	// 经度
	private Double lng;
	
	private double businessGrade ;//商户评分
	
	private Integer businessComment ;//商户评论（条数）
	
	private Float profit;//商户分利
	

	public Float getProfit() {
		return profit;
	}

	public void setProfit(Float profit) {
		this.profit = profit;
	}

	public double getBusinessGrade() {
		return businessGrade;
	}

	public void setBusinessGrade(double businessGrade) {
		this.businessGrade = businessGrade;
	}

	public Integer getBusinessComment() {
		return businessComment;
	}

	public void setBusinessComment(Integer businessComment) {
		this.businessComment = businessComment;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public enum businessStatus {
		NormalBusiness, Rest,;
	}

	public Integer getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}

	@Column(length = 20)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(Integer businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	@Column(length = 32)
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(length = 100)
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Column(length = 100)
	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	@Column(length = 50)
	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	@Column(length = 20)
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Column(length = 20)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(length = 50)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Column(length = 32)
	@JsonIgnore
	public String getAcPassword() {
		return acPassword;
	}

	public void setAcPassword(String acPassword) {
		this.acPassword = acPassword;
	}

	@Column(length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 商户图片 一个JSONArray的字符串， 最新的图片的位置为0的地方存放
	 * @return
	 */
	@Column(length = 2000)
	public String getBusinessPic() {
		return businessPic;
	}

	public void setBusinessPic(String businessPic) {
		this.businessPic = businessPic;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Integer getHeadOfficeId() {
		return headOfficeId;
	}

	public void setHeadOfficeId(Integer headOfficeId) {
		this.headOfficeId = headOfficeId;
	}

	public Integer getPropretyNumber() {
		return propretyNumber;
	}

	public void setPropretyNumber(Integer propretyNumber) {
		this.propretyNumber = propretyNumber;
	}

	public String getBusinessServicePackageNums() {
		return businessServicePackageNums;
	}

	public void setBusinessServicePackageNums(String businessServicePackageNums) {
		this.businessServicePackageNums = businessServicePackageNums;
	}

	@Column(length = 32)
	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	@Column(length = 200)
	public String getBusinessDescription() {
		return businessDescription;
	}

	public void setBusinessDescription(String businessDescription) {
		this.businessDescription = businessDescription;
	}

	public Byte getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(Byte businessStatus) {
		this.businessStatus = businessStatus;
	}

	public Date getBusinessUsualTime() {
		return businessUsualTime;
	}

	public void setBusinessUsualTime(Date businessUsualTime) {
		this.businessUsualTime = businessUsualTime;
	}

}
