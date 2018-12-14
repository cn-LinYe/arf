package com.arf.plugin.dto;

import java.io.Serializable;

public class WeatherInfo implements Serializable{
	/**
	 * 实现序列号接口 
	 */
	private static final long serialVersionUID = -6178048030884756859L;
	private int resCode;//返回码 0 为成功
	private String resError; //错误返回码
	private String cityInfo;// 城市名称
	private String quality;//空气质量 优 良
	private int aqi;//空气质量指数 34
	private String weather;//实时天气多云
	private int temperature;//实时当前温度	
	private String dayWeather;//今天天气
	private String dayWeatherPic;//天气对应的图片
	private String nightWeather;//晚上天气
	private String xqDesc;//心情指数
	private String washCarDesc;//洗车指数
	private String clothesDesc;//穿衣指数
	private String dayWindPower;//白天风力: "3-4级 5.5~7.9m/s",
	private String dayWindDirection;//白天风 东北风
	private int nightAirTemperature;//晚上温度℃
	private int dayAirTemperature;//白天温度℃
	
	public int getResCode() {
		return resCode;
	}
	public void setResCode(int resCode) {
		this.resCode = resCode;
	}
	public String getResError() {
		return resError;
	}
	public void setResError(String resError) {
		this.resError = resError;
	}
	public String getCityInfo() {
		return cityInfo;
	}
	public void setCityInfo(String cityInfo) {
		this.cityInfo = cityInfo;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public int getAqi() {
		return aqi;
	}
	public void setAqi(int aqi) {
		this.aqi = aqi;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public String getDayWeather() {
		return dayWeather;
	}
	public void setDayWeather(String dayWeather) {
		this.dayWeather = dayWeather;
	}
	public String getNightWeather() {
		return nightWeather;
	}
	public void setNightWeather(String nightWeather) {
		this.nightWeather = nightWeather;
	}
	public String getXqDesc() {
		return xqDesc;
	}
	public void setXqDesc(String xqDesc) {
		this.xqDesc = xqDesc;
	}
	public String getWashCarDesc() {
		return washCarDesc;
	}
	public void setWashCarDesc(String washCarDesc) {
		this.washCarDesc = washCarDesc;
	}
	public String getClothesDesc() {
		return clothesDesc;
	}
	public void setClothesDesc(String clothesDesc) {
		this.clothesDesc = clothesDesc;
	}
	public String getDayWindPower() {
		return dayWindPower;
	}
	public void setDayWindPower(String dayWindPower) {
		this.dayWindPower = dayWindPower;
	}
	public String getDayWindDirection() {
		return dayWindDirection;
	}
	public void setDayWindDirection(String dayWindDirection) {
		this.dayWindDirection = dayWindDirection;
	}
	public int getNightAirTemperature() {
		return nightAirTemperature;
	}
	public void setNightAirTemperature(int nightAirTemperature) {
		this.nightAirTemperature = nightAirTemperature;
	}
	public int getDayAirTemperature() {
		return dayAirTemperature;
	}
	public void setDayAirTemperature(int dayAirTemperature) {
		this.dayAirTemperature = dayAirTemperature;
	}
	public String getDayWeatherPic() {
		return dayWeatherPic;
	}
	public void setDayWeatherPic(String dayWeatherPic) {
		this.dayWeatherPic = dayWeatherPic;
	}
	
}
