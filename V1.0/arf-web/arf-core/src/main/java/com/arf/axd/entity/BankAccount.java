package com.arf.axd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
@Entity
@Table(name = "s_bank_account")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "s_bank_account_sequence")
public class BankAccount extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9159128048026605173L;
	
	/**
	 * 类型,1:支付宝,2:微信,3:银行
	 */
	public enum Type{
		Nouse,
		Alipay,
		Wechet,
		Bank;
		public static Type get(int index){
			Type[] types = Type.values();
			if(index < 0 || index > types.length){
				return null;
			}
			return types[index];
		}
	}
	
	/**
	 * 状态,0:正常状态,1:用户已解绑,2:过期失效
	 */
	public enum Status{
		Normal,Unwrap,OutOfDate
	}
	
	/**
	 * 用户类型,0:会员,1:商户
	 */
	public enum UserType{
		user,merchant
	}
	
	private String bankName;//银行全称
	private String bankShortName;//银行简称,eg. CMB
	private String bankNumber;//账户号码
	private String realName;//开户名
	private Integer type;//类型,1:支付宝,2:微信,3:银行
	private String bankBranch;//支行
	private Integer status;//状态,0:正常状态,1:用户已解绑,2:过期失效
	private String userName;//用户名
	private Integer userType;// 用户类型,0:会员,1:商户
	
	@Column(length = 50)
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Column(length = 20)
	public String getBankShortName() {
		return bankShortName;
	}
	public void setBankShortName(String bankShortName) {
		this.bankShortName = bankShortName;
	}
	
	@Column(length = 50)
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	
	@Column(length = 20)
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(length = 100)
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(length = 20)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
}
