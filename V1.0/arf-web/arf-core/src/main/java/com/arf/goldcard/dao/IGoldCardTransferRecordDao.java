package com.arf.goldcard.dao;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.goldcard.entity.GoldCardTransferRecord;

public interface IGoldCardTransferRecordDao extends BaseDao<GoldCardTransferRecord, Long> {

	/**
	 * 根据条件查询用户的金卡流转记录
	 * @param userName
	 * @param cardNo
	 * @param type
	 * @return
	 */
	public PageResult<GoldCardTransferRecord> findByCondition(String userName, String cardNo, GoldCardTransferRecord.Type type, int pageSize,int pageNo);
}
