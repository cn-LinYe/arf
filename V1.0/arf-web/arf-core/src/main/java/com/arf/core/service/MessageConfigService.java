/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service;

import com.arf.core.entity.MessageConfig;
import com.arf.core.entity.MessageConfig.Type;

/**
 * Service - 消息配置
 * 
 * @author arf
 * @version 4.0
 */
public interface MessageConfigService extends BaseService<MessageConfig, Long> {

	/**
	 * 查找消息配置
	 * 
	 * @param type
	 *            类型
	 * @return 消息配置
	 */
	MessageConfig find(Type type);

}