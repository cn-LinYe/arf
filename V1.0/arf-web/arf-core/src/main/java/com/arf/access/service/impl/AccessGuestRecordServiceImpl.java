package com.arf.access.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessGuestRecordDao;
import com.arf.access.entity.AccessGuestRecord;
import com.arf.access.entity.AccessGuestRecord.Status;
import com.arf.access.service.IAccessGuestRecordService;
import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.filter.NonprintUnicodeFilter;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessGuestRecordServiceImpl")
public class AccessGuestRecordServiceImpl extends
		BaseServiceImpl<AccessGuestRecord, Long> implements
		IAccessGuestRecordService {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "accessGuestRecordDaoImpl")
	private IAccessGuestRecordDao accessGuestRecordDaoImpl;
	
	@Override
	protected BaseDao<AccessGuestRecord, Long> getBaseDao() {
		return accessGuestRecordDaoImpl;
	}

	@Override
	public PageResult<Map<String, Object>> findListByCondition(Integer pageSize,
			Integer pageNo, List<String> userName, Status status) {
		return accessGuestRecordDaoImpl.findListByCondition(pageSize,pageNo,userName, status);
	}
	
	@Override
	public PageResult<Map<String,Object>> findListByCondition(Integer pageSize,
			Integer pageNo, String userName,String guestIdentifyId,Date startDate,Date endDate) {
		return accessGuestRecordDaoImpl.findListByCondition(pageSize,pageNo,userName,guestIdentifyId,startDate,endDate);
	}

	@Override
	public AccessGuestRecord findById(Long id,Status status) {
		return accessGuestRecordDaoImpl.findByIdUsername(id,status);
	}

	@Override
	public int delete(String id, String userName) {
		return accessGuestRecordDaoImpl.delete(id,userName);
	}

	@Override
	public List<AccessGuestRecord> findByGuestIdentifyIdStatus(String guestIdentifyId,
			Status status) {
		return accessGuestRecordDaoImpl.findByGuestIdentifyIdStatus(guestIdentifyId,status);
	}
	
	@Override
	public AccessGuestRecord save(AccessGuestRecord entity) {
		NonprintUnicodeFilter emjiFilter = NonprintUnicodeFilter.getInstance();
		String nickName = emjiFilter.filterEmoji(entity.getNickname());
		entity.setNickname(nickName);
		return super.save(entity);
	}
	@Override
	public AccessGuestRecord update(AccessGuestRecord entity) {
		NonprintUnicodeFilter emjiFilter = NonprintUnicodeFilter.getInstance();
		String nickName = emjiFilter.filterEmoji(entity.getNickname());
		entity.setNickname(nickName);
		return super.update(entity);
	}
	
	@Override
	public AccessGuestRecord update(AccessGuestRecord entity, String... ignoreProperties) {
		NonprintUnicodeFilter emjiFilter = NonprintUnicodeFilter.getInstance();
		String nickName = emjiFilter.filterEmoji(entity.getNickname());
		entity.setNickname(nickName);
		return super.update(entity, ignoreProperties);
	}

	@Override
	public int updateReadStatus(List<Long> idList) {
		return accessGuestRecordDaoImpl.updateReadStatus(idList);
	}
	
	@Override
	public void modifyOverdue(int applytimeouthour) {
		accessGuestRecordDaoImpl.modifyOverdue(applytimeouthour);
	}

	@Override
	public AccessGuestRecord findByIdStatus(Long id, Status status) {
		return accessGuestRecordDaoImpl.findByIdStatus(id,status);
	}

	@Override
	public PageResult<Map<String, Object>> findByBoundNumber(String roomBoundNumber, Status status,Integer pageSize,
			Integer pageNo) {
		return accessGuestRecordDaoImpl.findByBoundNumber(roomBoundNumber, status,pageSize,pageNo);
	}

	@Override
	public int findCountByRoomAndStatus(String roomNumber, Status status) {
		return accessGuestRecordDaoImpl.findCountByRoomAndStatus(roomNumber, status);
	}

}
