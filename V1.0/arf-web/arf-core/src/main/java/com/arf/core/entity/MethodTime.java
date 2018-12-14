package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "method_time")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "method_time_sequence")
public class MethodTime extends BaseEntity<Long>{
	
	private static final long serialVersionUID = -1079231545329611289L;
	
	private String methodName;
	private String arguments;
	private Long startTime;
	private Long endTime;
	private Long time;
	private String userName;
	
	@Column(length=150)
	public String getMethodName() {
		return methodName;
	}
	@Column(length=4000)
	public String getArguments() {
		return arguments;
	}
	@Column(length=16)
	public Long getStartTime() {
		return startTime;
	}
	@Column(length=16)
	public Long getEndTime() {
		return endTime;
	}
	@Column(length=13)
	public Long getTime() {
		return time;
	}
	@Column(length=20)
	public String getUserName() {
		return userName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
