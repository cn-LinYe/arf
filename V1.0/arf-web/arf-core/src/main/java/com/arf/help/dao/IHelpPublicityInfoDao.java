package com.arf.help.dao;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.help.entity.HelpPublicityInfo;

public interface IHelpPublicityInfoDao extends BaseDao<HelpPublicityInfo, Long> {

	PageResult<HelpPublicityInfo> findListByCondition(String communityNumber,
			Integer pageNo, Integer pageSize);

	
	
}
