/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import com.arf.core.Filter;
import com.arf.core.Order;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.ArticleCategoryDao;
import com.arf.core.dao.ArticleDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.TagDao;
import com.arf.core.entity.Article;
import com.arf.core.entity.ArticleCategory;
import com.arf.core.entity.Tag;
import com.arf.core.service.ArticleService;
import com.arf.core.service.StaticService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 文章
 * 
 * @author arf
 * @version 4.0
 */
@Service("articleServiceImpl")
public class ArticleServiceImpl extends BaseServiceImpl<Article, Long> implements ArticleService {

	@Resource(name = "ehCacheManager")
	private CacheManager cacheManager;
	@Resource(name = "articleDaoImpl")
	private ArticleDao articleDao;
	@Resource(name = "articleCategoryDaoImpl")
	private ArticleCategoryDao articleCategoryDao;
	@Resource(name = "tagDaoImpl")
	private TagDao tagDao;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;

	@Override
	protected BaseDao<Article, Long> getBaseDao() {
		return articleDao;
	}

	@Transactional(readOnly = true)
	public List<Article> findList(ArticleCategory articleCategory, Tag tag, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders) {
		return articleDao.findList(articleCategory, tag, isPublication, count, filters, orders);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "article", condition = "#useCache")
	public List<Article> findList(Long articleCategoryId, Long tagId, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		ArticleCategory articleCategory = articleCategoryDao.find(articleCategoryId);
		if (articleCategoryId != null && articleCategory == null) {
			return Collections.emptyList();
		}
		Tag tag = tagDao.find(tagId);
		if (tagId != null && tag == null) {
			return Collections.emptyList();
		}
		return articleDao.findList(articleCategory, tag, isPublication, count, filters, orders);
	}

	@Transactional(readOnly = true)
	public List<Article> findList(ArticleCategory articleCategory, Boolean isPublication, Date beginDate, Date endDate, Integer first, Integer count) {
		return articleDao.findList(articleCategory, isPublication, beginDate, endDate, first, count);
	}

	@Transactional(readOnly = true)
	public Page<Article> findPage(ArticleCategory articleCategory, Tag tag, Boolean isPublication, Pageable pageable) {
		return articleDao.findPage(articleCategory, tag, isPublication, pageable);
	}

	public long viewHits(Long id) {
		Assert.notNull(id);

		Ehcache cache = cacheManager.getEhcache(Article.HITS_CACHE_NAME);
		Element element = cache.get(id);
		Long hits;
		if (element != null) {
			hits = (Long) element.getObjectValue() + 1;
		} else {
			Article article = articleDao.find(id);
			if (article == null) {
				return 0L;
			}
			hits = article.getHits() + 1;
		}
		cache.put(new Element(id, hits));
		return hits;
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public Article save(Article article) {
		Assert.notNull(article);

		Article pArticle = super.save(article);
		articleDao.flush();
		articleDao.refresh(pArticle);
		staticService.build(pArticle);
		return pArticle;
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public Article update(Article article) {
		Assert.notNull(article);

		Article pArticle = super.update(article);
		articleDao.flush();
		articleDao.refresh(pArticle);
		staticService.build(pArticle);
		return pArticle;
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public Article update(Article article, String... ignoreProperties) {
		return super.update(article, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public void delete(Article article) {
		if (article != null) {
			staticService.delete(article);
		}
		super.delete(article);
	}

}