package com.arf.office.dao;

import java.util.Date;
import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.office.entity.OfficeClockRecord;

public interface IOfficeClockRecordDao extends BaseDao<OfficeClockRecord, Long> {

	OfficeClockRecord findByUsernameCompanyNumberAndtime(String companyNumber,
			String userName, Date now);

	List<OfficeClockRecord> findByIdentifierAndtime(String equipmentIdentifier,
			Date date);

}
