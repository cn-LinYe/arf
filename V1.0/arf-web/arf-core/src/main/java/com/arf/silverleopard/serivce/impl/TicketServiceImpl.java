package com.arf.silverleopard.serivce.impl;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.silverleopard.dao.TicketDao;
import com.arf.silverleopard.entity.Ticket;
import com.arf.silverleopard.serivce.TicketService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("ticketService")
public class TicketServiceImpl extends BaseServiceImpl<Ticket, Long> implements TicketService{

	@Resource(name = "ticketDao")
	private TicketDao ticketDao;
	
	@Override
	protected BaseDao<Ticket, Long> getBaseDao() {
		return ticketDao;
	}

	@Override
	public int findCount(String appId, Long uid) {
		return ticketDao.findCount(appId, uid);
	}

}
