package com.arf.notice.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.arf.core.cache.redis.RedisService;
import com.arf.core.service.AreaService;
import com.arf.core.service.SysconfigService;
import com.arf.notice.dto.NoticeDto;
import com.arf.notice.service.INoticeCService;
import com.arf.plugin.Weather;
import com.arf.plugin.dto.WeatherInfo;
import com.arf.redis.CacheNameDefinition;

@Service("noticeServiceCoreImpl")
public class NoticeCServiceImpl implements INoticeCService{

	private String DEFAULT_NOTICE="DEFAULT_NOTICE_MESSAGE";//获取默认的实时公共
	
	private String REPLACE_CHARACTER="\u6682\u7f3a";//替换字符串
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	
	@Resource(name = "sysconfigServiceImpl")
	private SysconfigService sysconfigServiceImpl;
	
	public NoticeDto getWeatherInfo(String userName,double latitude,double longitude,String cityCode){
		NoticeDto dto=new NoticeDto();
		if(StringUtils.isNotBlank(cityCode)){
			cityCode=cityCode.substring(0,cityCode.length()-2).concat("00");//处理地区code
			WeatherInfo weatherInfo= null; //Weather.getWeatherInfo(cityCode, redisService, areaService);
			if(weatherInfo!=null){
				int resCode=weatherInfo.getResCode();//返回码
				if(resCode==0){//返回数据OK
					String quality=weatherInfo.getQuality();//空气质量
					String cityInfo=weatherInfo.getCityInfo();//区域
					String weather=weatherInfo.getWeather();//实时天气多云		
					if(StringUtils.isBlank(cityInfo)||StringUtils.isBlank(weather)){
						redisService.del(String.format(CacheNameDefinition.RealTime_Weather_Info, cityCode));
						return getDefaultNotice();
					}
					int temperature= weatherInfo.getTemperature();//当前温度		
					String xqDesc=weatherInfo.getXqDesc();//心情指数
					String washCarDesc=weatherInfo.getWashCarDesc();//洗车指数
					
					int nightAirTemperature=weatherInfo.getNightAirTemperature();//晚上温度℃
					int dayAirTemperature=weatherInfo.getDayAirTemperature();//白天温度℃
					StringBuffer buffer=new StringBuffer();
					buffer.append(cityInfo).append(":");
					buffer.append(weather).append(nightAirTemperature).append("-").append(dayAirTemperature).append("℃");
					buffer.append("实时温度：").append(temperature).append("℃");
					buffer.append("空气质量：").append(quality).append("。");
					if(REPLACE_CHARACTER.equals(xqDesc)){//如果有心情添加心情指数
						
					}else{
						buffer.append(xqDesc);						
					}
					if(REPLACE_CHARACTER.equals(washCarDesc)){//如果有洗车指数添加洗车指数
						
					}else{
						buffer.append("洗车指数【").append(washCarDesc).append("】");						
					}
					String clothesDesc=weatherInfo.getClothesDesc();//穿衣指数
					if(REPLACE_CHARACTER.equals(clothesDesc)){//如果有洗车指数添加洗车指数
						
					}else{
						buffer.append(clothesDesc);						
					}
					dto.setNoticeContent(buffer.toString());
					return dto;
				}
			}	
		}
		return getDefaultNotice();			
	}
	
	public NoticeDto getDefaultNotice(){		
		String defaultNotice= sysconfigServiceImpl.getValue(DEFAULT_NOTICE);
		NoticeDto dto=new NoticeDto();
		if(StringUtils.isBlank(defaultNotice)){
			dto.setNoticeContent("人生难免有迷途，安心点是你人生永远的灯塔");
		}else{			
			dto.setNoticeContent(defaultNotice);
		}
		return dto;
	}
}
