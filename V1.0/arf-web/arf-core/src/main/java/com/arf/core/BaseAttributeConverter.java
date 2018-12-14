/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.AttributeConverter;

import com.arf.core.util.JsonUtils;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.JavaType;

/**
 * 类型转换 - 基类
 * 
 * @author arf
 * @version 4.0
 */
public abstract class BaseAttributeConverter<T> implements AttributeConverter<Object, String> {

	/** 类型 */
	private JavaType javaType;

	public BaseAttributeConverter() {
		Type superClass = getClass().getGenericSuperclass();
		Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
		javaType = JsonUtils.constructType(type);
	}

	public String convertToDatabaseColumn(Object attribute) {
		if (attribute == null) {
			return null;
		}

		return JsonUtils.toJson(attribute);
	}

	public Object convertToEntityAttribute(String dbData) {
		if (StringUtils.isEmpty(dbData)) {
			if (List.class.isAssignableFrom(javaType.getRawClass())) {
				return Collections.EMPTY_LIST;
			} else if (Set.class.isAssignableFrom(javaType.getRawClass())) {
				return Collections.EMPTY_SET;
			} else if (Map.class.isAssignableFrom(javaType.getRawClass())) {
				return Collections.EMPTY_MAP;
			} else {
				return null;
			}
		}

		return JsonUtils.toObject(dbData, javaType);
	}

}