package com.arf.express.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.express.dao.IExpressDeliveryRecordDao;
import com.arf.express.entity.ExpressDeliveryRecord;
import com.arf.express.entity.ExpressDeliveryRecord.Status;

@Repository("expressDeliveryRecordDaoImpl")
public class ExpressDeliveryRecordDaoImpl extends BaseDaoImpl<ExpressDeliveryRecord, Long> implements IExpressDeliveryRecordDao{

	@Override
	public PageResult<Map<String, Object>> findExpressOrder(String userName, Integer pageSize, Integer pageNo,Integer status,Integer businessNum) {
		StringBuffer sql = new StringBuffer(" SELECT  ");
		sql.append(" e.user_name as userName,e.full_name as fullName,e.express_num as expressNum, ");
		sql.append(" e.express_name AS expressName ,e.order_no AS orderNo ,e.status AS status, ");
		sql.append("  e.collection_date AS collectionDate, ");
		sql.append(" e.receive_date  AS receiveDate , e.evaluation_date AS evaluationDate ,e.evaluation as evaluation ,e.evaluation_score AS evaluationScore, ");
		sql.append(" e.business_num AS businessNum  ,p.business_name AS businessName ,p.contact_phone as contactPhone ,p.address AS address, ");
		sql.append(" p.business_pic AS businessPic ,p.business_hours AS businessHours,p.business_description AS businessDescription ,p.lat AS lat ,p.lng AS lng ");
		sql.append(" FROM e_express_delivery_record  e");
		sql.append(" LEFT JOIN p_business p ON p.business_num =e.business_num");
		sql.append(" where 1=1 ");
		
		StringBuffer sqlCount = new StringBuffer(" SELECT Count(*)  ");
		sqlCount.append(" FROM e_express_delivery_record  e");
		sqlCount.append(" LEFT JOIN p_business p ON p.business_num =e.business_num");
		sqlCount.append(" where 1=1 ");
		if (userName!=null) {
			sql.append(" AND e.user_name =:userName ");
			sqlCount.append(" AND e.user_name =:userName ");
		}
		
		
		if (businessNum!=null) {
			sql.append(" AND e.business_num =:businessNum ");
			sqlCount.append(" AND e.business_num =:businessNum ");
		}
		if (status!=null) {
			if (status==0) {
				sql.append(" AND e.status =:status ");
				sqlCount.append(" AND e.status =:status ");
			}else{
				sql.append(" AND e.status =:status1 ");
				sqlCount.append(" AND e.status =:status1 ");
				sql.append(" AND e.status =:status2 ");
				sqlCount.append(" AND e.status =:status2 ");
				sql.append(" AND e.status =:status3 ");
				sqlCount.append(" AND e.status =:status3 ");
			}
			
		}
		Query query =entityManager.createNativeQuery(sql.toString());
		Query queryCount =entityManager.createNativeQuery(sqlCount.toString());
		if (userName!=null) {
			 query.setParameter("userName", userName);
			 queryCount.setParameter("userName", userName);
		}
		
		
		if (businessNum!=null) {
			query.setParameter("businessNum", businessNum);
			queryCount.setParameter("businessNum", businessNum);
		}
		if (status!=null) {
			if (status==0) {
				query.setParameter("status", status);
				 queryCount.setParameter("status", status);
			}
			else {
				Integer status1 = Status.Hasreceive.ordinal();
				Integer status2 = Status.UserComment.ordinal();
				Integer status3 = Status.ErrorInput.ordinal();
				query.setParameter("status1", status1);
				queryCount.setParameter("status1", status1);
				query.setParameter("status2", status2);
				queryCount.setParameter("status2", status2);
				query.setParameter("status3", status3);
				queryCount.setParameter("status3", status3);
			}
		}
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo - 1) * pageSize);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> pbBusinesses =query.getResultList();
		
		
		int count = 0; 
		try {
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageResult<Map<String,Object>> pageResult = new PageResult<Map<String,Object>>(pbBusinesses,count);
		return pageResult;
	}

	@Override
	public List<ExpressDeliveryRecord> findByUserNameBusinessNum(String userName, Integer businessNum) {
		StringBuffer sb =new StringBuffer("from ExpressDeliveryRecord where 1=1");
		if (userName!=null) {
			sb.append(" and userName = :userName");
		}
		if (businessNum!=null) {
			sb.append(" and businessNum = :businessNum");
		}
		sb.append(" and status = :status");
		TypedQuery<ExpressDeliveryRecord> query = entityManager.createQuery(sb.toString(), ExpressDeliveryRecord.class);
		if (userName!=null) {
			query.setParameter("userName", userName);
		}
		if (businessNum!=null) {
			query.setParameter("businessNum", businessNum);
		}
		query.setParameter("status", Status.Unclaimed.ordinal());
		return query.getResultList();
	}

	@Override
	public ExpressDeliveryRecord findByUserNameOrderNo(String userName, String orderNo) {
		StringBuffer sb =new StringBuffer("from ExpressDeliveryRecord where 1=1");
		if (userName!=null) {
			sb.append(" and userName = :userName");
		}
		if (orderNo!=null) {
			sb.append(" and orderNo = :orderNo");
		}
		TypedQuery<ExpressDeliveryRecord> query = entityManager.createQuery(sb.toString(), ExpressDeliveryRecord.class);
		if (userName!=null) {
			query.setParameter("userName", userName);
		}
		if (orderNo!=null) {
			query.setParameter("orderNo", orderNo);
		}
		List<ExpressDeliveryRecord>  expressDeliveryRecords=query.getResultList();
		return (CollectionUtils.isEmpty(expressDeliveryRecords)?null:expressDeliveryRecords.get(0));
	}
}













