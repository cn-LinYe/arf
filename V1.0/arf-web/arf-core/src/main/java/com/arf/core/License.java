/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core;

import java.io.Serializable;
import java.util.Date;

/**
 * 许可信息
 * 
 * @author arf
 * @version 4.0
 */
public class License implements Serializable {

	private static final long serialVersionUID = 3217972553241932912L;

	/** "许可信息"属性名称 */
	public static final String LICENSE_ATTRIBUTE_NAME = License.class.getName() + ".LICENSE";

	/** 授权持有人 */
	private final String name;

	/** 授权域名 */
	private final String domain;

	/** 到期时间 */
	private final Date expire;

	/** 密钥 */
	private final String key;

	public License(String name, String domain, Date expire, String key) {
		this.name = name;
		this.domain = domain;
		this.expire = expire;
		this.key = key;
	}

	/**
	 * 获取授权持有人
	 * 
	 * @return 授权持有人
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取授权域名
	 * 
	 * @return 授权域名
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * 获取到期时间
	 * 
	 * @return 到期时间
	 */
	public Date getExpire() {
		return expire;
	}

	/**
	 * 获取密钥
	 * 
	 * @return 密钥
	 */
	public String getKey() {
		return key;
	}

}