package com.arf.anxinshop.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "es_hot_article")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "es_hot_article_sequence")
public class EsHotArticle extends BaseEntity<Long> {

	private static final long serialVersionUID = -4656292643784849337L;
	
	private String title;//文章标题
	private String content;//文章内容(富文本或则http url)
	private Integer indexOrder;//序号
	private Top top;//是否置顶,为1置顶
	private String groupIds;//用户分组,多个英文逗号分隔
	private Guest guest;//游客是否可见,为1可见
	private String previewImg;//预览图
	private String redirectBtnText;//跳转文字
	private String redirectBtnHref;//跳转链接
	private Date pubDate;
	private String scopeNumbers;//发布范围
	private ScopeType scopeType;//发布范围类型,4:渠道,3:联营公司,2:物业,1:小区,0:不限
	private Status status;//状态,0:待审核,1:已审核
	private IsPush isPush;//是否推送 0 不推送 1 推送
	private String pushTitle;//推送标题 不超过25字
	private String pushContent;//推送内容 不超过50字
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IsPush{
		NO,YES
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Top{
		NO,YES
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Guest{
		NO,YES
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ScopeType{
		NO_LIMIT,COMMUNITY,PROPERTY,BRANCH,CHANNEL
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		ADUIT,PASS
	}
	@Column(length=100)
	public String getTitle() {
		return title;
	}
	@Type(type = "text")
	@Column(length=65535,nullable=false)
	public String getContent() {
		return content;
	}
	@Column(length=11)
	public Integer getIndexOrder() {
		return indexOrder;
	}
	public Top getTop() {
		return top;
	}
	@Column(length=100)
	public String getGroupIds() {
		return groupIds;
	}
	public Guest getGuest() {
		return guest;
	}
	@Column(length=100)
	public String getPreviewImg() {
		return previewImg;
	}
	@Column(length=100)
	public String getRedirectBtnText() {
		return redirectBtnText;
	}
	@Column(length=100)
	public String getRedirectBtnHref() {
		return redirectBtnHref;
	}
	public Date getPubDate() {
		return pubDate;
	}
	@Type(type = "text")
	@Column(length=65535)
	public String getScopeNumbers() {
		return scopeNumbers;
	}
	public ScopeType getScopeType() {
		return scopeType;
	}
	public Status getStatus() {
		return status;
	}
	public IsPush getIsPush() {
		return isPush;
	}
	@Column(length=50)
	public String getPushTitle() {
		return pushTitle;
	}
	@Column(length=100)
	public String getPushContent() {
		return pushContent;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setIndexOrder(Integer indexOrder) {
		this.indexOrder = indexOrder;
	}
	public void setTop(Top top) {
		this.top = top;
	}
	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}
	public void setGuest(Guest guest) {
		this.guest = guest;
	}
	public void setPreviewImg(String previewImg) {
		this.previewImg = previewImg;
	}
	public void setRedirectBtnText(String redirectBtnText) {
		this.redirectBtnText = redirectBtnText;
	}
	public void setRedirectBtnHref(String redirectBtnHref) {
		this.redirectBtnHref = redirectBtnHref;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	public void setScopeNumbers(String scopeNumbers) {
		this.scopeNumbers = scopeNumbers;
	}
	public void setScopeType(ScopeType scopeType) {
		this.scopeType = scopeType;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public void setIsPush(IsPush isPush) {
		this.isPush = isPush;
	}
	public void setPushTitle(String pushTitle) {
		this.pushTitle = pushTitle;
	}
	public void setPushContent(String pushContent) {
		this.pushContent = pushContent;
	}
}
