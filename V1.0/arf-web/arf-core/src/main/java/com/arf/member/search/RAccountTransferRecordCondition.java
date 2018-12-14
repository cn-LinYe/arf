package com.arf.member.search;

import com.arf.base.BaseSearchForm;

public class RAccountTransferRecordCondition extends BaseSearchForm{
	
	private String userName;//用户名（手机号）
	private Byte accountType;//账户类型：0、基本账户 1、基本可用账户 2、赠送账户 3、积分账户
	private String operateTimeStrStart;//操作开始时间
	private String operateTimeStrEnd;//操作结束时间
	private Byte status;//状态（1、完成 2、冻结 3、失败）
	private Byte userType;//账户类型：0、会员 1、商户
	/**
	 * 类型<br/>
	 * 1-49为充值（1、支付宝充值 2、微信充值 3、银行卡充值 4、他人转账入账 5、系统充值）<br/>
	 * 50-99为消费（50、圈存 51、提现 52、给他人转账出账 53、信用卡还款 54、点滴洗套餐购买）
	 */
	private Byte type;
	private Integer consumeType;//0消费 1收入
	
	public Integer getConsumeType() {
		return consumeType;
	}
	public void setConsumeType(Integer consumeType) {
		this.consumeType = consumeType;
	}
	public String getUserName() {
		return userName;
	}
	public Byte getAccountType() {
		return accountType;
	}
	public String getOperateTimeStrStart() {
		return operateTimeStrStart;
	}
	public String getOperateTimeStrEnd() {
		return operateTimeStrEnd;
	}
	public Byte getStatus() {
		return status;
	}
	public Byte getType() {
		return type;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setAccountType(Byte accountType) {
		this.accountType = accountType;
	}
	public void setOperateTimeStrStart(String operateTimeStrStart) {
		this.operateTimeStrStart = operateTimeStrStart;
	}
	public void setOperateTimeStrEnd(String operateTimeStrEnd) {
		this.operateTimeStrEnd = operateTimeStrEnd;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public Byte getUserType() {
		return userType;
	}
	public void setUserType(Byte userType) {
		this.userType = userType;
	}
	
}
