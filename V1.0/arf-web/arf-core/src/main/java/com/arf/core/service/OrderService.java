/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.arf.core.Filter;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Cart;
import com.arf.core.entity.CouponCode;
import com.arf.core.entity.Goods;
import com.arf.core.entity.Invoice;
import com.arf.core.entity.Member;
import com.arf.core.entity.NoPricingOrder;
import com.arf.core.entity.Order;
import com.arf.core.entity.Payment;
import com.arf.core.entity.PaymentMethod;
import com.arf.core.entity.Receiver;
import com.arf.core.entity.Refunds;
import com.arf.core.entity.Returns;
import com.arf.core.entity.Shipping;
import com.arf.core.entity.ShippingMethod;

/**
 * Service - 订单
 * 
 * @author arf
 * @version 4.0
 */
public interface OrderService extends BaseService<Order, Long> {

	/**
	 * 根据编号查找订单
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 订单，若不存在则返回null
	 */
	Order findBySn(String sn);

	/**
	 * 查找订单
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param member
	 *            会员
	 * @param isPendingReceive
	 *            是否等待收款
	 * @param isPendingRefunds
	 *            是否等待退款
	 * @param isAllocatedStock
	 *            是否已分配库存
	 * @param hasExpired
	 *            是否已过期
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 订单
	 */
	List<Order> findList(Order.Type type, Order.Status status, Member member, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Integer count, List<Filter> filters, List<com.arf.core.Order> orders);

	/**
	 * 查找订单分页
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param member
	 *            会员
	 * @param isPendingReceive
	 *            是否等待收款
	 * @param isPendingRefunds
	 *            是否等待退款
	 * @param isAllocatedStock
	 *            是否已分配库存
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 订单分页
	 */
	Page<Order> findPage(Order.Type type, Order.Status status, Member member, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable);

	/**
	 * 筛选查找订单分页
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param isPendingReceive
	 *            是否等待收款
	 * @param isPendingRefunds
	 *            是否等待退款
	 * @param isAllocatedStock
	 *            是否已分配库存
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 订单分页
	 */
	Page<Order> findPageFilterOrder(Order.Type type, Order.Status status, Admin admin,Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable);

	/**
	 * 查询订单数量
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param member
	 *            会员
	 * @param goods
	 *            货品
	 * @param isPendingReceive
	 *            是否等待收款
	 * @param isPendingRefunds
	 *            是否等待退款
	 * @param isAllocatedStock
	 *            是否已分配库存
	 * @param hasExpired
	 *            是否已过期
	 * @return 订单数量
	 */
	Long count(Order.Type type, Order.Status status, Member member, Goods goods, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired);

	/**
	 * 筛选订单数量
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param admin
	 *            管理员
	 * @param goods
	 *            货品
	 * @param isPendingReceive
	 *            是否等待收款
	 * @param isPendingRefunds
	 *            是否等待退款
	 * @param isAllocatedStock
	 *            是否已分配库存
	 * @param hasExpired
	 *            是否已过期
	 * @return 订单数量
	 */
	Long countFilterOrder(Order.Type type, Order.Status status, Admin admin, Goods goods, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired);

	/**
	 * 计算税金
	 * 
	 * @param price
	 *            商品价格
	 * @param promotionDiscount
	 *            促销折扣
	 * @param couponDiscount
	 *            优惠券折扣
	 * @param offsetAmount
	 *            调整金额
	 * @return 税金
	 */
	BigDecimal calculateTax(BigDecimal price, BigDecimal promotionDiscount, BigDecimal couponDiscount, BigDecimal offsetAmount);

	/**
	 * 计算税金
	 * 
	 * @param order
	 *            订单
	 * @return 税金
	 */
	BigDecimal calculateTax(Order order);

	/**
	 * 计算订单金额
	 * 
	 * @param price
	 *            商品价格
	 * @param fee
	 *            支付手续费
	 * @param freight
	 *            运费
	 * @param tax
	 *            税金
	 * @param promotionDiscount
	 *            促销折扣
	 * @param couponDiscount
	 *            优惠券折扣
	 * @param offsetAmount
	 *            调整金额
	 * @return 订单金额
	 */
	BigDecimal calculateAmount(BigDecimal price, BigDecimal fee, BigDecimal freight, BigDecimal tax, BigDecimal promotionDiscount, BigDecimal couponDiscount, BigDecimal offsetAmount);

	/**
	 * 计算订单金额
	 * 
	 * @param order
	 *            订单
	 * @return 订单金额
	 */
	BigDecimal calculateAmount(Order order);

	/**
	 * 判断订单是否锁定
	 * 
	 * @param order
	 *            订单
	 * @param admin
	 *            管理员
	 * @param autoLock
	 *            是否自动加锁
	 * @return 订单是否锁定
	 */
	boolean isLocked(Order order, Admin admin, boolean autoLock);

	/**
	 * 判断订单是否锁定
	 * 
	 * @param order
	 *            订单
	 * @param member
	 *            会员
	 * @param autoLock
	 *            是否自动加锁
	 * @return 订单是否锁定
	 */
	boolean isLocked(Order order, Member member, boolean autoLock);

	/**
	 * 订单锁定
	 * 
	 * @param order
	 *            订单
	 * @param admin
	 *            管理员
	 */
	void lock(Order order, Admin admin);

	/**
	 * 订单锁定
	 * 
	 * @param order
	 *            订单
	 * @param member
	 *            会员
	 */
	void lock(Order order, Member member);

	/**
	 * 分配库存
	 * 
	 * @param order
	 *            订单
	 */
	void allocateStock(Order order);

	/**
	 * 释放已分配库存
	 * 
	 * @param order
	 *            订单
	 */
	void releaseAllocatedStock(Order order);

