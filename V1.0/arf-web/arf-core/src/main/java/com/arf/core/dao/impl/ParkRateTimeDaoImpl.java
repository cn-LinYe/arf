package com.arf.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.ParkRateTimeDao;
import com.arf.core.entity.ParkRateTime;

/**
 * Dao - 月租车
 * 
 * @author arf  dg
 * @version 4.0
 */
@Repository("parkRateTimeDaoImpl")
public class ParkRateTimeDaoImpl extends BaseDaoImpl<ParkRateTime, Long> implements ParkRateTimeDao {
	
}
