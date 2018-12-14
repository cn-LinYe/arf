package com.arf.platform.vo;

import java.util.List;

public class ParkingStatusListVo extends RequestDataVo{

	private String parkingStatusListVo;//json请求数据
	
	private List<ParkingStatusVo> vo;//json数据解析为List对象

	public String getParkingStatusListVo() {
		return parkingStatusListVo;
	}

	public void setParkingStatusListVo(String parkingStatusListVo) {
		this.parkingStatusListVo = parkingStatusListVo;
	}

	public List<ParkingStatusVo> getVo() {
		return vo;
	}

	public void setVo(List<ParkingStatusVo> vo) {
		this.vo = vo;
	}

}
