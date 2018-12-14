/**
 * @(#)NoPricingOrderServiceImpl.java
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

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.NoPricingOrderDao;
import com.arf.core.dao.OrderDao;
import com.arf.core.dao.OrderLogDao;
import com.arf.core.entity.Member;
import com.arf.core.entity.NoPricingOrder;
import com.arf.core.entity.OrderLog;
import com.arf.core.entity.NoPricingOrder.Status;
import com.arf.core.entity.Order;
import com.arf.core.service.NoPricingOrderService;

/**
 * Service - 货品
 * 
 * @author arf
 * @version 4.0
 */
@Service("noPricingOrderServiceImpl")
public class NoPricingOrderServiceImpl  extends BaseServiceImpl<NoPricingOrder,Long> implements NoPricingOrderService{
    @Resource(name = "noPricingOrderDaoImpl")
    private NoPricingOrderDao noPricingOrderDao;
    @Resource(name = "orderDaoImpl")
    private OrderDao orderDao;
    @Resource(name = "orderLogDaoImpl")
    private OrderLogDao orderLogDao;
    
    @Override
    protected BaseDao<NoPricingOrder, Long> getBaseDao() {
        return noPricingOrderDao;
    }

    @Override
    public List<NoPricingOrder> findByOrderSn(String sn) {
        return noPricingOrderDao.findByOrderSn(sn);
    }

    @Transactional(readOnly = true)
    public void bizOrderPrice(Order order,NoPricingOrder noprice,BigDecimal price){
        Member member=order.getMember();
        //获取订单金额
        BigDecimal amount=order.getAmount();
        String des=" 定价金额:"+price;
        
        if(price.compareTo(amount)>0){//如果商户定价大于等于订单金额，用户需要支付差价
            noprice.setStatus(Status.bizprice);
        }else{//如果商户定价小于订单金额，返还差价给用户，并修改订单状态
            des+=" 定价金额小于预付款";
            BigDecimal retMoney=amount.subtract(price);//获取应返还金额
            if(retMoney.compareTo(order.getBalance())<=0){//如果返还金额小于支付的现金劵,返还用户相应现金劵
                order.setRetBalance(retMoney);
                order.setBalance(order.getBalance().subtract(retMoney));
                member.setBalance(member.getBalance().add(retMoney));
                des+=" 返还现金劵"+retMoney;
            }else{//返还所有现金劵后，再返还相应消费劵
                retMoney=retMoney.subtract(order.getBalance());//获取需要返还的消费劵
                des+=" 返还消费劵"+retMoney;
                order.setRetBalance(order.getBalance());
                member.setBalance(member.getBalance().add(order.getBalance()));
                order.setBalance(BigDecimal.ZERO);
                
                order.setRetVouchers(retMoney);//返还消费劵
                order.setVouchers(order.getVouchers().subtract(retMoney));
                member.setVouchers(member.getVouchers().add(retMoney));
            }
            order.setAmountPaid(price);
            noprice.setStatus(Status.userpayment);
            order.setStatus(com.arf.core.entity.Order.Status.pendingDelivery);
            
        }
        order.setAmount(price);
        
        orderDao.flush();
        orderDao.refresh(order);
        //更新订单对象
//        noprice.setPrice(price);
        noprice.setServicePrice(price);
        noPricingOrderDao.flush();
        noPricingOrderDao.refresh(noprice);
        
        OrderLog orderLog = new OrderLog();
        orderLog.setType(OrderLog.Type.review);
        orderLog.setOrder(order);
        orderLog.setContent("未定价服务商户定价:"+des);
        orderLogDao.persist(orderLog);
    }
}
