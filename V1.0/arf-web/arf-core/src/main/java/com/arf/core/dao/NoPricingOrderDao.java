/**
 * @(#)NoPricingOrderDao.java
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
 *  1     2015-11-25       arf      Created
 **********************************************
 */

package com.arf.core.dao;

import java.util.List;

import com.arf.core.entity.NoPricingOrder;

/**
 * Dao - 非定价服务
 * 
 * @author arf
 * @version 4.0
 */
public interface NoPricingOrderDao extends BaseDao<NoPricingOrder,Long> {

    public List<NoPricingOrder> findByOrderSn(String sn);
}
