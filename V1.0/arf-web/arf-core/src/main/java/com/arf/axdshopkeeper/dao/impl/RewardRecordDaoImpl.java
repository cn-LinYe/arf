package com.arf.axdshopkeeper.dao.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.arf.axdshopkeeper.dao.IRewardRecordDao;
import com.arf.axdshopkeeper.entity.RewardRecord;
import com.arf.axdshopkeeper.entity.RewardRecord.RewardType;
import com.arf.axdshopkeeper.entity.UserAccount.IdentityType;
import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("rewardRecordDaoImpl")
public class RewardRecordDaoImpl extends BaseDaoImpl<RewardRecord, Long>
		implements IRewardRecordDao {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public PageResult<RewardRecord> findByCondition(Integer pageSize, Integer pageNo,
			String userName) {
		//查询总数
		StringBuffer sbCount = new StringBuffer("select count(id) as COUNT from bi_reward_record a ");
		sbCount.append(" where a.user_name = :userName ");
		Query queryCount = entityManager.createNativeQuery(sbCount.toString());
		queryCount.setParameter("userName", userName);
		Integer count = 0;
		try{
			count = ((BigInteger)queryCount.getSingleResult()).intValue();
		}catch(Exception e){
			logger.error("RewardRecordDaoImpl.findByCondition查询数据条数异常",e);
		}
		
		//分页查询
		StringBuffer sb = new StringBuffer("from RewardRecord a ");
		sb.append(" where a.userName = :userName ");
		sb.append(" order by createDate desc ");
		TypedQuery<RewardRecord> typedQuery = entityManager.createQuery(sb.toString(), RewardRecord.class);
		typedQuery.setParameter("userName", userName);
		//如果没有传分页，则返回全部
		if(pageNo==null || pageSize==null){
			pageNo=1;
			pageSize=count;
		}
		typedQuery.setFirstResult((pageNo-1)*pageSize);
		typedQuery.setMaxResults(pageSize);
		List<RewardRecord> rewardRecords = typedQuery.getResultList();
		
		PageResult<RewardRecord> pageResult = new PageResult<RewardRecord>();
		pageResult.setList(rewardRecords);
		pageResult.setTotalNum(count);
		return pageResult;
	}

	@Override
	public List<RewardRecord> findByUsernameIdentityTypeRewardType(
			String userName, IdentityType identityType, RewardType rewardType) {
		StringBuffer sb = new StringBuffer("from RewardRecord a ");
		sb.append(" where 1 = 1 and a.userName = :userName and a.identityType = :identityType ");
		if(rewardType != null){
			sb.append(" and a.rewardType = :rewardType");
		}
		TypedQuery<RewardRecord> typedQuery = entityManager.createQuery(sb.toString(), RewardRecord.class);
		typedQuery.setParameter("userName", userName);
		typedQuery.setParameter("identityType", identityType);
		if(rewardType != null){
			typedQuery.setParameter("rewardType", rewardType);
		}
		return typedQuery.getResultList();
	}

}













