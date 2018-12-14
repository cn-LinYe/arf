/**
 * @(#)NoPricingOrderDaoImpl.java
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

package com.arf.core.dao.impl;

import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import com.arf.core.dao.NoPricingOrderDao;
import com.arf.core.entity.NoPricingOrder;

/**
 * Dao - 保险
 * 
 * @author arf
 * @version 4.0
 */
@Repository("noPricingOrderDaoImpl")
public class NoPricingOrderDaoImpl extends BaseDaoImpl<NoPricingOrder,Long> implements NoPricingOrderDao{
    @Override
    public List<NoPricingOrder> findByOrderSn(String sn) {
        if (StringUtils.isEmpty(sn)) {
            return null;
        }
        String jpql = "select complains from NoPricingOrder as complains inner join complains.order as order where order.sn=:sn";
        return entityManager.createQuery(jpql,NoPricingOrder.class).setParameter("sn",sn).getResultList();
        
    }
}
