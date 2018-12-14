package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 指令表  不光只有锁车指令,所有的指令都会存入这张表
 * @author Caolibao
 *
 */
@Entity
@Table(name = "locklicenseplatemodel")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "locklicenseplatemodel_sequence")
@Deprecated
public class LockLicensePlateModel extends BaseEntity<Long> {
	
	/**
     * 
     */
    private static final long serialVersionUID = -6390243946602009096L;
//    private int id;
	private String community_number;//小区id
	private String license_plate_number;//车牌号
	private String license_plate_id;//车牌id
	private int license_plate_command;//车牌控制
	private int camera_exception;//摄像机异常
	private int commderParameter;//控制参数
	private long end_payment_time;//结束时间
	private long start_payment_time;//开始时间
	private long timeoutPeriod;//设置操作时间
	
//	@Id
//	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	public int getId() {
//		return id;
//	}
//	
//	public void setId(int id) {
//		this.id = id;
//	}
	
	@Column(name = "community_number",  nullable = false)
	public String getCommunity_number() {
		return community_number;
	}
	
	public void setCommunity_number(String community_number) {
		this.community_number = community_number;
	}
	
	@Column(name = "license_plate_number", length = 32, nullable = false)
	public String getLicense_plate_number() {
		return license_plate_number;
	}
	
	public void setLicense_plate_number(String license_plate_number) {
		this.license_plate_number = license_plate_number;
	}
	
	@Column(name = "license_plate_id",nullable = false)
	public String getLicense_plate_id() {
		return license_plate_id;
	}
	
	public void setLicense_plate_id(String license_plate_id) {
		this.license_plate_id = license_plate_id;
	}
	
	@Column(name = "license_plate_command")
	public int getLicense_plate_command() {
		return license_plate_command;
	}
	
	public void setLicense_plate_command(int license_plate_command) {
		this.license_plate_command = license_plate_command;
	}
	
	
	@Column(name = "camera_exception")
	public int getCamera_exception() {
		return camera_exception;
	}
	
	public void setCamera_exception(int camera_exception) {
		this.camera_exception = camera_exception;
	}
	
	@Column(name = "commderParameter",length =64)
	public int getCommderParameter() {
		return commderParameter;
	}
	public void setCommderParameter(int commderParameter) {
		this.commderParameter = commderParameter;
	}
	@Column(name = "end_payment_time",length =32)
	public long getEnd_payment_time() {
		return end_payment_time;
	}
	public void setEnd_payment_time(long end_payment_time) {
		this.end_payment_time = end_payment_time;
	}
	
	@Column(name = "start_payment_time",length =32)
	public long getStart_payment_time() {
		return start_payment_time;
	}
	
	
	
	@Column(name = "timeoutPeriod")
	public long getTimeoutPeriod() {
		return timeoutPeriod;
	}

	public void setTimeoutPeriod(long timeoutPeriod) {
		this.timeoutPeriod = timeoutPeriod;
	}

	public void setStart_payment_time(long start_payment_time) {
		this.start_payment_time = start_payment_time;
	}
	
	public LockLicensePlateModel() {
		super();
	}
	
	public LockLicensePlateModel(String community_number, String license_plate_number, String license_plate_id,
			int license_plate_command, int camera_exception, int commderParameter,
			long end_payment_time, long start_payment_time) {
		super();
		this.community_number = community_number;
		this.license_plate_number = license_plate_number;
		this.license_plate_id = license_plate_id;
		this.license_plate_command = license_plate_command;
		this.camera_exception = camera_exception;
		this.commderParameter = commderParameter;
		this.end_payment_time = end_payment_time;
		this.start_payment_time = start_payment_time;
	}


	public LockLicensePlateModel(String community_number, String license_plate_number, String license_plate_id,
			int license_plate_command, int commderParameter) {
		super();
		this.community_number = community_number;
		this.license_plate_number = license_plate_number;
		this.license_plate_id = license_plate_id;
		this.license_plate_command = license_plate_command;
		this.commderParameter = commderParameter;
	}

	public LockLicensePlateModel(String community_number, String license_plate_number, String license_plate_id,
			int license_plate_command, int commderParameter, long timeoutPeriod) {
		super();
		this.community_number = community_number;
		this.license_plate_number = license_plate_number;
		this.license_plate_id = license_plate_id;
		this.license_plate_command = license_plate_command;
		this.commderParameter = commderParameter;
		this.timeoutPeriod = timeoutPeriod;
	}

	public LockLicensePlateModel(String community_number, String license_plate_number, String license_plate_id) {
		super();
		this.community_number = community_number;
		this.license_plate_number = license_plate_number;
		this.license_plate_id = license_plate_id;
	}	
	
}

