package com.arf.axdshopkeeper.service;

import java.util.List;

import com.arf.axdshopkeeper.entity.RewardRecord;
import com.arf.axdshopkeeper.entity.RewardRecord.RewardType;
import com.arf.axdshopkeeper.entity.UserAccount.IdentityType;
import com.arf.base.PageResult;
import com.arf.core.service.BaseService;

public interface IRewardRecordService extends BaseService<RewardRecord, Long> {

	PageResult<RewardRecord> findByCondition(Integer pageSize, Integer pageNo,
			String userName);

	List<RewardRecord> findByUsernameIdentityTypeRewardType(String userName,
			IdentityType identityType, RewardType rewardType);

	RewardRecord findByInviteId(Long inviteId);
	
	List<RewardRecord> findByUsername(String userName);

}
