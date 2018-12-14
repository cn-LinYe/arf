package com.arf.axd_b2b.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.axd_b2b.dao.IAxdUserIdDao;
import com.arf.axd_b2b.entity.AxdUserId;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("axdUserIdDao")
public class AxdUserIdDaoImpl extends BaseDaoImpl<AxdUserId, Long>
implements IAxdUserIdDao {

}
