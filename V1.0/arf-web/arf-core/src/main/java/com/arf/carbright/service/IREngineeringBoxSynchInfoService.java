package com.arf.carbright.service;

import com.arf.carbright.entity.REngineeringBoxSynchInfo;
import com.arf.core.service.BaseService;

public interface IREngineeringBoxSynchInfoService extends BaseService<REngineeringBoxSynchInfo, Long>{
	
	/**根据小区，柜子编号，及箱子编号统计箱子今天内同步次数
	 * @param communityNumber
	 * @param cabinetNum
	 * @param boxNum
	 * @return
	 */
	public int statisticsCountByBox(String communityNumber,String cabinetNum,String boxNum);

}
