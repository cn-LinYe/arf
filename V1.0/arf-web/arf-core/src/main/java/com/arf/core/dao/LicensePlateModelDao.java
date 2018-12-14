package com.arf.core.dao;

import java.util.List;
import java.util.Map;

import com.arf.core.entity.LicensePlateModel;

/**
 * Dao - 车牌表
 * 
 * @author arf
 * @version 4.0
 */
public interface LicensePlateModelDao extends BaseDao<LicensePlateModel, Long>{
	/**
	 * //通过车牌查看
	 * @param License
	 * @return
	 */
	List<LicensePlateModel> CheckLicensePlate(String License);
	/**
	 * 用户名车牌查找
	 * @param user_name
	 * 用户名
	 * @param license_plate
	 * 
	 * @return
	 */
	LicensePlateModel selectByLicesenUser_name(String user_name,String license_plate);

	/**
	 * 查找所有車牌   zgr
	 * @return
	 */
	List<LicensePlateModel> CheckLicense_plate_id();//查找所有車牌

	
	
	/**
	 * 根据用户名查找车牌集合
	 * @param user_Name
	 * 					用户名		
	 * @return		车牌集合
	 */
	List<LicensePlateModel> CheckUser_name(String user_Name);
	
	/**
	 * 查询用户的非月租车车牌信息
	 * @param userName
	 * @return
	 */
	List<LicensePlateModel> findNotMonthlyLicense(String userName);
	
	

	/**
	 * 通过车牌号和车驾号查询
	 * @param LicensePlate
	 * @param CarCode
	 * @param userName
	 * @return
	 */
	LicensePlateModel selectByCarCode(String licensePlate,String carCode);
	
	/**
     * 获取vip用户所有已经认证的车牌
     * @return
     */
    public List<LicensePlateModel> getVipLicensePlate();
    
    /**
     * 获取用户所有绑定的车牌
     * @return
     */
    public List<LicensePlateModel> getVipLicensePlate(String userName);
    
    
    /**
     * 通过车牌id查看
     * @return
     */
    public LicensePlateModel findByLicenseId(String licensePlateId);
    
    
    /**
     * 通过车牌id查看
     * @return
     */
    public String findMaxLicenseId();
    /**
	 * 通过车牌号查询user_name不为‘nullUser’的
	 * @param License
	 * @return
	 */
	List<LicensePlateModel> findByLicenseAndNotNulluser(String license);
	
	 /**
     * 查看用户绑定月租车信息
     * @param userName
     * @return
     */
    public List<Map<String,Object>> findParkRateByUserName(String userName);
    
    /**
	 * 根据用户名联合授权表查询
	 * @param userName
	 * @param licensePlateNumber
	 * @return
	 */
	List<Map<String, Object>> findByUserNameAboutDevolution(String userName,String licensePlateNumber);
	
	 /**
     * 查询所有小区月租车用户信息
     * @param userName
     * @return
     */
    public List<Map<String,Object>> findAllParkRate();
}
