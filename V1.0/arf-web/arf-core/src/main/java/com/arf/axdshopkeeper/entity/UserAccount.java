package com.arf.axdshopkeeper.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "bi_user_account",uniqueConstraints = {
		@UniqueConstraint(columnNames = { "userName" }, name = "unique_userNamer") })
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "bi_user_account_sequence")
public class UserAccount extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	public static final String KEY_TOKEN_PREFIX = "Shopkeeper_Token:";
	//登陆TOKEN缓存时效30 * 24小时
	public static final long LOGIN_TOKEN_EXPIRE =  30 * 24 * 60 * 60L;
	
	private String userName;//
	private String passwd;//
	private IdentityType identityType;//身份类型枚举:SHOPKEEPER 0 店主;BRANCH 1 联营公司;BIZ_CHANNEL 2 渠道;ANERFA 3 安尔发
	private AccountType accountType;//账户类型枚举:SELF 0 账户本体;OPERATOR 1 运营人员;ADMINISTRATOR 2 管理员;
	private String identityNumber;//身份类型关联的号码,如果是店主下的管理员或者运营人员,则为店主userName,如果为联营公司则为联营公司编号,如果为渠道则为渠道编号
	private String communities;//绑定小区编号,多个使用英文逗号分隔
	private String realName;//真实姓名
	private Date enterDate;//入驻时间
	private Integer starsLevel;//店主星级
	private String avatarUrl;//头像URL
	private Status status;
	private String disableReason;
	private String addBy;
	private String tagNames;//标签(多个以英文逗号隔开)
	private String introduction;//简介
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IdentityType{
		SHOPKEEPER,
		BRANCH,
		BIZ_CHANNEL,
		ANERFA,
		;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		ENABLE,//正常使用
		SECURITY_FREEZE,//安全冻结
		DISABLE,//禁用
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AccountType{
		SELF,
		OPERATOR,
		ADMINISTRATOR;
	}

	@Column(length=20,nullable=false)
	public String getUserName() {
		return userName;
	}

	@Column(length=40,nullable=false)
	public String getPasswd() {
		return passwd;
	}

	@Column(nullable=false)
	public IdentityType getIdentityType() {
		return identityType;
	}

	@Column(nullable=false)
	public AccountType getAccountType() {
		return accountType;
	}

	@Column(length=20,nullable=false)
	public String getIdentityNumber() {
		return identityNumber;
	}

	@Type(type = "text")
	@Column(length=65535)
	public String getCommunities() {
		return communities;
	}

	@Column(length=20)
	public String getRealName() {
		return realName;
	}
	
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	public Date getEnterDate() {
		return enterDate;
	}

	public Integer getStarsLevel() {
		return starsLevel;
	}

	@Column(length=255)
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public Status getStatus() {
		return status;
	}
	@Column(length=50)
	public String getDisableReason() {
		return disableReason;
	}
	@Column(length=20)
	public String getAddBy() {
		return addBy;
	}
	@Column(length=50)
	public String getTagNames() {
		return tagNames;
	}
	@Column(length=255)
	public String getIntroduction() {
		return introduction;
	}

	public void setTagNames(String tagNames) {
		this.tagNames = tagNames;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public void setAddBy(String addBy) {
		this.addBy = addBy;
	}

	public void setDisableReason(String disableReason) {
		this.disableReason = disableReason;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public void setIdentityType(IdentityType identityType) {
		this.identityType = identityType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public void setCommunities(String communities) {
		this.communities = communities;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}

	public void setStarsLevel(Integer starsLevel) {
		this.starsLevel = starsLevel;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

}
