package com.arf.carbright.dao;

import java.util.List;

import com.arf.carbright.entity.Cabinet;
import com.arf.core.dao.BaseDao;

public interface CabinetDao extends BaseDao<Cabinet, Long> {
	
	/**
	 * 查询小区的所有柜子
	 * @param communityNum
	 * @return
	 */
	public List<Cabinet> findByCommunityNum(String communityNum);
}
