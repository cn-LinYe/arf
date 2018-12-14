/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.util.List;

import com.arf.core.entity.ParameterValue;
import com.arf.core.service.ParameterValueService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;
import org.apache.commons.collections.functors.UniquePredicate;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Service - 参数值
 * 
 * @author arf
 * @version 4.0
 */
@Service("parameterValueServiceImpl")
public class ParameterValueServiceImpl implements ParameterValueService {

	public void filter(List<ParameterValue> parameterValues) {
		CollectionUtils.filter(parameterValues, new Predicate() {
			public boolean evaluate(Object object) {
				ParameterValue parameterValue = (ParameterValue) object;
				if (parameterValue == null || StringUtils.isEmpty(parameterValue.getGroup())) {
					return false;
				}
				CollectionUtils.filter(parameterValue.getEntries(), new AndPredicate(new UniquePredicate(), new Predicate() {
					public boolean evaluate(Object object) {
						ParameterValue.Entry entry = (ParameterValue.Entry) object;
						return entry != null && StringUtils.isNotEmpty(entry.getName()) && StringUtils.isNotEmpty(entry.getValue());
					}
				}));
				return CollectionUtils.isNotEmpty(parameterValue.getEntries());
			}
		});
	}

}