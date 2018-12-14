package com.arf.advertisements.service;

import java.util.List;

import com.arf.advertisements.entity.Advertisements;
import com.arf.advertisements.entity.Advertisements.AdStatus;
import com.arf.core.service.BaseService;

public interface AdvertisementsService extends BaseService<Advertisements,Long>{

	/**
	 * 通过广告状态来查找
	 * @param status
	 * @return
	 */
	List<Advertisements> findByStatus(AdStatus status);

}
