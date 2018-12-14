package com.arf.violation.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "v_traffic_violation_order_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "v_traffic_violation_ order_item_sequence")
public class TrafficViolationOrderItem extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderNo; // varchar(40) 前缀: TVLO 订单编号,与v_traffic_violation_// order形成多对一映射
	private Date time; // datetime not null 违章记录时间
	private String location; // varchar(512) 违章发生位置
	private String reason; // varchar(1024) 违章原因
	private BigDecimal fineAmount; // bigdecimal(10,2) 罚款金额
	private String department; // varchar(100) 违章采集机关
	private Integer fineMarks; // int 违章扣分
	private String code; // varchar(40) 违章代码
	private String archive; // varchar(100) 违章项文书编号
	private String telephone; // varchar(20) 联系电话
	private String excutelocation; // varchar(100) 违章处理地址
	private String excutedepartment; // varchar(100) 执行部门
	private String punishmentaccording; // varchar(200) 处罚依据
	private String illegalentry; // varchar(100) 违法条款
	private String locationid; // varchar(10) 6位归属行政地区编码,eg.深圳440300
	private String locationName; // varchar(30) 归属行政地区名称,eg.广东省深圳市
	private Byte   canProcess; // tinyint 是否可以代办 0:不可以;1:可以
	private String uniqueCode; // varchar(50) 违章信息的特征码（相同车牌，违章时间，地点，该值相同
	private String secondaryUniqueCode; // varchar(20) 违章记录ID，用于下单。
	private String canprocessMsg; // varchar(200) 是否代办原因
	private String license; // varchar(10) NOT NULL 车牌号
	private String imgUrl; // varchar(1024) 图片(罚单)URL 一个JSONArray的字符串,最多五张图片
	private BigDecimal serviceAmount; // bigdecimal(10,2) 服务费金额
	private String category; // varchar(100) 违章分类类别（如该字段显示为“现场单”，请注意提示用户）
	
	private Integer status  ;//status  status  tinyint  NOT NULL  记录处理状态:	0.  新建/未支付1.  已支付2.  商户已拒绝3.  商户处理中4.  处理完毕5:用户取消订单

	public enum Status{
		New,Paid,Refuse,Confirm,Finish,Cancel;
		public static Status get(int ordinal){
			Status status[]=Status.values();
			if (status.length<ordinal) {
				return null;
			}
			return status[ordinal];
		}
	}
	
	@Column(nullable =false)
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(length=40)
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Column(length=512)
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Column(length=1024)
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getFineAmount() {
		return fineAmount;
	}
	public void setFineAmount(BigDecimal fineAmount) {
		this.fineAmount = fineAmount;
	}
	@Column(length=100)
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Integer getFineMarks() {
		return fineMarks;
	}
	public void setFineMarks(Integer fineMarks) {
		this.fineMarks = fineMarks;
	}
	@Column(length=40)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length=100)
	public String getArchive() {
		return archive;
	}
	public void setArchive(String archive) {
		this.archive = archive;
	}
	@Column(length=20)
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Column(length=100)
	public String getExcutelocation() {
		return excutelocation;
	}
	public void setExcutelocation(String excutelocation) {
		this.excutelocation = excutelocation;
	}
	@Column(length=100)
	public String getExcutedepartment() {
		return excutedepartment;
	}
	public void setExcutedepartment(String excutedepartment) {
		this.excutedepartment = excutedepartment;
	}
	@Column(length=200)
	public String getPunishmentaccording() {
		return punishmentaccording;
	}
	public void setPunishmentaccording(String punishmentaccording) {
		this.punishmentaccording = punishmentaccording;
	}
	@Column(length=100)
	public String getIllegalentry() {
		return illegalentry;
	}
	public void setIllegalentry(String illegalentry) {
		this.illegalentry = illegalentry;
	}
	@Column(length=10)
	public String getLocationid() {
		return locationid;
	}
	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}
	@Column(length=30)
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public Byte getCanProcess() {
		return canProcess;
	}
	public void setCanProcess(Byte canProcess) {
		this.canProcess = canProcess;
	}
	@Column(length=50)
	public String getUniqueCode() {
		return uniqueCode;
	}
	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}
	@Column(length=20)
	public String getSecondaryUniqueCode() {
		return secondaryUniqueCode;
	}
	public void setSecondaryUniqueCode(String secondaryUniqueCode) {
		this.secondaryUniqueCode = secondaryUniqueCode;
	}
	@Column(length=200)
	public String getCanprocessMsg() {
		return canprocessMsg;
	}
	public void setCanprocessMsg(String canprocessMsg) {
		this.canprocessMsg = canprocessMsg;
	}
	@Column(length=10)
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	@Column(length=1024)
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getServiceAmount() {
		return serviceAmount;
	}
	public void setServiceAmount(BigDecimal serviceAmount) {
		this.serviceAmount = serviceAmount;
	}
	@Column(length=100)
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}
