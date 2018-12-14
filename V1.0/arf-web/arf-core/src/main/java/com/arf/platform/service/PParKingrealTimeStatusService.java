package com.arf.platform.service;

import java.util.Collection;
import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.eparking.dto.ParKingRealTimeStatusDto;
import com.arf.eparking.entity.ParkingInfo;
import com.arf.eparking.vo.ParkingInfoSearchVo;
import com.arf.platform.entity.PParKingrealTimeStatus;

/**
 * Service - PParKingrealTimeStatus表
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface PParKingrealTimeStatusService extends BaseService<PParKingrealTimeStatus, Long> {
	
	/**
	 * 通过小区id查询停车场实时状态
	 * @param communityNo 小区id
	 * @return
	 */
	List<PParKingrealTimeStatus> getPParKingrealTimeStatusByCommunityNo(String communityNo);
	
	/**
	 * 搜索符合条件的车场
	 * @return
	 */
	Collection<ParKingRealTimeStatusDto>[] searchParkings(ParkingInfoSearchVo searchVo);
}
