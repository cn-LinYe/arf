package com.arf.core.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSON;
import com.arf.core.AppMessage;

/**
 * 针对接口请求做频率限制, 调用频繁的接口直接返回响应信息
 * @author arf
 *
 */
@Component("requestMappingsFilter")
public class RequestMappingsFilter extends OncePerRequestFilter {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final int DEFAULT_REQUESTS_PER_MIN = 300;//默认同一ip，每分钟请求频率限制：100次
	
	private Map<String,String> requestMappings = new HashMap<String,String>();
	
	private Map<String,RequestMapping> requestMappingsStatistics = new ConcurrentHashMap<String,RequestMapping>();
	
	{
		try {
			Properties properties = new Properties();
			URL res = RequestMappingsFilter.class.getClassLoader().getResource("requestmappings.properties");
			properties.load(new FileInputStream(new File(res.getPath())));
			if(!properties.isEmpty()){
				Iterator<String> servletUrls = properties.stringPropertyNames().iterator();
				while(servletUrls.hasNext()){
					String servletUrl = servletUrls.next();
					requestMappings.put(servletUrl, properties.getProperty(servletUrl));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try{
			String servletPath = request.getServletPath();
			int requestPreMin = DEFAULT_REQUESTS_PER_MIN;
			if(StringUtils.isNotBlank(requestMappings.get(servletPath))){
				requestPreMin = Integer.valueOf(requestMappings.get(servletPath));
			}
			String ip = request.getHeader("X-Forwarded-For");  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))  
	            ip = request.getHeader("Proxy-Client-IP");  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))  
	            ip = request.getHeader("WL-Proxy-Client-IP");  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))  
	            ip = request.getHeader("HTTP_CLIENT_IP");  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))  
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))  
	             ip = request.getRemoteAddr();  
	        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)){
	        	try {  
	                ip = InetAddress.getLocalHost().getHostAddress();  
	            }  
	            catch (UnknownHostException e) {  
					e.printStackTrace();
	            }  
	        }
	        String field = ip + "_" + servletPath;
	        RequestMapping cachedObj = requestMappingsStatistics.get(field);
	        if(cachedObj == null
	        		|| cachedObj.getRequestTimes() == null
	        		|| cachedObj.getRequestTime() == null
	        		|| cachedObj.getLastRequestTime() == null){
	        	Date now = new Date();
	        	toCacheOperate(field, 1, now, now);
	        	filterChain.doFilter(request, response);
	        	return;
	        }else{
	        	Integer requestTimes = cachedObj.getRequestTimes();
	        	Date requestTime = cachedObj.getRequestTime();
	        	Date lastRequestTime = cachedObj.getLastRequestTime();
	        	Calendar calLast = Calendar.getInstance();
	        	calLast.setTime(requestTime);
	        	Calendar calThis = Calendar.getInstance();
	        	calThis.setTime(new Date());
	        	if(calLast.get(Calendar.YEAR) == calThis.get(Calendar.YEAR)
	        			&& calLast.get(Calendar.MONTH) == calThis.get(Calendar.MONTH)
	        			&& calLast.get(Calendar.DAY_OF_MONTH) == calThis.get(Calendar.DAY_OF_MONTH)
	        			&& calLast.get(Calendar.HOUR_OF_DAY) == calThis.get(Calendar.HOUR_OF_DAY)
	        			&& calLast.get(Calendar.MINUTE) == calThis.get(Calendar.MINUTE)){
	        		if(requestTimes + 1 > requestPreMin){
	        			Date now = new Date();
	        			toCacheOperate(field, requestTimes + 1, now, requestTime);
	        			writeNoLoginedMsg(response,"操作太过频繁！");
	        			return;
	        		}else{
	        			Date now = new Date();
	        			toCacheOperate(field, requestTimes + 1, now, requestTime);
	        			filterChain.doFilter(request, response);
	        			return;
	        		}
	        	}else{//重新计数
	        		Date now = new Date();
	        		toCacheOperate(field, 1, now, requestTime);
	        		filterChain.doFilter(request, response);
	        		return;
	        	}
	        }
		}catch (Exception e) {
			logger.error("请求超频检测接口异常",e);
			filterChain.doFilter(request, response);
		}
        
