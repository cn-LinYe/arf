package com.arf.royalstar.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.royalstar.dao.RSCarwashingDeviceLogDao;
import com.arf.royalstar.entity.RSCarwashingDeviceLog;

@Repository("rsCarwashingDeviceLogDaoImpl")
public class RSCarwashingDeviceLogDaoImpl extends BaseDaoImpl<RSCarwashingDeviceLog, Long>
		implements RSCarwashingDeviceLogDao {

}
