package com.arf.axdshopkeeper.shopkeeperrecommend.dto;

import com.arf.axdshopkeeper.shopkeeperrecommend.entity.CofRecommend;

public class CofRecommendExtra extends CofRecommend {
	private static final long serialVersionUID = 4258379154110547631L;
	
	private Integer isLike;//是否点赞，0未点赞，1已点赞
	private Integer isComment;//是否评论，0未评论，1已评论
	private Integer isFavorites;//是否收藏，0未收藏，1已收藏
	public Integer getIsLike() {
		return isLike;
	}
	public Integer getIsComment() {
		return isComment;
	}
	public Integer getIsFavorites() {
		return isFavorites;
	}
	public void setIsLike(Integer isLike) {
		this.isLike = isLike;
	}
	public void setIsComment(Integer isComment) {
		this.isComment = isComment;
	}
	public void setIsFavorites(Integer isFavorites) {
		this.isFavorites = isFavorites;
	}
}
