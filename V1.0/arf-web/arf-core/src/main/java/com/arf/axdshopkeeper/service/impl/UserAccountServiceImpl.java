package com.arf.axdshopkeeper.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.axdshopkeeper.dao.IUserAccountDao;
import com.arf.axdshopkeeper.entity.UserAccount;
import com.arf.axdshopkeeper.service.IUserAccountService;
import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.google.common.collect.Lists;

@Service("userAccountService")
public class UserAccountServiceImpl extends
		BaseServiceImpl<UserAccount, Long> implements
		IUserAccountService {
	
	@Resource(name = "userAccountDao")
	private IUserAccountDao userAccountDao;
	
	@Override
	protected BaseDao<UserAccount, Long> getBaseDao() {
		return userAccountDao;
	}

	@Override
	public UserAccount findShopkeeper(String shopkeeper) {
		List<Filter> filters = Lists.newArrayList();
		filters.add(new Filter("identityType",Operator.eq,UserAccount.IdentityType.SHOPKEEPER));
		filters.add(new Filter("accountType",Operator.eq,UserAccount.AccountType.SELF));
		filters.add(new Filter("userName",Operator.eq,shopkeeper));
		List<UserAccount> list = this.findList(null, filters, null);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public UserAccount findByUserName(String userName) {
		List<Filter> filters = Lists.newArrayList();
		filters.add(new Filter("userName",Operator.eq,userName));
		List<UserAccount> list = this.findList(null, filters, null);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

}
