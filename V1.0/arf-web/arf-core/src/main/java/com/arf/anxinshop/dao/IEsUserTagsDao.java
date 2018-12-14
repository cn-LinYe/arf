package com.arf.anxinshop.dao;

import com.arf.anxinshop.entity.EsUserTags;
import com.arf.core.dao.BaseDao;

public interface IEsUserTagsDao extends BaseDao<EsUserTags, Long> {

	EsUserTags findByUserName(String userName);

	public void deleteAll();
}
