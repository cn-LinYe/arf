package com.arf.axdshopkeeper.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.axdshopkeeper.dao.IRewardRecordDao;
import com.arf.axdshopkeeper.entity.RewardRecord;
import com.arf.axdshopkeeper.entity.ShopkeeperLevel;
import com.arf.axdshopkeeper.entity.UserAccount;
import com.arf.axdshopkeeper.entity.RewardRecord.RewardType;
import com.arf.axdshopkeeper.entity.UserAccount.IdentityType;
import com.arf.axdshopkeeper.service.IRewardRecordService;
import com.arf.base.PageResult;
import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.google.common.collect.Lists;

@Service("rewardRecordServiceImpl")
public class RewardRecordServiceImpl extends
		BaseServiceImpl<RewardRecord, Long> implements IRewardRecordService {

	@Resource(name = "rewardRecordDaoImpl")
	private IRewardRecordDao rewardRecordDaoImpl;
	
	@Override
	protected BaseDao<RewardRecord, Long> getBaseDao() {
		return rewardRecordDaoImpl;
	}

	@Override
	public PageResult<RewardRecord> findByCondition(Integer pageSize, Integer pageNo,
			String userName) {
		return rewardRecordDaoImpl.findByCondition(pageSize,pageNo,userName);
	}

	@Override
	public List<RewardRecord> findByUsernameIdentityTypeRewardType(
			String userName, IdentityType identityType,RewardType rewardType) {
		return rewardRecordDaoImpl.findByUsernameIdentityTypeRewardType(userName,identityType,rewardType);
	}

	@Override
	public RewardRecord findByInviteId(Long inviteId) {
		List<Filter> filters = Lists.newArrayList();
		filters.add(new Filter("inviteId",Operator.eq,inviteId));
		List<RewardRecord> list = this.findList(null, filters, null);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public List<RewardRecord> findByUsername(String userName){
		List<Filter> filters = Lists.newArrayList();
		filters.add(new Filter("userName",Operator.eq,userName));
		List<RewardRecord> list = this.findList(null,filters,null);
		if(CollectionUtils.isNotEmpty(list)){
			return list;
		}else{
			return null;
		}
	}
	
}
