package com.arf.core.util;

import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class ShortenUrl {
	private static final String fmt = "http://api.t.sina.com.cn/short_url/shorten.json?source=%s&url_long=%s";
	private static final String SINA_APP_KEY = "2355740418";
	private static final Logger logger = LoggerFactory.getLogger(ShortenUrl.class);
	/**
	 * 长链接地址转换短链地址
	 * @param unUrlEncodeUrl 没有经过URLEncode的长链接
	 * @return
	 */
	public static String shortenUrl(String unUrlEncodeUrl,int ...invokeCount) {
		int count = 0;
		if(invokeCount != null && invokeCount.length > 0){
			count = invokeCount[0];
		}
		System.out.println(count);
		if(count >= 10){
			return unUrlEncodeUrl;
		}
		String fmtUrl = "";
		try {
			fmtUrl = URLEncoder.encode(unUrlEncodeUrl,"utf-8");
		} catch (Exception e) {
			logger.error("生成短链接出现错误",e);
		}
		fmtUrl = String.format(fmt, SINA_APP_KEY,fmtUrl);
		logger.info("生成短链接"+fmtUrl);
		String resp = HttpUtil.sendHttpGET(fmtUrl);
		if(StringUtils.isBlank(resp)){
			logger.error("生成短链接出现错误,resp为null");
			return shortenUrl(unUrlEncodeUrl,++count);
		}
		JSONArray list = JSON.parseArray(resp);
		if(list == null || list.size() == 0){
			logger.error("生成短链接出现错误,resp为null");
			return shortenUrl(unUrlEncodeUrl,++count);
		}
		String shorten = list.getJSONObject(0).getString("url_short");
		if(shorten == null){
			return shortenUrl(unUrlEncodeUrl,++count);
		}else{
			return shorten;
		}
	}
}