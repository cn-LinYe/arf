package com.arf.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentTime {
	

	public static long getTime() {
		long time = 0;
		try {
			// 中国人的标准格式
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// Date转换成String
			String m = df.format(new Date());
			// String转Date
			Date d2 = df.parse(m);
			System.out.println(d2.getTime());
			// Date转long
			time = d2.getTime();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	
}
