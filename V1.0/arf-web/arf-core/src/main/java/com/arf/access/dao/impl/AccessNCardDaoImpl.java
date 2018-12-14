package com.arf.access.dao.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessNCardDao;
import com.arf.access.entity.AccessNCard;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessNCardDao")
public class AccessNCardDaoImpl extends BaseDaoImpl<AccessNCard, Long> implements IAccessNCardDao{

	@Override
	public List<AccessNCard> findByRoomNumberAndCardNumber(String roomNumber, String cardNumber) {
		StringBuffer hql = new StringBuffer(" from AccessNCard a where 1=1 and a.roomNumber =:roomNumber ");
		if(StringUtils.isNotBlank(cardNumber)){
			hql.append(" and a.cardNumber =:cardNumber ");
		}
		TypedQuery<AccessNCard> query = entityManager.createQuery(hql.toString(), AccessNCard.class);
		query.setParameter("roomNumber", roomNumber);
		if(StringUtils.isNotBlank(cardNumber)){
			query.setParameter("cardNumber", cardNumber);
		}
		return query.getResultList();
	}

	@Override
	public int findByRoomNumber(String roomNumber) {
		StringBuffer countHql = new StringBuffer("select ifnull(count(id),0) as COUNT from access_n_card a");
		countHql.append(" where 1=1 and a.room_number=:roomNumber");
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		queryCount.setParameter("roomNumber", roomNumber);
		int count = 0;
		try{
			count = ((BigInteger)queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public AccessNCard findByCardNumber(String cardNumber) {
		StringBuffer hql = new StringBuffer(" from AccessNCard a where 1=1 and a.cardNumber =:cardNumber ");
		TypedQuery<AccessNCard> query = entityManager.createQuery(hql.toString(), AccessNCard.class);
		query.setParameter("cardNumber", cardNumber);
		List<AccessNCard> list =query.getResultList();
		return list.isEmpty()?null:list.get(0);
	}

	@Override
	public List<AccessNCard> findByRoomList(List<String> roomList) {
		if(CollectionUtils.isEmpty(roomList)){
			return null;
		}
		StringBuffer hql = new StringBuffer(" from AccessNCard a where 1=1 and a.roomNumber in(:roomList) ");
		TypedQuery<AccessNCard> query = entityManager.createQuery(hql.toString(), AccessNCard.class);
		query.setParameter("roomList", roomList);
		return query.getResultList();
	}

}
