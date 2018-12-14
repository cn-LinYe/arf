/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.arf.core.Setting;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.ShippingMethodDao;
import com.arf.core.entity.Area;
import com.arf.core.entity.FreightConfig;
import com.arf.core.entity.Receiver;
import com.arf.core.entity.ShippingMethod;
import com.arf.core.service.ShippingMethodService;
import com.arf.core.util.SettingUtils;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 配送方式
 * 
 * @author arf
 * @version 4.0
 */
@Service("shippingMethodServiceImpl")
public class ShippingMethodServiceImpl extends BaseServiceImpl<ShippingMethod, Long> implements ShippingMethodService {

	@Resource(name = "shippingMethodDaoImpl")
	private ShippingMethodDao shippingMethodDao;

	@Override
	protected BaseDao<ShippingMethod, Long> getBaseDao() {
		return shippingMethodDao;
	}

	@Transactional(readOnly = true)
	public BigDecimal calculateFreight(ShippingMethod shippingMethod, Area area, Integer weight) {
		Assert.notNull(shippingMethod);

		Setting setting = SettingUtils.get();
		BigDecimal firstPrice = shippingMethod.getDefaultFirstPrice();
		BigDecimal continuePrice = shippingMethod.getDefaultContinuePrice();
		if (area != null && CollectionUtils.isNotEmpty(shippingMethod.getFreightConfigs())) {
			List<Area> areas = new ArrayList<Area>();
			areas.addAll(area.getParents());
			areas.add(area);
			for (int i = areas.size() - 1; i >= 0; i--) {
				FreightConfig freightConfig = shippingMethod.getFreightConfig(areas.get(i));
				if (freightConfig != null) {
					firstPrice = freightConfig.getFirstPrice();
					continuePrice = freightConfig.getContinuePrice();
					break;
				}
			}
		}
		if (weight == null || weight <= shippingMethod.getFirstWeight() || continuePrice.compareTo(BigDecimal.ZERO) == 0) {
			return setting.setScale(firstPrice);
		} else {
			double contiuneWeightCount = Math.ceil((weight - shippingMethod.getFirstWeight()) / (double) shippingMethod.getContinueWeight());
			return setting.setScale(firstPrice.add(continuePrice.multiply(new BigDecimal(String.valueOf(contiuneWeightCount)))));
		}
	}

	@Transactional(readOnly = true)
	public BigDecimal calculateFreight(ShippingMethod shippingMethod, Receiver receiver, Integer weight) {
		return calculateFreight(shippingMethod, receiver != null ? receiver.getArea() : null, weight);
	}

}