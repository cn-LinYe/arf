package com.arf.silverleopard.dao.impl;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.silverleopard.dao.TicketDao;
import com.arf.silverleopard.entity.Ticket;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository("ticketDao")
public class TicketDaoImpl extends BaseDaoImpl<Ticket, Long> implements TicketDao{

	@Override
	public int findCount(String appId, Long uid) {
		if(StringUtils.isBlank(appId) || uid == null) {
			return 1;
		}
		StringBuffer countHql = new StringBuffer("select ifnull(count(1),0) as COUNT from p_tic_ket a");
		countHql.append(" where 1=1 and a.app_id=:appId");
		countHql.append(" and a.uid=:uid");
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		queryCount.setParameter("appId", appId);
		queryCount.setParameter("uid", uid);
		String o = queryCount.getSingleResult().toString();
		if(StringUtils.isBlank(o)) {
			return 0;
		}else {
			return Integer.parseInt(o);
		}
	}

}
