package com.arf.smart.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.smart.dao.IPCityCustomFunctionRecordDao;
import com.arf.smart.entity.PCityCustomFunctionRecord;
import com.arf.smart.service.IPCityCustomFunctionRecordService;

@Service("pcityCustomFunctionRecordService")
public class PCityCustomFunctionRecordServiceImpl extends BaseServiceImpl<PCityCustomFunctionRecord, Long> implements IPCityCustomFunctionRecordService{
	
	@Resource(name="pcityCustomFunctionRecordDao")
	IPCityCustomFunctionRecordDao pcityCustomFunctionRecordDao;

	@Override
	protected BaseDao<PCityCustomFunctionRecord, Long> getBaseDao() {
		return pcityCustomFunctionRecordDao;
	}

	@Override
	public List<PCityCustomFunctionRecord> findByCityCode(String cityCode) {
		return pcityCustomFunctionRecordDao.findByCityCode(cityCode);
	}

}
