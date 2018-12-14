package com.arf.carbright.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.carbright.dao.IAxdVouchersRecordDao;
import com.arf.carbright.entity.AxdVouchersRecord;
import com.arf.carbright.entity.AxdVouchersRecord.FeePayStatus;
import com.arf.carbright.entity.AxdVouchersRecord.UsedStatus;
import com.arf.carbright.entity.AxdVouchersRecord.VouchersType;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("axdVouchersRecordDaoImpl")
public class AxdVouchersRecordDaoImpl extends BaseDaoImpl<AxdVouchersRecord, Long> implements IAxdVouchersRecordDao{

	@Override
	public PageResult<AxdVouchersRecord> findUserVouchersRecord(String userName, VouchersType vouchersType,
			UsedStatus usedStatus, int pageSize, int pageNo) {
		StringBuilder hql = new StringBuilder("from AxdVouchersRecord r where r.userName = :userName ");
		//查询记录总数
		StringBuilder sql = new StringBuilder("select count(id) as COUNT from r_axd_vouchers_record r where r.user_name = :userName ");
		if(usedStatus != null){			
			if(UsedStatus.FEFail.ordinal()==usedStatus.ordinal()){
				hql.append(" and (r.usedStatus = "+UsedStatus.Expired.ordinal()+"  or  r.usedStatus = "+UsedStatus.Finished.ordinal()+") ");
				sql.append(" and (r.used_status = "+UsedStatus.Expired.ordinal()+"  or  r.used_status = "+UsedStatus.Finished.ordinal()+") ");
			}else{
				hql.append(" and r.usedStatus = :usedStatus ");
				sql.append(" and r.used_status = :usedStatus ");
			}					
		}		
		hql.append(" and r.vouchersType = :vouchersType ");
		sql.append(" and r.vouchers_type = :vouchersType ");
		if (usedStatus!=null&&usedStatus.ordinal()==UsedStatus.Finished.ordinal()) {
			hql.append(" order by useTime desc");
		}else{
			hql.append(" order by createDate desc");
		}
		
		
		TypedQuery<AxdVouchersRecord>  query = this.entityManager.createQuery(hql.toString(), AxdVouchersRecord.class);
		query.setParameter("userName", userName);
		query.setParameter("vouchersType", vouchersType.ordinal());
		Query queryCount = this.entityManager.createNativeQuery(sql.toString());
		queryCount.setParameter("userName", userName);
		queryCount.setParameter("vouchersType", vouchersType.ordinal());
		if(usedStatus != null){		
			if(UsedStatus.FEFail.ordinal()!=usedStatus.ordinal()){
				query.setParameter("usedStatus", usedStatus.ordinal());
				queryCount.setParameter("usedStatus", usedStatus.ordinal());
			}			
		}
		if(pageSize>0&&pageNo>=0){
			query.setMaxResults(pageSize);
			query.setFirstResult((pageNo-1) * pageSize);
		}		
		List<AxdVouchersRecord> list = query.getResultList();		
		int count = 0; 
		try {
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageResult<AxdVouchersRecord> pageResult = new PageResult<AxdVouchersRecord>(list,count);
		pageResult.setList(list);
		return pageResult;
	}

	@Override
	public List<AxdVouchersRecord> findByStatusAndEndTime(UsedStatus usedStatus, Date endTime) {
		String hql = "from AxdVouchersRecord a where a.usedStatus = :usedStatus and a.effectiveEndDate < :effectiveEndDate";
		TypedQuery<AxdVouchersRecord> query = entityManager.createQuery(hql, AxdVouchersRecord.class);
		query.setParameter("usedStatus", usedStatus.ordinal());
		query.setParameter("effectiveEndDate", endTime);
		return query.getResultList();
	}

	@Override
	public AxdVouchersRecord findUsedDishVouchersByNum(String userName,String vouchersNums,UsedStatus usedStatus) {
		String hql = "from AxdVouchersRecord a where a.usedStatus = :usedStatus and a.userName=:userName and vouchersNum = :vouchersNum and a.effectiveEndDate >= now()";
		hql=hql.concat(" ORDER BY a.effectiveEndDate");
		TypedQuery<AxdVouchersRecord> query = entityManager.createQuery(hql, AxdVouchersRecord.class);
		query.setParameter("usedStatus", usedStatus.ordinal());
		query.setParameter("userName", userName);
		query.setParameter("vouchersNum", vouchersNums);
		List<AxdVouchersRecord> records=query.getResultList();
		if(CollectionUtils.isEmpty(records)){
			return null;
		}else{
			return records.get(0);
		}
	}

	@Override
	public Integer findUserAllVouchersRecord(String userName, UsedStatus usedStatus) {
		//查询记录总数
		
		StringBuilder sql = new StringBuilder("select count(id) as COUNT from r_axd_vouchers_record r where r.user_name = :userName ");
		if(usedStatus != null){
				sql.append(" and r.used_status = :usedStatus ");			
		}
		Query queryCount = this.entityManager.createNativeQuery(sql.toString());
		queryCount.setParameter("userName", userName);
		if(usedStatus != null){
				queryCount.setParameter("usedStatus", usedStatus.ordinal());
		}			
		int count = 0; 
		try {
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String , Object>> findUserVouchersType(String userName, UsedStatus usedStatus) {		
		StringBuilder sql = new StringBuilder("select count(id) as vouchersCount ,r.vouchers_type as vouchersType from r_axd_vouchers_record r where r.user_name = :userName ");
		if(usedStatus != null){
			sql.append(" and r.used_status = :usedStatus ");				
		}		
		sql.append(" group by r.vouchers_type");
		Query query = this.entityManager.createNativeQuery(sql.toString());
		query.setParameter("userName", userName);
		if(usedStatus != null){
			query.setParameter("usedStatus", usedStatus.ordinal());			
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);	   
		List<Map<String,Object>> rows = query.getResultList(); 
		if(rows!=null){
			return rows;
		}
		return null;
				
	}

	@Override
	public void updateBatch(Integer useStatus, Integer feePayType, String outTradeNo, Integer feePayStatus,
			List<String> list) {
		String sql ="update AxdVouchersRecord set usedStatus=:useStatus,set feePayType=:feePayType,set outTradeNo=:outTradeNo,set feePayStatus=:feePayStatus where id in (:list)";
		entityManager.createQuery(sql)
					.setParameter("useStatus", useStatus)
					.setParameter("feePayType", feePayType)
					.setParameter("outTradeNo", outTradeNo)
					.setParameter("feePayStatus", feePayStatus).setParameter("list", list).executeUpdate();
	}

	@Override
	public AxdVouchersRecord findUsedDishVouchersByNum(String outTradeNo, FeePayStatus feePayStatus) {
		StringBuffer sql =new StringBuffer("from AxdVouchersRecord where outTradeNo=:outTradeNo and feePayStatus=:feePayStatus");
		TypedQuery<AxdVouchersRecord> query =entityManager.createQuery(sql.toString(),AxdVouchersRecord.class);
		query.setParameter("outTradeNo", outTradeNo);
		query.setParameter("feePayStatus", feePayStatus.ordinal());
		List<AxdVouchersRecord> axdVouchersRecords=query.getResultList();
		return CollectionUtils.isEmpty(axdVouchersRecords)?null:axdVouchersRecords.get(0);
	}

	@Override
	public AxdVouchersRecord findVouchersByNum(String userName, String vouchersNums) {
		String hql = "from AxdVouchersRecord a where  a.userName=:userName and vouchersNum = :vouchersNum ";
		hql=hql.concat(" ORDER BY a.effectiveEndDate");
		TypedQuery<AxdVouchersRecord> query = entityManager.createQuery(hql, AxdVouchersRecord.class);
		query.setParameter("userName", userName);
		query.setParameter("vouchersNum", vouchersNums);
		List<AxdVouchersRecord> records=query.getResultList();
		if(CollectionUtils.isEmpty(records)){
			return null;
		}else{
			return records.get(0);
		}
	}
}
