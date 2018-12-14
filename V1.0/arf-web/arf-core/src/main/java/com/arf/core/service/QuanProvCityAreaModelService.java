package com.arf.core.service;

import java.util.List;

import com.arf.core.dto.AreaDetailInfoDto;
import com.arf.core.entity.QuanProvCityAreaModel;

/**
 * Service - 省市区表
 * 
 * @author arf
 * @version 4.0
 */
public interface QuanProvCityAreaModelService extends BaseService<QuanProvCityAreaModel, Long> {

	/**
	 * 获取省
	 * 
	 * @return
	 */
	List<QuanProvCityAreaModel> sellectProvice();
	/**
	 * 获取市
	 * @param no
	 * 		编码
	 * @return
	 */
	List<QuanProvCityAreaModel> selectAllCity(String no);
	/**
	 * 获取区
	 * @param areacode
	 * 		编码
	 * @return
	 */
	List<QuanProvCityAreaModel> selectAllArea(String areacode);
	/**
	 * 获取小区
	 * @param no
	 * @return
	 */
	List<QuanProvCityAreaModel> selectAllQuarters(String no);
	/**
	 * 通过市名称获取省市区
	 * @param areaName
	 * 				小区名
	 * @return
	 */
	List<QuanProvCityAreaModel> checkByAreaName(String areaName);
	
	/**
	 * 根据no编码集合查找省市区信息
	 * @param nos
	 * @return
	 */
	List<QuanProvCityAreaModel> checkByAreaName(List<Integer> nos);
	
	/**
	 * 获取区域信息的详细信息，包括这个区域是县/区，则还包含市，省/直辖市
	 * @param no
	 * @return
	 */
	AreaDetailInfoDto getAreaDetailInfo(String no);
}
