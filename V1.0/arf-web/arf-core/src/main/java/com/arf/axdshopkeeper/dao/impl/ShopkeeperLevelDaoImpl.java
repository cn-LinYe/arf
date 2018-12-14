package com.arf.axdshopkeeper.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.axdshopkeeper.dao.IShopkeeperLevelDao;
import com.arf.axdshopkeeper.entity.ShopkeeperLevel;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("shopkeeperLevelDao")
public class ShopkeeperLevelDaoImpl extends BaseDaoImpl<ShopkeeperLevel, Long> implements IShopkeeperLevelDao{

}
