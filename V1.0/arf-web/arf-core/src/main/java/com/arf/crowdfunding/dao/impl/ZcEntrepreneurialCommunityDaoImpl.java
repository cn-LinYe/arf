package com.arf.crowdfunding.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.crowdfunding.dao.ZcEntrepreneurialCommunityDao;
import com.arf.crowdfunding.entity.ZcEntrepreneurialCommunity;

/**
 * 查询店铺信息Dao
 * @author LW on 2016-06-18
 * @version 1.0
 *
 */
@Repository("zcEntrepreneurialCommunityDao")
public class ZcEntrepreneurialCommunityDaoImpl extends BaseDaoImpl<ZcEntrepreneurialCommunity, Long> implements ZcEntrepreneurialCommunityDao {

	//店铺数据信息
	@Override
	public List<ZcEntrepreneurialCommunity> getShopMessage(String community_number,String project_id) {
		
		StringBuilder jpql = new StringBuilder();				
		jpql.append("from ZcEntrepreneurialCommunity where communityNumber=:communityNumber and projectId=:projectId and status=1");

		return entityManager.createQuery(jpql.toString(), ZcEntrepreneurialCommunity.class).setParameter("communityNumber", community_number)
				.setParameter("projectId", project_id).getResultList();
	}
	
	
	//店铺数据信息
		@Override
		public List<ZcEntrepreneurialCommunity> getShopMessageByCommunity(String community_number,Integer shopStatus,String shopkeeperPartnerUser) {
			
			StringBuilder jpql = new StringBuilder();	
			jpql.append("from ZcEntrepreneurialCommunity where communityNumber=:communityNumber and status=1 and projectId in (select projectId from ZcShopkeeperPartnerOrder where shopkeeperPartnerUser=:shopkeeperPartnerUser and status=4 and payStatus=1)");
			if(shopStatus>0){
				if(shopStatus==2 || shopStatus==3){
					jpql.append(" and (shopStatus=2 or shopStatus=3)");
				}else
				{
					jpql.append(" and shopStatus=:shopStatus");
				}
			}
			TypedQuery<ZcEntrepreneurialCommunity> typedQuery=entityManager.createQuery(jpql.toString(), ZcEntrepreneurialCommunity.class);
			typedQuery.setParameter("communityNumber", community_number);
			typedQuery.setParameter("shopkeeperPartnerUser", shopkeeperPartnerUser);
			if(shopStatus>0){
				if(shopStatus==2 || shopStatus==3){
					
				}else
				{
					typedQuery.setParameter("shopStatus",shopStatus);
				}
			}
			return typedQuery.getResultList();
		}

	//通过项目ID查找店铺名
	@Override
	public String getShopName(String project_id) {
		
		String jpql = "from ZcEntrepreneurialCommunity where projectId = :projectId";
		List<ZcEntrepreneurialCommunity> list = entityManager.createQuery(jpql, ZcEntrepreneurialCommunity.class)
				.setParameter("projectId",project_id)
				.getResultList();
		if(list.size() != 0){
			return list.get(0).getShopName();
		}else{
			return "Don't Find ShopName";
		}
	}

	/**得到众筹项目的总股数
	 * @param communityNumber 小区编号
	 * @param projectId 项目ID
	 */
	@Override
	public ZcEntrepreneurialCommunity getTotalSharesNum(String communityNumber, String projectId) {
		
		String jpql = "from ZcEntrepreneurialCommunity where projectId = :projectId and communityNumber=:communityNumber";
		List<ZcEntrepreneurialCommunity> list = entityManager.createQuery(jpql, ZcEntrepreneurialCommunity.class)
				.setParameter("projectId",projectId)
				.setParameter("communityNumber", communityNumber)
				.getResultList();
		if(list.size() > 0)
			return list.get(0);
		else
			return null;

	}

/*	//更新众筹项目状态
	@Override
	public void updateStatus(String community_number, String project_id, int status) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE ZcEntrepreneurialCommunity SET shopStatus=:shopStatus");
		if(status == 1){
			sb.append(",crowdfundingStartDate=:crowdfundingStartDate");
		}
		sb.append(" where communityNumber=:communityNumber and projectId=:projectId");		
		
		if(status == 1){
			 entityManager.createQuery(sb.toString())
			 .setParameter("shopStatus", status)
			 .setParameter("communityNumber", community_number)
			 .setParameter("crowdfundingStartDate", new Date())
			 .setParameter("projectId", project_id).executeUpdate();
		}else{
			 entityManager.createQuery(sb.toString())
			 .setParameter("shopStatus", status)
			 .setParameter("communityNumber", community_number)				
			 .setParameter("projectId", project_id).executeUpdate();
		}		

	}*/

	//判断项目是否已过期
	@Override
	public boolean isOverdue(String community_number, String project_id) {
		
		String jpql = "from ZcEntrepreneurialCommunity where projectId = :projectId and communityNumber=:communityNumber";
		List<ZcEntrepreneurialCommunity> list = entityManager.createQuery(jpql, ZcEntrepreneurialCommunity.class)
				.setParameter("projectId",project_id)
				.setParameter("communityNumber", community_number)
				.getResultList();
		if(list.size()>0){
			long timedue =  list.get(0).getCrowdfundingDays()*86400000l;
			long startTime = list.get(0).getCrowdfundingStartDate().getTime();
			long nowTime = new Date().getTime();
			return nowTime>(startTime+timedue)?true:false;
		}else{
			return false;
		}
		
	}

	//得到项目最小众筹金额 
	@Override
	public BigDecimal getLeastAmount(String community_number, String project_id) {
		
		String jpql = "from ZcEntrepreneurialCommunity where projectId = :projectId and communityNumber=:communityNumber";
		List<ZcEntrepreneurialCommunity> list = entityManager.createQuery(jpql, ZcEntrepreneurialCommunity.class)
				.setParameter("projectId",project_id)
				.setParameter("communityNumber", community_number)
				.getResultList();
		return list.size() > 0?list.get(0).getLeastPartnerAmount():BigDecimal.ONE;
	}

	@Override
	public ZcEntrepreneurialCommunity getCommunityMessage(String community_number, String project_id) {
		
		StringBuilder jpql = new StringBuilder();				
		jpql.append("from ZcEntrepreneurialCommunity where communityNumber=:communityNumber and projectId=:projectId and status=1");

		List<ZcEntrepreneurialCommunity> list = entityManager.createQuery(jpql.toString(), ZcEntrepreneurialCommunity.class).setParameter("communityNumber", community_number)
				.setParameter("projectId", project_id).getResultList();
		
		return list !=null && list.size() >0 ? list.get(0):null;
	}
	
}
