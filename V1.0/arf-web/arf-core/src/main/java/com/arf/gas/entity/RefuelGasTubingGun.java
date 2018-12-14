package com.arf.gas.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

/**
 * 家加油油站油罐油枪表
 */
@Entity
@Table(name = "refuel_gas_tubing_gun")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "refuel_gas_tubing_gun_sequence")
public class RefuelGasTubingGun extends BaseEntity<Long>{

	private static final long serialVersionUID = 1L;
	private Integer gasNum;//	int	否		普通	油站编号
	private Integer businessNum	;//int	否		普通	商户编号
	private Byte gunNum	;//tinyint	否		普通	油枪编号
	private Byte tubingNum	;//tinyint	否		普通	油罐编号
	private Integer tubingCapacity;//	int	否		普通	油罐容量
	private Integer remainingCapacity;//	int	否		普通	剩余容量
	private Byte spareGunNum;//	tinyint	否		普通	备用油枪编号
	private Byte oliType;//油类型（0、#0 1、#92 2、 #95）此类型缓存redis 不做数据库维护
	
	public Byte getOliType() {
		return oliType;
	}
	public void setOliType(Byte oliType) {
		this.oliType = oliType;
	}
	public Integer getGasNum() {
		return gasNum;
	}
	public void setGasNum(Integer gasNum) {
		this.gasNum = gasNum;
	}
	public Integer getBusinessNum() {
		return businessNum;
	}
	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}
	public Byte getGunNum() {
		return gunNum;
	}
	public void setGunNum(Byte gunNum) {
		this.gunNum = gunNum;
	}
	public Byte getTubingNum() {
		return tubingNum;
	}
	public void setTubingNum(Byte tubingNum) {
		this.tubingNum = tubingNum;
	}
	public Integer getTubingCapacity() {
		return tubingCapacity;
	}
	public void setTubingCapacity(Integer tubingCapacity) {
		this.tubingCapacity = tubingCapacity;
	}
	public Integer getRemainingCapacity() {
		return remainingCapacity;
	}
	public void setRemainingCapacity(Integer remainingCapacity) {
		this.remainingCapacity = remainingCapacity;
	}
	public Byte getSpareGunNum() {
		return spareGunNum;
	}
	public void setSpareGunNum(Byte spareGunNum) {
		this.spareGunNum = spareGunNum;
	}
	
	

}
