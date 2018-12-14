package com.arf.repair.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="repair_sort_setting")
@SequenceGenerator(name="sequenceGenerator",sequenceName="repair_sort_setting_sequence")
public class RepairSortSetting extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sortName;//种类名称
	private String communityNumber;//小区编号
	private Status status;//状态 0 Normal正常 1 Discard 废弃
	
	@JsonFormat(shape=JsonFormat.Shape.NUMBER)
	public enum Status{
		Normal,//正常
		Discard;// 废弃
	}
	@Column(length=32)
	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
