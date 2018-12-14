package com.arf.core.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.P_DeviceinfoDao;
import com.arf.core.entity.PDeviceinfo;

/**
 * Dao - P_Deviceinfo表
 * 
 * @author arf  dg
 * @version 4.0
 */
@Repository("P_DeviceinfoDaoImpl")
public class P_DeviceinfoDaoImpl extends BaseDaoImpl<PDeviceinfo, Long> implements P_DeviceinfoDao {

	@Override
	public PDeviceinfo findByDeviceNum(String deviceNum,String communityNum) {

		if (StringUtils.isEmpty(deviceNum)||StringUtils.isEmpty(communityNum)) {
            return null;
        }
		String hql = "from com.arf.core.entity.PDeviceinfo where  device_Num ='" + deviceNum + "' and communityNo='" + communityNum + "'" ;
		List<PDeviceinfo> list = entityManager.createQuery(hql,PDeviceinfo.class).getResultList();
		if(list != null && list.size() != 0){
			return list.get(0);
		}
		return null;
	}
	
}
