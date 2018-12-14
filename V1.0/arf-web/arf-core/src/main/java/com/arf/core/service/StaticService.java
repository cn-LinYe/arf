/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service;

import java.util.Map;

import com.arf.core.entity.Article;
import com.arf.core.entity.Goods;

/**
 * Service - 静态化
 * 
 * @author arf
 * @version 4.0
 */
public interface StaticService {

	/**
	 * 生成静态
	 * 
	 * @param templatePath
	 *            模板文件路径
	 * @param staticPath
	 *            静态文件路径
	 * @param model
	 *            数据
	 * @return 生成数量
	 */
	int build(String templatePath, String staticPath, Map<String, Object> model);

	/**
	 * 生成静态
	 * 
	 * @param article
	 *            文章
	 * @return 生成数量
	 */
	int build(Article article);

	/**
	 * 生成静态
	 * 
	 * @param goods
	 *            货品
	 * @return 生成数量
	 */
	int build(Goods goods);

	/**
	 * 生成首页静态
	 * 
	 * @return 生成数量
	 */
	int buildIndex();

	/**
	 * 生成Sitemap
	 * 
	 * @return 生成数量
	 */
	int buildSitemap();

	/**
	 * 生成其它静态
	 * 
	 * @return 生成数量
	 */
	int buildOther();

	/**
	 * 生成所有静态
	 * 
	 * @return 生成数量
	 */
	int buildAll();

	/**
	 * 删除静态
	 * 
	 * @param staticPath
	 *            静态文件路径
	 * @return 删除数量
	 */
	int delete(String staticPath);

	/**
	 * 删除静态
	 * 
	 * @param article
	 *            文章
	 * @return 删除数量
	 */
	int delete(Article article);

	/**
	 * 删除静态
	 * 
	 * @param goods
	 *            货品
	 * @return 删除数量
	 */
	int delete(Goods goods);

	/**
	 * 删除首页静态
	 * 
	 * @return 删除数量
	 */
	int deleteIndex();

	/**
	 * 删除其它静态
	 * 
	 * @return 删除数量
	 */
	int deleteOther();

}