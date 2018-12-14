package com.arf.gift.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name="gift_voucher_exchange_item")
@SequenceGenerator(name="sequenceGenerator",sequenceName="gift_voucher_exchange_item_sequence")
public class GiftVoucherExchangeItem extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String giftName	;//varchar(20)	not null	礼品名称
	private String sku	;//varchar(10)	not null	SKU编码
	private BigDecimal marketPrice	;//decimal(12,2)	not null	市场价
	private BigDecimal discountPrice	;//decimal(12,2)	not null	折扣价格
	private String userName	;//varchar(30)	not null	用户名
	private Integer exchangeCount	;//int	not null	兑换数量
	private String voucherNumber	;//varchar(40)	not null	卡券编号,与gift_ voucher_info_record形成多对一的映射关系
	
	@Column(length=20,nullable=false)
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	@Column(length=10,nullable=false)
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	@Column(precision=10,scale=2,nullable=false)
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	@Column(precision=10,scale=2,nullable=false)
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}
	@Column(length=30,nullable=false)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(nullable=false)
	public Integer getExchangeCount() {
		return exchangeCount;
	}
	public void setExchangeCount(Integer exchangeCount) {
		this.exchangeCount = exchangeCount;
	}
	@Column(length=40,nullable=false)
	public String getVoucherNumber() {
		return voucherNumber;
	}
	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}
	
	
	
}
