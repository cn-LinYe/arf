package com.arf.core.dao;

import java.util.List;
import java.util.Map;

import com.arf.core.entity.DevolutionLicensePlateModel;

/**
 * Dao - 授权表
 * 
 * @author arf
 * @version 4.0
 */
public interface DevolutionLicensePlateModelDao extends BaseDao<DevolutionLicensePlateModel, Long>{
	/**
	 * 查看是否授权过
	 * @param user_name
	 * @param license_plate
	 * @return
	 */
	DevolutionLicensePlateModel selectByLicesenUser_name(String user_name,String license_plate);
	
	/**
	 * 查询授权车辆
	 * @param user_name
	 * @param license_plate
	 * @return
	 */
	DevolutionLicensePlateModel selectByLicenseAndUserName(String user_name,String license_plate);

	/**
	 * 根据用户名查找授权表
	 * @param user_name
	 * 					用户名
	 * @return		授权表集合
	 */
	List<DevolutionLicensePlateModel> selectDevolution(String user_name);
	/**
	 * 根据用户名截止时间查询授权表
	 * @param user_nane 
	 * 		用户名
	 * @param endTime
	 * 		截止时间
	 * @return
	 */
	List<DevolutionLicensePlateModel> CheckUser_name(String user_nane,Long endTime);
	/**
	 * 根据用户名查找授权的月租车信息
	 * @param user_nane
	 * @param endTime
	 * @return
	 */
	List<Map<String,Object>> findParkRateByUserName(String userName);

	/**
	 * 通过车牌号查询没有过期的授权记录
	 * @param license_plate_number
	 * @return
	 */
	List<DevolutionLicensePlateModel> selectByLicense(String license_plate_number);
	
	/**
	 * 通过车牌号用户名查询没有过期的授权记录
	 * @param user_name
	 * @param license_plate_number
	 * @return
	 */
	DevolutionLicensePlateModel selectByLicenseUserName(String user_name,String license_plate_number);
	
	/**
	 * 通过车牌号查询所有授权记录
	 * @param license_plate_number
	 * @return
	 */
	List<DevolutionLicensePlateModel> selectDevolutionByLicense(String license_plate_number);

	/**
	 * 查询用户被授权车辆的授权信息和车辆信息
	 * @param userName
	 * @return
	 */
	List<Map<String,Object>> findByUserNameLeftjoinLicenseplate(
			String userName);
}
