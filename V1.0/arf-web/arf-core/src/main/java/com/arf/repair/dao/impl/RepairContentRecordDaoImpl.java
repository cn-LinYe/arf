package com.arf.repair.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.repair.dao.IRepairContentRecordDao;
import com.arf.repair.entity.RepairContentRecord;

@Repository("repairContentRecordDao")
public class RepairContentRecordDaoImpl extends BaseDaoImpl<RepairContentRecord, Long> implements IRepairContentRecordDao{

	@Override
	public PageResult<Map<String, Object>> findByCommunityAndUserName(String userName, String communityNumber,Integer pageSize, Integer pageNo) {
		StringBuffer countHql = new StringBuffer("select count(id) as COUNT from repair_content_record a");
		countHql.append(" where 1=1 and a.user_name =:userName");
		StringBuffer queryHql = new StringBuffer("select");
		queryHql.append(" a.id,");
		queryHql.append(" a.create_date createDate,");
		queryHql.append(" a.modify_date modifyDate,");
		queryHql.append(" a.user_name userName,");
		queryHql.append(" a.real_name realName,");
		queryHql.append(" a.community_number communityNumber,");
		queryHql.append(" a.room_number roomNumber,");
		queryHql.append(" a.repair_content repairContent,");
		queryHql.append(" a.repair_images repairImages,");
		queryHql.append(" a.status,");
		queryHql.append(" a.reserve_type reserveType,");
		queryHql.append(" a.expect_date expectDate,");
		queryHql.append(" a.expect_time expectTime,");
		queryHql.append(" a.apply_date applyDate,");
		queryHql.append(" a.process_user processUser,");
		queryHql.append(" a.process_date processDate,");
		queryHql.append(" a.warn_status warnStatus,");
		queryHql.append(" a.remark,");
		queryHql.append(" c.community_name communityName");
		queryHql.append(" from repair_content_record a");
		queryHql.append(" left join community c on c.community_number = a.community_number");
		queryHql.append(" where 1=1 and a.user_name =:userName");
		if(StringUtils.isNotBlank(communityNumber)){
			queryHql.append(" and a.community_number =:communityNumber");
			countHql.append(" and a.community_number =:communityNumber");
		}
		
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		queryCount.setParameter("userName", userName);
		typedQuery.setParameter("userName", userName);
		if(StringUtils.isNotBlank(communityNumber)){
			queryCount.setParameter("communityNumber",communityNumber);
			typedQuery.setParameter("communityNumber", communityNumber);
		}
		int count = 0;
		try{
			count = ((BigInteger)queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageResult<Map<String,Object>> pageResult = new PageResult<Map<String,Object>>();
		//查询所有
		pageResult.setTotalNum(count);
		//如果没有传分页，则返回全部
		if(pageNo==null || pageSize==null){
			pageNo=1;
			pageSize=count;
		}
		//分页查询
		typedQuery.setFirstResult((pageNo-1)*pageSize);
		typedQuery.setMaxResults(pageSize);
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = typedQuery.getResultList();
		pageResult.setList(rows);
		return pageResult;
	}

}
