package com.arf.gift.service;

import java.math.BigDecimal;

import com.arf.base.PageResult;
import com.arf.core.entity.ParkRateRecordModel;
import com.arf.core.service.BaseService;
import com.arf.gift.entity.GiftVoucherInfoRecord;
import com.arf.gift.entity.GiftVoucherInfoRecord.GetChannel;
import com.arf.gift.entity.GiftVoucherInfoRecord.Status;

public interface IGiftVoucherInfoRecordService extends BaseService<GiftVoucherInfoRecord, Long>{

	/**
	 * 根据条件查找
	 * @return
	 */
	public PageResult<GiftVoucherInfoRecord> findGiftRecordByCondition(Integer pageNo,Integer pageSize,GiftVoucherInfoRecord.Status status[],
			String userName,Long id,GiftVoucherInfoRecord.ExpressLogisticsStatus expressLogisticsStatus);

	public GiftVoucherInfoRecord genRecordAboutParkrate(Integer effectiveDate,Status status,GetChannel getChannel,
			String userName,String communityNumber,BigDecimal amount,String outTradeNo);
	
	/**根据状态统计礼品券数量
	 * @param status 状态枚举:UNUSED 0 未使用USED 1 已使用
	 * @return
	 */
	public int getGiftRecordCount(String userName,GiftVoucherInfoRecord.Status status);
	
	/**
	 * 月租缴费成功后生成礼品卷信息
	 * @param outTradeNo 缴费订单号
	 * @param totalFeeYuan 缴费金额（单位圆）
	 * @return
	 */
	public GiftVoucherInfoRecord genRecordByPayParkRate(String outTradeNo,BigDecimal totalFeeYuan,ParkRateRecordModel parkRateRecordModel);
	
	/**
	 * 礼品券码来查找
	 * @param voucherNumber
	 */
	public GiftVoucherInfoRecord findByVoucherNumber(String voucherNumber);

	/**
	 * 更新过期的兑换券记录状态为过期
	 */
	public int updateOverdueRecordStatus();
}
