package com.arf.traffic;

import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arf.traffic.dto.ResponseDto;

public class MyCallable implements Callable<ResponseDto>{

	private static Logger log = LoggerFactory.getLogger(MyCallable.class);
	
	private String url;	
	private String params;
	private Map<String, Object> header;
	private String charset;
	
	public MyCallable(String url,String params,Map<String, Object> header,String charset){
		this.url=url;
		this.params=params;	
		this.header=header;
		this.charset=charset;
	}	
	
	@Override
	public ResponseDto call() throws Exception {
		try {
			log.error("请求协议地址"+url+"参数如下"+params+"header:"+header);
			ResponseDto dto=null;
			if(url.startsWith("https://")){
				dto=HttpClientUtil.doSPost(url,params,header,charset);
			}else{				
				dto=HttpClientUtil.doPost(url,params,header,charset);
			}
			return dto;
		} catch (Exception e) {
			log.error("请求协议地址"+url+"参数如下"+params,e);
		}
		return null;
	}

}
