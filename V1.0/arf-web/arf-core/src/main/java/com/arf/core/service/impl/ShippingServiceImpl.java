/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.arf.core.Setting;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.ShippingDao;
import com.arf.core.dao.SnDao;
import com.arf.core.entity.Shipping;
import com.arf.core.entity.Sn;
import com.arf.core.service.ShippingService;
import com.arf.core.util.JsonUtils;
import com.arf.core.util.SettingUtils;
import com.arf.core.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Service - 发货单
 * 
 * @author arf
 * @version 4.0
 */
@Service("shippingServiceImpl")
public class ShippingServiceImpl extends BaseServiceImpl<Shipping, Long> implements ShippingService {

	@Resource(name = "shippingDaoImpl")
	private ShippingDao shippingDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;

	@Override
	protected BaseDao<Shipping, Long> getBaseDao() {
		return shippingDao;
	}

	@Transactional(readOnly = true)
	public Shipping findBySn(String sn) {
		return shippingDao.findBySn(sn);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Cacheable("shipping")
	public List<Map<String, String>> getTransitSteps(Shipping shipping) {
		Assert.notNull(shipping);

		Setting setting = SettingUtils.get();
		if (StringUtils.isEmpty(setting.getKuaidi100Key()) || StringUtils.isEmpty(shipping.getDeliveryCorpCode()) || StringUtils.isEmpty(shipping.getTrackingNo())) {
			return Collections.emptyList();
		}
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("id", setting.getKuaidi100Key());
		parameterMap.put("com", shipping.getDeliveryCorpCode());
		parameterMap.put("nu", shipping.getTrackingNo());
		parameterMap.put("show", "0");
		parameterMap.put("muti", "1");
		parameterMap.put("order", "asc");
		String content = WebUtils.get("http://api.kuaidi100.com/api", parameterMap);
		Map<String, Object> data = JsonUtils.toObject(content, new TypeReference<Map<String, Object>>() {
		});
		if (!StringUtils.equals(String.valueOf(data.get("status")), "1")) {
			return Collections.emptyList();
		}
		return (List<Map<String, String>>) data.get("data");
	}

	@Override
	@Transactional
	public Shipping save(Shipping shipping) {
		Assert.notNull(shipping);

		shipping.setSn(snDao.generate(Sn.Type.shipping));

		return super.save(shipping);
	}

}