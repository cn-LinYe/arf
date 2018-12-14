package com.arf.core.dao;

import java.util.List;

import com.arf.core.entity.TemporaryLicensePlate;

/**
 * Dao - 临时车牌表
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface TemporaryLicensePlateDao extends BaseDao<TemporaryLicensePlate, Long>{
	
	/**
	 * 车牌号查询
	 * @param licensePlateNumber
	 * @return
	 */
	public TemporaryLicensePlate selectyByLicensePlate(String licensePlateNumber);
	
	/**
	 * 通过车辆状态查询车牌
	 * @return
	 */
	public List<TemporaryLicensePlate> selectyByState();
	/**
     * 获取用户的所有入场车辆
     * @param userName
     * @return
     */
    public List<TemporaryLicensePlate> selectByUserName(String userName);

    /**
	 * 通过车牌号和小区编号查询
	 * @param licensePlate
	 * @param communityNumber
	 * @return
	 */
	public TemporaryLicensePlate selectByLicensePlateAndCommunityNumber(
			String licensePlate, String communityNumber);
}
