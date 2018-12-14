package com.arf.core.listener;

import javax.annotation.Resource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.arf.core.entity.MethodTime;
import com.arf.core.service.MethodTimeService;

public class MethodTimeActive implements MethodInterceptor {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static Integer DEFAULT_SLOW_REQUEST = 8; //单位:秒
	
	@Resource(name = "methodTimeServiceImpl")
	private MethodTimeService methodTimeService;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		StopWatch watch = new StopWatch();
		watch.start();
		Object object = invocation.proceed();
		watch.stop();
		
		if(watch.getTime()/1000 >= DEFAULT_SLOW_REQUEST){
			Class[] params = invocation.getMethod().getParameterTypes();
			String[] simpleParams = new String[params.length];
			for(int i=0;i<params.length; i++){
				simpleParams[i] = params[i].getSimpleName();
			}
			Object[] args = invocation.getArguments();
			
			MethodTime methodTime = new MethodTime();
			String methodName = invocation.getThis().getClass().getName()
					+"."+invocation.getMethod().getName()+"("+StringUtils.join(simpleParams,",")+")";
			if(methodName.length()>150){
				methodName = methodName.substring(0, 150);
			}
			methodTime.setMethodName(methodName);
			//String arguments = StringUtils.join(args,",");
			String arguments = JSON.toJSONString(args);
			if(arguments.length()>150){
				arguments = arguments.substring(0, 150);
			}
			methodTime.setArguments(arguments);
			methodTime.setStartTime(watch.getStartTime());
			methodTime.setTime(watch.getTime());
			methodTimeService.save(methodTime);
		}
		return object;
	}

}