	/**
	 * 释放过期订单已分配库存
	 */
	void releaseExpiredAllocatedStock();

	/**
	 * 订单生成
	 * 
	 * @param type
	 *            类型
	 * @param cart
	 *            购物车
	 * @param receiver
	 *            收货地址
	 * @param paymentMethod
	 *            支付方式
	 * @param shippingMethod
	 *            配送方式
	 * @param couponCode
	 *            优惠码
	 * @param invoice
	 *            发票
	 * @param balance
	 *            使用余额
	 * @param memo
	 *            附言
	 * @return 订单
	 */
	Order generate(Order.Type type, Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, CouponCode couponCode, Invoice invoice, BigDecimal balance, String memo);

	/**
	 * 订单创建
	 * 
	 * @param type
	 *            类型
	 * @param cart
	 *            购物车
	 * @param receiver
	 *            收货地址
	 * @param paymentMethod
	 *            支付方式
	 * @param shippingMethod
	 *            配送方式
	 * @param couponCode
	 *            优惠码
	 * @param invoice
	 *            发票
	 * @param balance
	 *            使用余额
	 * @param memo
	 *            附言
	 * @return 订单
	 */
	Order create(Order.Type type, Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, CouponCode couponCode, Invoice invoice, BigDecimal balance, String memo);

	/**
	 * 订单更新
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void update(Order order, Admin operator);

	/**
	 * 订单取消
	 * 
	 * @param order
	 *            订单
	 */
	void cancel(Order order);

	/**
	 * 订单审核
	 * 
	 * @param order
	 *            订单
	 * @param passed
	 *            是否审核通过
	 * @param operator
	 *            操作员
	 */
	void review(Order order, boolean passed, Admin operator);
	
	void review(Order order, boolean passed, Member operator);

	/**
	 * 订单收款
	 * 
	 * @param order
	 *            订单
	 * @param payment
	 *            收款单
	 * @param operator
	 *            操作员
	 */
	void payment(Order order, Payment payment, Admin operator);

	void paymentNoprice(Order order, Payment payment, Admin operator) throws Exception;
	/**
	 * 订单退款
	 * 
	 * @param order
	 *            订单
	 * @param refunds
	 *            退款单
	 * @param operator
	 *            操作员
	 */
	void refunds(Order order, Refunds refunds, Admin operator);

	/**
	 * 订单发货
	 * 
	 * @param order
	 *            订单
	 * @param shipping
	 *            发货单
	 * @param operator
	 *            操作员
	 */
	void shipping(Order order, Shipping shipping, Admin operator);
	/**
     * 订单发货
     * 
     * @param order
     *            订单
     * @param shipping
     *            发货单
     * @param operator
     *            操作员
     */
	void shipping(Order order, Shipping shipping,Member operator);
	/**
	 * 订单退货
	 * 
	 * @param order
	 *            订单
	 * @param returns
	 *            退货单
	 * @param operator
	 *            操作员
	 */
	void returns(Order order, Returns returns, Admin operator);
	/**
     * 订单退货
     * 
     * @param order
     *            订单
     * @param returns
     *            退货单
     * @param operator
     *            操作员
     */
	void returns(Order order, Returns returns,Member operator);
	/**
	 * 订单收货
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void receive(Order order, Admin operator);
	/**
     * 订单收货
     * 
     * @param order
     *            订单
     * @param operator
     *            操作员
     */
	void receive(Order order, Member operator,boolean flag);
	/**
	 * 订单完成
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void complete(Order order, Admin operator);
	/**
     * 订单完成
     * 
     * @param order
     *            订单
     * @param operator
     *            操作员
     */
	void complete(Order order, Member operator,boolean flag);
	/**
	 * 订单失败
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void fail(Order order, Admin operator);
	/**
     * 订单失败
     * 
     * @param order
     *            订单
     * @param operator
     *            操作员
     */
	void fail(Order order, Member operator,boolean flag);
	/**
     * 订单创建
     * 
     * @param type
     *            类型
     * @param cart
     *            购物车
     * @param receiver
     *            收货地址
     * @param paymentMethod
     *            支付方式
     * @param shippingMethod
     *            配送方式
     * @param couponCode
     *            优惠码
     * @param invoice
     *            发票
     * @param balance
     *            使用余额
     * @param memo
     *            附言
     * @return 订单
     */
	public Order appcreate(Member member,Order.Type type, Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, CouponCode couponCode, Invoice invoice, BigDecimal balance, String memo,String paymentPluginId,Integer useBalance,String imgurl,String memberAddress,String memberPoint);
	
	/**
     * 订单取消
     * 
     * @param order
     *            订单
     */
    void appcancel(Member member,Order order);
    
    Order appgenerate(Order.Type type, Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, CouponCode couponCode, Invoice invoice, BigDecimal balance, String memo)throws Exception;
    
    void appcancelNopricing(Member member,Order order,String memo,NoPricingOrder npo);
    
    Page<Order> findPagebiz(Order.Type type, Order.Status status, Member member, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable);
//	Order generate(Order.Type type,Integer quantity,Product product,Member member,Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, CouponCode couponCode, Invoice invoice, BigDecimal balance, String memo);
    
    List<Map<String,Object>> getOrderSize(Member member,Integer type);
    /**
     * 退款
     * @param order
     */
    void retOrderMoney(Admin member,Order order);
    /**
     * 结束订单，未退款
     * @param member
     * @param order
     */
    void noretOrderMoney(Admin member,Order order);
    /***
     * 获取未评论的最小的订单id
     * @param goodId
     * @param memberId
     * @return
     */
    public Long getNoReviewOrderId(Long goodId,Long memberId);
}