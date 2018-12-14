package com.arf.platform.service;

import java.util.Date;
import java.util.List;

import com.arf.base.PageResult;
import com.arf.core.AppMessage;
import com.arf.core.service.BaseService;
import com.arf.platform.entity.RStoprecord;
import com.arf.platform.search.StoprecordCondition;

/**
 * Service - RStoprecord表
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface RStoprecordService extends BaseService<RStoprecord, Long> {

	/**
	 * 根据小区编号，车牌号，来车时间，走车时间，查询停车收费记录
	 * @param communityNo
	 * @param license
	 * @param arriveTime
	 * @param leaveTime
	 */
	RStoprecord findByComLicArrLeav(String communityNo, String license, Date arriveTime, Date leaveTime);
	
	/**
	 * 查询最近一条未支付的订单记录
	 * @param communityNo
	 * @param license
	 * @param arriveTime
	 * @return
	 */
	RStoprecord findLatestNotPaidRecord(String communityNo, String license, Date arriveTime);
	
	/**
	 * 查询所有未支付并且使用了积分或者代金券的的订单记录
	 * @return
	 */
	List<RStoprecord> findOrderNotPaid(Date time);
	
	void saveOrUpdate(RStoprecord record);
	
	/**
	 * 通过商家订单编号来查找停车收费记录
	 * @param outTradeNo
	 * @return
	 */
	RStoprecord findByOutTradeNo(String outTradeNo);
	
	/**
	 * 查询某次来车已经支付的订单记录
	 * @param communityNo
	 * @param license
	 * @param arriveTime
	 * @return
	 */
	List<RStoprecord> findAllPaidRecord(String communityNo, String license, Date arriveTime);
	
	/**
	 * 查询某次来车已经支付的订单记录
	 * @param communityNo
	 * @param license
	 * @param arriveTime
	 * @return
	 */
	RStoprecord findRecentPaidRecord(String communityNo, String license, Date arriveTime);

	/**
	 * 分页查询停车记录
	 * @param condition
	 * @return
	 */
	PageResult<RStoprecord> myStopRecords(StoprecordCondition condition);
	
	/**
	 * 查询用户临停车历史订单信息
	 * @param vo
	 * @param result
	 * @return
	 */
	AppMessage reqTemporaryParkRecord(String userName,int pageSize,int pageNo , AppMessage result);
	
	/**
	 * 查询当前r_stoprecord表有几个月份的数据
	 * @return 返回的月份格式yyyy-MM
	 */
	List<String> findMonthsInDb();
	
	/**
	 * 将r_stoprecord表中的历史数据进行分表操作
	 * @param subTableName
	 * @param createStart 分表的数据记录开始日期 'yyyy-MM-dd HH:mm:ss'
	 * @param createEnd分表的数据记录截止日期 'yyyy-MM-dd HH:mm:ss'
	 * @param count 每批次迁移的数据量大小
	 * @return
	 */
	int migrateRStoprecords(String subTableName,String createStart,String createEnd,int count);
	
	/**根据停车场编号统计今天出场数量
	 * @param communityNo
	 * @return
	 */
	int findAppearanceTodayByParkingId(String communityNo);
	
}
