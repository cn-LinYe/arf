package com.arf.advertisements.dao;

import java.util.List;

import com.arf.advertisements.entity.Advertisements;
import com.arf.advertisements.entity.Advertisements.AdStatus;
import com.arf.core.dao.BaseDao;

public interface AdvertisementsDao extends BaseDao<Advertisements,Long>{

	/**
	 * 通过广告状态来查找
	 * @param status
	 * @return
	 */
	List<Advertisements> findByStatus(AdStatus status);

}
