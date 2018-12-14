package com.arf.axd_b2b.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.axd_b2b.dao.IAxdUserIdDao;
import com.arf.axd_b2b.entity.AxdUserId;
import com.arf.axd_b2b.service.IAxdUserIdService;
import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.google.common.collect.Lists;

@Service("axdUserIdService")
public class AxdUserIdServiceImpl extends
BaseServiceImpl<AxdUserId, Long> implements IAxdUserIdService{

	@Resource(name = "axdUserIdDao")
	private IAxdUserIdDao axdUserIdDao;
	@Override
	protected BaseDao<AxdUserId, Long> getBaseDao() {
		return axdUserIdDao;
	}
	@Override
	public AxdUserId findByUseridAndBusinessnum(String axdUserID, String businessNum) {
		List<Filter> filters = Lists.newArrayList();
		filters.add(new Filter("axdUserId",Operator.eq,axdUserID));
		filters.add(new Filter("businessNum",Operator.eq,businessNum));
		List<AxdUserId> axdUserIds = this.findList(null, filters,null);
		if(CollectionUtils.isNotEmpty(axdUserIds)){
			return axdUserIds.get(0);
		}else {
			return null;
		}
	}
	
	@Override
	public AxdUserId findByUserNameAndBusinessnum(String userName, String businessNum) {
		List<Filter> filters = Lists.newArrayList();
		filters.add(new Filter("axdUserName",Operator.eq,userName));
		filters.add(new Filter("businessNum",Operator.eq,businessNum));
		List<AxdUserId> axdUserIds = this.findList(null, filters,null);
		if(CollectionUtils.isNotEmpty(axdUserIds)){
			return axdUserIds.get(0);
		}else {
			return null;
		}
	}
}
