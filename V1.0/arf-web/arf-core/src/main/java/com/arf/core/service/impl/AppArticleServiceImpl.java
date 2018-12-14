/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.arf.core.dao.AppArticleDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.AppArticle;
import com.arf.core.service.AppArticleService;


/**
 * Service - app文章
 * 
 * @author arf app文章
 * @version 4.0
 */
@Service("appArticleServiceImpl")
public class AppArticleServiceImpl extends BaseServiceImpl<AppArticle, Long> implements AppArticleService {

	@Resource(name = "appArticleDaoImpl")
	private AppArticleDao appArticleDao;

	@Override
	protected BaseDao<AppArticle, Long> getBaseDao() {
		return appArticleDao;
	}

}