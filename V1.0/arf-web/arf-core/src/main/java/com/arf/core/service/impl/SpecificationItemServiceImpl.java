/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.arf.core.entity.SpecificationItem;
import com.arf.core.service.SpecificationItemService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;
import org.apache.commons.collections.functors.UniquePredicate;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Service - 规格项
 * 
 * @author arf
 * @version 4.0
 */
@Service("specificationItemServiceImpl")
public class SpecificationItemServiceImpl implements SpecificationItemService {

	public void filter(List<SpecificationItem> specificationItems) {
		CollectionUtils.filter(specificationItems, new Predicate() {
			public boolean evaluate(Object object) {
				SpecificationItem specificationItem = (SpecificationItem) object;
				if (specificationItem == null || StringUtils.isEmpty(specificationItem.getName())) {
					return false;
				}
				CollectionUtils.filter(specificationItem.getEntries(), new AndPredicate(new UniquePredicate(), new Predicate() {
					private Set<String> valueSet = new HashSet<String>();

					public boolean evaluate(Object object) {
						SpecificationItem.Entry entry = (SpecificationItem.Entry) object;
						return entry != null && entry.getId() != null && StringUtils.isNotEmpty(entry.getValue()) && valueSet.add(entry.getValue()) && entry.getIsSelected() != null;
					}
				}));
				return CollectionUtils.isNotEmpty(specificationItem.getEntries()) && specificationItem.isSelected();
			}
		});
	}

}