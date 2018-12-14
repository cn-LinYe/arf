package com.arf.gas.dao;

import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.gas.entity.RefuelGasCardOrder;

public interface IRefuelGasCardOrderDao extends BaseDao<RefuelGasCardOrder, Long>{

	/**
	 * 根据订单编号查询
	 * @param orderNo
	 * @return
	 */
	RefuelGasCardOrder findByOrderNo(String orderNo);
	
	/**
	 * 根据
	 * @param gunNum
	 * @param userName
	 * @return
	 */
	RefuelGasCardOrder findByCarNumBusiness(Integer gunNum,String userName);
	/**
	 * 统计开卡数量和金额
	 * @param carNumber
	 * @param userName
	 * @return
	 */
	Map<String,Object> statisticsCardAmountAndNumber(String startTime,String endTime,Integer gasNum);
	
	/**
	 * 统计会员开卡数量
	 * @param startTime
	 * @param endTime
	 * @param gasNum
	 * @return
	 */
	Map<String,Object> statisticsRefulesMember(String startTime,String endTime,Integer gasNum);
	
	/**
	 * 根据条件查询
	 * @param orderNo
	 * @param orderStatus
	 * @param memberUserName
	 * @param startTime
	 * @param endTime
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult<Map<String,Object>> findByCondition(Integer gasNum,Integer audit,String orderNo,Integer orderStatus,String memberUserName,String startTime,String endTime,Integer pageNo ,Integer pageSize);


	/**
	 * 查找批量开卡订单
	 * @param orderNo
	 * @param orderStatus
	 * @param memberUserName
	 * @param startTime
	 * @param endTime
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult<RefuelGasCardOrder> findByCondition(Integer gasNum,String orderNo,Integer orderStatus,String memberUserName,String startTime,String endTime,Integer pageNo ,Integer pageSize);

}
