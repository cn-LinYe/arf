package com.arf.carbright.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.carbright.dao.IPCustomerServiceManagementDao;
import com.arf.carbright.entity.PCustomerServiceManagement;
import com.arf.carbright.entity.PCustomerServiceManagement.Department;
import com.arf.carbright.entity.PCustomerServiceManagement.MessageType;
import com.arf.carbright.service.IPCustomerServiceManagementService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("pCustomerServiceManagementServiceImpl")
public class PCustomerServiceManagementServiceImpl extends BaseServiceImpl<PCustomerServiceManagement, Long> implements IPCustomerServiceManagementService{

	@Resource(name="pCustomerServiceManagementDaoImpl")
	public IPCustomerServiceManagementDao pCustomerServiceManagementDaoImpl;
	
	@Override
	protected BaseDao<PCustomerServiceManagement, Long> getBaseDao() {
		return pCustomerServiceManagementDaoImpl;
	}

	@Override
	public List<PCustomerServiceManagement> findByMessagType(MessageType messageType) {
		return pCustomerServiceManagementDaoImpl.findByMessagType(messageType);
	}
	
	@Override
	public List<PCustomerServiceManagement> findByDepartment(Department department){
		return pCustomerServiceManagementDaoImpl.findByDepartment(department);
	}

	@Override
	public List<PCustomerServiceManagement> findPropertyMgrUsers(String communityNumber) {
		return pCustomerServiceManagementDaoImpl.findPropertyMgrUsers(communityNumber);
	}

	@Override
	public List<PCustomerServiceManagement> findByStatus(Integer status,String userName) {
		return pCustomerServiceManagementDaoImpl.findByStatus(status,userName);
	}
	
	

}
