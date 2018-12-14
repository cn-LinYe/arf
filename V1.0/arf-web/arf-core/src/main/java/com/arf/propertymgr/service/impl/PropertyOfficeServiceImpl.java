package com.arf.propertymgr.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.propertymgr.dao.IPropertyOfficeDao;
import com.arf.propertymgr.entity.PropertyOffice;
import com.arf.propertymgr.service.IPropertyOfficeService;

@Service("propertyOfficeServiceImpl")
@Lazy(false)
public class PropertyOfficeServiceImpl extends BaseServiceImpl<PropertyOffice, Long> implements IPropertyOfficeService {

	@Resource(name = "propertyOfficeDaoImpl")
	private IPropertyOfficeDao propertyOfficeDaoImpl;

	@Override
	protected BaseDao<PropertyOffice, Long> getBaseDao() {
		
		return propertyOfficeDaoImpl;
	}
	
	

}
