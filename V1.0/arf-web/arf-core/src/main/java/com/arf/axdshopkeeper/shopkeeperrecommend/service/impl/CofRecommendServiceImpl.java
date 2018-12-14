package com.arf.axdshopkeeper.shopkeeperrecommend.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.axdshopkeeper.shopkeeperrecommend.dao.ICofRecommendDao;
import com.arf.axdshopkeeper.shopkeeperrecommend.entity.CofRecommend;
import com.arf.axdshopkeeper.shopkeeperrecommend.service.ICofRecommendService;
import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("cofRecommendServiceImpl")
public class CofRecommendServiceImpl extends BaseServiceImpl<CofRecommend, Long> implements ICofRecommendService {

	@Resource(name="cofRecommendDaoImpl")
	private ICofRecommendDao cofRecommendDaoImpl;
	
	@Override
	protected BaseDao<CofRecommend, Long> getBaseDao() {
		return cofRecommendDaoImpl;
	}

	@Override
	public CofRecommend findLatest(String communityNumber) {
		return cofRecommendDaoImpl.findLatest(communityNumber);
	}
	
	@Override
	public PageResult<Map<String, Object>> findExtraByCommunityNumberListPage(Integer pageSize,Integer pageNo,String userName,
			String communityNumber,String shopkeeperUserName){
		return cofRecommendDaoImpl.findExtraByCommunityNumberListPage(pageSize, pageNo, userName, communityNumber, shopkeeperUserName);
	}

	@Override
	public PageResult<CofRecommend> findByCommunityNumberListPage(Integer pageSize, Integer pageNo,
			String communityNumber,String shopkeeperUserName) {
		return cofRecommendDaoImpl.findByCommunityNumberListPage(pageSize, pageNo, communityNumber,
				shopkeeperUserName);
	}

	@Override
	public CofRecommend findById(Long id) {
		return cofRecommendDaoImpl.findById(id);
	}
	
	@Override
	public Map<String,Object> findMapByIdUserName(Long id,String userName){
		return cofRecommendDaoImpl.findMapByIdUserName(id, userName);
	}

	@Override
	public List<CofRecommend> findByKeyword(String keyword, String communityNumber, int limit) {
		return cofRecommendDaoImpl.findByKeyword(keyword, communityNumber, limit);
	}

	@Override
	public Map<String, Object> findNextRecommendByIdUserNameCommunityNumber(
			Long recommendId, String userName, String communityNumber) {
		return cofRecommendDaoImpl.findNextRecommendByIdUserNameCommunityNumber(recommendId,userName,communityNumber);
	}
	
}
