/**
 * @(#)NoPricingOrderService.java
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

package com.arf.core.service;

import java.math.BigDecimal;
import java.util.List;

import com.arf.core.entity.NoPricingOrder;
import com.arf.core.entity.Order;

/**
 * Service - 未定价
 * 
 * @author arf
 * @version 4.0
 */
public interface NoPricingOrderService extends BaseService<NoPricingOrder,Long>{

    public List<NoPricingOrder> findByOrderSn(String sn);
    /**
     * 商户定价逻辑
     * @param order
     * @param noprice
     */
    void bizOrderPrice(Order order,NoPricingOrder noprice,BigDecimal price);
}
