package com.arf.platform.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(
		name = "s_account",
		indexes={
				@Index(name="index_account_number",columnList="account_number"),
				@Index(name="index_user_name",columnList="user_name")
		},
		uniqueConstraints={
				@UniqueConstraint(columnNames={"user_name","type"},name="unique_user_name_type"),
				@UniqueConstraint(columnNames={"account_number"},name="unique_account_number")
		}
)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "s_account_sequence")
public class SMemberAccount extends BaseEntity<Long>{
	
	/**
	 * 状态（0、禁用 1、可用）
	 * @author arf
	 *
	 */
	public static enum Status{
		unusable,usable;
	}
	/**
	 * 账户类型（0、会员 1、商户）
	 * @author arf
	 *
	 */
	public static enum Type{
		member,business;
	}
	
	public static final String actPwdDefault = "123456"; 
	
	public static final String COLLECTION_POINT_PERCENT = "COLLECTION_POINT_PERCENT";//代收赠送积分比例
	public static final String CONSUME_POINT_PERCENT = "CONSUME_POINT_PERCENT";//消费赠送积分比例

	private static final long serialVersionUID = 1L;

	private String accountNumber;//账户编码
	private Integer userId;//用户id
	private String userName;//用户登录账户/手机号
	private Integer credit;//信用额度,单位:分
	private BigDecimal basicAccount;//基本账户余额,单位：元
	private BigDecimal basicAvaliableAccount;//基本可用账户,单位:元 待定 160520
	private BigDecimal giftAccount;//赠送账户,单位:元
	private BigDecimal consumAmount;//已消费金额（总额）
	private Integer point;//积分（积分账户）
	private Byte status;//状态（0、禁用 1、可用）
	private String pwd;//账户密码
	private String actPwd;//账户明文密码
	private Byte type;//账户类型（0、会员 1、商户）
	/**
	 * 积分累积经验值 注意:该字段不需要在业务中处理,具体数据累加由积分流转记录在save操作的时候自动进行,
	 * {@link com.arf.member.service.impl.PointTransferRecordServiceImpl.incrementPointExperience(PointTransferRecord)}
	 */
	private long pointExperience=0l;
	
	@Column(name = "account_number",length =50)
	public String getAccountNumber() {
		return accountNumber;
	}
	@Column(name = "user_id",length =11)
	public Integer getUserId() {
		return userId;
	}
	@Column(name = "user_name",length =50)
	public String getUserName() {
		return userName;
	}
	@Column(name = "credit",length =11)
	public Integer getCredit() {
		return credit;
	}
	@Column(name = "basic_account",precision=10,scale=2)
	public BigDecimal getBasicAccount() {
		return basicAccount;
	}
	@Column(name = "basic_avaliable_account",precision=10,scale=2)
	public BigDecimal getBasicAvaliableAccount() {
		return basicAvaliableAccount;
	}
	@Column(name = "gift_account",precision=10,scale=2)
	public BigDecimal getGiftAccount() {
		return giftAccount;
	}
	@Column(name = "consum_amount",precision=10,scale=2)
	public BigDecimal getConsumAmount() {
		return consumAmount;
	}
	@Column(name = "point",length =11)
	public Integer getPoint() {
		return point;
	}
	@Column(name = "status",length =4)
	public Byte getStatus() {
		return status;
	}
	@JsonIgnore
	@Column(name = "pwd",length =50)
	public String getPwd() {
		return pwd;
	}
	@JsonIgnore
	@Column(name = "act_pwd",length =50)
	public String getActPwd() {
		return actPwd;
	}
	@Column(name = "type",length =4)
	public Byte getType() {
		return type;
	}
	/**
	 * 积分累积经验值 注意:该字段不需要在业务中处理,具体数据累加由积分流转记录在save操作的时候自动进行,
	 * {@link com.arf.member.service.impl.PointTransferRecordServiceImpl.incrementPointExperience(PointTransferRecord)}
	 * @return
	 */
	public long getPointExperience() {
		return pointExperience;
	}
	/**
	 * 积分累积经验值 注意:该字段不需要在业务中处理,具体数据累加由积分流转记录在save操作的时候自动进行,
	 * {@link com.arf.member.service.impl.PointTransferRecordServiceImpl.incrementPointExperience(PointTransferRecord)}
	 * @param pointExperience
	 */
	public void setPointExperience(long pointExperience) {
		this.pointExperience = pointExperience;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	public void setBasicAccount(BigDecimal basicAccount) {
		this.basicAccount = basicAccount;
	}
	public void setBasicAvaliableAccount(BigDecimal basicAvaliableAccount) {
		this.basicAvaliableAccount = basicAvaliableAccount;
	}
	public void setGiftAccount(BigDecimal giftAccount) {
		this.giftAccount = giftAccount;
	}
	public void setConsumAmount(BigDecimal consumAmount) {
		this.consumAmount = consumAmount;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public void setActPwd(String actPwd) {
		this.actPwd = actPwd;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	
}
