package com.arf.carbright.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.arf.base.PageResult;
import com.arf.carbright.dao.RPackageRecordDao;
import com.arf.carbright.dto.SaleroomOfPeriodDto;
import com.arf.carbright.entity.RPackageRecord;
import com.arf.carbright.entity.RPackageRecord.FeePayStatus;
import com.arf.carbright.entity.RPackageRecord.ReturnPointVoucher;
import com.arf.carbright.entity.RPackageRecord.Status;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("packageRecordDao")
public class RPackageRecordDaoImpl extends BaseDaoImpl<RPackageRecord, Long> implements RPackageRecordDao {

	@Override
	public List<RPackageRecord> findByPackageTypeNum(String packageTypeNum,Integer status,Integer feePayStatus) {

		String jpql = "select packagerecord from RPackageRecord packagerecord where packagerecord.packageTypeNum = :packageTypeNumand"
				+ " and packagerecord.status=:status and packagerecord.feePayStatus=:feePayStatus";
		return entityManager.createQuery(jpql, RPackageRecord.class).setParameter("packageTypeNum", packageTypeNum)
				.setParameter("status", Status.available.ordinal())
				.setParameter("feePayStatus", FeePayStatus.pay_success).getResultList();
	}
	@Override
	public List<RPackageRecord> findByUserName(String userName, Integer status, Integer feePayStatus) {

		StringBuilder sb = new StringBuilder("select packagerecord from RPackageRecord packagerecord where 1=1 ");
		sb.append(" and packagerecord.userName = :userName ");
		if (status != null) {
			sb.append(" and packagerecord.status=:status ");
		}
		if (feePayStatus != null) {
			sb.append(" and packagerecord.feePayStatus = :feePayStatus ");
		}
		TypedQuery<RPackageRecord> query = entityManager.createQuery(sb.toString(), RPackageRecord.class)
				.setParameter("userName", userName);
		if (status != null) {
			query.setParameter("status", status);

		}
		if (feePayStatus != null) {
			query.setParameter("feePayStatus", feePayStatus);
		}
		return query.getResultList();
	}
	@Override
	public List<RPackageRecord> findByOutTradeNo(String outTradeNo) {
		String hql = "from RPackageRecord pkg where pkg.outTradeNo = :outTradeNo ";
		return this.entityManager.createQuery(hql, RPackageRecord.class).setParameter("outTradeNo", outTradeNo).getResultList();
	}
	
