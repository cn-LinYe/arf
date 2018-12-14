package com.arf.help.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.help.dao.IHelpPublicityInfoDao;
import com.arf.help.entity.HelpPublicityInfo;
import com.arf.help.service.IHelpPublicityInfoService;

@Service("helpPublicityInfoServiceImpl")
public class HelpPublicityInfoServiceImpl extends
		BaseServiceImpl<HelpPublicityInfo, Long> implements
		IHelpPublicityInfoService {

	@Resource(name = "helpPublicityInfoDaoImpl")
	private IHelpPublicityInfoDao helpPublicityInfoDaoImpl;
	
	@Override
	protected BaseDao<HelpPublicityInfo, Long> getBaseDao() {
		return helpPublicityInfoDaoImpl;
	}

	@Override
	public PageResult<HelpPublicityInfo> findListByCondition(
			String communityNumber, Integer pageNo, Integer pageSize) {
		return helpPublicityInfoDaoImpl.findListByCondition(communityNumber,pageNo,pageSize);
	}
	
	
}
