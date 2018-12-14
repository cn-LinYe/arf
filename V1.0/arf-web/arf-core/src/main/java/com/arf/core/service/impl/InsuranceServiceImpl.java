/**
 * @(#)InsuranceServiceImpl.java
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

package com.arf.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.InsuranceDao;
import com.arf.core.entity.Insurance;
import com.arf.core.service.InsuranceService;

/**
 * Service - 货品
 * 
 * @author arf
 * @version 4.0
 */
@Service("insuranceServiceImpl")
public class InsuranceServiceImpl extends BaseServiceImpl<Insurance,Long> implements InsuranceService{
    
    @Resource(name = "insuranceDaoImpl")
    private InsuranceDao insuranceDao;
    
    @Override
    protected BaseDao<Insurance, Long> getBaseDao() {
        return insuranceDao;
    }

}
