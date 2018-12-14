package com.arf.search.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.search.entity.SearchSuggestTags;

public interface ISearchSuggestTagsService extends BaseService<SearchSuggestTags, Long>{
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
