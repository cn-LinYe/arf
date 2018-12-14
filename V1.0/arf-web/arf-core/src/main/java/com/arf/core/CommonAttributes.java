/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core;

/**
 * 公共参数
 * 
 * @author arf
 * @version 4.0
 */
public final class CommonAttributes {

	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/** arf.xml文件路径 */
	public static final String SHOPXX_XML_PATH = "/arf.xml";

	/** dbconfig.properties文件路径 */
	public static final String SHOPXX_PROPERTIES_PATH = "/dbconfig.properties";

	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
	}

}