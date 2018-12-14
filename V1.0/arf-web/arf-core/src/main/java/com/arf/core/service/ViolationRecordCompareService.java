package com.arf.core.service;

import java.util.List;

import com.arf.core.entity.ViolationRecordCompare;

/**
 * Service - 用于比较的违章记录类
 * 
 * @author arf
 * @version 4.0
 */
public interface ViolationRecordCompareService extends BaseService<ViolationRecordCompare, Long> {
    /**
     * 根据用户名查询违章记录比较信息
     * @param username
     * @return
     */
    public List<ViolationRecordCompare> findListByUserName(String username);
}
