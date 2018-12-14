package com.arf.ecc.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.ecc.dao.ISettlementAccountDao;
import com.arf.ecc.entity.SettlementAccount;
import com.arf.ecc.service.SettlementAccountService;

@Repository("settlementAccountServiceImpl")
public class SettlementAccountServiceImpl extends BaseServiceImpl<SettlementAccount, Long>
		implements SettlementAccountService {

	@Resource(name="settlementAccountDaoImpl")
	ISettlementAccountDao settlementAccountDao;
	
	@Override
	protected BaseDao<SettlementAccount, Long> getBaseDao() {
		return settlementAccountDao;
	}
	
	public SettlementAccount findByNumber(String number,SettlementAccount.Type type){
		return settlementAccountDao.findByNumber(number, type);
	}
}
