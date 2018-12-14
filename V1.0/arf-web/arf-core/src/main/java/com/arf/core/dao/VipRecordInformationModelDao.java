package com.arf.core.dao;

import java.util.Date;
import java.util.List;

import com.arf.core.entity.VipRecordInformationModel;

/**
 * Dao - 开通vip订单纪录表
 * 
 * @author arf
 * @version 4.0
 */
public interface VipRecordInformationModelDao extends BaseDao<VipRecordInformationModel, Long>{
	/**
	 * 通过订单号获取订单信息
	 * @param out_trade_no
	 * 订单号
	 * @return
	 */
	VipRecordInformationModel selectByouttradeno(String out_trade_no);
	/**
	 * 查询符合条件的订单信息
	 * @param trade_status
	 * 				交易状态
	 * @param isRed
	 * 				是否发红包
	 * @param pay_choice
	 * 				支付方式
	 * @return		符合条件的订单信息
	 */
	List<VipRecordInformationModel> selectUnionID(String trade_status, int isRed, int pay_choice);
	
	/**
	 * 通过unionId查询充值开通ECC但是还没返红包的记录,即trade_status='9000' and pay_choice=3 and unionId = unionId
	 * @param unionId
	 * @return
	 */
	List<VipRecordInformationModel> selectNotSendRedPkgByUnionID(String unionId);
	
	
	/**
	 * 查询时间段内未发红包的VipRecordInformationModel
	 * @param payChoice
	 * @param createStart
	 * @param createEnd
	 * @return
	 */
	List<VipRecordInformationModel> findNotSendRedPkg(int payChoice, Date createStart, Date createEnd);
	
	/**
	 * 通过用户名 套餐ID 查找用户是否购买过次套餐
	 * @param userName
	 * @param parkageId
	 * @return
	 */
	List<VipRecordInformationModel> checkUserBuyEccPackage(String trade_status,String userName,Long eccPackageConfigId);
}
