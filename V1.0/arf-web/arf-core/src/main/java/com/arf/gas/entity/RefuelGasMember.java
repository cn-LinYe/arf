package com.arf.gas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "refuel_gas_member")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "refuel_gas_member_sequence")
public class RefuelGasMember extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5976253491329973141L;
	
	private String userName;//会员账号
	private String realName;//会员名称
	private MemberLevel memberLevel;//会员级别（0、Shareholder 股东 1、Vip_Member VIP会员 2、Member 会员）
	private Integer enjoyDiscounts;//享受折扣
	private String paymentPassword;	//	支付密码
	private String fingerprintPassword;//指纹密码
	private FreePayment freePayment;//是否支持免密支付（0、不支持 Not_Support 1、支持 Support）
	private String IDcardPhoto;//身份证照片（index ==0 为正面 1为反面）数组形式存储
	private String drivingPhoto;//行驶证照片（存在多张）数组形式存储
	private String protocolPhoto;//协议照片
	private MemberStatus memberStatus;/*//会员状态（0、Not_Submitted待提交资料 1、Normal_Use 正常使用 2、Prohibited_Use 禁止使用 3、First_Data_Submitted 一审资料已提交 
									4、一审驳回（待提交资料） First_Review_Rejected 5、一审通过（线下提交资料） First_Review_Passed 6、Second_Data_Submitted 二审资料已提交
									7、二审驳回（待提交资料） Second_Review_Rejected 8、线下资料已提交 Line_Data_Submitted 9、线下资料审核驳回 Line_Data_Rejected）*/
	private Integer consumptionAmount;//消费金额（充值+众筹金额需要维护此字段）
	private String licensePlateNumber;//车牌信息（多条记录，需保证与行驶证一一对应）'0','4','5','7','9'
	private Integer freePaymentQuota;//免密支付额度
	private String firstReviewResult;//一审结果
	private String LineDataResult;//线下申请结果
	private String secondReviewResult;//二审结果
	private Integer businessNum;//商户信息
	private Integer gasNum;//加油站编号
	private Integer cardAmount;//开卡金额
	private String recommend;//推荐人手机号
	private String idCard;
	private Date submitDate;//资料提交时间
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum FreePayment{
		Not_Support,//不支持
		Support;//支持 
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum MemberStatus{
		Not_Submitted,//待提交资料
		Normal_Use,//正常使用 
		Prohibited_Use,//禁止使用 
		First_Data_Submitted,//一审资料已提交 
		First_Review_Rejected,//一审驳回（待提交资料）
		First_Review_Passed,//一审通过（线下提交资料）
		Second_Data_Submitted,//二审资料已提交
		Second_Review_Rejected,//二审驳回（待提交资料）
		Line_Data_Submitted,//线下资料已提交
		Line_Data_Rejected,//线下资料审核驳回
		Date_Deleted;//资料已删除
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum MemberLevel{
		Shareholder,// 股东 
		Vip_Member,//VIP会员
		Member;//会员
	}
	
	@Column(length=50)
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	@Column(length=50)
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public Integer getCardAmount() {
		return cardAmount;
	}
	public void setCardAmount(Integer cardAmount) {
		this.cardAmount = cardAmount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public MemberLevel getMemberLevel() {
		return memberLevel;
	}
	public void setMemberLevel(MemberLevel memberLevel) {
		this.memberLevel = memberLevel;
	}
	public Integer getEnjoyDiscounts() {
		return enjoyDiscounts;
	}
	public void setEnjoyDiscounts(Integer enjoyDiscounts) {
		this.enjoyDiscounts = enjoyDiscounts;
	}
	public String getPaymentPassword() {
		return paymentPassword;
	}
	public void setPaymentPassword(String paymentPassword) {
		this.paymentPassword = paymentPassword;
	}
	public String getFingerprintPassword() {
		return fingerprintPassword;
	}
	public void setFingerprintPassword(String fingerprintPassword) {
		this.fingerprintPassword = fingerprintPassword;
	}
	public FreePayment getFreePayment() {
		return freePayment;
	}
	public void setFreePayment(FreePayment freePayment) {
		this.freePayment = freePayment;
	}
	@Column(length=500)
	public String getIDcardPhoto() {
		return IDcardPhoto;
	}
	public void setIDcardPhoto(String iDcardPhoto) {
		IDcardPhoto = iDcardPhoto;
	}
	@Column(length=2000)
	public String getDrivingPhoto() {
		return drivingPhoto;
	}
	public void setDrivingPhoto(String drivingPhoto) {
		this.drivingPhoto = drivingPhoto;
	}
	public String getProtocolPhoto() {
		return protocolPhoto;
	}
	public void setProtocolPhoto(String protocolPhoto) {
		this.protocolPhoto = protocolPhoto;
	}
	public MemberStatus getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(MemberStatus memberStatus) {
		this.memberStatus = memberStatus;
	}
	public Integer getConsumptionAmount() {
		return consumptionAmount;
	}
	public void setConsumptionAmount(Integer consumptionAmount) {
		this.consumptionAmount = consumptionAmount;
	}
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}
	public Integer getFreePaymentQuota() {
		return freePaymentQuota;
	}
	public void setFreePaymentQuota(Integer freePaymentQuota) {
		this.freePaymentQuota = freePaymentQuota;
	}
	public String getFirstReviewResult() {
		return firstReviewResult;
	}
	public void setFirstReviewResult(String firstReviewResult) {
		this.firstReviewResult = firstReviewResult;
	}
	public String getLineDataResult() {
		return LineDataResult;
	}
	public void setLineDataResult(String lineDataResult) {
		LineDataResult = lineDataResult;
	}
	public String getSecondReviewResult() {
		return secondReviewResult;
	}
	public void setSecondReviewResult(String secondReviewResult) {
		this.secondReviewResult = secondReviewResult;
	}
	public Integer getBusinessNum() {
		return businessNum;
	}
	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}
	public Integer getGasNum() {
		return gasNum;
	}
	public void setGasNum(Integer gasNum) {
		this.gasNum = gasNum;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

}
