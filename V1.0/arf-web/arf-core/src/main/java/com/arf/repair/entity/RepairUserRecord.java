package com.arf.repair.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="repair_user_record")
@SequenceGenerator(name="sequenceGenerator",sequenceName="repair_user_record_sequence")
public class RepairUserRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1220826686652253385L;
	
	private String userName;//维修人手机号
	private String realName;//真实姓名
	private String communityNumber;//小区编号以”,”分割
	private String repairSort;//维修种类(维修种类id 以”,”分割)
	private Status status;//维修人员状态 0 Normal 正常 1 Exception 异常
	private String remark;//备注
	
	@JsonFormat(shape=JsonFormat.Shape.NUMBER)
	public enum Status{
		Normal,//正常
		Exception;// 异常
	}
	@Column(length=20)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=32)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	@Column(length=400)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(length=100)
	public String getRepairSort() {
		return repairSort;
	}

	public void setRepairSort(String repairSort) {
		this.repairSort = repairSort;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	@Column(length=200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
