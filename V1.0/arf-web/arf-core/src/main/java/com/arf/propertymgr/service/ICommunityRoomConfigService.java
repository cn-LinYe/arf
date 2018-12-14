package com.arf.propertymgr.service;

import java.util.Map;

import com.arf.access.entity.AccessCommunity;
import com.arf.core.service.BaseService;
import com.arf.propertymgr.entity.CommunityRoomConfig;

public interface ICommunityRoomConfigService extends BaseService<CommunityRoomConfig, Long>{
	/**
	 * 根据小区编号查询小区房屋配置
	 * @param communityNumber
	 * @return
	 */
	public Map<String, Object> findDBtoRedis(String communityNumber);
	
	/**
	 * 根据communityNumber查询
	 * @param CommunityRoomConfig
	 * @return
	 */
	public CommunityRoomConfig findByCommunityNumber(String communityNumber);
	

}
