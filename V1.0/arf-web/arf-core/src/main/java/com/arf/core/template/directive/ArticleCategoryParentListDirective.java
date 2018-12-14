/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.arf.core.entity.ArticleCategory;
import com.arf.core.service.ArticleCategoryService;
import com.arf.core.util.FreeMarkerUtils;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 上级文章分类列表
 * 
 * @author arf
 * @version 4.0
 */
@Component("articleCategoryParentListDirective")
public class ArticleCategoryParentListDirective extends BaseDirective {

	/** "文章分类ID"参数名称 */
	private static final String ARTICLE_CATEGORY_ID_PARAMETER_NAME = "articleCategoryId";

	/** "是否递归"参数名称 */
	private static final String RECURSIVE_PARAMETER_NAME = "recursive";

	/** 变量名称 */
	private static final String VARIABLE_NAME = "articleCategories";

	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Long articleCategoryId = FreeMarkerUtils.getParameter(ARTICLE_CATEGORY_ID_PARAMETER_NAME, Long.class, params);
		Boolean recursive = FreeMarkerUtils.getParameter(RECURSIVE_PARAMETER_NAME, Boolean.class, params);
		Integer count = getCount(params);
		boolean useCache = useCache(env, params);
		List<ArticleCategory> articleCategories = articleCategoryService.findParents(articleCategoryId, recursive != null ? recursive : true, count, useCache);
		setLocalVariable(VARIABLE_NAME, articleCategories, env, body);
	}

}