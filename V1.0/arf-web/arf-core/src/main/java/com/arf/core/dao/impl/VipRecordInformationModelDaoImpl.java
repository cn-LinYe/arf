package com.arf.core.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import com.arf.core.dao.VipRecordInformationModelDao;
import com.arf.core.entity.VipRecordInformationModel;

/**
 * Dao - 开通vip订单纪录表
 * 
 * @author arf
 * @version 4.0
 */
@Repository("vipRecordInformationModelDaoImpl")
public class VipRecordInformationModelDaoImpl extends BaseDaoImpl<VipRecordInformationModel, Long> implements VipRecordInformationModelDao {

	@Override
	public VipRecordInformationModel selectByouttradeno(String out_trade_no) {
		if(StringUtils.isEmpty(out_trade_no)){
			return null;
		}
//		try {
//			String jpql = "select vipRecordInformation from VipRecordInformationModel vipRecordInformation where vipRecordInformation.out_trade_no = :out_trade_no";
//			return entityManager.createQuery(jpql, VipRecordInformationModel.class).setParameter("out_trade_no", out_trade_no).getSingleResult();
			String hql = "from com.arf.core.entity.VipRecordInformationModel where out_trade_no ='" + out_trade_no + "'";
			List<VipRecordInformationModel> list=entityManager.createQuery(hql,VipRecordInformationModel.class).getResultList();
			return (list==null||list.isEmpty())?null:list.get(0);
//		} catch (Exception e) {
//			System.out.println("@@@@@@@@@@@@@@@@@");
//			return null;
//		}
	}

	@Override
	public List<VipRecordInformationModel> selectUnionID(String trade_status, int isRed, int pay_choice) { 
		String jpql = "from com.arf.core.entity.VipRecordInformationModel where trim(trade_status) = '9000' AND isRed>0 AND pay_choice=3";
//		String jpql = "select vipRecordInformation from VipRecordInformationModel vipRecordInformation where vipRecordInformation.trade_status = :trade_status and vipRecordInformation.isRed = :isRed and vipRecordInformation.pay_choice = :pay_choice";
		return entityManager.createQuery(jpql, VipRecordInformationModel.class).getResultList();
	}

	@Override
	public List<VipRecordInformationModel> selectNotSendRedPkgByUnionID(String unionId) {
		this.clear();
		String jpql = "from com.arf.core.entity.VipRecordInformationModel where trim(trade_status) = '9000' AND isRed>0 AND pay_choice=3 and unionId = :unionId";
		return entityManager.createQuery(jpql, VipRecordInformationModel.class).setParameter("unionId", unionId).getResultList();
	}
	
	public List<VipRecordInformationModel> findNotSendRedPkg(int payChoice, Date createStart, Date createEnd){
		StringBuffer sqlSb = new StringBuffer(" from com.arf.core.entity.VipRecordInformationModel where trim(trade_status) = '9000' AND isRed>0 AND pay_choice= :payChoice ");
		if(createStart != null){
			sqlSb.append(" and create_date >= :createStart ");
		}
		if(createEnd != null){
			sqlSb.append(" and create_date <= :createEnd ");
		}
		TypedQuery<VipRecordInformationModel> query = this.entityManager.createQuery(sqlSb.toString(), VipRecordInformationModel.class);
		query.setParameter("payChoice", payChoice);
		if(createStart != null){
			query.setParameter("createStart", createStart);
		}
		if(createEnd != null){
			query.setParameter("createEnd", createEnd);
		}
		return query.getResultList();
	}

	@Override
	public List<VipRecordInformationModel> checkUserBuyEccPackage(String trade_status, String userName,
			Long eccPackageConfigId) {
		String jpql = "from com.arf.core.entity.VipRecordInformationModel where trade_status=:tradeStatus AND user_name=:userName AND eccPackageConfigId=:eccPackageConfigId";
		return entityManager.createQuery(jpql, VipRecordInformationModel.class)
				.setParameter("tradeStatus", trade_status)
				.setParameter("userName", userName)
				.setParameter("eccPackageConfigId", eccPackageConfigId)
				.getResultList();
	}
}
