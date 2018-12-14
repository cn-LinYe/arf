package com.arf.access.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessCardOrderDao;
import com.arf.access.entity.AccessCardOrder;
import com.arf.access.entity.AccessCardOrder.HadBound;
import com.arf.access.entity.AccessCardOrder.PayStatus;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessCardOrderDaoImpl")
public class AccessCardOrderDaoImpl extends BaseDaoImpl<AccessCardOrder, Long>
		implements IAccessCardOrderDao {

	@Override
	public List<AccessCardOrder> findByUsernameRoomBoundNumberPayStatusHadBound(String userName,String roomBoundNumber,PayStatus payStatus,HadBound hadBound) {
		StringBuffer hql = new StringBuffer(" from AccessCardOrder a where 1=1 and a.userName =:userName ");
		hql.append(" and a.payStatus =:payStatus and a.roomBoundNumber = :roomBoundNumber");
		if(hadBound!=null){
			hql.append(" and a.hadBound =:hadBound ");
		}
		TypedQuery<AccessCardOrder> query = entityManager.createQuery(hql.toString(), AccessCardOrder.class);
		query.setParameter("userName", userName);
		query.setParameter("payStatus", payStatus);
		if(hadBound!=null){
			query.setParameter("hadBound", hadBound);
		}
		query.setParameter("roomBoundNumber", roomBoundNumber);
		return query.getResultList();
	}

	@Override
	public AccessCardOrder findByOutTradeNo(String outTradeNo) {
		StringBuffer hql = new StringBuffer(" from AccessCardOrder a where 1=1 and a.outTradeNo =:outTradeNo ");
		TypedQuery<AccessCardOrder> query = entityManager.createQuery(hql.toString(), AccessCardOrder.class);
		query.setParameter("outTradeNo", outTradeNo);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<AccessCardOrder> findByUsernameRoomNumberPayStatusHadBound(String userName, String roomNumber,
			PayStatus payStatus, HadBound hadBound) {
		StringBuffer hql = new StringBuffer(" from AccessCardOrder a where 1=1 ");
		if(StringUtils.isNotBlank(userName)){
			hql.append(" and a.userName =:userName ");
		}
		hql.append(" and a.payStatus =:payStatus and a.roomNumber = :roomNumber");
		if(hadBound!=null){
			hql.append(" and a.hadBound =:hadBound ");
		}
		TypedQuery<AccessCardOrder> query = entityManager.createQuery(hql.toString(), AccessCardOrder.class);
		query.setParameter("payStatus", payStatus);
		if(StringUtils.isNotBlank(userName)){
			query.setParameter("userName", userName);
		}
		if(hadBound!=null){
			query.setParameter("hadBound", hadBound);
		}
		query.setParameter("roomNumber", roomNumber);
		return query.getResultList();
	}

}
