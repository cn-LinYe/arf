package com.arf.plugin.vo;

public class ViolationVo {

	public ViolationVo(String license,String engineno,String frameno){
		this.license=license;
		this.engineno=engineno;
		this.frameno=frameno;
	}
	
	public ViolationVo(String lsprefix,String lsnum,String engineno,String frameno){
		this.lsnum=lsnum;
		this.lsprefix=lsprefix;
		this.engineno=engineno;
		this.frameno=frameno;
	}
	private String lsprefix;//车牌前缀
	private String lsnum;//车牌剩余部分
	private String license;//车牌号码
	private String lstype;//车辆类型车牌类型（ 01:大型汽车号牌 02:小型汽车号牌 03:使馆汽车号牌 04:领馆汽车号牌 05:境外汽车号牌 06:外籍汽车号牌 07:两、三轮摩托车号牌 08:轻便摩托车号牌 09:使馆摩托车号牌 10:领馆摩托车号牌 11:境外摩托车号牌 12:外籍摩托车号牌 13:农用运输车号牌 14:拖拉机号牌15:挂车号牌 16:教练汽车号牌 17:教练摩托车号牌 18:试验汽车号牌 19:试验摩托车号牌 20:临时入境汽车号牌 21:临时入境摩托车号牌 22:临时行驶车号牌 23:警用汽车号牌 24:警用摩托号牌 25:原农机号牌 26:香港入出境车号牌 27:澳门入出境车号牌）
	private String carorg;//管局名称 默认车牌所在地
	private String engineno;//发动机号 根据管局需要输入（100为全部输入，0为不输入）
	private String frameno;	//车架号 根据管局需要输入（100为全部输入，0为不输入）
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
	public String getLstype() {
		return lstype;
	}
	public void setLstype(String lstype) {
		this.lstype = lstype;
	}
	public String getCarorg() {
		return carorg;
	}
	public void setCarorg(String carorg) {
		this.carorg = carorg;
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
	
	
}
