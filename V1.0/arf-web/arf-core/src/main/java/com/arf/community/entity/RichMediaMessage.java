package com.arf.community.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="msg_rich_media_message")
@SequenceGenerator(name="sequenceGenerator",sequenceName="msg_rich_media_message_sequence")
public class RichMediaMessage extends BaseEntity<Long> {

	private static final long serialVersionUID = -8233123713201377057L;
	public static final String COMMUNITY_EXPRESS = "COMMUNITY_EXPRESS";

	private String creator;//创建人
	private String contentType;//消息类型
	private String mainTitle;//主标题
	private String title;//标题
	private String previewImg;//预览图地址
	private String snippet;//正文摘要
	private String clkBtnText;//点击按钮文字
	private String msgContent;//消息内容, 如果整个内容是一个http(s)的连接则直接加载该连接,否则将该字段当做HTML文档内容加载到WebView
	private Date endDate;//过期时间,为null则永久有效
	private IsOverdue isOverdue;//过期枚举:0 NORMAL,1 OVERDUE

	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IsOverdue{
		NORMAL,OVERDUE
	}

	@Column(length=30)
	public String getCreator() {
		return creator;
	}

	@Column(length=20)
	public String getContentType() {
		return contentType;
	}

	@Column(length=100)
	public String getMainTitle() {
		return mainTitle;
	}

	@Column(length=100,nullable=false)
	public String getTitle() {
		return title;
	}

	@Column(length=200,nullable=false)
	public String getPreviewImg() {
		return previewImg;
	}

	@Column(length=300)
	public String getSnippet() {
		return snippet;
	}

	@Column(length=10)
	public String getClkBtnText() {
		return clkBtnText;
	}

	@Type(type = "text")
	@Column(length=65535,nullable=false)
	public String getMsgContent() {
		return msgContent;
	}

	public Date getEndDate() {
		return endDate;
	}

	public IsOverdue getIsOverdue() {
		return isOverdue;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPreviewImg(String previewImg) {
		this.previewImg = previewImg;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public void setClkBtnText(String clkBtnText) {
		this.clkBtnText = clkBtnText;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setIsOverdue(IsOverdue isOverdue) {
		this.isOverdue = isOverdue;
	}

}
