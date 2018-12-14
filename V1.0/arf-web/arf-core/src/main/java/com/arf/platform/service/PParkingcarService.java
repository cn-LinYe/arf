package com.arf.platform.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.arf.core.AppMessage;
import com.arf.core.dto.ParkingFeeDto;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.ParkRateRecordModel;
import com.arf.core.service.BaseService;
import com.arf.platform.entity.PParkingcar;
import com.arf.platform.entity.RStoprecord;

/**
 * Service - PParkingcar表
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface PParkingcarService extends BaseService<PParkingcar, Long> {
	
	
	/**
	 * 根据小区号，车牌号，车来时间，查询实时停车信息表p_parkingcar
	 * @param communityNo
	 * @param license
	 * @param arriveTime
	 * @return
	 */
	PParkingcar findByCommLicArr(String communityNo,String license,Date arriveTime); 
	
	/**
	 * 根据车牌号，车来时间，查询实时停车信息表p_parkingcar
	 * @param license
	 * @param arriveTime
	 * @return
	 */
	PParkingcar findByLicenseAndArriveTime(String license,Date arriveTime); 
	
	/**
	 * 根据小区号，车牌号，查询实时停车信息表p_parkingcar
	 * @param communityNo
	 * @param license
	 * @param arriveTime
	 * @return
	 */
	PParkingcar findByCommLic(String communityNo,String license); 
	
	/**
	 * 车牌查询
	 * @param communityNo
	 * @param license
	 * @param arriveTime
	 * @return
	 */
	List<PParkingcar> findByLic(String license); 
	
	/**
	 * 查找用户绑定车辆在场信息
	 * @param license
	 * @return
	 */
	List<PParkingcar> findByUserName(String userName); 
	
	/**
	 * 通过车牌集合查询
	 * @param licenses
	 * @return
	 */
	List<Map<String,Object>> findByLicenseList(List<String> licenses); 
	
	/**
	 * 查询用户的所有临时停车信息
	 * @return
	 */
	List<ParkingFeeDto> findTempParkingCar(String userName,String communityNumber,String license,long parkingCarId);
	
	/**
	 * 生成临时停车待支付/支付订单(停车收费记录-未支付(微信、支付宝)/支付(停车卡、电子钱包))
	 * @param userName
	 * @param parkingCarId
	 * @param payType 0钱包支付，1微信支付，2支付宝支付，3 银行卡 4、现金 5、停车卡 6、钱包-停车卡
	 * @param fee
	 * @param vouchersNum
	 * @param points
	 * @return
	 */
	AppMessage genTempParkingRecord(String userName,Long parkingCarId, Integer payType,BigDecimal fee,String vouchersNum,int points);
	
	/**
	 * 清理掉p_parkingcar表中的脏数据
	 * @param dayBefore 几天前的
	 * @return 共处理的脏数据总量
	 */
	long clearDirtyParkingcar(int dayBefore);
	
	/**
	 * 供app、微信公众号 查询临时停车费
	 * @param userName 没有值，则传 " "
	 * @param license 必填
	 * @param community 必填
	 * @param parkingCar 必填
	 * @return
	 */
	ParkingFeeDto getParkingFee(String userName, String license, CommunityModel community,PParkingcar parkingCar);
	
	/**
	 * 临时停车费通知道闸系统
	 * @param totalFee
	 * @param outTradeNo
	 * @param arrivedTimeStamp
	 * @param nowTimeStamp
	 * @param license
	 * @param community
	 * @return
	 * @throws Exception
	 */
	JSONObject tempParkingFeeNotifyGateway(BigDecimal totalFee,String outTradeNo,long arrivedTimeStamp,long nowTimeStamp, String license,CommunityModel community) throws Exception;
	
	/**
	 * 临时停车获得积分、缓存结果方法
	 * @param outTradeNo
	 * @param totalFeeYuan
	 */
	void gainGiftPointByPayTempParking(String outTradeNo,BigDecimal totalFeeYuan,Integer addpoint,Integer stayTime);
	
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
	boolean deleteById(Long id);

	/**
	 * 通过停车场编号、车牌号删除记录(去掉hibernate的乐观锁)
	 * @param communityNo
	 * @param license
	 * @return
	 */
	boolean deleteByCommunityNumberAndLicense(String communityNo, String license);
	
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
