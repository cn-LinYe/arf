package com.arf.gas.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.gas.entity.RefuelGasRecharge;

public interface IRefuelGasRechargeDao extends BaseDao<RefuelGasRecharge, Long>{

	/**
	 * 根据订单编号
	 * @param orderNo
	 * @return
	 */
	public RefuelGasRecharge findByOrderNo(String orderNo);
	
	/**
	 * 根据用户名，加油站，时间查询会员充值订单信息列表（可供加油站或会员查询）
	 * @param userName
	 * @param gasNum
	 * @param businessNum
	 * @param pageSize
	 * @param pageNo
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public PageResult<Map<String, Object>> findRechargeByUserNameAndGasNum(String userName,Integer gasNum,Integer businessNum,
			Integer pageSize,Integer pageNo,Date startTime,Date endTime,Integer orderStatus);
	
	/**
	 * 根据油卡编号查询充值订单
	 * @param userName
	 * @param gasNum
	 * @param businessNum
	 * @return
	 */
	List<RefuelGasRecharge> findByCardNumber(List<String> cardNumbers);
}
