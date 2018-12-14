/**
 * @(#)RedFailLog.java
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
 *  1     2016-3-3       arf      Created
 **********************************************
 */

package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

/**
 * 记录发送失败的红包
 *
 * @author arf
 * @since 2016-3-3
 */
@Entity(name = "redFailLog")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "redFailLog_sequence")
public class RedFailLog extends BaseEntity<Long>{

    /**
     * 
     */
    private static final long serialVersionUID = -3743387345349503273L;
    
    private String user_name;
    /**
     * 红包记录（唯一标示）
     */
    private String redRecord;
    
    private int amount;
    
    private String  openId;
    
    /**
     * 红包状态
     * @return
     */
    private String status;
    
    /**
     * vip支付订单号
     * @return
     */
    private String out_trade_no;
    /***
     * 红包记录表的Id
     */
    private Long redrecordid;
    /**
     * 处理状态
     */
    private Integer dealstatus;
    
    @Column(name = "user_name", length = 64, nullable = false)
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    
    @Column(name = "redRecord", length = 64, nullable = false)
    public String getRedRecord() {
        return redRecord;
    }

    public void setRedRecord(String redRecord) {
        this.redRecord = redRecord;
    }

    @Column(name = "amount", length = 64, nullable = false)
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Column(name = "openId", length = 64, nullable = false)
    public String getOpenId() {
        return openId;
    }

    
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    @Column(name = "status",length=16)
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Column(name = "out_trade_no", length = 64)
    public String getOut_trade_no() {
        return out_trade_no;
    }
    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    /**  
     * 获取红包记录表的Id  
     * @return redrecordid 红包记录表的Id  
     */
    @Column(name="redrecordid")
    public Long getRedrecordid() {
        return redrecordid;
    }
    /**  
     * 设置红包记录表的Id  
     * @param redrecordid 红包记录表的Id  
     */
    public void setRedrecordid(Long redrecordid) {
        this.redrecordid = redrecordid;
    }

    /**  
     * 获取处理状态  
     * @return dealstatus 处理状态  
     */
    @Column(name="dealstatus")
    public Integer getDealstatus() {
        return dealstatus;
    }

    /**  
     * 设置处理状态  
     * @param dealstatus 处理状态  
     */
    public void setDealstatus(Integer dealstatus) {
        this.dealstatus = dealstatus;
    }
}
