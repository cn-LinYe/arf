package com.arf.carbright.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_business_service_limit")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_business_service_limit_sequence")
public class BusinessServiceLimit extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 服务名主键ID
	private Integer pBusinessServiceRange;
	// 商户编号
	private Integer businessNum;
	// 服务单数量
	private Byte limitServiceCount;
	
	//洗衣帮帮数量
	private Integer laundryServiceCount;
	//洗衣帮帮状态0停止接单1开始接单
	private Integer laundryServiceStatus;
	//点滴洗数量外观数量
	private Integer carBrighterAppearanceCount;
	//点滴洗外观+内饰数量
	private Integer carBrighterAppearanceInteriorOtbCount;
	//点滴洗状态0停止接单1开始接单
	private Integer carBrighterStatus;
	//保险服务数量
	private Integer InsuranceCount;
	//保险服务数量状态0停止接单1开始接单
	private Integer InsuranceStatus;
	//快递服务数量
	private Integer expressageCount;
	//快递服务数量状态0停止接单1开始接单
	private Integer expressageStatus;
	
	
	public enum CarBrighterStatus{
		StopOrders,BeginOrders;
	}
	
	public enum Type {
		LaundryService, CarBrighter, Insurance, Expressage;
	}

	public Integer getLaundryServiceCount() {
		return laundryServiceCount;
	}

	public void setLaundryServiceCount(Integer laundryServiceCount) {
		this.laundryServiceCount = laundryServiceCount;
	}

	public Integer getLaundryServiceStatus() {
		return laundryServiceStatus;
	}

	public void setLaundryServiceStatus(Integer laundryServiceStatus) {
		this.laundryServiceStatus = laundryServiceStatus;
	}

	public Integer getCarBrighterAppearanceCount() {
		return carBrighterAppearanceCount;
	}

	public void setCarBrighterAppearanceCount(Integer carBrighterAppearanceCount) {
		this.carBrighterAppearanceCount = carBrighterAppearanceCount;
	}

	public Integer getCarBrighterAppearanceInteriorOtbCount() {
		return carBrighterAppearanceInteriorOtbCount;
	}

	public void setCarBrighterAppearanceInteriorOtbCount(Integer carBrighterAppearanceInteriorOtbCount) {
		this.carBrighterAppearanceInteriorOtbCount = carBrighterAppearanceInteriorOtbCount;
	}

	public Integer getCarBrighterStatus() {
		return carBrighterStatus;
	}

	public void setCarBrighterStatus(Integer carBrighterStatus) {
		this.carBrighterStatus = carBrighterStatus;
	}

	public Integer getInsuranceCount() {
		return InsuranceCount;
	}

	public void setInsuranceCount(Integer insuranceCount) {
		InsuranceCount = insuranceCount;
	}

	public Integer getInsuranceStatus() {
		return InsuranceStatus;
	}

	public void setInsuranceStatus(Integer insuranceStatus) {
		InsuranceStatus = insuranceStatus;
	}

	public Integer getExpressageCount() {
		return expressageCount;
	}

	public void setExpressageCount(Integer expressageCount) {
		this.expressageCount = expressageCount;
	}

	public Integer getExpressageStatus() {
		return expressageStatus;
	}

	public void setExpressageStatus(Integer expressageStatus) {
		this.expressageStatus = expressageStatus;
	}

	public Integer getpBusinessServiceRange() {
		return pBusinessServiceRange;
	}

	public void setpBusinessServiceRange(Integer pBusinessServiceRange) {
		this.pBusinessServiceRange = pBusinessServiceRange;
	}

	public Integer getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}

	public Byte getLimitServiceCount() {
		return limitServiceCount;
	}

	public void setLimitServiceCount(Byte limitServiceCount) {
		this.limitServiceCount = limitServiceCount;
	}

}
