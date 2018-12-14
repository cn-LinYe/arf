package com.arf.core.oldws;

import java.util.HashMap;
import java.util.Map;
/**
 * 基本  请求结果返回 封装类
 * @author no
 *
 */
public class BaseModelRsp {
	//返回码
	private int code;
	//返回信息描述
	private String msg;
	//额外数据
	private Map<String,Object> extrDatas = new HashMap<String,Object>();
	
	public BaseModelRsp() {
		
		super();
		this.code = -1;
		this.msg = "";
	}
	
	public BaseModelRsp(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public BaseModelRsp(int code, String msg, Map<String, Object> extrDatas) {
		super();
		this.code = code;
		this.msg = msg;
		this.extrDatas = extrDatas;
	}

	public int getCode() {
		return code;
	}

	public BaseModelRsp setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public BaseModelRsp setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Map<String, Object> getExtrDatas() {
		return extrDatas;
	}

	public void setExtrDatas(Map<String, Object> extrDatas) {
		this.extrDatas = extrDatas;
	}
	
	/**
	 * 设置额外数据
	 * @param attrName 额外数据的名字（key,数据唯一）
	 * @param value	        具体数据	
	 */
	public BaseModelRsp setAttrs(String attrName,Object value){
		this.extrDatas.put(attrName, value);
		return this;
	}

	/**
	 * 获取具体的额外数据
	 * @param attrName 额外数据名字
	 * @return		   具体额外数据.
	 */
	public Object getAttrs(String attrName){
		return this.extrDatas.get(attrName);
	}
	
	
}
