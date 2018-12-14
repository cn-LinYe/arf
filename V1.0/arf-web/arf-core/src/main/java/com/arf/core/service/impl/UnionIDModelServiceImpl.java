/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import com.arf.core.dao.UnionIDModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.UnionIDModel;
import com.arf.core.service.UnionIDModelService;


/**
 * Service - UnionID表
 * 
 * @author arf
 * @version 4.0
 */
@Service("unionIDModelServiceImpl")
public class UnionIDModelServiceImpl extends BaseServiceImpl<UnionIDModel, Long> implements UnionIDModelService {

	@Resource(name = "unionIDModelDaoImpl")
	private UnionIDModelDao unionIDModelDao;

	@Override
	protected BaseDao<UnionIDModel, Long> getBaseDao() {
		return unionIDModelDao;
	}

	@Override
	public List<UnionIDModel> selectAll() {
		return unionIDModelDao.selectAll();
	}

	@Override
	public void executeBatchBySql(List<UnionIDModel> wxUserUpdates, List<UnionIDModel> wxUserInserts) {
		unionIDModelDao.executeBatchBySql(wxUserUpdates, wxUserInserts);
	}

	@Override
	public List<UnionIDModel> sellectByUnionId(String unionID) {
		return unionIDModelDao.sellectByUnionId(unionID);
	}

	/**
     * 将最新的openid一起插入到数据库中
     * @param ids
     */
    public void insertOpenids(String ids[]){
        unionIDModelDao.insertOpenids(ids);
    }
    /**
     * 获取所有unionId为空的记录
     * @return
     */
    public List<UnionIDModel> getNoUnionID(){
        return unionIDModelDao.getNoUnionID();
    }
    
    /**
     * 批量更新unionID
     * @param list
     */
    public void updateListUnionID(List<UnionIDModel> list){
        unionIDModelDao.updateListUnionID(list);
    }

	@Override
	public UnionIDModel selectByOpenId(String openid) {
		return unionIDModelDao.selectByOpenId(openid);
	}
}