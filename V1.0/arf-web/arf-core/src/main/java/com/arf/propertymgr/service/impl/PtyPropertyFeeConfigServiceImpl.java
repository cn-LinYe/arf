package com.arf.propertymgr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.propertymgr.dao.IPtyPropertyFeeConfigDao;
import com.arf.propertymgr.entity.PtyPropertyFeeConfig;
import com.arf.propertymgr.service.IPtyPropertyFeeConfigService;

@Service("ptyPropertyFeeConfigServiceImpl")
public class PtyPropertyFeeConfigServiceImpl extends BaseServiceImpl<PtyPropertyFeeConfig, Long> implements IPtyPropertyFeeConfigService{

	@Resource(name="ptyPropertyFeeConfigDaoImpl")
	IPtyPropertyFeeConfigDao iPtyPropertyFeeConfigDao;
	
	@Override
	protected BaseDao<PtyPropertyFeeConfig, Long> getBaseDao() {
		return iPtyPropertyFeeConfigDao;
	}

	@Override
	public List<PtyPropertyFeeConfig> findCommunity() {
		return iPtyPropertyFeeConfigDao.findCommunity();
	}

}
