package com.arf.core.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class GetClassAttributes{
	
	private static final boolean IsOutputNull = true;//是否输出NULL属性
	
	/**获取Vo对象所有值
	 * @param clazs Vo对象
	 * @return
	 */
	public static String toString(Object clazs) {
		return toString(clazs,IsOutputNull);
	}
	public static String toString(Object clazs,boolean isOutputNull)
	{
		Map<String, Object> sbmap = new HashMap<String, Object>();
		getParamAndValue(clazs, clazs.getClass(),sbmap, isOutputNull);
		return sbmap.toString();
	}
	private static void getParamAndValue(Object clazs, Class<?> clazz,Map<String, Object> sbmap,boolean isOutputNull) {
		Class<?> sc = clazz.getSuperclass();
		Field[] sfields = sc.getDeclaredFields();
		if (sfields.length > 0) {//校验是否存在继承关系，全部属性输出
			getParamAndValue(clazs,sc,sbmap,isOutputNull);			
		}		
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			try {
				if (null != f.get(clazs) || isOutputNull) {
					sbmap.put(f.getName(), f.get(clazs));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
