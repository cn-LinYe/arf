package com.arf.member.dao;

import java.util.Date;
import java.util.List;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.member.entity.RAccountTransferRecord;
import com.arf.member.search.RAccountTransferRecordCondition;

public interface IRAccountTransferRecordDao extends BaseDao<RAccountTransferRecord, Long> {

	/**
	 * 根据查询条件查询电子钱包流转记录
	 * @param condition
	 * @return
	 */
	PageResult<RAccountTransferRecord> findListByCondition(RAccountTransferRecordCondition condition);

	/**
	 * 查询当前用户某个时间段的消费行为
	 * @param userName
	 * @param userType
	 * @param startTime 可为空
	 * @param endTime 可为空
	 * @return
	 */
	List<RAccountTransferRecord> findAllExpenseList(String userName,Byte userType,Date startTime,Date endTime);

	/**
	 * 根据条件查询用户某个时间段的消费
	 * @param userName
	 * @param userType
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Double getExpenseByCondition(String userName, Byte userType,Date startTime, Date endTime);

	
}
