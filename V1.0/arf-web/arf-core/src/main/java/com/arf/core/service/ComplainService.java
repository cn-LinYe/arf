/**
 * @(#)ComplainService.java
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
 *  1     2015-11-28       arf      Created
 **********************************************
 */

package com.arf.core.service;

import java.util.List;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Complain;
import com.arf.core.entity.Member;

/**
 * Service - 投诉
 * 
 * @author arf
 * @version 4.0
 */
public interface ComplainService extends BaseService<Complain,Long> {
    /**
     * 根据订单编号查找投诉记录
     * @param sn
     * @return
     */
    List<Complain> findByOrderSn(String sn);

    /**
     * 查询投诉信息
     * @param pageable 分页信息
     * @return 投诉信息
     */ 
    Page<Complain> findPage(Complain complain,Pageable pageable);
    /**
     * 根据admin过滤投诉信息
     * @param admin admin
     * @param pageable 分页信息
     * @return
     */
    Page<Complain> findPage(Admin admin,Pageable pageable);
    /**
     * 更新投诉单
     * @param id
     * @param isretMoney
     * @param memo
     */
    void updateComplain(Long id,Boolean isretMoney,String memo);
    /**
     * 查询用户当前的投诉
     * @param member
     * @param pageable
     * @return
     */
    Page<Complain> findPage(Member member,Pageable pageable);
}
