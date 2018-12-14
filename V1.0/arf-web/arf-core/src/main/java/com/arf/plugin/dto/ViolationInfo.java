package com.arf.plugin.dto;

import com.alibaba.fastjson.JSONArray;

public class ViolationInfo {
	private int status;// 0成功,状态
	private String msg;//返回信息
	private String engineno;//发动机号
	private String frameno;//车架号
	private String lsprefix;//车牌前缀 皖
	private String lsnum;// 车牌剩余部分 B91801
	private String license;//车牌号码
	private String carorg;//anhui
	private String usercarid;//1483850
	private JSONArray list;//违章详细内容 {"time": "2015-06-23 18:24:00.0","address": "赵非公路鼓浪路北约20米","content": "违反规定停放、临时停车且驾驶人不在现场或驾驶人虽在现场拒绝立即驶离，妨碍其他车辆、行人通行的","legalnum": "","price": "0","id": "3500713","score": "0"}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}	
	public String getEngineno() {
		return engineno;
	}
	public void setEngineno(String engineno) {
		this.engineno = engineno;
	}
	public String getFrameno() {
		return frameno;
	}
	public void setFrameno(String frameno) {
		this.frameno = frameno;
	}
	public String getLsprefix() {
		return lsprefix;
	}
	public void setLsprefix(String lsprefix) {
		this.lsprefix = lsprefix;
	}
	public String getLsnum() {
		return lsnum;
	}
	public void setLsnum(String lsnum) {
		this.lsnum = lsnum;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getCarorg() {
		return carorg;
	}
	public void setCarorg(String carorg) {
		this.carorg = carorg;
	}
	public String getUsercarid() {
		return usercarid;
	}
	public void setUsercarid(String usercarid) {
		this.usercarid = usercarid;
	}
	public JSONArray getList() {
		return list;
	}
	public void setList(JSONArray list) {
		this.list = list;
	}
	
	
}
