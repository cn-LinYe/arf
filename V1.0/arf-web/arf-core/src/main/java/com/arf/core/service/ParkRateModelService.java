package com.arf.core.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotBlank;

import com.arf.base.PageResult;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.ParkRateModel;
import com.arf.core.entity.ParkRateRecordModel;
import com.arf.platform.entity.PrivilegeCar;

/**
 * Service - ParkRate表
 * 
 * @author arf
 * @version 4.0
 */
public interface ParkRateModelService extends BaseService<ParkRateModel, Long> {
	/**
	 * 根据小区号和车牌号查询
	 * @param communityNumber
	 * 				小区号
	 * @param licensePlateNumber
	 * 				车牌号
	 * @return		停车费表
	 */
	ParkRateModel selectByLicenseComunity(String communityNumber, String licensePlateNumber);
	/**
     * 根据小区号和车牌号,用户名查询
     * @param communityNumber
     *              小区号
     * @param licensePlateNumber
     *              车牌号
     * @return      停车费表
     */
	ParkRateModel selectByLicenseComunity(String userName,String licensePlateNumber,String communityNumber);
	/***
	 * 根据小区号获取车牌信息
	 * @param licensePlateNumber
	 * @return
	 */
	List<ParkRateModel> selectListByPlateNumber(String licensePlateNumber,String userName);
	
	/***
	 * 根据小区id获取
	 * @param communityNumber
	 * @return
	 */
	List<ParkRateModel> selectListByCommunityNumber(String communityNumber);
	
	
	/***
	 * 根据车牌查询
	 * @param communityNumber
	 * @return
	 */
	List<ParkRateModel> selectListByLicensePlate(String licensePlate);
	
	/**
	 * 所有进闸的车牌
	 * @param licensePlate
	 * @return
	 */
	List<ParkRateModel> selectListByLicensePlateIn(String licensePlate,Integer inStatus);
	
	/***
     * 根据用户名，得到月租车牌小区和当前绑定小区
     * @param licensePlateNumber
     * @return
     */
    public List<ParkRateModel> selectListByUser(String userName);
    
    
    /***
     * 根据车牌获取小区信息和是否开启安心点
     * @param licensePlateNumber
     * @return
     */
    List<ParkRateModel> selectListByPlateNumberAxd(String licensePlateNumber);
    /***
     * 根据车牌获取小区信息和是否开启安心点
     * @param licensePlateNumber
     * @return
     */
    List<ParkRateModel> selectAllListByPlateNumberAxd(String userName);
    /***
     * 获取小区所有开通了安心点的用户
     * @return
     */
    List<ParkRateModel> selectAllUserNameAxd();
    
    /***
     * 获取所有开通了安心点的小区
     * @return
     */
    List<ParkRateModel> selectAllCommunityAxd();
    
    /***
     * 获取所有需要锁定的车牌
     * @return
     */
    List<ParkRateModel> selectAllNeedLicensePlate(Integer times);
    /**
     * 更新所有自动锁闸的车的状态
     * @param ids
     */
    void updateParkRate(String ids);
    /**
     * 获取用户所有的被自动锁闸的车牌
     * @param user_name
     * @return
     */
    List<ParkRateModel> selectAllLockLicensePlateByUser(String user_name);
    /**
     * 获取用户所有在白名单表的车牌
     * @param user_name
     * @return
     */
    List<ParkRateModel> selectAllLicenseByParkRate(String user_name);
    
    /**
     * 通过用户名来查询白名单记录, 小区编号正序返回
     * @param userName
     * @return
     */
    List<ParkRateModel> findByUserName(@NotBlank String userName,String communityNumber);

    /**
     * 更新白名单用户名（根据车牌）
     * @param license
     * @param userNames
     */
    void updateUserName(String userName,String ids);
    
    /**
     * 月租续费通知道闸
     * @param parkRateRecordModel
     * @param community
     * @return
     */
	boolean manualNotifyEcc(ParkRateRecordModel parkRateRecordModel,CommunityModel community);
	
	/**
	 * 查询白名单中即将到期或已过期 distanceDay 天的用户列表
	 * @param distanceDay
	 * @param beforeOrEnd
	 * @return
	 */
	List<Map<String,Object>> findNearDeadlime(Calendar date,Integer distanceDay,String beforeOrEnd);
	
	/**
	 * 通过车牌号和停车场编号查询特权车
	 * @param license
	 * @param community
	 * @return
	 */
	PrivilegeCar findPrivilegeCarByLicenseCommunity(String license,String community);
	
	/**
	 * 根据条件查找用户的订单
	 * @param userName
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	PageResult<Map<String,Object>>  findByCondition(String userName,Integer pageSize,Integer pageNo);
	
	/**
	 * 添加成功处理白名单
	 * @param userName
	 * @param license
	 */
	void modifyParkRate(String userName, String license);
	
	/**
	 * 推送消息
	 * @param userName
	 * @param licensePlateNumber
	 */
	void isInWhiteList(String userName,String licensePlateNumber);
	
	/**
	 * 查找用户绑定车辆以及是否在白名单车辆信息
	 * @param userName
	 * @param licensePlateNumber
	 */
	List<Map<String,Object>> findUserParkRate(String userName);
	
	/**
	 * 通过用户绑定的车牌查找白名单记录
	 * @param userName
	 * @param licensePlateNumber
	 */
	List<ParkRateModel> findParkRateByUserLicense(String userName);
	
	/**
	 * 月租缴费生成礼品卷、获得积分、缓存结果公用方法
	 * @param outTradeNo
	 * @param totalFeeYuan
	 * @param parkRateRecordModel
	 */
	void gainGiftPointByPayParkRate(String outTradeNo,BigDecimal totalFeeYuan,ParkRateRecordModel parkRateRecordModel);
	
	/**
	 * 查询车牌号是不是月租车（或未过期月租车）
	 * @param license
	 * @param monthlyExpired true 查询未过期的 false 过期的也查询
	 * @return
	 */
	List<ParkRateModel> findByLicenseNotOutofdate(String license,boolean monthlyExpired);
	
	/**
	 * 根据车牌查询所在月租车小区及小区是否支持在线月租缴费
	 */
	List<Map<String,Object>> findCommunitiesByLicensePlate(String licensePlateNumber,Boolean isNotOutOfDate);
	
	/**
	 * 更新lock状态
	 * @param id 白名单记录Id
	 * @param lockStatus
	 * @param userStatus
	 */
	void updatLockStatus(Long id,Integer lockStatus,Integer userLocks);
}
