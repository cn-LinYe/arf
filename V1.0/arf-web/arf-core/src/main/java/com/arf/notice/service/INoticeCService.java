package com.arf.notice.service;

import com.arf.notice.dto.NoticeDto;

public interface INoticeCService {
	
	/**获取天气预报
	 * @param userName
	 * @param latitude
	 * @param longitude
	 * @param cityCode
	 * @return
	 */
	public NoticeDto getWeatherInfo(String userName,double latitude,double longitude,String cityCode);
}
