package com.arf.core.service;

import java.util.List;

import com.arf.core.entity.SpareUserInforModel;
/**
 * Dao -物业上传用户信息（备份）
 * @author arf  zgr
 * @version 4.0
 *
 */

public interface SpareUserInforModelService extends BaseService<SpareUserInforModel, Long> {
	/**
	 * 通过车牌和小区查询
	 * @param community
	 * 小区
	 * @param LicensePlate
	 * 车牌
	 * @return
	 */
	SpareUserInforModel selectByLicenseCommunity(String community, String LicensePlate);
	/**
	 * 通过车牌查找(此方法取消和下面的方法冲突)
	 * @param LicensePlate
	 * @return
	 */
	SpareUserInforModel selectByLicensePlate(String LicensePlate);
	
	/**
	 * 通过车牌查找
	 * @param LicensePlate
	 * @return
	 */
	List<SpareUserInforModel> selectByLicenseCommunitys(String LicensePlate,String community);
	
	/**
	 * 通过小区id查询
	 * @param community
	 * @return
	 */
	List<SpareUserInforModel> selectByCommunity(String community);
	/**
     * 通过小区id查询,用户去重
     * @param community
     * @return
     */
    List<SpareUserInforModel> selectByCommunitySingle(String community);

}
