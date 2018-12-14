package com.arf.core.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.RedRecordModelDao;
import com.arf.core.entity.Member;
import com.arf.core.entity.RedRecordModel;

/**
 * Dao - 发送红包纪录表
 * 
 * @author arf
 * @version 4.0
 */
@Repository("redRecordModelDaoImpl")
public class RedRecordModelDaoImpl extends BaseDaoImpl<RedRecordModel, Long> implements RedRecordModelDao {

	@Override
	public List<RedRecordModel> sellectByStatus() {
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//		CriteriaQuery<RedRecordModel> criteriaQuery = criteriaBuilder.createQuery(RedRecordModel.class);
//		Root<RedRecordModel> root = criteriaQuery.from(RedRecordModel.class);
//		criteriaQuery.select(root);		
//		Predicate restrictions = criteriaBuilder.conjunction();
//		restrictions = criteriaBuilder.or(restrictions,criteriaBuilder.equal(root.get("status"), "SENDING"));
//		restrictions = criteriaBuilder.or(restrictions,criteriaBuilder.equal(root.get("status"), "SENT"));
//		//restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.isNull(root.get("status")));
//		criteriaQuery.where(restrictions);
//		return super.findList(criteriaQuery, null, null, null, null);
//		String hql = "from com.arf.core.entity.RedRecordModel where status !='"+"RECEIVED"+"'"+"or status is null";
//		return entityManager.createQuery(hql, RedRecordModel.class).getResultList();
		String hql = "select rrm from com.arf.core.entity.RedRecordModel rrm where rrm.status in('SENT','SENDING')";
		return this.entityManager.createQuery(hql, RedRecordModel.class).getResultList();
	}

	@Override
	public RedRecordModel findByOutTradeNo(String outTradeNo) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RedRecordModel> criteriaQuery = criteriaBuilder.createQuery(RedRecordModel.class);
		Root<RedRecordModel> root = criteriaQuery.from(RedRecordModel.class);
		criteriaQuery.select(root);		
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("out_trade_no"), outTradeNo));
		criteriaQuery.where(restrictions);
		List<RedRecordModel> list = this.findList(criteriaQuery, null, null, null, null);
		if(list != null && list .size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
}
