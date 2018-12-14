package com.arf.relievedassist.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "relieved_assist_content_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "relieved_assist_content_record_sequence")
public class RelievedAssistContentRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4066595143021534051L;
	
	private String userName;//发布者
	private Status status;//状态(0、正常Normal) 1、异常（被举报） Abnormal 2、删除 Del
	private String content;//发布内容
	private String assistImage;//图片数组，最多支持9张
	private Long classifyId;//分类枚举外键
	private String shoppingUrl;//安心购链接组，最多支持5个
	private String communityNumber;//小区编号
	private String remarks;//操作备注
	private ContentType contentType;//内容类型（0、文本 Text 1、语音 Audio 2、视频 Voice）
	private Integer commentsNumber;//评论数
	private Integer collectionNumber;//收藏数
	private Integer praiseNumber;//点赞数
	private String shoppingNumber;//商品组顺序点击数
	private String positioningInfo;//定位信息
	private String nickName;//昵称
	private UserType userType;//发帖用户类型（0 User 用户 1 Manager 管理员）
	private Date topTime;//置顶时间
	private Integer browseNumber;//点击数
	private String interestList;//帖子兴趣标签列表（标签id，以","号分割）
	private String shoppingImgUrl;//安心购商品图片组，最多支持5个
	private String shoppingName;//安心购商品名称组，最多支持5个
	private String shoppingPrice;//安心购商品价格组，最多支持5个
	private ApplyTopStatus applyTopStatus;//申请置顶状态（0 申请中 New 1 已同意 Agree 2 已驳回 Reject）
	private Date applyDate;//申请时间
	private Date auditDate;//审核时间
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		Normal,//正常
		Abnormal,//异常（被举报）
		Del;//删除
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ContentType{
		Text,//文本
		Audio,//语音
		Voice;//视频
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum UserType{
		User,//用户
		Manager;//管理员
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ApplyTopStatus{
		New,//申请中
		Agree,// 已同意
		Reject;// 已驳回
	}
	
	@Column(length=20)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	@Column(length=2000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Column(length=600)
	public String getAssistImage() {
		return assistImage;
	}

	public void setAssistImage(String assistImage) {
		this.assistImage = assistImage;
	}

	public Long getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(Long classifyId) {
		this.classifyId = classifyId;
	}
	@Column(length=300)
	public String getShoppingUrl() {
		return shoppingUrl;
	}

	public void setShoppingUrl(String shoppingUrl) {
		this.shoppingUrl = shoppingUrl;
	}
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(length=100)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public Integer getCommentsNumber() {
		return commentsNumber;
	}

	public void setCommentsNumber(Integer commentsNumber) {
		this.commentsNumber = commentsNumber;
	}

	public Integer getCollectionNumber() {
		return collectionNumber;
	}

	public void setCollectionNumber(Integer collectionNumber) {
		this.collectionNumber = collectionNumber;
	}

	public Integer getPraiseNumber() {
		return praiseNumber;
	}

	public void setPraiseNumber(Integer praiseNumber) {
		this.praiseNumber = praiseNumber;
	}
	@Column(length=20)
	public String getShoppingNumber() {
		return shoppingNumber;
	}

	public void setShoppingNumber(String shoppingNumber) {
		this.shoppingNumber = shoppingNumber;
	}
	public String getInterestList() {
		return interestList;
	}

	public void setInterestList(String interestList) {
		this.interestList = interestList;
	}

	@Column(length=50)
	public String getPositioningInfo() {
		return positioningInfo;
	}

	public void setPositioningInfo(String positioningInfo) {
		this.positioningInfo = positioningInfo;
	}
	@Column(length=20)
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Date getTopTime() {
		return topTime;
	}

	public void setTopTime(Date topTime) {
		this.topTime = topTime;
	}

	public Integer getBrowseNumber() {
		return browseNumber;
	}

	public void setBrowseNumber(Integer browseNumber) {
		this.browseNumber = browseNumber;
	}
	@Column(length=300)
	public String getShoppingImgUrl() {
		return shoppingImgUrl;
	}

	public void setShoppingImgUrl(String shoppingImgUrl) {
		this.shoppingImgUrl = shoppingImgUrl;
	}
	@Column(length=300)
	public String getShoppingName() {
		return shoppingName;
	}

	public void setShoppingName(String shoppingName) {
		this.shoppingName = shoppingName;
	}
	@Column(length=300)
	public String getShoppingPrice() {
		return shoppingPrice;
	}

	public void setShoppingPrice(String shoppingPrice) {
		this.shoppingPrice = shoppingPrice;
	}

	public ApplyTopStatus getApplyTopStatus() {
		return applyTopStatus;
	}

	public void setApplyTopStatus(ApplyTopStatus applyTopStatus) {
		this.applyTopStatus = applyTopStatus;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

}
