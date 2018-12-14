package com.arf.anxinshop.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.anxinshop.dao.IEsHotArticleDao;
import com.arf.anxinshop.entity.EsHotArticle;
import com.arf.anxinshop.entity.EsHotArticle.Guest;
import com.arf.anxinshop.entity.EsHotArticle.Status;
import com.arf.anxinshop.service.IEsHotArticleService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("esHotArticleServiceImpl")
public class EsHotArticleServiceImpl extends
		BaseServiceImpl<EsHotArticle, Long> implements IEsHotArticleService {

	@Resource(name = "esHotArticleDaoImpl")
	private IEsHotArticleDao esHotArticleDaoImpl;
	
	@Override
	protected BaseDao<EsHotArticle, Long> getBaseDao() {
		return esHotArticleDaoImpl;
	}

	@Override
	public List<EsHotArticle> findByGuestStatus(Guest guest, Status status) {
		return esHotArticleDaoImpl.findByGuestStatus(guest,status);
	}

	@Override
	public List<Map<String, Object>> findByStatusUserGroupIds(Status status,
			String groupids) {
		return esHotArticleDaoImpl.findByStatusUserGroupIds(status,groupids);
	}

	
	
}
