package com.arf.core.dao;

import java.util.List;

import com.arf.core.entity.CarBaoServiceModel;

/**
 * Dao - 车保服务类型表
 * 
 * @author arf
 * @version 4.0
 */
public interface CarBaoServiceModelDao extends BaseDao<CarBaoServiceModel, Long>{
	
	/**
	 * 通过类型查询服务
	 * @param level 类型
	 * @return
	 */
	List<CarBaoServiceModel> select(int level);
}
