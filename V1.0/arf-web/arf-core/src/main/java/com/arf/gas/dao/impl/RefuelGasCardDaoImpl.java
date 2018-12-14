package com.arf.gas.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gas.dao.IRefuelGasCardDao;
import com.arf.gas.entity.RefuelGasCard;
import com.arf.gas.entity.RefuelGasMemberDataChange;

@Repository("refuelGasCardDaoImpl")
public class RefuelGasCardDaoImpl extends BaseDaoImpl<RefuelGasCard, Long> implements IRefuelGasCardDao{

	@Override
	public RefuelGasCard findByUserName(String userName, Integer gasNum, Integer businessNum) {
		String hql = "from RefuelGasCard where userName = :userName and gasNum=:gasNum and businessNum=:businessNum";
		List<RefuelGasCard> list = entityManager.createQuery(hql,RefuelGasCard.class)
				.setParameter("userName", userName)
				.setParameter("gasNum", gasNum)
				.setParameter("businessNum", businessNum)
				.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	@Override
	public RefuelGasCard findByCardNumber(String cardNumber) {
		String hql = "from RefuelGasCard where cardNumber = :cardNumber";
		List<RefuelGasCard> list = entityManager.createQuery(hql,RefuelGasCard.class)
				.setParameter("cardNumber", cardNumber)
				.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void addCars(List<RefuelGasCard> refuelGasCards) {
		StringBuffer sql=new StringBuffer("INSERT INTO refuel_gas_card  ") ;
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sql.append("(create_date,");
        sql.append("modify_date,");
        sql.append("version,");
        sql.append("business_num,");
        sql.append("card_amount,");
        sql.append("card_number,");
        sql.append("card_status,");
        sql.append("consumption_amount,");
        sql.append("gas_num,");
        sql.append("recharge_total_amount,");
        sql.append("user_name");
        sql.append(")values");
		for (int i = 0; i < refuelGasCards.size(); i++) {
			RefuelGasCard card = refuelGasCards.get(i);
			sql.append("('");
			sql.append(sim.format(new Date())+"','");
			sql.append(sim.format(new Date())+"','");
			sql.append(1+"','");
			sql.append(card.getBusinessNum()+"','");
			sql.append(card.getCardAmount()+"','");
			sql.append(card.getCardNumber()+"','");
			sql.append(card.getCardStatus().ordinal()+"','");
			sql.append(card.getConsumptionAmount()+"','");
			sql.append(card.getGasNum()+"','");
			sql.append(card.getCardAmount()+"','");
			sql.append(card.getUserName());
			sql.append("')");
			sql.append(",");
		}
		String sqls =sql.toString().substring(0, sql.toString().length()-1);
		 entityManager.createNativeQuery(sqls).executeUpdate();
	}
}
