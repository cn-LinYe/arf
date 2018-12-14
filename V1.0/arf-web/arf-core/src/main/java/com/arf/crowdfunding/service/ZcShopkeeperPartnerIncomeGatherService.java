package com.arf.crowdfunding.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.crowdfunding.entity.ZcShopkeeperPartnerIncomeGather;

public interface ZcShopkeeperPartnerIncomeGatherService extends BaseService<ZcShopkeeperPartnerIncomeGather, Long>{
	
	/**获取收益记录
	 * @param community_number 小区ID
	 * @param project_id 项目ID
	 * @param shopkeeper_partner_user 受益人
	 * @param income_inquire 0周 1月2年
	 * @param condition
	 * @return
	 */
	public List<ZcShopkeeperPartnerIncomeGather> findIncomeGatherbyInquire(String community_number, String project_id,
			String shopkeeper_partner_user, Integer income_inquire, Integer condition);
}
