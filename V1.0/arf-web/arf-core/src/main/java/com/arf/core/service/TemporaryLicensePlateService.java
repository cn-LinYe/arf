package com.arf.core.service;

import java.util.List;

import com.arf.core.entity.TemporaryLicensePlate;

/**
 * Service - 临时车牌表
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface TemporaryLicensePlateService extends BaseService<TemporaryLicensePlate, Long> {
	
	/**
	 * 通过车牌号查询
	 * @param licensePlateNumber
	 * @return
	 */
	public TemporaryLicensePlate selectyByLicensePlate(String licensePlateNumber);
	
	/**
	 * 通过状态查询
	 * @return
	 */
	public List<TemporaryLicensePlate> selectyByState() ;
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
	TemporaryLicensePlate selectByLicensePlateAndCommunityNumber(String licensePlate,String communityNumber);
}
