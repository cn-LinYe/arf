/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import com.arf.core.FileInfo.FileType;
import com.arf.core.Setting;
import com.arf.core.entity.ProductImage;
import com.arf.core.plugin.StoragePlugin;
import com.arf.core.service.FileService;
import com.arf.core.service.PluginService;
import com.arf.core.service.ProductImageService;
import com.arf.core.util.FreeMarkerUtils;
import com.arf.core.util.ImageUtils;
import com.arf.core.util.SettingUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import freemarker.template.TemplateException;

/**
 * Service - 商品图片
 * 
 * @author arf
 * @version 4.0
 */
@Service("productImageServiceImpl")
public class ProductImageServiceImpl implements ProductImageService, ServletContextAware {

	/** 目标扩展名 */
	private static final String DEST_EXTENSION = "jpg";
	/** 目标文件类型 */
	private static final String DEST_CONTENT_TYPE = "image/jpeg";

	/** servletContext */
	private ServletContext servletContext;

	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "pluginServiceImpl")
	private PluginService pluginService;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * 添加图片处理任务
	 * 
	 * @param storagePlugin
	 *            存储插件
	 * @param sourcePath
	 *            原图片上传路径
	 * @param largePath
	 *            图片文件(大)上传路径
	 * @param mediumPath
	 *            图片文件(小)上传路径
	 * @param thumbnailPath
	 *            图片文件(缩略)上传路径
	 * @param tempFile
	 *            原临时文件
	 * @param contentType
	 *            原文件类型
	 */
	private void addTask(final StoragePlugin storagePlugin, final String sourcePath, final String largePath, final String mediumPath, final String thumbnailPath, final File tempFile, final String contentType) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				File tempDir = new File(System.getProperty("java.io.tmpdir"));
				tempDir.mkdirs();
				Setting setting = SettingUtils.get();
				File watermarkFile = new File(servletContext.getRealPath(setting.getWatermarkImage()));
				File largeTempFile = new File(tempDir, "/upload_" + UUID.randomUUID() + "." + DEST_EXTENSION);
				File mediumTempFile = new File(tempDir, "/upload_" + UUID.randomUUID() + "." + DEST_EXTENSION);
				File thumbnailTempFile = new File(tempDir, "/upload_" + UUID.randomUUID() + "." + DEST_EXTENSION);
				try {
					ImageUtils.zoom(tempFile, largeTempFile, setting.getLargeProductImageWidth(), setting.getLargeProductImageHeight());
					ImageUtils.addWatermark(largeTempFile, largeTempFile, watermarkFile, setting.getWatermarkPosition(), setting.getWatermarkAlpha());
					ImageUtils.zoom(tempFile, mediumTempFile, setting.getMediumProductImageWidth(), setting.getMediumProductImageHeight());
					ImageUtils.addWatermark(mediumTempFile, mediumTempFile, watermarkFile, setting.getWatermarkPosition(), setting.getWatermarkAlpha());
					ImageUtils.zoom(tempFile, thumbnailTempFile, setting.getThumbnailProductImageWidth(), setting.getThumbnailProductImageHeight());
					storagePlugin.upload(sourcePath, tempFile, contentType);
					storagePlugin.upload(largePath, largeTempFile, DEST_CONTENT_TYPE);
					storagePlugin.upload(mediumPath, mediumTempFile, DEST_CONTENT_TYPE);
					storagePlugin.upload(thumbnailPath, thumbnailTempFile, DEST_CONTENT_TYPE);
				} finally {
					FileUtils.deleteQuietly(tempFile);
					FileUtils.deleteQuietly(largeTempFile);
					FileUtils.deleteQuietly(mediumTempFile);
					FileUtils.deleteQuietly(thumbnailTempFile);
				}
			}
		});
	}

	public void filter(List<ProductImage> productImages) {
		CollectionUtils.filter(productImages, new Predicate() {
			public boolean evaluate(Object object) {
				ProductImage productImage = (ProductImage) object;
				return productImage != null && !productImage.isEmpty() && isValid(productImage);
			}
		});
	}

	public boolean isValid(ProductImage productImage) {
		Assert.notNull(productImage);

		return productImage.getFile() == null || productImage.getFile().isEmpty() || fileService.isValid(FileType.image, productImage.getFile());
	}

	public void build(ProductImage productImage) {
		if (productImage == null || productImage.getFile() == null || productImage.getFile().isEmpty()) {
			return;
		}

		try {
			Setting setting = SettingUtils.get();
			MultipartFile multipartFile = productImage.getFile();
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("uuid", UUID.randomUUID().toString());
			String uploadPath = FreeMarkerUtils.process(setting.getImageUploadPath(), model);
			String uuid = UUID.randomUUID().toString();
			String sourcePath = uploadPath + uuid + "-source." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			String largePath = uploadPath + uuid + "-large." + DEST_EXTENSION;
			String mediumPath = uploadPath + uuid + "-medium." + DEST_EXTENSION;
			String thumbnailPath = uploadPath + uuid + "-thumbnail." + DEST_EXTENSION;
			File tempDir = new File(System.getProperty("java.io.tmpdir"));
			tempDir.mkdirs();
			for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
				File tempFile = new File(tempDir, "/upload_" + UUID.randomUUID() + ".tmp");
				multipartFile.transferTo(tempFile);
				addTask(storagePlugin, sourcePath, largePath, mediumPath, thumbnailPath, tempFile, multipartFile.getContentType());
				productImage.setSource(storagePlugin.getUrl(sourcePath));
				productImage.setLarge(storagePlugin.getUrl(largePath));
				productImage.setMedium(storagePlugin.getUrl(mediumPath));
				productImage.setThumbnail(storagePlugin.getUrl(thumbnailPath));
				break;
			}
		} catch (IllegalStateException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void build(List<ProductImage> productImages) {
		if (CollectionUtils.isEmpty(productImages)) {
			return;
		}
		for (ProductImage productImage : productImages) {
			build(productImage);
		}
	}

}