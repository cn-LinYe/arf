package com.arf.statistics.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.statistics.dao.IStaticiesAdClickDao;
import com.arf.statistics.entity.StaticiesAdClick;
import com.arf.statistics.entity.StaticiesAdClick.ClickType;
import com.arf.statistics.service.IStaticiesAdClickService;

@Service("staticiesAdClickService")
public class StaticiesAdClickServiceImpl extends BaseServiceImpl<StaticiesAdClick, Long> implements IStaticiesAdClickService{
	
	@Resource(name="staticiesAdClickDao")
	IStaticiesAdClickDao staticiesAdClickDao;

	@Override
	protected BaseDao<StaticiesAdClick, Long> getBaseDao() {
		return staticiesAdClickDao;
	}

	@Override
	public StaticiesAdClick findByUserNameAndClickType(ClickType clickType, String userName,String accessMac,String adNum, String license
			, Date startDate, Date endDate) {
		return staticiesAdClickDao.findByUserNameAndClickType(clickType, userName,accessMac,adNum,license,startDate,endDate);
	}

}
