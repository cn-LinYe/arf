package com.arf.silverleopard.entity;

import com.arf.core.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "p_tic_ket")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_tic_ket_sequence")
public class Ticket extends BaseEntity<Long>{

	private static final long serialVersionUID = 3808320433929640970L;

	private Long uid;			//收银员唯一标识
	private String jobNumber;	//收银员工号
	private String name;		//收银员姓名
	private String tel;		//收银员联系电话
	private String enable;	//收银员是否被禁用，0：禁用，1：启用
	private String appId;	//门店凭证
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	
}
