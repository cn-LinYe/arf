package com.arf.review.entity;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 评论记录表,该表仅针对非商城的服务订单,商城订单评论{@link com.arf.core.entity.Review}
 * @author Caolibao
 *
 */
@Entity
@Table(name = "r_service_review")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_service_review_sequence")
public class ServiceReview extends BaseEntity<Long>{
	private static final long serialVersionUID = 8021457077005083998L;
	
	/** 评分 */
	private Integer score;

	/** 内容 */
	private String content;

	/** 是否显示 */
	private Boolean isShow;

	/** 会员 */
	private String username;
	
	/**
	 * 商户编码
	 */
	private String businessNum;
	
	/**
	 * 评论
	 */
	private ServiceReview forReview;
	
	/**
	 * 回复评论
	 */
	private Set<ServiceReview>  replyReviews = new TreeSet<ServiceReview>();
	
	/**
	 * 归属用户类型,标识该记录来自用户还是商户
	 */
	private BelongType belongType;
		
	/**所属订单 一般为对应订单的out_trade_no字段*/
	private String orderNo;
	
	/**
	 * 订单实体类型
	 */
	private Type type;
	
	/**
	 * 图片,一个JSONArray字符串,最多5张
	 */
	private String images;
	
	/**
	 * 是否匿评价 0:不匿名;1:匿名
	 */
	private Integer anonymous;
	
	/**
	 * 订单实体类型枚举 可以通过com.arf.review.entity.ServiceReview.OrderTypeClass.getOrderTypeClass()获取
	 */
	public enum Type {
		/** 洗衣帮帮 */
		LaundryService,
		/** 点滴洗 */
		CarBrighter,
		/** 保险服务  */
		Insurance,
		/** 快递服务  */
		Expressage,
		/** 违章处理服务  */
		Violation,
		;
	}
	
	/**
	 * 归属用户类型
	 */
	public enum BelongType {

		/** 用户评论 */
		Member,

		/** 商户回复 */
		Business,
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(length=512)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	@JsonIgnore
	public ServiceReview getForReview() {
		return forReview;
	}

	public void setForReview(ServiceReview forReview) {
		this.forReview = forReview;
	}

	@OneToMany(mappedBy = "forReview", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate asc")
	public Set<ServiceReview> getReplyReviews() {
		return replyReviews;
	}

	public void setReplyReviews(Set<ServiceReview> replyReviews) {
		this.replyReviews = replyReviews;
	}

	public BelongType getBelongType() {
		return belongType;
	}

	public void setBelongType(BelongType belongType) {
		this.belongType = belongType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Column(length=1024)
	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Integer getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}
}
