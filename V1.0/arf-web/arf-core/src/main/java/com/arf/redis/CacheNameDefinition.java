package com.arf.redis;

public interface CacheNameDefinition {
	
	public static final long Default_Expiration=60*30L;//默认缓存超时时间30分钟
	public static final String Community_DB_Redis="CommunityDBtoRedis:Community%s";//小区信息缓存key
	public final static String RedisBusinessToCommunityKey="BusinessDBToCommunityRedis:BusinessToCommunity%s";//商户小区关联表信息缓存至rediskey
	public static final String Member_DB_Redis="MemberDBtoRedis:Member%s"; //用户信息缓存key
	public static final String PBusiness_DB_Redis="PBusinessDBtoRedis:PBusiness%s"; //商户信息信息缓存key
	
	public static final String RealTime_Notice_Info="RealTime_Notice_Info:Notice%s";//用户信息通知
	public static final long RealTime_Notice_Info_Expiration=60*10L;//默认缓存超时时间10分钟
	
	public static final String RealTime_Weather_Info="RealTime_Weather_Info:CityCode%s";//区域性天气信息
	public static final long RealTime_Weather_Info_Expiration=60*60*1L;//默认缓存超时时间1个小时
	
	public static final String Express_Delivery_Info="Express_Delivery_Info:Delivery%s";//获取快递信息redis
	public static final long Express_Delivery_Info_Expiration=60*60*1L;//默认缓存超时时间1个小时
	
	public static final String Violation_Inquire_Info="Violation_Inquire_Info:License%s";//违章查询信息redis
	
	public static final String Parking_Retention_Time="Parking_Retention_Time:Community%s";//小区停车滞留时间
	
}
