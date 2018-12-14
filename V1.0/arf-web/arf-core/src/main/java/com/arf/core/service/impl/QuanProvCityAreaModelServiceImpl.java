/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import javax.annotation.Resource;
import com.arf.core.dao.QuanProvCityAreaModelDao;
import com.arf.core.dto.AreaDetailInfoDto;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.QuanProvCityAreaModel;
import com.arf.core.service.QuanProvCityAreaModelService;


/**
 * Service - 省市区表
 * 
 * @author arf
 * @version 4.0
 */
@Service("quanProvCityAreaModelServiceImpl")
public class QuanProvCityAreaModelServiceImpl extends BaseServiceImpl<QuanProvCityAreaModel, Long> implements QuanProvCityAreaModelService {

	@Resource(name = "quanProvCityAreaModelDaoImpl")
	private QuanProvCityAreaModelDao quanProvCityAreaModelDao;

	@Override
	protected BaseDao<QuanProvCityAreaModel, Long> getBaseDao() {
		return quanProvCityAreaModelDao;
	}
	
	@Override
	public List<QuanProvCityAreaModel> selectAllCity(String no) {
		return quanProvCityAreaModelDao.selectAllCity(no);
	}

	@Override
	public List<QuanProvCityAreaModel> selectAllArea(String areacode) {
		return quanProvCityAreaModelDao.selectAllArea(areacode);
	}

	@Override
	public List<QuanProvCityAreaModel> selectAllQuarters(String no) {
		return quanProvCityAreaModelDao.selectAllQuarters(no);
	}

	@Override
	public List<QuanProvCityAreaModel> sellectProvice() {
		return quanProvCityAreaModelDao.sellectProvice();
	}

	@Override
	@Transactional(readOnly = true)
	public List<QuanProvCityAreaModel> checkByAreaName(String areaName) {
		return quanProvCityAreaModelDao.checkByAreaName(areaName);
	}

	@Override
	public AreaDetailInfoDto getAreaDetailInfo(String no) {
		return quanProvCityAreaModelDao.getAreaDetailInfo(no);
	}

	@Override
	public List<QuanProvCityAreaModel> checkByAreaName(List<Integer> nos) {
		return quanProvCityAreaModelDao.checkByAreaName(nos);
	}

}