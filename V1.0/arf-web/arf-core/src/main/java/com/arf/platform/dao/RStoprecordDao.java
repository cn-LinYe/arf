package com.arf.platform.dao;

import java.util.Date;
import java.util.List;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.platform.entity.RStoprecord;
import com.arf.platform.search.StoprecordCondition;

/**
 * Dao - RStoprecord表
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface RStoprecordDao extends BaseDao<RStoprecord, Long>{

	/**
	 * 根据小区编号，车牌号，来车时间，走车时间，查询停车收费记录
	 * @param communityNo
	 * @param license
	 * @param arriveTimeStr
	 * @param leaveTimeStr
	 * @return
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
	 * 查询所有未支付的订单记录
	 * @return
	 */
	List<RStoprecord> findOrderNotPaid(Date time);

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
	 * 分页查询用户临时车停车记录
	 * @param condition
	 * @return
	 */
	PageResult<RStoprecord> myTemporaryStopRecords(String userName,int pageSize,int pageNo);
	
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
	int migrateRStoprecords(String subTableName,String createStart,String createEnd,long first,int count);
	
	/**
	 * 通过limit关键字批量删除count条数据
	 * @param startDate
	 * @param endDate
	 * @param count
	 * @return 实际删除条数
	 */
	int deleteBetweenCreateDate(String startDate,String endDate,int count);
	
	/**根据停车场编号统计今天出场数量
	 * @param communityNo
	 * @return
	 */
	int findAppearanceTodayByParkingId(String communityNo);
}
