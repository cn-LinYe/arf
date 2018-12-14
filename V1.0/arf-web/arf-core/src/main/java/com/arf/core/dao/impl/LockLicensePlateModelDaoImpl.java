package com.arf.core.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.LockLicensePlateModelDao;
import com.arf.core.entity.LockLicensePlateModel;

/**
 * Dao - 车牌控制表(用来锁定车牌)
 * 
 * @author arf
 * @version 4.0
 */
@Repository("lockLicensePlateModelDaoImpl")
public class LockLicensePlateModelDaoImpl extends BaseDaoImpl<LockLicensePlateModel, Long> implements LockLicensePlateModelDao {

	@Override
	public LockLicensePlateModel selectLicense(String license) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<LockLicensePlateModel> criteriaQuery = criteriaBuilder.createQuery(LockLicensePlateModel.class);
		Root<LockLicensePlateModel> root = criteriaQuery.from(LockLicensePlateModel.class);
		criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("license_plate_number"), license));	
//		String jpql="select lockLicense from LockLicensePlateModel lockLicense where lockLicense.license_plate_number =:license_plate_number ";
//		entityManager.createQuery(jpql,LockLicensePlateModel.class).setParameter("license_plate_number", license);
//      return entityManager.createQuery(jpql,LockLicensePlateModel.class).setParameter("license_plate_number", license).getSingleResult();
		List<LockLicensePlateModel> list=entityManager.createQuery(criteriaQuery).getResultList();
		return list.isEmpty()?null:list.get(0);
	}
	
	/**
     * 根据小区编号和车牌号码获取锁车对象
     * @return
     */
    public List<LockLicensePlateModel> selectLicenseByParm(String communityNumber,String licensePlateNumber){
        String hql="select lockLicensePlate from LockLicensePlateModel lockLicensePlate where lockLicensePlate.community_number=:community_number and lockLicensePlate.license_plate_number=:license_plate_number";
        return entityManager.createQuery(hql, LockLicensePlateModel.class).setParameter("community_number",communityNumber).setParameter("license_plate_number",licensePlateNumber).getResultList();
    }

    /**
     * 根据小区编号和车牌号码获取锁车对象
     * @return
     */
    public List<LockLicensePlateModel> selectLicenseByParm(String communityNumber,String licensePlateNumber,Integer license_plate_command){
        String hql="select lockLicensePlate from LockLicensePlateModel lockLicensePlate where lockLicensePlate.community_number=:community_number and lockLicensePlate.license_plate_number=:license_plate_number and lockLicensePlate.commderParameter=:license_plate_command";
        return entityManager.createQuery(hql, LockLicensePlateModel.class).setParameter("community_number",communityNumber).setParameter("license_plate_number",licensePlateNumber).setParameter("license_plate_command",license_plate_command).getResultList();
    }
	@Override
	public List<LockLicensePlateModel> selectLicenseAxd(String license) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<LockLicensePlateModel> criteriaQuery = criteriaBuilder.createQuery(LockLicensePlateModel.class);
		Root<LockLicensePlateModel> root = criteriaQuery.from(LockLicensePlateModel.class);
		criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("license_plate_number"), license));	
		List<LockLicensePlateModel> list=entityManager.createQuery(criteriaQuery).getResultList();
		return list.isEmpty()?null:list;
	}

	@Override
	public LockLicensePlateModel selectLicenseByLock(String communityNumber, String licensePlateNumber,	Integer license_plate_command) {
        String hql="select lockLicensePlate from LockLicensePlateModel lockLicensePlate where lockLicensePlate.community_number=:community_number and lockLicensePlate.license_plate_number=:license_plate_number and lockLicensePlate.license_plate_command=:license_plate_command";
        List<LockLicensePlateModel> lockLicensePlateModels =entityManager.createQuery(hql, LockLicensePlateModel.class).setParameter("community_number",communityNumber).setParameter("license_plate_number",licensePlateNumber).setParameter("license_plate_command",license_plate_command).getResultList();
        return (lockLicensePlateModels==null||lockLicensePlateModels.size()<=0)?null:lockLicensePlateModels.get(0);
    }
}
