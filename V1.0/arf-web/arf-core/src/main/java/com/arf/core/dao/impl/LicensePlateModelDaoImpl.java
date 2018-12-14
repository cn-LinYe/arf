package com.arf.core.dao.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.core.HQLResultConvert;
import com.arf.core.dao.LicensePlateModelDao;
import com.arf.core.entity.LicensePlateModel;

/**
 * Dao - 车牌表
 * 
 * @author arf
 * @version 4.0
 */
@Repository("licensePlateModelDaoImpl")
public class LicensePlateModelDaoImpl extends BaseDaoImpl<LicensePlateModel, Long> implements LicensePlateModelDao {

	@Override
	public List<LicensePlateModel> CheckLicensePlate(String License) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<LicensePlateModel> criteriaQuery = criteriaBuilder.createQuery(LicensePlateModel.class);
		Root<LicensePlateModel> root = criteriaQuery.from(LicensePlateModel.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("license_plate_number"), License));
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public LicensePlateModel selectByLicesenUser_name(String user_name, String license_plate) {
		String jpql = "select licensePlate from LicensePlateModel licensePlate where licensePlate.user_name = :user_name and licensePlate.license_plate_number = :license_plate";
		List<LicensePlateModel> list=entityManager.createQuery(jpql, LicensePlateModel.class).setParameter("user_name", user_name).setParameter("license_plate", license_plate).getResultList();
		return list.isEmpty()?null:list.get(0);
	}
	

	@Override
	public List<LicensePlateModel> CheckLicense_plate_id() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<LicensePlateModel> criteriaQuery = criteriaBuilder.createQuery(LicensePlateModel.class);
		Root<LicensePlateModel> root = criteriaQuery.from(LicensePlateModel.class);
		criteriaQuery.select(root);		
		return super.findList(criteriaQuery, null, null, null, null);
	}
	
	@Override
	public String findMaxLicenseId() {
		
		String jpql = " select license_plate_id from license_plate  order by license_plate_id desc ";
		Query query = entityManager.createNativeQuery(jpql);
		query.setFirstResult(0);
		query.setMaxResults(1);
		String licenseId=null;
		try {
			 licenseId=(String)query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		return licenseId;
	}

	
	@Override
	public LicensePlateModel findByLicenseId(String licensePlateId) {
		String jpql = " from LicensePlateModel licensePlate  where licensePlate.license_plate_id = :licensePlateId ";
		List<LicensePlateModel> list=entityManager.createQuery(jpql, LicensePlateModel.class)
				.setParameter("licensePlateId", licensePlateId).getResultList();
		return list.isEmpty()?null:list.get(0);
	}
	
//	public List<LicensePlateModel> CheckUser_name(String user_name){
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//		CriteriaQuery<LicensePlateModel> criteriaQuery = criteriaBuilder.createQuery(LicensePlateModel.class);
//		Root<LicensePlateModel> root = criteriaQuery.from(LicensePlateModel.class);
//		criteriaQuery.select(root);		
//		criteriaQuery.where(criteriaBuilder.equal(root.get("user_name"), user_name));
//		return super.findList(criteriaQuery, null, null, null, null);
//	}
	@Override
	public List<LicensePlateModel> CheckUser_name(String user_name){
        String hql="select licensePlate from LicensePlateModel licensePlate where licensePlate.user_name=:user_name";
        return entityManager.createQuery(hql, LicensePlateModel.class).setParameter("user_name",user_name).getResultList();
    }

	@Override
	public LicensePlateModel selectByCarCode(String licensePlate, String carCode) {
		String jpql = "select licensePlate from LicensePlateModel licensePlate where  licensePlate.license_plate_number = :licensePlate and licensePlate.his_no = :carCode";
		List<LicensePlateModel> list=entityManager.createQuery(jpql, LicensePlateModel.class).setParameter("license_plate", licensePlate).setParameter("his_no", carCode).getResultList();
		return list.isEmpty()?null:list.get(0);
	}
	
	/**
     * 获取vip用户所有已经认证的车牌
     * @return
     */
	@Override
	public List<LicensePlateModel> getVipLicensePlate(){
        String sql="select lp.license_plate_number,lp.his_no,m.username from xx_member m "+
                "inner join license_plate lp on m.username=lp.user_name and lp.is_verification=1 "+
                "where m.vip=1";
        
        return this.findListBySQL(sql, null,new HQLResultConvert<LicensePlateModel>() {
            @Override
            public List<LicensePlateModel> convert(List list) {
                List<LicensePlateModel> plist=new ArrayList<LicensePlateModel>();
                for(int i=0;i<list.size();i++){
                    Object[] obj=(Object[])list.get(i);
                    LicensePlateModel lpm=new LicensePlateModel();
                    lpm.setLicense_plate_number(obj[0]==null?"":obj[0].toString());
                    lpm.setHis_no(obj[1]==null?"":obj[1].toString());
                    lpm.setUser_name(obj[2]==null?"":obj[2].toString());
                    plist.add(lpm);
                }
                return plist;
            }
        });
    }
    
    /**
     * 获取用户所有绑定的车牌
     * @return
     */
    @Override
	public List<LicensePlateModel> getVipLicensePlate(String userName){
        String sql="select lp.license_plate_number,lp.his_no,m.username from xx_member m "+
                "inner join license_plate lp on m.username=lp.user_name "+
                "where m.username='"+userName+"'";
        
        return this.findListBySQL(sql, null,new HQLResultConvert<LicensePlateModel>() {
            @Override
            public List<LicensePlateModel> convert(List list) {
                List<LicensePlateModel> plist=new ArrayList<LicensePlateModel>();
                for(int i=0;i<list.size();i++){
                    Object[] obj=(Object[])list.get(i);
                    LicensePlateModel lpm=new LicensePlateModel();
                    lpm.setLicense_plate_number(obj[0]==null?"":obj[0].toString());
                    lpm.setHis_no(obj[1]==null?"":obj[1].toString());
                    lpm.setUser_name(obj[2]==null?"":obj[2].toString());
                    plist.add(lpm);
                }
                return plist;
            }
        });
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<LicensePlateModel> findNotMonthlyLicense(String userName) {
		String sql = "select t1.* from license_plate t1 where t1.user_name= :userName " +
					" and t1.license_plate_number not in( " +
					" 	select t2.license_plate_number from park_rate t2 where t2.user_name=t1.user_name " +
					" )";
		return this.entityManager.createNativeQuery(sql, LicensePlateModel.class).setParameter("userName", userName).getResultList();
	}

	@Override
	public List<LicensePlateModel> findByLicenseAndNotNulluser(String license) {
		String jpql = "select licensePlate from LicensePlateModel licensePlate where licensePlate.license_plate_number=:license and user_name != 'nullUser'";
		TypedQuery<LicensePlateModel> query = this.entityManager.createQuery(jpql, LicensePlateModel.class);
		query.setParameter("license", license);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> findParkRateByUserName(String userName) {
		StringBuffer sb = new StringBuffer("SELECT");
		sb.append("  l.*,GROUP_CONCAT(p.community_number) as communityNumber FROM license_plate l ");
		sb.append(" INNER JOIN park_rate p ON p.license_plate_number =l.license_plate_number ");
		sb.append(" AND l.user_name=:userName");
		sb.append(" GROUP BY l.license_plate_number");
		Query query = this.entityManager.createNativeQuery(sb.toString());
		query.setParameter("userName", userName);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}

	@Override
	public List<Map<String, Object>> findByUserNameAboutDevolution(
			String userName,String licensePlateNumber) {
		StringBuffer sb =new StringBuffer("select ");
		sb.append(" lp.license_plate_id licensePlateId, lp.license_plate_number license, lp.lock_numbers lockNumbers, ");
		sb.append(" lp.engine_numbers engineNumbers, lp.his_no frameNumebr,lp.verification_time verificationTime, ");
		sb.append(" lp.is_verification isVerification, lp.user_name devoluteUserName, ");
		sb.append(" CASE  WHEN ");
		sb.append(" (SELECT COUNT(1) from devolution_license_plate dlp where dlp.license_plate_number = lp.license_plate_number and (dlp.time_end / 1000) > UNIX_TIMESTAMP()) THEN 1 ");
		sb.append(" ELSE 0 END as isDevoluted ");
		sb.append(" from license_plate lp ");
		sb.append(" where 1=1 ");
		if(StringUtils.isNotBlank(userName)){
			sb.append(" and lp.user_name = :userName ");
		}
		if(StringUtils.isNotBlank(licensePlateNumber)){
			sb.append(" and lp.license_plate_number = :licensePlateNumber ");
		}
		Query query = this.entityManager.createNativeQuery(sb.toString());
		if(StringUtils.isNotBlank(userName)){
			query.setParameter("userName", userName);
		}
		if(StringUtils.isNotBlank(licensePlateNumber)){
			query.setParameter("licensePlateNumber", licensePlateNumber);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}

	@Override
	public List<Map<String, Object>> findAllParkRate() {
		StringBuffer sb = new StringBuffer("SELECT");
		sb.append(" l.license_plate_id licensePlateId, l.license_plate_number license, l.lock_numbers lockNumbers, ");
		sb.append(" l.engine_numbers engineNumbers, l.his_no frameNumebr,l.verification_time verificationTime, ");
		sb.append(" l.is_verification isVerification, l.user_name userName, ");
		sb.append("  p.community_number as communityNumber FROM license_plate l ");
		sb.append(" INNER JOIN park_rate p ON p.license_plate_number =l.license_plate_number ");
		sb.append(" AND l.user_name !=:userName");
		Query query = this.entityManager.createNativeQuery(sb.toString());
		query.setParameter("userName", "nullUser");
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}


	
}
