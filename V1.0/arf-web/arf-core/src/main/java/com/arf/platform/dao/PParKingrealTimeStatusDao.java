package com.arf.platform.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.platform.entity.PParKingrealTimeStatus;

/**
 * Dao - PParKingrealTimeStatus表
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface PParKingrealTimeStatusDao extends BaseDao<PParKingrealTimeStatus, Long>{

	/**
	 * 通过小区id查询停车场实时状态
	 * @param communityNo 小区id
	 * @return
	 */
	List<PParKingrealTimeStatus> getPParKingrealTimeStatusByCommunityNo(String communityNo);
	
}
