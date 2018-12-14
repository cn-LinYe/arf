/**
 * @(#)NoPricingOrder.java
 * 
 * Copyright arf.
 *
 * @Version: 1.0
 * @JDK: jdk jdk1.6.0_10
 * @Module: arf-core
 */ 
 /*- 				History
 **********************************************
 *  ID      DATE           PERSON       REASON
 *  1     2015-11-24       arf      Created
 **********************************************
 */

package com.arf.core.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 未定价商品
 * 
 * @author arf
 * @version 4.0
 */
@Entity
@Table(name = "xx_order_noPricing")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_order_noPricing_sequence")
public class NoPricingOrder extends BaseEntity<Long>{

    /**
     * 
     */
    private static final long serialVersionUID = -5046779908560505710L;
    public enum Status{
        /** 提交*/
        submit,
        /** 完成预付款 */
        payment,
        /** 商户定价 */
        bizprice,
        /** 用户取消 */
        canceled,
        /** 商户确认取消 */
        bizcancel,
        /** 用户完成付款 */
        userpayment,
        /** 商户已服务 */
        delivered,
        /** 用户确认 */
        received,
        /** 失败已退款 */
        failretmoney,
        
        /** 失败未退款 */
        failnoretmoney,
        
        /** 已失败 */
        failed,
        
        /** 已完成 */
        completed
    }
    /** 订单 */
    private Order order;
    /** 预付款金额 */
    private BigDecimal price;
    /** 订单状态 */
    private Status status;
    /** 商户提出的服务金额 */
    private BigDecimal servicePrice;
    /** 备注 */
    private String memo;
    /**  
     * 获取订单  
     * @return order 订单  
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="orders",nullable = false,unique=true)
    public Order getOrder() {
        return order;
    }
    /**  
     * 设置订单  
     * @param order 订单  
     */
    public void setOrder(Order order) {
        this.order = order;
    }
    /**  
     * 获取预付款金额  
     * @return price 预付款金额  
     */
    @Column(name="price",precision = 21, scale = 6)
    public BigDecimal getPrice() {
        return price;
    }
    /**  
     * 设置预付款金额  
     * @param price 预付款金额  
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    /**  
     * 获取订单状态  
     * @return status 订单状态  
     */
    @Column(name="status")
    public Status getStatus() {
        return status;
    }
    /**  
     * 设置订单状态  
     * @param status 订单状态  
     */
    public void setStatus(Status status) {
        this.status = status;
    }
    /**  
     * 获取商户提出的服务金额  
     * @return servicePrice 商户提出的服务金额  
     */
    @Column(name="serviceprice",precision = 21, scale = 6)
    public BigDecimal getServicePrice() {
        return servicePrice;
    }
    /**  
     * 设置商户提出的服务金额  
     * @param servicePrice 商户提出的服务金额  
     */
    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }
    /**  
     * 获取备注  
     * @return memo 备注  
     */
    @Column(name="memo")
    public String getMemo() {
        return memo;
    }
    /**  
     * 设置备注  
     * @param memo 备注  
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }
}
