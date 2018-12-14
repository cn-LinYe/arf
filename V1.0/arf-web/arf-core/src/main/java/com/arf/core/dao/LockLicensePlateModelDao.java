package com.arf.core.dao;

import java.util.List;

import com.arf.core.entity.LockLicensePlateModel;

/**
 * Dao - 车牌控制表(用来锁定车牌)
 * 
 * @author arf
 * @version 4.0
 */
public interface LockLicensePlateModelDao extends BaseDao<LockLicensePlateModel, Long>{
	/**
	 * 通过车牌号查找车牌
	 * @param license 
	 * 		车牌号
	 * @return
	 */
	LockLicensePlateModel selectLicense(String license);
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
     * 和上面的只能留一个
     * @param license
     * @return
     */
     List<LockLicensePlateModel> selectLicenseAxd(String license) ;
     
     /**
      * 
      * @param communityNumber
      * @param licensePlateNumber
      * @return
      */
 	LockLicensePlateModel selectLicenseByLock(String communityNumber,String licensePlateNumber,Integer license_plate_command);

}
