/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Editor - HTML清理
 * 
 * @author arf
 * @version 4.0
 */
public class HtmlCleanEditor extends PropertyEditorSupport {

	/** 默认白名单 */
	public static final Whitelist DEFAULT_WHITELIST = Whitelist.none();

	/** 宽松白名单 */
	public static final Whitelist RELAXED_WHITELIST = new Whitelist()
			.addTags("a", "b", "s", "blockquote", "br", "hr", "caption", "cite", "code", "col", "colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6", "i", "img", "li", "ol", "p", "pre", "q", "small", "span", "strike", "strong", "sub", "sup", "table", "tbody", "td", "tfoot",
					"th", "thead", "tr", "u", "ul").addAttributes("a", "href", "title").addAttributes("blockquote", "cite").addAttributes("col", "span", "width").addAttributes("colgroup", "span", "width").addAttributes("img", "align", "alt", "height", "src", "title", "width", "border")
			.addAttributes("ol", "start", "type").addAttributes("q", "cite").addAttributes("table", "summary", "width", "cellpadding", "cellspacing", "border", "bordercolor").addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width")
			.addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope", "width").addAttributes("ul", "type").addAttributes(":all", "style").addProtocols("a", "href", "ftp", "http", "https", "mailto").addProtocols("blockquote", "cite", "http", "https")
			.addProtocols("cite", "cite", "http", "https").addProtocols("img", "src", "http", "https").addProtocols("q", "cite", "http", "https");

	/** 是否移除两端空白 */
	private boolean trim;

	/** 是否将空转换为null */
	private boolean emptyAsNull;

	/** 白名单 */
	private Whitelist whitelist = DEFAULT_WHITELIST;

	/**
	 * @param trim
	 *            是否移除两端空白
	 * @param emptyAsNull
	 *            是否将空转换为null
	 */
	public HtmlCleanEditor(boolean trim, boolean emptyAsNull) {
		this.trim = trim;
		this.emptyAsNull = emptyAsNull;
	}

	/**
	 * @param trim
	 *            是否移除两端空白
	 * @param emptyAsNull
	 *            是否将空转换为null
	 * @param whitelist
	 *            白名单
	 */
	public HtmlCleanEditor(boolean trim, boolean emptyAsNull, Whitelist whitelist) {
		this.trim = trim;
		this.emptyAsNull = emptyAsNull;
		this.whitelist = whitelist;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	@Override
	public String getAsText() {
		Object value = getValue();
		return value != null ? value.toString() : StringUtils.EMPTY;
	}

	/**
	 * 设置内容
	 * 
	 * @param text
	 *            内容
	 */
	@Override
	public void setAsText(String text) {
		if (text != null) {
			String value = Jsoup.clean(text, whitelist);
			value = trim ? value.trim() : value;
			if (emptyAsNull && StringUtils.isEmpty(text)) {
				value = null;
			}
			setValue(value);
		} else {
			setValue(null);
		}
	}

}