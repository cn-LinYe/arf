/**
 * @(#)AppArticle.java
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
 *  1     2016-3-28       arf      Created
 **********************************************
 */

package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Class description goes here.
 *
 * @author arf
 * @since 2016-3-28
 */
@Entity
@Table(name = "edsys_article")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "edsys_article_sequence")
public class AppArticle extends BaseEntity<Long>{

    /**
     * 
     */
    private static final long serialVersionUID = 4406389347271094175L;
    
    /**
     * 文章标题
     */
    @Column(name="title",nullable=false,length=200)
    private String title;
    /**
     * 文章内容
     */
    @Column(name="content",length=10000)
    private String content;
    /**
     * 用户名
     */
    @Column(name="username",nullable=false,length=100)
    private String username;
    /**
     * 访问次数
     */
    @Column(name="clickcount",nullable=false)
    private Long clickcount=0L;
    /**  
     * 获取文章标题  
     * @return title 文章标题  
     */
    public String getTitle() {
        return title;
    }
    /**  
     * 设置文章标题  
     * @param title 文章标题  
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**  
     * 获取文章内容  
     * @return content 文章内容  
     */
    public String getContent() {
        return content;
    }
    /**  
     * 设置文章内容  
     * @param content 文章内容  
     */
    public void setContent(String content) {
        this.content = content;
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
    /**  
     * 获取访问次数  
     * @return clickcount 访问次数  
     */
    public Long getClickcount() {
        return clickcount;
    }
    /**  
     * 设置访问次数  
     * @param clickcount 访问次数  
     */
    public void setClickcount(Long clickcount) {
        this.clickcount = clickcount;
    }
}
