package com.arf.member.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.member.dao.IVipLevelDao;
import com.arf.member.entity.VipLevel;

@Repository("vipLevelDaoImpl")
public class VipLevelDaoImpl extends BaseDaoImpl<VipLevel, Long> implements IVipLevelDao {

}
