package com.arf.traffic.dto;

import com.alibaba.fastjson.JSONObject;

public class ResponseDto {
	private int httpCode;
	private String httpMess;
	private JSONObject jsonObject;
	
	public ResponseDto(){}
	
	public ResponseDto(int code,String message,JSONObject obj){
		this.httpCode=code;
		this.httpMess=message;
		this.jsonObject=obj;
	}
	
	public int getHttpCode() {
		return httpCode;
	}
	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}	
	public String getHttpMess() {
		return httpMess;
	}
	public void setHttpMess(String httpMess) {
		this.httpMess = httpMess;
	}
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
}
