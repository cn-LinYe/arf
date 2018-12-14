package com.arf.platform.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.arf.core.dao.BaseDao;
import com.arf.platform.entity.PParkingcar;

/**
 * Dao - PParkingcar表
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface PParkingcarDao extends BaseDao<PParkingcar, Long>{
	public PParkingcar findByCommLic(String communityNo, String license) ;

	/**
	 * 根据小区号，车牌号，车来时间，查询实时停车信息表p_parkingcar
	 * @param communityNo
	 * @param license
	 * @param arriveTime
	 * @return
	 */
	public PParkingcar findByCommLicArr(String communityNo, String license, Date arriveTime);
	
	/**
	 * 根据车牌号，车来时间，查询实时停车信息表p_parkingcar
	 * @param license
	 * @param arriveTime
	 * @return
	 */
	public PParkingcar findByLicenseAndArriveTime(String license, Date arriveTime);
	
	/**
	 * 车牌查询
	 * @param communityNo
	 * @param license
	 * @param arriveTime
	 * @return
	 */
	List<PParkingcar> findByLic(String license);
	/**
	 * 通过车牌集合查询
	 * @param licenses
	 * @return
	 */
	List<Map<String,Object>> findByLicenseList(List<String> licenses); 
	
	/**
	 * 查询用户的临时停车车辆信息
	 * @param userName
	 * @return
	 */
	List<PParkingcar> findTempParkingCar(String userName);
	
	/**
	 * 查询所有p_parkingcar记录总没有对应community的数据id
	 * @param startCreate 查询的开始时间
	 * @param endCreate 查询的截止时间
	 * @return
	 */
	List<Long> findParkingcarIdWithoutCommunity(Date startCreate,Date endCreate);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int deleteByIds(Long ...ids);
	
	/**
	 * 查询车牌+community_no重复的来车记录ID
	 * @param startArrive 来车的开始时间
	 * @param endArrive 来车的截止时间
	 * @return
	 */
	List<Long> findRepeatParkingcar(Date startArrive, Date endArrive);
	
	/**
	 * 查找用户绑定车辆在场信息
	 * @param license
	 * @return
	 */
	List<PParkingcar> findByUserName(String userName); 
	
	/**根据停车场编号统计今天入场数量
	 * @param communityNo
	 * @return
	 */
	int findAdmissionTodayByParkingId(String communityNo);

	/**
	 * 通过id删除记录(去掉hibernate的乐观锁)
	 * @param id
	 * @return
	 */
	public boolean deleteById(Long id);

	/**
	 * 通过停车场编号、车牌号删除记录(去掉hibernate的乐观锁)
	 * @param communityNo
	 * @param license
	 * @return
	 */
	public boolean deleteByCommunityNumberAndLicense(String communityNo,
			String license);
	
	/**根据停车场编号查询油站在场车辆
	 * @param communityNo
	 * @return
	 */
	List<Map<String,Object>> findGasCardByCommunityNo(String communityNo);
	
	/**根据车牌集合查询油站在场车辆
	 * @param communityNo
	 * @return
	 */
	List<Map<String,Object>> findGasCardByLicenseList(List<String> licenses);
}
