package com.arf.relievedassist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "relieved_assist_collection_praise_browse_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "relieved_assist_collection_praise_browse_record_sequence")
public class RelievedAssistCollectionPraiseBrowseRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7004419565078440L;
	
	private String userName;//收藏用户
	private Long contentId;//帮帮贴ID
	private Long commentId;//评论ID
	private CollectionStatus collectionStatus;//状态(0、正常 Normal 1、失效 Failure)
	private Type type;//操作类型(0、收藏 Collection 1、点赞 Praise 2、浏览 Browse 3 分享 Share 4 点击链接 ClickUrl 5 Buy 购买商品6 Apply_Top 申请置顶）
	private String communityNumber;//用户绑定小区
	private Integer browseDuration;//浏览时长（秒）
	private String goodsUrl;//商品链接
	private SourceType sourceType;//浏览来源
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum CollectionStatus{
		Normal,//正常
		Failure;//失效
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Type{
		Collection,//收藏 
		Praise,//点赞
		Browse,//浏览
		Share,//分享
		ClickUrl,//点击链接
		Buy,//购买商品
		Apply_Top;//申请置顶
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum SourceType{
		AXD_HOME,//安心点首页
		AXD_SEARCH,//安心点搜索
		QQ,//qq
		WECHAT;//微信
	}
	
	@Column(length=20)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getContentId() {
		return contentId;
	}
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	public CollectionStatus getCollectionStatus() {
		return collectionStatus;
	}
	public void setCollectionStatus(CollectionStatus collectionStatus) {
		this.collectionStatus = collectionStatus;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public Integer getBrowseDuration() {
		return browseDuration;
	}
	public void setBrowseDuration(Integer browseDuration) {
		this.browseDuration = browseDuration;
	}
	@Column(length=100)
	public String getGoodsUrl() {
		return goodsUrl;
	}
	public void setGoodsUrl(String goodsUrl) {
		this.goodsUrl = goodsUrl;
	}
	public SourceType getSourceType() {
		return sourceType;
	}
	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

}
