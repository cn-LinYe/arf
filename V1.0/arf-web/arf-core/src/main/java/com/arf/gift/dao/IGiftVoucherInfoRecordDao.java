package com.arf.gift.dao;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.gift.entity.GiftVoucherInfoRecord;

public interface IGiftVoucherInfoRecordDao extends BaseDao<GiftVoucherInfoRecord, Long>{

	/**
	 * 根据条件查找
	 * @return
	 */
	public PageResult<GiftVoucherInfoRecord> findGiftRecordByCondition(Integer pageNo,Integer pageSize,GiftVoucherInfoRecord.Status status[],String userName,Long id,GiftVoucherInfoRecord.ExpressLogisticsStatus expressLogisticsStatus);

	/**根据状态统计礼品券数量
	 * @param status 状态枚举:UNUSED 0 未使用USED 1 已使用
	 * @return
	 */
	public int getGiftRecordCount(String userName,GiftVoucherInfoRecord.Status status);

	/**
	 * 更新过期的兑换券记录状态为过期
	 */
	public int updateOverdueRecordStatus();
}
