package com.arf.activity.parkrate.dao;

import com.arf.activity.parkrate.entity.ActivityRecord;
import com.arf.core.dao.BaseDao;

public interface IActivityRecordDao extends BaseDao<ActivityRecord, Long> {

	ActivityRecord findByOutTradeNo(String outTradeNo);

}
