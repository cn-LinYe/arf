package com.arf.relievedassist.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.service.BaseService;
import com.arf.relievedassist.entity.RelievedAssistContentRecord;

public interface IRelievedAssistContentRecordService extends BaseService<RelievedAssistContentRecord, Long>{

	/**
	 * 根据条件分页查询帖子记录
	 * @param userName
	 * @param communityNumber
	 * @param startTime
	 * @param endTime
	 * @param classifyId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult<Map<String, Object>> findListByCondition(String userName,String communityNumber,
			Date startTime,Date endTime,Long classifyId,Integer pageNo,Integer pageSize,Integer orderType);
	/**
	 * 根据id集合分页查询
	 * @param idList
	 * @param pageNo
	 * @param pageSize
	 * @param orderType
	 * @return
	 */
	public PageResult<Map<String, Object>> findByIds(String communityNumber,List<Long> idList,Integer pageNo,Integer pageSize,Integer orderType);
	
	/**
	 * 根据用户名查找
	 * @param userName
	 * @return
	 */
	List<RelievedAssistContentRecord> findByUserName(String userName);
}
