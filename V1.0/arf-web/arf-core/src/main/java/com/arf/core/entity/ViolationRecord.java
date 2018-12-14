/**
 * @(#)ViolationRecord.java
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

/**
 * 违章记录项对象
 *
 * @author arf
 * @since 2016-2-25
 */
//@Entity(name = "violationRecord")
//@SequenceGenerator(name = "sequenceGenerator", sequenceName = "violationRecord_sequence")
public class ViolationRecord{
    /**
     * 
     */
//    private static final long serialVersionUID = 7543129925297033510L;
    /**违章时间*/
    private String time;
    /**违章地点*/
    private String location;
    /**违章原因*/
    private String reason;
    /**  
     * 获取违章时间  
     * @return time 违章时间  
     */
//    @Column(length=60)
    public String getTime() {
        return time;
    }
    /**  
     * 设置违章时间  
     * @param time 违章时间  
     */
    public void setTime(String time) {
        this.time = time;
    }
    /**  
     * 获取违章地点  
     * @return location 违章地点  
     */
//    @Column(length=60)
    public String getLocation() {
        return location;
    }
    /**  
     * 设置违章地点  
     * @param location 违章地点  
     */
    public void setLocation(String location) {
        this.location = location;
    }
    /**  
     * 获取违章原因  
     * @return reason 违章原因  
     */
//    @Column(length=200)
    public String getReason() {
        return reason;
    }
    /**  
     * 设置违章原因  
     * @param reason 违章原因  
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
}
