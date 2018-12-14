package com.arf.installment.smartlock.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.installment.smartlock.dao.ILockInstallmentFundsOrderDao;
import com.arf.installment.smartlock.entity.LockInstallmentFundsOrder;

@Repository("IClearGoldCardBalanceLogDao")
public class LockInstallmentFundsOrderDaoImpl extends BaseDaoImpl<LockInstallmentFundsOrder, Long> implements ILockInstallmentFundsOrderDao{

}
