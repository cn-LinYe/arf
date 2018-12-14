package com.arf.laundry.dao.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.laundry.dao.ILaundryOrderDao;
import com.arf.laundry.dto.MerchantOrderDto;
import com.arf.laundry.entity.LaundryOrder;
import com.arf.laundry.search.LaundryOrderCondition;

@Repository("laundryOrderDaoImpl")
public class LaundryOrderDaoImpl extends BaseDaoImpl<LaundryOrder, Long> implements ILaundryOrderDao {

	@Override
	public LaundryOrder findByOutTradeNo(String outTradeNo){
		try{
			String hql = "from LaundryOrder where outTradeNo = :outTradeNo";
			TypedQuery<LaundryOrder> query = entityManager.createQuery(hql,LaundryOrder.class);
			query.setParameter("outTradeNo", outTradeNo);
			return query.getSingleResult();
		} catch(NoResultException e){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public MerchantOrderDto findMerchantOrderByOutTradeNo(String outTradeNo){
		/*String sql = " select lo.id,lo.total_fee as totalFee,lo.status,lo.out_trade_no as outTradeNo,lo.business_num as businessNum,"
				+ " lo.total_count as totalCount,lo.business_confirm_date as businessConfirmDate,lo.business_taken_date as businessTakenDate,"
				+ " lo.business_complete_date as businessCompleteDate,lo.fee_pay_status as feePayStatus,lo.paid_date as paidDate,lo.area_code as areaCode,"
				+ " lo.user_real_name as userRealName,lo.address,lo.taking_date as takingDate,lo.user_phone as userPhone,lo.remark,lo.complaint_status as complaintStatus,"
				+ " lo.user_name as userName,lo.pay_type as payType,lo.refund_status as refundStatus,lo.reject_reason as rejectReason,mb.photo as userAvatar "
				+ " from p_laundry_order lo "
				+ " left join xx_member mb on lo.user_name = mb.username "
				+ " where lo.out_trade_no = :outTradeNo ";*/
		String sql = " select lo.id, lo.address, lo.area_code as areaCode, lo.business_complete_date as businessCompleteDate,"
				+ "	lo.business_confirm_date as businessConfirmDate,lo.business_num as businessNum,lo.business_taken_date as businessTakenDate,"
				+ "	lo.complaint_status as complaintStatus, lo.fee_pay_status as feePayStatus, lo.out_trade_no as outTradeNo,"
				+ "	lo.paid_date as paidDate, lo.pay_type as payType, lo.refund_status as refundStatus, lo.remark, lo.status,"
				+ "	lo.taking_date as takingDate, lo.total_count as totalCount, lo.total_fee as totalFee, lo.user_name as userName,"
				+ " lo.user_phone as userPhone, lo.user_real_name as userRealName, lo.cancel_date as cancelDate, lo.finished_date as finishedDate,"
				+ " lo.reject_reason as rejectReason,mb.photo as userAvatar "
				+ " from p_laundry_order lo "
				+ " left join xx_member mb on lo.user_name = mb.username "
				+ " where lo.out_trade_no = :outTradeNo ";
		Query query = super.entityManager.createNativeQuery(sql);
		query.setParameter("outTradeNo", outTradeNo);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> rows = query.getResultList();
		Map<String,Object> row = CollectionUtils.isEmpty(rows)?null:rows.get(0);
		return JSON.toJavaObject(new JSONObject(row), MerchantOrderDto.class);
	}
	
	@Override
	public PageResult<Map<String,Object>> findListByCondition(LaundryOrderCondition condition){
		PageResult<Map<String,Object>> pageResult = new PageResult<Map<String,Object>>();
		int pageSize = condition.getPageSize();
		Integer businessNum = condition.getBusinessNum();
		Integer status = condition.getStatus();
		StringBuffer sbCount = new StringBuffer("select count(id) as COUNT from p_laundry_order lo where 1=1 and lo.business_num = :businessNum");
		StringBuffer sb = new StringBuffer(" select lo.id, lo.address, lo.area_code as areaCode, lo.business_complete_date as businessCompleteDate,"
				+ "	lo.business_confirm_date as businessConfirmDate,lo.business_num as businessNum,lo.business_taken_date as businessTakenDate,"
				+ "	lo.complaint_status as complaintStatus, lo.fee_pay_status as feePayStatus, lo.out_trade_no as outTradeNo,"
				+ "	lo.paid_date as paidDate, lo.pay_type as payType, lo.refund_status as refundStatus, lo.remark, lo.status,"
				+ "	lo.taking_date as takingDate, lo.total_count as totalCount, lo.total_fee as totalFee, lo.user_name as userName,"
				+ " lo.user_phone as userPhone, lo.user_real_name as userRealName, lo.cancel_date as cancelDate, lo.finished_date as finishedDate,"
				+ " lo.reject_reason as rejectReason,mb.photo as userAvatar "
				+ " from p_laundry_order lo "
				+ " left join xx_member mb on lo.user_name = mb.username "
				+ " where 1=1 and lo.business_num = :businessNum");
		if(status != null){
			sbCount.append(" and lo.status = :status");
			sb.append(" and lo.status = :status");
		}
		sbCount.append(" order by lo.create_date desc");
		sb.append(" order by lo.create_date desc");
		Query queryCount = this.entityManager.createNativeQuery(sbCount.toString());
		Query typedQuery = this.entityManager.createNativeQuery(sb.toString());
		queryCount.setParameter("businessNum", businessNum);
		typedQuery.setParameter("businessNum", businessNum);
		if(status != null){
			queryCount.setParameter("status",status);
			typedQuery.setParameter("status",status);
		}
		int count = 0;
		try{
			count = ((BigInteger)queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//查询所有
		pageResult.setTotalNum(count);
		//分页查询
		if(condition.isUsePage()){
			typedQuery.setFirstResult(condition.getStartRowNum());
			typedQuery.setMaxResults(pageSize);
		}
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> rows = typedQuery.getResultList();
		pageResult.setList(rows);
		return pageResult;
	}
	
	@Override
	public void autoReceivedOrder(Integer delayDays){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.get(Calendar.DATE)-delayDays);
		String hql = "update LaundryOrder set status = 6,finishedDate = now() "
				+ " where status = 5 and complaintStatus = 0 and businessCompleteDate < :date";
		Query query = entityManager.createQuery(hql);
		query.setParameter("date", cal.getTime());
		query.executeUpdate();
	}
}
