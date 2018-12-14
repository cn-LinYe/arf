package com.arf.silverleopard.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.silverleopard.dao.StoreDao;
import com.arf.silverleopard.entity.Store;

@Repository("storeDao")
public class StoreDaoImpl extends BaseDaoImpl<Store, Long> implements StoreDao{


}
