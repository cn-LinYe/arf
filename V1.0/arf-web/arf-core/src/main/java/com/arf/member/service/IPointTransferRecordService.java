package com.arf.member.service;

import com.arf.base.PageResult;
import com.arf.core.service.BaseService;
import com.arf.member.dto.PointGiftDto;
import com.arf.member.entity.PointTransferRecord;
import com.arf.member.vo.PointGiftVo;

public interface IPointTransferRecordService extends BaseService<PointTransferRecord, Long> {

	public PageResult<PointTransferRecord> findListOrders(String accountNumber,PointTransferRecord.PointType pointType, int pageSize, int pageNo);
	
	/**
	 * 通过subType和用户名查找数量
	 * @param subType
	 * @return
	 */
	public int findCount(Integer subType,String userName);
	
	/**
	 * 通过传入的赠送积分业务类型来适配具体的积分配赠
	 * @param businessType
	 * @param userName
	 * @param communityNumber
	 * @param license
	 * @return
	 */
	public PointGiftDto pointGiftByBusiness(PointGiftVo vo);
	
}
