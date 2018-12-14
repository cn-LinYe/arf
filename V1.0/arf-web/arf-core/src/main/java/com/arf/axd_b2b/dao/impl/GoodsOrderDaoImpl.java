package com.arf.axd_b2b.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.axd_b2b.dao.IGoodsOrderDao;
import com.arf.axd_b2b.entity.GoodsOrder;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("goodsOrderDao")
public class GoodsOrderDaoImpl extends BaseDaoImpl<GoodsOrder, Long>
implements IGoodsOrderDao {

}
