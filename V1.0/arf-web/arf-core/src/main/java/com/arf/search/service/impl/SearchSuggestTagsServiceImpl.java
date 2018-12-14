package com.arf.search.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.search.dao.ISearchSuggestTagsDao;
import com.arf.search.entity.SearchSuggestTags;
import com.arf.search.service.ISearchSuggestTagsService;

@Service("searchSuggestTagsService")
public class SearchSuggestTagsServiceImpl extends BaseServiceImpl<SearchSuggestTags, Long>
implements ISearchSuggestTagsService{

	@Resource(name = "searchSuggestTagsDao")
	private ISearchSuggestTagsDao searchSuggestTagsDao;
	
	@Override
	protected BaseDao<SearchSuggestTags, Long> getBaseDao() {
		return searchSuggestTagsDao;
	}

	@Override
	public List<SearchSuggestTags> findStartsWith(String keyword, String comunityNumber, int limit) {
		return searchSuggestTagsDao.findStartsWith(keyword, comunityNumber, limit);
	}

	@Override
	public SearchSuggestTags findBySuggest(String suggest, String communityNumber) {
		return searchSuggestTagsDao.findBySuggest(suggest, communityNumber);
	}


}
