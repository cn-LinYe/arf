package com.arf.member.dao;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.member.entity.PointTransferRecord;

public interface IPointTransferRecordDao extends BaseDao<PointTransferRecord, Long> {

	public PageResult<PointTransferRecord> findListOrders(String accountNumber,PointTransferRecord.PointType pointType, int pageSize, int pageNo);
	/**
	 * 通过subType和用户名查找数量
	 * @param subType
	 * @return
	 */
	public int findCount(Integer subType,String userName);
	
}
