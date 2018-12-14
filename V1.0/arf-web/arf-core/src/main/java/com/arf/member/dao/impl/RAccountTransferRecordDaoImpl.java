package com.arf.member.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.member.dao.IRAccountTransferRecordDao;
import com.arf.member.entity.RAccountTransferRecord;
import com.arf.member.search.RAccountTransferRecordCondition;

@Repository("rAccountTransferRecordDaoImpl")
public class RAccountTransferRecordDaoImpl extends BaseDaoImpl<RAccountTransferRecord, Long> implements IRAccountTransferRecordDao {

	@Override
	public PageResult<RAccountTransferRecord> findListByCondition(RAccountTransferRecordCondition condition) {
		
		PageResult<RAccountTransferRecord> pageResult = new PageResult<RAccountTransferRecord>();
		
		String userName = condition.getUserName();
		Byte accountType = condition.getAccountType();
		String operateTimeStrStart = condition.getOperateTimeStrStart();
		String operateTimeStrEnd = condition.getOperateTimeStrEnd();
		Byte status = condition.getStatus();
		Byte type = condition.getType();
		Byte userType = condition.getUserType();
		Integer consumeType = condition.getConsumeType();
		
		StringBuffer sbCount = new StringBuffer("select count(id) as COUNT from r_account_transfer_record r where 1 = 1 ");
		StringBuffer sb = new StringBuffer("from com.arf.member.entity.RAccountTransferRecord where 1 = 1");
		if(StringUtils.isNotBlank(userName)){
			sbCount.append(" and r.user_name = :userName");
			sb.append(" and userName = :userName");
		}
		if(accountType != null){
			sbCount.append(" and r.account_type = :accountType");
			sb.append(" and accountType = :accountType");
		}
		if(StringUtils.isNotBlank(operateTimeStrStart)){
			sbCount.append(" and r.operate_time >= :operateTimeStrStart");
			sb.append(" and operateTime >= :operateTimeStrStart");
		}
		if(StringUtils.isNotBlank(operateTimeStrEnd)){
			sbCount.append(" and r.operate_time < :operateTimeStrEnd");
			sb.append(" and operateTime < :operateTimeStrEnd");
		}
		if(status != null){
			sbCount.append(" and r.status = :status");
			sb.append(" and status = :status");
		}
		if(type != null){
			sbCount.append(" and r.type = :type");
			sb.append(" and type = :type");
		}
		if(userType != null){
			sbCount.append(" and r.user_type = :userType");
			sb.append(" and userType = :userType");
		}
		if(consumeType != null){
			if(consumeType == 0){//0消费 1收入
				sbCount.append(" and r.type >= 50");
				sb.append(" and type >= 50");
			}else if(consumeType == 1){
				sbCount.append(" and r.type < 50");
				sb.append(" and type < 50");
			}
		}
		sbCount.append(" order by r.create_date desc");
		sb.append(" order by createDate desc");
		
		//创建查询
		Query queryCount = this.entityManager.createNativeQuery(sbCount.toString());
		TypedQuery<RAccountTransferRecord> typedQuery = entityManager.createQuery(sb.toString(), RAccountTransferRecord.class);
		
		//设置参数
		if(StringUtils.isNotBlank(userName)){
			queryCount.setParameter("userName", userName);
			typedQuery.setParameter("userName", userName);
		}
		if(accountType != null){
			queryCount.setParameter("accountType", accountType);
			typedQuery.setParameter("accountType", accountType);
		}
		SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(StringUtils.isNotBlank(operateTimeStrStart)){
			Date operateTimeStart = null;
			try {
				operateTimeStart = adf.parse(operateTimeStrStart);
				typedQuery.setParameter("operateTimeStrStart", operateTimeStart);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			queryCount.setParameter("operateTimeStrStart", operateTimeStrStart);
		}
		if(StringUtils.isNotBlank(operateTimeStrEnd)){
			Date operateTimeEnd = null;
			try {
				operateTimeEnd = adf.parse(operateTimeStrEnd);
				typedQuery.setParameter("operateTimeStrEnd", operateTimeEnd);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			queryCount.setParameter("operateTimeStrEnd", operateTimeStrEnd);
		}
		if(status != null){
			queryCount.setParameter("status", status);
			typedQuery.setParameter("status", status);
		}
		if(type != null){
			queryCount.setParameter("type", type);
			typedQuery.setParameter("type", type);
		}
		if(userType != null){
			queryCount.setParameter("userType", userType);
			typedQuery.setParameter("userType", userType);
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
			typedQuery.setMaxResults(condition.getPageSize());
		}
		List<RAccountTransferRecord> list = typedQuery.getResultList();
		pageResult.setList(list);
		return pageResult;
	}
	
	public List<RAccountTransferRecord> findAllExpenseList(String userName,Byte userType,Date startTime,Date endTime){
		StringBuffer sb = new StringBuffer("from com.arf.member.entity.RAccountTransferRecord where 1 = 1");
		sb.append(" and type >= 50 and status = 1");
		sb.append(" and userName = :userName");
		sb.append(" and userType = :userType");
		if(startTime != null){
			sb.append(" and createDate >= :startTime");
		}
		if(endTime != null){
			sb.append(" and createDate <= :endTime");
		}
		String hql = sb.toString();
		TypedQuery<RAccountTransferRecord> typedQuery = entityManager.createQuery(hql, RAccountTransferRecord.class);
		typedQuery.setParameter("userName", userName);
		typedQuery.setParameter("userType", userType);
		if(startTime != null){
			typedQuery.setParameter("startTime", startTime);
		}
		if(endTime != null){
			typedQuery.setParameter("endTime", endTime);
		}
		return typedQuery.getResultList();
	}

	@Override
	public Double getExpenseByCondition(String userName, Byte userType,Date startTime, Date endTime) {
		StringBuffer sql = new StringBuffer("select SUM(amount) from r_account_transfer_record r where 1 = 1 ");
		sql.append(" and type >= 50 and status = 1");
		sql.append(" and user_name = :userName");
		sql.append(" and user_type = :userType");
		if(startTime != null){
			sql.append(" and create_date >= :startTime");
		}
		if(endTime != null){
			sql.append(" and create_date <= :endTime");
		}
		String hql = sql.toString();
		Query query = this.entityManager.createNativeQuery(hql);
		query.setParameter("userName", userName);
		query.setParameter("userType", userType);
		if(startTime != null){
			query.setParameter("startTime", startTime);
		}
		if(endTime != null){
			query.setParameter("endTime", endTime);
		}
		double expense = 0d;
		try {
			BigDecimal expenseAll = (BigDecimal) query.getSingleResult();
			expense = expenseAll.doubleValue();
			return expense;
		} catch (Exception e) {
			return 0d;
		}
	}

}
