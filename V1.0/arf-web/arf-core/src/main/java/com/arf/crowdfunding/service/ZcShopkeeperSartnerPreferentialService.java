package com.arf.crowdfunding.service;

import com.arf.core.service.BaseService;
import com.arf.crowdfunding.entity.ZcShopkeeperSartnerPreferential;

public interface ZcShopkeeperSartnerPreferentialService extends BaseService<ZcShopkeeperSartnerPreferential, Long>{

	/**
	 * 得到优惠套餐ID
	 * @param community_number		小区编号
	 * @param project_id			项目ID
	 * @param shopkeeper_partner	店主/合伙人
	 * @return
	 */
	public ZcShopkeeperSartnerPreferential getPackTypeId(String community_number,String project_id ,int shopkeeper_partner);
}
