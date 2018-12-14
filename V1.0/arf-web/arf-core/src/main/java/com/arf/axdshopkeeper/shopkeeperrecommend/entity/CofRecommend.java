package com.arf.axdshopkeeper.shopkeeperrecommend.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

//店长推荐表
@Entity
@Table(name = "cof_recommend")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cof_recommend_sequence")
public class CofRecommend extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 7563423296155556486L;
	private String userName;//发布人userName，店长发布则为店长userName
	private String communityNumber;//小区编号
	private Type type;//推荐类型：0总部推荐，1店长推荐
	private String name;//推荐人姓名
	private String photo;//推荐人头像
	private String introduce;//达人介绍
	private String contentName;//推荐标题
	private String content;//推荐内容
	private String contentPhoto;//内容图片
	private Status status;//状态：0新建，1删除，2审核驳回，3审核通过
	private Integer likeCount;//点赞数
	private Integer commentCount;//评论数
	private Integer favoritesCount;//收藏数
	private String recommendPhoto;//商品图片
	private String recommendTitle;//商品标题
	private int recommendPrice;//商品价格 单位：分
	private String recommendUrl;//商品购买链接
	private Date stickTime;//置顶时间
	private FileType fileType;//contentPhoto类型 0 图片 1 小视频
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum FileType{
		img,//0 图片
		video;//1 小视频
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Type{
		HEADQUARTERS,//总部推荐
		SHOPERKEEPER//店长推荐
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		NEW,//正常
		DELETE,//删除
		REJECT,//审核驳回
		PASS//通过
	}
	@Column(length = 20,nullable=false)
	public String getUserName() {
		return userName;
	}
	@Column(length = 32)
	public String getCommunityNumber() {
		return communityNumber;
	}
	@Column(nullable=false)
	public Type getType() {
		return type;
	}
	@Column(length=255,nullable=false)
	public String getName() {
		return name;
	}
	@Column(length=255)
	public String getPhoto() {
		return photo;
	}
	@Column(length=20)
	public String getIntroduce() {
		return introduce;
	}
	@Column(length=255)
	public String getContentName() {
		return contentName;
	}
	@Column(length=3000)
	public String getContent() {
		return content;
	}
	@Column(length=2048)
	public String getContentPhoto() {
		return contentPhoto;
	}
	@Column(nullable=false)
	public Status getStatus() {
		return status;
	}
	public Integer getLikeCount() {
		return likeCount;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public Integer getFavoritesCount() {
		return favoritesCount;
	}
	@Column(length=255)
	public String getRecommendPhoto() {
		return recommendPhoto;
	}
	@Column(length=255)
	public String getRecommendTitle() {
		return recommendTitle;
	}
	public int getRecommendPrice() {
		return recommendPrice;
	}
	@Column(length=255)
	public String getRecommendUrl() {
		return recommendUrl;
	}
	public void setRecommendPhoto(String recommendPhoto) {
		this.recommendPhoto = recommendPhoto;
	}
	public void setRecommendTitle(String recommendTitle) {
		this.recommendTitle = recommendTitle;
	}
	public void setRecommendPrice(int recommendPrice) {
		this.recommendPrice = recommendPrice;
	}
	public void setRecommendUrl(String recommendUrl) {
		this.recommendUrl = recommendUrl;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public void setFavoritesCount(Integer favoritesCount) {
		this.favoritesCount = favoritesCount;
	}
	public void setContentPhoto(String contentPhoto) {
		this.contentPhoto = contentPhoto;
	}
	public Date getStickTime() {
		return stickTime;
	}
	public void setStickTime(Date stickTime) {
		this.stickTime = stickTime;
	}
	public FileType getFileType() {
		return fileType;
	}
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
	
}
