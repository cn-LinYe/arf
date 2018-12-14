package com.arf.finance.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.finance.dao.IFinanceInfoDao;
import com.arf.finance.entity.FinanceInfo;
import com.google.common.collect.Lists;

@Repository("financeInfoDaoImpl")
public class FinanceInfoDaoImpl extends BaseDaoImpl<FinanceInfo, Long>
		implements IFinanceInfoDao {

	@Override
	public PageResult<FinanceInfo> findListByCondition(String communityNumber,
			Integer pageNo, Integer pageSize) {
		PageResult<FinanceInfo> pageResult = new PageResult<FinanceInfo>();
		//总记录数sql
		StringBuffer sbCount = new StringBuffer("select count(fi.id) as COUNT");
		sbCount.append(" from finance_info fi ");
		sbCount.append(" where 1 = 1");
		if(StringUtils.isNotBlank(communityNumber)){
			sbCount.append(" and (FIND_IN_SET(:communityNumber,fi.community_numbers) or fi.scope = 0)");
		}else{
			sbCount.append(" and (fi.scope = 0)");
		}
		sbCount.append(" and (fi.status = 0 or fi.status = 1)");
		Query queryCount = this.entityManager.createNativeQuery(sbCount.toString());
		if(StringUtils.isNotBlank(communityNumber)){
			queryCount.setParameter("communityNumber", communityNumber);
		}
		int count = 0; 
		try {
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		pageResult.setTotalNum(count);
		//分页查询sql
		StringBuffer sb = new StringBuffer("select fi.id,fi.create_date createDate,fi.modify_date modifyDate,fi.version, ");
		sb.append(" fi.community_numbers communityNumbers,fi.content,fi.fee,fi.name,");
		sb.append(" fi.photo,fi.scope,fi.status,fi.summary,fi.tags");
		sb.append(" from finance_info fi ");
		sb.append(" where 1 = 1");
		if(StringUtils.isNotBlank(communityNumber)){
			sb.append(" and (FIND_IN_SET(:communityNumber,fi.community_numbers) or fi.scope = 0)");
		}else{
			sb.append(" and (fi.scope = 0)");
		}
		sb.append(" and (fi.status = 0 or fi.status = 1)");
		sb.append(" order by fi.create_date desc");
		Query query = this.entityManager.createNativeQuery(sb.toString());
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber", communityNumber);
		}
		if(pageNo != null && pageSize != null){
			query.setFirstResult((pageNo-1)*pageSize);
			query.setMaxResults(pageSize);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);//最重要的语句
		List<FinanceInfo> financeInfoList = Lists.newArrayList();
		List<Map<String,Object>> rows = query.getResultList();
		if(CollectionUtils.isNotEmpty(rows)){
			for (Map<String, Object> map : rows) {
				FinanceInfo actActivity = new FinanceInfo();
				actActivity = JSON.parseObject(JSON.toJSONString(map), FinanceInfo.class);
				financeInfoList.add(actActivity);
			}
		}
		pageResult.setList(financeInfoList);
		return pageResult;
	}

}
