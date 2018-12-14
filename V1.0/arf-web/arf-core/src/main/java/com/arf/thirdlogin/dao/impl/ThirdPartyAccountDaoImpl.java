package com.arf.thirdlogin.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.thirdlogin.dao.ThirdPartyAccountDao;
import com.arf.thirdlogin.entity.ThirdPartyAccount;

@Repository("thirdPartyAccountDao")
public class ThirdPartyAccountDaoImpl extends BaseDaoImpl<ThirdPartyAccount, Long> implements ThirdPartyAccountDao{

	@Override
	public ThirdPartyAccount findByOpenidOrUid(String qqOpenid, String wxOpenid, String uid, String userName) {
		StringBuffer sql = new StringBuffer("from ThirdPartyAccount where 1=1");
		if(StringUtils.isNotBlank(qqOpenid)){
			sql.append(" and qqOpenId =:qqOpenid");
		}
		if(StringUtils.isNotBlank(wxOpenid)){
			sql.append(" and wxOpenId =:wxOpenid");
		}
		if(StringUtils.isNotBlank(uid)){
			sql.append(" and weiboUid =:uid");
		}
		if(StringUtils.isNotBlank(userName)){
			sql.append(" and userName =:userName");
		}
		TypedQuery<ThirdPartyAccount> query =entityManager.createQuery(sql.toString(),ThirdPartyAccount.class);
		if(StringUtils.isNotBlank(qqOpenid)){
			query.setParameter("qqOpenid", qqOpenid);
		}
		if(StringUtils.isNotBlank(wxOpenid)){
			query.setParameter("wxOpenid", wxOpenid);
		}
		if(StringUtils.isNotBlank(uid)){
			query.setParameter("uid", uid);
		}
		if(StringUtils.isNotBlank(userName)){
			query.setParameter("userName", userName);
		}
		List<ThirdPartyAccount> recordsList = query.getResultList();
		if(recordsList.size()>0){
			return recordsList.get(0);
		}else{
			return null;
		}
	}

}
