package com.arf.notice.dto;

import java.io.Serializable;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;

public class NoticeDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String adClickedUrl;//"http://www.ta-rf.cn/",
	private String noticeContent; //"今天天气不错，可以出门去散散步咯"
	private String noticeTime;//"2016/12/13 15:03:23"
	private Integer noticeType;//0 跳URL  1跳原生 2 H5源码
	private JSONArray dataArray;
	private TypeEnum typeEnum; //"DRIP_WASH_LIST"
	private String sourceCode; // "<img src=’img.png’/>"

	public enum TypeEnum{
		DRIP_WASH_LIST;
	}

	public String getAdClickedUrl() {
		return adClickedUrl;
	}

	public void setAdClickedUrl(String adClickedUrl) {
		this.adClickedUrl = adClickedUrl;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
	}

	public Integer getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}

	public JSONArray getDataArray() {
		return dataArray;
	}

	public void setDataArray(JSONArray dataArray) {
		this.dataArray = dataArray;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public TypeEnum getTypeEnum() {
		return typeEnum;
	}

	public void setTypeEnum(TypeEnum typeEnum) {
		this.typeEnum = typeEnum;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}	
	
}
