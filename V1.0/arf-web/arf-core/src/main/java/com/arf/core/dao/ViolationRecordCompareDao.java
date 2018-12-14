package com.arf.core.dao;

import java.util.List;

import com.arf.core.entity.ViolationRecordCompare;

/**
 * Dao - 用于比较的违章记录类
 * 
 * @author arf
 * @version 4.0
 */
public interface ViolationRecordCompareDao extends BaseDao<ViolationRecordCompare, Long>{
    /**
     * 根据用户名查询违章记录比较信息
     * @param username
     * @return
     */
    public List<ViolationRecordCompare> findListByUserName(String username);
}
