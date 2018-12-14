/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

/**
 * Entity - SEO设置
 * 
 * @author arf
 * @version 4.0
 */
@Entity
@Table(name = "xx_seo")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_seo_sequence")
public class Seo extends BaseEntity<Long> {

	private static final long serialVersionUID = -3503657242384822672L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 首页 */
		index,

		/** 文章列表 */
		articleList,

		/** 文章搜索 */
		articleSearch,

		/** 文章内容 */
		articleContent,

		/** 商品列表 */
		goodsList,

		/** 商品搜索 */
		goodsSearch,

		/** 商品内容 */
		goodsContent,

		/** 品牌列表 */
		brandList,

		/** 品牌内容 */
		brandContent
	}

	/** 类型 */
	private Type type;

	/** 页面标题 */
	private String title;

	/** 页面关键词 */
	private String keywords;

	/** 页面描述 */
	private String description;

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	@Column(nullable = false, updatable = false, unique = true)
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取页面标题
	 * 
	 * @return 页面标题
	 */
	@Length(max = 200)
	public String getTitle() {
		return title;
	}

	/**
	 * 设置页面标题
	 * 
	 * @param title
	 *            页面标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取页面关键词
	 * 
	 * @return 页面关键词
	 */
	@Length(max = 200)
	public String getKeywords() {
		return keywords;
	}

	/**
	 * 设置页面关键词
	 * 
	 * @param keywords
	 *            页面关键词
	 */
	public void setKeywords(String keywords) {
		if (keywords != null) {
			keywords = keywords.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "");
		}
		this.keywords = keywords;
	}

	/**
	 * 获取页面描述
	 * 
	 * @return 页面描述
	 */
	@Length(max = 200)
	public String getDescription() {
		return description;
	}

	/**
	 * 设置页面描述
	 * 
	 * @param description
	 *            页面描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}