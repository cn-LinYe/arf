/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import com.arf.core.Theme;
import com.arf.core.service.ThemeService;
import com.arf.core.util.CompressUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service - 主题
 * 
 * @author arf
 * @version 4.0
 */
@Service("themeServiceImpl")
public class ThemeServiceImpl implements ThemeService, ServletContextAware {

	/** servletContext */
	private ServletContext servletContext;

	@Value("${theme.template_path}")
	private String themeTemplatePath;
	@Value("${theme.resource_path}")
	private String themeResourcePath;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public List<Theme> getAll() {
		File[] files = new File(servletContext.getRealPath(themeTemplatePath)).listFiles(new FileFilter() {
			public boolean accept(File file) {
				File themeXmlFile = new File(file, "theme.xml");
				return themeXmlFile.exists() && themeXmlFile.isFile();
			}
		});
		List<Theme> themes = new ArrayList<Theme>();
		for (File file : files) {
			File themeXmlFile = new File(file, "theme.xml");
			themes.add(get(themeXmlFile));
		}
		return themes;
	}

	public Theme get(String id) {
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		File themeXmlFile = new File(servletContext.getRealPath(themeTemplatePath + "/" + id), "theme.xml");
		if (themeXmlFile.exists() && themeXmlFile.isFile()) {
			return get(themeXmlFile);
		}
		return null;
	}

	public boolean upload(MultipartFile multipartFile) {
		if (multipartFile == null || multipartFile.isEmpty()) {
			return false;
		}
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		File tempThemeFile = new File(tempDir, "/upload_" + UUID.randomUUID() + ".tmp");
		File tempThemeDir = new File(tempDir, "/upload_" + UUID.randomUUID());
		try {
			tempDir.mkdirs();
			multipartFile.transferTo(tempThemeFile);
			CompressUtils.unZip(tempThemeFile, tempThemeDir);
			File themeXmlFile = new File(tempThemeDir, "/template/theme.xml");
			if (themeXmlFile.exists() && themeXmlFile.isFile()) {
				Theme theme = get(themeXmlFile);
				if (theme != null && StringUtils.isNotEmpty(theme.getId())) {
					FileUtils.moveDirectory(new File(tempThemeDir, "/template"), new File(servletContext.getRealPath(themeTemplatePath), theme.getId()));
					FileUtils.moveDirectory(new File(tempThemeDir, "/resources"), new File(servletContext.getRealPath(themeResourcePath), theme.getId()));
					return true;
				}
			}
		} catch (IllegalStateException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			FileUtils.deleteQuietly(tempThemeFile);
			FileUtils.deleteQuietly(tempThemeDir);
		}
		return false;
	}

	/**
	 * 获取主题
	 * 
	 * @param themeXmlFile
	 *            主题配置文件
	 */
	private Theme get(File themeXmlFile) {
		try {
			Document document = new SAXReader().read(themeXmlFile);
			Node idNode = document.selectSingleNode("/theme/id");
			Node nameNode = document.selectSingleNode("/theme/name");
			Node versionNode = document.selectSingleNode("/theme/version");
			Node authorNode = document.selectSingleNode("/theme/author");
			Node siteUrlNode = document.selectSingleNode("/theme/siteUrl");
			Node previewNode = document.selectSingleNode("/theme/preview");

			Theme theme = new Theme();
			theme.setId(idNode != null ? idNode.getText().trim() : null);
			theme.setName(nameNode != null ? nameNode.getText().trim() : null);
			theme.setVersion(versionNode != null ? versionNode.getText().trim() : null);
			theme.setAuthor(authorNode != null ? authorNode.getText().trim() : null);
			theme.setSiteUrl(siteUrlNode != null ? siteUrlNode.getText().trim() : null);
			theme.setPreview(previewNode != null ? previewNode.getText().trim() : null);
			return theme;
		} catch (DocumentException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}