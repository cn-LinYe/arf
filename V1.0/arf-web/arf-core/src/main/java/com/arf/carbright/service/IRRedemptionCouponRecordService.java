package com.arf.carbright.service;

import com.arf.carbright.entity.RRedemptionCouponRecord;
import com.arf.core.service.BaseService;

public interface IRRedemptionCouponRecordService extends BaseService<RRedemptionCouponRecord, Long>{
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
