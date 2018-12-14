/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity - 管理员
 * 
 * @author arf
 * @version 4.0
 */
@Entity
@Table(name = "xx_admin")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_admin_sequence")
public class Admin extends BaseEntity<Long> {

	private static final long serialVersionUID = -7519486823153844426L;

	/** "登录令牌"Cookie名称 */
	public static final String LOGIN_TOKEN_COOKIE_NAME = "adminLoginToken";
	
	/**
	 * 类型
	 */
	public enum Type {

		/** 管理员 */
		admin,

		/** 子公司 */
		company,
		
		/**物业*/
		property,
	}
	
	public enum State{
			
			/**等待审核*/
			pendingReview,
			
			/**通过审核*/
			through,
			
			/**拒绝*/
			refuse
			
	}
	

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;

	/** E-mail */
	private String email;

	/** 姓名 */
	private String name;

	/** 部门 */
	private String department;

	/** 是否启用 */
	private Boolean isEnabled;

	/** 是否锁定 */
	private Boolean isLocked;

	/** 连续登录失败次数 */
	private Integer loginFailureCount;

	/** 锁定日期 */
	private Date lockedDate;

	/** 最后登录日期 */
	private Date loginDate;

	/** 最后登录IP */
	private String loginIp;

	/** 锁定KEY */
	private String lockKey;

	/** 角色 */
	private Set<Role> roles = new HashSet<Role>();
	
	
	/** 余额 */
	private BigDecimal balance;
	
	
	/** 用户类型*/
	private Type type;
	
	/**公司网站*/
	private String webSite;
	
	/**电话号码*/
	private String tel;
	
	/**手机号码*/
	private String phone;
	
	/**地址*/
	private Area area;
	
	/**详细地址*/
	private String address;
	
	/**营业执照*/
	private String licenseImage;
	
	/**开户行*/
	private String bank;
	
	/**开户支行*/
	private String bankBranch;
	
	/**开户名*/
	private String bankName;
	
	/**开户卡号*/
	private String bankNumber;
	
	/**是否审核*/
	private State isChecked;

	/**会员(商户)*/
	private Set<Member> members=new HashSet<Member>();
	/**凭证码*/
	private String documentCode;
	/**物业id*/
	private Integer belongsid;
	/**登录类型*/
	private Integer login_type;
	
	/**  
     * 获取凭证码  
     * @return documentCode 凭证码  
     */
	@Column(name="documentCode",length=64)
    public String getDocumentCode() {
        return documentCode;
    }

    /**  
     * 设置凭证码  
     * @param documentCode 凭证码  
     */
    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    /**  
     * 获取物业id  
     * @return belongsid 物业id  
     */
    @Column(name="belongs_id")
    public Integer getBelongsid() {
        return belongsid;
    }

    /**  
     * 设置物业id  
     * @param belongsid 物业id  
     */
    public void setBelongsid(Integer belongsid) {
        this.belongsid = belongsid;
    }

    /**  
     * 获取登录类型  
     * @return login_type 登录类型  
     */
    @Column(name="login_type")
    public Integer getLogin_type() {
        return login_type;
    }

    /**  
     * 设置登录类型  
     * @param login_type 登录类型  
     */
    public void setLogin_type(Integer login_type) {
        this.login_type = login_type;
    }

	/** 账号记录 */
	private List<AdminProLog> adminProLogs = new ArrayList<AdminProLog>();
	
	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$")
	@Length(min = 11, max = 20)
	@Column(nullable = false, updatable = false, unique = true)
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 * 
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取密码
	 * 
	 * @return 密码
	 */
	@NotEmpty(groups = Save.class)
	@Length(min = 4, max = 20)
	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 * 
	 * @param password
	 *            密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取E-mail
	 * 
	 * @return E-mail
	 */
	@NotEmpty
	@Email
	@Length(max = 200)
	@Column(nullable = false)
	public String getEmail() {
		return email;
	}

	/**
	 * 设置E-mail
	 * 
	 * @param email
	 *            E-mail
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取姓名
	 * 
	 * @return 姓名
	 */
	@Length(max = 200)
	public String getName() {
		return name;
	}

	/**
	 * 设置姓名
	 * 
	 * @param name
	 *            姓名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取部门
	 * 
	 * @return 部门
	 */
	@Length(max = 200)
	public String getDepartment() {
		return department;
	}

