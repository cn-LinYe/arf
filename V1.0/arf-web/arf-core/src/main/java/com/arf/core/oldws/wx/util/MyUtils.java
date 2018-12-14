package com.arf.core.oldws.wx.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class MyUtils {
	
	private static MyUtils util=null;
	private MyUtils() {
	};
	public synchronized static MyUtils getInstance() {
		if (null == util) {
			synchronized (MyUtils.class) {
				util = new MyUtils();
			}
		}
		return util;
	}
	
	//给服务器发送请求数据
	public static String doPost(String url,String datas) {
		String result = "";
		HttpURLConnection conn;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/json;charset=utf-8");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					conn.getOutputStream(), "utf-8"));
			writer.write(datas);
			writer.flush();
			writer.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String temp = null;
			StringBuilder stringBuilder = new StringBuilder();
			while (null != (temp = reader.readLine())) {
				stringBuilder.append(temp);
				temp = null;
			}
			result = stringBuilder.toString();
			reader.close();
			System.out.println("--->result: "+result);
			return result;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 接收参数表单类型
	 */
	public static String doPost(String url,List<NameValuePair> params) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String result = null;
		try {
			HttpEntity httpentity = new UrlEncodedFormEntity(params, "UTF-8");
			httppost.setEntity(httpentity);
			HttpResponse respon = httpclient.execute(httppost);
			if (respon.getStatusLine().getStatusCode() == 200) {
				HttpEntity resEntity = respon.getEntity();
				result = EntityUtils.toString(resEntity);
			}
			httpclient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("--->result: "+result);
		return result;
	}
	/**
	 * 接收参数json列表
	 */
	public static String doJsonPost(String url,String params) {
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String result = null;
		try {
            StringEntity entity = new StringEntity(params,"utf-8");//解决中文乱码问题    
            entity.setContentEncoding("UTF-8");    
            entity.setContentType("application/json");    
            httppost.setEntity(entity); 
            
			HttpResponse respon = httpclient.execute(httppost);
			if (respon.getStatusLine().getStatusCode() == 200) {
				HttpEntity resEntity = respon.getEntity();
				result = EntityUtils.toString(resEntity);
			}
			httpclient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("--->result: "+result);
		return result;
	}
	
	public static List<NameValuePair> getReqParams(Map<String,String> map){
		List<NameValuePair> par = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			// 添加要传递的参数
			par.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return par;
	}
}
