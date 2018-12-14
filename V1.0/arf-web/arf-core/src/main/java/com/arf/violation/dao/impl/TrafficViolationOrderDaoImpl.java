package com.arf.violation.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.violation.dao.ITrafficViolationOrderDao;
import com.arf.violation.entity.TrafficViolationOrder;
import com.arf.violation.entity.TrafficViolationOrder.Status;

@Repository("trafficViolationOrderDao")
public class TrafficViolationOrderDaoImpl extends BaseDaoImpl<TrafficViolationOrder, Long> implements ITrafficViolationOrderDao{

	@Override
	public TrafficViolationOrder findByOrderNo(String orderNo) {
		StringBuffer sql =new StringBuffer(" from TrafficViolationOrder where orderNo=:orderNo ");
		TypedQuery<TrafficViolationOrder> query = entityManager.createQuery(sql.toString(),TrafficViolationOrder.class);
		query.setParameter("orderNo", orderNo);
		List<TrafficViolationOrder> list ;
		try {
			 list = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		
		return list.isEmpty()?null:list.get(0);
	}

	@Override
	public PageResult<Map<String,Object>> findByCondition(String outTradeNo, String userName, Integer pageSize,Integer pageNo, Status status,String businessNum,String startTime,String endTime) {
		StringBuffer sql =new StringBuffer("SELECT v.order_no orderNo,v.create_date createDate,v.status status,v.fee_pay_status feePayStatus,v.fee_pay_type feePayType,v.business_num businessNum,p.business_name businessName,p.contact_phone businessPhone,p.business_pic businessPic,");
		sql.append(" v.real_name realName,v.user_phone userPhone,v.need_invoice needInvoice,v.invoice_address invoiceAddress,v.fine_amount fineAmount,v.fine_marks fineMarks,v.service_amount serviceAmount,v.invoice_freight invoiceFreight,v.addition_service additionService,v.addition_service_fee additionServiceFee,");
		sql.append(" v.addition_service_no additionServiceNo,v.addition_service_point additionServicePoint,v.complaint_status complaintStatus,v.refund_status refundStatus,v.cancel_date cancelDate,v.fee_pay_time feePayTime,v.confirm_date confirmDate,v.finished_time finishedTime,");
		sql.append(" v.consignee_name consigneeName ,v.consignee_phone consigneePhone, v.reviewed,r.score reviewedScore,r.content reviewedContent");//
		sql.append(" FROM v_traffic_violation_order v ");
		sql.append(" LEFT JOIN p_business p ON p.business_num =v.business_num");
		sql.append(" LEFT JOIN r_service_review r ON r.order_no =v.order_no");
		
		sql.append(" where 1=1 and fee_pay_status=1");
		StringBuffer sqlConunt =new StringBuffer("select Count(*)");
		sqlConunt.append(" FROM v_traffic_violation_order v ");
		sqlConunt.append(" LEFT JOIN p_business p ON p.business_num =v.business_num");
		sqlConunt.append(" LEFT JOIN r_service_review r ON r.order_no =v.order_no");
		sqlConunt.append(" where 1=1 and fee_pay_status=1");
		
		if (StringUtils.isBlank(outTradeNo)&&status !=null) {
			
			sql.append(" and v.status=:status");
			sqlConunt.append(" and v.status=:status");
		}
		if (StringUtils.isNotBlank(outTradeNo)) {
			sql.append(" and v.outTradeNo=:outTradeNo");
			sqlConunt.append("  and v.outTradeNo=:outTradeNo");
		}
		if (StringUtils.isBlank(outTradeNo)&&StringUtils.isNotBlank(businessNum)) {
			sql.append(" AND v.business_num=:businessNum");
			sqlConunt.append(" AND v.business_num=:businessNum");
		}
		if (StringUtils.isNotBlank(userName)) {
			sql.append(" AND v.user_name=:user_name");
			sqlConunt.append(" AND v.user_name=:user_name");
		}
		if (StringUtils.isNotBlank(startTime)) {
			sql.append(" AND v.create_date>=:startTime");
			sqlConunt.append(" AND v.create_date>=:startTime");
		}
		if (StringUtils.isNotBlank(endTime)) {
			sql.append(" AND v.create_date<=:endTime");
			sqlConunt.append(" AND v.create_date<=:endTime");
		}
		sql.append(" ORDER BY v.create_date DESC");
		
		Query query=entityManager.createNativeQuery(sql.toString());
		Query queryCount =entityManager.createNativeQuery(sqlConunt.toString());
		if (StringUtils.isBlank(outTradeNo)&&status!=null) {
			query.setParameter("status", status.ordinal());
			queryCount.setParameter("status", status.ordinal());
		}
		if (StringUtils.isNotBlank(outTradeNo)) {
			query.setParameter("outTradeNo", outTradeNo);
			queryCount.setParameter("outTradeNo", outTradeNo);
		}

		if (StringUtils.isBlank(outTradeNo)&&StringUtils.isNotBlank(businessNum)) {
			query.setParameter("businessNum", businessNum);
			queryCount.setParameter("businessNum", businessNum);
		}
		if (StringUtils.isNotBlank(userName)) {
			query.setParameter("user_name", userName);
			queryCount.setParameter("user_name", userName);
		}
		
		if (StringUtils.isNotBlank(startTime)) {
			query.setParameter("startTime", startTime);
			queryCount.setParameter("startTime", startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			query.setParameter("endTime", endTime);
			queryCount.setParameter("endTime", endTime);
		}
		
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo-1)*pageSize);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> map= query.getResultList();
		int count=0;
		try {
		count=((BigInteger)queryCount.getSingleResult()).intValue();		
		} catch (Exception e) {
		}
		PageResult<Map<String, Object>> result = new PageResult<Map<String, Object>>(map,count);
		return result;
	}
	@Override
	public Integer findByStatus(Integer Status,String businessNum) {
		//查询记录总数
		StringBuffer sql = new StringBuffer("select count(*) as COUNT from v_traffic_violation_order v where v.business_num =:businessNum");
		sql.append("  and v.status =:Status");
		Query queryCount = super.entityManager.createNativeQuery(sql.toString());
		queryCount.setParameter("Status", Status);
		queryCount.setParameter("businessNum", businessNum);
		
		int count = 0;
		try{
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<TrafficViolationOrder> findByModifyDate(Date todayStartDate, Date todayEndDate,String businessNum) {
		StringBuffer hql = new StringBuffer("from TrafficViolationOrder where businessNum =:businessNum");
		hql.append(" and createDate >=:todayStartDate");
		hql.append(" and createDate <=:todayEndDate");
		hql.append(" and feePayStatus =:feePayStatus");
		TypedQuery<TrafficViolationOrder> typedQuery = super.entityManager.createQuery(hql.toString(), TrafficViolationOrder.class);
		
		typedQuery.setParameter("businessNum", businessNum);
		typedQuery.setParameter("todayStartDate", todayStartDate);
		typedQuery.setParameter("todayEndDate", todayEndDate);
		Integer feePayStatus = TrafficViolationOrder.FeePayStatus.Paid.ordinal();
		typedQuery.setParameter("feePayStatus", feePayStatus);
		
		List<TrafficViolationOrder> list = typedQuery.getResultList();
		return list;
	}

	@Override
	public TrafficViolationOrder findByAdditionServiceNo(String additionServiceNo) {
		StringBuffer sql =new StringBuffer(" from TrafficViolationOrder where additionServiceNo=:additionServiceNo ");
		TypedQuery<TrafficViolationOrder> query = entityManager.createQuery(sql.toString(),TrafficViolationOrder.class);
		query.setParameter("additionServiceNo", additionServiceNo);
		List<TrafficViolationOrder> list ;
		try {
			 list = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		
		return list.isEmpty()?null:list.get(0);
	}


}
