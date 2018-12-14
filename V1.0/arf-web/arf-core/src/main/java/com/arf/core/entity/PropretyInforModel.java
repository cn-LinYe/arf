package com.arf.core.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 物业费基本信息
 * @author Administrator
 *
 */

@Entity
@Table(name = "propretyInfor")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "propretyInfor_sequence")
public class PropretyInforModel extends BaseEntity<Long>{
	
    private static final long serialVersionUID = -3468655551588641806L;
    /** 楼栋名称*/
	private String buildingNumber;
	/** 单元号*/
	private String unitNumber;
	/** 楼层数 */
	private String floorNumber;
	/**房间名*/
	private String houseNmuber;
	/** 房间id*/
	private String houseId;
	/**房屋面积单位平分米*/
	private Double houseArea = 0.0;
	/**业主电话,必须用户名 */
	private String userName;
	/** 证件号*/
	private String cardNumber;
	/** 业主姓名 */
	private String name;
	/** 小区id*/
	private String communityNumber;
	/**小区名*/
	private String communityName;
	/** 物业id*/
	private int propretyId;
	/** 计费类型 1 按面积计费2:按照 月份计费3:按照年计费*/
	private int chargeType;
	/** 每平方单价*/
	private Double pricePerSquareMeter = 0.0;
	/** 按月计费*/
	private Double pricePerMonth = 0.0;
	/** 每年单价*/
	private Double pricePerYear = 0.0;
	/** 结束时间 */
	private Date endTime;
	/** 是否推送 */
	private Integer isPush=0;
	/** 推送时间*/
	private Date pushTime;
	//子公司编号 
	private Integer branchId;

	@Column(name = "buildingNumber", length = 32)
	public String getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	@Column(name = "unitNumber", length = 32)
	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	@Column(name = "houseNmuber", length = 32)
	public String getHouseNmuber() {
		return houseNmuber;
	}

	public void setHouseNmuber(String houseNmuber) {
		this.houseNmuber = houseNmuber;
	}

	@Column(name = "houseArea")
	public Double getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(Double houseArea) {
		this.houseArea = houseArea;
	}
	@Column(name = "userName",length = 64,nullable =false)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "communityNumber",length = 32)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(name = "communityName",length = 32)
	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	@Column(name = "propretyId")
	public int getPropretyId() {
		return propretyId;
	}

	public void setPropretyId(int propretyId) {
		this.propretyId = propretyId;
	}
	@Column(name = "chargeType")
	public int getChargeType() {
		return chargeType;
	}

	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}

	@Column(name = "pricePerSquareMeter")
	public Double getPricePerSquareMeter() {
		return pricePerSquareMeter;
	}

	public void setPricePerSquareMeter(Double pricePerSquareMeter) {
		this.pricePerSquareMeter = pricePerSquareMeter;
	}

	@Column(name = "pricePerMonth")
	public Double getPricePerMonth() {
		return pricePerMonth;
	}

	@Column(name = "pricePerYear")
	public Double getPricePerYear() {
		return pricePerYear;
	}

	@Column(name = "endTime",nullable=false)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Column(name = "cardNumber",length=32)
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	@Column(name = "name",length=32)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "floorNumber",length=32)
	public String getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber;
	}

	public void setPricePerMonth(Double pricePerMonth) {
		this.pricePerMonth = pricePerMonth;
	}
	public void setPricePerYear(Double pricePerYear) {
		this.pricePerYear = pricePerYear;
	}
	
	@Column(name = "houseId",length =32,unique=true)
	public String getHouseId() {
		return houseId;
	}
	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}
	
	@Column(name = "isPush")
	public Integer getIsPush() {
		return isPush;
	}

	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}
	@Column(name = "pushTime")
	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	
	
	
	
	
}
