package com.arf.smart.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="p_city_custom_function_record")
@SequenceGenerator(name="sequenceGenerator",sequenceName="p_city_custom_function_record_sequence")
public class PCityCustomFunctionRecord  extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2460325879072128544L;
	
	private String cities;//参与城市（以“，”分割）
	private String orderList;//自定义图标顺序（以“，”分割）
	private IsPriorPlatform isPriorPlatform;//是否平台排序优先 0 NO 否1 YES 是
	private String remark;//备注
	private String communities;//参与小区（以“，”分割）
	private Date startDate;//上架日期
	private Date endDate;//下架日期
	private String startTime;//每天开始时间
	private String endTime;//每天结束时间
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IsPriorPlatform{
		No,
		Yes;
	}
	
	@Column(length=2000)
	public String getCities() {
		return cities;
	}
	public void setCities(String cities) {
		this.cities = cities;
	}
	@Column(length=100)
	public String getOrderList() {
		return orderList;
	}
	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}
	public IsPriorPlatform getIsPriorPlatform() {
		return isPriorPlatform;
	}
	public void setIsPriorPlatform(IsPriorPlatform isPriorPlatform) {
		this.isPriorPlatform = isPriorPlatform;
	}
	@Column(length=500)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length=4000)
	public String getCommunities() {
		return communities;
	}
	public void setCommunities(String communities) {
		this.communities = communities;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Column(length=10)
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Column(length=10)
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
