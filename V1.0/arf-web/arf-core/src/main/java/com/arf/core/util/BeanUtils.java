package com.arf.core.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BeanUtils {
	private BeanUtils(){}
	
	/**
	 * 将一个java bean转为Map(HashMap),该方法返回的Map结果为线程不安全的Map,
	 * 如需要在并发条件下使用请转为java.util.concurrent.ConcurrentHashMap<K, V>
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> bean2Map(Serializable bean){
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.setTimeZone(TimeZone.getDefault());
		Map<String,Object> map = null;
		try {
			map = JSON.parseObject(objMapper.writeValueAsString(bean), Map.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		if(map == null){
			map = new HashMap<String,Object>();
		}
		return map;
	}
	
}
