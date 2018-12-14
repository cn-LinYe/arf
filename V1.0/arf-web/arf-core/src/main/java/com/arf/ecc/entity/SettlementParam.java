package com.arf.ecc.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "settlement_param")
@SequenceGenerator(name="sequenceGenerator",sequenceName="settlement_param_sequence")
public class SettlementParam extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private String number;//小区/停车场编号
	private Integer type;//0:小区,1:停车场
	private Integer status;//0:启用,1禁用
	private Integer branchId;//子公司编号
	private Integer propertyNumber;//物业编号
	private String assignType;//分配类型：upload_parkrate上传白名单开通ecc，package_ecc:套餐开通ECC，package_exchange：套餐中使用vip验证码，package_goldcard：套餐中购买金卡，goldcard_buy：购买金卡，goldcard_exchange:使用兑换码获得金卡
	private Integer assignMode;//分配方式 0：按比例分配，1：按固定金额分配
	private Integer eccProrataPercentProperty;//物业公司开通ECC分成百分比,eg. 30即分成30%给相关物业公司等
	private Integer eccProrataPercentBranch;//子公司开通ECC分成百分比,eg. 30即分成30%给相关子公司等
	private BigDecimal eccFixedAmountProperty;//物业公司开通ECC的分配金额
	private BigDecimal eccFixedAmountBranch;//子公司开通ECC的分配金额
	
	public static enum AssignType{
		upload_parkrate,//上传白名单开通
		package_ecc,//套餐开通ECC
		package_exchange,//套餐中使用vip验证码
		package_goldcard,//套餐中购买金卡
		goldcard_buy,//单独购买金卡
		goldcard_exchange,//单独使用兑换码获得金卡
	}
	
	public static enum AssignMode{
		percent,
		fixed,
	}
	
	@Column(length=20)
	public String getNumber() {
		return number;
	}
	public Integer getType() {
		return type;
	}
	public Integer getStatus() {
		return status;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public Integer getPropertyNumber() {
		return propertyNumber;
	}
	@Column(length=20)
	public String getAssignType() {
		return assignType;
	}
	public Integer getAssignMode() {
		return assignMode;
	}
	public Integer getEccProrataPercentProperty() {
		return eccProrataPercentProperty;
	}
	public Integer getEccProrataPercentBranch() {
		return eccProrataPercentBranch;
	}
	@Column(precision = 10, scale = 2)
	public BigDecimal getEccFixedAmountProperty() {
		return eccFixedAmountProperty;
	}
	@Column(precision = 10, scale = 2)
	public BigDecimal getEccFixedAmountBranch() {
		return eccFixedAmountBranch;
	}
	
	
	public void setNumber(String number) {
		this.number = number;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public void setPropertyNumber(Integer propertyNumber) {
		this.propertyNumber = propertyNumber;
	}
	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}
	public void setAssignMode(Integer assignMode) {
		this.assignMode = assignMode;
	}
	public void setEccProrataPercentProperty(Integer eccProrataPercentProperty) {
		this.eccProrataPercentProperty = eccProrataPercentProperty;
	}
	public void setEccProrataPercentBranch(Integer eccProrataPercentBranch) {
		this.eccProrataPercentBranch = eccProrataPercentBranch;
	}
	public void setEccFixedAmountProperty(BigDecimal eccFixedAmountProperty) {
		this.eccFixedAmountProperty = eccFixedAmountProperty;
	}
	public void setEccFixedAmountBranch(BigDecimal eccFixedAmountBranch) {
		this.eccFixedAmountBranch = eccFixedAmountBranch;
	}
}
