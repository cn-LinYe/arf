package com.arf.core.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import com.arf.core.dao.RBookrecordDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.RBookrecord;
import com.arf.core.service.RBookrecordService;

/**
 * Service - RBookrecord表
 * 
 * @author arf dg
 * @version 4.0
 */
@Service("RBookrecordServiceImpl")
public class RBookrecordServiceImpl extends BaseServiceImpl<RBookrecord, Long> implements RBookrecordService {

	@Resource(name = "RBookrecordDaoImpl")
	private RBookrecordDao RBookrecordDao;

	@Override
	protected BaseDao<RBookrecord, Long> getBaseDao() {
		return RBookrecordDao;
	}

	@Override
	public List<RBookrecord> findByOrderSn(String orderSn) {
		return RBookrecordDao.findByOrderSn(orderSn);
	}

}