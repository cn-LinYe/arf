package com.arf.anxinshop.service;

import com.arf.anxinshop.entity.EsUserTags;
import com.arf.core.service.BaseService;

public interface IEsUserTagsService extends BaseService<EsUserTags, Long> {

	EsUserTags findByUserName(String userName);

	public void deleteAll();
}
