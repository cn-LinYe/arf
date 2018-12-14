package com.arf.carbright.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_goods_order_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "goods_order_record_sequence")
public class GoodsOrderRecord extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	
	private String orderNo;//订单编号
	private String parkingId;//停车场ID/小区id(小区编码community_number)
	private String parkingName;//停车场名/小区名
	private String license;//车牌号码
	private Short feePayType;//支付方式 0;代支付，1微信支付，2支付宝支付，3 银行卡 4、现金 5、钱包支付 6、套餐支付
	private Short feePayStatus;//支付状态0;未支付1;已支付 2;支付失败
	private Short status;//订单状态：0未确认，1已确认，2已取消3已完成；
	private BigDecimal fee;//费用，单位元
	private String userName;//车主登录账号
	private Date createTime;//下单时间
	private Short isPayagent;//是否代付0否 1 是 默认0；
	private Short orderType;//订单类型：0、会员套餐
	private Integer goodsTypeId;//商品类型id
	private String entrance;//入口名称
	private String invoiceContent;//发票内容
	private String invoiceTitle;//发票标题
	private String thumbnail;//缩略图
	private Integer branchId;//子公司或物业特有信息子公司唯一标识id
	private Integer headOfficeId;//总公司id
	private Integer popertyNumber;//物业唯一标识id
	
	@Column(length=40)
	public String getOrderNo() {
		return orderNo;
	}
	@Column(length=50)
	public String getParkingId() {
		return parkingId;
	}
	@Column(length=50)
	public String getParkingName() {
		return parkingName;
	}
	@Column(length=20)
	public String getLicense() {
		return license;
	}
	@Column(length=4)
	public Short getFeePayType() {
		return feePayType;
	}
	@Column(length=40)
	public Short getFeePayStatus() {
		return feePayStatus;
	}
	@Column(length=4)
	public Short getStatus() {
		return status;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getFee() {
		return fee;
	}
	@Column(length=20)
	public String getUserName() {
		return userName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(length=4)
	public Short getIsPayagent() {
		return isPayagent;
	}
	@Column(length=4)
	public Short getOrderType() {
		return orderType;
	}
	@Column(length=11)
	public Integer getGoodsTypeId() {
		return goodsTypeId;
	}
	@Column(length=20)
	public String getEntrance() {
		return entrance;
	}
	@Column(length=255)
	public String getInvoiceContent() {
		return invoiceContent;
	}
	@Column(length=255)
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	@Column(length=255)
	public String getThumbnail() {
		return thumbnail;
	}
	@Column(length=11)
	public Integer getBranchId() {
		return branchId;
	}
	@Column(length=11)
	public Integer getHeadOfficeId() {
		return headOfficeId;
	}
	@Column(length=11)
	public Integer getPopertyNumber() {
		return popertyNumber;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public void setFeePayType(Short feePayType) {
		this.feePayType = feePayType;
	}
	public void setFeePayStatus(Short feePayStatus) {
		this.feePayStatus = feePayStatus;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setIsPayagent(Short isPayagent) {
		this.isPayagent = isPayagent;
	}
	public void setOrderType(Short orderType) {
		this.orderType = orderType;
	}
	public void setGoodsTypeId(Integer goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}
	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}
	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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
	
}
