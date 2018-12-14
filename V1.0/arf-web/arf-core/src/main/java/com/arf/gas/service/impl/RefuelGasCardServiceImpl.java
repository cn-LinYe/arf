package com.arf.gas.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gas.dao.IRefuelGasCardDao;
import com.arf.gas.entity.RefuelGasCard;
import com.arf.gas.service.IRefuelGasCardService;
@Service("refuelGasCardServiceImpl")
public class RefuelGasCardServiceImpl extends BaseServiceImpl<RefuelGasCard, Long> implements IRefuelGasCardService{

	@Resource(name="refuelGasCardDaoImpl")
	IRefuelGasCardDao refuelGasCardDao;
	@Override
	protected BaseDao<RefuelGasCard, Long> getBaseDao() {
		return refuelGasCardDao;
	}
	@Override
	public RefuelGasCard findByUserName(String userName, Integer gasNum, Integer businessNum) {
		return refuelGasCardDao.findByUserName(userName, gasNum, businessNum);
	}
	@Override
	public RefuelGasCard findByCardNumber(String cardNumber) {
		return refuelGasCardDao.findByCardNumber(cardNumber);
	}
	@Override
	public void addCars(List<RefuelGasCard> refuelGasCards) {
		refuelGasCardDao.addCars(refuelGasCards);
	}

}
