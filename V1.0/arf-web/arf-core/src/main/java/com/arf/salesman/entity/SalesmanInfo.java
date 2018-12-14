package com.arf.salesman.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name="p_salesman_info")
@SequenceGenerator(name="sequenceGenerator",sequenceName="p_salesman_info_sequence")
public class SalesmanInfo extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5964970176003542238L;
	private String fullName	;//	姓名
	private String userName	;//	账号名称
	private String password	;//	账号密码
	private Integer branchId	;//	子公司名称
	private Byte status	;//	状态：0正常，1冻结
	private String avatarPic	;//	头像地址
	
	public enum Status{
		Normal,Frozen
	}
	
	@Column(length=40)
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@Column(length=40)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=40)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	@Column(length=100)
	public String getAvatarPic() {
		return avatarPic;
	}
	public void setAvatarPic(String avatarPic) {
		this.avatarPic = avatarPic;
	}
	
	
}
