/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service;

import com.arf.core.License;

/**
 * Service - 许可信息
 * 
 * @author arf
 * @version 4.0
 */
public interface LicenseService {

	/**
	 * 获取许可信息
	 * 
	 * @param version
	 *            版本
	 * @param sn
	 *            序列号
	 * @return 许可信息
	 */
	License get(String version, String sn);

}