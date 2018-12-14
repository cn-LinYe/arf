package com.arf.core.dao;

import com.arf.core.entity.PDeviceinfo;

/**
 * Dao - P_Deviceinfo表
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface P_DeviceinfoDao extends BaseDao<PDeviceinfo, Long>{
	
	
	/**
	 * 摄像机编号查询
	 * @param deviceNum
	 * @return
	 */
	PDeviceinfo findByDeviceNum(String deviceNum,String communityNum);
	
	
}
