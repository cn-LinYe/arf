package com.arf.relievedassist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "relieved_assist_comment_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "relieved_assist_comment_record_sequence")
public class RelievedAssistCommentRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6660369954678939840L;
	
	private Long contentId;//帮帮帖ID
	private Long commentId;//父评论ID
	private String comment;//评论内容
	private String userName;//评论人
	private Integer praiseNumber;//点赞数
	private CommentType commentType;//评论类型（0、公开 Public 1、私信 Private ）
	private String communityNumber;//小区编号
	private Status status;//状态(0、正常Normal) 1、异常（被举报） Abnormal 2、删除 Del
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum CommentType{
		Public,//公开 
		Private;//私信
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		Normal,//正常
		Abnormal,//异常（被举报）
		Del;//删除
	}
	
	public Long getContentId() {
		return contentId;
	}
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	@Column(length=500)
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Column(length=20)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getPraiseNumber() {
		return praiseNumber;
	}
	public void setPraiseNumber(Integer praiseNumber) {
		this.praiseNumber = praiseNumber;
	}
	public CommentType getCommentType() {
		return commentType;
	}
	public void setCommentType(CommentType commentType) {
		this.commentType = commentType;
	}
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

}
