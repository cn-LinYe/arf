package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 授权表
 * @author Administrator
 *
 */
@Entity
@Table(name = "devolution_license_plate")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "devolution_license_plate_sequence")
public class DevolutionLicensePlateModel extends BaseEntity<Long>{

	
	
	/**
     * 
     */
    private static final long serialVersionUID = 485750022100190723L;
//    private int id;
	private String license_plate_number;// 车牌号
	private String user_name; // 此为用户表外键
	private String license_plate_id = "";// 赋值主车牌号id
	private long start_time;// 授权开始时间
	private long timeEnd; // 截止时间（vip默认为0）
	
//	@Id
//	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
	
	@Column(name = "License_plate_number", length = 32, nullable = false)
	public String getLicense_plate_number() {
		return license_plate_number;
	}
	public void setLicense_plate_number(String license_plate_number) {
		this.license_plate_number = license_plate_number;
	}
	
	@Column(name = "user_name", length = 64, nullable = false)
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	@Column(name = "License_plate_id", length = 32, nullable = false)

	public String getLicense_plate_id() {
		return license_plate_id;
	}
	public void setLicense_plate_id(String license_plate_id) {
		this.license_plate_id = license_plate_id;
	}
	
	@Column(name = "start_time", length = 32, nullable = false)

	public long getStart_time() {
		return start_time;
	}
	public void setStart_time(long start_time) {
		this.start_time = start_time;
	}
	
	@Column(name = "time_end", length = 32)
	public long getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(long timeEnd) {
		this.timeEnd = timeEnd;
	}
	
	public DevolutionLicensePlateModel(String license_plate_number, String user_name, String license_plate_id,
			long start_time, long timeEnd) {
		super();
		this.license_plate_number = license_plate_number;
		this.user_name = user_name;
		this.license_plate_id = license_plate_id;
		this.start_time = start_time;
		this.timeEnd = timeEnd;
	}
	public DevolutionLicensePlateModel() {
		super();
	}
}
	
