package com.arf.statistics.dao;

import java.util.Date;

import com.arf.core.dao.BaseDao;
import com.arf.statistics.entity.StaticiesAdClick;
import com.arf.statistics.entity.StaticiesAdClick.ClickType;

public interface IStaticiesAdClickDao extends BaseDao<StaticiesAdClick, Long>{
	
	/**
	 * 根据用户名和点击广告类型查询
	 * @param clickType
	 * @param userName
	 * @param date
	 * @return
	 */
	StaticiesAdClick findByUserNameAndClickType(ClickType clickType,String userName,String accessMac,String adNum, String license
			,Date startDate, Date endDate);

}
