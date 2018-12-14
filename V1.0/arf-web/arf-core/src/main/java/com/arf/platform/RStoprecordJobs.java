package com.arf.platform;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.arf.core.service.SmsService;
import com.arf.platform.service.PParkingcarService;
import com.arf.platform.service.RStoprecordService;

public class RStoprecordJobs {
	
	@Resource(name = "RStoprecordServiceImpl")
	private RStoprecordService stoprecordService;
	
	@Resource(name = "PParkingcarServiceImpl")
	private PParkingcarService parkingcarService;
	
	private String tableNamePrefix = "r_stoprecord_";
	
	private Logger log = LoggerFactory.getLogger(getClass());
	public static String Log_Notice_User = "";
	
	@Resource(name = "smsServiceImpl")
	private SmsService smsService;
	
	/**
	 * 清理掉p_parkingcar表中的脏数据
	 */
	public void clearDirtyParkingcar(){
		long efftct = parkingcarService.clearDirtyParkingcar(7);
		log.info("☆☆☆☆☆☆☆☆☆p_parkingcar表脏数据清理完成,清理掉p_parkingcar表中的脏数据共" + efftct + "条");
	}

	/**
	 * 定时任务-r_stoprecord表数据分表操作
	 * 将每个月的数据迁移到独立对应的分表中
	 * 分表表名格式:
	 * r_stoprecord_201612
	 */
	public void migrateRStoprecords(){
		Date now = new Date();
		String month = DateFormatUtils.format(now, "yyyy-MM");//当前月份
		int pageSize = 10000;
		
		//查询当前r_stoprecord有几个月份的数据
		List<String> months = stoprecordService.findMonthsInDb();
		months.remove(month);//当前月份的不做迁移
		String logStr = "数据库中r_stoprecord共存在" + JSON.toJSONString(months)+"的数据需要迁移;";
		log.info(logStr);
		for(String mon : months){
			long timeStart = System.currentTimeMillis();
			String createStart = mon + "-01" + " 00:00:00";
			String createEnd = DateFormatUtils.format(getLastDayOfMonth(mon), "yyyy-MM-dd") + " 23:59:59";
			String subTableName = tableNamePrefix + mon.replaceAll("-", "");
			int i = stoprecordService.migrateRStoprecords(subTableName, createStart, createEnd, pageSize);
			long timeEnd = System.currentTimeMillis();
			String logStr_ = mon+"共迁移"+i+"条数据,共耗时-->" + (timeEnd - timeStart) + "ms";
			log.info(logStr_);
			logStr = logStr + logStr_ + ";";
		}
		
		try {
			if(StringUtils.isNotBlank(RStoprecordJobs.Log_Notice_User)){
				smsService.send(Log_Notice_User, logStr);
			}
		} catch (Exception e) {
		}
	}
	
	public static Date getLastDayOfMonth(String yyyy_MM) {  
	        Calendar cDay1 = Calendar.getInstance();  
	        try {
				cDay1.setTime(DateUtils.parseDate(yyyy_MM, new String[]{"yyyy-MM"}));
			} catch (ParseException e) {
				e.printStackTrace();
			}  
	        final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);  
	        Date lastDate = cDay1.getTime();  
	        lastDate.setDate(lastDay); 
	        return lastDate;  
	}
}
