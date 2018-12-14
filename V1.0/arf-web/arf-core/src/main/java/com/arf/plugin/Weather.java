package com.arf.plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.entity.Area;
import com.arf.core.service.AreaService;
import com.arf.plugin.dto.WeatherInfo;
import com.arf.redis.CacheNameDefinition;

public class Weather {
	public static final String APPCODE = "APPCODE ceb7075f7a9b4026bd4de21e3f1df289";
	public static final String HOST = "http://ali-weather.showapi.com";
	public static final String PATH = "/area-to-weather";
	public static final String METHOD = "GET";
	public static final Logger log = LoggerFactory.getLogger(Weather.class);
	
	/**统一暴露接口
	 * @param com 
	 * @param 
	 * @return
	 */
	public static WeatherInfo getWeatherInfo(String cityCode,RedisService redisService,AreaService areaService){
		//获取缓存中天气信息
		String weatherInfo=redisService.hGet(String.format(CacheNameDefinition.RealTime_Weather_Info, cityCode), cityCode);
		if(StringUtils.isNotBlank(weatherInfo)){
			WeatherInfo dto=JSON.parseObject(weatherInfo, WeatherInfo.class);
			return dto;
		}
		Area areaEntity=areaService.findByAreaNo(cityCode);//读取数据库
		String area="";
		if(areaEntity!=null){//获取区域的名称
			area=areaEntity.getFullName();
		}
	   return getWeather(area, cityCode,redisService);
	}
	
	public static WeatherInfo getWeather(String area,String cityCode,RedisService redisService) {
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE
		// 83359fd73fe94948385f570e3c139105
		headers.put("Authorization",APPCODE);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("area", area);
		querys.put("areaid", "");
		querys.put("need3HourForcast", "0");
		querys.put("needAlarm", "1");
		querys.put("needHourData", "0");
		querys.put("needIndex", "1");
		querys.put("needMoreDay", "0");
		ExecutorService pool = Executors.newFixedThreadPool(1);
		try {
			// 推送给商家信息
			Callable<String> myCallable = new MyCallable(HOST, PATH, METHOD, headers, querys);
			Future<String> fback = pool.submit(myCallable);
			// 从Future对象上获取任务的返回值
			String weatherInfo = fback.get();
			return getWeatherInfo(cityCode, weatherInfo, redisService);
		} catch (Exception e) {
			log.error("天气统一接口异常," + HOST + "异常信息如下:", e);
		} finally {
			// 关闭线程池
			pool.shutdown();
		}
		return null;
	}
	
	private static WeatherInfo getWeatherInfo(String cityCode,String weatherInfo,RedisService redisService){
		WeatherInfo info=new WeatherInfo();
		if(weatherInfo!=null){
			JSONObject obj = JSONObject.parseObject(weatherInfo);
			String showapi_res_code=obj.getString("showapi_res_code");
			if(showapi_res_code!=null&&"0".equals(showapi_res_code)){				
				info.setResCode(0);
			}
			info.setResError(obj.getString("showapi_res_error"));
			JSONObject showapiResBody=obj.getJSONObject("showapi_res_body");
			if(showapiResBody!=null){
				JSONObject apicityInfo=showapiResBody.getJSONObject("cityInfo");//获取到区域信息
				if(apicityInfo!=null){
					info.setCityInfo(apicityInfo.getString("c5"));// 城市名称
				}
				JSONObject apinow=showapiResBody.getJSONObject("now");//获取当前天气情况
				if(apinow!=null){
					JSONObject apiAqiDetail=apinow.getJSONObject("aqiDetail");
					if(apiAqiDetail!=null){
						info.setQuality(apiAqiDetail.getString("quality"));//空气质量 优 良
						info.setAqi(apiAqiDetail.getInteger("aqi"));//空气质量指数 34
					}						
					info.setWeather(apinow.getString("weather"));//实时天气多云
					info.setTemperature(apinow.getInteger("temperature"));//实时当前温度
				}
				JSONObject apif1=showapiResBody.getJSONObject("f1");//获取当天天气情况
				if(apif1!=null){
					info.setDayWeather(apif1.getString("day_weather"));//今天天气
					info.setNightWeather(apif1.getString("night_weather"));//晚上天气					
					JSONObject apiIndex=apif1.getJSONObject("index");//获取指数信息
					if(apiIndex!=null){				
						JSONObject apiclothes =apiIndex.getJSONObject("clothes");
						if(apiclothes!=null){
							info.setClothesDesc(apiclothes.getString("desc"));//穿衣指数
						}
						
						JSONObject apixq =apiIndex.getJSONObject("xq");
						if(apixq!=null){
							info.setXqDesc(apixq.getString("desc"));//心情指数
						}
						
						JSONObject apiWashCar =apiIndex.getJSONObject("wash_car");
						if(apiWashCar!=null){
							info.setWashCarDesc(apiWashCar.getString("desc"));//洗车指数
						}
					}
					info.setDayAirTemperature(apif1.getInteger("day_air_temperature"));//白天温度℃
					info.setNightAirTemperature(apif1.getInteger("night_air_temperature"));//晚上温度℃
					info.setDayWindDirection(apif1.getString("day_wind_power"));//白天风力: "3-4级 5.5~7.9m/s",
					info.setDayWindDirection(apif1.getString("day_wind_direction"));//白天风 东北风
				}
			}
			HashMap<byte[], byte[]> weatherInMap=new HashMap<byte[], byte[]>();
			String weatherIninfo =JSONObject.toJSONString(info);
			weatherInMap.put(cityCode.getBytes(), weatherIninfo.getBytes());
			redisService.hMSet(String.format(CacheNameDefinition.RealTime_Weather_Info, cityCode), weatherInMap);
			redisService.setExpiration(String.format(CacheNameDefinition.RealTime_Weather_Info, cityCode), CacheNameDefinition.RealTime_Weather_Info_Expiration);
		}
		return info;		
	}
}
