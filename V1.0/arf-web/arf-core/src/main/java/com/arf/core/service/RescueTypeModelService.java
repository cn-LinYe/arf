package com.arf.core.service;

import java.util.List;

import com.arf.core.entity.RescueTypeModel;

/**
 * Service - 救援类型表
 * 
 * @author arf
 * @version 4.0
 */
public interface RescueTypeModelService extends BaseService<RescueTypeModel, Long> {
	/**
	 * 查看道路救援类型信息
	 * @return
	 * 		道路救援类型信息
	 */
	List<RescueTypeModel> sellectAll();
}
