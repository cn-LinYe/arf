package com.arf.crowdfunding.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.crowdfunding.dao.ZcShopkeeperSartnerPreferentialDao;
import com.arf.crowdfunding.entity.ZcShopkeeperSartnerPreferential;
import com.arf.crowdfunding.service.ZcShopkeeperSartnerPreferentialService;

/**
 * 众筹优惠Service
 * @author LW on 2016-06-20
 * @version 1.0
 *
 */
@Service("zcShopkeeperSartnerPreferentialServiceImpl")
public class ZcShopkeeperSartnerPreferentialServiceImpl extends BaseServiceImpl<ZcShopkeeperSartnerPreferential, Long> implements ZcShopkeeperSartnerPreferentialService{

	@Resource(name="zcShopkeeperSartnerPreferentialDao")
	private ZcShopkeeperSartnerPreferentialDao zcShopkeeperSartnerPreferentialDao;

	
	@Override
	public ZcShopkeeperSartnerPreferential getPackTypeId(String community_number, String project_id, int shopkeeper_partner) {
		
		return zcShopkeeperSartnerPreferentialDao.getPackTypeId(community_number, project_id, shopkeeper_partner);
	}
	
	@Override
	protected BaseDao<ZcShopkeeperSartnerPreferential, Long> getBaseDao() {
		return zcShopkeeperSartnerPreferentialDao;
	}

}
