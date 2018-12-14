package com.arf.eparking.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_blacklist_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_blacklist_record_sequence")
public class BlacklistRecord extends BaseEntity<Long>{

	private static final long serialVersionUID = 1L;
	
	public enum Status{
		newone,
		handling,
		deactivated;
	}
	
	public enum Type{
		e_break_contract,
		parking_fee_arrears,
		carbright;
	}

	private String blacklistNo;//记录唯一标识
	private String userName;//用户名
	private String licenses;//相关车牌号(一个JSONArray的字符串)
	private Integer status;//状态:0-新生成;1-处理中;2-已解除
	private Integer type;//被拉黑类型:0:E停车预约爽约1:停车费用欠缴2:点滴洗业务被拉黑(其它后续添加)
	private Date explainStartTime;//用户提起申述时间
	private String explainContent;//用户申述内容
	private BigDecimal amountOwed;//欠款金额
	private BigDecimal recoveredAmount;//追缴总金额
	private String lastHandleUser;//最后处理人
	private Date dateOfArrears;//欠款开始日期(amount_owed存在时,此字段存在)
	private String cause;//被拉黑原因
	private BigDecimal breakContractFee;//爽约费扣款金额
	
	@Column(length = 32)
	public String getBlacklistNo() {
		return blacklistNo;
	}
	@Column(length = 20)
	public String getUserName() {
		return userName;
	}
	@Column(length = 256)
	public String getLicenses() {
		return licenses;
	}
	@Column(length = 11)
	public Integer getStatus() {
		return status;
	}
	@Column(length = 11)
	public Integer getType() {
		return type;
	}
	public Date getExplainStartTime() {
		return explainStartTime;
	}
	@Column(length = 512)
	public String getExplainContent() {
		return explainContent;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getAmountOwed() {
		return amountOwed;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getRecoveredAmount() {
		return recoveredAmount;
	}
	@Column(length = 20)
	public String getLastHandleUser() {
		return lastHandleUser;
	}
	public Date getDateOfArrears() {
		return dateOfArrears;
	}
	@Column(length = 200)
	public String getCause() {
		return cause;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getBreakContractFee() {
		return breakContractFee;
	}
	public void setBlacklistNo(String blacklistNo) {
		this.blacklistNo = blacklistNo;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setLicenses(String licenses) {
		this.licenses = licenses;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setExplainStartTime(Date explainStartTime) {
		this.explainStartTime = explainStartTime;
	}
	public void setExplainContent(String explainContent) {
		this.explainContent = explainContent;
	}
	public void setAmountOwed(BigDecimal amountOwed) {
		this.amountOwed = amountOwed;
	}
	public void setRecoveredAmount(BigDecimal recoveredAmount) {
		this.recoveredAmount = recoveredAmount;
	}
	public void setLastHandleUser(String lastHandleUser) {
		this.lastHandleUser = lastHandleUser;
	}
	public void setDateOfArrears(Date dateOfArrears) {
		this.dateOfArrears = dateOfArrears;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public void setBreakContractFee(BigDecimal breakContractFee) {
		this.breakContractFee = breakContractFee;
	}
	
}
