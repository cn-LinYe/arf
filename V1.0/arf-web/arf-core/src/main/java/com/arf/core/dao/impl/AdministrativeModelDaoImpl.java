package com.arf.core.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.arf.axdshopkeeper.entity.BizChannel;
import com.arf.axdshopkeeper.entity.BizChannelRefBranch;
import com.arf.core.dao.AdministrativeModelDao;
import com.arf.core.entity.AdministrativeModel;

/**
 * Dao - 管理员基本信息
 * 
 * @author arf
 * @version 4.0
 */
@Repository("administrativeModelDaoImpl")
public class AdministrativeModelDaoImpl extends BaseDaoImpl<AdministrativeModel, Long>
		implements AdministrativeModelDao {

	public List<AdministrativeModel> sellectByLevelCityNo(Integer cityNo) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AdministrativeModel> criteriaQuery = criteriaBuilder.createQuery(AdministrativeModel.class);
		Root<AdministrativeModel> root = criteriaQuery.from(AdministrativeModel.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("level"), 2),
				criteriaBuilder.equal(root.get("cityno"), cityNo));	
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public AdministrativeModel sellectByPropretyNumber(Integer property_number) {
		String jpql = "select adminM from AdministrativeModel adminM where adminM.property_number = :property_number and adminM.level =3";
		List<AdministrativeModel> list = entityManager.createQuery(jpql, AdministrativeModel.class).setParameter("property_number", property_number).getResultList();
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public AdministrativeModel sellectByBranchId(Integer branch_id) {
		String jpql = "select adminM from AdministrativeModel adminM where adminM.branch_id = :branch_id and adminM.level =2";
		List<AdministrativeModel> list = entityManager.createQuery(jpql, AdministrativeModel.class).setParameter("branch_id", branch_id).getResultList();
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public BizChannelRefBranch findChannelRefByBranchId(String branchId) {
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" a.id id ");
		sb.append(" ,a.create_date createDate ");
		sb.append(" ,a.modify_date modifyDate ");
		sb.append(" ,a.version version ");
		sb.append(" ,a.branch_id branchId ");
		sb.append(" ,a.biz_channel_number bizChannelNumber ");
		sb.append(" from bi_biz_channel_ref_branch a ");
		sb.append(" where 1 = 1 and a.branch_id = :branch_id ");
		Query typedQuery = this.entityManager.createNativeQuery(sb.toString());
		typedQuery.setParameter("branch_id", branchId);
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = typedQuery.getResultList();
		if(CollectionUtils.isNotEmpty(rows)){
			return JSON.parseObject(JSON.toJSONString(rows.get(0)), BizChannelRefBranch.class);
		}
		return null;
	}

	@Override
	public BizChannel findChannelByChannelnum(String bizChannelNumber) {
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" a.id id ");
		sb.append(" ,a.create_date createDate ");
		sb.append(" ,a.modify_date modifyDate ");
		sb.append(" ,a.version version ");
		sb.append(" ,a.biz_channel_name bizChannelName ");
		sb.append(" ,a.biz_channel_number bizChannelNumber ");
		sb.append(" ,a.principal_name principalName ");
		sb.append(" ,a.principal_mobile principalMobile ");
		sb.append(" from bi_biz_channel a ");
		sb.append(" where 1 = 1 and a.biz_channel_number = :biz_channel_number ");
		Query typedQuery = this.entityManager.createNativeQuery(sb.toString());
		typedQuery.setParameter("biz_channel_number", bizChannelNumber);
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = typedQuery.getResultList();
		if(CollectionUtils.isNotEmpty(rows)){
			return JSON.parseObject(JSON.toJSONString(rows.get(0)), BizChannel.class);
		}
		return null;
	}
}
