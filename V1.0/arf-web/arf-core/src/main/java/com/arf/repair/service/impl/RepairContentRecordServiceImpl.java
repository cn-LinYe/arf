package com.arf.repair.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.repair.dao.IRepairContentRecordDao;
import com.arf.repair.entity.RepairContentRecord;
import com.arf.repair.service.IRepairContentRecordService;

@Service("repairContentRecordService")
public class RepairContentRecordServiceImpl extends BaseServiceImpl<RepairContentRecord, Long> implements IRepairContentRecordService{

	@Resource(name="repairContentRecordDao")
	IRepairContentRecordDao repairContentRecordDao;
	
	@Override
	protected BaseDao<RepairContentRecord, Long> getBaseDao() {
		return repairContentRecordDao;
	}

	@Override
	public PageResult<Map<String, Object>> findByCommunityAndUserName(String userName, String communityNumber,Integer pageSize, Integer pageNo) {
		return repairContentRecordDao.findByCommunityAndUserName(userName, communityNumber, pageSize, pageNo);
	}

}
