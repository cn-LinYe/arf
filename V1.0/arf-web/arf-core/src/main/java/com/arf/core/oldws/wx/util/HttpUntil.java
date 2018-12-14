package com.arf.core.oldws.wx.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * http访问简单工具	
 * @author no
 * @data 2015年11月5日
 */
public class HttpUntil {

	public static String REQUEST_METHOD_GET = "GET";
	public static String REQUEST_METHOD_POST = "POST";
	public static String CONTENT_TYPE_JSON = "application/json";
	
	
	public static String HttpGetRequset(String url,String param){
		return baseHttpRequset(url, REQUEST_METHOD_GET, param, CONTENT_TYPE_JSON);
	}
	
	public static String HttpPostRequset(String url,String param){
		return baseHttpRequset(url, REQUEST_METHOD_POST, param, CONTENT_TYPE_JSON);
	}
	
	
	public static String httpRequsetPost(String url,String param){
		HttpURLConnection conn;
		String result = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestProperty("test_id", "123456");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod(REQUEST_METHOD_POST);
			conn.setRequestProperty("Content-Type", CONTENT_TYPE_JSON);
			if(param != null){
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
			writer.write(param);
			writer.flush();
			writer.close();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			StringBuilder builder = new StringBuilder();
			String temp = null;
			while(null != (temp = reader.readLine())){
				builder.append(temp);
				temp = null;
			}
			reader.close();
			reader = null;
			
		    result = builder.toString();
			builder = null;
			temp = null;
						
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	public static String baseHttpRequset(String url,String method,String param,String contentType){
		HttpURLConnection conn;
		String result = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod(method);
			conn.setRequestProperty("Content-Type", contentType);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
			writer.write(param);
			writer.flush();
			writer.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			StringBuilder builder = new StringBuilder();
			String temp = null;
			while(null != (temp = reader.readLine())){
				builder.append(temp);
				temp = null;
			}
			reader.close();
			reader = null;
		    result = builder.toString();
			builder = null;
			temp = null;
						
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
}
