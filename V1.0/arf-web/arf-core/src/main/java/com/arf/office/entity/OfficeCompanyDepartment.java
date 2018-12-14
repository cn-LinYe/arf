package com.arf.office.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "office_company_department")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "office_company_department_sequence")
public class OfficeCompanyDepartment extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4847637829549852301L;
	
	private String departmentNumber;//部门编号
	private String departmentName;//部门名称
	private String companyNumber;//所属公司
	private String contactPhone;//部门联系人电话
	private String contactName;//部门联系人姓名
	private Long parenteId;//父节点id
	
	@Column(length = 20)
	public String getDepartmentNumber() {
		return departmentNumber;
	}
	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}
	@Column(length = 40)
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	@Column(length = 20)
	public String getCompanyNumber() {
		return companyNumber;
	}
	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	@Column(length = 2000)
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	@Column(length = 20)
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public Long getParenteId() {
		return parenteId;
	}
	public void setParenteId(Long parenteId) {
		this.parenteId = parenteId;
	}

}
