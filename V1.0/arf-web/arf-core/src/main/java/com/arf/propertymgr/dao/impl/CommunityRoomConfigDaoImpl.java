package com.arf.propertymgr.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.access.entity.AccessCommunity;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.propertymgr.dao.ICommunityRoomConfigDao;
import com.arf.propertymgr.entity.CommunityRoomConfig;

@Repository("communityRoomConfigDao")
public class CommunityRoomConfigDaoImpl extends BaseDaoImpl<CommunityRoomConfig, Long> implements ICommunityRoomConfigDao{
	/**
	 * 根据communityNumber查询
	 * @param CommunityRoomConfig
	 * @return
	 */
	@Override
	public CommunityRoomConfig findByCommunityNumber(String communityNumber) {
		StringBuffer sql =new StringBuffer(" from CommunityRoomConfig where communityNumber=:communityNumber ");
		TypedQuery<CommunityRoomConfig> query = entityManager.createQuery(sql.toString(),CommunityRoomConfig.class);
		query.setParameter("communityNumber", communityNumber);
		List<CommunityRoomConfig> list ;
		try {
			 list = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		return list.isEmpty()?null:list.get(0);
	}
}
