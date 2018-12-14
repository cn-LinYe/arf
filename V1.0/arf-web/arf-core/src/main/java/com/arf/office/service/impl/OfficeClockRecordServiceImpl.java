package com.arf.office.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.office.dao.IOfficeClockRecordDao;
import com.arf.office.entity.OfficeClockRecord;
import com.arf.office.service.IOfficeClockRecordService;

@Service("officeClockRecordServiceImpl")
public class OfficeClockRecordServiceImpl extends
		BaseServiceImpl<OfficeClockRecord, Long> implements
		IOfficeClockRecordService {

	@Resource(name = "officeClockRecordDaoImpl")
	private IOfficeClockRecordDao officeClockRecordDaoImpl;
	
	@Override
	protected BaseDao<OfficeClockRecord, Long> getBaseDao() {
		return officeClockRecordDaoImpl;
	}

	@Override
	public OfficeClockRecord findByUsernameCompanyNumberAndtime(
			String companyNumber, String userName, Date now) {
		return officeClockRecordDaoImpl.findByUsernameCompanyNumberAndtime(
				companyNumber,userName,now);
	}

	@Override
	public List<OfficeClockRecord> findByIdentifierAndtime(
			String equipmentIdentifier, Date date) {
		return officeClockRecordDaoImpl.findByIdentifierAndtime(equipmentIdentifier,date);
	}

}
