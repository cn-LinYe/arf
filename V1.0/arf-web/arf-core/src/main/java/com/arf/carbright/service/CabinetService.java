package com.arf.carbright.service;

import java.util.List;

import com.arf.carbright.entity.Box;
import com.arf.carbright.entity.Cabinet;
import com.arf.core.service.BaseService;

public interface CabinetService extends BaseService<Cabinet, Long> {
	
	/**
	 * 查询小区的所有柜子
	 * @param communityNum
	 * @return
	 */
	public List<Cabinet> findByCommunityNum(String communityNum);
	
	/**
	 * 查询根据柜子编号查询某一小区的柜子
	 * @param communityNum 所属停车场id/小区id
	 * @param cabinetNum 柜子编号
	 * @return
	 */
	public Cabinet findByNum(String communityNum,String cabinetNum);
}
