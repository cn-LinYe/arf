package com.arf.core.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

public class PropertiesUtils {
	/**
	 * 根据对象列表和对象的某个属性返回属性的List集合
	 * @param <R>
	 * 
	 * @param objList
	 *            对象列表
	 * @param propertyName
	 *            要操作的属性名称
	 * @return
	 * 
	 *         <pre>
	 *  
	 *  指定属性的List集合; 
	 *         </pre>
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T, R> List<R> getPropertyList(List<T> objList, String propertyName,Class<R> clazz) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (objList == null || objList.size() == 0)
			return new ArrayList<R>();
		if (propertyName == null || "".equals(propertyName)) {
			return new ArrayList<R>();
		}
		PropertyUtilsBean p = new PropertyUtilsBean();
		List<R> propList = new LinkedList<R>();
		for (int i = 0; i < objList.size(); i++) {
			T obj = objList.get(i);
			propList.add((R) p.getProperty(obj, propertyName));
		}
		return propList;
	}

	/**
	 * 将List列表中的对象的某个属性封装成一个Map对象，key值是属性名，value值是对象列表中对象属性值的列表
	 * 
	 * @param objList
	 *            对象列表
	 * @param propertyName
	 *            属性名称,可以是一个或者多个
	 * @return 返回封装了属性名称和属性值列表的Map对象，如果参数非法则抛出异常信息
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static <T, R> Map<String, List<R>> getPropertiesMap(List<T> objList,Class<R> clazz, String... propertyName)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (objList == null || objList.size() == 0)
			throw new IllegalArgumentException("No objList specified");
		if (propertyName == null || propertyName.length == 0) {
			throw new IllegalArgumentException(
					"No propertyName specified for bean class '" + objList.get(0).getClass() + "'");
		}
		Map<String, List<R>> maps = new HashMap<String, List<R>>();
		for (int i = 0; i < propertyName.length; i++) {
			maps.put(propertyName[i], getPropertyList(objList, propertyName[i],clazz));
		}
		return maps;
	}
}
