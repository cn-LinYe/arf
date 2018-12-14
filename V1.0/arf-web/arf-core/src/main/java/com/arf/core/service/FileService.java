/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service;

import java.util.List;

import com.arf.core.FileInfo;
import com.arf.core.FileInfo.FileType;
import com.arf.core.FileInfo.OrderType;
import com.arf.core.entity.Member;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service - 文件
 * 
 * @author arf
 * @version 4.0
 */
public interface FileService {

	/**
	 * 文件验证
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @return 文件验证是否通过
	 */
	boolean isValid(FileType fileType, MultipartFile multipartFile);

	/**
	 * 文件上传
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @param async
	 *            是否异步
	 * @return 访问URL
	 */
	String upload(FileType fileType, MultipartFile multipartFile, boolean async);
	
	String uploadInsurance(FileType fileType, MultipartFile multipartFile, boolean async,Member member);

	/**
	 * 文件上传(异步)
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @return 访问URL
	 */
	String upload(FileType fileType, MultipartFile multipartFile);

	/**
	 * 文件上传至本地(同步)
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @return 路径
	 */
	String uploadLocal(FileType fileType, MultipartFile multipartFile);

	/**
	 * 文件浏览
	 * 
	 * @param path
	 *            浏览路径
	 * @param fileType
	 *            文件类型
	 * @param orderType
	 *            排序类型
	 * @return 文件信息
	 */
	List<FileInfo> browser(String path, FileType fileType, OrderType orderType);

}