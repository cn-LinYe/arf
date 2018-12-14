package com.arf.member.dao.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.member.dao.IPointTransferRecordDao;
import com.arf.member.entity.PointTransferRecord;

@Repository("pointTransferRecordDaoImpl")
public class PointTransferRecordDaoImpl extends BaseDaoImpl<PointTransferRecord, Long> implements IPointTransferRecordDao {

	@SuppressWarnings("unchecked")
	public PageResult<PointTransferRecord> findListOrders(String accountNumber,PointTransferRecord.PointType pointType, int pageSize, int pageNo){
		StringBuilder sqlSb = new StringBuilder("select * from r_point_transfer_record r where r.account_number = :accountNumber");
		StringBuilder sqlCount = new StringBuilder("select count(id) as COUNT from r_point_transfer_record r where r.account_number = :accountNumber");
		if(pointType != null){
			sqlSb.append(" and r.point_type = :pointType ");
			sqlCount.append(" and r.point_type = :pointType ");
		}
		sqlSb.append(" order by r.create_date DESC ");
		Query query = this.entityManager.createNativeQuery(sqlSb.toString(), PointTransferRecord.class);
		Query queryCount = this.entityManager.createNativeQuery(sqlCount.toString());
		query.setParameter("accountNumber", accountNumber);
		queryCount.setParameter("accountNumber", accountNumber);
		if(pointType != null){
			query.setParameter("pointType", pointType.ordinal());
			queryCount.setParameter("pointType", pointType.ordinal());
		}
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo-1)*pageSize);
		List<PointTransferRecord> list = query.getResultList();
		
		int count = 0;
		try{
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return new PageResult<PointTransferRecord>(list,count);
	}

	@Override
	public int findCount(Integer subType,String userName) {
		StringBuffer sql=new StringBuffer(" SELECT COUNT(1) FROM r_point_transfer_record r LEFT JOIN s_account s ON s.id = r.account_id ");
		sql.append(" WHERE 1=1 and sub_type =:subType");
		sql.append(" AND s.user_name=:userName");
		Query queryCount = this.entityManager.createNativeQuery(sql.toString());
		queryCount.setParameter("subType", subType);
		queryCount.setParameter("userName", userName);
		int count = 0;
		try{
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
		} catch (Exception e){
			e.printStackTrace();
		}
		return count;
	}
}
