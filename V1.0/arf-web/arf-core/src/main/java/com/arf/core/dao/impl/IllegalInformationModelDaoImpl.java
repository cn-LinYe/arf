package com.arf.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.IllegalInformationModelDao;
import com.arf.core.entity.IllegalInformationModel;

/**
 * Dao - 违章信息表
 * 
 * @author arf
 * @version 4.0
 */
@Repository("illegalInformationModelDaoImpl")
public class IllegalInformationModelDaoImpl extends BaseDaoImpl<IllegalInformationModel, Long> implements IllegalInformationModelDao {
	
}
