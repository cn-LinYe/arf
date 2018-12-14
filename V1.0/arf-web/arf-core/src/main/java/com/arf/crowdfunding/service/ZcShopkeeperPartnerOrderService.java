package com.arf.crowdfunding.service;


import java.math.BigDecimal;
import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.crowdfunding.entity.ZcShopkeeperPartnerOrder;

public interface ZcShopkeeperPartnerOrderService extends BaseService<ZcShopkeeperPartnerOrder, Long>{

	/**
	 * 查找支付成功的订单
	 * @param community_number
	 * @param project_id
	 * @return
	 */
	public List<ZcShopkeeperPartnerOrder> getSuccessOrder(String community_number,String project_id);

	
	/**
	 * 针对店主判断众筹项目是否已有店主
	 * @param community_number	小区编号
	 * @param project_id		项目ID
	 * @return
	 */
	public boolean isFullShop(String community_number,String project_id);
	/**
	 * 插入订单数据(店主)
	 * @param community_number	小区编号
	 * @param user_name			用户APP登录名
	 * @param username			用户真实姓名
	 * @param project_id		项目ID
	 * @param shopkeeper_amount 众筹金额
	 * @param mobile			联系方法
	 * @return					
	 */
	public ZcShopkeeperPartnerOrder saveOpenShopMessage(String community_number, String user_name, String username, String project_id,String shopkeeper_amount,
			String mobile,BigDecimal leastAmount,int shopkeeperPartner);
	/**
	 * 查找用户未支付订单
	 * @param community_number 	小区编号
	 * @param project_id		项目ID
	 * @param user_name			用户APP登录名
	 * @return					
	 */
	public ZcShopkeeperPartnerOrder getNoPaymentOrder(String community_number,String project_id,String user_name);
	/**
	 * 得到已经售出的众筹的金额
	 * @param communityNumber	小区编号
	 * @param projectId			项目ID
	 * @return
	 */
	public BigDecimal getOrderedShareNum(String communityNumber,String projectId);
	/**
	 * 当支付成功时 更新订单状态
	 * @param order_no	订单号
	 * @param paymode	支付方式
	 * @return
	 */
	public int saveShopkeeperPaymentMessage(String order_no, int paymode);
	/**
	 * 查找订单是否存在
	 * @param orderNo	订单号
	 * @return
	 */
	public boolean isExistOrder(String orderNo);
	/**
	 * 通过订单号查找支付订单号
	 * @param order_no	订单号
	 * @return
	 */
	public ZcShopkeeperPartnerOrder getOutTradeNo(String order_no,Integer shoperParenter);
	/**
	 * 取消订单
	 * @param orderNo	订单号
	 * @return
	 */
	public int cancelOrder(String orderNo);
	/**
	 * 得到订单信息
	 * @param order_no	订单号
	 * @return
	 */
	public ZcShopkeeperPartnerOrder getOrderMessage(String order_no);
	/**
	 * 通过项目ID和小区编号得到店主信息
	 * @param project_id		项目ID
	 * @param community_number	小区编号
	 * @return
	 */
	public String getShopkeeperMobile(String project_id,String community_number);
	/**
	 * 得到项目所有合伙人的手机号码
	 * @param project_id	项目ID
	 * @return
	 */
	public List<ZcShopkeeperPartnerOrder> getAllPartnerMobile(String project_id);
	/**
	 * 当请求成为店主时 校验该项目是否已有店主
	 * @param community_number	小区编号
	 * @param project_id		项目ID
	 * @return
	 */
	public boolean isExistShopkeeper(String community_number,String project_id);
	/**
	 * 通过小区编号跟项目ID查找店主的账户号
	 * @param community_number	小区编号
	 * @param project_id		项目ID
	 * @return
	 */
	public String getShopkeeperAccount(String community_number,String project_id);
	/**
	 * 判断订单是否已被支付
	 * @param orderNo	订单号
	 * @return
	 */
	public boolean isPayment(String orderNo);
	/**
	 * 判断改名用户是否已有申请成为店主的订单
	 * @param community_number	小区编号
	 * @param project_id		项目ID
	 * @param user_name			用户APP登录
	 * @return
	 */
	public boolean isCreateOrder(String community_number,String project_id,String user_name);
	/**
	 * 当项目已有店主时，把所有请求成为该项目店主的订单设置为已取消
	 * @param community_number
	 * @param project_id
	 * @param pser_name
	 */
	public void updateShopKeeperOrderStatus(String community_number,String project_id);
	/**
	 * 当众筹已满时，对请求成为该项目合伙人的订单状态全部设置为已取消
	 * @param community_number	小区编号
	 * @param project_id		项目ID
	 */
	public void updateParenterOrderStatus(String community_number,String project_id);
	
	/**
	 * 判断申请成为合伙人的用户是否意识项目店主
	 * @param community_number
	 * @param project_id
	 * @param user_name
	 * @return
	 */
	public boolean isShopKeeper(String community_number,String project_id,String user_name);
	
	public List<ZcShopkeeperPartnerOrder> getNoPaymentOrderSuccess(String community_number, String project_id) ;
	
	public int updateOpenShopMessage(long id, BigDecimal payment_amount);

	public BigDecimal getSameUserShares(String community_number,String project_id,String user_name);

}
