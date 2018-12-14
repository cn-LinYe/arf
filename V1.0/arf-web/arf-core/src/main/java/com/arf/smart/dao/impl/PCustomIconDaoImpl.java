package com.arf.smart.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.smart.dao.PCustomIconDao;
import com.arf.smart.entity.PCustomIcon;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("pcustomIconDao")
public class PCustomIconDaoImpl extends BaseDaoImpl<PCustomIcon, Long> implements PCustomIconDao{

}
