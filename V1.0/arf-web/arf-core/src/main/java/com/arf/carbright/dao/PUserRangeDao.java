package com.arf.carbright.dao;

import java.util.List;

import com.arf.carbright.entity.PUserRange;
import com.arf.core.dao.BaseDao;

public interface PUserRangeDao extends BaseDao<PUserRange, Long>{

	/**
	 * 通过停车场id查符合的套餐
	 * @param parkingID
	 * @return
	 */
	List<PUserRange> findbyparkingID(String parkingID);
	
	PUserRange findByUseRangeNum(String useRangeNum);
	
	/**通过商户ID查符合的套餐
	 * @param businessId
	 * @return
	 */
	public List<PUserRange> findbyBusinessId(int businessId);
}
