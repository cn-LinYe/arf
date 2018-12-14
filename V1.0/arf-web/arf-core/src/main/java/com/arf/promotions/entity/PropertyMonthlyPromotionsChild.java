package com.arf.promotions.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name = "property_monthly_promotions_child")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "property_monthly_payment_promotions_sequence")
public class PropertyMonthlyPromotionsChild extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private Expression expression;//表达式（0、大于等于 More_Equal 1、等于 Equal 2大于 More）
	private Integer paymentMonth;//缴费月份
	private Integer giftMonth;//赠送月份
	private String promotionsName;//活动规则名称
	private String childNo;//子类编号
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Expression{
		More_Equal,
		Equal,
		More;
	}

	public Expression getExpression() {
		return expression;
	}

	public Integer getPaymentMonth() {
		return paymentMonth;
	}

	public Integer getGiftMonth() {
		return giftMonth;
	}

	@Column(length = 20)
	public String getPromotionsName() {
		return promotionsName;
	}

	@Column(length = 20)
	public String getChildNo() {
		return childNo;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public void setPaymentMonth(Integer paymentMonth) {
		this.paymentMonth = paymentMonth;
	}

	public void setGiftMonth(Integer giftMonth) {
		this.giftMonth = giftMonth;
	}

	public void setPromotionsName(String promotionsName) {
		this.promotionsName = promotionsName;
	}

	public void setChildNo(String childNo) {
		this.childNo = childNo;
	}
	
}
