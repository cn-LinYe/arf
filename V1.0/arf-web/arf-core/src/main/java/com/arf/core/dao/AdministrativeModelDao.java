package com.arf.core.dao;

import java.util.List;

import com.arf.axdshopkeeper.entity.BizChannel;
import com.arf.axdshopkeeper.entity.BizChannelRefBranch;
import com.arf.core.entity.AdministrativeModel;

/**
 * Dao - 管理员基本信息
 * 
 * @author arf
 * @version 4.0
 */
public interface AdministrativeModelDao extends BaseDao<AdministrativeModel, Long>{
	
	/**
	 * 查找子公司信息
	 * @param cityNo
	 * 			市区号
	 * @return
	 * 			新增级别是2 的子公司信息
	 */
	List<AdministrativeModel> sellectByLevelCityNo(Integer cityNo);
	
	/**
	 * 查找物业信息
	 */
	AdministrativeModel sellectByPropretyNumber(Integer property_number);

	/**
	 * 查找子公司信息
	 */
	AdministrativeModel sellectByBranchId(Integer branch_id);

	/**
	 * 查询子公司-渠道关联表
	 * @param branchId
	 * @return
	 */
	BizChannelRefBranch findChannelRefByBranchId(String branchId);

	/**
	 * 查询渠道
	 * @param bizChannelNumber
	 * @return
	 */
	BizChannel findChannelByChannelnum(String bizChannelNumber);
}
