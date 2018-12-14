package com.arf.laundry.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.laundry.dao.ILaundryOrderItemsDao;
import com.arf.laundry.entity.LaundryOrder;
import com.arf.laundry.entity.LaundryOrderItems;

@Repository("laundryOrderItemsDaoImpl")
public class LaundryOrderItemsDaoImpl extends BaseDaoImpl<LaundryOrderItems, Long> implements ILaundryOrderItemsDao {

}
