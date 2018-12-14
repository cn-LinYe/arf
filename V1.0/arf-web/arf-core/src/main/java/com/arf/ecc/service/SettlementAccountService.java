package com.arf.ecc.service;

import com.arf.core.service.BaseService;
import com.arf.ecc.entity.SettlementAccount;

public interface SettlementAccountService extends BaseService<SettlementAccount, Long> {

	public SettlementAccount findByNumber(String number,SettlementAccount.Type type);
}
