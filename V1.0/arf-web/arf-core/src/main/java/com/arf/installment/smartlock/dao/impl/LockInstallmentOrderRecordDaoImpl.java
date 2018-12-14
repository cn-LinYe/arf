package com.arf.installment.smartlock.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.installment.smartlock.dao.ILockInstallmentOrderRecordDao;
import com.arf.installment.smartlock.dto.LockInstallmentOrderRecordDto;
import com.arf.installment.smartlock.entity.LockInstallmentOrderRecord;

@Repository("lockInstallmentOrderRecordDao")
public class LockInstallmentOrderRecordDaoImpl extends BaseDaoImpl<LockInstallmentOrderRecord, Long> implements ILockInstallmentOrderRecordDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<LockInstallmentOrderRecordDto> findByUserName(String userName){
		List<LockInstallmentOrderRecordDto> list = new ArrayList<LockInstallmentOrderRecordDto>();
		String sql = "select a.id, a.create_date as createDate, a.modify_date as modifyDate, a.installment_periods as installmentPeriods,"
				+ " a.user_phone as userPhone, a.real_name as realName, a.city_no as cityNo, a.full_city_name as fullCityName, a.address, a.status,"
				+ " a.funds_clear as fundsClear, a.order_no as orderNo, a.buy_date as buyDate, a.pay_type as payType, a.apply_refund_date as applyRefundDate,"
				+ " a.refund_finish_date as refundFinishDate,"
				+ " b.type_num as typeNum, b.optional_periods as optionalPeriods, b.installation_fee as installationFee, b.market_price as marketPrice,"
				+ " b.installment_price as installmentPrice, b.model, b.first_funds as firstFunds, b.pre_pay_point as prePayPoint, b.interest,"
				+ " b.type_name as typeName, b.type, b.overdue_fine_day as overdueFineDay "
				+ " from lock_installment_order_record a "
				+ " left join i_installment_type b on a.type_num = b.type_num"
				+ " where a.user_name = :userName and a.status <> 0 "
				+ " order by a.create_date ASC ";
		Query query = super.entityManager.createNativeQuery(sql);
		query.setParameter("userName", userName);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> rows = query.getResultList();
		if(CollectionUtils.isNotEmpty(rows)){
			list = JSON.parseArray(JSON.toJSONString(rows), LockInstallmentOrderRecordDto.class);
		}
		return list;
		
//		query.setParameter("userName", userName);
//		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(LockInstallmentOrderRecordDto.class));
//		return query.getResultList();
	}
}
