package com.arf.help.service;

import com.arf.base.PageResult;
import com.arf.core.service.BaseService;
import com.arf.help.entity.HelpPublicityInfo;

public interface IHelpPublicityInfoService extends
		BaseService<HelpPublicityInfo, Long> {

	PageResult<HelpPublicityInfo> findListByCondition(String communityNumber,
			Integer pageNo, Integer pageSize);

}
