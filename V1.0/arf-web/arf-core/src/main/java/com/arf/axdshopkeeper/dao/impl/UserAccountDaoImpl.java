package com.arf.axdshopkeeper.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.axdshopkeeper.dao.IUserAccountDao;
import com.arf.axdshopkeeper.entity.UserAccount;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("userAccountDao")
public class UserAccountDaoImpl extends BaseDaoImpl<UserAccount, Long> implements IUserAccountDao{

}
