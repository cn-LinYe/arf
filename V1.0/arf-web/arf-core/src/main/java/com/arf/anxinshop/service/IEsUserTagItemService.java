package com.arf.anxinshop.service;

import java.util.List;

import com.arf.anxinshop.entity.EsUserTagItem;
import com.arf.core.service.BaseService;

public interface IEsUserTagItemService extends BaseService<EsUserTagItem, Long> {

	public List<EsUserTagItem> findByGroupId(Long groupId);
}
