package com.arf.core.service;

import com.arf.core.entity.PDeviceinfo;

/**
 * Service - P_Deviceinfo表
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface PDeviceinfoService extends BaseService<PDeviceinfo, Long> {
	
	/**
	 * 摄像机编号查询
	 * @param deviceNum
	 * @return
	 */
	PDeviceinfo findByDeviceNum(String deviceNum,String communityNum);
	
}
