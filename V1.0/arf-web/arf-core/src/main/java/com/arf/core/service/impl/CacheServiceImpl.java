/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import com.arf.core.service.CacheService;
import com.arf.core.service.ConfigService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * Service - 缓存
 * 
 * @author arf
 * @version 4.0
 */
@Service("cacheServiceImpl")
public class CacheServiceImpl implements CacheService {

	@Resource(name = "ehCacheManager")
	private CacheManager cacheManager;
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource(name = "messageSource")
	private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;
	@Resource(name = "configServiceImpl")
	private ConfigService configService;

	public String getDiskStorePath() {
		return cacheManager.getConfiguration().getDiskStoreConfiguration().getPath();
	}

	public int getCacheSize() {
		int cacheSize = 0;
		String[] cacheNames = cacheManager.getCacheNames();
		if (cacheNames != null) {
			for (String cacheName : cacheNames) {
				Ehcache cache = cacheManager.getEhcache(cacheName);
				if (cache != null) {
					cacheSize += cache.getSize();
				}
			}
		}
		return cacheSize;
	}

	@CacheEvict(value = { "setting", "logConfig", "templateConfig", "pluginConfig", "messageConfig", "area", "seo", "adPosition", "memberAttribute", "navigation", "tag", "friendLink", "brand", "article", "articleCategory", "goods", "productCategory", "review", "consultation", "promotion",
			"shipping", "authorization" }, allEntries = true)
	public void clear() {
		freeMarkerConfigurer.getConfiguration().clearTemplateCache();
		reloadableResourceBundleMessageSource.clearCache();
		configService.init();
	}

}