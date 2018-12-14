package com.arf.search.dao.impl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.search.dao.ISearchSuggestTagsDao;
import com.arf.search.entity.SearchSuggestTags;

@Repository("searchSuggestTagsDao")
public class SearchSuggestTagsDaoImpl extends BaseDaoImpl<SearchSuggestTags, Long> implements ISearchSuggestTagsDao{

	@Override
	public List<SearchSuggestTags> findStartsWith(String keyword, String communityNumber, int limit) {
		StringBuffer sql = new StringBuffer("select a.* from search_suggest_tags a where 1=1 ");
		if(StringUtils.isNotBlank(communityNumber)){
			sql.append(" and (a.community_number is null or a.community_number = :communityNumber) ");
		}else{
			sql.append(" and (a.community_number is null) ");
		}
		sql.append(" and (a.suggest like concat('',:keyword,'%') or find_in_set(:keyword, a.tags) > 0) ");
		sql.append(" order by locate(:keyword, a.suggest) ASC ");
		sql.append(" , char_length(a.suggest) ASC ");
		sql.append(" limit :limit ");
		Query query = this.entityManager.createNativeQuery(sql.toString(), SearchSuggestTags.class);
		query.setParameter("keyword", keyword);
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber", communityNumber);
		}
		query.setParameter("limit", limit);
		return query.getResultList();
	}

	@Override
	public SearchSuggestTags findBySuggest(String suggest, String communityNumber) {
		StringBuffer hql = new StringBuffer("from SearchSuggestTags sst where 1=1 and sst.suggest = :suggest ");
		
		if(StringUtils.isNotBlank(communityNumber)){
			hql.append(" and (sst.communityNumber is null or sst.communityNumber = :communityNumber) ");
		}else{
			hql.append(" and (sst.communityNumber is null) ");
		}
		
		hql.append(" order by sst.createDate desc ");
		TypedQuery<SearchSuggestTags> query = this.entityManager.createQuery(hql.toString(), SearchSuggestTags.class);
		query.setParameter("suggest", suggest);
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber", communityNumber);
		}
		query.setMaxResults(1);
		List<SearchSuggestTags> list = query.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

}
