/**
 * @(#)InsuranceDaoImpl.java
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

import org.springframework.stereotype.Repository;

import com.arf.core.dao.InsuranceDao;
import com.arf.core.entity.Insurance;

/**
 * Dao - 保险
 * 
 * @author arf
 * @version 4.0
 */
@Repository("insuranceDaoImpl")
public class InsuranceDaoImpl extends BaseDaoImpl<Insurance,Long> implements InsuranceDao{

}
