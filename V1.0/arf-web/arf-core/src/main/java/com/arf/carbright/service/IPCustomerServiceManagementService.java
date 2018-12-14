package com.arf.carbright.service;

import java.util.List;

import com.arf.carbright.entity.PCustomerServiceManagement;
import com.arf.carbright.entity.PCustomerServiceManagement.Department;
import com.arf.carbright.entity.PCustomerServiceManagement.MessageType;
import com.arf.core.service.BaseService;

public interface IPCustomerServiceManagementService extends BaseService<PCustomerServiceManagement, Long>{

	/**根据消息类型查找客服人员
	 * @param messageType
	 * @return
	 */
	public List<PCustomerServiceManagement> findByMessagType(MessageType messageType);

	/**
	 * 根据部门查找客服人员
	 * @param department
	 * @return
	 */
	public List<PCustomerServiceManagement> findByDepartment(Department department);
	
	/**
	 * 查询物业管理处的客服人员
	 * @return
	 */
	public List<PCustomerServiceManagement> findPropertyMgrUsers(String communityNumber);
	
	/**
	 * 根据状态查询所有客服人员
	 * @return
	 */
	public List<PCustomerServiceManagement> findByStatus(Integer status,String userName);
}
