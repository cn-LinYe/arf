package com.arf.goldcard.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_gold_card_exchange_code")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_gold_card_exchange_code_sequence")
public class GoldCardExchangeCode extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private String exchangeCode;//兑换码,8为数字+大写字母组合
	private Integer status;//0:未使用,1:已发放,2:已使用
	//private String userName;//兑换人用户名
	//private String businessName;//发放人(商户)名称
	//private String businessMobile;//发放人(商户)手机号
	//private String cardNo;//卡编号,与p_user_gold_card. card_no多对一映射
	private String typeNum;//类型编号,与p_gold_card_type形成多对一映射
	private BigDecimal faceValue;//面值
	private BigDecimal price;//单卡价格
	//private Date exchangeDate;//兑换时间
	//private Date grantDate;//发放时间
	private String batchNum;//生成批次编码
	
	public enum Status{
		NoUsed,HaveGrant,Used
	}
	
	@Column(length=20)
	public String getExchangeCode() {
		return exchangeCode;
	}
	@Column(length=11)
	public Integer getStatus() {
		return status;
	}
	//@Column(length=20)
	//public String getUserName() {
	//	return userName;
	//}
//	@Column(length=20)
//	public String getBusinessName() {
//		return businessName;
//	}
//	@Column(length=20)
//	public String getBusinessMobile() {
//		return businessMobile;
//	}
//	@Column(length=40)
//	public String getCardNo() {
//		return cardNo;
//	}
	@Column(length=40)
	public String getTypeNum() {
		return typeNum;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getFaceValue() {
		return faceValue;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getPrice() {
		return price;
	}
//	public Date getExchangeDate() {
//		return exchangeDate;
//	}
//	public Date getGrantDate() {
//		return grantDate;
//	}
	@Column(length=20)
	public String getBatchNum() {
		return batchNum;
	}
	
	
	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	//public void setUserName(String userName) {
	//	this.userName = userName;
	//}
//	public void setBusinessName(String businessName) {
//		this.businessName = businessName;
//	}
//	public void setBusinessMobile(String businessMobile) {
//		this.businessMobile = businessMobile;
//	}
//	public void setCardNo(String cardNo) {
//		this.cardNo = cardNo;
//	}
	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}
	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
//	public void setExchangeDate(Date exchangeDate) {
//		this.exchangeDate = exchangeDate;
//	}
//	public void setGrantDate(Date grantDate) {
//		this.grantDate = grantDate;
//	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
}
