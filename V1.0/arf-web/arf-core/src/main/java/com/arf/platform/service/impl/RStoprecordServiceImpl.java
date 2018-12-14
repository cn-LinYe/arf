/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.platform.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.AppMessage;
import com.arf.core.AppMessageCode;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.platform.dao.RStoprecordDao;
import com.arf.platform.entity.RStoprecord;
import com.arf.platform.search.StoprecordCondition;
import com.arf.platform.service.RStoprecordService;


/**
 * Service - RStoprecord表
 * 
 * @author arf
 * @version 1.0
 */
@Service("RStoprecordServiceImpl")
public class RStoprecordServiceImpl extends BaseServiceImpl<RStoprecord, Long> implements RStoprecordService {

	@Resource(name = "RStoprecordDaoImpl")
	private RStoprecordDao RStoprecordDao;

	@Override
	protected BaseDao<RStoprecord, Long> getBaseDao() {
		return RStoprecordDao;
	}

	@Override
	public RStoprecord findByComLicArrLeav(String communityNo, String license, Date arriveTime,
			Date leaveTime) {
		return RStoprecordDao.findByComLicArrLeav(communityNo,license,arriveTime,leaveTime);
	}

	@Override
	public RStoprecord findLatestNotPaidRecord(String communityNo, String license, Date arriveTime) {
		return RStoprecordDao.findLatestNotPaidRecord(communityNo, license, arriveTime);
	}

	@Override
	public void saveOrUpdate(RStoprecord record) {
		if(record.getId() != null && record.getId() > 0){
			this.update(record);
		}else{
			this.save(record);
		}
	}

	@Override
	public RStoprecord findByOutTradeNo(String outTradeNo) {
		return RStoprecordDao.findByOutTradeNo(outTradeNo);
	}

	@Override
	public List<RStoprecord> findAllPaidRecord(String communityNo, String license, Date arriveTime) {
		return RStoprecordDao.findAllPaidRecord(communityNo, license, arriveTime);
	}

	@Override
	public PageResult<RStoprecord> myStopRecords(StoprecordCondition condition) {
		return RStoprecordDao.myStopRecords(condition);
	}

	@Override
	public AppMessage reqTemporaryParkRecord(String userName, int pageSize, int pageNo, AppMessage result) {
		Map<String,Object> extrDatas = new HashMap<String,Object>();
		PageResult<RStoprecord> resultList = RStoprecordDao.myTemporaryStopRecords(userName, pageSize, pageNo);
		extrDatas.put("temporaryList", resultList.getList());
		extrDatas.put("totalNum", resultList.getTotalNum());
		result = new AppMessage(AppMessageCode.CODE_SUCCESS, AppMessageCode.MSG_SUCCESS, extrDatas);
		return result;
	}

	@Override
	public List<String> findMonthsInDb() {
		return RStoprecordDao.findMonthsInDb();
	}

	@Override
	@Transactional
	public int migrateRStoprecords(String subTableName, String createStart, String createEnd, int count) {
		long first = 0L;
		//判断表存在不存在,不存在建表再插入新的数据库
		if(!this.existTable(subTableName)){
			this.copyTable("r_stoprecord", subTableName);
		}
		this.RStoprecordDao.changeEngine(subTableName, "MyISAM");
		while(true){
			int effect = this.RStoprecordDao.migrateRStoprecords(subTableName, createStart, createEnd, first, count);
			if(effect > 0){
				first = first + effect;
			}else{
				break;
			}
		}
		//删除原r_stoprecord表中的数据
		while(true){
			int effect = this.RStoprecordDao.deleteBetweenCreateDate(createStart, createEnd, count);
			if(effect <= 0){
				break;
			}
		}
		
		return (int) first;
	}
	@Override
	public List<RStoprecord> findOrderNotPaid(Date time) {
		return RStoprecordDao.findOrderNotPaid(time);
	}

	@Override
	public RStoprecord findRecentPaidRecord(String communityNo, String license, Date arriveTime) {		
		return RStoprecordDao.findRecentPaidRecord(communityNo, license, arriveTime);
	}

	@Override
	public int findAppearanceTodayByParkingId(String communityNo) {		
		return RStoprecordDao.findAppearanceTodayByParkingId(communityNo);
	}

}