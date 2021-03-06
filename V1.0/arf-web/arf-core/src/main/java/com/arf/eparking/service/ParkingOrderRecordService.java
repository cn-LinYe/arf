package com.arf.eparking.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.carbright.search.ParkingOrderRecordCondition;
import com.arf.core.service.BaseService;
import com.arf.eparking.entity.ParkingOrderRecord;

public interface ParkingOrderRecordService extends BaseService<ParkingOrderRecord, Long> {
	
	/**
	 * 通过订单编号来查询订单记录
	 * @param orderNo
	 * @return
	 */
	ParkingOrderRecord findByOrderNo(String orderNo);

	/**
	 * 根据来车时间和用户名查询订单(判断是否重复预定)
	 * @param userName
	 * @param arriveTime
	 * @return
	 */
	ParkingOrderRecord findByUserNameAndArriveTime(String userName,Date arriveTime);

	/**
	 * 根据查询条件查询我的订单,
	 *  status 为空：查询所有记录，0：待支付，1：进行中，2：已完成，3：已取消
	 * @param condition
	 * @return
	 */
	PageResult<Map<String,Object>> findListByCondition(ParkingOrderRecordCondition condition);
	
	/**
	 * 根据用户名和状态status查询订单
	 * @param userName
	 * @param status
	 * @return
	 */
	ParkingOrderRecord findByUserName(String userName,Integer status);

	/**
	 * 查询可取消的订单
	 * bookTime 小于当前时间
	 * status 订单状态为：null, 2等待到达，3已延时
	 * @return
	 */
	List<ParkingOrderRecord> findOrderEnableCancel();
	
	/**
	 * 查询可续约的订单
	 * status 订单状态为：2等待到达
	 * @return
	 */
	List<ParkingOrderRecord> findOrderEnableExtension();
	
	/**
	 * 根据条件查询支付的停车订单
	 * @param condition
	 * @return
	 */
	PageResult<ParkingOrderRecord> findByCondition(ParkingOrderRecordCondition condition);

	/**
	 * 根据用户名查询未关联黑名单的违约订单
	 * @param userName
	 * @return
	 */
	List<ParkingOrderRecord> findBreakContract(String userName);

	/**
	 * 根据车牌号查询在进行中的订单
	 * @param license 车牌号
	 * @param date 当前时间
	 * @return
	 */
	ParkingOrderRecord findByLicenArriveTime(String license, Date date);
	
	/**
	 * 根据用户名查询正在进行的业务
	 * @param userName
	 * @return
	 */
	List<ParkingOrderRecord> findOngoingOrderByUserName(String userName);
	
	/**
	 * 根据用户按时间倒序查询最后一个订单业务
	 * @param userName
	 * @param status ：status=FINISH 已完成的最后一单，status=ONGOING 正在进行的最后一单
	 * @return
	 */
	ParkingOrderRecord findOrderByUserName(String userName,String status);
}
