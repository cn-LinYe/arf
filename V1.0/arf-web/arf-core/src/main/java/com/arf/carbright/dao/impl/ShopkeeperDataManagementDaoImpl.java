package com.arf.carbright.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.ShopkeeperDataManagementDao;
import com.arf.carbright.entity.ShopkeeperDataManagement;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("shopkeeperDataManagementDao")
public class ShopkeeperDataManagementDaoImpl extends BaseDaoImpl<ShopkeeperDataManagement, Long> implements ShopkeeperDataManagementDao{

	@Override
	public ShopkeeperDataManagement findByPhone(String phone) {
		if (StringUtils.isBlank(phone)) {
			return null;
		}
		String hql = "from ShopkeeperDataManagement sdm where sdm.phone =:phone";
		List<ShopkeeperDataManagement> shopkeeperDataManagements= this.entityManager.createQuery(hql,ShopkeeperDataManagement.class)
				.setParameter("phone", phone)
				.getResultList();
		return CollectionUtils.isEmpty(shopkeeperDataManagements)?null:shopkeeperDataManagements.get(0);
	}

}
