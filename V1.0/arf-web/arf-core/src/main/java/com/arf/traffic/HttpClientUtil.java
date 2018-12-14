package com.arf.traffic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.arf.core.util.HttpUtils;
import com.arf.traffic.dto.ResponseDto;

public class HttpClientUtil {

	private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

	public static ResponseDto doPost(String url, String params, Map<String, Object> header, String charset)
			throws Exception {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);		
		if (header != null) {// 设置HTTP 请求头数据
			for (String key : header.keySet()) {
				method.setRequestHeader(key, header.get(key) + "");
			}
		}
		// 接收参数json列表
		if (StringUtils.isNotBlank(params)) {
			RequestEntity requestEntity = new StringRequestEntity(params, "application/json", "utf-8");
			method.setRequestEntity(requestEntity);
		}

		charset = (charset == null) ? "UTF-8" : charset;// 设置默认的编码
		try {
			client.executeMethod(method);
			int statusCode = method.getStatusCode();// 获取HTTP请求状态码
			if (statusCode == HttpStatus.SC_OK) {
				InputStream inputStream = method.getResponseBodyAsStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					response.append(line);
				}
				bufferedReader.close();
				inputStreamReader.close();
				// 释放资源
				inputStream.close();
				inputStream = null;
				method.releaseConnection();
			}
			String messgae = response.toString();// 获取
			String statusText = HttpStatus.getStatusText(statusCode);
			JSONObject jsonObject = null;
			if (StringUtils.isNotBlank(messgae))
				jsonObject = JSONObject.parseObject(response.toString());
			return new ResponseDto(statusCode, statusText, jsonObject);
		} catch (Exception e) {
			log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
		}
		return null;
	}

	public static ResponseDto doSPost(String requestUrl, String params, Map<String, Object> header, String charset) {
		try {
			HttpResponse response = HttpUtils.doPost(requestUrl, header, params);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			String messgae = EntityUtils.toString(response.getEntity());
			String statusText = HttpStatus.getStatusText(statusCode);
			JSONObject jsonObject = null;
			if(statusCode == HttpStatus.SC_OK ){
				if (StringUtils.isNotBlank(messgae))
					jsonObject = JSONObject.parseObject(messgae);
			}
			return new ResponseDto(statusCode, statusText, jsonObject);
		} catch (Exception e) {
			log.error("执行HTTP Post请求" + requestUrl + "时，发生异常！", e);
		}
		return null;
	}

}