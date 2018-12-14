/**
 * @(#)Insurance.java
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
 * Entity - 保险订单
 * 
 * @author arf
 * @version 4.0
 */
@Entity
@Table(name = "xx_order_insurance")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_order_insurance_sequence")
public class Insurance  extends BaseEntity<Long>{

    /**
     * 
     */
    private static final long serialVersionUID = 6041237674733547065L;
    
    public enum Status{
        /** 提交 */
        submit,
        /** 商户定价 */
        bizprice,
        /** 提交新保单 */
        newinsurance,
        /** 用户确认 */
        reciver ,
        /** 用户/商户取消 */
        cancel
    }
    
    /** 订单 */
    private Order order;
    /** 保单号 */
    private String no;
    /** 保单价格 */
    private BigDecimal price;
    /** 订单状态 */
    private Status status;
    /** 保单图片 */
    private String image;
    /** 上一年保单图片 */
    private String lastyearImage;
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
     * 获取保单号  
     * @return no 保单号  
     */
    @Column(length=200)
    public String getNo() {
        return no;
    }
    /**  
     * 设置保单号  
     * @param no 保单号  
     */
    public void setNo(String no) {
        this.no = no;
    }
    /**  
     * 获取保单价格  
     * @return price 保单价格  
     */
    @Column(precision = 21, scale = 6)
    public BigDecimal getPrice() {
        return price;
    }
    /**  
     * 设置保单价格  
     * @param price 保单价格  
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    /**  
     * 获取订单状态  
     * @return status 订单状态  
     */
    @Column()
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
     * 获取保单图片  
     * @return image 保单图片  
     */
    @Column(length=500)
    public String getImage() {
        return image;
    }
    /**  
     * 设置保单图片  
     * @param image 保单图片  
     */
    public void setImage(String image) {
        this.image = image;
    }
    /**  
     * 获取上一年保单图片  
     * @return lastyearImage 上一年保单图片  
     */
    @Column(length=500)
    public String getLastyearImage() {
        return lastyearImage;
    }
    /**  
     * 设置上一年保单图片  
     * @param lastyearImage 上一年保单图片  
     */
    public void setLastyearImage(String lastyearImage) {
        this.lastyearImage = lastyearImage;
    }
}
