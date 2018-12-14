/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.listener;

import javax.annotation.Resource;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import com.arf.core.entity.Article;
import com.arf.core.entity.Goods;
import com.arf.core.service.ArticleService;
import com.arf.core.service.GoodsService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Listener - 缓存
 * 
 * @author arf
 * @version 4.0
 */
@Component("cacheEventListener")
public class CacheEventListener implements net.sf.ehcache.event.CacheEventListener {

	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public void notifyElementEvicted(Ehcache ehcache, Element element) {
	}

	public void notifyElementExpired(Ehcache ehcache, Element element) {
		String cacheName = ehcache.getName();
		if (StringUtils.equals(cacheName, Article.HITS_CACHE_NAME)) {
			Long id = (Long) element.getObjectKey();
			Long hits = (Long) element.getObjectValue();
			Article article = articleService.find(id);
			if (article != null && hits != null && hits > 0) {
				article.setHits(hits);
				articleService.update(article);
			}
		} else if (StringUtils.equals(cacheName, Goods.HITS_CACHE_NAME)) {
			Long id = (Long) element.getObjectKey();
			Long hits = (Long) element.getObjectValue();
			Goods goods = goodsService.find(id);
			if (goods != null && hits != null && hits > 0) {
				goods.setHits(hits);
				goodsService.update(goods);
			}
		}
	}

	public void notifyElementPut(Ehcache ehcache, Element element) throws CacheException {
	}

	public void notifyElementRemoved(Ehcache ehcache, Element element) throws CacheException {
	}

	public void notifyElementUpdated(Ehcache ehcache, Element element) throws CacheException {
	}

	public void notifyRemoveAll(Ehcache ehcache) {
	}

	public void dispose() {
	}

}