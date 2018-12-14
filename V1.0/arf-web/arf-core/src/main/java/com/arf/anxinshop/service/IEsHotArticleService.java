package com.arf.anxinshop.service;

import java.util.List;
import java.util.Map;

import com.arf.anxinshop.entity.EsHotArticle;
import com.arf.anxinshop.entity.EsHotArticle.Guest;
import com.arf.anxinshop.entity.EsHotArticle.Status;
import com.arf.core.service.BaseService;

public interface IEsHotArticleService extends BaseService<EsHotArticle, Long> {

	List<EsHotArticle> findByGuestStatus(Guest guest, Status status);

	List<Map<String, Object>> findByStatusUserGroupIds(Status status, String groupids);

}
