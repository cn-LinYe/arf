package com.arf.search.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.search.entity.AppModuleTags;

public interface IAppModuleTagsDao extends BaseDao<AppModuleTags, Long>{

	List<AppModuleTags> findByTags(String[] tags);

}
