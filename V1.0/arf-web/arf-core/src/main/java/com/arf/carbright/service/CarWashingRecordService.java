package com.arf.carbright.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.arf.carbright.entity.CarWashingRecord;
import com.arf.core.AppMessage;
import com.arf.core.service.BaseService;

public interface CarWashingRecordService  extends BaseService<CarWashingRecord, Long> {
	
	/**
	 * 操作类型
	 * @author Caolibao
	 * 2016年5月19日 下午3:45:07
	 *
	 */
	public enum OperateType{
		Merchant_Confirm_Order, //商户确认订单操作
		Merchant_Cancel_Order, //商户取消订单操作
		Merchant_Confirm_Fetch_Key, //商户确认已取钥匙操作
		Merchant_Finished_Order, //商户完成订单操作
		;
		
		public static OperateType get(int ordinal){
			OperateType types[] = OperateType.values();
			if(ordinal > types.length - 1){
				return null;
			}else{
				return types[ordinal];
			}
		}
	}
	
	public enum ImageType{
		Car_Washing_Pre, //洗车前
		Car_Washing_Post, //洗车后
		User_Avatar,//头像
		;
		public static ImageType get(int ordinal){
			ImageType types[] = ImageType.values();
			if(ordinal > types.length - 1){
				return null;
			}else{
				return types[ordinal];
			}
		}
	}
	
	
	/**
	 * 车主存钥匙，发送凭证码给蓝牙柜子；
	 * @param id
	 * @return
	 */
	AppMessage storeKey(long id);
	
	/**
	 * 车主取钥匙
	 * @param id
	 * @return
	 */
	AppMessage fetchKey(long id);
	
	/**
	 * 用户下订单接口
	 * 车主下订单先要查询“小区柜子表(e_cabinet)”，
	 * 判断是否有空的可用柜子，如果有锁定其中一个柜子更改其状态为“锁定”，
	 * 同时操作业务订单表r_car_washing_record，生成一条订单记录；
	 * @param order 一个未持久化的CarWashingRecord
	 * @param genOrderNo 是否需要生成订单编号
	 * @param businessNum 商户编码，新接口添加，若不为空则按此查询商户信息
	 * @return
	 */
	AppMessage bookingOrder(CarWashingRecord order,boolean genOrderNo,Integer businessNum,String cabinetNum,MultipartFile[] locationImage);
	
	/**
	 * 用户取消订单接口
	 * 更改订单状态，根据订单的状态判断是否允许取消订单，
	 * 允许取消则直接更改订单状态，不能取消订单，提示原因；
	 * @param orderNo 订单编号
	 * @return
	 */
	AppMessage cancelOrder(long id);
	
	/**
	 * 商户确认订单接口：正常车主支付完订单并且已经将钥匙存入蓝牙柜后，商户可确认订单；
	 * 商户取消订单接口：直接更改订单状态；
	 * 商户确认已取钥匙接口：商户取到车钥匙后，更新订单状态为“服务中；” 
	 * 商户完成订单接口：商户存入车钥匙后，更新订单状态为“已完成”
	 * 这几个商户操作订单的接口，根据类型来判断
	 * 
	 * @param orderNo
	 * @param operateType 操作类型 {@link OperateType}
	 * @return
	 */
	AppMessage merchantOperateOrder(long id,OperateType operateType,Double startMileage,Double endMileage);

	/**
	 * 订单图片上传 订单Id或者用户userName(头像上传)
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	AppMessage uploadImages(Long id,ImageType imgType,Integer osName,Integer appVersionCode,MultipartFile ...files) throws Exception;
	
	AppMessage payOrder(long orderId,CarWashingRecord.FeePayType payType);

	/**
	 * 查询用户的订单根据订单状态
	 * @param userName
	 * @param status
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	AppMessage findMyOrders(String userName,Long packageRecordId, com.arf.carbright.entity.CarWashingRecord.Status status,int pageSize, int pageNo);
	
	/**
	 * 查询用户订单记录更状态查询
	 * @param userName
	 * @param status :status=ONGOING 正在进行，status=FINISH 已经完成
	 * @return
	 */
	List<CarWashingRecord> findOrderByUserName(String userName,String status);
	
	/**
	 * 通过订单编号来查询订单记录
	 * @param orderNo
	 * @return
	 */
	CarWashingRecord findByOrderNo(String orderNo);
	
	/**
	 * 通过订单编号
	 * @param orderNo
	 * @return
	 */
	CarWashingRecord findByOrderNum(String orderNo);
	
	/**
	 * 商户查询自己的订单接口
	 * @param businessLoginName
	 * @param status 订单状态
	 * @return
	 */
	AppMessage findMerchantOrders(String userName,CarWashingRecord.Status status,int pageSize, int pageNo);

	/**
	 * 订单分析统计
	 * @param businessId
	 * @param status
	 * @param result
	 * @param request
	 * @return
	 */
	public AppMessage orderMerchantAnalysis(Long businessId, HttpServletRequest request);
	
	/**
	 * 上传图片方法
	 * @param files
	 * @return
	 * @throws IOException
	 */
	public String uploadPic(MultipartFile ...files) throws IOException ;
	
	
	
}
