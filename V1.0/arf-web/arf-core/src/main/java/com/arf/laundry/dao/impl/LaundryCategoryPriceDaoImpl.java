package com.arf.laundry.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.laundry.dao.ILaundryCategoryPriceDao;
import com.arf.laundry.entity.LaundryCategoryPrice;

@Repository("laundryCategoryPriceDaoImpl")
public class LaundryCategoryPriceDaoImpl extends BaseDaoImpl<LaundryCategoryPrice, Long> implements ILaundryCategoryPriceDao {

	/*@Override
	public List<LaundryCategoryPrice> findSuperCategoryByBusinessNumStatus(Integer businessNum,LaundryCategoryPrice.Status status) {
		String hql = "from LaundryCategoryPrice where businessNum = :businessNum and (superCategoryNum is null or superCategoryNum = 0) and status = :status order by orderIndex";
		TypedQuery<LaundryCategoryPrice> typedQuery = entityManager.createQuery(hql,LaundryCategoryPrice.class);
		typedQuery.setParameter("businessNum",businessNum.toString());
		typedQuery.setParameter("status",(byte)status.ordinal());
		return typedQuery.getResultList();
	}*/
	
	public static String allFields = " a.id,a.create_date as createDate,a.modify_date as modifyDate,a.version,a.category_num as categoryNum,a.super_category_num as superCategoryNum,"
			+ " a.super_category_name as superCategoryName,a.price,a.status,a.category_name as categoryName,a.img,a.order_index as orderIndex,a.business_num as businessNum,a.area_code as areaCode";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LaundryCategoryPrice> findSuperCategoryByBusinessNumStatus(Integer businessNum,LaundryCategoryPrice.Status status) {
		String hql = "select"+ allFields+" from l_laundry_category_price a "
				+ " left join l_laundry_category_price_tpl tpl on a.category_num = tpl.category_num  "
				+ " where a.business_num = :businessNum and (a.super_category_num is null or a.super_category_num = 0) and a.status = :status "
				+ " and tpl.status = 0 "
				+ " order by a.order_index";
		Query query = entityManager.createNativeQuery(hql);
		query.setParameter("businessNum",businessNum.toString());
		query.setParameter("status",(byte)status.ordinal());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> rows = query.getResultList();
		List<LaundryCategoryPrice> resultList = new ArrayList<>();
		if(CollectionUtils.isEmpty(rows)){
			return null;
		}
		for(Map<String,Object> row:rows){
			resultList.add(JSON.toJavaObject(new JSONObject(row), LaundryCategoryPrice.class));
		}
		return resultList;
	}

	@Override
	public List<LaundryCategoryPrice> findCategoryByBusinessNumAndSuperCategoryStatus(Integer businessNum, String superCategoryNum,LaundryCategoryPrice.Status status) {
		String hql = "from LaundryCategoryPrice where businessNum = :businessNum and superCategoryNum = :superCategoryNum and status = :status order by orderIndex";
		TypedQuery<LaundryCategoryPrice> typedQuery = entityManager.createQuery(hql,LaundryCategoryPrice.class);
		typedQuery.setParameter("businessNum",businessNum.toString());
		typedQuery.setParameter("superCategoryNum",superCategoryNum);
		typedQuery.setParameter("status",(byte)status.ordinal());
		return typedQuery.getResultList();
	}
	
	@Override
	public List<LaundryCategoryPrice> findByCondition(Integer businessNum, String superCategoryNum, LaundryCategoryPrice.Status status) {
		StringBuffer sb = new StringBuffer("from LaundryCategoryPrice where businessNum = :businessNum");
		if(superCategoryNum != null){
			sb.append(" and superCategoryNum = :superCategoryNum");
		}else{
			sb.append(" and superCategoryNum is null");
		}
		if(status != null){
			sb.append(" and status = :status");
		}
		sb.append(" order by orderIndex");
		TypedQuery<LaundryCategoryPrice> typedQuery = entityManager.createQuery(sb.toString(),LaundryCategoryPrice.class);
		typedQuery.setParameter("businessNum", businessNum.toString());
		if(superCategoryNum != null){
			typedQuery.setParameter("superCategoryNum", superCategoryNum);
		}
		if(status != null){
			typedQuery.setParameter("status", (byte)status.ordinal());
		}
		return typedQuery.getResultList();
	}

	@Override
	public LaundryCategoryPrice findByCategoryNumStatus(String categoryNum,LaundryCategoryPrice.Status status) {
		String hql = "from LaundryCategoryPrice where categoryNum = :categoryNum and status = :status";
		TypedQuery<LaundryCategoryPrice> typedQuery = entityManager.createQuery(hql,LaundryCategoryPrice.class);
		typedQuery.setParameter("categoryNum",categoryNum);
		typedQuery.setParameter("status",(byte)status.ordinal());
		try {
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public LaundryCategoryPrice findByBusinessNumCategoryNumStatus(String businessNum,String categoryNum,LaundryCategoryPrice.Status status) {
		String hql = "from LaundryCategoryPrice where businessNum = :businessNum and categoryNum = :categoryNum ";
		if(status != null){
			hql += " and status = :status";
		}
		TypedQuery<LaundryCategoryPrice> typedQuery = entityManager.createQuery(hql,LaundryCategoryPrice.class);
		typedQuery.setParameter("businessNum", businessNum);
		typedQuery.setParameter("categoryNum",categoryNum);
		if(status != null){
			typedQuery.setParameter("status",(byte)status.ordinal());
		}
		try {
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
}
