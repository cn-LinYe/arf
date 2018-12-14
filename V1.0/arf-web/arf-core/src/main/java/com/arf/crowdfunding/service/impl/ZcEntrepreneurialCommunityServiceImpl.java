package com.arf.crowdfunding.service.impl;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.crowdfunding.dao.ZcEntrepreneurialCommunityDao;
import com.arf.crowdfunding.entity.ZcEntrepreneurialCommunity;
import com.arf.crowdfunding.service.ZcEntrepreneurialCommunityService;
/**
 * 获取店铺信息Service
 * @author LW on 2016-6-18
 * @version 1.0
 *
 */
@Service("zcEntrepreneurialCommunityServiceImpl")
public class ZcEntrepreneurialCommunityServiceImpl extends BaseServiceImpl<ZcEntrepreneurialCommunity, Long> implements ZcEntrepreneurialCommunityService{

	@Resource(name="zcEntrepreneurialCommunityDao")
	private ZcEntrepreneurialCommunityDao zcEntrepreneurialCommunityDao;
	
	@Override
	public List<ZcEntrepreneurialCommunity> getShopMessage(String community_number, String project_id) {
		
		return zcEntrepreneurialCommunityDao.getShopMessage(community_number, project_id);
	}

	@Override
	public String getShopName(String project_id) {
		
		return zcEntrepreneurialCommunityDao.getShopName(project_id);
	}

	@Override
	public ZcEntrepreneurialCommunity getTotalSharesNum(String communityNumber, String projectId) {
		
		return zcEntrepreneurialCommunityDao.getTotalSharesNum(communityNumber, projectId);
	}

	@Override
	protected BaseDao<ZcEntrepreneurialCommunity, Long> getBaseDao() {
		
		return zcEntrepreneurialCommunityDao;
	}

	@Override
	public List<ZcEntrepreneurialCommunity> getShopMessageByCommunity(String community_number,Integer shopStatus,String shopkeeperPartnerUser) {
		return zcEntrepreneurialCommunityDao.getShopMessageByCommunity(community_number, shopStatus,shopkeeperPartnerUser);
	}

	@Override
	public void updateStatus(String community_number, String project_id, int status) {
		
		ZcEntrepreneurialCommunity zc = zcEntrepreneurialCommunityDao.getCommunityMessage(community_number, project_id);
		if(zc != null){
			zc.setShopStatus(status);
			if(status == 1){
				zc.setCrowdfundingStartDate(new Date());
			}
			update(zc);
			this.getBaseDao().flush();
		}			
		
	}

	@Override
	public synchronized boolean isOverdue(String community_number, String project_id) {
		
		return zcEntrepreneurialCommunityDao.isOverdue(community_number, project_id);
	}

	@Override
	public BigDecimal getLeastAmount(String community_number, String project_id) {
		
		return zcEntrepreneurialCommunityDao.getLeastAmount(community_number, project_id);
	}

   
}
