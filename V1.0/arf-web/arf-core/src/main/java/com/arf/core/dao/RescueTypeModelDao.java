package com.arf.core.dao;

import java.util.List;

import com.arf.core.entity.RescueTypeModel;

/**
 * Dao - 救援类型表
 * 
 * @author arf
 * @version 4.0
 */
public interface RescueTypeModelDao extends BaseDao<RescueTypeModel, Long>{
	
	/**
	 * 查看道路救援类型信息
	 * @return
	 * 		道路救援类型信息
	 */
	List<RescueTypeModel> sellectAll();
	
}
