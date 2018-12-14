package com.arf.carbright.dao;

import com.arf.carbright.entity.RRedemptionCouponRecord;
import com.arf.core.dao.BaseDao;

public interface IRRedemptionCouponRecordDao extends BaseDao<RRedemptionCouponRecord, Long>{

	/**通过兑换码获取套餐
	 * @param redemptionCode 兑换码
	 * @return
	 */
	public RRedemptionCouponRecord findByRedemptionCode(String redemptionCode);
	
	/**统计兑换数量
	 * @param getCouponId 赠送记录ID
	 * @return
	 */
	public int statisticsCountByGetCouponId(String getCouponId);
}
