package com.arf.axd_b2b.dao;

import com.arf.axd_b2b.entity.BusinessAccesssInfo;
import com.arf.core.dao.BaseDao;

public interface IBusinessAccesssInfoDao extends BaseDao<BusinessAccesssInfo, Long> {
	public BusinessAccesssInfo findNearest(Double lat, Double lng);
	
	public BusinessAccesssInfo findByBusinessNum(String businessNum);
}
