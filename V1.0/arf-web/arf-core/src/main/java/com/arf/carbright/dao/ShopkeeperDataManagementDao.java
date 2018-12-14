package com.arf.carbright.dao;

import com.arf.carbright.entity.ShopkeeperDataManagement;
import com.arf.core.dao.BaseDao;

public interface ShopkeeperDataManagementDao extends BaseDao<ShopkeeperDataManagement, Long>{
	
	/**
	 * 通过电话查找店主
	 * @return
	 */
	public ShopkeeperDataManagement findByPhone(String phone);

}
