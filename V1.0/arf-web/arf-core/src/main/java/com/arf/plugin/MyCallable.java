package com.arf.plugin;

import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arf.core.util.HttpUtils;

public class MyCallable implements Callable<String>{

	public static final Logger log=LoggerFactory.getLogger(MyCallable.class);
	
	public String host;
	public String path;
	public String method; 
	public Map<String, String> headers;
	public Map<String, String> querys;
	
	public MyCallable(String host,String path,String method,Map<String, String> headers,Map<String, String> querys) {
		this.host=host;
		this.path=path;
		this.method=method;
		this.querys=querys;
		this.headers=headers;
	}
	
	@Override
	public String call() throws Exception {
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			log.info("统一接口异常"+host+" 异常信息如下",e);
		}
		return null;	
	}

}
