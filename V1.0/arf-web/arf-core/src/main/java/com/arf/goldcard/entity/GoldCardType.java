package com.arf.goldcard.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_gold_card_type")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_gold_card_type_sequence")
public class GoldCardType extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal faceValue;//面值
	private BigDecimal price;//单卡价格
	private PeriodType periodType;//有效周期类型,枚举:MONTH有效期按月算;DAY有效期按天算
	private Integer usefulPeriod;//有效周期(天/月)数
	private Integer unShelved;//是否下架,0:正常,1:下架
	private String typeNum;//类型编号
	private String themeColor;//卡面主题色,rgba的16进制方式:#CFCFCFCF
	private String cardName;//卡名称
	private Integer stock;//库存,余量<=0售罄
	private Date unshelvedDate;//下架时间
	private Date shelvedDate;//上架时间
	private BigDecimal profitProrataAmount;//利益分配金额  由该字段来确定相关利益方的分配金额.
	
	/**
	 * 有效周期类型枚举
	 * @author Caolibao
	 *
	 */
	public enum PeriodType{
		MONTH, //MONTH有效期按月算;
		DAY, //DAY有效期按天算
		;
	}
	
	public enum UnShelved{
		normal, //0正常
		UnShelved, //1下架
		;
	}
	
	@Column(precision=10,scale=2)
	public BigDecimal getFaceValue() {
		return faceValue;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getPrice() {
		return price;
	}
	@Column(length=11)
	public PeriodType getPeriodType() {
		return periodType;
	}
	@Column(length=11)
	public Integer getUsefulPeriod() {
		return usefulPeriod;
	}
	@Column(length=11)
	public Integer getUnShelved() {
		return unShelved;
	}
	@Column(length=40)
	public String getTypeNum() {
		return typeNum;
	}
	@Column(length=9)
	public String getThemeColor() {
		return themeColor;
	}
	@Column(length=30)
	public String getCardName() {
		return cardName;
	}
	@Column(length=11)
	public Integer getStock() {
		return stock;
	}
	public Date getUnshelvedDate() {
		return unshelvedDate;
	}
	public Date getShelvedDate() {
		return shelvedDate;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getProfitProrataAmount() {
		return profitProrataAmount;
	}
	
	public void setProfitProrataAmount(BigDecimal profitProrataAmount) {
		this.profitProrataAmount = profitProrataAmount;
	}
	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public void setPeriodType(PeriodType periodType) {
		this.periodType = periodType;
	}
	public void setUsefulPeriod(Integer usefulPeriod) {
		this.usefulPeriod = usefulPeriod;
	}
	public void setUnShelved(Integer unShelved) {
		this.unShelved = unShelved;
	}
	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}
	public void setThemeColor(String themeColor) {
		this.themeColor = themeColor;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public void setUnshelvedDate(Date unshelvedDate) {
		this.unshelvedDate = unshelvedDate;
	}
	public void setShelvedDate(Date shelvedDate) {
		this.shelvedDate = shelvedDate;
	}
	
}
