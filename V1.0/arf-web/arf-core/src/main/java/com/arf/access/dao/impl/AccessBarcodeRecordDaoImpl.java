package com.arf.access.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessBarcodeRecordDao;
import com.arf.access.entity.AccessBarcodeRecord;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessBarcodeRecordDaoImpl")
public class AccessBarcodeRecordDaoImpl extends
		BaseDaoImpl<AccessBarcodeRecord, Long> implements
		IAccessBarcodeRecordDao {

	
	
}
