package com.arf.axd.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_ecc_physical_gift_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_ecc_physical_gift_record_sequence")
public class EccPhysicalGiftRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1661813165495246166L;

	private String physicalName;//实物名称
	
	private String realName;//真实姓名
	
	private String userName;//用户账号
	
	private BigDecimal payAmount;//支付金额
	
	private Integer openEccType;//通过哪种方式获得赠送(0开通ecc)
	
	private Integer status;//状态（0尚未赠送，1已发放)
	
	private String logisticsName;//物流名称
	
	private String logisticsNum;//物流单号
	
	private String orderNo;//订单编号
	
	private Long eccPackageId;//Ecc配置表id
	
	private Date logisticsDate;//物流发放时间
	
	private String operationName;//操作人员姓名

	public enum OpenEccType{
		EccGive
	}
	public enum Status{
		NotGive,HaveGive
	}
	
	@Column(length=40)
	public String getPhysicalName() {
		return physicalName;
	}

	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}
	@Column(length=40)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	@Column(length=40)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(precision=10,scale=2)
	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getOpenEccType() {
		return openEccType;
	}

	public void setOpenEccType(Integer openEccType) {
		this.openEccType = openEccType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(length=40)
	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}
	@Column(length=40)
	public String getLogisticsNum() {
		return logisticsNum;
	}

	public void setLogisticsNum(String logisticsNum) {
		this.logisticsNum = logisticsNum;
	}
	@Column(length=40)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getEccPackageId() {
		return eccPackageId;
	}

	public void setEccPackageId(Long eccPackageId) {
		this.eccPackageId = eccPackageId;
	}

	public Date getLogisticsDate() {
		return logisticsDate;
	}

	public void setLogisticsDate(Date logisticsDate) {
		this.logisticsDate = logisticsDate;
	}
	@Column(length=40)
	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	
	
	
	
}
