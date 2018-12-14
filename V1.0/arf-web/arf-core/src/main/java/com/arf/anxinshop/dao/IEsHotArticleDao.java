package com.arf.anxinshop.dao;

import java.util.List;
import java.util.Map;

import com.arf.anxinshop.entity.EsHotArticle;
import com.arf.anxinshop.entity.EsHotArticle.Guest;
import com.arf.anxinshop.entity.EsHotArticle.Status;
import com.arf.core.dao.BaseDao;

public interface IEsHotArticleDao extends BaseDao<EsHotArticle, Long> {

	List<EsHotArticle> findByGuestStatus(Guest guest, Status status);

	List<Map<String, Object>> findByStatusUserGroupIds(Status status,
			String groupids);
	
	List<EsHotArticle> findByKeyword(String keyword,String communityNumber,int limit);

}
