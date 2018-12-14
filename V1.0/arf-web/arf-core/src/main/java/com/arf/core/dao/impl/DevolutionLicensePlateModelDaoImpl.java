package com.arf.core.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.DevolutionLicensePlateModelDao;
import com.arf.core.entity.DevolutionLicensePlateModel;

import ch.qos.logback.core.helpers.Transform;

/**
 * Dao - 授权表
 * 
 * @author arf
 * @version 4.0
 */
@Repository("devolutionLicensePlateModelDaoImpl")
public class DevolutionLicensePlateModelDaoImpl extends BaseDaoImpl<DevolutionLicensePlateModel, Long> implements DevolutionLicensePlateModelDao {

	@Override
	public DevolutionLicensePlateModel selectByLicesenUser_name(String user_name, String license_plate) {
		String jpql = "select devolutionLicensePlate from DevolutionLicensePlateModel devolutionLicensePlate where devolutionLicensePlate.user_name = :user_name and devolutionLicensePlate.license_plate_number = :license_plate";
		List<DevolutionLicensePlateModel> list=entityManager.createQuery(jpql, DevolutionLicensePlateModel.class).setParameter("user_name", user_name).setParameter("license_plate", license_plate).getResultList(); 
		return list.isEmpty()?null:list.get(0);
	}
	
	@Override
	public DevolutionLicensePlateModel selectByLicenseAndUserName(String user_name, String license_plate) {
		String jpql = "select devolutionLicensePlate from DevolutionLicensePlateModel devolutionLicensePlate where devolutionLicensePlate.user_name = :user_name and devolutionLicensePlate.license_plate_number = :license_plate order by time_end desc";
		List<DevolutionLicensePlateModel> list = entityManager.createQuery(jpql, DevolutionLicensePlateModel.class).setParameter("user_name", user_name).setParameter("license_plate", license_plate).getResultList(); 
		return list.isEmpty()?null:list.get(0);
	}
	
	public List<DevolutionLicensePlateModel> selectDevolution(String user_name) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DevolutionLicensePlateModel> criteriaQuery = criteriaBuilder.createQuery(DevolutionLicensePlateModel.class);
		Root<DevolutionLicensePlateModel> root = criteriaQuery.from(DevolutionLicensePlateModel.class);
		criteriaQuery.select(root);		
		criteriaQuery.where(criteriaBuilder.equal(root.get("user_name"), user_name));
		return super.findList(criteriaQuery, null, null, null, null);
	}


	@Override
	public List<DevolutionLicensePlateModel> CheckUser_name(String user_nane, Long endTime) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DevolutionLicensePlateModel> criteriaQuery = criteriaBuilder.createQuery(DevolutionLicensePlateModel.class);
		Root<DevolutionLicensePlateModel> root = criteriaQuery.from(DevolutionLicensePlateModel.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("user_name"), user_nane));
		if (null == endTime || endTime <= 0l){
		}else{
		    restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.ge(root.<Long>get("timeEnd"),endTime));
		}
		
		criteriaQuery.where(restrictions);
		return super.entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public List<DevolutionLicensePlateModel> selectByLicense(String license_plate_number) {
		Date date = new Date();
		String jpql  = "select devolutionLicensePlate from DevolutionLicensePlateModel devolutionLicensePlate where devolutionLicensePlate.timeEnd >= :now and devolutionLicensePlate.license_plate_number = :license_plate_number";
		return entityManager.createQuery(jpql, DevolutionLicensePlateModel.class).setParameter("now", date.getTime()).setParameter("license_plate_number", license_plate_number).getResultList();
	}
	
	@Override
	public DevolutionLicensePlateModel selectByLicenseUserName(String user_name,String license_plate_number){
		Date date = new Date();
		String jpql  = "select devolutionLicensePlate from DevolutionLicensePlateModel devolutionLicensePlate where devolutionLicensePlate.timeEnd >= :now and devolutionLicensePlate.license_plate_number = :license_plate_number "
				+ " and devolutionLicensePlate.user_name = :user_name";
		List<DevolutionLicensePlateModel> list = entityManager.createQuery(jpql, DevolutionLicensePlateModel.class).setParameter("now", date.getTime()).setParameter("license_plate_number", license_plate_number)
				.setParameter("user_name", user_name).getResultList();
		return list.isEmpty()?null:list.get(0);
	}
	
	@Override
	public List<DevolutionLicensePlateModel> selectDevolutionByLicense(String license_plate_number) {
		String jpql = "select devolutionLicensePlate from DevolutionLicensePlateModel devolutionLicensePlate where devolutionLicensePlate.license_plate_number =:license_plate_number";
		return entityManager.createQuery(jpql, DevolutionLicensePlateModel.class).setParameter("license_plate_number", license_plate_number).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> findParkRateByUserName(String userName) {
		StringBuffer sb =new StringBuffer(" SELECT ");
		sb.append(" d.license_plate_number as licensePlateNumber,d.license_plate_id as licensePlateId,d.user_name as userName,d.start_time devoluteStartTime,");
		sb.append(" d.time_end devoluteEndTime,GROUP_CONCAT(p.community_number) communityNumber,");
		sb.append(" CASE WHEN l.lock_numbers is null AND d.license_plate_number is not null then 1 else l.lock_numbers END as lockNumber  ");
		sb.append(" FROM devolution_license_plate d");
		sb.append(" LEFT JOIN license_plate l ON d.license_plate_number =l.license_plate_number");
		sb.append(" INNER JOIN park_rate p ON p.license_plate_number=d.license_plate_number");
		sb.append(" AND d.user_name=:userName ");
		sb.append(" and d.time_end/1000>UNIX_TIMESTAMP(NOW())");
		sb.append(" GROUP BY p.license_plate_number");
		 
		Query query = this.entityManager.createNativeQuery(sb.toString());
		query.setParameter("userName", userName);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}

	@Override
	public List<Map<String,Object>> findByUserNameLeftjoinLicenseplate(
			String userName) {
		StringBuffer sb =new StringBuffer("select ");
		sb.append(" dlp.user_name, dlp.start_time devoluteStartTime, dlp.time_end devoluteEndTime, ");
		sb.append(" lp.license_plate_id licensePlateId, lp.license_plate_number license, ");
		sb.append(" lp.lock_numbers lockNumbers,lp.engine_numbers engineNumbers, lp.his_no frameNumebr, ");
		sb.append(" lp.verification_time verificationTime, lp.is_verification isVerification,lp.user_name devoluteUserName ");
		sb.append(" from devolution_license_plate dlp ");
		sb.append(" left join license_plate lp on lp.license_plate_number = dlp.license_plate_number ");
		sb.append(" where dlp.user_name = :userName ");
		sb.append(" and (dlp.time_end / 1000) > UNIX_TIMESTAMP() ");
		Query query = this.entityManager.createNativeQuery(sb.toString());
		query.setParameter("userName", userName);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}
}
