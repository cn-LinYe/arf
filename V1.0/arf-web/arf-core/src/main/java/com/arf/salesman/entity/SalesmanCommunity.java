package com.arf.salesman.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name="r_salesman_community")
@SequenceGenerator(name="sequenceGenerator",sequenceName="r_salesman_community_sequence")
public class SalesmanCommunity extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2821877664013039364L;

	private String communityNumber	;//	小区编号
	private String communityName	;//	小区名称
	private String userName		;//	关联业务员账号
	
	@Column(length=40)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(length=40)
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	@Column(length=40)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
