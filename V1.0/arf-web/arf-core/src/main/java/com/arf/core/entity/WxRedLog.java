/**
 * @(#)WxRedLog.java
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
 *  1     2016-4-15       arf      Created
 **********************************************
 */

package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 微信红包日志
 *
 * @author arf
 * @since 2016-4-15
 */
@Entity
@Table(name = "wx_red_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "wx_red_log_sequence")
public class WxRedLog  extends BaseEntity<Long>{

    /**
     * 
     */
    private static final long serialVersionUID = 2575751843099245561L;
    
    /**微信返回的结果字符串*/
    private String content;
    /**红包返回code*/
    private String returncode;
    /**红包返回结果*/
    private String returnmsg;
    /**红包返回代码*/
    private String resultcode;
    /**关联的用户名*/
    private String username;
    /**关联的红包订单号*/
    private String redrecord;
    /**  
     * 获取微信返回的结果字符串  
     * @return content 微信返回的结果字符串  
     */
    @Column(name = "content", length =1000)
    public String getContent() {
        return content;
    }
    /**  
     * 设置微信返回的结果字符串  
     * @param content 微信返回的结果字符串  
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**  
     * 获取红包返回code  
     * @return returncode 红包返回code  
     */
    @Column(name = "returncode", length =500)
    public String getReturncode() {
        return returncode;
    }
    /**  
     * 设置红包返回code  
     * @param returncode 红包返回code  
     */
    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }
    /**  
     * 获取红包返回结果  
     * @return returnmsg 红包返回结果  
     */
    @Column(name = "returnmsg", length =500)
    public String getReturnmsg() {
        return returnmsg;
    }
    /**  
     * 设置红包返回结果  
     * @param returnmsg 红包返回结果  
     */
    public void setReturnmsg(String returnmsg) {
        this.returnmsg = returnmsg;
    }
    /**  
     * 获取红包返回代码  
     * @return resultcode 红包返回代码  
     */
    @Column(name = "resultcode", length =500)
    public String getResultcode() {
        return resultcode;
    }
    /**  
     * 设置红包返回代码  
     * @param resultcode 红包返回代码  
     */
    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }
    /**  
     * 获取关联的用户名  
     * @return username 关联的用户名  
     */
    @Column(name = "username", length =500)
    public String getUsername() {
        return username;
    }
    /**  
     * 设置关联的用户名  
     * @param username 关联的用户名  
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**  
     * 获取关联的红包订单号  
     * @return redrecord 关联的红包订单号  
     */
    @Column(name = "redrecord", length =500)
    public String getRedrecord() {
        return redrecord;
    }
    /**  
     * 设置关联的红包订单号  
     * @param redrecord 关联的红包订单号  
     */
    public void setRedrecord(String redrecord) {
        this.redrecord = redrecord;
    }
}
