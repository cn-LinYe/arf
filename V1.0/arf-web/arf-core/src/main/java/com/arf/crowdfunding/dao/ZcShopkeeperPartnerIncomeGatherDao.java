package com.arf.crowdfunding.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.crowdfunding.entity.ZcShopkeeperPartnerIncomeGather;

public interface ZcShopkeeperPartnerIncomeGatherDao extends BaseDao<ZcShopkeeperPartnerIncomeGather, Long>{
	
	/**查看收益
	 * @param community_number 小区编号
	 * @param project_id 项目ID
	 * @param shopkeeper_partner_user 用户（店主或者合伙人）
	 * @param income_inquire 0周 1月 2年
	 * @param condition 查看基数
	 * @return
	 */
	public List<ZcShopkeeperPartnerIncomeGather> findIncomeGatherbyInquire(String community_number,String project_id,String shopkeeper_partner_user,Integer income_inquire,Integer condition);
}
