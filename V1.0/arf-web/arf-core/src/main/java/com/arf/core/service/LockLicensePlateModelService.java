package com.arf.core.service;

import java.util.List;

import com.arf.core.AppMessage;
import com.arf.core.entity.LockLicensePlateModel;

/**
 * Service - 车牌控制表(用来锁定车牌)
 * 
 * @author arf
 * @version 4.0
 */
public interface LockLicensePlateModelService extends BaseService<LockLicensePlateModel, Long> {
	/**
	 * 通过车牌号查找车牌(此方法不能用)
	 * @param license 
	 * 		车牌号
	 * @return
	 */
	LockLicensePlateModel selectLicense(String license);
	/**
	 * 此处和上面会留一个 
	 * @param license
	 * @return
	 */
	List<LockLicensePlateModel> selectLicenseAxd(String license);
	/**
	 * 根据小区编号和车牌号码获取锁车对象
	 * @return
	 */
	List<LockLicensePlateModel> selectLicenseByParm(String communityNumber,String licensePlateNumber);
	/**
     * 根据小区编号和车牌号码获取锁车对象
     * @return
     */
    List<LockLicensePlateModel> selectLicenseByParm(String communityNumber,String licensePlateNumber,Integer license_plate_command);
	
	/**
	 * 锁闸只能有一条命令
	 * @param communityNumber
	 * @param licensePlateNumber
	 * @return
	 */
	LockLicensePlateModel selectLicenseByLock(String communityNumber,String licensePlateNumber,Integer license_plate_command);
	
}
