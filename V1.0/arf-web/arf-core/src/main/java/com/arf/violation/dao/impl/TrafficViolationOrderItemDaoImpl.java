package com.arf.violation.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.violation.dao.ITrafficViolationOrderItemDao;
import com.arf.violation.entity.TrafficViolationOrderItem;

@Repository("trafficViolationOrderItemDaoImpl")
public class TrafficViolationOrderItemDaoImpl extends BaseDaoImpl<TrafficViolationOrderItem, Long>
		implements ITrafficViolationOrderItemDao {

	@Override
	public TrafficViolationOrderItem findByUniqueCode(String uniqueCode) {

		StringBuffer sql = new StringBuffer(" from TrafficViolationOrderItem where uniqueCode=:uniqueCode ");
		TypedQuery<TrafficViolationOrderItem> query = entityManager.createQuery(sql.toString(),
				TrafficViolationOrderItem.class);
		query.setParameter("uniqueCode", uniqueCode);
		List<TrafficViolationOrderItem> list;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public List<Map<String, Object>> findByOrderNo(String orderNo) {
		StringBuffer sql = new StringBuffer("SELECT v.time,v.location,v.reason,v.fine_amount fineAmount,v.department department,v.fine_marks fineMarks,v.code code,v.archive archive,v.telephone telephone,");
		sql.append("  v.excutelocation excutelocation,v.excutedepartment excutedepartment,");
		sql.append("  v.punishmentaccording punishmentaccording,v.illegalentry illegalentry,v.locationid locationid,v.location_name locationName,");
		sql.append("  v.can_process canProcess,v.unique_code uniqueCode,v.secondary_unique_code secondaryUniqueCode,v.canprocess_msg canprocessMsg,");
		sql.append("  v.license license,v.img_url imgUrl,v.service_amount serviceAmount,v.category category");
		sql.append("  FROM v_traffic_violation_order_item v");
		sql.append("  WHERE 1=1");
		sql.append("  AND v.order_no=:orderNo");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("orderNo", orderNo);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> map = query.getResultList();
		return map;
	}

	@Override
	public int findByLicense(String license) {

		StringBuffer sql = new StringBuffer("SELECT COUNT(1) ");
		sql.append("  FROM v_traffic_violation_order o");
		sql.append("  LEFT JOIN v_traffic_violation_order_item v  ON v.order_no=o.order_no");
		sql.append("  WHERE v.license =:license AND o.status=2 GROUP BY v.order_no");
		Query queryConut =entityManager.createNativeQuery(sql.toString());
		queryConut.setParameter("license", license);
		int count =0;
		try {
			count =((BigInteger)queryConut.getSingleResult()).intValue();			
		} catch (Exception e) {
		}
		return count;
	}

	@Override
	public void updateOrderStatus(String orderNo,Integer status){

		StringBuffer sql = new StringBuffer("UPDATE TrafficViolationOrderItem SET status=:status where orderNo=:orderNo");
		entityManager.createQuery(sql.toString()).setParameter("status", status).setParameter("orderNo", orderNo).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findByUniqueCodes(List<String> uniqueCodes) {
		if (CollectionUtils.isEmpty(uniqueCodes)) {
			return null;
		}
		StringBuffer hql = new StringBuffer("select t.uniqueCode from TrafficViolationOrderItem t where t.uniqueCode in (:uniqueCode) and status="+TrafficViolationOrderItem.Status.Paid.ordinal() +"or status="+TrafficViolationOrderItem.Status.Confirm.ordinal()+" or status="+TrafficViolationOrderItem.Status.Finish.ordinal());
		Query query =entityManager.createQuery(hql.toString());
		query.setParameter("uniqueCode", uniqueCodes);
		return query.getResultList();
	}

	@Override
	public List<Date> findByTimeLicense(List<Date> time, String license) {
		if (CollectionUtils.isEmpty(time)||license==null) {
			return null;
		}
		StringBuffer hql = new StringBuffer("select t.time from TrafficViolationOrderItem t where t.time in (:time) and t.license=:license and status="+TrafficViolationOrderItem.Status.Paid.ordinal() +"or status="+TrafficViolationOrderItem.Status.Confirm.ordinal()+" or status="+TrafficViolationOrderItem.Status.Finish.ordinal());
		Query query =entityManager.createQuery(hql.toString());
		query.setParameter("time", time);
		query.setParameter("license", license);
		return query.getResultList();
	}
}

















