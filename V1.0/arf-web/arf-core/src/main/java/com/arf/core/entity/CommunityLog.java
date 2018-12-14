/**
 * @(#)CommunityLog.java
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
 *  1     2016-4-14       arf      Created
 **********************************************
 */

package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 小区日志表
 * @author Administrator
 *
 */
@Entity
@Table(name = "communitylog")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "communitylog_sequence")
public class CommunityLog extends BaseEntity<Long>{

    /**
     * 
     */
    private static final long serialVersionUID = -4570883796080160935L;
    /**旧小区ID*/
    private String oldcommunity;
    /**新小区ID*/
    private String newcommunity;
    /**用户名*/
    private String username;
    
    /**  
     * 获取旧小区ID  
     * @return oldcommunity 旧小区ID  
     */
    @Column(name = "oldcommunity", length = 32)
    public String getOldcommunity() {
        return oldcommunity;
    }
    /**  
     * 设置旧小区ID  
     * @param oldcommunity 旧小区ID  
     */
    public void setOldcommunity(String oldcommunity) {
        this.oldcommunity = oldcommunity;
    }
    /**  
     * 获取新小区ID  
     * @return newcommunity 新小区ID  
     */
    @Column(name = "newcommunity", length = 32)
    public String getNewcommunity() {
        return newcommunity;
    }
    /**  
     * 设置新小区ID  
     * @param newcommunity 新小区ID  
     */
    public void setNewcommunity(String newcommunity) {
        this.newcommunity = newcommunity;
    }
    /**  
     * 获取用户名  
     * @return username 用户名  
     */
    @Column(name = "username", length = 32)
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
