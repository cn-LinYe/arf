/**
 * @(#)AppMessage.java
 * 
 * Copyright arf.
 *
 * @Version: 1.0
 * @JDK: jdk jdk1.6.0_10
 * @Module: arf-core
 */ 
 /*- 				History
 **********************************************
 *  ID      DATE           PERSON       REASON
 *  1     2015-11-20       arf      Created
 **********************************************
 */

package com.arf.core;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;

import com.google.common.collect.Maps;

/**
 * APP接口返回对象
 * author:arf
 * @author arf
 * @version 4.0
 */
public class AppMessage {

    /**响应*/
    private int code;
    
    /**返回信息描述*/
    private String msg;
    
    /**返回服务器当前时间*/
    private String serverCurrentTime;
    
    /**额外数据*/
    private Map<String,Object> extrDatas = Maps.newHashMap();
    
    public AppMessage(){
    }
    public AppMessage(int code,String msg,Map<String,Object> extrDatas){
        this.code=code;
        this.msg=msg;
        this.serverCurrentTime=DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
        this.extrDatas=extrDatas;
    }
 
    /**  
     * 获取响应  
     * @return code 响应  
     */
    public int getCode() {
        return code;
    }
    /**  
     * 设置响应  
     * @param code 响应  
     */
    public void setCode(int code) {
        this.code = code;
    }
    /**  
     * 获取返回信息描述  
     * @return msg 返回信息描述  
     */
    public String getMsg() {
        return msg;
    }
    /**  
     * 设置返回信息描述  
     * @param msg 返回信息描述  
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    /**  
     * 获取服务器当前时间
     * @return serverTime 返回信息描述  
     */
    public String getServerCurrentTime() {
		return serverCurrentTime;
	}
    
    /**  
     * 设置返回服务器当前时间
     * @param serverTime 返回信息描述  
     */
	public void setServerCurrentTime(String serverCurrentTime) {
		this.serverCurrentTime = serverCurrentTime;
	}
	/**  
     * 获取额外数据  
     * @return extrDatas 额外数据  
     */
    public Map<String, Object> getExtrDatas() {
        return extrDatas;
    }
    /**  
     * 设置额外数据  
     * @param extrDatas 额外数据  
     */
    public void setExtrDatas(Map<String, Object> extrDatas) {
        this.extrDatas = extrDatas;
    }
    /**
     * 请求成功
     * @parameter msg  描述
     * @parameter map  返回的数据对象
     * @return
     */
    public static AppMessage success(String msg,Map<String,Object> map){
        return new AppMessage(AppMessageCode.CODE_SUCCESS,msg,map);
    }
    /**
     * 请求成功
     * @parameter msg  描述
     * @return
     */
    public static AppMessage success(String msg){
        return success(msg,null);
    }
    /**
     * 请求成功
     * @return
     */
    public static AppMessage success(){
        return success(AppMessageCode.MSG_SUCCESS,null);
    }
    
    /**
     * 请求错误
     * @parameter msg  描述
     * @parameter map  返回的数据对象
     * @return
     */
    public static AppMessage error(String msg,Map<String,Object> map){
        return new AppMessage(AppMessageCode.CODE_ERROR,msg,map);
    }
    /**
     * 请求错误
     * @parameter msg  描述
     * @return
     */
    public static AppMessage error(String msg){
        return error(msg,null);
    }
    /**
     * 请求错误
     * @return
     */
    public static AppMessage error(){
        return error(AppMessageCode.MSG_ERROR,null);
    }
    
    /**
     * 用户验证失败
     * @parameter msg  描述
     * @parameter map  返回的数据对象
     * @return
     */
    public static AppMessage valfail(String msg,Map<String,Object> map){
        return new AppMessage(AppMessageCode.CODE_VALFAIL,msg,map);
    }
    /**
     * 用户验证失败
     * @parameter msg  描述
     * @return
     */
    public static AppMessage valfail(String msg){
        return valfail(msg,null);
    }
    /**
     * 用户验证失败
     * @return
     */
    public static AppMessage valfail(){
        return valfail(AppMessageCode.MSG_VALFAIL,null);
    }
    
    
    
    
    
    
    
    /**
     * 服务器异常
     * @parameter msg  描述
     * @parameter map  返回的数据对象
     * @return
     */
    public static AppMessage serverError(String msg,Map<String,Object> map){
        return new AppMessage(AppMessageCode.CODE_SERVER_ERROR,msg,map);
    }
    /**
     * 服务器异常
     * @parameter msg  描述
     * @return
     */
    public static AppMessage serverError(String msg){
        return serverError(msg,null);
    }
    /**
     *服务器异常
     * @return
     */
    public static AppMessage serverError(){
        return serverError(AppMessageCode.MSG_SERVER_ERROR,null);
    }
    
    
    
    
    
    public static AppMessage codeMessage(int code,String message){
        return new AppMessage(code,message,null);
    }
    
    public static AppMessage codeMessage(int code,String message,Map<String,Object> map){
        return new AppMessage(code,message,map);
    }
}
