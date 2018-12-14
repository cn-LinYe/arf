package com.arf.core.dao;

import java.util.List;

import com.arf.core.entity.UserLicenseRecordModel;

/**
 * Dao - 违章查询接口 用户车辆信息Db缓存
 * 
 * @author arf
 * @version 4.0
 */
public interface UserLicenseRecordModelDao extends BaseDao<UserLicenseRecordModel, Long>{
    
    List<UserLicenseRecordModel> selectRecords(UserLicenseRecordModel userLicenseRecordModel);
    
    /**
     * 删除旧违章查询表的记录,转移新表数据到旧表
     */
    public void initTable();
    /**
     * 获取车牌信息
     * @param license_plate_number
     * @param his_no
     * @return
     */
    public UserLicenseRecordModel findLicenseRecord(String license_plate_number,String his_no);
    /***
     * 比较新旧表信息并获取所有的违章记录
     */
    public void compareTable();
}