	/**
	 * 设置部门
	 * 
	 * @param department
	 *            部门
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * 获取是否启用
	 * 
	 * @return 是否启用
	 */
	@NotNull
	@Column(nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * 设置是否启用
	 * 
	 * @param isEnabled
	 *            是否启用
	 */
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * 获取是否锁定
	 * 
	 * @return 是否锁定
	 */
	@Column(nullable = false)
	public Boolean getIsLocked() {
		return isLocked;
	}

	/**
	 * 设置是否锁定
	 * 
	 * @param isLocked
	 *            是否锁定
	 */
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * 获取连续登录失败次数
	 * 
	 * @return 连续登录失败次数
	 */
	@Column(nullable = false)
	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	/**
	 * 设置连续登录失败次数
	 * 
	 * @param loginFailureCount
	 *            连续登录失败次数
	 */
	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	/**
	 * 获取锁定日期
	 * 
	 * @return 锁定日期
	 */
	public Date getLockedDate() {
		return lockedDate;
	}

	/**
	 * 设置锁定日期
	 * 
	 * @param lockedDate
	 *            锁定日期
	 */
	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	/**
	 * 获取最后登录日期
	 * 
	 * @return 最后登录日期
	 */
	public Date getLoginDate() {
		return loginDate;
	}

	/**
	 * 设置最后登录日期
	 * 
	 * @param loginDate
	 *            最后登录日期
	 */
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	/**
	 * 获取最后登录IP
	 * 
	 * @return 最后登录IP
	 */
	public String getLoginIp() {
		return loginIp;
	}

	/**
	 * 设置最后登录IP
	 * 
	 * @param loginIp
	 *            最后登录IP
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	/**
	 * 获取锁定KEY
	 * 
	 * @return 锁定KEY
	 */
	@Column(nullable = false, updatable = false)
	public String getLockKey() {
		return lockKey;
	}

	/**
	 * 设置锁定KEY
	 * 
	 * @param lockKey
	 *            锁定KEY
	 */
	public void setLockKey(String lockKey) {
		this.lockKey = lockKey;
	}

	/**
	 * 获取角色
	 * 
	 * @return 角色
	 */
	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_admin_role")
	public Set<Role> getRoles() {
		return roles;
	}


	/**
	 * 设置角色
	 * 
	 * @param roles
	 *            角色
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		setLockKey(DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30)));
	}

	/***
	 * 获取类型
	 * @return
	 */
	@Column(nullable = false, updatable = false)
	public Type getType() {
		return type;
	}

	/***
	 * 
	 * @param type
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**  
	 * 获取公司网站  
	 * @return webSite 公司网站  
	 */
	public String getWebSite() {
		return webSite;
	}
	

	/**  
	 * 设置公司网站  
	 * @param webSite 公司网站  
	 */
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	

	/**  
	 * 获取电话号码  
	 * @return tel 电话号码  
	 */
	public String getTel() {
		return tel;
	}
	

	/**  
	 * 设置电话号码  
	 * @param tel 电话号码  
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	

	/**  
	 * 获取手机号码  
	 * @return phone 手机号码  
	 */
	@Length(max = 200)
	public String getPhone() {
		return phone;
	}
	

	/**  
	 * 设置手机号码  
	 * @param phone 手机号码  
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	

	/**  
	 * 获取地址  
	 * @return area 地址  
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Area getArea() {
		return area;
	}
	

	/**  
	 * 设置地址  
	 * @param area 地址  
	 */
	public void setArea(Area area) {
		this.area = area;
	}
	

	/**  
	 * 获取详细地址  
	 * @return address 详细地址  
	 */
	public String getAddress() {
		return address;
	}
	

	/**  
	 * 设置详细地址  
	 * @param address 详细地址  
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	

	/**  
	 * 获取营业执照  
	 * @return licenseImage 营业执照  
	 */
	public String getLicenseImage() {
		return licenseImage;
	}
	

	/**  
	 * 设置营业执照  
	 * @param licenseImage 营业执照  
	 */
	public void setLicenseImage(String licenseImage) {
		this.licenseImage = licenseImage;
	}
	

	/**  
	 * 获取开户行  
	 * @return bank 开户行  
	 */
	public String getBank() {
		return bank;
	}
	

	/**  
	 * 设置开户行  
	 * @param bank 开户行  
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}
	

	/**  
	 * 获取开户支行  
	 * @return bankBranch 开户支行  
	 */
	public String getBankBranch() {
		return bankBranch;
	}
	

	/**  
	 * 设置开户支行  
	 * @param bankBranch 开户支行  
	 */
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	

	/**  
	 * 获取开户名  
	 * @return bankName 开户名  
	 */
	public String getBankName() {
		return bankName;
	}
	

	/**  
	 * 设置开户名  
	 * @param bankName 开户名  
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	

	/**  
	 * 获取开户卡号  
	 * @return bankNumber 开户卡号  
	 */
	public String getBankNumber() {
		return bankNumber;
	}
	

	/**  
	 * 设置开户卡号  
	 * @param bankNumber 开户卡号  
	 */
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	

	/**  
	 * 获取是否审核  
	 * @return isChecked 是否审核  
	 */
	public State getIsChecked() {
		return isChecked;
	}
	

	/**  
	 * 设置是否审核  
	 * @param isChecked 是否审核  
	 */
	public void setIsChecked(State isChecked) {
		this.isChecked = isChecked;
	}
	

	/**  
	 * 获取余额  
	 * @return balance 余额  
	 */
	@Column(nullable = false, precision = 27, scale = 12)
	public BigDecimal getBalance() {
		return balance;
	}
	
	/**  
	 * 设置余额  
	 * @param balance 余额  
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**  
	 * 获取会员(商户)  
	 * @return members 会员(商户)  
	 */
	@OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Member> getMembers() {
		return members;
	}
	

	/**  
	 * 设置会员(商户)  
	 * @param members 会员(商户)  
	 */
	public void setMembers(Set<Member> members) {
		this.members = members;
	}

	/**  
	 * 获取账号记录  
	 * @return adminProLogs 账号记录  
	 */
	@OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate desc")
	public List<AdminProLog> getAdminProLogs() {
		return adminProLogs;
	}
	

	/**  
	 * 设置账号记录  
	 * @param adminProLogs 账号记录  
	 */
	public void setAdminProLogs(List<AdminProLog> adminProLogs) {
		this.adminProLogs = adminProLogs;
	}
	
	
	

}