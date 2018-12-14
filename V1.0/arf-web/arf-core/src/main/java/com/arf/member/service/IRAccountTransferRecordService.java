package com.arf.member.service;

import java.util.Date;
import java.util.List;

import com.arf.base.PageResult;
import com.arf.core.service.BaseService;
import com.arf.member.entity.RAccountTransferRecord;
import com.arf.member.search.RAccountTransferRecordCondition;
import com.arf.platform.entity.SMemberAccount;

public interface IRAccountTransferRecordService extends BaseService<RAccountTransferRecord, Long> {

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
	 * 生成电子钱包流转记录
	 * @param sMemberAccount 支付之后的电子账户
	 * @param out_trade_no 订单号，可为空
	 * @param fee 消费金额（请求在调用方法之前确保fee > 0，除非真要生成消费金额为0的流转记录）
	 * @param communityNo 小区编号，可为空
	 * @param userType 用户类型
	 * @param type 收入/消费类型
	 * @param comment 注释说明
	 * @return
	 * @see com.arf.member.entity.RAccountTransferRecord.UserType
	 * @see com.arf.member.entity.RAccountTransferRecord.Type
	 */
	RAccountTransferRecord genAcoountRecords(SMemberAccount sMemberAccount,String out_trade_no,double fee, String communityNo,Byte userType, Byte type, String comment);

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
