package com.arf.relievedassist.dao;

import com.arf.core.dao.BaseDao;
import com.arf.relievedassist.entity.RelievedAssistUserGradeRecord;

public interface IRelievedAssistUserGradeRecordDao extends BaseDao<RelievedAssistUserGradeRecord, Long>{
	
	/**查询默认的等级，如果没有填充1
	 * @return
	 */
	RelievedAssistUserGradeRecord findDefaultGrade();
	
	/**
	 * 查询积分所对应的等级称号
	 * @param assistPoint
	 * @return
	 */
	RelievedAssistUserGradeRecord findByAssistPoint(Integer assistPoint);
}
