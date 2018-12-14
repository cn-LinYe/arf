package com.arf.laundry.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "l_laundry_order_items")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "l_laundry_order_items_sequence")
public class LaundryOrderItems extends BaseEntity<Long>{

	private static final long serialVersionUID = 9177035557664770484L;

	private String categoryNum;//类目编号
	private String categoryName;//类目名称
	private String superCategoryNum;//所属大类 
	private String superCategoryName;//所属大类名称
	private BigDecimal itemsFee;//单项总价格
	private BigDecimal price;//单件价格
	private LaundryOrder outTradeNo;//订单编号,外键->p_ laundry_order. out_trade_no
	private Integer itemsCount;//单项件数
	private String itemsNum;//编码
	
	@Column(length = 20)
	public String getCategoryNum() {
		return categoryNum;
	}
	@Column(length = 20)
	public String getCategoryName() {
		return categoryName;
	}
	@Column(length = 20)
	public String getSuperCategoryNum() {
		return superCategoryNum;
	}
	@Column(length = 20)
	public String getSuperCategoryName() {
		return superCategoryName;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getItemsFee() {
		return itemsFee;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getPrice() {
		return price;
	}
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false,referencedColumnName="outTradeNo")
	public LaundryOrder getOutTradeNo() {
		return outTradeNo;
	}
	@Column(length = 11)
	public Integer getItemsCount() {
		return itemsCount;
	}
	@Column(length = 30)
	public String getItemsNum() {
		return itemsNum;
	}
	public void setCategoryNum(String categoryNum) {
		this.categoryNum = categoryNum;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public void setSuperCategoryNum(String superCategoryNum) {
		this.superCategoryNum = superCategoryNum;
	}
	public void setSuperCategoryName(String superCategoryName) {
		this.superCategoryName = superCategoryName;
	}
	public void setItemsFee(BigDecimal itemsFee) {
		this.itemsFee = itemsFee;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public void setOutTradeNo(LaundryOrder outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public void setItemsCount(Integer itemsCount) {
		this.itemsCount = itemsCount;
	}
	public void setItemsNum(String itemsNum) {
		this.itemsNum = itemsNum;
	}
	
}
