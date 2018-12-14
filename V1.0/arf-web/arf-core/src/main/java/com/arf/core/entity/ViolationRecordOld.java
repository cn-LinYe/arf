/**
 * @(#)ViolationRecordOld.java
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
 * 旧违章记录表
 *
 * @author arf
 * @since 2016-2-25
 */
@Entity
@Table(name = "violation_record_old")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "violationRecordOld_sequence")
public class ViolationRecordOld extends BaseEntity<Long>{
    /**
     * 
     */
    private static final long serialVersionUID = 5774345405368855681L;
    /**车架号*/
    private String his_no;
    /**车牌号*/
    private String license_plate_number;
    /**是否有违章*/
    private boolean hasData;
    /**违章数据json字符串*/
    private String record;
    /**  
     * 获取车架号  
     * @return his_no 车架号  
     */
    @Column()
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
    @Column()
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
     * 获取是否有违章  
     * @return hasData 是否有违章  
     */
    @Column(name="has_data")
    public boolean isHasData() {
        return hasData;
    }
    /**  
     * 设置是否有违章  
     * @param hasData 是否有违章  
     */
    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }
    /**  
     * 获取违章数据json字符串  
     * @return record 违章数据json字符串  
     */
    @Column(length=10000)
    public String getRecord() {
        return record;
    }
    /**  
     * 设置违章数据json字符串  
     * @param record 违章数据json字符串  
     */
    public void setRecord(String record) {
        this.record = record;
    }
}
