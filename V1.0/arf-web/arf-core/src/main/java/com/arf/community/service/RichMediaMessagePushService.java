package com.arf.community.service;

import java.util.List;
import java.util.Map;

import com.arf.community.entity.RichMediaMessagePush;
import com.arf.core.service.BaseService;

public interface RichMediaMessagePushService extends BaseService<RichMediaMessagePush, Long> {

	/**
	 * 根据目标类型查询符合条件的
	 * @param communitys
	 * @param propertys
	 * @param branchIds
	 * @param cityNos
	 * @return
	 */
	List<Map<String, Object>> findByComProBraCity(List<String> communitys,List<Integer> propertys, List<Integer> branchIds,List<String> cityNos);

}
