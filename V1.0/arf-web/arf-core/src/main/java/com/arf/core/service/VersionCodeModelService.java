package com.arf.core.service;

import java.util.List;
import java.util.Map;

import com.arf.core.entity.VersionCodeModel;

/**
 * Service - 版本号表
 * 
 * @author arf
 * @version 4.0
 */
public interface VersionCodeModelService extends BaseService<VersionCodeModel, Long> {
	/**
	 * 获取版本信息
	 * @return
	 */
	List<VersionCodeModel> findVersion();
}
