package com.arf.gas.dao;

import java.util.List;
import java.util.Map;

import com.arf.core.dao.BaseDao;
import com.arf.gas.entity.RefuelGasMemberDataChange;

public interface IRefuelGasMemberDataChangeDao extends BaseDao<RefuelGasMemberDataChange, Long>{
	/**
	 * 根据用户名商户编号油站编号查询
	 * @param userName
	 * @param gasNum
	 * @param businessNum
	 * @return
	 */
	List<RefuelGasMemberDataChange> findByUserNameGasNum(String userName,Integer gasNum ,Integer businessNum);
	
	/**
	 * 根据用户名商户编号油站编号查询
	 * @param userName
	 * @param gasNum
	 * @param businessNum
	 * @return
	 */
	List<Map<String,Object>> findByUserNameGasNumInfo(String userName,Integer gasNum ,Integer businessNum,Integer changeStatus);
	
	
}
