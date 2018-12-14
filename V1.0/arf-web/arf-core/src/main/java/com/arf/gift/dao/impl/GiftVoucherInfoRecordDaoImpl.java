package com.arf.gift.dao.impl;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gift.dao.IGiftVoucherInfoRecordDao;
import com.arf.gift.entity.GiftVoucherInfoRecord;

@Repository("giftVoucherInfoRecordDaoImpl")
public class GiftVoucherInfoRecordDaoImpl extends BaseDaoImpl<GiftVoucherInfoRecord, Long>implements IGiftVoucherInfoRecordDao{

	@Override
	public PageResult<GiftVoucherInfoRecord> findGiftRecordByCondition(Integer pageNo, Integer pageSize, GiftVoucherInfoRecord.Status status[],String userName, Long id,GiftVoucherInfoRecord.ExpressLogisticsStatus expressLogisticsStatus) {
		
		StringBuilder sql = new StringBuilder("select g from GiftVoucherInfoRecord g where 1=1 ");
		StringBuilder countSb = new StringBuilder("select count(1) as COUNT from GiftVoucherInfoRecord g where 1=1 ");
		if (userName!=null) {
			sql.append(" and g.userName=:userName");
			countSb.append(" and g.userName=:userName");
		}
		if (id!=null) {
			sql.append(" and g.id=:id");
			countSb.append(" and g.id=:id");
		}
		if (status!=null && status.length > 0) {
			sql.append(" and g.status in(:status)");
			countSb.append(" and g.status in(:status)");
		}
		if (expressLogisticsStatus!=null) {
			sql.append(" and g.expressLogisticsStatus=:expressLogisticsStatus");
			countSb.append(" and g.expressLogisticsStatus=:expressLogisticsStatus");
		}
		sql.append(" order by create_date desc ");
		
		TypedQuery<GiftVoucherInfoRecord> query = entityManager.createQuery(sql.toString(), GiftVoucherInfoRecord.class);				
		TypedQuery<Long> queryCount = entityManager.createQuery(countSb.toString(), Long.class);	
		
		if(userName!=null)
		{
			query.setParameter("userName", userName);
			queryCount.setParameter("userName", userName);
		}
		if (status != null && status.length > 0) {
			query.setParameter("status", Arrays.asList(status));
			queryCount.setParameter("status", Arrays.asList(status));
		}
		if (id != null) {
			query.setParameter("id", id);
			queryCount.setParameter("id", id);
		}
		if (expressLogisticsStatus != null) {
			query.setParameter("expressLogisticsStatus", expressLogisticsStatus);
			queryCount.setParameter("expressLogisticsStatus", expressLogisticsStatus);
		}
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo - 1) * pageSize);
		
		List<GiftVoucherInfoRecord> results = query.getResultList();
		int totalNum = 0;
		try{
			totalNum = queryCount.getSingleResult().intValue();
		}catch(Exception e){
			e.printStackTrace();
		}
		PageResult<GiftVoucherInfoRecord> page = new PageResult<GiftVoucherInfoRecord>(results, totalNum);
		return page;
	}

	@Override
	public int getGiftRecordCount(String userName,GiftVoucherInfoRecord.Status status) {
		StringBuilder sql = new StringBuilder("select count(1) as COUNT from GiftVoucherInfoRecord g where 1=1 ");
		if(status!=null){
			sql.append(" and g.status=:status");
		}
		if(userName!=null){
			sql.append(" and g.userName=:userName");
		}
		TypedQuery<Long> queryCount = entityManager.createQuery(sql.toString(), Long.class);
		if(status!=null){
			queryCount.setParameter("status", status);			
		}	
		if(userName!=null){
			queryCount.setParameter("userName", userName);
		}
		try {
			Long count=queryCount.getSingleResult();
			if(count!=null){
				return count.intValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateOverdueRecordStatus() {
		String hql = "update GiftVoucherInfoRecord set status = :overDue where date(endDate) < date(now()) and status = :unUsed ";
		Query queryCount = entityManager.createQuery(hql.toString());
		queryCount.setParameter("overDue", GiftVoucherInfoRecord.Status.OVER_DUE);
		queryCount.setParameter("unUsed", GiftVoucherInfoRecord.Status.UNUSED);
		return queryCount.executeUpdate();
	}

}
