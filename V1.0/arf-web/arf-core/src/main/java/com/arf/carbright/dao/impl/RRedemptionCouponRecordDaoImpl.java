package com.arf.carbright.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.IRRedemptionCouponRecordDao;
import com.arf.carbright.entity.RRedemptionCouponRecord;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("rRedemptionCouponRecordDao")
public class RRedemptionCouponRecordDaoImpl extends BaseDaoImpl<RRedemptionCouponRecord,Long> implements IRRedemptionCouponRecordDao {

	@Override
	public RRedemptionCouponRecord findByRedemptionCode(String redemptionCode) {
		String hql = "from RRedemptionCouponRecord r where r.redemptionCode = :redemptionCode";
		List<RRedemptionCouponRecord> records = this.entityManager.createQuery(hql, RRedemptionCouponRecord.class).setParameter("redemptionCode", redemptionCode).getResultList();
		if(CollectionUtils.isEmpty(records)){
			return null;
		}else{
			return records.get(0);
		}
	}

	@Override
	public int statisticsCountByGetCouponId(String getCouponId) {
		String hql = "select count(1) from RRedemptionCouponRecord r where r.getCouponId = :getCouponId";
		Long statisticsCount = this.entityManager.createQuery(hql, Long.class)
				.setParameter("getCouponId", (getCouponId!=null)?Integer.valueOf(getCouponId):0).getSingleResult();
		if(statisticsCount!=null){
			return statisticsCount.intValue();
		}
		return 0;
	}

}