	@Override
	public BigDecimal findSumTotalFee(String outTradeNo) {
		String hql = "select sum(pkg.packagePrice) as TotalFee from RPackageRecord pkg where pkg.outTradeNo = :outTradeNo ";
		try{
			return this.entityManager.createQuery(hql, BigDecimal.class)
					.setParameter("outTradeNo", outTradeNo)
					.getSingleResult();
		}catch(NoResultException e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public BigDecimal findSumGiftAmount(String outTradeNo) {
		String hql = "select sum(pkg.giftAmount) as giftAmount from RPackageRecord pkg where pkg.outTradeNo = :outTradeNo ";
		try{
			return this.entityManager.createQuery(hql, BigDecimal.class)
					.setParameter("outTradeNo", outTradeNo)
					.getSingleResult();
		}catch(NoResultException e){
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public PageResult<RPackageRecord> findPageByUserName(String userName,List<String> useRangeNum,Integer status, Integer feePayStatus,
			int pageSize, int pageNo) {
		return findPageByCondition(userName, null,useRangeNum, status, feePayStatus,null,null,pageSize, pageNo);
	}
		
	public PageResult<RPackageRecord> findPageByCondition(String userName,Integer businessId,List<String> useRangeNum,Integer status, Integer feePayStatus,
			Date startTime,Date endTime,int pageSize, int pageNo) {
		StringBuilder sb = new StringBuilder("select packagerecord from RPackageRecord packagerecord where 1=1 ");
		StringBuilder countSb = new StringBuilder("select count(*) as COUNT from RPackageRecord packagerecord where 1=1 ");
		if(userName!=null)
		{
			sb.append(" and packagerecord.userName = :userName ");
			countSb.append(" and packagerecord.userName = :userName ");
		}
		if(businessId!=null){
			sb.append(" and packagerecord.businessId = :businessId ");
			countSb.append(" and packagerecord.businessId = :businessId ");
		}
		if (status != null) {
			sb.append(" and packagerecord.status=:status ");
			countSb.append(" and packagerecord.status=:status ");
		}
		if (feePayStatus != null) {
			sb.append(" and packagerecord.feePayStatus = :feePayStatus ");
			countSb.append(" and packagerecord.feePayStatus = :feePayStatus ");
		}
		if(useRangeNum!=null){
			sb.append(" and packagerecord.useRangeNum in (:useRangeNum) ");
			countSb.append(" and packagerecord.useRangeNum in (:useRangeNum) ");
		}
		if(endTime!=null&&startTime!=null){
			sb.append(" and (packagerecord.startTime > :startTime and packagerecord.endTime < :endTime) ");
			countSb.append(" and (packagerecord.startTime > :startTime and packagerecord.endTime < :endTime) ");
		}
		sb.append(" order by create_date desc ");
		
		TypedQuery<RPackageRecord> query = entityManager.createQuery(sb.toString(), RPackageRecord.class);				
		TypedQuery<Long> queryCount = entityManager.createQuery(countSb.toString(), Long.class);				
		if(userName!=null)
		{
			query.setParameter("userName", userName);
			queryCount.setParameter("userName", userName);
		}
		if (status != null) {
			query.setParameter("status", status);
			queryCount.setParameter("status", status);

		}
		if (feePayStatus != null) {
			query.setParameter("feePayStatus", feePayStatus);
			queryCount.setParameter("feePayStatus", feePayStatus);
		}
		if(useRangeNum!=null){
			query.setParameter("useRangeNum", useRangeNum);
			queryCount.setParameter("useRangeNum", useRangeNum);			
		}
		if(endTime!=null&&startTime!=null){
			query.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);
			queryCount.setParameter("startTime", startTime);
			queryCount.setParameter("endTime", endTime);
		}
		if(businessId!=null){
			query.setParameter("businessId", businessId);
			queryCount.setParameter("businessId", businessId);
		}
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo - 1) * pageSize);
		
		List<RPackageRecord> results = query.getResultList();
		int totalNum = 0;
		try{
			totalNum = queryCount.getSingleResult().intValue();
		}catch(Exception e){
			e.printStackTrace();
		}
		PageResult<RPackageRecord> page = new PageResult<RPackageRecord>(results, totalNum);
		return page;
		
	}
	@Override
	public int findCountByPkgNum(String userName, String packageTypeNum,RPackageRecord.Status status) {
		StringBuffer sqlSb = new StringBuffer("select count(id) as COUNT from RPackageRecord p where 1=1 ");
		if(StringUtils.isNotBlank(userName)){
			sqlSb.append(" and p.userName = :userName ");
		}
		if(StringUtils.isNotBlank(packageTypeNum)){
			sqlSb.append(" and p.packageTypeNum = :packageTypeNum ");
		}
		if(status!=null){
			sqlSb.append(" and p.status = :status ");
		}
		sqlSb.append(" and p.feePayStatus = " + RPackageRecord.FeePayStatus.pay_success.ordinal());
		TypedQuery<Long> query = entityManager.createQuery(sqlSb.toString(), Long.class);
		if(StringUtils.isNotBlank(userName)){
			query.setParameter("userName", userName);
		}
		if(StringUtils.isNotBlank(packageTypeNum)){
			query.setParameter("packageTypeNum", packageTypeNum);
		}
		if(status!=null){
			query.setParameter("status", status.ordinal());
		}
		int count = 0;
		try {
			Long res = query.getSingleResult();
			count = res == null ? 0:res.intValue();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public SaleroomOfPeriodDto findSaleroomOfPeriod(long businessId, Date startBuyingTime, Date endBuyingTime) {
		StringBuffer sqlsb = new StringBuffer("select sum(r.gift_amount) as giftAmount,sum(package_price) as packagePrice,count(1) "
				+ " as count,r.business_id as businessId,min(r.buying_date) as startBuyingTime ,max(buying_date) as endBuyingTime from R_Package_Record r where 1=1 ");
		sqlsb.append(" and r.business_id = :businessId ");
		if(startBuyingTime != null){
			sqlsb.append(" and r.buying_date >= :startBuyingTime ");
		}
		if(endBuyingTime != null){
			sqlsb.append(" and r.buying_date <= :endBuyingTime ");
		}
		
		sqlsb.append(" and r.fee_pay_status = "+RPackageRecord.FeePayStatus.pay_success.ordinal()+" ");
		
		Query query = this.entityManager.createNativeQuery(sqlsb.toString());
		query.setParameter("businessId", businessId);
		if(startBuyingTime != null){
			query.setParameter("startBuyingTime", startBuyingTime);
		}
		if(endBuyingTime != null){
			query.setParameter("endBuyingTime", endBuyingTime);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	    @SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = query.getResultList();  
	    return JSON.parseObject(JSON.toJSONString(rows.get(0)), SaleroomOfPeriodDto.class);
	}
	@Override
	public List<RPackageRecord> findByStatusAndEndTime(Integer status,Date endTime) {
		String hql = "from RPackageRecord packageRecord where packageRecord.status = :status and packageRecord.endTime < :endTime";
		TypedQuery<RPackageRecord>  query = entityManager.createQuery(hql, RPackageRecord.class);
		query.setParameter("status", status);
		query.setParameter("endTime", endTime);
		return query.getResultList();
	}
	
	/**
	 * 统计商户套餐（某一个）销售情况
	 * 根据不同时间进行查询
	 * ****/
	@Override
	public BigDecimal salesInfoStatistics(Date startTime,Date endTime,Integer businessId,Integer packageId){
		StringBuffer bufferhql =new StringBuffer("select sum(packagePrice-giftAmount) from RPackageRecord packageRecord where packageRecord.feePayStatus = :feePayStatus");
		if(businessId!=null){			
			bufferhql.append(" and packageRecord.businessId=:businessId");
		}
		if(packageId!=null){
			bufferhql.append(" and packageRecord.packageTypeId=:packageTypeId");
		}
		if(startTime!=null&&endTime!=null){
			bufferhql.append(" and (packageRecord.buyingDate<:endTime and packageRecord.buyingDate>:startTime)");
		}
		TypedQuery<Object> query=entityManager.createQuery(bufferhql.toString(),Object.class);		
		query.setParameter("feePayStatus", RPackageRecord.FeePayStatus.pay_success.ordinal());
		if(businessId!=null){			
			query.setParameter("businessId", businessId);
		}
		if(packageId!=null){
			query.setParameter("packageTypeId", packageId);
		}
		if(startTime!=null&&endTime!=null){			
			query.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);
		}
		List<Object> objList = query.getResultList();
		if(!CollectionUtils.isEmpty(objList)){
			Object value=objList.get(0);
			if(value!=null)
			return new BigDecimal(value.toString());
		}		
		return new BigDecimal(0);		
	}
	@Override
	public List<HashMap<String,Object>> salesInfoGroupDay(Date startTime,Date endTime,Integer businessId,Integer packageId,Integer limitCount){
		StringBuffer bufferhql =new StringBuffer("SELECT SUM(package_price-gift_amount) salesAmount,DATE_FORMAT(buying_date,'%Y-%m-%d') salesDate FROM r_package_record  WHERE fee_pay_status =:feePayStatus");
		if(businessId!=null){			
			bufferhql.append(" and business_id=:businessId");
		}
		if(packageId!=null){
			bufferhql.append(" and package_type_id=:packageTypeId");
		}
		if(startTime!=null&&endTime!=null){
			bufferhql.append(" and (buying_date<:endTime and buying_date>:startTime)");
		}
		bufferhql.append(" GROUP BY salesDate desc LIMIT :limitCount");
		Query query=entityManager.createNativeQuery(bufferhql.toString());		
		query.setParameter("feePayStatus", RPackageRecord.FeePayStatus.pay_success.ordinal());
		if(businessId!=null){			
			query.setParameter("businessId", businessId);
		}
		if(packageId!=null){
			query.setParameter("packageTypeId", packageId);
		}
		if(startTime!=null&&endTime!=null){			
			query.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);
		}
		query.setParameter("limitCount",limitCount);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<HashMap<String,Object>> objList =query.getResultList();
		if(!CollectionUtils.isEmpty(objList)){			
			return objList;
		}		
		return null;		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<RPackageRecord> findByFailureRecordToday(String buyingdate, int minute) {
		StringBuffer bufferhql =new StringBuffer("SELECT * FROM r_package_record AS r WHERE (r.fee_pay_status=0 or r.fee_pay_status=2) AND (r.point>0 or r.vouchers_money>0) AND r.return_point=:returnpoint AND r.buying_date>:buyingdate AND timestampdiff(MINUTE,r.buying_date,now())>:minute");		
		Query  query=entityManager.createNativeQuery(bufferhql.toString(),RPackageRecord.class);	
		query.setParameter("returnpoint",ReturnPointVoucher.Possible_Return.ordinal());
		query.setParameter("buyingdate",buyingdate);
		query.setParameter("minute",minute);
		return query.getResultList();		
	}
	@Override
	public List<HashMap<String,Object>> findByUserName(String userName) {
		StringBuffer bufferhql =new StringBuffer("SELECT id,r.business_id as businessId,COUNT(r.business_id) as count FROM r_package_record r  WHERE r.user_name=:userName  AND r.status =0 GROUP BY r.business_id");		
		Query  query=entityManager.createNativeQuery(bufferhql.toString());	
		query.setParameter("userName",userName);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<HashMap<String,Object>> objList =query.getResultList();
		return objList;		
	}
	@Override
	public RPackageRecord findBypackageId(String userName,Long packageId) {
		String hql = "from RPackageRecord packageRecord where packageRecord.id = :packageId and packageRecord.userName = :userName";
		TypedQuery<RPackageRecord>  query = entityManager.createQuery(hql, RPackageRecord.class);
		query.setParameter("packageId", packageId);
		query.setParameter("userName", userName);
		return query.getResultList().get(0);
	}
	@Override
	public List<RPackageRecord> findPackageByUserName(String userName, Date endTime) {
		StringBuffer sql =new StringBuffer(" FROM RPackageRecord ");
		sql.append(" WHERE status ="+Status.available.ordinal()+" AND feePayStatus ="+FeePayStatus.pay_success.ordinal()+" AND now()<endTime");
		sql.append(" and endTime<=:endTime ");
		if (userName!=null) {
			sql.append("  AND userName =:userName ");
		}else{
			sql.append("  GROUP BY userName ");
		}
		TypedQuery<RPackageRecord>  query=entityManager.createQuery(sql.toString(),RPackageRecord.class);
		if (userName!=null) {
			query.setParameter("userName", userName);
		}
		query.setParameter("endTime", endTime);
		return query.getResultList();
	}
	@Override
	public void updatePackageRecord(List<String> list) {
		StringBuffer sql =new StringBuffer("UPDATE RPackageRecord set pushTime=NOW() WHERE userName in(:list)");
		entityManager.createQuery(sql.toString()).setParameter("list", list).executeUpdate();
		
	}
	@Override
	public void updatePackageByOrderNo(List<String> list) {
		StringBuffer sql =new StringBuffer("UPDATE RPackageRecord set pushTime=NOW() WHERE orderNo in(:list)");
		entityManager.createQuery(sql.toString()).setParameter("list", list).executeUpdate();
	}
	@Override
	public int findCountByPkgNum(String userName, String packageTypeNum) {		
		return findCountByPkgNum(userName, packageTypeNum,null);
	}
}
