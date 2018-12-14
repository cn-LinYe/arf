package com.arf.notice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.arf.core.cache.redis.RedisService;
import com.arf.notice.dto.NoticeDto;
import com.arf.redis.CacheNameDefinition;

public class NoticeSource {
	public SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//设置日期格式
	
	public enum NoticeType{
		DRIP_WASH_NOTICE,//点滴洗实时公告信息
		TEMPORARY_PARKING_NOTICE;//临时停车公告信息
	}
	
	/**创建一个公告
	 * @param userName 用户账号
	 * @param noticeContent 公告内容
	 * @param noticeTime 公告时间
	 * @param noticeType 公告类型
	 * @param redisService redis存储
	 */
	public void createNotice(String userName,String noticeContent,String noticeTime,NoticeType noticeType,RedisService redisService){
		if(noticeType!=null){			
			HashMap<byte[], byte[]> noticeMap=new HashMap<byte[], byte[]>();
			NoticeDto dto=new NoticeDto();
			dto.setNoticeContent(noticeContent);
			if(StringUtils.isBlank(noticeTime)){				
				dto.setNoticeTime(df.format(new Date()));
			}else{
				dto.setNoticeTime(noticeTime);
			}
			String notice =JSONObject.toJSONString(dto);
			String noticekey=noticeType.name();
			noticeMap.put(noticekey.getBytes(), notice.getBytes());			
			redisService.hMSet(String.format(CacheNameDefinition.RealTime_Notice_Info, userName), noticeMap);
			redisService.setExpiration(String.format(CacheNameDefinition.RealTime_Notice_Info, userName), CacheNameDefinition.Default_Expiration);
		}		
	}
}
