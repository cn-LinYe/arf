package com.arf.gas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

/**
 * 家加油商家补油子订单表
 */
@Entity
@Table(name = "refuel_gas_supply_order_child")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "refuel_gas_supply_order_child_sequence")
public class RefuelGasSupplyOrderChild extends BaseEntity<Long>{

	private static final long serialVersionUID = -1354562848240352894L;
	private String supplyOrderNo	;//varchar(20)	否		普通	补油订单编号
	private Byte oilType	;//tinyint	否		普通	油类型（0、#0 1、#92 2、 #95）此类型缓存redis 不做数据库维护
	private Integer oilPrice;//	int	否		普通	油品单价
	private Integer oilTotalAmount	;//int	否		普通	油品总金额
	private Integer oilCapacity	;//int	否		普通	油品容量
	private Date supplyDate	;//datetime	否		普通	补油时间（与补油订单表时间一样，同一个订单编号视为同一个订单）
	private Byte tubingNum	;//tinyint	否		普通	油罐编号

	public Byte getTubingNum() {
		return tubingNum;
	}
	public void setTubingNum(Byte tubingNum) {
		this.tubingNum = tubingNum;
	}
	@Column(length=30)
	public String getSupplyOrderNo() {
		return supplyOrderNo;
	}
	public void setSupplyOrderNo(String supplyOrderNo) {
		this.supplyOrderNo = supplyOrderNo;
	}
	public Byte getOilType() {
		return oilType;
	}
	public void setOilType(Byte oilType) {
		this.oilType = oilType;
	}
	public Integer getOilPrice() {
		return oilPrice;
	}
	public void setOilPrice(Integer oilPrice) {
		this.oilPrice = oilPrice;
	}
	public Integer getOilTotalAmount() {
		return oilTotalAmount;
	}
	public void setOilTotalAmount(Integer oilTotalAmount) {
		this.oilTotalAmount = oilTotalAmount;
	}
	public Integer getOilCapacity() {
		return oilCapacity;
	}
	public void setOilCapacity(Integer oilCapacity) {
		this.oilCapacity = oilCapacity;
	}
	public Date getSupplyDate() {
		return supplyDate;
	}
	public void setSupplyDate(Date supplyDate) {
		this.supplyDate = supplyDate;
	}
	
	

}
