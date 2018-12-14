package com.arf.gift.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gift.dao.IGiftVoucherExchangeItemDao;
import com.arf.gift.entity.GiftVoucherExchangeItem;

@Repository("giftVoucherExchangeItemDaoImpl")
public class GiftVoucherExchangeItemDaoImpl extends BaseDaoImpl<GiftVoucherExchangeItem, Long> implements IGiftVoucherExchangeItemDao{

	@Override
	public List<Map<String,Object>> findByVoucherNumber(String voucherNumber) {
		StringBuffer sb =new StringBuffer(" SELECT g.voucher_number as voucherNumber,g.exchange_count exchangeCount,g.user_name userName,");
		sb.append(" g.discount_price discountPrice,g.market_price marketPrice,g.sku,g.gift_name giftName");
		sb.append(" FROM gift_voucher_exchange_item g WHERE 1=1 AND g.voucher_number=:voucherNumber");
		Query query =entityManager.createNativeQuery(sb.toString());
		query.setParameter("voucherNumber", voucherNumber);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = query.getResultList();
		return rows;
	}
}
