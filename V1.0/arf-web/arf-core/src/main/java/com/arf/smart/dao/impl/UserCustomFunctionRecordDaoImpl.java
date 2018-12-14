package com.arf.smart.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.smart.dao.UserCustomFunctionRecordDao;
import com.arf.smart.entity.UserCustomFunctionRecord;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("userCustomFunctionRecordDao")
public class UserCustomFunctionRecordDaoImpl extends BaseDaoImpl<UserCustomFunctionRecord, Long> implements UserCustomFunctionRecordDao{

	@Override
	public UserCustomFunctionRecord findByUserName(String userName,String cityCode,String communityNumber) {
		StringBuffer sql = new StringBuffer("from UserCustomFunctionRecord where 1=1 and userName =:userName");
		if(StringUtils.isNotBlank(cityCode)){
			sql.append(" and cityCode =:cityCode");
		}else{
			sql.append(" and cityCode is null");
		}
		if(StringUtils.isNotBlank(communityNumber)){
			sql.append(" and communityNumber =:communityNumber");
		}else{
			sql.append(" and communityNumber is null");
		}
		TypedQuery<UserCustomFunctionRecord> query =entityManager.createQuery(sql.toString(),UserCustomFunctionRecord.class);
		query.setParameter("userName", userName);
		if(StringUtils.isNotBlank(cityCode)){
			query.setParameter("cityCode", cityCode);
		}
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber", communityNumber);
		}
		List<UserCustomFunctionRecord> list = query.getResultList();
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			return list.get(0);
		}
	}

}
