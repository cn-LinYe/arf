/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * Editor - 日期
 * 
 * @author arf
 * @version 4.0
 */
public class DateEditor extends PropertyEditorSupport {

	/** 默认日期格式 */
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** 是否将空转换为null */
	private boolean emptyAsNull;

	/** 日期格式 */
	private String dateFormat = DEFAULT_DATE_FORMAT;

	/**
	 * @param emptyAsNull
	 *            是否将空转换为null
	 */
	public DateEditor(boolean emptyAsNull) {
		this.emptyAsNull = emptyAsNull;
	}

	/**
	 * @param emptyAsNull
	 *            是否将空转换为null
	 * @param dateFormat
	 *            日期格式
	 */
	public DateEditor(boolean emptyAsNull, String dateFormat) {
		this.emptyAsNull = emptyAsNull;
		this.dateFormat = dateFormat;
	}

	/**
	 * 获取日期
	 * 
	 * @return 日期
	 */
	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		return value != null ? DateFormatUtils.format(value, dateFormat) : StringUtils.EMPTY;
	}

	/**
	 * 设置日期
	 * 
	 * @param text
	 *            字符串
	 */
	@Override
	public void setAsText(String text) {
		if (text != null) {
			String value = text.trim();
			if (emptyAsNull && StringUtils.isEmpty(text)) {
				setValue(null);
			} else {
				try {
					setValue(DateUtils.parseDate(value, CommonAttributes.DATE_PATTERNS));
				} catch (ParseException e) {
					setValue(null);
				}
			}
		} else {
			setValue(null);
		}
	}

}