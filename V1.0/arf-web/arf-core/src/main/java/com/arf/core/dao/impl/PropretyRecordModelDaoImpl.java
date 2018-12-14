package com.arf.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.PropretyRecordModelDao;
import com.arf.core.entity.ParkRateRecordModel;
import com.arf.core.entity.PropretyRecordModel;
import com.arf.core.entity.TemporaryCarOrder;

/**
 * Dao - 物业费订单
 * 
 * @author arf  dg
 * @version 4.0
 */
@Repository("propretyRecordModelDaoImpl")
public class PropretyRecordModelDaoImpl extends BaseDaoImpl<PropretyRecordModel, Long> implements PropretyRecordModelDao {

	@Override
	public PropretyRecordModel selectByouttradeno(String out_trade_no) {
		
//		try {
			String jpql = "select propretyRecordModel from PropretyRecordModel propretyRecordModel where propretyRecordModel.out_trade_no = :out_trade_no";
			List<PropretyRecordModel> propretyRecordModels =entityManager.createQuery(jpql, PropretyRecordModel.class).setParameter("out_trade_no", out_trade_no).getResultList();
			PropretyRecordModel propretyRecordModel = (propretyRecordModels==null || propretyRecordModels.size()<=0)?null:propretyRecordModels.get(0);
			return propretyRecordModel;
//		} catch (Exception e) {
//			return null;
//		}
	}

	@Override
	public PropretyRecordModel selectByHouseId(String HouseId) {
	String jpql = "select propretyRecordModel from PropretyRecordModel propretyRecordModel where propretyRecordModel.houseId = :HouseId";
	List<PropretyRecordModel> propretyRecordModels =entityManager.createQuery(jpql, PropretyRecordModel.class).setParameter("houseId", HouseId).getResultList();
	PropretyRecordModel propretyRecordModel = (propretyRecordModels==null || propretyRecordModels.size()<=0)?null:propretyRecordModels.get(0);
	return propretyRecordModel;}
}
