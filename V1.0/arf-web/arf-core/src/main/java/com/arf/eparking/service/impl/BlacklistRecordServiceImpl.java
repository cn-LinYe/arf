package com.arf.eparking.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.eparking.dao.BlacklistRecordDao;
import com.arf.eparking.entity.BlacklistRecord;
import com.arf.eparking.entity.BlacklistRecord.Status;
import com.arf.eparking.entity.BlacklistRecord.Type;
import com.arf.eparking.service.BlacklistRecordService;

@Repository("blacklistRecordServiceImpl")
public class BlacklistRecordServiceImpl extends BaseServiceImpl<BlacklistRecord, Long> implements BlacklistRecordService  {

	@Resource(name = "blacklistRecordDaoImpl")
	private BlacklistRecordDao blacklistRecordDaoImpl;
	
	@Override
	protected BaseDao<BlacklistRecord, Long> getBaseDao() {
		return blacklistRecordDaoImpl;
	}

	@Override
	public String genBlacklistNo() {
		Date now = new Date();
		String dateStr = DateFormatUtils.format(now, "yyyyMMddHHmmssSSS");
		String blacklistNo = "";
		while(true){
			blacklistNo =  dateStr + RandomStringUtils.randomNumeric(8);
			BlacklistRecord exist = blacklistRecordDaoImpl.finByBlacklistNo(blacklistNo);//查库
			if(exist == null){
				break;
			}
		}
		return blacklistNo;
	}

	@Override
	public BlacklistRecord findByUserNameStatusAndType(String userName,Status status, Type type) {
		return blacklistRecordDaoImpl.findByUserNameStatusAndType(userName,status,type);
	}

}
