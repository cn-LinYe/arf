package com.arf.smart.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.smart.dao.PCustomFunctionDao;
import com.arf.smart.entity.PCustomFunction;
import com.arf.smart.entity.PCustomFunction.Status;

@Repository("pcustomFunctionDao")
public class PCustomFunctionDaoImpl extends BaseDaoImpl<PCustomFunction, Long> implements PCustomFunctionDao{

	@Override
	public List<Map<String, Object>> findByStatus(Status status) {
		StringBuffer sql = new StringBuffer("SELECT ");
		sql.append("p.id id,p.create_date createDate,p.modify_date modifyDate,p.version version,p.big_icon bigIcon,");
		sql.append("p.custom_type customType,p.icon_hints iconHints,p.icon_mark iconMark,p.icon_name iconName,p.icon_order iconOrder,");
		sql.append("p.icon_type iconType,p.small_icon smallIcon,p.status status,p.horizontal_icon horizontalIcon,p.description description,");
		sql.append(" p.webview_url as webviewUrl,p.cities,p.exculude_cities as exculudeCities ,p.communities,p.exculude_communities exculudeCommunities");
		sql.append(" from p_custom_function p where 1=1 ");
		if(status!=null){
			sql.append(" and p.status =:status");
		}
		sql.append(" order by p.icon_order");
		Query query =entityManager.createNativeQuery(sql.toString());
		if(status!=null){
			query.setParameter("status", status.ordinal());
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> map= query.getResultList();
		return map;
	}

	@Override
	public List<Map<String, Object>> findByIds(Long[] array) {
		StringBuffer sql = new StringBuffer("SELECT ");
		sql.append("p.id id,p.create_date createDate,p.modify_date modifyDate,p.version version,p.big_icon bigIcon,");
		sql.append("p.custom_type customType,p.icon_hints iconHints,p.icon_mark iconMark,p.icon_name iconName,p.icon_order iconOrder,");
		sql.append("p.icon_type iconType,p.small_icon smallIcon,p.status status,p.horizontal_icon horizontalIcon,p.description description,");
		sql.append(" p.webview_url as webviewUrl,p.cities,p.exculude_cities as exculudeCities,p.communities ");
		sql.append(" from p_custom_function p where 1=1 ");
		sql.append(" and p.id in (:array)");
		sql.append(" and p.status =:status");
		Query query =entityManager.createNativeQuery(sql.toString());
		
		query.setParameter("array",  Arrays.asList(array));
		query.setParameter("status",  Status.normal.ordinal());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> map= query.getResultList();
		return map;
	}

}
