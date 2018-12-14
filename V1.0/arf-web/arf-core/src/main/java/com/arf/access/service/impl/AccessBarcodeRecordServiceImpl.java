package com.arf.access.service.impl;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessBarcodeRecordDao;
import com.arf.access.entity.AccessBarcodeRecord;
import com.arf.access.service.IAccessBarcodeRecordService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessBarcodeRecordServiceImpl")
@Lazy(value = false)
public class AccessBarcodeRecordServiceImpl extends
		BaseServiceImpl<AccessBarcodeRecord, Long> implements
		IAccessBarcodeRecordService {

	@Resource(name = "accessBarcodeRecordDaoImpl")
	private IAccessBarcodeRecordDao accessBarcodeRecordDaoImpl;
	
	@Override
	protected BaseDao<AccessBarcodeRecord, Long> getBaseDao() {
		return accessBarcodeRecordDaoImpl;
	}

}
