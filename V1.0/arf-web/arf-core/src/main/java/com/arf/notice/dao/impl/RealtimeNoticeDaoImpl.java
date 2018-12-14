package com.arf.notice.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.notice.dao.IRealtimeNoticeDao;
import com.arf.notice.entity.RealtimeNotice;

@Repository("realtimeNoticeDaoImpl")
public class RealtimeNoticeDaoImpl extends BaseDaoImpl<RealtimeNotice, Long> implements IRealtimeNoticeDao{

}
