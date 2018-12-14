package com.arf.help.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.help.dao.IHelpPublicityInfoDao;
import com.arf.help.entity.HelpPublicityInfo;
import com.google.common.collect.Lists;

@Repository("helpPublicityInfoDaoImpl")
public class HelpPublicityInfoDaoImpl extends
		BaseDaoImpl<HelpPublicityInfo, Long> implements IHelpPublicityInfoDao {

	@Override
	public PageResult<HelpPublicityInfo> findListByCondition(
			String communityNumber, Integer pageNo, Integer pageSize) {
		PageResult<HelpPublicityInfo> pageResult = new PageResult<HelpPublicityInfo>();
		//总记录数sql
		StringBuffer sbCount = new StringBuffer("select count(a.id) as COUNT");
		sbCount.append(" from help_publicity_info a ");
		sbCount.append(" where 1 = 1");
		sbCount.append(" and a.status = 0");
		Query queryCount = this.entityManager.createNativeQuery(sbCount.toString());
		int count = 0; 
		try {
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		pageResult.setTotalNum(count);
		//分页查询sql
		StringBuffer sb = new StringBuffer("select a.id,a.create_date createDate,a.modify_date modifyDate,a.version, ");
		sb.append(" a.title,a.status,a.content");
		sb.append(" from help_publicity_info a ");
		sb.append(" where 1 = 1");
		sb.append(" and a.status = 0");
		sb.append(" order by a.create_date desc");
		Query query = this.entityManager.createNativeQuery(sb.toString());
		if(pageNo != null && pageSize != null){
			query.setFirstResult((pageNo-1)*pageSize);
			query.setMaxResults(pageSize);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);//最重要的语句
		List<HelpPublicityInfo> helpPublicityInfoList = Lists.newArrayList();
		List<Map<String,Object>> rows = query.getResultList();
		if(CollectionUtils.isNotEmpty(rows)){
			for (Map<String, Object> map : rows) {
				HelpPublicityInfo helpPublicityInfo = new HelpPublicityInfo();
				helpPublicityInfo = JSON.parseObject(JSON.toJSONString(map), HelpPublicityInfo.class);
				helpPublicityInfoList.add(helpPublicityInfo);
			}
		}
		pageResult.setList(helpPublicityInfoList);
		return pageResult;
	}

}
