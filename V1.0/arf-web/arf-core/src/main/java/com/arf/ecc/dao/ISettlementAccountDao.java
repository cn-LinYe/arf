package com.arf.ecc.dao;

import com.arf.core.dao.BaseDao;
import com.arf.ecc.entity.SettlementAccount;

public interface ISettlementAccountDao extends BaseDao<SettlementAccount, Long> {

	public SettlementAccount findByNumber(String number,SettlementAccount.Type type);
	
}
