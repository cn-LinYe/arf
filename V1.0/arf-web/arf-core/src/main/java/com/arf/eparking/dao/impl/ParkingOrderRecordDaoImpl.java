package com.arf.eparking.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.carbright.entity.RPackageRecord;
import com.arf.carbright.search.ParkingOrderRecordCondition;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.eparking.dao.ParkingOrderRecordDao;
import com.arf.eparking.entity.ParkingOrderRecord;

@Repository("parkingOrderRecordDaoImpl")
public class ParkingOrderRecordDaoImpl extends BaseDaoImpl<ParkingOrderRecord, Long>  implements ParkingOrderRecordDao {

	@Override
	public ParkingOrderRecord findByOrderNo(String orderNo) {
		String hql = "from ParkingOrderRecord r where r.orderNo = :orderNo ";
		List<ParkingOrderRecord> records = this.entityManager.createQuery(hql, ParkingOrderRecord.class).setParameter("orderNo", orderNo).getResultList();
		if(CollectionUtils.isEmpty(records)){
			return null;
		}else{
			return records.get(0);
		}
	}

	@Override
	public ParkingOrderRecord findByUserNameAndArriveTime(String userName,Date arriveTime) {
		String hql = "from ParkingOrderRecord r where r.userName = :userName and arriveTime = :arriveTime ";
		TypedQuery<ParkingOrderRecord> query = this.entityManager.createQuery(hql, ParkingOrderRecord.class);
		query.setParameter("userName", userName);
		query.setParameter("arriveTime", arriveTime);
		List<ParkingOrderRecord> records = query.getResultList();
		if(CollectionUtils.isEmpty(records)){
			return null;
		}else{
			return records.get(0);
		}
	}

