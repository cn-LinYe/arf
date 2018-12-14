package com.arf.wechat.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.wechat.dao.WXUserDao;
import com.arf.wechat.entity.WXUser;

@Repository("wxUserDaoImpl")
public class WXUserDaoImpl extends BaseDaoImpl<WXUser, Long> implements WXUserDao {

	@Override
	public WXUser findByOpenId(String openId) {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("openId", Operator.eq, openId));
		List<WXUser> list = this.findList(null, null, filters, null);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	@Override
	public WXUser findByUnionId(String openId) {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("unionid", Operator.eq, openId));
		List<WXUser> list = this.findList(null, null, filters, null);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	
	
}
