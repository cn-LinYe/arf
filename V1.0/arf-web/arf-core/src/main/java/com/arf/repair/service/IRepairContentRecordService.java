package com.arf.repair.service;

import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.service.BaseService;
import com.arf.repair.entity.RepairContentRecord;

public interface IRepairContentRecordService extends BaseService<RepairContentRecord, Long>{

	/**
	 * 查询我的报修记录
	 * @param userName
	 * @param communityNumber
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public PageResult<Map<String, Object>> findByCommunityAndUserName(String userName,String communityNumber,Integer pageSize, Integer pageNo);
}
