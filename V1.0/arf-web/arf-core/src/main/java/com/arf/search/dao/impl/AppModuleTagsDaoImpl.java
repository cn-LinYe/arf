package com.arf.search.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.search.dao.IAppModuleTagsDao;
import com.arf.search.entity.AppModuleTags;
import com.google.common.collect.Lists;

@Repository("appModuleTagsDao")
public class AppModuleTagsDaoImpl extends BaseDaoImpl<AppModuleTags, Long> implements IAppModuleTagsDao{

	@Override
	public List<AppModuleTags> findByTags(String[] tags) {
		if(tags == null || tags.length == 0){
			return Lists.newArrayList();
		}
		StringBuffer sql = new StringBuffer("select a.* from search_app_module_tags a where 1=1 ");
		sql.append(" and ( 1<>1 ");
		for(int i=0;i<tags.length;i++){
			sql.append(" or a.name like concat('%',:tag_" + i + ",'%') ");
			sql.append(" or LOCATE(a.tags,:tag_" + i + ")>0 ");
		}
		sql.append(" ) ");
		Query query = this.entityManager.createNativeQuery(sql.toString(), AppModuleTags.class);
		for(int i=0;i<tags.length;i++){
			String tag = tags[i];
			query.setParameter("tag_" + i, tag);
		}
		return query.getResultList();
	}

}
