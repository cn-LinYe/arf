/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.template.method;

import java.util.List;
import java.util.regex.Pattern;

import com.arf.core.util.FreeMarkerUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * 模板方法 - 字符串缩略
 * 
 * @author arf
 * @version 4.0
 */
@Component("abbreviateMethod")
public class AbbreviateMethod implements TemplateMethodModelEx {

	/** 中文字符配比 */
	private static final Pattern PATTERN = Pattern.compile("[\\u4e00-\\u9fa5\\ufe30-\\uffa0]");

	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		String str = FreeMarkerUtils.getArgument(0, String.class, arguments);
		Integer width = FreeMarkerUtils.getArgument(1, Integer.class, arguments);
		String ellipsis = FreeMarkerUtils.getArgument(2, String.class, arguments);
		if (StringUtils.isEmpty(str) || width == null) {
			return str;
		}
		int i = 0;
		for (int strWidth = 0; i < str.length(); i++) {
			strWidth = PATTERN.matcher(String.valueOf(str.charAt(i))).find() ? strWidth + 2 : strWidth + 1;
			if (strWidth >= width) {
				break;
			}
		}
		return ellipsis != null && i < str.length() - 1 ? str.substring(0, i) + ellipsis : str.substring(0, i);
	}

}