package com.arf.carbright.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.carbright.dao.MerchantTransactionRecordDao;
import com.arf.carbright.entity.MerchantTransactionRecord;
import com.arf.carbright.entity.MerchantTransactionRecord.RecordType;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("merchantTransactionRecordDao")
public class MerchantTransactionRecordDaoImpl extends BaseDaoImpl<MerchantTransactionRecord, Long> implements MerchantTransactionRecordDao{

	@Override
	public PageResult<Map<String, Object>> findByBusinessNumAndRecordType(String businessNum, RecordType recordType,
			Integer pageSize, Integer pageNo, String startTime, String endTime) {
		StringBuffer sql = new StringBuffer("SELECT ");
		sql.append("m.id id,m.create_date createDate,m.modify_date modifyDate,m.version version,m.record_type recordType,m.total_amount totalAmount,");
		sql.append("m.payment_amount paymentAmount,m.platform_vouchers platformVouchers,m.business_vouchers businessVouchers, m.business_income businessIncome,");
		sql.append("m.daily_deductions dailyDeductions,m.balance balance,m.record_status recordStatus,m.source_type sourceType,");
		sql.append("m.transaction_status transactionStatus,m.order_no orderNo,m.community_number communityNumber,m.business_num businessNum,m.consume_user consumeUser,");
		sql.append("m.pay_type payType,m.remark remark");
		sql.append(" from merchant_transaction_record m where 1=1 and m.business_num =:businessNum");
		
		StringBuffer sqlCount = new StringBuffer("SELECT count(1)");
		sqlCount.append(" from merchant_transaction_record m where 1=1 and m.business_num =:businessNum");
		
		if(recordType!=null&&recordType!=RecordType.AllRecord){
			sql.append(" and m.record_type = :recordType");
			sqlCount.append(" and m.record_type = :recordType");
		}
		if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
			sql.append(" and m.create_date >= :startTime and m.create_date <= :endTime");
			sqlCount.append(" and m.create_date >= :startTime and m.create_date <= :endTime");
		}
		sql.append(" order by m.create_date desc");
		Query query =entityManager.createNativeQuery(sql.toString());
		Query queryConut =entityManager.createNativeQuery(sqlCount.toString());
		
		query.setParameter("businessNum", businessNum);
		queryConut.setParameter("businessNum", businessNum);
		
		if(recordType!=null && recordType!=RecordType.AllRecord){
			query.setParameter("recordType", recordType.ordinal());
			queryConut.setParameter("recordType", recordType.ordinal());
		}
		if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
			query.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);
			queryConut.setParameter("startTime", startTime);
			queryConut.setParameter("endTime", endTime);
		}
		if(pageSize!=null&&pageNo!=null){
			query.setMaxResults(pageSize);
			query.setFirstResult((pageNo-1)*pageSize);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> map= query.getResultList();
		int count =0;
		try {
			count =((BigInteger)queryConut.getSingleResult()).intValue();			
		} catch (Exception e) {
		}
		PageResult<Map<String,Object>> result = new PageResult<Map<String, Object>>(map,count);
		return result;
	}

}
