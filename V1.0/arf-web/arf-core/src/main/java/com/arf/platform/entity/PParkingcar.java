package com.arf.platform.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.OrderEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(
	name = "p_parkingcar",
	indexes={
		@Index(name="index_community_no",columnList="community_no"),
		@Index(name="index_license",columnList="license")
	}
)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_parkingcar_sequence")
public class PParkingcar extends OrderEntity<Long>{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 停车场ID
	 */
	private Integer parkingId;
	
	/**
	 * 小区编号(停车场编码)
	 */
	private String communityNo;
	
	/**
	 * 车位编号,没有指定泊位时为空
	 */
	private String berthNo;
	
	/**
	 * 来车时间
	 */
	private Date arriveTime;
	
	/**
	 * 车牌
	 */
	private String license;
	
	/**
	 * 汇报时间
	 */
	private Date reportTime;
	
	/**
	 * 停车类型：0月卡车,1临时车,2免费车,3电子账户车
	 */
	private Short stopType;
	
	/**
	 * 停车类型枚举 0月卡车,1临时车,2免费车,3电子账户车
	 * @author Caolibao
	 * 2016年5月26日 下午10:33:11
	 *
	 */
	public enum StopType{
		Monthly_Card,
		Temp_Parking,
		Free,
		Member_Account
		;
	}
	public enum ParkingType{
		Community,
		Parking
		;
	}
	
	/**
	 * 来车图片
	 */
	private String arriveCarImgUrl;
	
	/**
	 * 停车场类型（0 小区 1 紧急场所）
	 */
	private Short parkingType;
	//物业编号 
	private Integer propertyNumber;
	//子公司编号 
	private Integer branchId;
	
	@Column(name = "parking_type",length=4)
	public Short getParkingType() {
		return parkingType;
	}

	public void setParkingType(Short parkingType) {
		this.parkingType = parkingType;
	}

	@Column(name = "parking_id")
	public Integer getParkingId() {
		return parkingId;
	}

	public void setParkingId(Integer parkingId) {
		this.parkingId = parkingId;
	}
	@Column(name = "community_no",length=32)
	public String getCommunityNo() {
		return communityNo;
	}

	public void setCommunityNo(String communityNo) {
		this.communityNo = communityNo;
	}
	
	@Column(name = "berthNo",length=16)
	public String getBerthNo() {
		return berthNo;
	}

	public void setBerthNo(String berthNo) {
		this.berthNo = berthNo;
	}
	@Column(name = "arrive_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	@Column(name = "license",length=20)
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	@Column(name = "report_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	@Column(name = "stop_type",length=4)
	public Short getStopType() {
		return stopType;
	}

	public void setStopType(Short stopType) {
		this.stopType = stopType;
	}
	@Column(name = "arrive_car_img_url",length=200)
	public String getArriveCarImgUrl() {
		return arriveCarImgUrl;
	}

	public void setArriveCarImgUrl(String arriveCarImgUrl) {
		this.arriveCarImgUrl = arriveCarImgUrl;
	}

	public Integer getPropertyNumber() {
		return propertyNumber;
	}

	public void setPropertyNumber(Integer propertyNumber) {
		this.propertyNumber = propertyNumber;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	
}
