package com.arf.core.service;

import java.util.List;

import com.arf.core.entity.CameraCodeModel;

/**
 * Service - 摄像机管理
 * 
 * @author arf
 * @version 4.0
 */
public interface CameraCodeModelService extends BaseService<CameraCodeModel, Long> {
	
	/**
	 * 根据编码摄像机表信息
	 * @param cameraCode 编码
	 * @return
	 */
	List<CameraCodeModel> selectbycode(String cameraCode);
}