	@Override
	public PageResult<Map<String,Object>> findListByCondition(ParkingOrderRecordCondition condition) {
		PageResult<Map<String,Object>> pageResult = new PageResult<Map<String,Object>>();
		int pageSize = condition.getPageSize();
		String userName = condition.getUserName();
		Integer status = condition.getStatus();
		String license = condition.getLicense();
		StringBuffer sbCount = new StringBuffer("select count(id) as COUNT from r_parking_order_record r where 1 = 1 ");
		StringBuffer sb = new StringBuffer("select r.*,p.address,p.parking_pic_url parkingPicUrl,s.i_value as orderMaxWaitTime,"
				+ "s2.i_value as breakContractTime,"
				+ "s3.i_value/100 as breakContractFee from r_parking_order_record r ");
		sb.append("left join p_parkinginfo p on p.parking_no = r.parking_id ");
		//TODO 此处要求后台生成最大可预约时间时i_no = 'OrderMaxWaitTime'
		sb.append("left join s_orderfee_rule_item s on s.parking_id = r.parking_id and s.i_no = 'OrderMaxWaitTime' ");
		sb.append("left join s_orderfee_rule_item s2 on s2.parking_id = r.parking_id and s2.i_no = 'BreakContractTime' ");
		sb.append("left join s_orderfee_rule_item s3 on s3.parking_id = r.parking_id and s3.i_no = 'BreakContractFee' ");
		sb.append("where 1 = 1");
		//设置查询条件
		if(StringUtils.isNotBlank(userName)){
			sbCount.append(" and r.user_name = :userName");
			sb.append(" and r.user_name = :userName");
		}
		//后台 订单状态：0已取消，1超时取消，2等待到达，3已延时，4已完成，5用户已评论；
		//前端 订单状态：null 查询所有记录 0待支付 1进行中 2已完成 3已取消
		if(status != null){
			if(status == 0){//查询待支付
				sbCount.append(" and (r.status is null)");
				sb.append(" and (r.status is null)");
			}else if(status == 1){//查询进行中
				sbCount.append(" and (r.status = 2)");
				sb.append(" and (r.status = 2)");
			}else if(status == 2){//查询已完成
				sbCount.append(" and (r.status = 4 or r.status = 5)");
				sb.append(" and (r.status = 4 or r.status = 5)");
			}else if(status == 3){//已取消
				sbCount.append(" and (r.status = 0 or r.status = 1)");
				sb.append(" and (r.status = 0 or r.status = 1)");
			}
		}
		if(StringUtils.isNotBlank(license)){
			sbCount.append(" and r.license = :license");
			sb.append(" and r.license = :license");
		}
		sbCount.append(" order by r.create_date desc");
		sb.append(" order by r.create_date desc");
		//创建查询
		Query queryCount = this.entityManager.createNativeQuery(sbCount.toString());
		Query typedQuery = this.entityManager.createNativeQuery(sb.toString());
		//设置参数
		if(StringUtils.isNotBlank(userName)){
			queryCount.setParameter("userName", userName);
			typedQuery.setParameter("userName", userName);
		}
		if(StringUtils.isNotBlank(license)){
			queryCount.setParameter("license", license);
			typedQuery.setParameter("license", license);
		}
		int count = 0; 
		try {
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
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
	public ParkingOrderRecord findByUserName(String userName,Integer status) {
		StringBuffer hql = new StringBuffer("from ParkingOrderRecord r where r.userName = :userName");
		if(status != null){
			hql.append(" and status = :status");
		}else{
			hql.append(" and status is null");
		}
		TypedQuery<ParkingOrderRecord> query = this.entityManager.createQuery(hql.toString(), ParkingOrderRecord.class);
		query.setParameter("userName", userName);
		if(status != null){
			query.setParameter("status", status);
		}
		List<ParkingOrderRecord> records = query.getResultList();
		if(CollectionUtils.isEmpty(records)){
			return null;
		}else{
			return records.get(0);
		}
	}

	@Override
	public List<ParkingOrderRecord> findOrderEnableCancel() {
		Date time = new Date();
		StringBuffer hql = new StringBuffer("from ParkingOrderRecord r where (status = :Waiting or status = :Delayed or status is null) and bookTime < :time");
		TypedQuery<ParkingOrderRecord> query = this.entityManager.createQuery(hql.toString(), ParkingOrderRecord.class);
		query.setParameter("Waiting", ParkingOrderRecord.Status.Waiting.ordinal());
		query.setParameter("Delayed", ParkingOrderRecord.Status.Delayed.ordinal());
		query.setParameter("time", time);
		List<ParkingOrderRecord> records = query.getResultList();
		if(CollectionUtils.isEmpty(records)){
			return null;
		}else{
			return records;
		}
	}

	@Override
	public List<ParkingOrderRecord> findOrderEnableExtension() {
		StringBuffer hql = new StringBuffer("from ParkingOrderRecord r where status = :Waiting");
		TypedQuery<ParkingOrderRecord> query = this.entityManager.createQuery(hql.toString(), ParkingOrderRecord.class);
		query.setParameter("Waiting", ParkingOrderRecord.Status.Waiting.ordinal());
		List<ParkingOrderRecord> records = query.getResultList();
		if(CollectionUtils.isEmpty(records)){
			return null;
		}else{
			return records;
		}
	}

	@Override
	public List<ParkingOrderRecord> findBreakContract(String userName) {
		StringBuffer hql = new StringBuffer("from ParkingOrderRecord r where userName = :userName and breakContractFee != null and (blacklistNo = null or blacklistNo = '')");
		TypedQuery<ParkingOrderRecord> query = this.entityManager.createQuery(hql.toString(), ParkingOrderRecord.class);
		query.setParameter("userName", userName);
		List<ParkingOrderRecord> records = query.getResultList();
		return records;
	}

	@Override
	public ParkingOrderRecord findByLicenArriveTime(String license, Date date) {
		StringBuffer hql = new StringBuffer("from ParkingOrderRecord r where license = :license and status = :status and bookTime >= :bookTime");
		TypedQuery<ParkingOrderRecord> query = this.entityManager.createQuery(hql.toString(), ParkingOrderRecord.class);
		query.setParameter("license", license);
		query.setParameter("status", ParkingOrderRecord.Status.Waiting.ordinal());
		query.setParameter("bookTime", date);
		ParkingOrderRecord parkingOrderRecord = null;
		try {
			parkingOrderRecord = query.getSingleResult();
		} catch (Exception e) {
			
		}
		return parkingOrderRecord;
	}
	
	@Override
	public List<ParkingOrderRecord> findOngoingOrderByUserName(String userName){
		StringBuffer hql = new StringBuffer("from ParkingOrderRecord r where userName = :userName and (status = :ongoing1 or status = :ongoing2)");
		TypedQuery<ParkingOrderRecord> query = this.entityManager.createQuery(hql.toString(), ParkingOrderRecord.class);
		query.setParameter("userName", userName);
		query.setParameter("ongoing1", ParkingOrderRecord.Status.Waiting.ordinal());
		query.setParameter("ongoing2", ParkingOrderRecord.Status.Delayed.ordinal());
		List<ParkingOrderRecord> records = query.getResultList();
		return records;
	}
	
	@Override
	public ParkingOrderRecord findOrderByUserName(String userName,String status){
		StringBuffer hql = new StringBuffer("from ParkingOrderRecord r where userName = :userName ");
		if("FINISH".equals(status)){
			hql.append(" and (status = :status1 or status = :status2)");
		}else if("ONGOING".equals(status)){
			hql.append(" and (status = :status1 or status = :status2)");
		}
		hql.append("  order by r.bookTime desc ");
		TypedQuery<ParkingOrderRecord> query = this.entityManager.createQuery(hql.toString(), ParkingOrderRecord.class);
		query.setParameter("userName", userName);
		if("FINISH".equals(status)){
			query.setParameter("status1", ParkingOrderRecord.Status.Finished.ordinal());
			query.setParameter("status2", ParkingOrderRecord.Status.Member_Evaluate.ordinal());
		}else if("ONGOING".equals(status)){
			query.setParameter("status1", ParkingOrderRecord.Status.Waiting.ordinal());
			query.setParameter("status2", ParkingOrderRecord.Status.Delayed.ordinal());
		}
		ParkingOrderRecord parkingOrderRecord = null;
		try{
			parkingOrderRecord = query.getSingleResult();
		} catch(Exception e){
			e.printStackTrace();
		}
		return parkingOrderRecord;
	}

	@Override
	public PageResult<ParkingOrderRecord> findByCondition(ParkingOrderRecordCondition condition) {
		Integer status =condition.getStatus();
		Integer pageSize =condition.getPageSize();
		Integer pageNo =condition.getPageNo();
		String userName =condition.getUserName();
		
		StringBuffer sb =new StringBuffer(" select r from ParkingOrderRecord r where 1=1 ");
		StringBuffer sbCount =new StringBuffer("select count(*) as COUNT from ParkingOrderRecord r where 1=1 ");
//		if (status!=null) {
//			sb.append(" and r.feePayStatus=:status");
//			sbCount.append(" and r.feePayStatus=:status");
//		}
		if (userName!=null) {
			sb.append(" and r.userName=:userName");
			sbCount.append(" and r.userName=:userName");
		}
		sb.append(" order by r.createDate");
		TypedQuery<ParkingOrderRecord> query = entityManager.createQuery(sb.toString(), ParkingOrderRecord.class);				
		TypedQuery<Long> queryCount = entityManager.createQuery(sbCount.toString(), Long.class);
//		if (status!=null) {
//			query.setParameter("status", status);
//			queryCount.setParameter("status", status);
//		}
		if (userName!=null) {
			query.setParameter("userName", userName);
			queryCount.setParameter("userName", userName);
		}
		if (pageNo!=null&&pageSize!=null) {
			query.setMaxResults(pageSize);
			query.setFirstResult((pageNo - 1) * pageSize);
		}
		List<ParkingOrderRecord> results = query.getResultList();
		int totalNum = 0;
		try{
			totalNum = queryCount.getSingleResult().intValue();
		}catch(Exception e){
			e.printStackTrace();
		}
		PageResult<ParkingOrderRecord> page = new PageResult<ParkingOrderRecord>(results, totalNum);
		return page;
	}
}
