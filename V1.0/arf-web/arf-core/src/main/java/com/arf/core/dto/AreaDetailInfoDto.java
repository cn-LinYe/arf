package com.arf.core.dto;import java.io.Serializable;/** * 省市区信息DTO * @see {@link com.arf.core.dao.impl.QuanProvCityAreaModelDaoImpl.getAreaDetailInfo(String)} * @author Caolibao * 2016年9月10日 上午10:33:44 * */public class AreaDetailInfoDto implements Serializable{	private static final long serialVersionUID = 1L;	private String areaName; //成安县,	private String no; //130424,	private String areaLevel; //3,	private String geo; //114.67,36.4441,	private String upperAreaname; //邯郸市,	private String upperNo; //130400,	private String upperAreaLevel; //2,	private String upperGeo; //114.491,36.6123,	private String upperAreaname2; //河北省,	private String upperNo2; //130000,	private String upperAreaLevel2;// 1,	private String upperGeo2; //114.502,38.0455	public String getAreaName() {		return areaName;	}	public String getNo() {		return no;	}	public String getAreaLevel() {		return areaLevel;	}	public String getGeo() {		return geo;	}	public String getUpperAreaname() {		return upperAreaname;	}	public String getUpperNo() {		return upperNo;	}	public String getUpperAreaLevel() {		return upperAreaLevel;	}	public String getUpperGeo() {		return upperGeo;	}	public String getUpperAreaname2() {		return upperAreaname2;	}	public String getUpperNo2() {		return upperNo2;	}	public String getUpperAreaLevel2() {		return upperAreaLevel2;	}	public String getUpperGeo2() {		return upperGeo2;	}	public void setAreaName(String areaName) {		this.areaName = areaName;	}	public void setNo(String no) {		this.no = no;	}	public void setAreaLevel(String areaLevel) {		this.areaLevel = areaLevel;	}	public void setGeo(String geo) {		this.geo = geo;	}	public void setUpperAreaname(String upperAreaname) {		this.upperAreaname = upperAreaname;	}	public void setUpperNo(String upperNo) {		this.upperNo = upperNo;	}	public void setUpperAreaLevel(String upperAreaLevel) {		this.upperAreaLevel = upperAreaLevel;	}	public void setUpperGeo(String upperGeo) {		this.upperGeo = upperGeo;	}	public void setUpperAreaname2(String upperAreaname2) {		this.upperAreaname2 = upperAreaname2;	}	public void setUpperNo2(String upperNo2) {		this.upperNo2 = upperNo2;	}	public void setUpperAreaLevel2(String upperAreaLevel2) {		this.upperAreaLevel2 = upperAreaLevel2;	}	public void setUpperGeo2(String upperGeo2) {		this.upperGeo2 = upperGeo2;	}}