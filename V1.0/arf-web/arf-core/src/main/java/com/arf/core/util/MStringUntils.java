package com.arf.core.util;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;

/**
 * 字符串工具类
 * 
 * @author no
 *
 */
public class MStringUntils {

	/**
	 * 判断是否为空字符串或者null
	 * 
	 * @param str
	 * @return ""或null 返回true. 否:返回false;
	 */
	
	public static boolean isEmptyOrNull(String str) {
		if (str == null)
			return true;
		if ("".equals(str))
			return true;
		return false;
	}
	//判断是否是数组
	public static boolean isNumber(String str)
	{
		if(str==null)return false;
	    Pattern pattern=Pattern.compile("[0-9]*");
	    Matcher match=pattern.matcher(str);
	    return match.matches();
	}
	
	/**
	 * 判断是否为非空字符串
	 * 
	 * @param str
	 * @return ""或null 返回false. 否:返回true;
	 */
	
	public static boolean isNotEmptyOrNull(String str) {
		return isEmptyOrNull(str) == true ? false : true;
	}
	
	/**
	 * 判断是否存在空字符串或者null
	 * 
	 * @param strs
	 * @return 存在空字符串或者null : true 否:false
	 */
	public static boolean hasEmptyOrNullWithOr(String... strs) {
		if(null == strs || strs.length <= 0)
			return true;
		for (String string : strs) {
			if (isEmptyOrNull(string))
				return true;
		}
		return false;
	}
	
	/**
	 * 对象是否为空(null)
	 * @param obj
	 * @return true:空对象  false:非空对象
	 */
	public static boolean isNullObject(Object obj){
		return null == obj ? true:false;
	}
	
	/**
	 * 是否存在空(null)对象
	 * @param objs
	 * @return true:存在空对象 false:不存在空对象
	 */
	public static boolean hasNullObjecWithOr(Object...objs){
		if(null == objs || objs.length <= 0)
			return true;
		for (Object object : objs) {
			if(null == object)
				return true;
		}
		return false;
	}
	
	/**
	 * 将一个集合每个元素添加上joinner并以java.lang.Strig返回
	 * @param collection
	 * @param joinner 要分隔的字符
	 * @return
	 */
	public static String join(Collection<? extends Object> collection,String joinner){
		if(CollectionUtils.isEmpty(collection)){
			return null;
		}
		int index = 0;
		String res = "";
		for(Object obj : collection){
			index++;
			if(obj == null){
				continue;
			}
			if(index == 1){
				res += obj.toString();
			}else{
				res += joinner + obj.toString();
			}
		}
		return res;
	}
	/**
	 * 获取当前时间是星期几
	 * @param date
	 * @return
	 */
	 public static String getWeekOfDate(Date date) {
	        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
	        if (w < 0){
	        	 w = 0;
	        }
	        return weekDays[w];
	    }
}
