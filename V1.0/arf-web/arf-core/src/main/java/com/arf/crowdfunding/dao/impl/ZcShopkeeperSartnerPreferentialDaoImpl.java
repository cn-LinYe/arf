package com.arf.crowdfunding.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.crowdfunding.dao.ZcShopkeeperSartnerPreferentialDao;
import com.arf.crowdfunding.entity.ZcShopkeeperSartnerPreferential;

@Repository("zcShopkeeperSartnerPreferentialDao")
public class ZcShopkeeperSartnerPreferentialDaoImpl extends BaseDaoImpl<ZcShopkeeperSartnerPreferential, Long> implements ZcShopkeeperSartnerPreferentialDao {

	@Override
	public ZcShopkeeperSartnerPreferential getPackTypeId(String community_number, String project_id, int shopkeeper_partner) {
		
		String sql = "from ZcShopkeeperSartnerPreferential where communityNumber=:communityNumber and projectId=:projectId and shopkeeperPartner=:shopkeeperPartner";
		
		 List<ZcShopkeeperSartnerPreferential> list = entityManager.createQuery(sql,ZcShopkeeperSartnerPreferential.class)
				.setParameter("communityNumber", community_number)
				.setParameter("projectId", project_id)
				.setParameter("shopkeeperPartner", shopkeeper_partner)
				.getResultList();
		return list != null && list.size() >0 ?list.get(0):null;
	}

	
}
