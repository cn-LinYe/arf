package com.arf.propertymgr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.arf.access.dao.IAccessCommunityDao;
import com.arf.access.entity.AccessCommunity;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.SysconfigService;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.propertymgr.dao.ICommunityRoomConfigDao;
import com.arf.propertymgr.entity.CommunityRoomConfig;
import com.arf.propertymgr.entity.CommunityRoomConfig.AccessCard;
import com.arf.propertymgr.entity.CommunityRoomConfig.AccessPass;
import com.arf.propertymgr.entity.CommunityRoomConfig.InviteVisit;
import com.arf.propertymgr.entity.CommunityRoomConfig.ReceiveVisit;
import com.arf.propertymgr.entity.CommunityRoomConfig.VisualAccessPass;
import com.arf.propertymgr.service.ICommunityRoomConfigService;
import com.google.common.collect.Lists;

@Service("communityRoomConfigService")
public class CommunityRoomConfigServiceImpl extends BaseServiceImpl<CommunityRoomConfig, Long> implements ICommunityRoomConfigService{
	
	@Resource(name="communityRoomConfigDao")
	ICommunityRoomConfigDao communityRoomConfigDao;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name="accessCommunityDao")
	IAccessCommunityDao accessCommunityDao;
	
	private static final String CommunityRoomConfig = "CommunityRoomConfig:"; 
	
	@Override
	protected BaseDao<CommunityRoomConfig, Long> getBaseDao() {
		return communityRoomConfigDao;
	}

	@Override
	public Map<String, Object> findDBtoRedis(String communityNumber) {
		Map<String, Object> communityRoomConfig = new HashMap<String, Object>();
		Map<byte[], byte[]> eMap = new HashMap<byte[], byte[]>();
		Map<String, String> roomConfigMap = redisService.hgetAll(CommunityRoomConfig);
		if(roomConfigMap==null || roomConfigMap.isEmpty()){
			roomConfigMap = new HashMap<String, String>();
			List<CommunityRoomConfig> roomConfigs = this.findAll();
			if(CollectionUtils.isNotEmpty(roomConfigs)){
				for(CommunityRoomConfig roomConfig:roomConfigs){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("propertyUpload", roomConfig.getPropertyUpload()==null?0:roomConfig.getPropertyUpload().ordinal());
					map.put("propertyAudit", roomConfig.getPropertyAudit()==null?0:roomConfig.getPropertyAudit().ordinal());
					map.put("householdReview", roomConfig.getHouseholdReview()==null?0:roomConfig.getHouseholdReview().ordinal());
					map.put("householdsAdded", roomConfig.getHouseholdsAdded()==null?0:roomConfig.getHouseholdsAdded().ordinal());
					map.put("accessDevices", roomConfig.getAccessDevices()==null?2:roomConfig.getAccessDevices().ordinal());
					map.put("accessCard", roomConfig.getAccessCard()==null?2:roomConfig.getAccessCard().ordinal());
					map.put("receiveVisit", roomConfig.getReceiveVisit()==null?2:roomConfig.getReceiveVisit().ordinal());
					map.put("inviteVisit", roomConfig.getInviteVisit()==null?2:roomConfig.getInviteVisit().ordinal());
					map.put("accessPass", roomConfig.getAccessPass()==null?2:roomConfig.getAccessPass().ordinal());
					map.put("visualAccessPass", roomConfig.getVisualAccessPass()==null?2:roomConfig.getVisualAccessPass().ordinal());
					roomConfigMap.put(roomConfig.getCommunityNumber(), JSON.toJSONString(map));
					eMap.put(roomConfig.getCommunityNumber().getBytes(), JSON.toJSONString(map).getBytes());
				}
				redisService.hMSet(CommunityRoomConfig, eMap);
			}
		}
		if(!roomConfigMap.isEmpty()){
			String config = roomConfigMap.get(communityNumber);
			if(StringUtils.isNotBlank(config)){
				communityRoomConfig = JSON.parseObject(config);
			}
		}
		return communityRoomConfig;
	}

	@Override
	public CommunityRoomConfig findByCommunityNumber(String communityNumber) {
		return communityRoomConfigDao.findByCommunityNumber(communityNumber);
	}
}
