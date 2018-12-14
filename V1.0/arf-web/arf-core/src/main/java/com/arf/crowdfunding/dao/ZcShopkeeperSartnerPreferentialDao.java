package com.arf.crowdfunding.dao;

import com.arf.core.dao.BaseDao;
import com.arf.crowdfunding.entity.ZcShopkeeperSartnerPreferential;

public interface ZcShopkeeperSartnerPreferentialDao extends BaseDao<ZcShopkeeperSartnerPreferential, Long>{

	/**
	 * 查找优惠套餐ID
	 * @param community_number		小区编号
	 * @param project_id			项目ID
	 * @param shopkeeper_partner	店主/合伙人
	 * @return
	 */
	public ZcShopkeeperSartnerPreferential getPackTypeId(String community_number,String project_id ,int shopkeeper_partner);
}
