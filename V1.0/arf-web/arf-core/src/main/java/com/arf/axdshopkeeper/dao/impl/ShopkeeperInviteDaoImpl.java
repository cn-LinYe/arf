package com.arf.axdshopkeeper.dao.impl;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.arf.axdshopkeeper.dao.IShopkeeperInviteDao;
import com.arf.axdshopkeeper.entity.ShopkeeperInvite;
import com.arf.axdshopkeeper.entity.UserAccount.IdentityType;
import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("shopkeeperInviteDao")
public class ShopkeeperInviteDaoImpl extends BaseDaoImpl<ShopkeeperInvite, Long> implements IShopkeeperInviteDao{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public PageResult<ShopkeeperInvite> findByCondition(Integer pageSize, Integer pageNo,String inviterUserName,
			IdentityType identityType) {
		//查询总数
		StringBuffer sbCount = new StringBuffer("select count(id) as COUNT from bi_shopkeeper_invite a ");
		sbCount.append(" where 1 = 1 and a.inviter_user_name = :inviterUserName and a.identity_type = :identityType");
		Query queryCount = entityManager.createNativeQuery(sbCount.toString());
		queryCount.setParameter("inviterUserName", inviterUserName);
		queryCount.setParameter("identityType", identityType.ordinal());
		Integer count = 0;
		try{
			count = ((BigInteger)queryCount.getSingleResult()).intValue();
		}catch(Exception e){
			logger.error("ShopkeeperInviteDaoImpl.findByCondition查询数据条数异常",e);
		}
		
		//分页查询
		StringBuffer sb = new StringBuffer("from ShopkeeperInvite a ");
		sb.append(" where 1 = 1 and a.inviterUserName = :inviterUserName and a.identityType = :identityType");
		sb.append(" order by a.createDate desc ");
		TypedQuery<ShopkeeperInvite> typedQuery = entityManager.createQuery(sb.toString(), ShopkeeperInvite.class);
		typedQuery.setParameter("inviterUserName", inviterUserName);
		typedQuery.setParameter("identityType", identityType);
		//如果没有传分页，则返回全部
		if(pageNo==null || pageSize==null){
			pageNo=1;
			pageSize=count;
		}
		typedQuery.setFirstResult((pageNo-1)*pageSize);
		typedQuery.setMaxResults(pageSize);
		List<ShopkeeperInvite> rewardRecords = typedQuery.getResultList();
		
		PageResult<ShopkeeperInvite> pageResult = new PageResult<ShopkeeperInvite>();
		pageResult.setList(rewardRecords);
		pageResult.setTotalNum(count);
		return pageResult;
	}

	@Override
	public List<ShopkeeperInvite> findByBranchOrChannel(String inviterNumber,
			IdentityType identityType) {
		StringBuffer sb = new StringBuffer("from ShopkeeperInvite a ");
		sb.append(" where 1 = 1 and a.inviterNumber = :inviterNumber and a.identityType = :identityType");
		sb.append(" order by a.createDate desc ");
		TypedQuery<ShopkeeperInvite> typedQuery = entityManager.createQuery(sb.toString(), ShopkeeperInvite.class);
		typedQuery.setParameter("inviterNumber", inviterNumber);
		typedQuery.setParameter("identityType", identityType);
		return typedQuery.getResultList();
	}

	@Override
	public int findNewCount(String userName) {
		StringBuffer sb = new StringBuffer("select ifnull(count(*),0) as COUNT from bi_shopkeeper_invite a ");
		sb.append(" where 1 = 1 and a.inviter_user_name = :userName and a.read_status = :readStatus");
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("userName", userName);
		query.setParameter("readStatus", ShopkeeperInvite.ReadStatus.NEW.ordinal());
		String s = query.getSingleResult().toString();
		return Integer.parseInt(s);
	}

	@Override
	public void readMyInvite(String userName, String[] idList) {
		StringBuffer sb = new StringBuffer("update bi_shopkeeper_invite set read_status = :read where inviter_user_name = :userName ");
		if(idList != null && idList.length > 0){
			sb.append(" and id in(:idList)");
		}
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("read", ShopkeeperInvite.ReadStatus.READ.ordinal());
		query.setParameter("userName", userName);
		if(idList != null && idList.length > 0){
			query.setParameter("idList", Arrays.asList(idList));
		}
		query.executeUpdate();
	}
}
