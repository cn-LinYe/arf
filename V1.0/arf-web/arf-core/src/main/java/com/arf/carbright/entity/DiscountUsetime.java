package com.arf.carbright.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "p_discount_usetime")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_discount_usetime_sequence")
public class DiscountUsetime extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5153386436406491210L;
	
	private String startTime	;//datetime	开始时间
	private String endTime	;//datetime	结束时间
	private String userWeek	;//Varchar(40)	如果类型是星期则用，隔开天数,如果
	private Date useDaysStartTime	;//dateTime  	使用开始时间
	private Date useDaysEndTime	;//dateTime 	使用结束时间  
	private Long discountId	;//long	优惠信息id
	private DiscountFrequency discountFrequency	;//int	限时优惠频率（0:按天1:按周2:按月）
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum DiscountFrequency{
		day,week,month;
		public static DiscountFrequency get(int ordinal){
			DiscountFrequency statuss[] = DiscountFrequency.values();
			if(ordinal > statuss.length - 1){
				return null;
			}else{
				return statuss[ordinal];
			}
		}
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Column(length=40)
	public String getUserWeek() {
		return userWeek;
	}
	public void setUserWeek(String userWeek) {
		this.userWeek = userWeek;
	}
	public Date getUseDaysStartTime() {
		return useDaysStartTime;
	}
	public void setUseDaysStartTime(Date useDaysStartTime) {
		this.useDaysStartTime = useDaysStartTime;
	}
	public Date getUseDaysEndTime() {
		return useDaysEndTime;
	}
	public void setUseDaysEndTime(Date useDaysEndTime) {
		this.useDaysEndTime = useDaysEndTime;
	}
	public Long getDiscountId() {
		return discountId;
	}
	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}
	public DiscountFrequency getDiscountFrequency() {
		return discountFrequency;
	}
	public void setDiscountFrequency(DiscountFrequency discountFrequency) {
		this.discountFrequency = discountFrequency;
	}
	
	
}
