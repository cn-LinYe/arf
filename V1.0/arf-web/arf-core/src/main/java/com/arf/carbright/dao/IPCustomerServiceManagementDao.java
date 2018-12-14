package com.arf.carbright.dao;

import java.util.List;

import com.arf.carbright.entity.PCustomerServiceManagement;
import com.arf.carbright.entity.PCustomerServiceManagement.Department;
import com.arf.core.dao.BaseDao;

public interface IPCustomerServiceManagementDao extends BaseDao<PCustomerServiceManagement, Long>{
	
	/**根据不同类型不同部门反馈不同意见及投诉
	 * @param messageType信息类型
	 * @return
	 */
	public List<PCustomerServiceManagement> findByMessagType(PCustomerServiceManagement.MessageType messageType);
	
	/**
	 * 根据部门查找
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
