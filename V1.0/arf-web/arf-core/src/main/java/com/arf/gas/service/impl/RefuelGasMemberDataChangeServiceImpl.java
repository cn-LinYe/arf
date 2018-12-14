package com.arf.gas.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gas.dao.IRefuelGasMemberDataChangeDao;
import com.arf.gas.entity.RefuelGasMemberDataChange;
import com.arf.gas.service.IRefuelGasMemberDataChangeService;

@Service("refuelGasMemberDataChangeServiceImpl")
public class RefuelGasMemberDataChangeServiceImpl extends BaseServiceImpl<RefuelGasMemberDataChange, Long> implements IRefuelGasMemberDataChangeService{

	@Resource(name="refuelGasMemberDataChangeDaoImpl")
	IRefuelGasMemberDataChangeDao refuelGasMemberDataChangeDao;
	@Override
	protected BaseDao<RefuelGasMemberDataChange, Long> getBaseDao() {
		return refuelGasMemberDataChangeDao;
	}
	@Override
	public List<RefuelGasMemberDataChange> findByUserNameGasNum(String userName, Integer gasNum, Integer businessNum) {
		return refuelGasMemberDataChangeDao.findByUserNameGasNum(userName, gasNum, businessNum);
	}
	@Override
	public List<Map<String, Object>> findByUserNameGasNumInfo(String userName, Integer gasNum, Integer businessNum,
			Integer changeStatus) {
		return refuelGasMemberDataChangeDao.findByUserNameGasNumInfo(userName, gasNum, businessNum, changeStatus);
	}

}
