/**
 * @(#)ViolationRecordCompare.java
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
 * 用于比较的违章记录类
 *
 * @author arf
 * @since 2016-2-25
 */
@Entity
@Table(name = "violation_record_compare")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "violationRecordCompare_sequence")
public class ViolationRecordCompare extends BaseEntity<Long>{
    /**
     * 
     */
    private static final long serialVersionUID = 8998343800101983194L;
    /**车架号*/
    private String his_no;
    /**车牌号*/
    private String license_plate_number;
    /**旧违章记录*/
    private String oldrecords;
    /**新违章记录*/
    private String newrecords;
    /**用户名*/
    private String username;
    /**  
     * 获取车架号  
     * @return his_no 车架号  
     */
    @Column(length=10)
    public String getHis_no() {
        return his_no;
    }
    /**  
     * 设置车架号  
     * @param his_no 车架号  
     */
    public void setHis_no(String his_no) {
        this.his_no = his_no;
    }
    /**  
     * 获取车牌号  
     * @return license_plate_number 车牌号  
     */
    @Column(length=10)
    public String getLicense_plate_number() {
        return license_plate_number;
    }
    /**  
     * 设置车牌号  
     * @param license_plate_number 车牌号  
     */
    public void setLicense_plate_number(String license_plate_number) {
        this.license_plate_number = license_plate_number;
    }
    /**  
     * 获取旧违章记录  
     * @return oldrecords 旧违章记录  
     */
    @Column(length=10000)
    public String getOldrecords() {
        return oldrecords;
    }
    /**  
     * 设置旧违章记录  
     * @param oldrecords 旧违章记录  
     */
    public void setOldrecords(String oldrecords) {
        this.oldrecords = oldrecords;
    }
    /**  
     * 获取新违章记录  
     * @return newrecords 新违章记录  
     */
    @Column(length=10000)
    public String getNewrecords() {
        return newrecords;
    }
    /**  
     * 设置新违章记录  
     * @param newrecords 新违章记录  
     */
    public void setNewrecords(String newrecords) {
        this.newrecords = newrecords;
    }
    /**  
     * 获取用户名  
     * @return username 用户名  
     */
    public String getUsername() {
        return username;
    }
    /**  
     * 设置用户名  
     * @param username 用户名  
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
