package com.arf.access.service;

import java.util.List;
import java.util.Map;

import com.arf.access.entity.AccessUserPasswd;
import com.arf.access.entity.AccessUserPasswd.UseStatus;
import com.arf.core.service.BaseService;

public interface IAccessUserPasswdService extends
		BaseService<AccessUserPasswd, Long> {

	/**
	 * 根据密码和蓝牙mac查询
	 * @param password
	 * @param bluetoothMac
	 * @param roomNumber
	 * @param useStatus
	 * @return
	 */
	public List<AccessUserPasswd> findByBlueMacAndPwd(String password ,String bluetoothMac,String roomNumber,UseStatus useStatus);
	
	/**
	 * 根据房间号，小区编号查询
	 * @param roomNumber
	 * @param communityNumber
	 * @return
	 */
	public List<Map<String, Object>> findByRoomAndCommunity(String roomNumber,String communityNumber);
	
	/**
	 * 根据密码和蓝牙mac查询
	 * @param password
	 * @param bluetoothMac
	 * @return
	 */
	public AccessUserPasswd findByPwdAndMac(String password ,String bluetoothMac);
}
