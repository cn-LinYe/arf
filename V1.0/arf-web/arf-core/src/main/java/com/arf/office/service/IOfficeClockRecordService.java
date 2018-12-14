package com.arf.office.service;

import java.util.Date;
import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.office.entity.OfficeClockRecord;

public interface IOfficeClockRecordService extends BaseService<OfficeClockRecord, Long> {

	/**
	 * 查询用户某天的唯一考勤记录
	 * @param companyNumber
	 * @param userName
	 * @param now
	 * @return
	 */
	OfficeClockRecord findByUsernameCompanyNumberAndtime(String companyNumber,
			String userName, Date now);

	/**
	 * 查询某一天、唯一手机标识的考勤记录
	 * @param equipmentIdentifier
	 * @param date
	 * @return
	 */
	List<OfficeClockRecord> findByIdentifierAndtime(String equipmentIdentifier,
			Date date);

}
