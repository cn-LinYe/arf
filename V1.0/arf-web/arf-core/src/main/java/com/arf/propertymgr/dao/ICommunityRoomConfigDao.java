package com.arf.propertymgr.dao;

import com.arf.core.dao.BaseDao;
import com.arf.propertymgr.entity.CommunityRoomConfig;

public interface ICommunityRoomConfigDao extends BaseDao<CommunityRoomConfig, Long>{

	com.arf.propertymgr.entity.CommunityRoomConfig findByCommunityNumber(String communityNumber);

	

}
