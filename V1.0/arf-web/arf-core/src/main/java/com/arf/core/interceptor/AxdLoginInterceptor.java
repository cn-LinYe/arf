package com.arf.core.interceptor;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.arf.base.AppBaseInfo;
import com.arf.base.AppLog;
import com.arf.carbright.dto.BusinessCachedDto;
import com.arf.core.AppMessage;
import com.arf.core.AppMessageCode;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dto.MemberCachedDto;
import com.arf.core.entity.LicensePlateModel;
import com.arf.core.service.LicensePlateModelService;

public class AxdLoginInterceptor extends HandlerInterceptorAdapter{
	
	private final Logger log = LoggerFactory.getLogger(AxdLoginInterceptor.class); 
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "licensePlateModelServiceImpl")
	private LicensePlateModelService licensePlateModelService;
	
	//登陆TOKEN缓存时效7 * 24小时
	public static final long LOGIN_TOKEN_EXPIRE =  30 * 24 * 60 * 60L;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		AppBaseInfo appBaseInfo = new AppBaseInfo(request);
		String businessToken = request.getParameter("businessToken");
		String targetAction = request.getServletPath();
		if(StringUtils.isNotBlank(businessToken)){ //商户版
			/*PBusiness pbusiness = redisService.get(PBusiness.Prefix_Cache_Key_Business_Token + businessToken,PBusiness.class);
			if(pbusiness == null){
				AppLog appLog = new AppLog(appBaseInfo, request.getParameter("userName"), AppLog.LogType.APPLOG_TOKEN_FAILED, "安心点商户版token验证失效("+targetAction+")");
				licensePlateModelService.saveAppLogs(appLog);
				writeNoLoginedMsg(response,"您的登录已经失效,请重新登录");
				return false;
			}else{
				redisService.setExpiration(PBusiness.Prefix_Cache_Key_Business_Token + businessToken, LOGIN_TOKEN_EXPIRE);
			}*/
			
			//增加RegistrationId后新的验证方式
			String userName = request.getParameter("userName");
			if(StringUtils.isEmpty(userName)){
				userName = request.getParameter("user_name");
			}
			if(StringUtils.isEmpty(userName)){
				userName = request.getParameter("user_Name");
			}
			if(StringUtils.isEmpty(userName)){
				AppLog appLog = new AppLog(appBaseInfo, request.getParameter("userName"), AppLog.LogType.APPLOG_TOKEN_FAILED, "安心点商户版token验证失效("+targetAction+")");
				licensePlateModelService.saveAppLogs(appLog);
				writeNoLoginedMsg(response,"您的登录已经失效,请重新登录");
				return false;
			}
			BusinessCachedDto pbusiness = redisService.get(BusinessCachedDto.Prefix_Cache_Key_Business_UserName + userName,BusinessCachedDto.class);
			if(pbusiness == null){
				AppLog appLog = new AppLog(appBaseInfo, request.getParameter("userName"), AppLog.LogType.APPLOG_TOKEN_FAILED, "安心点商户版token验证失效("+targetAction+")");
				licensePlateModelService.saveAppLogs(appLog);
				writeNoLoginedMsg(response,"您的登录已经失效,请重新登录");
				return false;
			}else{
				if(!businessToken.equals(pbusiness.getDocumentCode())){
					AppLog appLog = new AppLog(appBaseInfo, request.getParameter("userName"), AppLog.LogType.APPLOG_TOKEN_FAILED, "安心点商户版token验证失效("+targetAction+")");
					licensePlateModelService.saveAppLogs(appLog);
					writeNoLoginedMsg(response,"您的登录已经失效,请重新登录");
					return false;
				}
				redisService.setExpiration(BusinessCachedDto.Prefix_Cache_Key_Business_UserName + userName, LOGIN_TOKEN_EXPIRE);
				if(StringUtils.isNotBlank(pbusiness.getRegistrationId()) 
						&& redisService.exists(BusinessCachedDto.Prefix_Cache_Key_Business_RegistrationId + pbusiness.getRegistrationId())){
					redisService.setExpiration(BusinessCachedDto.Prefix_Cache_Key_Business_RegistrationId + pbusiness.getRegistrationId(), LOGIN_TOKEN_EXPIRE);
				}
			}
		}else{ //用户版
			String userName = request.getParameter("userName");
			if(StringUtils.isEmpty(userName)){
				userName = request.getParameter("user_name");
			}
			if(StringUtils.isEmpty(userName)){
				userName = request.getParameter("user_Name");
			}
			String documentCode = request.getParameter("documentCode");
			if(StringUtils.isEmpty(documentCode)){
				documentCode = request.getParameter("document_code");
			}
			if(StringUtils.isEmpty(documentCode)){
				documentCode = request.getParameter("document_Code");
			}
			if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(documentCode)){
				AppLog appLog = new AppLog(appBaseInfo, request.getParameter("userName"), AppLog.LogType.APPLOG_TOKEN_FAILED, "安心点用户版token验证失效("+targetAction+"),userName或documentCode为空,userName="+userName+"&documentCode="+documentCode);
				licensePlateModelService.saveAppLogs(appLog);
				writeNoLoginedMsg(response,"您的登录已经失效,请重新登录");
				return false;
			}
			MemberCachedDto cachedDto = redisService.get(MemberCachedDto.Prefix_Cache_Key_Member_Username + userName, MemberCachedDto.class);
			if(cachedDto == null){
				AppLog appLog = new AppLog(appBaseInfo, request.getParameter("userName"), AppLog.LogType.APPLOG_TOKEN_FAILED, "安心点用户版token验证失效("+targetAction+"),没有获取到缓存documentCode,userName="+userName+"&documentCode="+documentCode);
				licensePlateModelService.saveAppLogs(appLog);
				writeNoLoginedMsg(response,"您的登录已经失效,请重新登录");
				return false;
			}
			if(!documentCode.equals(cachedDto.getDocumentCode())){ //被别人挤掉线
				String realDeviceName = cachedDto.getDeviceName();
				AppLog appLog = new AppLog(appBaseInfo, request.getParameter("userName"), AppLog.LogType.APPLOG_TOKEN_FAILED, "安心点用户版token验证失效("+targetAction+"),请求和缓存documentCode不一致,userName="+userName+"&documentCode="+documentCode+"&cached documentCode="+cachedDto.getDocumentCode());
				licensePlateModelService.saveAppLogs(appLog);
				writeNoLoginedMsg(response,"系统检测到您的安心点账号在" + (StringUtils.isBlank(realDeviceName)?"新的":realDeviceName) + "设备上登录成功。如果不是您的操作，账号可能泄露，请及时更改密码。");
				//清除com.arf.core.dto.MemberCachedDto.Prefix_Cache_Key_Member_RegistrationId缓存
				if(StringUtils.isNotBlank(cachedDto.getRegistrationId())){
					redisService.del(MemberCachedDto.Prefix_Cache_Key_Member_RegistrationId + cachedDto.getRegistrationId());
				}
				return false;
			}
