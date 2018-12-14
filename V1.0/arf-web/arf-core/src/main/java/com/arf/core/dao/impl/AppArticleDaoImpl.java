package com.arf.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.AppArticleDao;
import com.arf.core.entity.AppArticle;

/**
 * Dao - app文章
 * 
 * @author arf  app文章
 * @version 4.0
 */
@Repository("appArticleDaoImpl")
public class AppArticleDaoImpl extends BaseDaoImpl<AppArticle, Long> implements AppArticleDao {
	
}
