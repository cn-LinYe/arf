package com.arf.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

/**
 * 用户活跃记录表
 */
@Entity(name="r_user_active_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_user_active_record_sequence")
public class UserActiveRecord extends BaseEntity<Long> {

	/**
     * 
     */
    private static final long serialVersionUID = 5421830082515027363L;
    
    private Date month;
    
    private Integer axdTimes;
    
    private Long parkingDuration;
    
    private String userName;
    
    private Integer isVip;
    
    private Integer isActive;
    
    private String redisKey;

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public Integer getAxdTimes() {
		return axdTimes;
	}

	public void setAxdTimes(Integer axdTimes) {
		this.axdTimes = axdTimes;
	}

	public Long getParkingDuration() {
		return parkingDuration;
	}

	public void setParkingDuration(Long parkingDuration) {
		this.parkingDuration = parkingDuration;
	}
    @Column(length=40)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	@Column(length=40)
	public String getRedisKey() {
		return redisKey;
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}
}
