/**
 * @(#)HQLResultConvert.java
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
 *  1     2015-11-24       arf      Created
 **********************************************
 */

package com.arf.core;

import java.util.List;

/**
 * HQL结果集转换
 * author:arf
 * @author arf
 * @version 4.0
 */
public interface HQLResultConvert<T> {

    List<T> convert(List<Object> list);
}