//			Date since = cachedDto.getCachedSince();
//			if(since != null){
//				if((since.getTime() + LOGIN_TOKEN_EXPIRE * 1000L - 10 * 60 * 1000L) < new Date().getTime()){
//					cachedDto.setCachedSince(new Date());
//					cachedDto.setRegistrationId(request.getParameter("registrationId"));
//					redisService.set(MemberCachedDto.Prefix_Cache_Key_Member_Username + userName,cachedDto, LOGIN_TOKEN_EXPIRE);
//				}else if(StringUtils.isBlank(cachedDto.getRegistrationId())){
//					cachedDto.setCachedSince(new Date());
//					cachedDto.setRegistrationId(request.getParameter("registrationId"));
//					redisService.set(MemberCachedDto.Prefix_Cache_Key_Member_Username + userName,cachedDto, LOGIN_TOKEN_EXPIRE);
//				}
//			}else{
//				cachedDto.setCachedSince(new Date());
//				cachedDto.setRegistrationId(request.getParameter("registrationId"));
//				redisService.set(MemberCachedDto.Prefix_Cache_Key_Member_Username + userName,cachedDto, LOGIN_TOKEN_EXPIRE);
//			}
			
			redisService.setExpiration(MemberCachedDto.Prefix_Cache_Key_Member_Username + userName, AxdLoginInterceptor.LOGIN_TOKEN_EXPIRE);
			if(StringUtils.isNotBlank(cachedDto.getRegistrationId()) 
					&& redisService.exists(MemberCachedDto.Prefix_Cache_Key_Member_RegistrationId + cachedDto.getRegistrationId())){
				redisService.setExpiration(MemberCachedDto.Prefix_Cache_Key_Member_RegistrationId + cachedDto.getRegistrationId(), AxdLoginInterceptor.LOGIN_TOKEN_EXPIRE);
			}
			
			//此处做自动解锁操作
			/**
			 * 根据用户名去application域中获取用户的上次请求时间
			 * 如果用户域数据为Null,或者距离上次请求时间大于1小时，更新域中用户的上次请求时间
			 * 查询用户是否有被自动锁住的车牌，发送解锁指令
			 */
			ServletContext application = request.getSession().getServletContext();
			Long lasttime = (Long)application.getAttribute(userName);
			if(lasttime == null){
				lasttime = new Long(0);
			}
			long nowtime = System.currentTimeMillis();
			
			if(nowtime-lasttime.longValue()>3600000){
			    List<LicensePlateModel> lplist=licensePlateModelService.CheckUser_name(userName);
	            if(lplist!=null&&!lplist.isEmpty()){
	                for(int i=0;i<lplist.size();i++){
	                	try{
	                		LicensePlateModel lpm1=lplist.get(i);
		                    lpm1.setOverErrorCount(lpm1.getAllowErrorCount());
		                    lpm1.setLastActiveTime(new Date());
		                    licensePlateModelService.update(lpm1);
	                    }catch (ObjectOptimisticLockingFailureException e) {
	                    	log.error("document login更新LicensePlateModel出现乐观锁异常");
						}catch (Exception e) {
							log.error("document login时更新LicensePlateModel 异常",e);
						}
	                }
	            }
	            application.setAttribute(userName,nowtime);
			}
			
		}
		return true;
	}
	
	private void writeNoLoginedMsg(HttpServletResponse response,String msg){
		AppMessage message = new AppMessage();
		try {
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("content-type", "application/json;charset=UTF-8");  
			response.setCharacterEncoding("UTF-8"); 
			message.setCode(AppMessageCode.CODE_VALFAIL);
			message.setMsg(msg);
			response.getWriter().write(JSON.toJSONString(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
