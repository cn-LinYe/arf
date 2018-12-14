package com.arf.statistics.service;

import java.util.Date;

import com.arf.core.service.BaseService;
import com.arf.statistics.entity.StaticiesAdClick;
import com.arf.statistics.entity.StaticiesAdClick.ClickType;

public interface IStaticiesAdClickService extends BaseService<StaticiesAdClick, Long> {
	/**
	 * 根据用户名和点击广告类型查询
	 * @param clickType
	 * @param userName
	 * @param date
	 * @return
	 */
	StaticiesAdClick findByUserNameAndClickType(ClickType clickType,String userName,String accessMac,String adNum, String license
			, Date startDate, Date endDate);

}
