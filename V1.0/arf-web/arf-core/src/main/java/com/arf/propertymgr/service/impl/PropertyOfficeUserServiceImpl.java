package com.arf.propertymgr.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.arf.access.entity.AccessManagement;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.propertymgr.dao.IPropertyOfficeDao;
import com.arf.propertymgr.dao.IPropertyOfficeUserDao;
import com.arf.propertymgr.entity.PropertyOffice;
import com.arf.propertymgr.entity.PropertyOfficeUser;
import com.arf.propertymgr.service.IPropertyOfficeService;
import com.arf.propertymgr.service.IPropertyOfficeUserService;

@Service("propertyOfficeUserServiceImpl")
@Lazy(false)
public class PropertyOfficeUserServiceImpl extends BaseServiceImpl<PropertyOfficeUser, Long> implements IPropertyOfficeUserService {

	@Resource(name = "propertyOfficeUserDaoImpl")
	private IPropertyOfficeUserDao propertyOfficeUserDaoImpl;

	@Override
	protected BaseDao<PropertyOfficeUser, Long> getBaseDao() {
		
		return propertyOfficeUserDaoImpl;
	}

	@Override
	public List<PropertyOfficeUser> findByCommunityNumber(String communityNumber) {
		return propertyOfficeUserDaoImpl.findByCommunityNumber(communityNumber);
	}
	@Override
	public List<PropertyOfficeUser> findByUserName(String userName) {
		return propertyOfficeUserDaoImpl.findByUserName(userName);
	}
	
	@Override
	public List<PropertyOfficeUser> findByCommunitys(List<String> communityNumbers) {		
		return propertyOfficeUserDaoImpl.findByCommunitys(communityNumbers);
	}

}
