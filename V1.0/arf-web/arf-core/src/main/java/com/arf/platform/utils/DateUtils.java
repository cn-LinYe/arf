package com.arf.platform.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtils {
public static void main(String[] args) {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	System.out.println(df.format(1451699441).toString());
}
	
	/**
	 * 长整型转成Date类型<br/>
	 * 时间戳（秒） —> "yyyy-MM-dd HH:mm:ss"
	 * @param inTime
	 * @return
	 */
	public static Date  longPaseDate(Long inTime){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date arriveTime= null;
		if (inTime == null) {
			return null;
		}
		try {
			 arriveTime = df.parse(df.format(inTime));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return arriveTime;
	}
	
	public static Date  stringPaseDate(String inTime){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date arriveTime= null;
		if (inTime == null) {
			return null;
		}
		try {
			 arriveTime = df.parse(inTime);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return arriveTime;
	}
	public static Date  stringFomatDate(String inTime,String formatter){
		SimpleDateFormat df=null;
		if (StringUtils.isBlank(formatter)) {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}else{
			df = new SimpleDateFormat(formatter);
		}
		Date arriveTime= null;
		if (StringUtils.isBlank(inTime) ) {
			return null;
		}
		try {
			 arriveTime = df.parse(inTime);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return arriveTime;
	}
	
	
	/**
	 * 获取时间戳的字符串形势
	 * @param timestamp 时间戳(秒)
	 * @param formatter 格式（默认"yyyy-MM-dd HH:mm:ss"）
	 * @return
	 */
    public static String getDateStrByTimestamp(long timestamp, String formatter){
    	SimpleDateFormat sdf; 
    	if(!StringUtils.isEmpty(formatter)) {
    		sdf = new SimpleDateFormat(formatter);
    	 }else {
    		 sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	 }
    	Date date = new Date(timestamp * 1000L);
    	try {
    		return sdf.format(date);
		} catch (Exception e) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(date);
		}
    }
    /**
     * 根据传入Date，返回形如"yyyy-MM-dd HH:mm:ss"格式日期字符串
     * @param date
     * @return
     */
    public static String getDateStrByDate(Date date){
    	if(date == null){
    		return null;
    	}
    	String daeStr = null;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	daeStr = sdf.format(date);
    	return daeStr;
    }
    
    /**
     * 把时间date从原来的格式srcFomatStr转换为新的格式destFormatStr
     * @param fomatStr String 时间格式
     * @param date String  时间
     * @return  时间戳
     */
    public static long getTime(String fomatStr, String date) {
        long temp = 0;
        if ((date != null) && !date.equals("")) {
            try {
                SimpleDateFormat srcFormat = new SimpleDateFormat(fomatStr);
                Date dateTemp = srcFormat.parse(date);
                temp = dateTemp.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }
    
	public static boolean isSameDate(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
		return isSameDate;
	}
	
	
	 /**
     * 区分两个日期之间指定字段的差值
     * 
     * @param time1
     *            开始时间
     * @param time2
     *            结束时间
     * @param field
     *            要比较的字段(年，月，日,...)  同jav.util.Calendar类的Field
     * @return 如果time1>time2就反回一个正的差值,如果time1<time2则返回一个负的差值,如果相等，返回0
     */
    public int getFieldDifference(long time1, long time2, int field) {
        if (time1 == time2) {
            return 0;
        } else if (time1 > time2) {
            return -getFieldDifference(time2, time1, field);
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setLenient(false);
        cal1.setTimeInMillis(time1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setLenient(false);
        cal2.setTimeInMillis(time2);
        for (int x = 0; x < Calendar.FIELD_COUNT; x++) {
            if (x > field) {
                cal1.clear(x);
                cal2.clear(x);
            }
        }
        time1 = cal1.getTimeInMillis();
        time2 = cal2.getTimeInMillis();
 
        long ms = 0;
        int min = 0, max = 1;
 
        while (true) {
            cal1.setTimeInMillis(time1);
            cal1.add(field, max);
            ms = cal1.getTimeInMillis();
            if (ms == time2) {
                min = max;
                break;
            } else if (ms > time2) {
                break;
            } else {
                max <<= 1;
            }
        }
 
        while (max > min) {
            cal1.setTimeInMillis(time1);
            int t = (min + max) >>> 1;
            cal1.add(field, t);
            ms = cal1.getTimeInMillis();
            if (ms == time2) {
                min = t;
                break;
            } else if (ms > time2) {
                max = t;
            } else {
                min = t;
            }
        }
        return -min;
    }
    
    /**
	 * 获取指定时间的那天 HH:mm:ss 的时间，如2016-12-20 00:00:00
	 * 
	 * @param date
	 *            h m s
	 * @return
	 */
	public static Date dayDetial(Date date, int h, int m, int s) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, h);
		c.set(Calendar.MINUTE, m);
		c.set(Calendar.SECOND, s);
		return c.getTime();
	}
	
	 /**
		 * 获取两个时间之间相差的秒数
		 * 
		 * @param startDate,enDate
		 * @return
		 */
		public static int getSecond(Date startDate,Date enDate) {
			long start = startDate.getTime();
			long end = enDate.getTime();
			int c = (int)((end - start) / 1000);
			return c;
		}
		 /**
		 * 将天数转换为毫秒
		 * 
		 * @param delayDays
		 * @return
		 */
		public static Long getDate(int delayDays){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DATE, cal.get(Calendar.DATE)-delayDays);
			return cal.getTimeInMillis();
		}
		/**
		 * 将毫秒转换为date 2017/03/31 00:00:00
		 * 
		 * @param delayDays
		 * @return
		 */
		@SuppressWarnings("unused")
		public static Date getStartTime(Long millis){  
	        Calendar todayStart = Calendar.getInstance();
	        if(millis!=null)
	        todayStart.setTimeInMillis(millis);
	        todayStart.set(Calendar.HOUR_OF_DAY, 0);  
	        todayStart.set(Calendar.MINUTE, 0);  
	        todayStart.set(Calendar.SECOND, 0);  
	        todayStart.set(Calendar.MILLISECOND, 0);  
	        return todayStart.getTime();  
	    }  
		/**
		 * 将毫秒转换为date 2017/03/31 23:59:59
		 * 
		 * @param delayDays
		 * @return
		 */
	    @SuppressWarnings("unused")
	    public static Date getEndTime(Long millis){  
	        Calendar todayEnd = Calendar.getInstance();
	        if(millis!=null)
	        todayEnd.setTimeInMillis(millis);
	        todayEnd.set(Calendar.HOUR_OF_DAY, 23);  
	        todayEnd.set(Calendar.MINUTE, 59);  
	        todayEnd.set(Calendar.SECOND, 59);  
	        todayEnd.set(Calendar.MILLISECOND, 999);  
	        return todayEnd.getTime();  
	    }
}
