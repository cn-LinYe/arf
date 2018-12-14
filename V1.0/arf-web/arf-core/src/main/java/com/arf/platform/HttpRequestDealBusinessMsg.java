package com.arf.platform;

import java.util.Map;

public class HttpRequestDealBusinessMsg {
	
	/**
     * 参数校验错误：在发送请求前，对基础的数据进行简单的校验 -1
     */
    public static final int STATUS_PARAM_ERROR = -1;
    
    /**
     * 处理成功 0(有响应)
     */
    public static final int STATUS_SUCCESS = 0;
    
    /**
     * 处理失败 1(无响应)
     */
    public static final int STATUS_FAILED = 1;
    
    public HttpRequestDealBusinessMsg(){}
    
	public HttpRequestDealBusinessMsg(int status, String message) {
		this.status = status;
		this.message = message;
	}

	/**
     * 处理状态
     */
    private int status;
    
    /**
     * 处理信息
     */
    private String message;
    
    ////////////////////////////////////
    
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String msg;
    
    /**
     * 响应业务数据
     */
    private Map<String,Object> businessData;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getBusinessData() {
		return businessData;
	}

	public void setBusinessData(Map<String, Object> businessData) {
		this.businessData = businessData;
	}
	

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "HttpRequestDealBusinessMsg [status=" + status + ", message=" + message + ", businessData="
				+ businessData + "]";
	}
    
}
