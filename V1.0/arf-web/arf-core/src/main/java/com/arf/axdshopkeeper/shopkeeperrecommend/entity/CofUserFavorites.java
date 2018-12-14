package com.arf.axdshopkeeper.shopkeeperrecommend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

//推荐点赞收藏表
@Entity
@Table(name="cof_user_favorites")
@SequenceGenerator(name="sequenceGenerator",sequenceName="cof_user_favorites_sequence")
public class CofUserFavorites extends BaseEntity<Long> {

	private static final long serialVersionUID = -5767006815511079061L;
	private String userName;//用户名
	private Long recommendId;//推荐id
	private Type type;//类型
	private Status status;//状态
	
	@JsonFormat(shape=JsonFormat.Shape.NUMBER_INT)
	public enum Type{
		LIKE,//喜欢
		COLLECT//收藏
	}
	@JsonFormat(shape=JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		NORMAL,//生效
		DELETE,//取消
	}
	@Column(length=20,nullable=false)
	public String getUserName() {
		return userName;
	}
	@Column(nullable=false)
	public Long getRecommendId() {
		return recommendId;
	}
	@Column(nullable=false)
	public Type getType() {
		return type;
	}
	@Column(nullable=false)
	public Status getStatus() {
		return status;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setRecommendId(Long recommendId) {
		this.recommendId = recommendId;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
}
