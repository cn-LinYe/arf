package com.arf.core.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.UserLicenseRecordModelDao;
import com.arf.core.entity.UserLicenseRecordModel;

/**
 * Dao - 违章查询接口 用户车辆信息Db缓存
 * 
 * @author arf
 * @version 4.0
 */
@Repository("userLicenseRecordModelDaoImpl")
public class UserLicenseRecordModelDaoImpl extends BaseDaoImpl<UserLicenseRecordModel, Long> implements UserLicenseRecordModelDao {
	
    @Override
	public List<UserLicenseRecordModel> selectRecords(UserLicenseRecordModel userLicenseRecordModel) {
        
        List<UserLicenseRecordModel> list = null;
        Long id = userLicenseRecordModel.getId();
        String his_no = userLicenseRecordModel.getHis_no();
        String license_plate_number = userLicenseRecordModel.getLicense_plate_number();
        
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserLicenseRecordModel> criteriaQuery = criteriaBuilder.createQuery(UserLicenseRecordModel.class);
        Root<UserLicenseRecordModel> root = criteriaQuery.from(UserLicenseRecordModel.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (null != id && id > 0) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("id"), id));
        }
        if (null != his_no) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("his_no"), his_no));
        }
        if (null != license_plate_number) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("license_plate_number"), license_plate_number));
        }
        
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, null, null, null, null);
    }
    /**
     * 删除旧违章查询表的记录,转移新表数据到旧表
     */
    @Override
	public void initTable(){
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String clearTable="truncate table violation_record_old";//清除表数据的语句
        
        entityManager.createNativeQuery(clearTable).executeUpdate();
        String moveTable="insert into violation_record_old(create_date,modify_date,version,license_plate_number,his_no,has_data,record) "+
                "select '"+sim.format(new Date())+"','"+sim.format(new Date())+"',0,license_plate_number,his_no,case when has_data is null or has_data='' then false else has_data end,records from user_license_record";//转移表数据
        
        entityManager.createNativeQuery(moveTable).executeUpdate();
    }
    /**
     * 获取车牌信息
     * @param license_plate_number
     * @param his_no
     * @return
     */
    @Override
	public UserLicenseRecordModel findLicenseRecord(String license_plate_number,String his_no){
        String hql="from UserLicenseRecordModel urecord where urecord.license_plate_number=:license_plate_number and urecord.his_no=:his_no";
        List<UserLicenseRecordModel> list=entityManager.createQuery(hql,UserLicenseRecordModel.class).setParameter("license_plate_number", license_plate_number).setParameter("his_no", his_no).getResultList();
        return list.isEmpty()?null:list.get(0);
    }
    /***
     * 比较新旧表信息并获取所有的违章记录
     */
    @Override
	public void compareTable(){
        String sql="truncate table violation_record_compare";//清空比较表
        entityManager.createNativeQuery(sql).executeUpdate();
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //查找出违章表中的违章信息匹配旧违章信息保存到违章记录比较表
        //查找出违章表中新查询到的违章信息保存到违章记录比较表
        sql="insert into violation_record_compare(create_date,modify_date,version,license_plate_number,his_no,oldrecords,newrecords,username) "+
                "select '"+sim.format(new Date())+"','"+sim.format(new Date())+"',0,ur.license_plate_number,ur.his_no,vrold.record,ur.records,lp.user_name from user_license_record ur "+
                "inner join violation_record_old vrold on ur.license_plate_number=vrold.license_plate_number and ur.his_no=vrold.his_no "+
                "inner join license_plate lp on lp.license_plate_number=vrold.license_plate_number and lp.his_no=vrold.his_no "+
                "where ur.has_data=true "+
                "union  "+
                "select '"+sim.format(new Date())+"','"+sim.format(new Date())+"',0,ur.license_plate_number,ur.his_no,'',ur.records,lp.user_name from user_license_record ur "+
                "inner join license_plate lp on lp.license_plate_number=ur.license_plate_number and lp.his_no=ur.his_no "+
                "where ur.license_plate_number not in (select license_plate_number from violation_record_old) "+
                "and ur.has_data=true";
        entityManager.createNativeQuery(sql).executeUpdate();
    }
}
