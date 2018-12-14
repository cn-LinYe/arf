package com.arf.relievedassist.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.relievedassist.dao.IRelievedAssistSensitiveWordRecordDao;
import com.arf.relievedassist.entity.RelievedAssistSensitiveWordRecord;

@Repository("relievedAssistSensitiveWordRecordDao")
public class RelievedAssistSensitiveWordRecordDaoImpl extends BaseDaoImpl<RelievedAssistSensitiveWordRecord, Long> implements IRelievedAssistSensitiveWordRecordDao{

}
