package com.arf.anxinshop.dao;

import java.util.List;

import com.arf.anxinshop.entity.EsUserTagItem;
import com.arf.core.dao.BaseDao;

public interface IEsUserTagItemDao extends BaseDao<EsUserTagItem, Long> {

	public List<EsUserTagItem> findByGroupId(Long groupId);
}
