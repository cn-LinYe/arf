package com.arf.silverleopard.dao;

import com.arf.core.dao.BaseDao;
import com.arf.silverleopard.entity.Ticket;

public interface TicketDao extends BaseDao<Ticket, Long>{

	int findCount(String appId,Long uid);
}
