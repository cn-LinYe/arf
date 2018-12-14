/**
 * @(#)Complain.java
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
 *  1     2015-11-28       arf      Created
 **********************************************
 */

package com.arf.core.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 订单投诉
 * 
 * @author arf
 * @version 4.0
 */
@Entity
@Table(name = "xx_order_complains")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_order_complains_sequence")
public class Complain extends BaseEntity<Long>{
    /**
     * 状态
     * @author arf
     *
     */
    public enum Status{
        /**提交*/
        submit,
        /**处理*/
        deal
    }
    /**
     * 
     */
    private static final long serialVersionUID = -8458839486191698611L;
    /** 投诉内容 */
    private String content;
    /** IP */
    private String ip;
    /** 是否显示 */
    private Boolean isshow;
    /** 订单 */
    private Order order;
    /** 会员 */
    private Member member;
    /** 处理状态 */
    private Status status;
    /**是否需要返还金额*/
    private Boolean isretMoney;
    /**处理结果备注*/
    private String memo;
    /**用户是否删除*/
    private Boolean isdel;
    
    
    /**  
     * 获取用户是否删除  
     * @return isdel 用户是否删除  
     */
    @Column()
    public Boolean getIsdel() {
        return isdel;
    }
    /**  
     * 设置用户是否删除  
     * @param isdel 用户是否删除  
     */
    public void setIsdel(Boolean isdel) {
        this.isdel = isdel;
    }
    /**  
     * 获取是否需要返还金额  
     * @return isretMoney 是否需要返还金额  
     */
    @Column()
    public Boolean getIsretMoney() {
        return isretMoney;
    }
    /**  
     * 设置是否需要返还金额  
     * @param isretMoney 是否需要返还金额  
     */
    public void setIsretMoney(Boolean isretMoney) {
        this.isretMoney = isretMoney;
    }
    /**  
     * 获取处理结果备注  
     * @return memo 处理结果备注  
     */
    public String getMemo() {
        return memo;
    }
    /**  
     * 设置处理结果备注  
     * @param memo 处理结果备注  
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }
    /**  
     * 获取投诉内容  
     * @return content 投诉内容  
     */
    @Column(length=2000)
    public String getContent() {
        return content;
    }
    /**  
     * 设置投诉内容  
     * @param content 投诉内容  
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**  
     * 获取IP  
     * @return ip IP  
     */
    @Column(length=20)
    public String getIp() {
        return ip;
    }
    /**  
     * 设置IP  
     * @param ip IP  
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
    /**  
     * 获取是否显示  
     * @return isshow 是否显示  
     */
    @Column(nullable = false)
    public Boolean getIsshow() {
        return isshow;
    }
    /**  
     * 设置是否显示  
     * @param isshow 是否显示  
     */
    public void setIsshow(Boolean isshow) {
        this.isshow = isshow;
    }
    /**  
     * 获取订单  
     * @return order 订单  
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="orders",nullable = false, updatable = false)
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
     * 获取会员  
     * @return member 会员  
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    public Member getMember() {
        return member;
    }
    /**  
     * 设置会员  
     * @param member 会员  
     */
    public void setMember(Member member) {
        this.member = member;
    }
    /**  
     * 获取处理状态  
     * @return status 处理状态  
     */
    @Column(nullable = false)
    public Status getStatus() {
        return status;
    }
    /**  
     * 设置处理状态  
     * @param status 处理状态  
     */
    public void setStatus(Status status) {
        this.status = status;
    }
}
