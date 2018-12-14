package com.arf.axdshopkeeper.shopkeeperrecommend.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.axdshopkeeper.shopkeeperrecommend.dao.ICofCommentDao;
import com.arf.axdshopkeeper.shopkeeperrecommend.entity.CofComment;
import com.arf.axdshopkeeper.shopkeeperrecommend.service.ICofCommentService;
import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("cofCommentServiceImpl")
public class CofCommentServiceImpl extends BaseServiceImpl<CofComment, Long> implements ICofCommentService {

	@Resource(name = "cofCommentDaoImpl")
	private ICofCommentDao cofCommentDaoImpl;
	
	@Override
	protected BaseDao<CofComment, Long> getBaseDao() {
		return cofCommentDaoImpl;
	}
	
	@Override
	public PageResult<CofComment> findByRecommendIdListPage(Integer pageNo,Integer pageSize, Long recommendId){
		return cofCommentDaoImpl.findByRecommendIdListPage(pageNo, pageSize, recommendId);
	}
	
	@Override
	public PageResult<Map<String,Object>> findMoreByRecommendIdListPage(Integer pageNo,Integer pageSize, Long recommendId){
		return cofCommentDaoImpl.findMoreByRecommendIdListPage(pageNo, pageSize, recommendId);
	}
	
	@Override
	public Integer countByRecommendId(Long recommendId){
		return cofCommentDaoImpl.countByRecommendId(recommendId);
	}

}
