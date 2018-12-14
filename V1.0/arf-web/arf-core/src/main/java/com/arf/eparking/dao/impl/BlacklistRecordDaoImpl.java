package com.arf.eparking.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.eparking.dao.BlacklistRecordDao;
import com.arf.eparking.entity.BlacklistRecord;
import com.arf.eparking.entity.OrderfeeRuleItem;
import com.arf.eparking.entity.BlacklistRecord.Status;
import com.arf.eparking.entity.BlacklistRecord.Type;
import com.arf.platform.entity.SMemberAccount;

@Repository("blacklistRecordDaoImpl")
public class BlacklistRecordDaoImpl extends BaseDaoImpl<BlacklistRecord, Long> implements BlacklistRecordDao {

	@Override
	public BlacklistRecord finByBlacklistNo(String blacklistNo) {
		String hql = "from com.arf.eparking.entity.BlacklistRecord where blacklistNo = :blacklistNo";
		List<BlacklistRecord> list = entityManager.createQuery(hql,BlacklistRecord.class).setParameter("blacklistNo", blacklistNo).getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	@Override
	public BlacklistRecord findByUserNameStatusAndType(String userName,Status status, Type type) {
		Assert.notNull(status,"状态不能为空");
		Assert.notNull(type,"类型不能为空");
		StringBuffer hql = new StringBuffer("from BlacklistRecord b where 1 = 1 and userName = :userName and status = :status and type = :type");
		TypedQuery<BlacklistRecord> query = entityManager.createQuery(hql.toString(), BlacklistRecord.class);
		query.setParameter("userName",userName);
		query.setParameter("status",status.ordinal());
		query.setParameter("type",type.ordinal());
		List<BlacklistRecord> list = query.getResultList();
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

}
