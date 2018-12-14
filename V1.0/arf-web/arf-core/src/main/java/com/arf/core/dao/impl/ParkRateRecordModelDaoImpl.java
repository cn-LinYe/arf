package com.arf.core.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.ParkRateRecordModelDao;
import com.arf.core.entity.ParkRateRecordModel;

/**
 * Dao - 月租车
 * 
 * @author arf  dg
 * @version 4.0
 */
@Repository("parkRateRecordModelDaoImpl")
public class ParkRateRecordModelDaoImpl extends BaseDaoImpl<ParkRateRecordModel, Long> implements ParkRateRecordModelDao {

	@Override
	public ParkRateRecordModel selectByouttradeno(String out_trade_no) {
		
		
		String jpql = "select parkRateRecordModel from ParkRateRecordModel parkRateRecordModel where parkRateRecordModel.out_trade_no = :out_trade_no";
		List<ParkRateRecordModel> temporaryLicensePlates =entityManager.createQuery(jpql, ParkRateRecordModel.class).setParameter("out_trade_no", out_trade_no).getResultList();
		ParkRateRecordModel temporaryLicensePlate = (CollectionUtils.isEmpty(temporaryLicensePlates))?null:temporaryLicensePlates.get(0);
		return temporaryLicensePlate;
		
	}

	@Override
	public List<ParkRateRecordModel> findByUserName(String userName) {
		String jpql = "select parkRateRecordModel from ParkRateRecordModel parkRateRecordModel where parkRateRecordModel.userName = :userName";
		return entityManager.createQuery(jpql, ParkRateRecordModel.class).setParameter("userName", userName).getResultList();
	}

	@Override
	public List<ParkRateRecordModel> findTodayRecord(Date startDate, Date endDate,List<String> communityList) {
		StringBuffer sql = new StringBuffer("from ParkRateRecordModel where tradeStatus = :tradeStatus");
		sql.append(" and paidDate>=:startDate and paidDate <=:endDate and communityNumber in (:communityList)");
		TypedQuery<ParkRateRecordModel> query=entityManager.createQuery(sql.toString(),ParkRateRecordModel.class);
		query.setParameter("tradeStatus", "9000");
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("communityList",communityList);
		List<ParkRateRecordModel> list = query.getResultList();
		return list;
	}
}
