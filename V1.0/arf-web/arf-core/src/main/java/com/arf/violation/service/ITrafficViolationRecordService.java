package com.arf.violation.service;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.arf.base.PageResult;
import com.arf.core.service.BaseService;
import com.arf.violation.entity.TrafficViolationRecord;

public interface ITrafficViolationRecordService extends BaseService<TrafficViolationRecord, Long>{

	/**
	 * 通过违章信息的特征码查询
	 * @return
	 */
	TrafficViolationRecord findByUniqueCode(String uniqueCode);
	
	/**
	 * 通过通过违章记录ID查询集合查询
	 * @return
	 */
	List<TrafficViolationRecord> findBysecondaryUniqueCodes(List<String> list);
	
	/**
	 * 通过通过违章时间和车牌查找违章
	 * @return
	 */
	List<TrafficViolationRecord> findByLiscenseTime(String license,List<Date> list);
	
	/**
	 * 批量新增违章记录
	 * @param jsonArray
	 */
	void saveAllViolationRecord(JSONArray jsonArray,String license);
	
	/**
	 * 通过违章记录ID查询
	 * @return
	 */
	TrafficViolationRecord findBySecondaryUniqueCode(String secondaryUniqueCode);
	/**
	 * 根据车牌查找
	 * @return
	 */
	PageResult<TrafficViolationRecord> findByLicense(String license,Integer pageNo,Integer pageSize);
	
	/**
	 * 根据uniqueCode数组查询
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
	 * 
	 * 获取车牌验证条件 
	 * @param licensePlateNumber 必填
	 * @return 
	 * [0] = violationsQueryCarCodeLen 车架号需要位数, <br />
	 * [1] = violationsQueryCarEngineLen 发动机需要位数，<br />
	 * 99 完整的 ； 0 不需要 ； 大于0 取对应数字长度 ;null 通过车牌号查询不到该参数
	 */
	Integer[] getVerificationCondition(String licensePlateNumber);
}
