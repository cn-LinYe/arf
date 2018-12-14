package com.arf.advertisements.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.advertisements.dao.AdvertisementsDao;
import com.arf.advertisements.entity.Advertisements;
import com.arf.advertisements.entity.Advertisements.AdStatus;
import com.arf.advertisements.service.AdvertisementsService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("advertisementsService")
public class AdvertisementsServiceImpl extends BaseServiceImpl<Advertisements,Long> implements AdvertisementsService{

	@Resource(name="advertisementsDao")
	AdvertisementsDao advertisementsDao;
	
	@Override
	protected BaseDao<Advertisements, Long> getBaseDao() {
		return advertisementsDao;
	}

	@Override
	public List<Advertisements> findByStatus(AdStatus status) {
		return advertisementsDao.findByStatus(status);
	}

}
