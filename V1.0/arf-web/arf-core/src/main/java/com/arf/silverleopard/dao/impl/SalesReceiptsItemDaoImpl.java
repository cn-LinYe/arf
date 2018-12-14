package com.arf.silverleopard.dao.impl;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.silverleopard.dao.SalesReceiptsItemDao;
import com.arf.silverleopard.entity.SalesReceiptsItem;

@Repository("salesReceiptsItemDao")
public class SalesReceiptsItemDaoImpl extends BaseDaoImpl<SalesReceiptsItem, Long> implements SalesReceiptsItemDao{

	@Override
	public int findCount(String productUid) {
		if(StringUtils.isBlank(productUid)) {
			return 1;
		}
		StringBuffer countHql = new StringBuffer("select ifnull(count(1),0) as COUNT from p_sales_receipts_item a");
		countHql.append(" where 1=1 and a.product_uid=:productUid");
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		queryCount.setParameter("productUid", productUid);
		String o = queryCount.getSingleResult().toString();
		if(StringUtils.isBlank(o)) {
			return 0;
		}else {
			return Integer.parseInt(o);
		}
	}

}
