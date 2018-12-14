package com.arf.salesman.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.salesman.dao.ISalesmanCommunityDao;
import com.arf.salesman.entity.SalesmanCommunity;
import com.arf.salesman.service.ISalesmanCommunityService;
@Service("salesmanCommunityServiceImpl")
public class SalesmanCommunityServiceImpl extends BaseServiceImpl<SalesmanCommunity, Long> implements ISalesmanCommunityService{

	@Resource(name="salesmanCommunityDaoImpl")
	ISalesmanCommunityDao salesmanCommunityDaoImpl;
	
	@Override
	protected BaseDao<SalesmanCommunity, Long> getBaseDao() {
		return salesmanCommunityDaoImpl;
	}

	@Override
	public PageResult<Map<String, Object>> findByUserName(String userName,Integer pageNo,Integer pageSize) {
		return salesmanCommunityDaoImpl.findByUserName(userName,pageNo,pageSize);
	}

	@Override
	public SalesmanCommunity findByUserNameCommunity(String communityNum, String userName) {
		// TODO Auto-generated method stub
		return salesmanCommunityDaoImpl.findByUserNameCommunity(communityNum, userName);
	}

	
}
