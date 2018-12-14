package com.arf.carbright.dao.impl;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.carbright.dao.CarWashingRecordDao;
import com.arf.carbright.entity.CarWashingRecord;
import com.arf.carbright.entity.CarWashingRecord.OrderType;
import com.arf.carbright.entity.CarWashingRecord.Status;
import com.arf.carbright.entity.RPackageRecord.FeePayStatus;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("carWashingRecordDao")
public class CarWashingRecordDaoImpl extends BaseDaoImpl<CarWashingRecord, Long> implements CarWashingRecordDao{

	@Override
	public PageResult<CarWashingRecord> findMyOrders(String userName, Status status, int pageSize, int pageNo) {
		return findPackageUsedRecords(userName,null, status, pageSize, pageNo);
	}

	@Override
	public CarWashingRecord findByOrderNo(String orderNo) {
		String hql = "from CarWashingRecord r where r.id = :orderNo ";
		Long orderId=null;
		if(StringUtils.isNotBlank(orderNo)){
			orderId=Long.valueOf(orderNo);
		}
		List<CarWashingRecord> records = this.entityManager.createQuery(hql, CarWashingRecord.class).setParameter("orderNo", orderId).getResultList();
		if(CollectionUtils.isEmpty(records)){
			return null;
		}else{
			return records.get(0);
		}
	}

	
	@SuppressWarnings("unchecked")
	public PageResult<CarWashingRecord> findMerchantOrders(long businessId,CarWashingRecord.Status status,int pageSize, int pageNo){
		StringBuilder sqlSb = new StringBuilder("select * from r_car_washing_record r where r.business_id = :businessId ");
		StringBuilder sqlCount = new StringBuilder("select count(id) as COUNT from r_car_washing_record r where r.business_id = :businessId ");
		if(status != null){
			if(status != null && status.ordinal() == CarWashingRecord.Status.Merchant_Completed.ordinal()){
				sqlSb.append(" and r.status in (:status) ");
				sqlCount.append(" and r.status in (:status) ");
			}else if(status != null && status.ordinal() == CarWashingRecord.Status.Confirmed.ordinal()){
				sqlSb.append(" and r.status in (:status) ");
				sqlCount.append(" and r.status in (:status) ");
			}else{
				sqlSb.append(" and r.status = :status ");
				sqlCount.append(" and r.status = :status ");
			}
		}
		sqlSb.append(" order by r.create_date DESC ");
		Query query = this.entityManager.createNativeQuery(sqlSb.toString(), CarWashingRecord.class);
		Query queryCount = this.entityManager.createNativeQuery(sqlCount.toString());
		query.setParameter("businessId", businessId);
		queryCount.setParameter("businessId", businessId);
		
		if(status != null){
			if(status != null && status.ordinal() == CarWashingRecord.Status.Merchant_Completed.ordinal()){
				List<Integer> statuses =  Arrays.asList(new Integer[]{
						CarWashingRecord.Status.Merchant_Completed.ordinal(),
						CarWashingRecord.Status.Member_Completed.ordinal(),
						CarWashingRecord.Status.Member_Evaluate.ordinal()
						});
				query.setParameter("status", statuses);
				queryCount.setParameter("status", statuses);
			}else if(status != null && status.ordinal() == CarWashingRecord.Status.Confirmed.ordinal()){
				List<Integer> statuses =  Arrays.asList(new Integer[]{
						CarWashingRecord.Status.Confirmed.ordinal(),
						CarWashingRecord.Status.User_stored_Key.ordinal(),
						});
				query.setParameter("status", statuses);
				queryCount.setParameter("status", statuses);
			}else{
				query.setParameter("status", status.ordinal());
				queryCount.setParameter("status", status.ordinal());
			}
		}
		
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo-1) * pageSize);
		List<CarWashingRecord> list = query.getResultList();
		
		int count = 0; 
		try {
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new PageResult<CarWashingRecord>(list,count);
	}

	@Override
	public PageResult<CarWashingRecord> findPackageUsedRecords(String userName, Long packageRecordId, Status status, int pageSize,
			int pageNo) {
		StringBuilder hql = new StringBuilder("from CarWashingRecord r where r.userName = :userName ");
		//查询记录总数
		StringBuilder sql = new StringBuilder("select count(id) as COUNT from r_car_washing_record r where r.user_name = :userName ");
		if(status != null){
			if(status == Status.Merchant_Completed){
				hql.append(" and r.status in(:status) ");
				sql.append(" and r.status in(:status) ");
			}else{
				hql.append(" and r.status = :status ");
				sql.append(" and r.status = :status ");
			}
		}
		if(packageRecordId != null){
			hql.append(" and r.packageRecordId = :packageRecordId ");
			sql.append(" and r.package_record_id = :packageRecordId ");
		}
		
		hql.append(" order by createDate desc");
		
		TypedQuery<CarWashingRecord>  query = this.entityManager.createQuery(hql.toString(), CarWashingRecord.class);
		query.setParameter("userName", userName);
		
		Query queryCount = this.entityManager.createNativeQuery(sql.toString());
		queryCount.setParameter("userName", userName);
		
		if(status != null){
			if(status == Status.Merchant_Completed){
				List<Integer> statuses =  Arrays.asList(new Integer[]{
						CarWashingRecord.Status.Merchant_Completed.ordinal(),
						CarWashingRecord.Status.Member_Completed.ordinal(),
						CarWashingRecord.Status.Member_Evaluate.ordinal()
						});
				query.setParameter("status", statuses);
				queryCount.setParameter("status", statuses);
			}else{
				query.setParameter("status", status.ordinal());
				queryCount.setParameter("status", status.ordinal());
			}
		}
		if(packageRecordId != null){
			query.setParameter("packageRecordId", packageRecordId);
			queryCount.setParameter("packageRecordId", packageRecordId);
		}
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo-1) * pageSize);
		List<CarWashingRecord> list = query.getResultList();
		
		int count = 0; 
		try {
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageResult<CarWashingRecord> pageResult = new PageResult<>(list,count);
		pageResult.setList(list);
		return pageResult;
	}
	
	public List<CarWashingRecord> findOrderByUserName(String userName,String status){
		StringBuffer hql = new StringBuffer("from CarWashingRecord r where userName = :userName ");
		if("FINISH".equals(status)){
			hql.append(" and (status = :status1 or status = :status2 or status = :status3)");
		}else if("ONGOING".equals(status)){
			hql.append(" and (status = :status1 or status = :status2)");
		}
		hql.append("  order by r.bookTime desc ");
		TypedQuery<CarWashingRecord> query = this.entityManager.createQuery(hql.toString(), CarWashingRecord.class);
		query.setParameter("userName", userName);
		if("FINISH".equals(status)){
			query.setParameter("status1", CarWashingRecord.Status.Member_Evaluate.ordinal());
			query.setParameter("status2", CarWashingRecord.Status.Member_Completed.ordinal());
			query.setParameter("status3", CarWashingRecord.Status.Member_Evaluate.ordinal());
		}else if("ONGOING".equals(status)){
			query.setParameter("status1", CarWashingRecord.Status.User_stored_Key.ordinal());
			query.setParameter("status2", CarWashingRecord.Status.Servicing.ordinal());
		}
		List<CarWashingRecord> records = null;
		try{
			records = query.getResultList();
		} catch(Exception e){
			e.printStackTrace();
		}
		return records;
	}
	
	@Override
	public int orderMerchantAnalysis(Long businessId, Integer status) {
		StringBuffer sql = new StringBuffer("SELECT COUNT(1) from CarWashingRecord carWashingRecord where carWashingRecord.businessId = :businessId");
		if(status != null){
			if(status == CarWashingRecord.Status.Merchant_Completed.ordinal()){
				sql.append(" and (carWashingRecord.status = :Merchant_Completed or carWashingRecord.status = :Member_Completed or carWashingRecord.status = :Member_Evaluate)");
			}else if(status == CarWashingRecord.Status.Confirmed.ordinal()){ //待服务 = 已确认 + 已存钥匙
				sql.append(" and (carWashingRecord.status = :Confirmed or carWashingRecord.status = :User_stored_Key)");
			}else{
				sql.append(" and carWashingRecord.status = :status");
			}
		}
		
		sql.append(" and carWashingRecord.feePayStatus = "+ FeePayStatus.pay_success.ordinal() +" ");
		TypedQuery<Long> queryCount = entityManager.createQuery(sql.toString(),Long.class);
		queryCount.setParameter("businessId", businessId);
		
		if(status != null){
			if(status == CarWashingRecord.Status.Merchant_Completed.ordinal()){
				queryCount.setParameter("Merchant_Completed", CarWashingRecord.Status.Merchant_Completed.ordinal());
				queryCount.setParameter("Member_Completed", CarWashingRecord.Status.Member_Completed.ordinal());
				queryCount.setParameter("Member_Evaluate", CarWashingRecord.Status.Member_Evaluate.ordinal());
			}else if(status == CarWashingRecord.Status.Confirmed.ordinal()){
				queryCount.setParameter("Confirmed", CarWashingRecord.Status.Confirmed.ordinal());
				queryCount.setParameter("User_stored_Key", CarWashingRecord.Status.User_stored_Key.ordinal());
			}else{
				queryCount.setParameter("status", status);
			}
		}
		
		long count = 0;
		try {
			count = queryCount.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.valueOf("" + count);
	}

	@Override
	public List<CarWashingRecord> findOrderTimeOut(Status status, OrderType orderType, Date rangeDate, int minutes) {
		String hql = "select * from r_car_washing_record r where r.fee_pay_status=:fee_pay_status and r.status =:status and r.order_type=:order_type and r.create_Date>:rangeDate and timestampdiff(MINUTE,r.create_Date,now())>:minutes";
		@SuppressWarnings("unchecked")
		List<CarWashingRecord> records = this.entityManager.createNativeQuery(hql, CarWashingRecord.class)
		.setParameter("status", status.ordinal())
		.setParameter("fee_pay_status",FeePayStatus.pay_success.ordinal())
		.setParameter("order_type", orderType.ordinal())
		.setParameter("rangeDate", rangeDate)
		.setParameter("minutes", minutes)
		.getResultList();
		return records;
	}

	
	@Override
	public List<CarWashingRecord> findByStatus(Integer[] status,Long businessId) {
		if (status==null||status.length<=0) {
			return null;
		}
		StringBuffer hql = new StringBuffer("select t from CarWashingRecord t where t.status in (:status)");
		hql.append("  and t.businessId=:businessId  ");
		TypedQuery<CarWashingRecord> query =entityManager.createQuery(hql.toString(), CarWashingRecord.class)
				.setParameter("status", Arrays.asList(status))
				.setParameter("businessId",businessId);
		return query.getResultList();
	}

	@Override
	public CarWashingRecord findByOrderNum(String orderNo) {
		String hql = "from CarWashingRecord r where r.orderNo = :orderNo ";
		List<CarWashingRecord> records = this.entityManager.createQuery(hql, CarWashingRecord.class).setParameter("orderNo", orderNo).getResultList();
		if(CollectionUtils.isEmpty(records)){
			return null;
		}else{
			return records.get(0);
		}
	}
}
