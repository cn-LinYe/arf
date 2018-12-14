package com.arf.core.listener;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.arf.core.entity.MethodTime;
import com.arf.core.service.MethodTimeService;
import com.arf.core.util.SpringUtils;
import com.google.common.collect.Maps;

@WebListener("apiRequestTimeListener")
public class ApiRequestTimeListener implements ServletRequestListener {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	private final int RESP_TIME_LOWER = 5 * 1000;
	
	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		try{
			HttpServletRequest request = (HttpServletRequest) arg0.getServletRequest();
			if(request != null){
				Long startTime = (Long) request.getAttribute("arfRequestStart");
				if(startTime != null){
					Long endTime = System.currentTimeMillis();
					if((endTime - startTime) > RESP_TIME_LOWER){
						String servletPath = request.getServletPath();
						MethodTime methodTime = new MethodTime();
						Enumeration<String> keys = request.getParameterNames();
						Map<String,String> params = Maps.newHashMap();
						while(keys.hasMoreElements()){
							String key = keys.nextElement();
							params.put(key, request.getParameter(key));
						}
						String userName = request.getParameter("userName");
						if(userName == null){
							userName = request.getParameter("user_name");
						}
						if(userName == null){
							userName = request.getParameter("user_Name");
						}
						methodTime.setMethodName(servletPath);
						String args_ = JSON.toJSONString(params);
						if(args_ != null && args_.length() > 4000){
							args_ = args_.substring(0, 3999);
						}
						methodTime.setArguments(args_);
						methodTime.setStartTime(startTime);
						methodTime.setEndTime(endTime);
						methodTime.setTime(endTime - startTime);
						methodTime.setUserName(userName);
						
						MethodTimeService methodTimeService = SpringUtils.getBean("methodTimeServiceImpl", MethodTimeService.class);
						methodTimeService.save(methodTime);
					}
				}
			}
		}catch (Exception e) {
			logger.error("计算接口调用时间出错",e);
		}
	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		ServletRequest request = arg0.getServletRequest();
		if(request != null){
			Long startTime = System.currentTimeMillis();
			request.setAttribute("arfRequestStart", startTime);
		}
	}

}
