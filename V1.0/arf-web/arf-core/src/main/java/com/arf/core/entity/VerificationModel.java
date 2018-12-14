package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 验证码存储表
 */
@Entity
@Table(name = "verification_code")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_verification_code_sequence")
public class VerificationModel extends BaseEntity<Long> {
	

	/**
     * 
     */
    private static final long serialVersionUID = -4469481319417942295L;

    /**
	 * 凭证码	
	 */
	private String verificationCodes;

	/**
	 * 账号（手机号）
	 */
	private String phone;

	/**
     * 当前时间
     */
	@Column(name="currentTime",length=21,nullable = false)
    private Long currentTimes;
    
	public VerificationModel() {
		super();
	}

	public VerificationModel(Long currentTimes, String verificationCodes, String phone) {
		super();
		this.currentTimes = currentTimes;
		this.verificationCodes = verificationCodes;
		this.phone = phone;
	}

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

//	@Column(name = "currentTime",nullable = false)
//	public long getCurrentTime() {
//		return currentTime;
//	}
//
//	public void setCurrentTime(long currentTime) {
//		this.currentTime = currentTime;
//	}

	
	@Column(name = "verificationCodes", length = 255, nullable = false)
	public String getVerificationCodes() {
		return verificationCodes;
	}

	public void setVerificationCodes(String verificationCodes) {
		this.verificationCodes = verificationCodes;
	}

	@Column(name = "phone", length = 20, nullable = false)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
    /**  
     * 获取当前时间  
     * @return currentTime 当前时间  
     */
    public Long getCurrentTimes() {
        return currentTimes;
    }
    /**  
     * 设置当前时间  
     * @param currentTime 当前时间  
     */
    public void setCurrentTimes(Long currentTimes) {
        this.currentTimes = currentTimes;
    }
}
