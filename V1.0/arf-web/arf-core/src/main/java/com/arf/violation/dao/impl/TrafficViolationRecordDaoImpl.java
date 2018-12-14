package com.arf.violation.dao.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.violation.dao.ITrafficViolationRecordDao;
import com.arf.violation.entity.TrafficViolationRecord;

@Repository("trafficViolationRecordDaoImpl")
public class TrafficViolationRecordDaoImpl extends BaseDaoImpl<TrafficViolationRecord, Long> implements ITrafficViolationRecordDao{

	@Override
	public TrafficViolationRecord findByUniqueCode(String uniqueCode) {

		if (StringUtils.isBlank(uniqueCode)) {
			return null;
		}

		StringBuffer sql =new StringBuffer(" from TrafficViolationRecord where uniqueCode=:uniqueCode ");
		TypedQuery<TrafficViolationRecord> query = entityManager.createQuery(sql.toString(),TrafficViolationRecord.class);
		query.setParameter("uniqueCode", uniqueCode);
		List<TrafficViolationRecord> list ;
		try {
			 list = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		return list.isEmpty()?null:list.get(0);
	}

	@Override
	public List<TrafficViolationRecord> findByUniqueCode(String[] uniqueCode,TrafficViolationRecord.Status status) {
		if (uniqueCode.length<=0||status==null) {
			return null;
		}
		StringBuffer hql = new StringBuffer("select t from TrafficViolationRecord t where t.uniqueCode in (:uniqueCode)");
		hql.append("  and t.status=:status  ");
		TypedQuery<TrafficViolationRecord> query =entityManager.createQuery(hql.toString(), TrafficViolationRecord.class)
				.setParameter("uniqueCode", Arrays.asList(uniqueCode))
				.setParameter("status", Byte.parseByte(status.ordinal()+""));
		return query.getResultList();
	}

	@Override
	public PageResult<TrafficViolationRecord> findByLicense(String license,Integer pageNo,Integer pageSize) {
		if (StringUtils.isBlank(license)) {
			return null;
		}
		StringBuffer sql =new StringBuffer("from TrafficViolationRecord where license =:license");
		StringBuffer sqlCount =new StringBuffer("select count(1) from v_traffic_violation_record where license =:license");
		TypedQuery<TrafficViolationRecord> query =entityManager.createQuery(sql.toString(),TrafficViolationRecord.class);
		Query queryCount =entityManager.createNativeQuery(sqlCount.toString());
		query.setParameter("license", license);
		if (pageNo!=null&&pageSize!=null) {
			query.setMaxResults(pageNo);
			query.setFirstResult((pageNo-1)*pageSize);
		}
		List<TrafficViolationRecord> list=query.getResultList();
		queryCount.setParameter("license", license);
		int count=0;
		try {
		count=((BigInteger)queryCount.getSingleResult()).intValue();		
		} catch (Exception e) {
		}
		PageResult<TrafficViolationRecord> result = new PageResult<TrafficViolationRecord>(list,count);
		return result;
	}

	@Override
	public TrafficViolationRecord findBySecondaryUniqueCode(String secondaryUniqueCode) {

		if (StringUtils.isBlank(secondaryUniqueCode)) {
			return null;
		}

		StringBuffer sql =new StringBuffer(" from TrafficViolationRecord where secondaryUniqueCode=:secondaryUniqueCode ");
		TypedQuery<TrafficViolationRecord> query = entityManager.createQuery(sql.toString(),TrafficViolationRecord.class);
		query.setParameter("secondaryUniqueCode", secondaryUniqueCode);
		List<TrafficViolationRecord> list ;
		try {
			 list = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		return list.isEmpty()?null:list.get(0);
	}

	@Override
	public List<TrafficViolationRecord> findBysecondaryUniqueCodes(List<String> list) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		StringBuffer hql = new StringBuffer("select t from TrafficViolationRecord t where t.secondaryUniqueCode in (:secondaryUniqueCode)");
		TypedQuery<TrafficViolationRecord> query =entityManager.createQuery(hql.toString(),TrafficViolationRecord.class);
		query.setParameter("secondaryUniqueCode", list);
		return query.getResultList();
	}

	@Override
	public void saveAllViolationRecord(JSONArray jsonArray,String license) {
				SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        StringBuffer sql=new StringBuffer("INSERT INTO v_traffic_violation_record  ") ;
		        sql.append("(create_date,");
		        sql.append("modify_date,");
		        sql.append("version,");
		        sql.append("time,");
		        sql.append("location,");
		        sql.append("reason,");
		        sql.append("status,");
		        sql.append("fine_amount,");
		        sql.append("department,");
		        sql.append("fine_marks,");
		        sql.append("code,");
		        sql.append("archive,");
		        sql.append("telephone,");
		        sql.append("excutelocation,");
		        sql.append("excutedepartment,");
		        sql.append("category,");
		        sql.append("punishmentaccording,");
		        sql.append("illegalentry,");
		        sql.append("locationid,");
		        sql.append("location_name,");
		        sql.append("data_source_id,");
		        sql.append("record_type,");
		        sql.append("can_process,");
		        sql.append("unique_code,");
		        sql.append("secondary_unique_code,");
		        sql.append("degree_poundage,");
		        sql.append("canprocess_msg,");
		        sql.append("query_date,");
		        sql.append("license");
		        sql.append(")values");
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					sql.append("('");
					sql.append(sim.format(new Date())+"','");
					sql.append(sim.format(new Date())+"','");
					sql.append(1+"','");
					sql.append(jsonObject.getString("Time")+"','");
					sql.append(jsonObject.getString("Location")+"','");
					sql.append(jsonObject.getString("Reason")+"','");
					sql.append(jsonObject.getByte("status")+"','");
					sql.append(jsonObject.getBigDecimal("count")+"','");
					sql.append(jsonObject.getString("department")+"','");
					sql.append(jsonObject.getInteger("Degree")+"','");
					sql.append(jsonObject.getString("Code")+"','");
					sql.append(jsonObject.getString("Archive")+"','");
					sql.append(jsonObject.getString("Telephone")+"','");
					sql.append(jsonObject.getString("Excutelocation")+"','");
					sql.append(jsonObject.getString("Excutedepartment")+"','");
					sql.append(jsonObject.getString("Category")+"','");
					sql.append(jsonObject.getString("Punishmentaccording")+"','");
					sql.append(jsonObject.getString("Illegalentry")+"','");
					sql.append(jsonObject.getString("Locationid")+"','");
					sql.append(jsonObject.getString("LocationName")+"','");
					sql.append(jsonObject.getString("DataSourceID")+"','");
					sql.append(jsonObject.getString("RecordType")+"','");
					sql.append(jsonObject.getString("CanProcess")+"','");
					sql.append(jsonObject.getString("UniqueCode")+"','");
					sql.append(jsonObject.getString("SecondaryUniqueCode")+"','");
					sql.append(jsonObject.getString("DegreePoundage")+"','");
					sql.append(jsonObject.getString("CanprocessMsg")+"','");
					sql.append(sim.format(new Date())+"','");
					sql.append(license);
					sql.append("')");
					sql.append(",");
				}
				String sqls =sql.toString().substring(0, sql.toString().length()-1);
				 entityManager.createNativeQuery(sqls).executeUpdate();
				 
	}

	@Override
	public List<TrafficViolationRecord> findByUniqueCode(List<String> uniqueCode) {
		if (CollectionUtils.isEmpty(uniqueCode)) {
			return null;
		}
		StringBuffer hql = new StringBuffer("select t from TrafficViolationRecord t where t.uniqueCode in (:uniqueCode)");
		TypedQuery<TrafficViolationRecord> query =entityManager.createQuery(hql.toString(), TrafficViolationRecord.class)
				.setParameter("uniqueCode", uniqueCode);
		return query.getResultList();
	}

	@Override
	public List<TrafficViolationRecord> findByLiscenseTime(String license, List<Date> list) {
		if (CollectionUtils.isEmpty(list)||StringUtils.isBlank(license)) {
			return null;
		}
		StringBuffer hql = new StringBuffer(" from TrafficViolationRecord  where time in (:list) and license=:license");
		TypedQuery<TrafficViolationRecord> query =entityManager.createQuery(hql.toString(), TrafficViolationRecord.class);
		query.setParameter("license", license);
		query.setParameter("list", list);
		return query.getResultList();
	}

}












