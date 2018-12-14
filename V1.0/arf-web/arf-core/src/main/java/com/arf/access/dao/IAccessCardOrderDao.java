package com.arf.access.dao;

import java.util.List;

import com.arf.access.entity.AccessCardOrder;
import com.arf.access.entity.AccessCardOrder.HadBound;
import com.arf.access.entity.AccessCardOrder.PayStatus;
import com.arf.core.dao.BaseDao;

public interface IAccessCardOrderDao extends BaseDao<AccessCardOrder, Long> {

	/**
	 * 根据用户名、绑定编号、支付状态、使用状态
	 * @param userName
	 * @return
	 */
	List<AccessCardOrder> findByUsernameRoomBoundNumberPayStatusHadBound(String userName,String roomBoundNumber,PayStatus payStatus,HadBound hadBound);

	/**
	 * 根据用户名、房间编号、支付状态、使用状态
	 * @param userName
	 * @param roomBoundNumber
	 * @param payStatus
	 * @param hadBound
	 * @return
	 */
	List<AccessCardOrder> findByUsernameRoomNumberPayStatusHadBound(String userName,String roomNumber,PayStatus payStatus,HadBound hadBound);

	
	/**
	 * 根据订单编号
	 * @param outTradeNo
	 * @return
	 */
	AccessCardOrder findByOutTradeNo(String outTradeNo);

}
