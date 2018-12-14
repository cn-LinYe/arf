package com.arf.community.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.community.dao.RichMediaMessagePushDao;
import com.arf.community.entity.RichMediaMessagePush;
import com.arf.community.service.RichMediaMessagePushService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("richMediaMessagePushServiceImpl")
public class RichMediaMessagePushServiceImpl extends BaseServiceImpl<RichMediaMessagePush, Long> implements RichMediaMessagePushService {
	
	@Resource(name="richMediaMessagePushDaoImpl")
	RichMediaMessagePushDao richMediaMessagePushDaoImpl;
	
	@Override
	protected BaseDao<RichMediaMessagePush, Long> getBaseDao() {
		return richMediaMessagePushDaoImpl;
	}

	@Override
	public List<Map<String, Object>> findByComProBraCity(List<String> communitys, List<Integer> propertys,List<Integer> branchIds, List<String> cityNos) {
		return richMediaMessagePushDaoImpl.findByComProBraCity(communitys,propertys,branchIds,cityNos);
	}

}
