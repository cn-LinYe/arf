package com.arf.relievedassist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "relieved_assist_user_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "relieved_assist_user_record_sequence")
public class RelievedAssistUserRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2904683392191364415L;
	
	private String userName;//用户账号
	private String communityNumber;//小区编号
	private String buildingName;//楼栋信息
	private Integer building;//楼栋编号
	private Integer unit;//单元信息
	private String unitName;//	单元名称
	private Long gradeId;//等级类型
	private Integer contentNumber;//帖子条数
	private Integer collectionNumber;//收藏数
	private Integer praiseNumber;//点赞数
	private Integer commentNumber;//评论数
	private Integer assistPoint;//帮帮积分
	private Integer commentedNumber;//被评论数
	private Integer collectedNumber;//被收藏数
	private Integer praisedNumber;//被点赞数
	private Status status;//状态（0 Normal 正常 1 Prohibited 禁言 2 Deleted 已解绑）
	private Boolean isMain;//是否主用户（true 是 false否）
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		Normal,//正常
		Prohibited,//禁言
		Deleted;//已解绑
	}
	
	@Column(length=20)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(length=20)
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public Integer getBuilding() {
		return building;
	}
	public void setBuilding(Integer building) {
		this.building = building;
	}
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	@Column(length=20)
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Long getGradeId() {
		return gradeId;
	}
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	public Integer getContentNumber() {
		return contentNumber;
	}
	public void setContentNumber(Integer contentNumber) {
		this.contentNumber = contentNumber;
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
	public Integer getCommentNumber() {
		return commentNumber;
	}
	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}
	public Integer getAssistPoint() {
		return assistPoint;
	}
	public void setAssistPoint(Integer assistPoint) {
		this.assistPoint = assistPoint;
	}
	public Integer getCommentedNumber() {
		return commentedNumber;
	}
	public void setCommentedNumber(Integer commentedNumber) {
		this.commentedNumber = commentedNumber;
	}
	public Integer getCollectedNumber() {
		return collectedNumber;
	}
	public void setCollectedNumber(Integer collectedNumber) {
		this.collectedNumber = collectedNumber;
	}
	public Integer getPraisedNumber() {
		return praisedNumber;
	}
	public void setPraisedNumber(Integer praisedNumber) {
		this.praisedNumber = praisedNumber;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Boolean getIsMain() {
		return isMain;
	}
	public void setIsMain(Boolean isMain) {
		this.isMain = isMain;
	}

}
