/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import com.arf.core.Setting.AccountLockType;
import com.arf.core.Setting.CaptchaType;
import com.arf.core.entity.Admin;
import com.arf.core.service.AdminService;
import com.arf.core.service.CaptchaService;
import com.arf.core.util.MD5Util;
import com.arf.core.util.SettingUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 权限认证
 * 
 * @author arf
 * @version 4.0
 */
public class AuthenticationRealm extends AuthorizingRealm {

	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;

	/**
	 * 获取认证信息
	 * 
	 * @param token
	 *            令牌
	 * @return 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token) {
		AuthenticationToken authenticationToken = (AuthenticationToken) token;
		
		ServletRequest request = authenticationToken.getRequest();
        String arfadminAuth = request.getParameter("arfadminAuth");//安尔发云平台鉴权登录, 则无需判断密码
        if(StringUtils.isNotBlank(arfadminAuth)){
        	return this.authArfadminLogin(authenticationToken);
        }
		
		String username = authenticationToken.getUsername();
		String password = new String(authenticationToken.getPassword());
		String captchaId = authenticationToken.getCaptchaId();
		String captcha = authenticationToken.getCaptcha();
		String ip = authenticationToken.getHost();
        
		if (!captchaService.isValid(CaptchaType.adminLogin, captchaId, captcha)) {
			throw new UnsupportedTokenException();
		}
		if (username != null && password != null) {
			Admin admin = adminService.findByUsername(username);
			if (admin == null) {
				throw new UnknownAccountException();
			}
			if (!admin.getIsEnabled()) {
				throw new DisabledAccountException();
			}
			Setting setting = SettingUtils.get();
			if (admin.getIsLocked()) {
				if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.admin)) {
					int loginFailureLockTime = setting.getAccountLockTime();
					if (loginFailureLockTime == 0) {
						throw new LockedAccountException();
					}
					Date lockedDate = admin.getLockedDate();
					Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
					if (new Date().after(unlockDate)) {
						admin.setLoginFailureCount(0);
						admin.setIsLocked(false);
						admin.setLockedDate(null);
						adminService.update(admin);
					} else {
						throw new LockedAccountException();
					}
				} else {
					admin.setLoginFailureCount(0);
					admin.setIsLocked(false);
					admin.setLockedDate(null);
					adminService.update(admin);
				}
			}
//			if(username.length()<11){
				String md5pwd = "";
			    if(username.length() >= 9){
			    	md5pwd = MD5Util.getMD5(username.subSequence(4,9) + MD5Util.getMD5(password));
			    }else{
			    	md5pwd = MD5Util.getMD5(username + MD5Util.getMD5(password));
			    }
	            
	            if (!md5pwd.equals(admin.getPassword())) {
	                int loginFailureCount = admin.getLoginFailureCount() + 1;
	                if (loginFailureCount >= setting.getAccountLockCount()) {
	                    admin.setIsLocked(true);
	                    admin.setLockedDate(new Date());
	                }
	                admin.setLoginFailureCount(loginFailureCount);
	                adminService.update(admin);
	                throw new IncorrectCredentialsException();
	            }
//			}else{
//    			if (!DigestUtils.md5Hex(password).equals(admin.getPassword())) {
//    				int loginFailureCount = admin.getLoginFailureCount() + 1;
//    				if (loginFailureCount >= setting.getAccountLockCount()) {
//    					admin.setIsLocked(true);
//    					admin.setLockedDate(new Date());
//    				}
//    				admin.setLoginFailureCount(loginFailureCount);
//    				adminService.update(admin);
//    				throw new IncorrectCredentialsException();
//    			}
//			}
			admin.setLoginIp(ip);
			admin.setLoginDate(new Date());
			admin.setLoginFailureCount(0);
			adminService.update(admin);
			return new SimpleAuthenticationInfo(new Principal(admin.getId(), username), password, getName());
		}
		throw new UnknownAccountException();
	}

	
	private AuthenticationInfo authArfadminLogin(AuthenticationToken authenticationToken){
		ServletRequest request = authenticationToken.getRequest();
        String arfadminAuth = request.getParameter("arfadminAuth");//安尔发云平台鉴权登录, 则无需判断密码
		String username = arfadminAuth.split("\\s+")[0];
    	String password = new String(authenticationToken.getPassword());
    	String arfadminToken = arfadminAuth.split("\\s+")[1];
    	String md5Token = MD5Util.getMD5(MD5Util.getMD5(username));
    	if(!md5Token.equals(arfadminToken)){
    		throw new UnknownAccountException();
    	}
    	String ip = authenticationToken.getHost();
    	Admin admin = adminService.findByUsername(username);
    	if (admin == null) {
			throw new UnknownAccountException();
		}
		if (!admin.getIsEnabled()) {
			throw new DisabledAccountException();
		}
		Setting setting = SettingUtils.get();
		if (admin.getIsLocked()) {
			if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.admin)) {
				int loginFailureLockTime = setting.getAccountLockTime();
				if (loginFailureLockTime == 0) {
					throw new LockedAccountException();
				}
				Date lockedDate = admin.getLockedDate();
				Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
				if (new Date().after(unlockDate)) {
					admin.setLoginFailureCount(0);
					admin.setIsLocked(false);
					admin.setLockedDate(null);
					adminService.update(admin);
				} else {
					throw new LockedAccountException();
				}
			} else {
				admin.setLoginFailureCount(0);
				admin.setIsLocked(false);
				admin.setLockedDate(null);
				adminService.update(admin);
			}
		}
		admin.setLoginIp(ip);
		admin.setLoginDate(new Date());
		admin.setLoginFailureCount(0);
		adminService.update(admin);
		return new SimpleAuthenticationInfo(new Principal(admin.getId(), username), password, getName());
	}
	
	/**
	 * 获取授权信息
	 * 
	 * @param principals
	 *            principals
	 * @return 授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
		if (principal != null) {
			List<String> authorities = adminService.findAuthorities(principal.getId());
			if (authorities != null) {
				SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
				authorizationInfo.addStringPermissions(authorities);
				return authorizationInfo;
			}
		}
		return null;
	}

}