package com.arf.violation.dao;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.violation.entity.TrafficViolationRecord;

public interface ITrafficViolationRecordDao extends BaseDao<TrafficViolationRecord, Long>{

	/**
	 * 通过违章信息的特征码查询
	 * @return
	 */
	TrafficViolationRecord findByUniqueCode(String uniqueCode);
	
	/**
	 * 通过违章记录ID查询
	 * @return
	 */
	TrafficViolationRecord findBySecondaryUniqueCode(String secondaryUniqueCode);
	
	/**
	 * 通过通过违章时间和车牌查找违章
	 * @return
	 */
	List<TrafficViolationRecord> findByLiscenseTime(String license,List<Date> list);
	
	
	/**
	 * 根据车牌查找违章
	 * @return
	 */
	PageResult<TrafficViolationRecord> findByLicense(String license,Integer pageNo,Integer pageSize);
	
	/**
	 * 根据违章标示编码数组查询
	 * @param uniqueCode
	 * @return
	 */
	List<TrafficViolationRecord> findByUniqueCode(String[] uniqueCode,TrafficViolationRecord.Status status);
	
	/**
	 * 通过uniqueCode的list查违章记录
	 * @param uniqueCode
	 * @return
	 */
	List<TrafficViolationRecord> findByUniqueCode(List<String> uniqueCode);
	
	/**
	 * 通过通过违章记录ID查询集合查询
	 * @return
	 */
	List<TrafficViolationRecord> findBysecondaryUniqueCodes(List<String> list);
	
	/**
	 * 批量新增违章记录
	 * @param jsonArray
	 */
	void saveAllViolationRecord(JSONArray jsonArray,String license);
}
