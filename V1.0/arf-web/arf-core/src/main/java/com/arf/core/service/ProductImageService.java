/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service;

import java.util.List;

import com.arf.core.entity.ProductImage;

/**
 * Service - 商品图片
 * 
 * @author arf
 * @version 4.0
 */
public interface ProductImageService {

	/**
	 * 商品图片过滤
	 * 
	 * @param productImages
	 *            商品图片
	 */
	void filter(List<ProductImage> productImages);

	/**
	 * 商品图片文件验证
	 * 
	 * @param productImage
	 *            商品图片
	 * @return 是否验证通过
	 */
	boolean isValid(ProductImage productImage);

	/**
	 * 生成商品图片(异步)
	 * 
	 * @param productImage
	 *            商品图片
	 */
	void build(ProductImage productImage);

	/**
	 * 生成商品图片(异步)
	 * 
	 * @param productImages
	 *            商品图片
	 */
	void build(List<ProductImage> productImages);

}