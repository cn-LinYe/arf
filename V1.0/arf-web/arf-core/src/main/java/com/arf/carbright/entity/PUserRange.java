package com.arf.carbright.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;
import com.arf.core.entity.OrderEntity;

/**
 * 套餐使用范围表
 * @author dg
 */
@Entity
@Table(name = "p_useRange",uniqueConstraints={@UniqueConstraint(columnNames={"useRangeNum"},name="unique_use_range_num"),
		@UniqueConstraint(columnNames={"parkingId","businessId"},name="unique_parkingId_businessId")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_userRange_sequence")
public class PUserRange extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  String useRangeNum;
	private  String parkingId;
	private  Integer businessId;
	//物业编号 
	private Integer propertyNumber;
	//子公司编号 
	private Integer branchId;
	
	@Column(name = "useRangeNum",length =20)
	public String getUseRangeNum() {
		return useRangeNum;
	}
	public void setUseRangeNum(String useRangeNum) {
		this.useRangeNum = useRangeNum;
	}
	@Column(name = "parkingId",length =20)
	public String getParkingId() {
		return parkingId;
	}
	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}
	@Column(name = "businessId")
	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
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
