package com.arf.crowdfunding.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.crowdfunding.dao.ZcShopkeeperPartnerIncomeGatherDao;
import com.arf.crowdfunding.entity.ZcShopkeeperPartnerIncomeGather;
import com.arf.crowdfunding.service.ZcShopkeeperPartnerIncomeGatherService;

@Service("zcShopkeeperPartnerIncomeGatherServiceImpl")
public class ZcShopkeeperPartnerIncomeGatherServiceImpl extends BaseServiceImpl<ZcShopkeeperPartnerIncomeGather, Long> implements ZcShopkeeperPartnerIncomeGatherService{

	@Resource(name="zcShopkeeperPartnerIncomeGatherDao")
	private ZcShopkeeperPartnerIncomeGatherDao zcShopkeeperPartnerIncomeGatherDao;
	@Override
	public List<ZcShopkeeperPartnerIncomeGather> findIncomeGatherbyInquire(String community_number, String project_id,
			String shopkeeper_partner_user, Integer income_inquire, Integer condition) {		
		return zcShopkeeperPartnerIncomeGatherDao.findIncomeGatherbyInquire(community_number, project_id, shopkeeper_partner_user, income_inquire, condition);
	}

	@Override
	protected BaseDao<ZcShopkeeperPartnerIncomeGather, Long> getBaseDao() {
		return zcShopkeeperPartnerIncomeGatherDao;
	}

}
