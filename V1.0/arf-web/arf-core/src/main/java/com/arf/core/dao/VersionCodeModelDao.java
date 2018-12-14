package com.arf.core.dao;

import java.util.List;

import com.arf.core.entity.VersionCodeModel;

/**
 * Dao - 版本号表
 * 
 * @author arf
 * @version 4.0
 */
public interface VersionCodeModelDao extends BaseDao<VersionCodeModel, Long>{
	/**
	 * 获取版本号集合
	 * @return
	 */
	List<VersionCodeModel> findVersion();
}
