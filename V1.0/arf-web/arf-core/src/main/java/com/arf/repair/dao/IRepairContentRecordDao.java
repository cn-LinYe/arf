package com.arf.repair.dao;

import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.repair.entity.RepairContentRecord;

public interface IRepairContentRecordDao extends BaseDao<RepairContentRecord, Long> {

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
