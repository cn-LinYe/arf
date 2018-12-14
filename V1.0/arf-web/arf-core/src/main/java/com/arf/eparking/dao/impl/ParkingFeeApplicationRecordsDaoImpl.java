package com.arf.eparking.dao.impl;

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
import com.arf.eparking.dao.ParkingFeeApplicationRecordsDao;
import com.arf.eparking.entity.ParkingFeeApplicationRecords;
import com.arf.eparking.entity.ParkingFeeApplicationRecords.ApplyStatus;

@Repository("parkingFeeApplicationRecordsDao")
public class ParkingFeeApplicationRecordsDaoImpl extends BaseDaoImpl<ParkingFeeApplicationRecords, Long> implements ParkingFeeApplicationRecordsDao{

	@Override
	public PageResult<Map<String, Object>> findByBusinessNumAndApplyStatus(String businessNum, ApplyStatus applyStatus,
			Integer pageSize, Integer pageNo) {
		StringBuffer sql = new StringBuffer("SELECT ");
		sql.append("p.id id,p.create_date createDate,p.modify_date modifyDate,p.version version,p.apply_date applyDate,p.apply_status applyStatus,");
		sql.append("p.arrive_time arriveTime,p.business_num businessNum,p.community_number communityNumber, p.duration duration,");
		sql.append("p.discount_price discountPrice,p.fee fee,p.fee_pay_status feePayStatus,p.leave_time leaveTime,p.license license,p.name name,");
		sql.append("p.operating_date operatingDate,p.order_no orderNo,p.pay_type payType,p.to_pay toPay,p.to_pay_price toPayPrice,");
		sql.append("p.to_pay_user toPayUser,p.user_name userName,p.open_id openId,p.pay_date payDate");
		sql.append(" from r_parking_fee_application_records p where 1=1 and p.business_num =:businessNum");
		
		StringBuffer sqlCount = new StringBuffer("SELECT count(1)");
		sqlCount.append(" from r_parking_fee_application_records p where 1=1 and p.business_num =:businessNum");

		sql.append(" and p.apply_status <> 3");//屏蔽掉无效记录
		sqlCount.append(" and p.apply_status <> 3");//屏蔽掉无效记录
		
		if(applyStatus!=null){
			sql.append(" and p.apply_status=:applyStatus");
			sqlCount.append(" and p.apply_status=:applyStatus");
		}
		Query query =entityManager.createNativeQuery(sql.toString());
		Query queryConut =entityManager.createNativeQuery(sqlCount.toString());
		
		query.setParameter("businessNum", businessNum);
		queryConut.setParameter("businessNum", businessNum);
		
		if(applyStatus!=null){
			query.setParameter("applyStatus", applyStatus.ordinal());
			queryConut.setParameter("applyStatus", applyStatus.ordinal());
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
	@Override
	public PageResult<Map<String, Object>> findByBusinessNumAndApplyStatus(String businessNum, ApplyStatus applyStatus,
			Integer pageSize, Integer pageNo,String startTime,String endTime) {
		StringBuffer sql = new StringBuffer("SELECT ");
		sql.append("p.id id,p.create_date createDate,p.modify_date modifyDate,p.version version,p.apply_date applyDate,p.apply_status applyStatus,");
		sql.append("p.arrive_time arriveTime,p.business_num businessNum,p.community_number communityNumber, p.duration duration,");
		sql.append("p.discount_price discountPrice,p.fee fee,p.fee_pay_status feePayStatus,p.leave_time leaveTime,p.license license,p.name name,");
		sql.append("p.operating_date operatingDate,p.order_no orderNo,p.pay_type payType,p.to_pay toPay,p.to_pay_price toPayPrice,");
		sql.append("p.to_pay_user toPayUser,p.user_name userName,p.open_id openId,p.pay_date payDate");
		sql.append(" from r_parking_fee_application_records p where 1=1 and p.business_num =:businessNum");
		
		StringBuffer sqlCount = new StringBuffer("SELECT count(1)");
		sqlCount.append(" from r_parking_fee_application_records p where 1=1 and p.business_num =:businessNum");
		
		sql.append(" and p.apply_status <> 3");//屏蔽掉无效记录
		sqlCount.append(" and p.apply_status <> 3");//屏蔽掉无效记录
		
		if(applyStatus!=null){
			sql.append(" and p.apply_status=:applyStatus");
			sqlCount.append(" and p.apply_status=:applyStatus");
		}else{//如果前端不传输审核状态就查询拒绝及申请通过的
			sql.append(" and p.apply_status > 0");
			sqlCount.append(" and p.apply_status > 0");
		}
		if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
			sql.append(" and p.create_date >= :startTime and p.create_date <= :endTime");
			sqlCount.append(" and p.create_date >= :startTime and p.create_date <= :endTime");
		}
		sql.append(" order by create_date desc");
		Query query =entityManager.createNativeQuery(sql.toString());
		Query queryConut =entityManager.createNativeQuery(sqlCount.toString());
		
		query.setParameter("businessNum", businessNum);
		queryConut.setParameter("businessNum", businessNum);
		
		if(applyStatus!=null){
			query.setParameter("applyStatus", applyStatus.ordinal());
			queryConut.setParameter("applyStatus", applyStatus.ordinal());
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
	
	@Override
	public List<Map<String, Object>> statisticsBusinessNumOrder(String businessNum,ApplyStatus applyStatus,String startTime,String endTime){
		StringBuffer sql = new StringBuffer("SELECT SUM(if(p.to_pay = 2, p.to_pay_price, 0)) toPayPrice,COUNT(1) agreeItem,SUM(p.discount_price) discountPrice");
		sql.append(" from r_parking_fee_application_records p where 1=1 and p.business_num =:businessNum");
		sql.append(" and p.is_use_server = 1");//用户已经支付停车费
		if(applyStatus!=null){
			sql.append(" and p.apply_status=:applyStatus");
		}
		if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
			sql.append(" and p.create_date >= :startTime and p.create_date <= :endTime");
		}
		Query query =entityManager.createNativeQuery(sql.toString());		
		query.setParameter("businessNum", businessNum);		
		if(applyStatus!=null){
			query.setParameter("applyStatus", applyStatus.ordinal());
		}
		if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
			query.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);
		}		
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}

