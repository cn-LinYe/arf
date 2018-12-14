package com.arf.member.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.member.entity.AppTerminalDevice;

/**
 * 用户终端设备记录表Dao
 * @author Caolibao
 *
 */
public interface IAppTerminalDeviceService extends BaseService<AppTerminalDevice, Long>{
	
	/**
	 * 根据设备唯一识别号判断该设备是不是存在记录
	 * @param uniqueCode
	 * @return
	 */
	boolean exist(String userName, String uniqueCode);
	
	/**
	 * 通过用户名查询出终端设备记录List
	 * @param userName
	 * @return
	 */
	List<AppTerminalDevice> findByUserName(String userName);
	
	/**
	 * 通过用户名查询出终端设备
	 * @param userName
	 * @param uniqueCode
	 * @return
	 */
	AppTerminalDevice findByUserNameUniqueCode(String userName,String uniqueCode);
}
