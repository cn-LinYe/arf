package com.arf.silverleopard.dao.impl;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.silverleopard.dao.SalesReceiptsDao;
import com.arf.silverleopard.entity.SalesReceipts;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository("salesReceiptsDao")
public class SalesReceiptsDaoImpl extends BaseDaoImpl<SalesReceipts, Long> implements SalesReceiptsDao{

	@Override
	public int findCount(String sn) {
		if(StringUtils.isBlank(sn)) {
			return 1;
		}
		StringBuffer countHql = new StringBuffer("select ifnull(count(1),0) as COUNT from p_sales_receipts a");
		countHql.append(" where 1=1 and a.sn=:sn");
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		queryCount.setParameter("sn", sn);
		String o = queryCount.getSingleResult().toString();
		if(StringUtils.isBlank(o)) {
			return 1;
		}else {
			return Integer.parseInt(o);
		}
	}

}
