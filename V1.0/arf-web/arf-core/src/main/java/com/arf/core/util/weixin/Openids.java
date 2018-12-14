/**
 * @(#)Openids.java
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

package com.arf.core.util.weixin;

/**
 * openid对象
 *
 * @author arf
 * @since 2016-3-3
 */
public class Openids {
    /**总条数*/
    private Long total;
    /**当前拉取的条数*/
    private Long count;
    /**拉取的openid列表*/
    private String[] openid;
    /**列表末尾的openid*/
    private String nextopenid;
    /**  
     * 获取总条数  
     * @return total 总条数  
     */
    public Long getTotal() {
        return total;
    }
    /**  
     * 设置总条数  
     * @param total 总条数  
     */
    public void setTotal(Long total) {
        this.total = total;
    }
    /**  
     * 获取当前拉取的条数  
     * @return count 当前拉取的条数  
     */
    public Long getCount() {
        return count;
    }
    /**  
     * 设置当前拉取的条数  
     * @param count 当前拉取的条数  
     */
    public void setCount(Long count) {
        this.count = count;
    }
    /**  
     * 获取拉取的openid列表  
     * @return openid 拉取的openid列表  
     */
    public String[] getOpenid() {
        return openid;
    }
    /**  
     * 设置拉取的openid列表  
     * @param openid 拉取的openid列表  
     */
    public void setOpenid(String[] openid) {
        this.openid = openid;
    }
    /**  
     * 获取列表末尾的openid  
     * @return nextopenid 列表末尾的openid  
     */
    public String getNextopenid() {
        return nextopenid;
    }
    /**  
     * 设置列表末尾的openid  
     * @param nextopenid 列表末尾的openid  
     */
    public void setNextopenid(String nextopenid) {
        this.nextopenid = nextopenid;
    }
}
