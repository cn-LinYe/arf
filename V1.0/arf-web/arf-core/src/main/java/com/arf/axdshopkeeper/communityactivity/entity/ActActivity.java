package com.arf.axdshopkeeper.communityactivity.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="act_activity")
@SequenceGenerator(name="sequenceGenerator",sequenceName="act_activity_sequence")
public class ActActivity extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private AddType type;//添加方式	0总部添加 1店长添加
	@Deprecated
	private String communityNumber;//小区
	private String userName;//店长user_name
	private String shopName;//店铺名称
	private IsFaceSkip isFaceSkip;//是否直接跳转 0 否 1 是
	//详情编辑、直接跳转
	private String photo;//活动图片
	private String name;//活动名称
	//详情编辑
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private Integer number;//额定人数
	private IsExceed isExceed;//人数是否可超，0不可，1可以
	private IsName isName;//是否需填写姓名，0不填写，1填写
	private String address;//活动地点
	private String mobile;//联系电话
	private String content;//活动详情
	private IsSkipButton isSkipButton;//是否设置跳转按钮，0不设置，1设置
	private String skipButtonName;//按钮文字
	private BodyType bodyType;//跳转设置，0 跳转到外链/安心购
	private String bodySkipUrl;//跳转地址
	//直接跳转
	private String detail;//描述
	private FaceSkipType faceSkipType;//直接跳转类型，0跳转到外链/安心购，
	private String faceUrl;//直接跳转url地址
	//其他活动内容
	private IsLaunch isLaunch;//是否启用，0启用，1 不启用
	private String otherName;//标签
	private String otherPhoto;//封面
	private OtherSkipType otherSkipType;//其他活动跳转，0跳转到外链/安心购
	private String otherUrl;//其他活动地址
	
	private Status status;//状态 0新建，1删除，2审核驳回，3审核通过
	private Integer numberApplicant;//已报名人数
	private IsPayment isPayment;//是否缴费 0 false , 1 true
	private BigDecimal fee;//缴费金额
	private String feeName;//费用名称
	private IsAxdPartner isAxdPartner;//是否安心点合伙人招募 0 false , 1 true
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IsPayment{
		NO,YES
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IsAxdPartner{
		NO,YES
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		NORMAL,DELETED,REJECT,PASSED
	}
	
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AddType{
		ANERFA_ADD,SHOPKEEPER_ADD
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IsExceed{
		NO,YES
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IsName{
		NO,YES
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IsFaceSkip{
		NO,YES
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum FaceSkipType{
		ANXINGOU
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IsSkipButton{
		NO,YES
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum BodyType{
		ANXINGOU
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IsLaunch{
		USE,NO_USE
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum OtherSkipType{
		ANXINGOU
	}
	
	@Column(length = 20)
	public String getUserName() {
		return userName;
	}
	@Column(length = 20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	@Column(length = 255)
	public String getPhoto() {
		return photo;
	}
	@Column(length = 255)
	public String getName() {
		return name;
	}
	@Type(type = "text")
	@Column(length=65535)
	public String getContent() {
		return content;
	}
	@Column(length = 255)
	public String getAddress() {
		return address;
	}
	public Integer getNumber() {
		return number;
	}
	public IsPayment getIsPayment() {
		return isPayment;
	}
	@Column(precision = 10, scale = 2)
	public BigDecimal getFee() {
		return fee;
	}
	@Column(length = 255)
	public String getFeeName() {
		return feeName;
	}
	public IsAxdPartner getIsAxdPartner() {
		return isAxdPartner;
	}
	@Column(length = 20)
	public String getMobile() {
		return mobile;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public Status getStatus() {
		return status;
	}
	public Integer getNumberApplicant() {
		return numberApplicant;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public void setIsPayment(IsPayment isPayment) {
		this.isPayment = isPayment;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public void setIsAxdPartner(IsAxdPartner isAxdPartner) {
		this.isAxdPartner = isAxdPartner;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public void setNumberApplicant(Integer numberApplicant) {
		this.numberApplicant = numberApplicant;
	}
	
	public AddType getType() {
		return type;
	}
	public String getShopName() {
		return shopName;
	}
	public IsExceed getIsExceed() {
		return isExceed;
	}
	public IsName getIsName() {
		return isName;
	}
	public IsFaceSkip getIsFaceSkip() {
		return isFaceSkip;
	}
	public FaceSkipType getFaceSkipType() {
		return faceSkipType;
	}
	public String getFaceUrl() {
		return faceUrl;
	}
	public IsSkipButton getIsSkipButton() {
		return isSkipButton;
	}
	@Column(length = 20)
	public String getSkipButtonName() {
		return skipButtonName;
	}
	public BodyType getBodyType() {
		return bodyType;
	}
	public String getBodySkipUrl() {
		return bodySkipUrl;
	}
	public IsLaunch getIsLaunch() {
		return isLaunch;
	}
	public String getOtherName() {
		return otherName;
	}
	public String getOtherPhoto() {
		return otherPhoto;
	}
	public OtherSkipType getOtherSkipType() {
		return otherSkipType;
	}
	public String getOtherUrl() {
		return otherUrl;
	}
	@Type(type = "text")
	@Column(length=65535)
	public String getDetail() {
		return detail;
	}
	public void setType(AddType type) {
		this.type = type;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public void setIsExceed(IsExceed isExceed) {
		this.isExceed = isExceed;
	}
	public void setIsName(IsName isName) {
		this.isName = isName;
	}
	public void setIsFaceSkip(IsFaceSkip isFaceSkip) {
		this.isFaceSkip = isFaceSkip;
	}
	public void setFaceSkipType(FaceSkipType faceSkipType) {
		this.faceSkipType = faceSkipType;
	}
	public void setFaceUrl(String faceUrl) {
		this.faceUrl = faceUrl;
	}
	public void setIsSkipButton(IsSkipButton isSkipButton) {
		this.isSkipButton = isSkipButton;
	}
	public void setSkipButtonName(String skipButtonName) {
		this.skipButtonName = skipButtonName;
	}
	public void setBodyType(BodyType bodyType) {
		this.bodyType = bodyType;
	}
	public void setBodySkipUrl(String bodySkipUrl) {
		this.bodySkipUrl = bodySkipUrl;
	}
	public void setIsLaunch(IsLaunch isLaunch) {
		this.isLaunch = isLaunch;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	public void setOtherPhoto(String otherPhoto) {
		this.otherPhoto = otherPhoto;
	}
	public void setOtherSkipType(OtherSkipType otherSkipType) {
		this.otherSkipType = otherSkipType;
	}
	public void setOtherUrl(String otherUrl) {
		this.otherUrl = otherUrl;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	

}
