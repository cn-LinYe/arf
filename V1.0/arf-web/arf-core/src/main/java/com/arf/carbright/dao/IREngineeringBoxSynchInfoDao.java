package com.arf.carbright.dao;

import com.arf.carbright.entity.REngineeringBoxSynchInfo;
import com.arf.core.dao.BaseDao;


public interface IREngineeringBoxSynchInfoDao extends BaseDao<REngineeringBoxSynchInfo, Long>{

	/**根据小区，柜子编号，及箱子编号统计箱子今天内同步次数
	 * @param communityNumber
	 * @param cabinetNum
	 * @param boxNum
	 * @return
	 */
	public int statisticsCountByBox(String communityNumber,String cabinetNum,String boxNum);
}
