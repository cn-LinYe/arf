package com.arf.insurance.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.insurance.dao.IInsuranceOrderRecordDao;
import com.arf.insurance.entity.InsuranceOrderRecord;

@Repository("insuranceOrderRecordDaoImpl")
public class InsuranceOrderRecordDaoImpl extends BaseDaoImpl<InsuranceOrderRecord, Long>
		implements IInsuranceOrderRecordDao {

	@Override
	public PageResult<InsuranceOrderRecord> findInsurancePageByCondition(String userName,Integer businessNum, String orderStartDate, String orderEndDate,
			Integer orderStatus, Integer pageSize, Integer pageNo) {
		StringBuffer jpql = new StringBuffer("from InsuranceOrderRecord ri WHERE 1=1");		
		StringBuffer sql = new StringBuffer("SELECT COUNT(*) as COUNT FROM r_insurance_order_record r  WHERE 1=1");
		if (userName!=null) {
			jpql.append(" and ri.userName =:userName");
			 sql.append(" AND r.user_name =:userName");
		}
		if (businessNum!=null) {
			jpql.append(" AND ri.businessNum =:businessNum");
			 sql.append(" AND r.business_num =:businessNum");
		}

		if (orderStartDate != null) {
			jpql.append(" AND ri.buyingDate >=:orderStartDate");
			 sql.append(" AND r.buying_date >=:orderStartDate");
		}
		if (orderEndDate != null) {
			jpql.append(" AND ri.buyingDate <=:orderEndDate");
			 sql.append(" AND  r.buying_date <=:orderEndDate");
		}
		if (orderStatus != null) {
			jpql.append(" and ri.status = :orderStatus");
			sql.append(" AND  r.status = :orderStatus");
		}
		jpql.append(" order by ri.createDate desc ");
		TypedQuery<InsuranceOrderRecord> query = entityManager.createQuery(jpql.toString(), InsuranceOrderRecord.class);
		Query queryCount = this.entityManager.createNativeQuery(sql.toString());

		if (userName!=null) {
			 query.setParameter("userName", userName);
			 queryCount.setParameter("userName", userName);
		}
		
		if (businessNum!=null) {
			 query.setParameter("businessNum", businessNum);
			 queryCount.setParameter("businessNum", businessNum);
		}
		
		if (orderStartDate != null) {
			Date orderSDate=new Date();
			try {orderSDate=DateUtils.parseDate(orderStartDate, new String[]{"yyyy-MM-dd HH:mm:ss"});} catch (Exception e) {
				e.printStackTrace();
			}
			query.setParameter("orderStartDate", orderSDate);
			queryCount.setParameter("orderStartDate", orderStartDate);
		}
		if (orderEndDate != null) {
			Date orderEDate=new Date();
			try {orderEDate=DateUtils.parseDate(orderEndDate, new String[]{"yyyy-MM-dd HH:mm:ss"});} catch (Exception e) {
				e.printStackTrace();
			}
			query.setParameter("orderEndDate", orderEDate);
			queryCount.setParameter("orderEndDate", orderEndDate);
		}
		if (orderStatus != null) {
			query.setParameter("orderStatus", orderStatus.byteValue());
			queryCount.setParameter("orderStatus", orderStatus);
		}
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo - 1) * pageSize);
		List<InsuranceOrderRecord> results = query.getResultList();

		int totalNum = 0;
		try {
			totalNum = ((BigInteger) queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PageResult<InsuranceOrderRecord>(results, totalNum);

	}

	@Override
	public InsuranceOrderRecord findInsuranceRecord(String orderNo, Integer businessNum) {
		String hql = "from InsuranceOrderRecord i where i.orderNo = :orderNo";
		if (businessNum != null && businessNum > 0) {
			hql = hql.concat(" and i.businessNum=:businessNum");
		}
		TypedQuery<InsuranceOrderRecord> query = entityManager.createQuery(hql, InsuranceOrderRecord.class);
		query.setParameter("orderNo", orderNo);
		if (businessNum != null && businessNum > 0) {
			query.setParameter("businessNum", businessNum);
		}
		List<InsuranceOrderRecord> records = query.getResultList();
		if (CollectionUtils.isEmpty(records)) {
			return null;
		} else {
			return records.get(0);
		}
	}

	@Override
	public InsuranceOrderRecord findByOrderNo(String orderNo) {
		String hql = "from InsuranceOrderRecord where orderNo = :orderNo";
		TypedQuery<InsuranceOrderRecord> query = entityManager.createQuery(hql, InsuranceOrderRecord.class);
		query.setParameter("orderNo", orderNo);
		List<InsuranceOrderRecord> insurances = query.getResultList();
		return CollectionUtils.isEmpty(insurances) ? null : insurances.get(0);
	}



	@Override
	public InsuranceOrderRecord findByOutTradeNo(String outOrderNo) {
		String hql = "from InsuranceOrderRecord i where i.outOrderNo = :outOrderNo";		
		TypedQuery<InsuranceOrderRecord> query = entityManager.createQuery(hql, InsuranceOrderRecord.class);
		query.setParameter("outOrderNo", outOrderNo);		
		List<InsuranceOrderRecord> records=query.getResultList();
		if(CollectionUtils.isEmpty(records)){
			return null;
		}else{
			return records.get(0);
		}
	}

	@Override
	public List<InsuranceOrderRecord> findUserOrders(String userName) {
		String hql = "from InsuranceOrderRecord where userName = :userName";
		TypedQuery<InsuranceOrderRecord> query = entityManager.createQuery(hql, InsuranceOrderRecord.class);
		query.setParameter("userName", userName);
		return query.getResultList();		
	}
}
