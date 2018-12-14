package com.arf.member.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.member.dao.IAppTerminalDeviceDao;
import com.arf.member.entity.AppTerminalDevice;

/**
 * 用户终端设备记录表Dao
 * @author Caolibao
 *
 */
@Repository("appTerminalDeviceDao")
public class AppTerminalDeviceDaoImpl extends BaseDaoImpl<AppTerminalDevice, Long> implements IAppTerminalDeviceDao{
	
}
