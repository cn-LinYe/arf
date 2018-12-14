package com.arf.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 车牌表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "license_plate",
indexes={@Index(name="index_licenseplate_username",columnList="user_name"),
		@Index(name="lic_pla_num_index",columnList="license_plate_number"),
		@Index(name="index_license_plate_id",columnList="license_plate_id")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "license_plate_sequence")
public class LicensePlateModel extends BaseEntity<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1445155502664282808L;
	// private int id;
	private String license_plate_number;// 车牌号
	private String user_name; // 此为外键
	private long timeEnd; // 截止时间（vip默认为0）
	private String license_plate_id = "";// 自动生成的id号
	private String sessionID = "anerfa88888";// 凭证码
	private String state;// 车闸状态
	private int outOrInRecord = 0;
	private long time;// 上传车牌时间
	private int lockNumbers = 0;// 锁定状态
	private String engine_numbers = "";// 发动机号
	private String otherUserName;//另一个车主
	private String his_no="";// 车驾号
	/**
	 * 0：代表没验证（默认值）
	 * 1：代表已经被验证
	 */
	private int isVerification=0;// 车牌是否验证
	/** 验证次数**/
	private int times=0;//验证次数
	/** 验证时间**/
	private Date verificationTime=new Date(1);
	/**车牌允许出错次数*/
	private Integer allowErrorCount;
	/**剩余出错次数*/
	private Integer overErrorCount;
	/**上次激活时间*/
	private Date lastActiveTime;
	
	public LicensePlateModel(String license_plate_number, String user_name, long timeEnd, String license_plate_id) {
		super();
		this.license_plate_number = license_plate_number;
		this.user_name = user_name;
		this.timeEnd = timeEnd;
		this.license_plate_id = license_plate_id;
	}

	public LicensePlateModel(String license_plate_number, String user_name, long timeEnd, String license_plate_id,
			String his_no) {
		super();
		this.license_plate_number = license_plate_number;
		this.user_name = user_name;
		this.timeEnd = timeEnd;
		this.license_plate_id = license_plate_id;
		this.his_no = his_no;
	}

	public LicensePlateModel() {
		super();
	}

	public LicensePlateModel(String user_name, String license_plate_number) {
		super();
		this.user_name = user_name;
		this.license_plate_number = license_plate_number;
	}

	public LicensePlateModel(String license_plate_number, String user_name, String license_plate_id) {
		super();
		this.license_plate_number = license_plate_number;
		this.user_name = user_name;
		this.license_plate_id = license_plate_id;
	}

	// @Id
	// @Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// public int getId() {
	// return id;
	// }
	//
	// public void setId(int id) {
	// this.id = id;
	// }

	@Column(name = "user_name", length = 64, nullable = false)
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	@Column(name = "License_plate_number", length = 32, nullable = false)
	public String getLicense_plate_number() {
		return license_plate_number;
	}

	public void setLicense_plate_number(String license_plate_number) {
		this.license_plate_number = license_plate_number;
	}

	@Column(name = "License_plate_id", length = 32, nullable = false)
	public String getLicense_plate_id() {
		return license_plate_id;
	}

	public void setLicense_plate_id(String license_plate_id) {
		this.license_plate_id = license_plate_id;
	}

	@Column(name = "timeEnd", length = 32, nullable = false)
	public long getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(long timeEnd) {
		this.timeEnd = timeEnd;
	}

	@Column(name = "sessionID", length = 16)
	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	@Column(name = "state", length = 8)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "outOrInRecord", length = 8)
	public int getOutOrInRecord() {
		return outOrInRecord;
	}

	public void setOutOrInRecord(int outOrInRecord) {
		this.outOrInRecord = outOrInRecord;
	}

	@Column(name = "time", length = 16)
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Column(name = "his_no", length = 32)
	public String getHis_no() {
		return his_no;
	}

	public void setHis_no(String his_no) {
		this.his_no = his_no;
	}

	@Column(name = "engine_numbers", length = 20)
	public String getEngine_numbers() {
		return engine_numbers;
	}

	public void setEngine_numbers(String engine_numbers) {
		this.engine_numbers = engine_numbers;
	}

	@Column(name = "lockNumbers", nullable = false)
	public int getLockNumbers() {
		return lockNumbers;
	}

	public void setLockNumbers(int lockNumbers) {
		this.lockNumbers = lockNumbers;
	}

	@Column(name = "isVerification", nullable = false)
	public int getIsVerification() {
		return isVerification;
	}

	public void setIsVerification(int isVerification) {
		this.isVerification = isVerification;
	}

	@Column(name = "times", nullable = false)
	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	@Column(name = "verificationTime",nullable=false)
	public Date getVerificationTime() {
		return verificationTime;
	}

	public void setVerificationTime(Date verificationTime) {
		this.verificationTime = verificationTime;
	}
	@Column(name = "otherUserName")
	public String getOtherUserName() {
		return otherUserName;
	}
	public void setOtherUserName(String otherUserName) {
		this.otherUserName = otherUserName;
	}

    /**  
     * 获取车牌允许出错次数  
     * @return allowErrorCount 车牌允许出错次数  
     */
    public Integer getAllowErrorCount() {
        return allowErrorCount;
    }

    /**  
     * 设置车牌允许出错次数  
     * @param allowErrorCount 车牌允许出错次数  
     */
    public void setAllowErrorCount(Integer allowErrorCount) {
        this.allowErrorCount = allowErrorCount;
    }

    /**  
     * 获取剩余出错次数  
     * @return overErrorCount 剩余出错次数  
     */
    public Integer getOverErrorCount() {
        return overErrorCount;
    }

    /**  
     * 设置剩余出错次数  
     * @param overErrorCount 剩余出错次数  
     */
    public void setOverErrorCount(Integer overErrorCount) {
        this.overErrorCount = overErrorCount;
    }

    /**  
     * 获取上次激活时间  
     * @return lastActiveTime 上次激活时间  
     */
    public Date getLastActiveTime() {
        return lastActiveTime;
    }

    /**  
     * 设置上次激活时间  
     * @param lastActiveTime 上次激活时间  
     */
    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }
}
