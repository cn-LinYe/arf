/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import com.arf.core.FileInfo;
import com.arf.core.FileInfo.FileType;
import com.arf.core.FileInfo.OrderType;
import com.arf.core.Setting;
import com.arf.core.entity.Member;
import com.arf.core.plugin.StoragePlugin;
import com.arf.core.service.AdminService;
import com.arf.core.service.FileService;
import com.arf.core.service.MemberService;
import com.arf.core.service.PluginService;
import com.arf.core.util.FreeMarkerUtils;
import com.arf.core.util.SettingUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import freemarker.template.TemplateException;

/**
 * Service - 文件
 * 
 * @author arf
 * @version 4.0
 */
@Service("fileServiceImpl")
public class FileServiceImpl implements FileService, ServletContextAware {

	/** servletContext */
	private ServletContext servletContext;

	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	
	@Resource(name = "pluginServiceImpl")
	private PluginService pluginService;
	
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	
	@Resource(name = "memberServiceImpl")
    private MemberService memberService;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * 添加文件上传任务
	 * 
	 * @param storagePlugin
	 *            存储插件
	 * @param path
	 *            上传路径
	 * @param file
	 *            上传文件
	 * @param contentType
	 *            文件类型
	 */
	private void addUploadTask(final StoragePlugin storagePlugin, final String path, final File file, final String contentType) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				upload(storagePlugin, path, file, contentType);
			}
		});
	}

	/**
	 * 上传文件
	 * 
	 * @param storagePlugin
	 *            存储插件
	 * @param path
	 *            上传路径
	 * @param file
	 *            上传文件
	 * @param contentType
	 *            文件类型
	 */
	private void upload(StoragePlugin storagePlugin, String path, File file, String contentType) {
		Assert.notNull(storagePlugin);
		Assert.hasText(path);
		Assert.notNull(file);
		Assert.hasText(contentType);

		try {
			storagePlugin.upload(path, file, contentType);
		} finally {
			FileUtils.deleteQuietly(file);
		}
	}

	public boolean isValid(FileType fileType, MultipartFile multipartFile) {
		Assert.notNull(fileType);
		Assert.notNull(multipartFile);
		Assert.state(!multipartFile.isEmpty());

		Setting setting = SettingUtils.get();
		if (setting.getUploadMaxSize() != null && setting.getUploadMaxSize() != 0 && multipartFile.getSize() > setting.getUploadMaxSize() * 1024L * 1024L) {
			return false;
		}
		String[] uploadExtensions;
		switch (fileType) {
		case flash:
			uploadExtensions = setting.getUploadFlashExtensions();
			break;
		case media:
			uploadExtensions = setting.getUploadMediaExtensions();
			break;
		case file:
			uploadExtensions = setting.getUploadFileExtensions();
			break;
		default:
			uploadExtensions = setting.getUploadImageExtensions();
			break;
		}
		if (ArrayUtils.isNotEmpty(uploadExtensions)) {
			return FilenameUtils.isExtension(multipartFile.getOriginalFilename(), uploadExtensions);
		}
		return false;
	}

	public String upload(FileType fileType, MultipartFile multipartFile, boolean async) {
		Assert.notNull(fileType);
		Assert.notNull(multipartFile);
		Assert.state(!multipartFile.isEmpty());

		Setting setting = SettingUtils.get();
		String uploadPath;
		switch (fileType) {
		case flash:
			uploadPath = setting.getFlashUploadPath();
			break;
		case media:
			uploadPath = setting.getMediaUploadPath();
			break;
		case file:
			uploadPath = setting.getFileUploadPath();
			break;
		default:
			uploadPath = setting.getImageUploadPath();
			break;
		}
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("uuid", UUID.randomUUID().toString());
			//设置上传用户名
			uploadPath=uploadPath.replaceAll("user", adminService.getCurrentUsername());
			String path = FreeMarkerUtils.process(uploadPath, model);
			String destPath = path + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			File tempDir = new File(System.getProperty("java.io.tmpdir"));
			tempDir.mkdirs();
			for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
				File tempFile = new File(tempDir + "/upload_" + UUID.randomUUID() + ".tmp");
				multipartFile.transferTo(tempFile);
				String contentType = multipartFile.getContentType();
				if (async) {
					addUploadTask(storagePlugin, destPath, tempFile, contentType);
				} else {
					upload(storagePlugin, destPath, tempFile, contentType);
				}
				return storagePlugin.getUrl(destPath);
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return null;
	}
	
	public String uploadInsurance(FileType fileType, MultipartFile multipartFile, boolean async,Member member) {
        Assert.notNull(fileType);
        Assert.notNull(multipartFile);
        Assert.state(!multipartFile.isEmpty());

        Setting setting = SettingUtils.get();
        String uploadPath;
        switch (fileType) {
        case flash:
            uploadPath = setting.getFlashUploadPath();
            break;
        case media:
            uploadPath = setting.getMediaUploadPath();
            break;
        case file:
            uploadPath = setting.getFileUploadPath();
            break;
        default:
            uploadPath = setting.getImageUploadPath();
            break;
        }
        try {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("uuid", UUID.randomUUID().toString());
            //设置上传用户名
            uploadPath=uploadPath.replaceAll("user","insurance/"+member.getUsername());
            String path = FreeMarkerUtils.process(uploadPath, model);
            String destPath = path + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            File tempDir = new File(System.getProperty("java.io.tmpdir"));
            tempDir.mkdirs();
            for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
                File tempFile = new File(tempDir + "/upload_" + UUID.randomUUID() + ".tmp");
                multipartFile.transferTo(tempFile);
                String contentType = multipartFile.getContentType();
                if (async) {
                    addUploadTask(storagePlugin, destPath, tempFile, contentType);
                } else {
                    upload(storagePlugin, destPath, tempFile, contentType);
                }
                return storagePlugin.getUrl(destPath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (TemplateException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return null;
    }

	public String upload(FileType fileType, MultipartFile multipartFile) {
		Assert.notNull(fileType);
		Assert.notNull(multipartFile);
		Assert.state(!multipartFile.isEmpty());

		return upload(fileType, multipartFile, true);
	}

	public String uploadLocal(FileType fileType, MultipartFile multipartFile) {
		Assert.notNull(fileType);
		Assert.notNull(multipartFile);
		Assert.state(!multipartFile.isEmpty());

		Setting setting = SettingUtils.get();
		String uploadPath;
		switch (fileType) {
		case flash:
			uploadPath = setting.getFlashUploadPath();
			break;
		case media:
			uploadPath = setting.getMediaUploadPath();
			break;
		case file:
			uploadPath = setting.getFileUploadPath();
			break;
		default:
			uploadPath = setting.getImageUploadPath();
			break;
		}
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("uuid", UUID.randomUUID().toString());
			String path = FreeMarkerUtils.process(uploadPath, model);
			String destPath = path + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			File destFile = new File(servletContext.getRealPath(destPath));
			new File(path).mkdirs();
			multipartFile.transferTo(destFile);
			return destPath;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public List<FileInfo> browser(String path, FileType fileType, OrderType orderType) {
		if (path != null) {
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			if (!path.endsWith("/")) {
				path += "/";
			}
		} else {
			path = "/";
		}
		Setting setting = SettingUtils.get();
		String uploadPath;
		switch (fileType) {
		case flash:
			uploadPath = setting.getFlashUploadPath();
			break;
		case media:
			uploadPath = setting.getMediaUploadPath();
			break;
		case file:
			uploadPath = setting.getFileUploadPath();
			break;
		default:
			uploadPath = setting.getImageUploadPath();
			break;
		}
		//每个用户创建一条数据
		String browsePath = StringUtils.substringBeforeLast(uploadPath, "${").replaceAll("user", adminService.getCurrentUsername());

		
		browsePath = StringUtils.substringBeforeLast(browsePath, "/") + path;
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		if (browsePath.indexOf("..") >= 0) {
			return fileInfos;
		}
		for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
			fileInfos = storagePlugin.browser(browsePath);
			break;
		}
		switch (orderType) {
		case size:
			Collections.sort(fileInfos, new SizeComparator());
			break;
		case type:
			Collections.sort(fileInfos, new TypeComparator());
			break;
		default:
			Collections.sort(fileInfos, new NameComparator());
			break;
		}
		return fileInfos;
	}

	private class NameComparator implements Comparator<FileInfo> {
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory()).append(fileInfos1.getName(), fileInfos2.getName()).toComparison();
		}
	}

	private class SizeComparator implements Comparator<FileInfo> {
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory()).append(fileInfos1.getSize(), fileInfos2.getSize()).toComparison();
		}
	}

	private class TypeComparator implements Comparator<FileInfo> {
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory()).append(FilenameUtils.getExtension(fileInfos1.getName()), FilenameUtils.getExtension(fileInfos2.getName())).toComparison();
		}
	}

}