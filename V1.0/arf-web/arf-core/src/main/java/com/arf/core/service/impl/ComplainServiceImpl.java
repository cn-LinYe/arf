/**
 * @(#)ComplainServiceImpl.java
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

package com.arf.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.ComplainDao;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Complain;
import com.arf.core.entity.Complain.Status;
import com.arf.core.entity.Member;
import com.arf.core.service.AdminService;
import com.arf.core.service.ComplainService;
import com.arf.core.service.MemberService;
import com.arf.core.service.OrderService;

/**
 * ServiceImpl - 投诉
 * 
 * @author arf
 * @version 4.0
 */
@Service("complainServiceImpl")
public class ComplainServiceImpl extends BaseServiceImpl<Complain,Long> implements ComplainService{
    @Resource(name = "complainDaoImpl")
    private ComplainDao complainDao;
    
    @Resource(name = "orderServiceImpl")
    private OrderService orderService;
    
    @Resource(name = "memberServiceImpl")
    private MemberService memberService;
    
    @Resource(name = "adminServiceImpl")
    private AdminService adminService;
    @Override
    protected BaseDao<Complain, Long> getBaseDao() {
        return complainDao;
    }
    @Override
    public List<Complain> findByOrderSn(String sn) {
        return complainDao.findByOrderSn(sn);
    }
	@Override
	public Page<Complain> findPage(Complain complain, Pageable pageable) {
		
		return complainDao.findPage(pageable);
	}
	@Override
	public Page<Complain> findPage(Admin admin, Pageable pageable) {
		
		return complainDao.findPage(admin, pageable);
	}
    
	@Transactional(readOnly = true)
	public void updateComplain(Long id,Boolean isretMoney,String memo){
	    Complain complain=this.find(id);
        complain.setStatus(Status.deal);
        complain.setIsretMoney(isretMoney);
        if(isretMoney){//如果需要退款，调用订单退款处理
            orderService.retOrderMoney(adminService.getCurrent(),complain.getOrder());
        }else{
            orderService.noretOrderMoney(adminService.getCurrent(),complain.getOrder());
        }
        complain.setMemo(memo);
        complainDao.flush();
        complainDao.refresh(complain);
	}
    @Override
    public Page<Complain> findPage(Member member, Pageable pageable) {
        return complainDao.findPage(member, pageable);
    }
}
