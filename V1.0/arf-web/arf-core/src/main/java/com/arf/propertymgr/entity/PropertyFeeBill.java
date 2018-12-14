package com.arf.propertymgr.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "pty_property_fee_bill",
		uniqueConstraints = {
		@UniqueConstraint(columnNames = { "billNo" }, name = "unique_bill_no") 
		}
)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "pty_property_fee_bill_sequence")
public class PropertyFeeBill extends BaseEntity<Long> {

	private static final long serialVersionUID = 109752514255503099L;
	private String communityNumber;//小区编号
	private String   building;//栋        
	private String   unit;//单元             
	private String   floor;//楼层            
	private String   room;//房号            
	private String   boundNumber;//物业房间用户绑定表中bound_number     
	private BigDecimal totalFee;//物业费用        
	private byte    billingPeriod;//同pty_property_room_info.billing_period   
	private BigDecimal pricePerM2;//每平米单价     
	private BigDecimal acreage;//物业面积          
	private byte    chargeType;//同pty_property_room_info.charge_type      
	private String   billNo;//账单编号          
	private String   billName;//账单名称，根据charge_type和billing_period生成,例如:`2016年9月`,`2016年1季度`,`2017全年`        
	private Status  status;//账单状态:0: NORMAL 正常，1: OUT_OF_DATE  过期，2: STOP_TOLL 停止收费           
	private Date     endDate;//过期时间         
	private PayStatus  payStatus;//缴费状态: 0:NOT_PAID  未缴费，1:PAID_SUCCESS 已缴费      
	private Date     paidDate;//缴费时间        
	private String  remark;//账单备注,具体的账单详情简略描述           
	private String  otherFee;//其它费用，一个JSONARRAY字符串      
	private String  agentUserName;//代缴人用户名  
	private AgentFlag agentFlag;//是否代缴标识:0:NOT 未代缴 ，1:SENT 已发送代缴/已被代缴，2:SUCCESS 代缴成功
	private PayType payType;//"支付方式枚举:WEIXIN 1:微信支付 ALIPAY 2:支付宝支付  WALLET_ACCOUNT 3:电子钱包 GOLDCARD 4:停车卡
	private String userName;//房间户主的用户名
	private BigDecimal sumFee;//总费用 = 物业费+其他费用
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PayStatus{
		NOT_PAID,
		PAID_SUCCESS,
		;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AgentFlag{
		NOT,
		SENT,
		SUCCESS,
		;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		NORMAL,
		OUT_OF_DATE,
		STOP_TOLL,
		;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PayType{
		UNKNOWN,
		WEIXIN,
		ALIPAY,
		WALLET_ACCOUNT,
		GOLDCARD
		;
	}
	
	@Column(length = 20)
	public String getBuilding() {
		return building;
	}
	@Column(length = 20)
	public String getUnit() {
		return unit;
	}
	@Column(length = 20)
	public String getFloor() {
		return floor;
	}
	@Column(length = 20)
	public String getRoom() {
		return room;
	}
	@Column(length = 40,nullable = false)
	public String getBoundNumber() {
		return boundNumber;
	}
	@Column(precision = 10, scale = 2,nullable = false)
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	public byte getBillingPeriod() {
		return billingPeriod;
	}
	@Column(precision = 10, scale = 2)
	public BigDecimal getPricePerM2() {
		return pricePerM2;
	}
	@Column(precision = 10, scale = 2)
	public BigDecimal getAcreage() {
		return acreage;
	}
	@Column(nullable = false)
	public byte getChargeType() {
		return chargeType;
	}
	@Column(length = 40,nullable = false)
	public String getBillNo() {
		return billNo;
	}
	@Column(length = 40)
	public String getBillName() {
		return billName;
	}
	@Column(nullable = false)
	public Status getStatus() {
		return status;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}
	@Column(nullable = false)
	public PayStatus getPayStatus() {
		return payStatus;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getPaidDate() {
		return paidDate;
	}
	@Column(length = 200)
	public String getRemark() {
		return remark;
	}
	@Column(length = 2000)
	public String getOtherFee() {
		return otherFee;
	}
	@Column(length = 20)
	public String getAgentUserName() {
		return agentUserName;
	}
	public AgentFlag getAgentFlag() {
		return agentFlag;
	}
	@Column(length = 20)
	public String getUserName() {
		return userName;
	}
	@Column(precision = 10, scale = 2)
	public BigDecimal getSumFee() {
		return sumFee;
	}
	public void setSumFee(BigDecimal sumFee) {
		this.sumFee = sumFee;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public void setBoundNumber(String boundNumber) {
		this.boundNumber = boundNumber;
	}
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	public void setBillingPeriod(byte billingPeriod) {
		this.billingPeriod = billingPeriod;
	}
	public void setPricePerM2(BigDecimal pricePerM2) {
		this.pricePerM2 = pricePerM2;
	}
	public void setAcreage(BigDecimal acreage) {
		this.acreage = acreage;
	}
	public void setChargeType(byte chargeType) {
		this.chargeType = chargeType;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public void setBillName(String billName) {
		this.billName = billName;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}
	public void setAgentUserName(String agentUserName) {
		this.agentUserName = agentUserName;
	}
	public void setAgentFlag(AgentFlag agentFlag) {
		this.agentFlag = agentFlag;
	}
	public PayType getPayType() {
		return payType;
	}
	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	@Column(length = 20,nullable=false)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	
}
