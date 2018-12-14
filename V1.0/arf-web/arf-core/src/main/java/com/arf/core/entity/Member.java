/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.entity;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.annotation.JSONField;
import com.arf.core.interceptor.MemberInterceptor;
import com.arf.core.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Entity - 会员
 * 
 * @author arf
 * @version 4.0
 */
@Entity
@Table(name = "xx_member",
indexes={@Index(name="INDEX_COMMUITY_member",columnList="community_number")},
uniqueConstraints={@UniqueConstraint(columnNames={"username"},name="UK_jl89bhr7i6ei7ca9iyrjn1wa1")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_member_sequence")

public class Member extends BaseEntity<Long> {

	private static final long serialVersionUID = 1533130686714725835L;
	
	private IsOpened isOpened;
	private IsSpeed isSpeed;
	
	private String accessList;//生效门禁
	
	/**
	 * 是否开启声音
	 */
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IsOpened {

		/*开启*/
		TRUE,

		/** 关闭 */
		FALSE,
		
	}
	/**
	 * 是否开启极速模式
	 */
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IsSpeed {
		
		/*开启*/
		TRUE,
		
		/** 关闭 */
		FALSE,
		
	}
	
	public IsOpened getIsOpened() {
		return isOpened;
	}
	public void setIsOpened(IsOpened isOpened) {
		this.isOpened = isOpened;
	}
	public IsSpeed getIsSpeed() {
		return isSpeed;
	}
	public void setIsSpeed(IsSpeed isSpeed) {
		this.isSpeed = isSpeed;
	}
	@Column(length=200)
	public String getAccessList() {
		return accessList;
	}
	public void setAccessList(String accessList) {
		this.accessList = accessList;
	}
	/**
	 * 类型
	 */
	public enum Type {

		/** 会员 */
		member,

		/** 商户 */
		memberBip,
		
	}
		
	/**
	 * 性别
	 */
	public enum Gender {

		/** 男 */
		male,

		/** 女 */
		female;
		
		public static Gender getGender(int index){
			if(index == 0){
				return male;
			}else if(index == 1){
				return female;
			}else{
				return null;
			}
		}
	}

	/**
	 * 排名类型
	 */
	public enum RankingType {

		/** 积分 */
		point,

		/** 余额 */
		balance,

		/** 消费金额 */
		amount,
		
		/** 创建时间 */
		createDate,
	}
	/**
	 * 会员状态
	 */
	public enum State{
		
		/**等待审核*/
		pendingReview,
		
		/**通过审核*/
		through,
		
		/**拒绝*/
		refuse
		
	}
	/**是新增or修改*/
	public enum Neworup {
		/** 新增 */
		isnew,		
		/**修改*/
		isup,	
	}
	/**是否vip*/
	public enum Vip{
		/**不是vip*/
		notvip,
		/** 是vip*/
		vip
	}
	/** "身份信息"属性名称 */
	public static final String PRINCIPAL_ATTRIBUTE_NAME = MemberInterceptor.class.getName() + ".PRINCIPAL";

	/** "用户名"Cookie名称 */
	public static final String USERNAME_COOKIE_NAME = "username";

	/** "昵称"Cookie名称 */
	public static final String NICKNAME_COOKIE_NAME = "nickname";

	/** 会员注册项值属性个数 */
	public static final int ATTRIBUTE_VALUE_PROPERTY_COUNT = 10;

	/** 会员注册项值属性名称前缀 */
	public static final String ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "attributeValue";

	/** 最大收藏商品数 */
	public static final Integer MAX_FAVORITE_COUNT = 10;

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;

	/** E-mail */
	private String email;

	/** 昵称 */
	private String nickname;
	
	/** 积分 */
	private Long point;

	/** 余额 */
	private BigDecimal balance;

	/** 消费金额 */
	private BigDecimal amount;
	
	/** 是否启用 */
	private Boolean isEnabled;
	
	/** 是否锁定 */
	private Boolean isLocked;

	/** 连续登录失败次数 */
	private Integer loginFailureCount;

	/** 锁定日期 */
	private Date lockedDate;

	/** 注册IP */
	private String registerIp;

	/** 最后登录IP */
	private String loginIp;

	/** 最后登录日期 */
	private Date loginDate;

	/** 姓名 */
	private String name;

	/** 性别 */
	private Gender gender;

	/** 出生日期 */
	private Date birth;

	/** 地址 */
	private String address;

	/** 邮编 */
	private String zipCode;

	/** 电话 */
	private String phone;

	/** 手机 */
	private String mobile;

	/** 登录插件ID */
	private String loginPluginId;

	/** openID */
	private String openId;

	/** 锁定KEY */
	private String lockKey;

	/** 会员注册项值0 */
	private String attributeValue0;

	/** 会员注册项值1 */
	private String attributeValue1;

	/** 会员注册项值2 */
	private String attributeValue2;

	/** 会员注册项值3 */
	private String attributeValue3;

	/** 会员注册项值4 */
	private String attributeValue4;

	/** 会员注册项值5 */
	private String attributeValue5;

	/** 会员注册项值6 */
	private String attributeValue6;

	/** 会员注册项值7 */
	private String attributeValue7;

	/** 会员注册项值8 */
	private String attributeValue8;

	/** 会员注册项值9 */
	private String attributeValue9;

	/** 安全密匙 */
	private SafeKey safeKey;
	
	/**用户类型*/
	private Type type;
	
	/**开户行*/
	private String bank;
	
	/**开户支行*/
	private String bankBranch;
	
	/**开户名*/
	private String bankName;
	
	/**开户卡号*/
	private String bankNumber;
	
	/**会员状态*/
	private State state;

	/** 备注 */
	private String memo;
	/** 商户公司名*/
	private String companyName;
	/**用户唯一标识ID*/
	private String keyId;
	/**
	 * 注册方式0代表普通注册1代表物业上传
	 */
	private int registerWay=0;
	/**开通vip时间*/
	private Date vipTime; 
	
	/**
	 * 通过上传开vip的车牌
	 */
	private String spLicNum;
	//物业编号 
	private Integer propertyNumber;
	//子公司编号 
	private Integer branchId;
	//所在行政区域编码
	private String areaCode;
	//所属行业
	private String industry;
	
	private Date signTime;//签到时间
	private Integer isPushSign;//是否推送签到后设置为0推送后设置为1
	private Deductions deductions;//扣费状态0 开启 1关闭
	
	private String defaultLicense;//用户默认车牌
	
	public enum IsPushSign{
		NotPush,HavePush
	}
	
	public enum Deductions{
		Open,//0 开启
		Close//1关闭
		;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public Integer getIsPushSign() {
		return isPushSign;
	}
	public void setIsPushSign(Integer isPushSign) {
		this.isPushSign = isPushSign;
	}
	@Column(name = "spLicNum")
	public String getSpLicNum() {
		return spLicNum;
	}
	public void setSpLicNum(String spLicNum) {
		this.spLicNum = spLicNum;
	}

	/**发送短信次数*/
	private Integer sendMessageTimes=0;
	
	@Column(name = "sendMessageTimes")
	public Integer getSendMessageTimes() {
		return sendMessageTimes;
	}
	public void setSendMessageTimes(Integer sendMessageTimes) {
		this.sendMessageTimes = sendMessageTimes;
	}

	@Column(name = "vipTime")
	public Date getVipTime() {
		return vipTime;
	}

	public void setVipTime(Date vipTime) {
		this.vipTime = vipTime;
	}

	/**  
     * 获取用户唯一标识ID  
     * @return keyId 用户唯一标识ID  
     */
	@Column(name = "keyId",nullable=false,length=32,updatable=false)
    public String getKeyId() {
        return keyId;
    }

    /**  
     * 设置用户唯一标识ID  
     * @param keyId 用户唯一标识ID  
     */
    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    @Column(name = "registerWay",nullable=false)
	public int getRegisterWay() {
		return registerWay;
	}

	public void setRegisterWay(int registerWay) {
		this.registerWay = registerWay;
	}
	
	/**  
     * 获取商户公司名  
     * @return companyName 商户公司名  
     */
    public String getCompanyName() {
        return companyName;
    }

    /**  
     * 设置商户公司名  
     * @param companyName 商户公司名  
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }




    /**
	 * 获取备注
	 * @return the 备注
	 */
	public String getMemo() {
		return memo;
	}
	



	/**
	 * 设置备注
	 * @param 备注 the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	



	/**
	 * 获取会员状态
	 * @return the 会员状态
	 */
	public State getState() {
		return state;
	}
	


	/**
	 * 设置会员状态
	 * @param 会员状态 
	 */
	public void setState(State state) {
		this.state = state;
	}
	

	/**管理员(子公司)*/
	private Admin admin;
	
	
	
	/**
	 * 获取开户行
	 * @return the 开户行
	 */
	public String getBank() {
		return bank;
	}
	

	/**
	 * 设置开户行
	 * @param 开户行 the bank to set
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}
	

	/**
	 * 获取开户支行
	 * @return the 开户支行
	 */
	public String getBankBranch() {
		return bankBranch;
	}
	

	/**
	 * 设置开户支行
	 * @param 开户支行 the bankBranch to set
	 */
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	

	/**
	 * 获取开户名
	 * @return the 开户名
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * 设置开户名
	 * @param 开户名 the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	

	/**
	 * 获取开户卡号
	 * @return the 开户卡号
	 */
	public String getBankNumber() {
		return bankNumber;
	}
	

	/**
	 * 设置开户卡号
	 * @param 开户卡号 the bankNumber to set
	 */
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	

	/**  
	 * 获取用户类型  
	 * @return type 用户类型  
	 */
	public Type getType() {
		return type;
	}

	/**  
	 * 设置用户类型  
	 * @param type 用户类型  
	 */
	public void setType(Type type) {
		this.type = type;
	}
	/** 地区 */
	private Area area;
	/** 会员等级 */
	private MemberRank memberRank;
	/** 购物车 */
	private Cart cart;
	/** 订单 */
	private Set<Order> orders = new HashSet<Order>();
	/** 支付记录 */
	private Set<PaymentLog> paymentLogs = new HashSet<PaymentLog>();
	/** 预存款记录 */
	private Set<DepositLog> depositLogs = new HashSet<DepositLog>();
	/** 优惠码 */
	private Set<CouponCode> couponCodes = new HashSet<CouponCode>();
	/** 收货地址 */
	private Set<Receiver> receivers = new HashSet<Receiver>();
	/** 评论 */
	private Set<Review> reviews = new HashSet<Review>();
	/** 所属商户评论 */
	private Set<Review> memberBipreviews = new HashSet<Review>();
	/** 咨询 */
	private Set<Consultation> consultations = new HashSet<Consultation>();
	/** 收藏货品 */
	private Set<Goods> favoriteGoods = new HashSet<Goods>();
	/** 到货通知 */
	private Set<ProductNotify> productNotifies = new HashSet<ProductNotify>();
	/** 接收的消息 */
	private Set<Message> inMessages = new HashSet<Message>();
	/** 发送的消息 */
	private Set<Message> outMessages = new HashSet<Message>();
	/** 积分记录 */
	private Set<PointLog> pointLogs = new HashSet<PointLog>();
	/** 凭证码 **/
	private String documentCode;
	/** 买锁返现次数 */
	private Integer lock_Count;
	/** 是否激活 */
	private Integer activation;
	/** 是否vip */
	private Integer vip;
	/** 消费劵(累积) */
	private BigDecimal vouchers;
	/** 小区编号 */
	private String communityNumber="0";
	/** 是否发送短信 0发送1不发 */
	private String isSendMessage;
	/** 是否推送消息 */
    private String isSendPush;
	/** 授权码 */
	private String authorizationCode;
	/** 授权时间 */
	private Date authorizationTime;
	/** 设备
	 *	0、没有   1、Android 2、ios 
	 */
	private Integer currentEquipment;
	/** 用户级别 */
	private Integer userlevel;
	/** 车驾号 */
	private String driving_license_number;
	/** 车驾号开始时间 */
	private Date drivingLicense_startTime;
	/** 车驾号结束时间 */
	private Date drivingLicenseEndTime;
	/**新增or修改*/
	private Neworup neworup;
	/**用户头像地址*/
	private String photo;
	/**小区支付装态*/
	private Integer communityApplyState;
	/***/
	private String communityWanting;
	/**用户首次绑定的小区*/
	private String mainCommunity;
	
	/** 办公室门禁是否推送消息 */
    private String accessIsSendPush;
	
	/**  
     * 获取用户首次绑定的小区  
     * @return mainCommunity 用户首次绑定的小区  
     */
	@Column(name="main_community",length=64)
    public String getMainCommunity() {
        return mainCommunity;
    }
    /**  
     * 设置用户首次绑定的小区  
     * @param mainCommunity 用户首次绑定的小区  
     */
    public void setMainCommunity(String mainCommunity) {
        this.mainCommunity = mainCommunity;
    }
    /**获取状态
	 * @return the 新增or修改
	 */
	public Neworup getNeworup() {
		return neworup;
	}


	/**设置状态
	 * @param 新增or修改 the neworup to set
	 */
	public void setNeworup(Neworup neworup) {
		this.neworup = neworup;
	}
	/**  
     * 获取用户级别  
     * @return userlevel 用户级别  
     */
	@Column(name="user_level")
    public Integer getUserlevel() {
        return userlevel;
    }


    /**  
     * 设置用户级别  
     * @param userlevel 用户级别  
     */
    public void setUserlevel(Integer userlevel) {
        this.userlevel = userlevel;
    }


    /**  
     * 获取车驾号  
     * @return driving_license_number 车驾号  
     */
    @Column(name="driving_license_number")
    public String getDriving_license_number() {
        return driving_license_number;
    }


    /**  
     * 设置车驾号  
     * @param driving_license_number 车驾号  
     */
    public void setDriving_license_number(String driving_license_number) {
        this.driving_license_number = driving_license_number;
    }


    /**  
     * 获取车驾号开始时间  
     * @return drivingLicense_startTime 车驾号开始时间  
     */
    @Column(name="drivingLicense_startTime")
    public Date getDrivingLicense_startTime() {
        return drivingLicense_startTime;
    }


    /**  
     * 设置车驾号开始时间  
     * @param drivingLicense_startTime 车驾号开始时间  
     */
    public void setDrivingLicense_startTime(Date drivingLicense_startTime) {
        this.drivingLicense_startTime = drivingLicense_startTime;
    }


    /**  
     * 获取车驾号结束时间  
     * @return drivingLicenseEndTime 车驾号结束时间  
     */
    @Column(name="drivingLicenseEndTime")
    public Date getDrivingLicenseEndTime() {
        return drivingLicenseEndTime;
    }


    /**  
     * 设置车驾号结束时间  
     * @param drivingLicenseEndTime 车驾号结束时间  
     */
    public void setDrivingLicenseEndTime(Date drivingLicenseEndTime) {
        this.drivingLicenseEndTime = drivingLicenseEndTime;
    }


    /**
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$")
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
	 * 获取昵称
	 * 
	 * @return 昵称
	 */
	@Length(max = 200)
	public String getNickname() {
		return nickname;
	}

	/**
	 * 设置昵称
	 * 
	 * @param nickname
	 *            昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * 获取积分
	 * 
	 * @return 积分
	 */
	@Column(nullable = false)
	public Long getPoint() {
		return point;
	}

	/**
	 * 设置积分
	 * 
	 * @param point
	 *            积分
	 */
	public void setPoint(Long point) {
		this.point = point;
	}

	/**
	 * 获取余额
	 * 
	 * @return 余额
	 */
	@Column(nullable = false, precision = 27, scale = 12)
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * 设置余额
	 * 
	 * @param balance
	 *            余额
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * 获取消费金额
	 * 
	 * @return 消费金额
	 */
	@Column(nullable = false, precision = 27, scale = 12)
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置消费金额
	 * 
	 * @param amount
	 *            消费金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
	 * 获取注册IP
	 * 
	 * @return 注册IP
	 */
	@Column(nullable = false, updatable = false)
	public String getRegisterIp() {
		return registerIp;
	}

	/**
	 * 设置注册IP
	 * 
	 * @param registerIp
	 *            注册IP
	 */
	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
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
	 * 获取性别
	 * 
	 * @return 性别
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * 设置性别
	 * 
	 * @param gender
	 *            性别
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * 获取出生日期
	 * 
	 * @return 出生日期
	 */
	public Date getBirth() {
		return birth;
	}

	/**
	 * 设置出生日期
	 * 
	 * @param birth
	 *            出生日期
	 */
	public void setBirth(Date birth) {
		this.birth = birth;
	}

	/**
	 * 获取地址
	 * 
	 * @return 地址
	 */
	@Length(max = 200)
	public String getAddress() {
		return address;
	}

	/**
	 * 设置地址
	 * 
	 * @param address
	 *            地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取邮编
	 * 
	 * @return 邮编
	 */
	@Length(max = 200)
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * 设置邮编
	 * 
	 * @param zipCode
	 *            邮编
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * 获取电话
	 * 
	 * @return 电话
	 */
	@Length(max = 200)
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置电话
	 * 
	 * @param phone
	 *            电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取手机
	 * 
	 * @return 手机
	 */
	@Length(max = 200)
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机
	 * 
	 * @param mobile
	 *            手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取登录插件ID
	 * 
	 * @return 登录插件ID
	 */
	@Column(updatable = false)
	public String getLoginPluginId() {
		return loginPluginId;
	}

	/**
	 * 设置登录插件ID
	 * 
	 * @param loginPluginId
	 *            登录插件ID
	 */
	public void setLoginPluginId(String loginPluginId) {
		this.loginPluginId = loginPluginId;
	}

	/**
	 * 获取openID
	 * 
	 * @return openID
	 */
	@Column(updatable = false)
	public String getOpenId() {
		return openId;
	}

	/**
	 * 设置openID
	 * 
	 * @param openId
	 *            openID
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
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
	 * 获取会员注册项值0
	 * 
	 * @return 会员注册项值0
	 */
	@Length(max = 200)
	public String getAttributeValue0() {
		return attributeValue0;
	}

	/**
	 * 设置会员注册项值0
	 * 
	 * @param attributeValue0
	 *            会员注册项值0
	 */
	public void setAttributeValue0(String attributeValue0) {
		this.attributeValue0 = attributeValue0;
	}

	/**
	 * 获取会员注册项值1
	 * 
	 * @return 会员注册项值1
	 */
	@Length(max = 200)
	public String getAttributeValue1() {
		return attributeValue1;
	}

	/**
	 * 设置会员注册项值1
	 * 
	 * @param attributeValue1
	 *            会员注册项值1
	 */
	public void setAttributeValue1(String attributeValue1) {
		this.attributeValue1 = attributeValue1;
	}

	/**
	 * 获取会员注册项值2
	 * 
	 * @return 会员注册项值2
	 */
	@Length(max = 200)
	public String getAttributeValue2() {
		return attributeValue2;
	}

	/**
	 * 设置会员注册项值2
	 * 
	 * @param attributeValue2
	 *            会员注册项值2
	 */
	public void setAttributeValue2(String attributeValue2) {
		this.attributeValue2 = attributeValue2;
	}

	/**
	 * 获取会员注册项值3
	 * 
	 * @return 会员注册项值3
	 */
	@Length(max = 200)
	public String getAttributeValue3() {
		return attributeValue3;
	}

	/**
	 * 设置会员注册项值3
	 * 
	 * @param attributeValue3
	 *            会员注册项值3
	 */
	public void setAttributeValue3(String attributeValue3) {
		this.attributeValue3 = attributeValue3;
	}

	/**
	 * 获取会员注册项值4
	 * 
	 * @return 会员注册项值4
	 */
	@Length(max = 200)
	public String getAttributeValue4() {
		return attributeValue4;
	}

	/**
	 * 设置会员注册项值4
	 * 
	 * @param attributeValue4
	 *            会员注册项值4
	 */
	public void setAttributeValue4(String attributeValue4) {
		this.attributeValue4 = attributeValue4;
	}

	/**
	 * 获取会员注册项值5
	 * 
	 * @return 会员注册项值5
	 */
	@Length(max = 200)
	public String getAttributeValue5() {
		return attributeValue5;
	}

	/**
	 * 设置会员注册项值5
	 * 
	 * @param attributeValue5
	 *            会员注册项值5
	 */
	public void setAttributeValue5(String attributeValue5) {
		this.attributeValue5 = attributeValue5;
	}

	/**
	 * 获取会员注册项值6
	 * 
	 * @return 会员注册项值6
	 */
	@Length(max = 200)
	public String getAttributeValue6() {
		return attributeValue6;
	}

	/**
	 * 设置会员注册项值6
	 * 
	 * @param attributeValue6
	 *            会员注册项值6
	 */
	public void setAttributeValue6(String attributeValue6) {
		this.attributeValue6 = attributeValue6;
	}

	/**
	 * 获取会员注册项值7
	 * 
	 * @return 会员注册项值7
	 */
	@Length(max = 200)
	public String getAttributeValue7() {
		return attributeValue7;
	}

	/**
	 * 设置会员注册项值7
	 * 
	 * @param attributeValue7
	 *            会员注册项值7
	 */
	public void setAttributeValue7(String attributeValue7) {
		this.attributeValue7 = attributeValue7;
	}

	/**
	 * 获取会员注册项值8
	 * 
	 * @return 会员注册项值8
	 */
	@Length(max = 200)
	public String getAttributeValue8() {
		return attributeValue8;
	}

	/**
	 * 设置会员注册项值8
	 * 
	 * @param attributeValue8
	 *            会员注册项值8
	 */
	public void setAttributeValue8(String attributeValue8) {
		this.attributeValue8 = attributeValue8;
	}

	/**
	 * 获取会员注册项值9
	 * 
	 * @return 会员注册项值9
	 */
	@Length(max = 200)
	public String getAttributeValue9() {
		return attributeValue9;
	}

	/**
	 * 设置会员注册项值9
	 * 
	 * @param attributeValue9
	 *            会员注册项值9
	 */
	public void setAttributeValue9(String attributeValue9) {
		this.attributeValue9 = attributeValue9;
	}

	/**
	 * 获取安全密匙
	 * 
	 * @return 安全密匙
	 */
	@Embedded
	public SafeKey getSafeKey() {
		return safeKey;
	}

	/**
	 * 设置安全密匙
	 * 
	 * @param safeKey
	 *            安全密匙
	 */
	public void setSafeKey(SafeKey safeKey) {
		this.safeKey = safeKey;
	}

	/**
	 * 获取地区
	 * 
	 * @return 地区
	 */
	@ManyToOne(fetch = FetchType.LAZY) 
	@JSONField(serialize=false)
	public Area getArea() {
		return area;
	}

	/**
	 * 设置地区
	 * 
	 * @param area
	 *            地区
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * 获取会员等级
	 * 
	 * @return 会员等级
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@JSONField(serialize=false)
	public MemberRank getMemberRank() {
		return memberRank;
	}

	/**
	 * 设置会员等级
	 * 
	 * @param memberRank
	 *            会员等级
	 */
	public void setMemberRank(MemberRank memberRank) {
		this.memberRank = memberRank;
	}

	/**
	 * 获取购物车
	 * 
	 * @return 购物车
	 */
	@OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JSONField(serialize=false)
	public Cart getCart() {
		return cart;
	}

	/**
	 * 设置购物车
	 * 
	 * @param cart
	 *            购物车
	 */
	public void setCart(Cart cart) {
		this.cart = cart;
	}

	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JSONField(serialize=false)
	public Set<Order> getOrders() {
		return orders;
	}

	/**
	 * 设置订单
	 * 
	 * @param orders
	 *            订单
	 */
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	/**
	 * 获取支付记录
	 * 
	 * @return 支付记录
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JSONField(serialize=false)
	public Set<PaymentLog> getPaymentLogs() {
		return paymentLogs;
	}

	/**
	 * 设置支付记录
	 * 
	 * @param paymentLogs
	 *            支付记录
	 */
	public void setPaymentLogs(Set<PaymentLog> paymentLogs) {
		this.paymentLogs = paymentLogs;
	}

	/**
	 * 获取预存款记录
	 * 
	 * @return 预存款记录
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JSONField(serialize=false)
	public Set<DepositLog> getDepositLogs() {
		return depositLogs;
	}

	/**
	 * 设置预存款记录
	 * 
	 * @param depositLogs
	 *            预存款记录
	 */
	public void setDepositLogs(Set<DepositLog> depositLogs) {
		this.depositLogs = depositLogs;
	}

	/**
	 * 获取优惠码
	 * 
	 * @return 优惠码
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JSONField(serialize=false)
	public Set<CouponCode> getCouponCodes() {
		return couponCodes;
	}

	/**
	 * 设置优惠码
	 * 
	 * @param couponCodes
	 *            优惠码
	 */
	public void setCouponCodes(Set<CouponCode> couponCodes) {
		this.couponCodes = couponCodes;
	}

	/**
	 * 获取收货地址
	 * 
	 * @return 收货地址
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("isDefault desc, createDate desc")
	@JSONField(serialize=false)
	public Set<Receiver> getReceivers() {
		return receivers;
	}

	/**
	 * 设置收货地址
	 * 
	 * @param receivers
	 *            收货地址
	 */
	public void setReceivers(Set<Receiver> receivers) {
		this.receivers = receivers;
	}

	/**
	 * 获取评论
	 * 
	 * @return 评论
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate desc")
	@JSONField(serialize=false)
	public Set<Review> getReviews() {
		return reviews;
	}

	/**
	 * 设置评论
	 * 
	 * @param reviews
	 *            评论
	 */
	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	
	
	
	/**
	 * 获取所属商户评论
	 * @return the 所属商户评论
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate desc")
	@JSONField(serialize=false)
	public Set<Review> getMemberBipreviews() {
		return memberBipreviews;
	}
	


	/**
	 * 设置所属商户评论
	 * @param 所属商户评论 
	 */
	public void setMemberBipreviews(Set<Review> memberBipreviews) {
		this.memberBipreviews = memberBipreviews;
	}
	


	/**
	 * 获取咨询
	 * 
	 * @return 咨询
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate desc")
	@JSONField(serialize=false)
	public Set<Consultation> getConsultations() {
		return consultations;
	}

	/**
	 * 设置咨询
	 * 
	 * @param consultations
	 *            咨询
	 */
	public void setConsultations(Set<Consultation> consultations) {
		this.consultations = consultations;
	}

	/**
	 * 获取收藏货品
	 * 
	 * @return 收藏货品
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_member_favorite_goods")
	@OrderBy("createDate desc")
	@JSONField(serialize=false)
	public Set<Goods> getFavoriteGoods() {
		return favoriteGoods;
	}

	/**
	 * 设置收藏货品
	 * 
	 * @param favoriteGoods
	 *            收藏货品
	 */
	public void setFavoriteGoods(Set<Goods> favoriteGoods) {
		this.favoriteGoods = favoriteGoods;
	}

	/**
	 * 获取到货通知
	 * 
	 * @return 到货通知
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JSONField(serialize=false)
	public Set<ProductNotify> getProductNotifies() {
		return productNotifies;
	}

	/**
	 * 设置到货通知
	 * 
	 * @param productNotifies
	 *            到货通知
	 */
	public void setProductNotifies(Set<ProductNotify> productNotifies) {
		this.productNotifies = productNotifies;
	}

	/**
	 * 获取接收的消息
	 * 
	 * @return 接收的消息
	 */
	@OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Message> getInMessages() {
		return inMessages;
	}
	
	/**  
	 * 获取管理员(子公司)  
	 * @return admin 管理员(子公司)  
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JSONField(serialize=false)
	public Admin getAdmin() {
		return admin;
	}
	

	/**  
	 * 设置管理员(子公司)  
	 * @param admin 管理员(子公司)  
	 */
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	

	/**
	 * 设置接收的消息
	 * 
	 * @param inMessages
	 *            接收的消息
	 */
	public void setInMessages(Set<Message> inMessages) {
		this.inMessages = inMessages;
	}

	/**
	 * 获取发送的消息
	 * 
	 * @return 发送的消息
	 */
	@OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JSONField(serialize=false)
	public Set<Message> getOutMessages() {
		return outMessages;
	}

	/**
	 * 设置发送的消息
	 * 
	 * @param outMessages
	 *            发送的消息
	 */
	public void setOutMessages(Set<Message> outMessages) {
		this.outMessages = outMessages;
	}

	/**
	 * 获取积分记录
	 * 
	 * @return 积分记录
	 */
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JSONField(serialize=false)
	public Set<PointLog> getPointLogs() {
		return pointLogs;
	}

	/**
	 * 设置积分记录
	 * 
	 * @param pointLogs
	 *            积分记录
	 */
	public void setPointLogs(Set<PointLog> pointLogs) {
		this.pointLogs = pointLogs;
	}

	/**
	 * 获取会员注册项值
	 * 
	 * @param memberAttribute
	 *            会员注册项
	 * @return 会员注册项值
	 */
	@Transient
	public Object getAttributeValue(MemberAttribute memberAttribute) {
		if (memberAttribute == null || memberAttribute.getType() == null) {
			return null;
		}

		switch (memberAttribute.getType()) {
		case name:
			return getName();
		case gender:
			return getGender();
		case birth:
			return getBirth();
		case area:
			return getArea();
		case address:
			return getAddress();
		case zipCode:
			return getZipCode();
		case phone:
			return getPhone();
		case mobile:
			return getMobile();
		case text:
		case select:
			if (memberAttribute.getPropertyIndex() != null) {
				try {
					String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
					return PropertyUtils.getProperty(this, propertyName);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		case checkbox:
			if (memberAttribute.getPropertyIndex() != null) {
				try {
					String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
					String propertyValue = (String) PropertyUtils.getProperty(this, propertyName);
					if (StringUtils.isNotEmpty(propertyValue)) {
						return JsonUtils.toObject(propertyValue, List.class);
					}
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		}
		return null;
	}

	/**
	 * 设置会员注册项值
	 * 
	 * @param memberAttribute
	 *            会员注册项
	 * @param memberAttributeValue
	 *            会员注册项值
	 */
	@Transient
	public void setAttributeValue(MemberAttribute memberAttribute, Object memberAttributeValue) {
		if (memberAttribute == null || memberAttribute.getType() == null) {
			return;
		}

		switch (memberAttribute.getType()) {
		case name:
			if (memberAttributeValue instanceof String || memberAttributeValue == null) {
				setName((String) memberAttributeValue);
			}
			break;
		case gender:
			if (memberAttributeValue instanceof Gender || memberAttributeValue == null) {
				setGender((Gender) memberAttributeValue);
			}
			break;
		case birth:
			if (memberAttributeValue instanceof Date || memberAttributeValue == null) {
				setBirth((Date) memberAttributeValue);
			}
			break;
		case area:
			if (memberAttributeValue instanceof Area || memberAttributeValue == null) {
				setArea((Area) memberAttributeValue);
			}
			break;
		case address:
			if (memberAttributeValue instanceof String || memberAttributeValue == null) {
				setAddress((String) memberAttributeValue);
			}
			break;
		case zipCode:
			if (memberAttributeValue instanceof String || memberAttributeValue == null) {
				setZipCode((String) memberAttributeValue);
			}
			break;
		case phone:
			if (memberAttributeValue instanceof String || memberAttributeValue == null) {
				setPhone((String) memberAttributeValue);
			}
			break;
		case mobile:
			if (memberAttributeValue instanceof String || memberAttributeValue == null) {
				setMobile((String) memberAttributeValue);
			}
			break;
		case text:
		case select:
			if ((memberAttributeValue instanceof String || memberAttributeValue == null) && memberAttribute.getPropertyIndex() != null) {
				try {
					String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
					PropertyUtils.setProperty(this, propertyName, memberAttributeValue);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		case checkbox:
			if ((memberAttributeValue instanceof Collection || memberAttributeValue == null) && memberAttribute.getPropertyIndex() != null) {
				try {
					String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
					PropertyUtils.setProperty(this, propertyName, memberAttributeValue != null ? JsonUtils.toJson(memberAttributeValue) : null);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		}
	}

	/**
	 * 移除所有会员注册项值
	 */
	@Transient
	public void removeAttributeValue() {
		setName(null);
		setGender(null);
		setBirth(null);
		setArea(null);
		setAddress(null);
		setZipCode(null);
		setPhone(null);
		setMobile(null);
		for (int i = 0; i < ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
			String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
			try {
				PropertyUtils.setProperty(this, propertyName, null);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		setLockKey(DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30)));
	}
	/**
	 * 获取凭证码
	 * @return
	 */
	@Length(max = 255)
    public String getDocumentCode() {
        return documentCode;
    }
    /**
     * 设置凭证码
     * @param documentCode
     *          凭证码
     */
    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    /**  
     * 获取买锁返现次数  
     * @return lock_Count 买锁返现次数  
     */
    @Column(name="lock_Count")
    public Integer getLock_Count() {
        return lock_Count;
    }

    /**  
     * 设置买锁返现次数  
     * @param lock_Count 买锁返现次数  
     */
    public void setLock_Count(Integer lock_Count) {
        this.lock_Count = lock_Count;
    }

    /**  
     * 获取是否激活  
     * @return activation 是否激活  
     */
    @Column(name="activation")
    public Integer getActivation() {
        return activation;
    }

    /**  
     * 设置是否激活  
     * @param activation 是否激活  
     */
    public void setActivation(Integer activation) {
        this.activation = activation;
    }

    /**  
     * 获取是否vip  
     * @return vip 是否vip  
     */
    @Column(name="vip")
    public Integer getVip() {
        return vip;
    }

    /**  
     * 设置是否vip  
     * @param vip 是否vip  
     */
    public void setVip(Integer vip) {
        this.vip = vip;
    }

    /**  
     * 获取消费劵(累积)  
     * @return vouchers 消费劵(累积)  
     */
    @Column(name="vouchers",precision = 21, scale = 6)
    public BigDecimal getVouchers() {
        return vouchers;
    }

    /**  
     * 设置消费劵(累积)  
     * @param vouchers 消费劵(累积)  
     */
    public void setVouchers(BigDecimal vouchers) {
        this.vouchers = vouchers;
    }

    /**  
     * 获取小区编号  
     * @return communityNumber 小区编号  
     */
    @Column(name="community_number",length=32)
    public String getCommunityNumber() {
        return communityNumber;
    }

    /**  
     * 设置小区编号  
     * @param communityNumber 小区编号  
     */
    public void setCommunityNumber(String communityNumber) {
        this.communityNumber = communityNumber;
    }

    /**  
     * 获取是否发送短信  
     * @return isSendMessage 是否发送短信  
     */
    @Column(name="is_SendMessage")
    public String getIsSendMessage() {
        return isSendMessage;
    }

    /**  
     * 设置是否发送短信  
     * @param isSendMessage 是否发送短信  
     */
    public void setIsSendMessage(String isSendMessage) {
        this.isSendMessage = isSendMessage;
    }

    
    /**  
     * 获取是否推送消息  
     * @return isSendPush 是否推送消息  
     */
    @Column(name="is_SendPush")
    public String getIsSendPush() {
        return isSendPush;
    }

    /**  
     * 设置是否推送消息  
     * @param isSendPush 是否推送消息  
     */
    public void setIsSendPush(String isSendPush) {
        this.isSendPush = isSendPush;
    }

    /**  
     * 获取授权码  
     * @return authorizationCode 授权码  
     */
    @Column(name="authorizationCode")
    public String getAuthorizationCode() {
        return authorizationCode;
    }

    /**  
     * 设置授权码  
     * @param authorizationCode 授权码  
     */
    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    /**  
     * 获取授权时间  
     * @return authorizationTime 授权时间  
     */
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@DateBridge(resolution = Resolution.SECOND)
    @Column(name="authorizationTime")
    public Date getAuthorizationTime() {
        return authorizationTime;
    }

    /**  
     * 设置授权时间  
     * @param authorizationTime 授权时间  
     */
    public void setAuthorizationTime(Date authorizationTime) {
        this.authorizationTime = authorizationTime;
    }

    /**  
     * 获取设备  
     * @return currentEquipment 设备  
     */
    @Column(name="currentEquipment")
    public Integer getCurrentEquipment() {
        return currentEquipment;
    }

    /**  
     * 设置设备  
     * @param currentEquipment 设备  
     */
    public void setCurrentEquipment(Integer currentEquipment) {
        this.currentEquipment = currentEquipment;
    }

	/**  
	 * 获取用户头像地址  
	 * @return photo 用户头像地址  
	 */
	public String getPhoto() {
		return photo;
	}
	

	/**  
	 * 设置用户头像地址  
	 * @param photo 用户头像地址  
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**  
	 * 获取小区支付装态  
	 * @return communityApplyState 小区支付装态  
	 */
	public Integer getCommunityApplyState() {
		return communityApplyState;
	}
	
	/**  
	 * 设置小区支付装态  
	 * @param communityApplyState 小区支付装态  
	 */
	public void setCommunityApplyState(Integer communityApplyState) {
		this.communityApplyState = communityApplyState;
	}
	
	/**  
	 * 获取  
	 * @return communityWanting   
	 */
	public String getCommunityWanting() {
		return communityWanting;
	}
	
	/**  
	 * 设置  
	 * @param communityWanting   
	 */
	public void setCommunityWanting(String communityWanting) {
		this.communityWanting = communityWanting;
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
	public String getAreaCode() {
		return areaCode;
	}
	public String getIndustry() {
		return industry;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public Deductions getDeductions() {
		return deductions;
	}
	public void setDeductions(Deductions deductions) {
		this.deductions = deductions;
	}
	@Column(length = 32)
	public String getDefaultLicense() {
		return defaultLicense;
	}
	public void setDefaultLicense(String defaultLicense) {
		this.defaultLicense = defaultLicense;
	}
	public String getAccessIsSendPush() {
		return accessIsSendPush;
	}
	public void setAccessIsSendPush(String accessIsSendPush) {
		this.accessIsSendPush = accessIsSendPush;
	}
    
}