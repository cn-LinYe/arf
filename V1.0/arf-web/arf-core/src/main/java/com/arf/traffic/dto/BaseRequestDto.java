package com.arf.traffic.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;

import com.arf.traffic.TrafficInterface;

public class BaseRequestDto implements Serializable{

	/**
	 * 停车场返回数据结构DTO
	 */
	private static final long serialVersionUID = 8077326299389399242L;
	private String parkingId;//停车场编号
	private String pushTime ;//推送时间
	private String dataCount ;//数据条数
	private List<Map<String, Object>> data;//数据具体内容
	
	public BaseRequestDto(){
		this.pushTime=DateFormatUtils.format(new Date(), TrafficInterface.LongPattern);
	}	
	public BaseRequestDto(String parkingId,String dataCount,List<Map<String, Object>> data){
		this.parkingId=parkingId;
		this.dataCount=dataCount;
		this.data=data;
		this.pushTime=DateFormatUtils.format(new Date(), TrafficInterface.LongPattern);
	}
	
	public String getParkingId() {
		return parkingId;
	}
	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}
	public String getPushTime() {
		return pushTime;
	}
	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
	}
	public String getDataCount() {
		return dataCount;
	}
	public void setDataCount(String dataCount) {
		this.dataCount = dataCount;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
}
