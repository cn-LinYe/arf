/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.Principal;
import com.arf.core.dao.AdminDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Admin.Type;
import com.arf.core.entity.Role;
import com.arf.core.service.AdminService;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 管理员
 * 
 * @author arf
 * @version 4.0
 */
@Service("adminServiceImpl")
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long> implements AdminService {

	@Value("${system.sn}")
	private String systemSn;

	@Resource(name = "adminDaoImpl")
	private AdminDao adminDao;

	@Override
	protected BaseDao<Admin, Long> getBaseDao() {
		return adminDao;
	}

	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		return adminDao.usernameExists(username);
	}

	@Transactional(readOnly = true)
	public Admin findByUsername(String username) {
		return adminDao.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public List<String> findAuthorities(Long id) {
		List<String> authorities = new ArrayList<String>();
		Admin admin = adminDao.find(id);
		if (admin != null && admin.getRoles() != null) {
			for (Role role : admin.getRoles()) {
				if (role != null && role.getAuthorities() != null) {
					authorities.addAll(role.getAuthorities());
				}
			}
		}
		return authorities;
	}

	@Transactional(readOnly = true)
	public boolean isAuthenticated() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			return subject.isAuthenticated();
		}
		return false;
	}

	@Transactional(readOnly = true)
	public Admin getCurrent() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return adminDao.find(principal.getId());
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public String getCurrentUsername() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return principal.getUsername();
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public String getLoginToken() {
		return DigestUtils.sha256Hex(systemSn);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Admin save(Admin admin) {
		return super.save(admin);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Admin update(Admin admin) {
		return super.update(admin);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Admin update(Admin admin, String... ignoreProperties) {
		return super.update(admin, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Admin admin) {
		super.delete(admin);
	}

	
	@Transactional(readOnly = true)
	public Page<Admin> findPage(Type type, Pageable pageable) {
		
		return adminDao.findPage(type, pageable);
	}
		
	@Transactional(readOnly = true)
	public List<Admin> findByType(Type type) {
		
		return adminDao.findByType(type);
	}

	@Override
	public Admin selectAdmin(String userName, String documentCode) {
		return adminDao.selectAdmin(userName, documentCode);
	}

}