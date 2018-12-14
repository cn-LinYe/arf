package com.arf.platform.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.platform.dao.ISMemberAccountDao;
import com.arf.platform.entity.SMemberAccount;

@Repository("sMemberAccountDaoImpl")
public class SMemberAccountDaoImpl extends BaseDaoImpl<SMemberAccount, Long> implements ISMemberAccountDao {

	@Override
	public SMemberAccount findByUserName(String userName) {
		String hql = "from com.arf.platform.entity.SMemberAccount where userName = :userName";
		List<SMemberAccount> list = entityManager.createQuery(hql,SMemberAccount.class).setParameter("userName", userName).getResultList();
		if(list != null && list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public SMemberAccount finByAccountNumber(String accountNumber) {
		String hql = "from com.arf.platform.entity.SMemberAccount where accountNumber = :accountNumber";
		List<SMemberAccount> list = entityManager.createQuery(hql,SMemberAccount.class).setParameter("accountNumber", accountNumber).getResultList();
		if(list != null && list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public SMemberAccount findByUserNameUserType(String userName, Byte type) {
		String hql = "from com.arf.platform.entity.SMemberAccount where userName = :userName and type = :type";
		List<SMemberAccount> list = entityManager.createQuery(hql,SMemberAccount.class).setParameter("userName", userName).setParameter("type", type).getResultList();
		if(list != null && list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public SMemberAccount findByUserNameUserTypeStatus(String userName,Byte type, Byte status) {
		String hql = "from com.arf.platform.entity.SMemberAccount where userName = :userName and type = :type and status = :status";
		List<SMemberAccount> list = entityManager.createQuery(hql,SMemberAccount.class)
				.setParameter("userName", userName)
				.setParameter("type", type)
				.setParameter("status", status)
				.getResultList();
		if(list != null && list.size() != 0){
			return list.get(0);
		}
		return null;
	}


}
