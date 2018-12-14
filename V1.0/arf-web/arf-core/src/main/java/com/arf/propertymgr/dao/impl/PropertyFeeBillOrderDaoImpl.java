package com.arf.propertymgr.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.propertymgr.dao.IPropertyFeeBillOrderDao;
import com.arf.propertymgr.entity.PropertyFeeBillOrder;

@Repository("propertyFeeBillOrderDaoImpl")
public class PropertyFeeBillOrderDaoImpl extends BaseDaoImpl<PropertyFeeBillOrder, Long>
		implements IPropertyFeeBillOrderDao {

}
