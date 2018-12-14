package com.arf.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 停车场设备
 */
@Entity
@Table(name = "p_deviceinfo")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_deviceinfo_sequence")
public class PDeviceinfo extends BaseEntity<Long>{

	
	
	private static final long serialVersionUID = 4462538859664021752L;
	/**停车场ID**/
	private Integer parkingId;
	/**设备编号**/
	private String device_Num;
	/**设备类型(type)：0车牌识别设备,1安防监控设备2 诱导屏 **/
	private Integer device_Type;
	/** 状态：0停用,1正常,3异常,4故障,5维修**/
	private Integer status;
	/**主机名(host)**/
	private String host;
	/**用户名(user)**/
	private String user;
	/**密码(pwd)**/
	private String pwd;
	/**设备供应商类型0福瑞泰克1蓝鹏(host)**/
	private Integer supplier_type;
//	/**创建时间(host)**/
//	private Date create_time;
	/**修改时间(host)**/
	private Date modify_time;
	/**小区id**/
	private String communityNo;
	//物业编号 
	private Integer propertyNumber;
	//子公司编号 
	private Integer branchId;
	
	@Column(name = "communityNo")
	public String getCommunityNo() {
		return communityNo;
	}
	public void setCommunityNo(String communityNo) {
		this.communityNo = communityNo;
	}
	@Column(name = "parking_id")
	public Integer getParkingId() {
		return parkingId;
	}
	public void setParkingId(Integer parkingId) {
		this.parkingId = parkingId;
	}
	@Column(name = "device_Num")
	public String getDevice_Num() {
		return device_Num;
	}
	public void setDevice_Num(String device_Num) {
		this.device_Num = device_Num;
	}
	@Column(name = "device_Type")
	public Integer getDevice_Type() {
		return device_Type;
	}
	
	public void setDevice_Type(Integer device_Type) {
		this.device_Type = device_Type;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "host")
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	@Column(name = "user")
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	@Column(name = "pwd")
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	@Column(name = "supplier_type")
	public Integer getSupplier_type() {
		return supplier_type;
	}
	public void setSupplier_type(Integer supplier_type) {
		this.supplier_type = supplier_type;
	}
//	@Column(name = "create_time")
//	public Date getCreate_time() {
//		return create_time;
//	}
//	public void setCreate_time(Date create_time) {
//		this.create_time = create_time;
//	}
//	
	@Column(name = "modify_time")
	public Date getModify_time() {
		return modify_time;
	}
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
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
	@Override
	public String toString() {
		return "PDeviceinfo [parkingId=" + parkingId + ", device_Num=" + device_Num + ", device_Type=" + device_Type
				+ ", status=" + status + ", host=" + host + ", user=" + user + ", pwd=" + pwd + ", supplier_type="
				+ supplier_type + ", modify_time=" + modify_time + ", communityNo=" + communityNo + "]";
	}
}
