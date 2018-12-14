package com.arf.gas.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gas.dao.IRefuelGasMemberDataChangeDao;
import com.arf.gas.entity.RefuelGasMemberDataChange;

import javassist.convert.Transformer;

@Repository("refuelGasMemberDataChangeDaoImpl")
public class RefuelGasMemberDataChangeDaoImpl extends BaseDaoImpl<RefuelGasMemberDataChange, Long> implements IRefuelGasMemberDataChangeDao{

	@Override
	public List<RefuelGasMemberDataChange> findByUserNameGasNum(String userName, Integer gasNum, Integer businessNum) {
		String hql = "from RefuelGasMemberDataChange where userName = :userName and gasNum=:gasNum and businessNum=:businessNum";
		List<RefuelGasMemberDataChange> list = entityManager.createQuery(hql,RefuelGasMemberDataChange.class)
				.setParameter("userName", userName)
				.setParameter("gasNum", gasNum)
				.setParameter("businessNum", businessNum)
				.getResultList();
		return list;
	}

	@Override
	public List<Map<String, Object>> findByUserNameGasNumInfo(String userName, Integer gasNum, Integer businessNum,
			Integer changeStatus) {
		StringBuffer sql =new StringBuffer("SELECT r.id,r.user_name as userName ,r.driving_photo as drivingPhoto ,r.license_plate_number as licensePlateNumber ,");
		sql.append(" r.change_result as changeResult ,r.change_status as changeStatus ,r.gas_num as gasNum	 ,r.business_num as businessNum	,");
		sql.append(" r.second_review_result as secondReviewResult ,r.protocol_photo as protocolPhoto	,r.gas_num as gasName ,c.card_number as cardNumber ,");
		sql.append(" c.card_amount as cardAmount ,m.consumption_amount as consumptionAmount ,p.business_name as businessName,p.address as businessAddress ,");
		sql.append(" p.business_pic as businessPic ,p.business_description as businessDescription ,p.lat ,p.lng ");
		sql.append(" FROM refuel_gas_member_data_change r ");
		sql.append(" LEFT JOIN refuel_gas_card c ON c.gas_num =r.gas_num AND c.user_name=r.user_name");
		sql.append(" LEFT JOIN p_business p ON p.business_num =r.business_num");
		sql.append(" LEFT JOIN refuel_gas_member m ON m.user_name=r.user_name");
		sql.append(" where 1=1");
		if (StringUtils.isNotBlank(userName)) {
			sql.append(" AND r.user_name =:userName");
		}
		if (gasNum!=null) {
			sql.append(" and r.gas_num=:gasNum ");
		}
		if (businessNum!=null) {
			sql.append(" AND r.business_num=:businessNum ");
		}
		if (changeStatus!=null) {
			sql.append(" AND r.change_status =:changeStatus ");
		}
		
		Query query=entityManager.createNativeQuery(sql.toString());
		if (StringUtils.isNotBlank(userName)) {
			query.setParameter("userName", userName);
		}
		if (gasNum!=null) {
			query.setParameter("gasNum", gasNum);
		}
		if (businessNum!=null) {
			query.setParameter("businessNum", businessNum);
		}
		if (changeStatus!=null) {
			query.setParameter("changeStatus", changeStatus);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list=query.getResultList();
		return list;
	}
}










