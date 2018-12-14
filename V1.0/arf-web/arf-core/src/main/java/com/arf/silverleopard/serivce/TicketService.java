package com.arf.silverleopard.serivce;

import com.arf.core.service.BaseService;
import com.arf.silverleopard.entity.Ticket;

public interface TicketService extends BaseService<Ticket, Long>{

	int findCount(String appId,Long uid);
}
