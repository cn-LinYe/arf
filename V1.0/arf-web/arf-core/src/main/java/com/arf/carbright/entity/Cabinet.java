package com.arf.carbright.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "e_cabinet",uniqueConstraints={@UniqueConstraint(columnNames={"parkingId","cabinetNum"})})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cabinet_sequence")
public class Cabinet extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;

	private String parkingId;	//Varchar(20)		所属停车场id/小区id
	private String areaNum;	//Varchar(20)	唯一	所属区域编码
	private String areaName;	//Varchar(50)		所属区域名称
	private String cabinetNum;	//Varchar(20)	唯一	柜子编号
	private String cabinetName;	//Varchar(50)		柜子名称
	private String cabinetAddress;	//Varchar(50)		柜子安装地址
	private int status;	//tinyint		状态，0正常，1掉线
	private String remark;	//Varchar(500)		描述说明
	private int branchId;	//int		子公司或物业特有信息子公司唯一标识id
	private int headOfficeId;	//int		总公司id
	private int popertyNumber;	//int		物业唯一标识id
	private String bluetoothName;//柜子蓝牙名称 
	// 纬度
	private Double lat;
	// 经度
	private Double lng;
	
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}
	
	@Column(length = 20)
	public String getAreaNum() {
		return areaNum;
	}
	@Column(length = 50)
	public String getAreaName() {
		return areaName;
	}
	public int getStatus() {
		return status;
	}
	@Column(length = 20)
	public String getParkingId() {
		return parkingId;
	}

	@Column(length = 20)
	public String getCabinetNum() {
		return cabinetNum;
	}

	@Column(length = 50)
	public String getCabinetName() {
		return cabinetName;
	}

	@Column(length = 50)
	public String getCabinetAddress() {
		return cabinetAddress;
	}

	@Column(length = 500)
	public String getRemark() {
		return remark;
	}

	public int getBranchId() {
		return branchId;
	}

	public int getHeadOfficeId() {
		return headOfficeId;
	}

	public int getPopertyNumber() {
		return popertyNumber;
	}

	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}

	public void setCabinetNum(String cabinetNum) {
		this.cabinetNum = cabinetNum;
	}

	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}

	public void setCabinetAddress(String cabinetAddress) {
		this.cabinetAddress = cabinetAddress;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public void setHeadOfficeId(int headOfficeId) {
		this.headOfficeId = headOfficeId;
	}

	public void setPopertyNumber(int popertyNumber) {
		this.popertyNumber = popertyNumber;
	}
	
	public void setAreaNum(String areaNum) {
		this.areaNum = areaNum;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	@Column(length = 50)
	public String getBluetoothName() {
		return bluetoothName;
	}
	public void setBluetoothName(String bluetoothName) {
		this.bluetoothName = bluetoothName;
	}
}
