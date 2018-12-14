package com.arf.member.service;

import com.arf.core.service.BaseService;
import com.arf.member.entity.VipLevel;

public interface IVipLevelService extends BaseService<VipLevel, Long> {

	/**
	 * 查询小于等于传入的经验值最大的升级规则
	 * @param experience
	 * @return
	 */
	VipLevel findFirstByLeExp(long experience);

	VipLevel findByLevel(int level);

}
