package com.arf.advertisements.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.advertisements.dao.IPopupAdInfoDao;
import com.arf.advertisements.entity.PopupAdInfo;
import com.arf.advertisements.entity.PopupAdInfo.PopupStatus;
import com.arf.advertisements.entity.PopupAdInfo.PopupType;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("popupAdInfoDao")
public class PopupAdInfoDaoImpl extends BaseDaoImpl<PopupAdInfo, Long> implements IPopupAdInfoDao{

	@Override
	public List<PopupAdInfo> findByPopupTypeAndStatus(PopupType popupType, PopupStatus popupStatus) {
		StringBuffer hql = new StringBuffer("select ad from PopupAdInfo ad where 1=1 and ad.popupStatus = :popupStatus ");
		if(popupType!=null){
			hql.append(" and ad.popupType=:popupType");
		}
		hql.append(" and ad.popupStartTime <=:nowDate");
		hql.append(" and ad.popupEndTime >=:nowDate");
		TypedQuery<PopupAdInfo> typedQuery = super.entityManager.createQuery(hql.toString(), PopupAdInfo.class);
		
		typedQuery.setParameter("popupStatus", popupStatus);
		if(popupType!=null){
			typedQuery.setParameter("popupType", popupType);
		}
		typedQuery.setParameter("nowDate", new Date());
		
		List<PopupAdInfo> list = typedQuery.getResultList();
		return list;
	}

}
