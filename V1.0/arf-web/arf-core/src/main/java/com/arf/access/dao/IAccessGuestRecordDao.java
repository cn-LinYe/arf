package com.arf.access.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.arf.access.entity.AccessGuestRecord;
import com.arf.access.entity.AccessGuestRecord.Status;
import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;

public interface IAccessGuestRecordDao extends BaseDao<AccessGuestRecord, Long> {

	/**
	 * 分页查询我的访客记录
	 * @param pageSize
	 * @param pageNo
	 * @param userName
	 * @return
	 */
	PageResult<Map<String, Object>> findListByCondition(Integer pageSize,
			Integer pageNo, List<String> userName, Status status);
	
	/**
	 * 分页查询我的访客记录
	 * @param pageSize
	 * @param pageNo
	 * @param userName
	 * @param guestIdentifyId
	 * @return
	 */
	PageResult<Map<String, Object>> findListByCondition(Integer pageSize,
			Integer pageNo, String userName,String guestIdentifyId,Date startDate,Date endDate);

	/**
	 * 根据主键id、状态查询
	 * @param id
	 * @param status
	 * @return
	 */
	AccessGuestRecord findByIdUsername(Long id,Status status);

	/**
	 * 根据id、用户名批量删除（逻辑删除）
	 * @param id
	 * @param userName
	 * @return
	 */
	int delete(String ids, String userName);

	/**
	 * 根据访客openid，状态查询
	 * @param guestIdentifyId
	 * @param status
	 * @return
	 */
	List<AccessGuestRecord> findByGuestIdentifyIdStatus(String guestIdentifyId, Status status);

	/**
	 * 批量设置已读状态
	 * @param idList
	 */
	int updateReadStatus(List<Long> idList);

	/**
	 * 将过期的修改为status = AccessGuestRecord.Status.TIMEOUT
	 * @param applytimeouthour (单位：小时) 当前时间(now)与申请时间(applyDate)的差值超过此值，即为超时
	 */
	void modifyOverdue(int applytimeouthour);

	/**
	 * 根据主键id、状态查询
	 * @param id
	 * @param status
	 * @return
	 */
	AccessGuestRecord findByIdStatus(Long id, Status status);
	
	/**
	 * 根据房屋绑定编号，状态分页查询
	 * @param guestIdentifyId
	 * @param status
	 * @return
	 */
	PageResult<Map<String, Object>> findByBoundNumber(String roomBoundNumber, Status status,Integer pageSize,
			Integer pageNo);
	
	/**
	 * 根据房屋编号，状态分页查询访客记录数量
	 * @param idList
	 */
	int findCountByRoomAndStatus(String roomNumber, Status status);

}
