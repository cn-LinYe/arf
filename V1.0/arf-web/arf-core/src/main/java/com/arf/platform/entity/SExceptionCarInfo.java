package com.arf.platform.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.OrderEntity;

@Entity
@Table(name = "s_exception_car_info")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "s_exception_car_info_sequence")
public class SExceptionCarInfo extends OrderEntity<Long>{

	private static final long serialVersionUID = 1L;
	
	private Byte type;//异常车辆类型
	private String license;//车牌号
	private String note;//说明
	private Integer create_user_id;//创建人id
	private String create_user_name;//创建人名称
	private Date create_time;//创建时间
	private Date modify_time;//修改时间
	
	//小区编号
	private String communityNumber;
	//物业编号 
	private Integer propertyNumber;
	//子公司编号 
	private Integer branchId;
	
	@Column(name = "type",length =4)
	public Byte getType() {
		return type;
	}
	@Column(name = "license",length =20)
	public String getLicense() {
		return license;
	}
	@Column(name = "note",length =200)
	public String getNote() {
		return note;
	}
	@Column(name = "create_user_id",length =11)
	public Integer getCreate_user_id() {
		return create_user_id;
	}
	@Column(name = "create_user_name",length =20)
	public String getCreate_user_name() {
		return create_user_name;
	}
	@Column(name = "create_time")
	public Date getCreate_time() {
		return create_time;
	}
	@Column(name = "modify_time")
	public Date getModify_time() {
		return modify_time;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setCreate_user_id(Integer create_user_id) {
		this.create_user_id = create_user_id;
	}
	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public Integer getPropertyNumber() {
		return propertyNumber;
	}
	public void setPropertyNumber(Integer propertyNumber) {
		this.propertyNumber = propertyNumber;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	
}
