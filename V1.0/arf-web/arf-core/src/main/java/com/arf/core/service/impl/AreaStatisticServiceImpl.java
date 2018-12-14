package com.arf.core.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.arf.core.dao.AreaStatisticDao;
import com.arf.core.entity.AreaStatistic;
import com.arf.core.service.AreaStatisticService;
/**
 * Service - 地区统计
 * 
 * @author arf
 * @version 4.0
 */
@Service("areaStatisticServiceImpl")
public class AreaStatisticServiceImpl implements AreaStatisticService {	
	@Resource(name = "areaStatisticDaoImpl")
	private AreaStatisticDao areaStatisticDao;

	@Override
	public List<AreaStatistic> findMemcount() {
		return areaStatisticDao.findMemcount();
	}

	@Override
	public List<AreaStatistic> findArdcount() {	
		return areaStatisticDao.findArdcount();
	}

	@Override
	public List<AreaStatistic> findSalesvolume() {
		return areaStatisticDao.findSalesvolume();
	}

	

}
