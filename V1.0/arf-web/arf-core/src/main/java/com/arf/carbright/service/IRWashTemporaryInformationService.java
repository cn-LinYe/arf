package com.arf.carbright.service;

import java.util.List;

import com.arf.carbright.entity.RWashTemporaryInformation;
import com.arf.core.service.BaseService;

public interface IRWashTemporaryInformationService extends BaseService<RWashTemporaryInformation, Long>{

	/**根据有效期查询及小区信息查询可用的用户信息（车牌及小区）
	 * @param communityNumber 小区编号
	 * @return
	 */
	public List<RWashTemporaryInformation> findTemporaryUserbyCommunity(String communityNumber,String userName);
}
