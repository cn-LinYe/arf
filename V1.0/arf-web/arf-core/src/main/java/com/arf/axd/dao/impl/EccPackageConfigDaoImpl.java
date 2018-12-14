package com.arf.axd.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.axd.dao.IEccPackageConfigDao;
import com.arf.axd.entity.EccPackageConfig;
import com.arf.axd.entity.EccPackageConfig.Status;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("eccPackageConfigDaoImpl")
public class EccPackageConfigDaoImpl extends BaseDaoImpl<EccPackageConfig,Long> implements IEccPackageConfigDao {

	@Override
	public List<Map<String,Object>> findByStatus(Status status,Date date) {
		StringBuffer hql = new StringBuffer("select");
		hql.append(" a.id,");
		hql.append(" a.gift_amount giftAmount ,a.gift_name giftName ,a.package_type packageType,");
		hql.append(" a.package_amount packageAmount,");
		hql.append(" a.package_name packageName,");
		hql.append(" a.package_index packageIndex,");
		hql.append(" a.vouchers_num vouchersNum,");
		hql.append(" a.vouchers_count vouchersCount,");
		hql.append(" a.vouchers_total_amount vouchersTotalAmount,");
		hql.append(" a.e_account eAccount,");
		hql.append(" a.cash_num cashNum,");
		hql.append(" a.cash_count cashCount,");
		hql.append(" a.cash_total_amount cashTotalAmount,");
		hql.append(" a.package_description packageDescription,");
		hql.append(" a.status,");
		hql.append(" a.start_released_date startReleasedDate,");
		hql.append(" a.end_released_date endReleasedDate,");
		hql.append(" a.url,");
		hql.append(" a.background_type backgroundType,");
		hql.append(" b.scope,");
		hql.append(" b.use_scope useScope,");
		hql.append(" b.use_rules useRules,");
		hql.append(" b.vouchers_name vouchersName,");
		hql.append(" c.vouchers_name cashName");
		hql.append(" from p_ecc_package_config a");
		hql.append(" LEFT JOIN p_axd_vouchers b ON a.vouchers_num = b.vouchers_num");
		hql.append(" LEFT JOIN p_axd_vouchers c ON c.vouchers_num = a.cash_num");
		hql.append(" where status = :status and start_released_date <= :date and end_released_date >= :date");
		Query query = entityManager.createNativeQuery(hql.toString());
		query.setParameter("status", (byte)status.ordinal());
		query.setParameter("date", date);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> rows = query.getResultList();
		return rows;
	}

}