	@Override
	public List<ParkingFeeApplicationRecords> findByBusinessNumAndOrderNo(String businessNum, String orderNo) {
		StringBuffer sql = new StringBuffer("from ParkingFeeApplicationRecords where 1=1 ");
		if(StringUtils.isNotBlank(businessNum)){
			sql.append(" and businessNum =:businessNum");
		}
		if(StringUtils.isNotBlank(orderNo)){
			sql.append(" and orderNo =:orderNo");
		}
		TypedQuery<ParkingFeeApplicationRecords> query =entityManager.createQuery(sql.toString(),ParkingFeeApplicationRecords.class);
		if(StringUtils.isNotBlank(businessNum)){
			query.setParameter("businessNum", businessNum);
		}
		if(StringUtils.isNotBlank(orderNo)){
			query.setParameter("orderNo", orderNo);
		}
		List<ParkingFeeApplicationRecords> recordsList = query.getResultList();
		return recordsList;
	}

	@Override
	public List<ParkingFeeApplicationRecords> findByBusinessNumAndLicense(String businessNum, String communityNumber,
			String license, Date arriveTime) {
		StringBuffer sql = new StringBuffer("from ParkingFeeApplicationRecords where 1=1 ");
		if(StringUtils.isNotBlank(businessNum)){
			sql.append(" and businessNum =:businessNum");
		}
		if(StringUtils.isNotBlank(communityNumber)){
			sql.append(" and communityNumber =:communityNumber");
		}
		if(StringUtils.isNotBlank(license)){
			sql.append(" and license =:license");
		}
		if(arriveTime!=null){
			sql.append(" and arriveTime =:arriveTime");
		}
		TypedQuery<ParkingFeeApplicationRecords> query =entityManager.createQuery(sql.toString(),ParkingFeeApplicationRecords.class);
		if(StringUtils.isNotBlank(businessNum)){
			query.setParameter("businessNum", businessNum);
		}
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber", communityNumber);
		}
		if(StringUtils.isNotBlank(businessNum)){
			query.setParameter("license", license);
		}
		if(arriveTime!=null){
			query.setParameter("arriveTime", arriveTime);
		}
		List<ParkingFeeApplicationRecords> recordsList = query.getResultList();
		return recordsList;
	}

	@Override
	public PageResult<Map<String, Object>> findByOpenId(String openId, Integer pageSize,Integer pageNo) {
		StringBuffer sql = new StringBuffer("SELECT ");
		sql.append("p.id id,p.create_date createDate,p.modify_date modifyDate,p.version version,p.apply_date applyDate,p.apply_status applyStatus,");
		sql.append("p.arrive_time arriveTime,p.business_num businessNum,p.community_number communityNumber, p.duration duration,");
		sql.append("p.discount_price discountPrice,p.fee fee,p.fee_pay_status feePayStatus,p.leave_time leaveTime,p.license license,p.name name,");
		sql.append("p.operating_date operatingDate,p.order_no orderNo,p.pay_type payType,p.to_pay toPay,p.to_pay_price toPayPrice,");
		sql.append("p.to_pay_user toPayUser,p.user_name userName,p.open_id openId,c.community_name communityName");
		sql.append(" from r_parking_fee_application_records p ");
		sql.append(" left join community c on c.community_number=p.community_number");
		sql.append(" where 1=1 and p.open_id =:openId and p.apply_status <>:applyStatus");
		
		StringBuffer sqlCount = new StringBuffer("SELECT count(1)");
		sqlCount.append(" from r_parking_fee_application_records p where 1=1 and p.open_id =:openId and p.apply_status <>:applyStatus");
		
		Query query =entityManager.createNativeQuery(sql.toString());
		Query queryConut =entityManager.createNativeQuery(sqlCount.toString());
		
		query.setParameter("openId", openId);
		queryConut.setParameter("openId", openId);
		
		query.setParameter("applyStatus", ParkingFeeApplicationRecords.ApplyStatus.Failure.ordinal());
		queryConut.setParameter("applyStatus", ParkingFeeApplicationRecords.ApplyStatus.Failure.ordinal());
		
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
