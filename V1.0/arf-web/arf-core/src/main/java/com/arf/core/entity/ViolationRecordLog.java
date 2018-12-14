/**
 * @(#)ViolationRecordLog.java
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
 *  1     2016-2-25       arf      Created
 **********************************************
 */

package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 违章查询日志表
 *
 * @author arf
 * @since 2016-2-25
 */
@Entity
@Table(name = "violation_record_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "violation_record_log_sequence")
public class ViolationRecordLog extends BaseEntity<Long>{

    /**
     * 
     */
    private static final long serialVersionUID = -3245662400367463354L;
    /**成功*/
    public final static String SUCCESS="success";
    /**失败*/
    public final static String FAIL="fail";
    
    /** 备注 */
    private String memo;
    /**操作人*/
    private String operator;
    /**状态*/
    private String status;
    /**  
     * 获取备注  
     * @return memo 备注  
     */
    @Column(length=500)
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

    /**  
     * 获取操作人  
     * @return operator 操作人  
     */
    @Column(length=100)
    public String getOperator() {
        return operator;
    }

    /**  
     * 设置操作人  
     * @param operator 操作人  
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**  
     * 获取状态  
     * @return status 状态  
     */
    @Column(length=10)
    public String getStatus() {
        return status;
    }

    /**  
     * 设置状态  
     * @param status 状态  
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
