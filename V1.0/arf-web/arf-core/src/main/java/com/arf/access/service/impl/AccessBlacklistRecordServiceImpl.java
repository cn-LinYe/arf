package com.arf.access.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessBlacklistRecordDao;
import com.arf.access.entity.AccessBlacklistRecord;
import com.arf.access.entity.AccessBlacklistRecord.Status;
import com.arf.access.entity.AccessGuestRecord.GuestType;
import com.arf.access.service.IAccessBlacklistRecordService;
import com.arf.core.dao.BaseDao;
import com.arf.core.filter.NonprintUnicodeFilter;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessBlacklistRecordServiceImpl")
public class AccessBlacklistRecordServiceImpl extends
		BaseServiceImpl<AccessBlacklistRecord, Long> implements
		IAccessBlacklistRecordService {
	Logger log = LoggerFactory.getLogger(getClass());
	@Resource(name = "accessBlacklistRecordDaoImpl")
	private IAccessBlacklistRecordDao accessBlacklistRecordDaoImpl;
	
	@Override
	protected BaseDao<AccessBlacklistRecord, Long> getBaseDao() {
		return accessBlacklistRecordDaoImpl;
	}

	@Override
	public AccessBlacklistRecord findByGuestUsernameOprateUsername(String guestIdentifyId,
			GuestType guestType, String userName,String oprateUsername) {
		return accessBlacklistRecordDaoImpl.findByGuestUsernameOprateUsername(
				guestIdentifyId,guestType,userName,oprateUsername);
	}

	@Override
	public List<AccessBlacklistRecord> findByUsernameStatusOprateUsername(String userName,
			Status status,String oprateUsername) {
		return accessBlacklistRecordDaoImpl.findByUsernameStatusOprateUsername(userName,status,oprateUsername);
	}

	@Override
	public AccessBlacklistRecord save(AccessBlacklistRecord entity) {
		NonprintUnicodeFilter emjiFilter = NonprintUnicodeFilter.getInstance();
		String nickName = emjiFilter.filterEmoji(entity.getNickname());
		entity.setNickname(nickName);
		return super.save(entity);
	}

	@Override
	public AccessBlacklistRecord update(AccessBlacklistRecord entity) {
		NonprintUnicodeFilter emjiFilter = NonprintUnicodeFilter.getInstance();
		String nickName = emjiFilter.filterEmoji(entity.getNickname());
		entity.setNickname(nickName);
		return super.update(entity);
	}
	
	@Override
	public AccessBlacklistRecord update(AccessBlacklistRecord entity, String... ignoreProperties) {
		NonprintUnicodeFilter emjiFilter = NonprintUnicodeFilter.getInstance();
		String nickName = emjiFilter.filterEmoji(entity.getNickname());
		entity.setNickname(nickName);
		return super.update(entity, ignoreProperties);
	}
}
