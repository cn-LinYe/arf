package com.arf.core.dao;

import java.util.List;

import com.arf.core.entity.CameraCodeModel;

/**
 * Dao - 摄像机管理
 * 
 * @author arf
 * @version 4.0
 */
public interface CameraCodeModelDao extends BaseDao<CameraCodeModel, Long>{
	/**
	 * 根据编码摄像机表信息
	 * @param cameraCode 编码
	 * @return
	 */
	List<CameraCodeModel> selectbycode(String cameraCode);
}
