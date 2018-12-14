package com.arf.core.service;

import java.util.List;

import com.arf.core.entity.CarBaoServiceModel;

/**
 * Service - 车保服务类型表
 * 
 * @author arf
 * @version 4.0
 */
public interface CarBaoServiceModelService extends BaseService<CarBaoServiceModel, Long> {
	/**
	 * 通过类型查询服务
	 * @param level 类型
	 * @return
	 */
	List<CarBaoServiceModel> select(int level);
}
