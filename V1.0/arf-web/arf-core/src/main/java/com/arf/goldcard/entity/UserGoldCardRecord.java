package com.arf.goldcard.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_user_gold_card_record")
@SequenceGenerator(name = "sequenceGenerator",sequenceName="p_user_gold_card_record_sequence")
public class UserGoldCardRecord extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal price;//购买价格
	private BigDecimal faceValue;//面值
	private Date buyDate;//购买日期
	private Date endDate;//失效时间
	private String typeNum;//类型编号,与p_gold_card_type形成多对一映射//如果开通ecc生成记录为空
	private String cardNo;//卡编号,在线支付时对应out_trade_no
	private Status status;//状态:0: 失效1: 正常
	private PayType payType;//支付方式:1:微信2:支付宝 ,3:钱包账户支付。后续添加其它支付方式
	private PayStatus payStatus;//支付状态:0:未支付1:支付成功
	private String userName;//用户名
	private Integer sourceType=0; //默认来源来源0代表购买金卡1代表购买金卡开通ECC生成的记录
	
	public enum SourceType{
		Purchase_Gold_Card,Open_Ecc
	}
	/**
	 * 支付方式
	 * @author Caolibao
	 *
	 */
	public enum PayType{
		Not_Use,//占位项
		Paid_WeiXin, //微信支付
		Paid_Alipay, //支付宝支付
		Paid_Account, //钱包账户支付
		;
		public static PayType get(int ordinal){
			PayType statuss[] = PayType.values();
			if(ordinal > statuss.length - 1){
				return null;
			}else{
				return statuss[ordinal];
			}
		}
	}
	
	/**
	 * 支付状态
	 * @author Caolibao
	 *
	 */
	public enum PayStatus{
		Not_Paid, //0:未支付
		Paid,//1:支付成功
		;
	}
	
	/**
	 * 订单状态
	 * @author Caolibao
	 *
	 */
	public enum Status{
		Disable,//失效
		Normal,//正常
		;
	}
	
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	
	@Column(precision=10,scale=2)
	public BigDecimal getPrice() {
		return price;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getFaceValue() {
		return faceValue;
	}
	public Date getBuyDate() {
		return buyDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	@Column(length = 40)
	public String getTypeNum() {
		return typeNum;
	}
	@Column(length = 40)
	public String getCardNo() {
		return cardNo;
	}
	@Column(length = 11)
	public Status getStatus() {
		return status;
	}
	@Column(length = 11)
	public PayType getPayType() {
		return payType;
	}
	@Column(length = 11)
	public PayStatus getPayStatus() {
		return payStatus;
	}
	@Column(length = 20)
	public String getUserName() {
		return userName;
	}
	
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