//      @SuppressWarnings("unchecked")
//		Map<String,Object> cachedObj = redisService.hGet(Request_Mappings_Filter_Key,field,Map.class);
//        if(cachedObj == null
//        		|| cachedObj.get("requestTimes") == null
//        		|| cachedObj.get("requestTime") == null
//        		|| cachedObj.get("lastRequestTime") == null){
//        	Date now = new Date();
//        	toRedisOperate(field, 1, now.getTime(), now.getTime());
//        	filterChain.doFilter(request, response);
//        	return;
//        }else{
//        	Integer requestTimes = (Integer) cachedObj.get("requestTimes");
//        	Long requestTime = (Long) cachedObj.get("requestTime");
//        	Long lastRequestTime = (Long) cachedObj.get("lastRequestTime");
//        	Calendar calLast = Calendar.getInstance();
//        	calLast.setTime(new Date(requestTime));
//        	Calendar calThis = Calendar.getInstance();
//        	calThis.setTime(new Date());
//        	if(calLast.get(Calendar.YEAR) == calThis.get(Calendar.YEAR)
//        			&& calLast.get(Calendar.MONTH) == calThis.get(Calendar.MONTH)
//        			&& calLast.get(Calendar.DAY_OF_MONTH) == calThis.get(Calendar.DAY_OF_MONTH)
//        			&& calLast.get(Calendar.HOUR_OF_DAY) == calThis.get(Calendar.HOUR_OF_DAY)
//        			&& calLast.get(Calendar.MINUTE) == calThis.get(Calendar.MINUTE)){
//        		if(requestTimes + 1 > requestPreMin){
//        			Date now = new Date();
//        			toRedisOperate(field, requestTimes + 1, now.getTime(), requestTime);
//        			writeNoLoginedMsg(response,"操作太过频繁！");
//        			return;
//        		}else{
//        			Date now = new Date();
//        			toRedisOperate(field, requestTimes + 1, now.getTime(), requestTime);
//        			filterChain.doFilter(request, response);
//        			return;
//        		}
//        	}else{//重新计数
//        		Date now = new Date();
//        		toRedisOperate(field, 1, now.getTime(), requestTime);
//        		filterChain.doFilter(request, response);
//        		return;
//        	}
//        }
	}
	
	
//	@Resource(name = "redisService")
//	private RedisService redisService;
//	private final String Request_Mappings_Filter_Key = "Arfweb_Request_Mappings_Filter.Map";
	
//	private void toRedisOperate(String field,int requestTimes,Long requestTime,Long lastRequestTime){
//		Map<String,Object> toCached = new HashMap<String,Object>();
//		toCached.put("requestTimes", requestTimes);
//		toCached.put("requestTime", requestTime);
//		toCached.put("lastRequestTime", lastRequestTime);
//    	redisService.hset(Request_Mappings_Filter_Key,field,JSON.toJSONString(toCached));
//	}
	
	private void toCacheOperate(String field,Integer requestTimes,Date requestTime,Date lastRequestTime){
		requestMappingsStatistics.put(field, new RequestMapping(requestTimes, requestTime, lastRequestTime));
	}
	
	public class RequestMapping{
		private Integer requestTimes;//一分钟内请求次数
		private Date requestTime;//请求时间
		private Date lastRequestTime;//上次请求时间
		public RequestMapping() {
			super();
		}
		public RequestMapping(Integer requestTimes, Date requestTime,
				Date lastRequestTime) {
			super();
			this.requestTimes = requestTimes;
			this.requestTime = requestTime;
			this.lastRequestTime = lastRequestTime;
		}

		public Integer getRequestTimes() {
			return requestTimes;
		}
		public Date getRequestTime() {
			return requestTime;
		}
		public Date getLastRequestTime() {
			return lastRequestTime;
		}
		public void setRequestTimes(Integer requestTimes) {
			this.requestTimes = requestTimes;
		}
		public void setRequestTime(Date requestTime) {
			this.requestTime = requestTime;
		}
		public void setLastRequestTime(Date lastRequestTime) {
			this.lastRequestTime = lastRequestTime;
		}
	}
	
	private void writeNoLoginedMsg(HttpServletResponse response,String msg){
		AppMessage message = new AppMessage();
		try {
			response.setHeader("content-type", "application/json;charset=UTF-8");  
			response.setCharacterEncoding("UTF-8"); 
			message.setCode(40004);
			message.setMsg(msg);
			response.getWriter().write(JSON.toJSONString(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
