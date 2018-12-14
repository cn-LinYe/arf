package com.arf.goldcard.dao.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.goldcard.dao.IGoldCardTransferRecordDao;
import com.arf.goldcard.entity.GoldCardTransferRecord;

@Repository("goldCardTransferRecordDaoImpl")
public class GoldCardTransferRecordDaoImpl extends BaseDaoImpl<GoldCardTransferRecord, Long>
		implements IGoldCardTransferRecordDao {

	@Override
	public PageResult<GoldCardTransferRecord> findByCondition(String userName, String cardNo, GoldCardTransferRecord.Type type, int pageSize, int pageNo) {
		StringBuffer hql = new StringBuffer("from GoldCardTransferRecord where userName = :userName");
		//查询记录总数
		StringBuffer sql = new StringBuffer("select count(id) as COUNT from r_gold_card_transfer_record r where r.user_name = :userName");
		if(!StringUtils.isEmpty(cardNo)){
			hql.append(" and cardNo = :cardNo");
			sql.append(" and r.card_no = :cardNo");
		}
		if(type!=null){
			hql.append(" and type = :type");
			sql.append(" and r.type = :type");
		}
		hql.append(" order by createDate desc");
		TypedQuery<GoldCardTransferRecord> typedQuery = super.entityManager.createQuery(hql.toString(), GoldCardTransferRecord.class);
		Query queryCount = super.entityManager.createNativeQuery(sql.toString());
		
		typedQuery.setParameter("userName", userName);
		queryCount.setParameter("userName", userName);
		if(!StringUtils.isEmpty(cardNo)){
			typedQuery.setParameter("cardNo", cardNo);
			queryCount.setParameter("cardNo", cardNo);
		}
		if(type!=null){
			typedQuery.setParameter("type", type.ordinal());
			queryCount.setParameter("type", type.ordinal());
		}
		
		typedQuery.setMaxResults(pageSize);
		typedQuery.setFirstResult((pageNo-1)*pageSize);
		List<GoldCardTransferRecord> list = typedQuery.getResultList();
		
		int count = 0;
		try{
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
		}catch(Exception e){
			e.printStackTrace();
		}
		PageResult<GoldCardTransferRecord> pageResult = new PageResult<>(list,count);
		pageResult.setList(list);
		return pageResult;
	}

}
