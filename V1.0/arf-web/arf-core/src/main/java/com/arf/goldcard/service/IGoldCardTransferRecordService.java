package com.arf.goldcard.service;

import java.math.BigDecimal;

import com.arf.base.PageResult;
import com.arf.core.service.BaseService;
import com.arf.goldcard.entity.GoldCardTransferRecord;

public interface IGoldCardTransferRecordService extends BaseService<GoldCardTransferRecord, Long> {

	/**
	 * 根据条件查询用户的金卡流转记录
	 * @param userName
	 * @param cardNo 可为空
	 * @param type 可为空
	 * @return
	 */
	public PageResult<GoldCardTransferRecord> findByCondition(String userName,String cardNo,GoldCardTransferRecord.Type type,int pageSize,int pageNo);
	
	/**
	 * 生成金卡流转记录
	 * @param balance 余额
	 * @param amount 扣费金额
	 * @param remark 说明:eg.月租车包月扣费、临时停车扣费
	 * @param type 类型:枚举 ECC_Month_Pkg月租车缴费 Temp_Parking_Fee临时停车费 Property_Fee物业费
	 * @param userName 用户名
	 * @param status 状态,0:正常,1:冻结
	 * @param orderNo 订单号,如果为月租车缴费则为月租车订单编号,如果为临时停车缴费则为临时停车费订单编号
	 * @return
	 */
	GoldCardTransferRecord genGoldCardTransferRecord(BigDecimal balance,BigDecimal amount,String remark,Integer type,String userName,Integer status,String orderNo);
	/**
	 * 生成金卡流转记录
	 * @param balance 余额
	 * @param amount 扣费金额
	 * @param remark 说明:eg.月租车包月扣费、临时停车扣费
	 * @param type 类型:枚举 ECC_Month_Pkg月租车缴费 Temp_Parking_Fee临时停车费 Property_Fee物业费
	 * @param userName 用户名
	 * @param status 状态,0:正常,1:冻结
	 * @param orderNo 订单号,如果为月租车缴费则为月租车订单编号,如果为临时停车缴费则为临时停车费订单编号
	 * @param payType 支付方式 0充值 1扣除
	 * @return
	 */
	GoldCardTransferRecord genGoldCardTransferRecord(BigDecimal balance,BigDecimal amount,String remark,Integer type,String userName,Integer status,String orderNo,Integer payType);
}
