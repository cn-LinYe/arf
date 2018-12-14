package com.arf.axdshopkeeper.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.axdshopkeeper.dao.IShopkeeperApplyDao;
import com.arf.axdshopkeeper.entity.ShopkeeperApply;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("shopkeeperApplyDaoImpl")
public class ShopkeeperApplyDaoImpl extends BaseDaoImpl<ShopkeeperApply, Long>
		implements IShopkeeperApplyDao {

}
