package com.arf.platform.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.OrderEntity;

@Entity
@Table(name = "p_parkingrealtimestatus")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_parkingrealtimestatus_sequence")
public class PParKingrealTimeStatus extends OrderEntity<Long> {

	/**
	 * 新的缓存存储数据格式为一个Map,key为停车场parkingId,value为对应实体的json string
	 * {@link com.arf.platform.entity.PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status_Map}
	 */
	@Deprecated
	public static final String Key_Prefix_Cache_Parking_Realtime_Status = "PParKingrealTimeStatus.CacheParkingRealtimeStatus:";
	
	public static final String Key_Prefix_Cache_Parking_Realtime_Status_Map = "PParKingrealTimeStatus.CacheParkingRealtimeStatusMap";
	
	private static final long serialVersionUID = 1L;

	/**
	 * 停车场id/小区id
	 */
	private String parkingId;
	
	/**
	 * 停车场名/小区名
	 */
	private String parkingName;
	
	/**
	 * 总车位数量
	 */
	private Integer empty;
	
	/**
	 * 总空车位数量
	 */
	private Integer totalEmpty;
	
	/**
	 * 总可预定车位数
	 */
	private Integer sheduled;
	
	/**
	 * 空可预定车位
	 */
	private Integer rscheduled;

	/**
	 * 内部总车位数量
	 */
	private Integer internal;
	
	/**
	 * 内部空车位数量
	 */
	private Integer internalEmpty;
	
	/**
	 * 车位使用状态 0充足；1紧张；2已满
	 */
	private Short status;
	
	/**
	 * 是否掉线，0否，1是
	 */
	private Short isDrop;
	
	/**
	 * 最后汇报时间
	 */
	private Date lastTime;
	
	/**
	 * 子公司或物业特有信息子公司唯一标识id
	 */
	private Integer branchId;
	
	/**
	 * 总公司id
	 */
	private Integer headOfficeId;
	
	/**
	 * 物业唯一标识id
	 */
	private Integer popertyNumber;

	@Column(name = "parking_id",length =32)
	public String getParkingId() {
		return parkingId;
	}

	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}
	@Column(name = "parking_name",length =50)
	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	@Column(name = "empty")
	public Integer getEmpty() {
		return empty;
	}

	public void setEmpty(Integer empty) {
		this.empty = empty;
	}
	@Column(name = "total_empty")
	public Integer getTotalEmpty() {
		return totalEmpty;
	}

	public void setTotalEmpty(Integer totalEmpty) {
		this.totalEmpty = totalEmpty;
	}
	@Column(name = "sheduled")
	public Integer getSheduled() {
		return sheduled;
	}

	public void setSheduled(Integer sheduled) {
		this.sheduled = sheduled;
	}
	@Column(name = "rscheduled")
	public Integer getRscheduled() {
		return rscheduled;
	}

	public void setRscheduled(Integer rscheduled) {
		this.rscheduled = rscheduled;
	}
	@Column(name = "internal")
	public Integer getInternal() {
		return internal;
	}

	public void setInternal(Integer internal) {
		this.internal = internal;
	}
	@Column(name = "internal_empty")
	public Integer getInternalEmpty() {
		return internalEmpty;
	}

	public void setInternalEmpty(Integer internalEmpty) {
		this.internalEmpty = internalEmpty;
	}
	@Column(name = "status",length =4)
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	@Column(name = "is_drop",length =4)
	public Short getIsDrop() {
		return isDrop;
	}

	public void setIsDrop(Short isDrop) {
		this.isDrop = isDrop;
	}
	@Column(name = "last_time")
	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	@Column(name = "branch_id")
	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	@Column(name = "head_office_id")
	public Integer getHeadOfficeId() {
		return headOfficeId;
	}

	public void setHeadOfficeId(Integer headOfficeId) {
		this.headOfficeId = headOfficeId;
	}
	@Column(name = "poperty_number")
	public Integer getPopertyNumber() {
		return popertyNumber;
	}

	public void setPopertyNumber(Integer popertyNumber) {
		this.popertyNumber = popertyNumber;
	}
	
}
