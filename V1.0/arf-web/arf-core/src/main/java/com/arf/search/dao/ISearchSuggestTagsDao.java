package com.arf.search.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.search.entity.SearchSuggestTags;

public interface ISearchSuggestTagsDao extends BaseDao<SearchSuggestTags, Long>{
	
	/**
	 * 查询以keyword开头的搜索过并命中的记录
	 * @param keyword
	 * @param comunityNumber
	 * @param limit 查询返回的限制条数
	 * @return
	 */
	List<SearchSuggestTags> findStartsWith(String keyword, String comunityNumber, int limit);
	
	SearchSuggestTags findBySuggest(String suggest, String communityNumber);
}
