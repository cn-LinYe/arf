package com.arf.base;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;

/**
 * App基础信息类,内部属性在app每次调用接口时都会上传这些属性
 * @author Caolibao
 * 2016年7月29日 下午9:23:27
 *
 */
public class AppBaseInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer osName;//手机操作系统:Android:0;IOS :1
    private String osVersion;//手机os版本
    private String appVersionName;//安心点APP当前版本
    private Integer appVersionCode;//安心点APP当前版本号
    private String registrationId;//设备唯一标识,所取值为极光推送对应的registrationId.
    
    public AppBaseInfo(){
    	super();
    }
    
    /**
     * 从HttpServletRequest获取相关属性值构造HttpServletRequest对象
     * @param request
     */
    public AppBaseInfo(HttpServletRequest request){
		PropertyDescriptor descriptors[] = org.springframework.beans.BeanUtils
				.getPropertyDescriptors(AppBaseInfo.class);
		for (int i = 0; i < descriptors.length; i++) {
			try {
				PropertyDescriptor descriptor = descriptors[i];
				Method writeMethod = descriptor.getWriteMethod();
				String name = descriptor.getName();
				if ("class".equals(name)) {
					continue;
				}
				String value = request.getParameter(name);
				if(value == null){
					continue;
				}
				Class<?> type = descriptor.getPropertyType();

				if (type == String.class) {
					writeMethod.invoke(this, value);
				}else if(type == Integer.class){
					writeMethod.invoke(this, Integer.parseInt(value));
				}else if(type == Long.class){
					writeMethod.invoke(this, Long.parseLong(value));
				}else if(type == Double.class){
					writeMethod.invoke(this, Double.parseDouble(value));
				}else if(type == Float.class){
					writeMethod.invoke(this, Float.parseFloat(value));
				}else if(type == Short.class){
					writeMethod.invoke(this, Short.parseShort(value));
				}else if(type == Byte.class){
					writeMethod.invoke(this, Byte.parseByte(value));
				}else if(type == char.class){
					writeMethod.invoke(this, value.charAt(0));
				}else if (type == Date.class) {
					writeMethod.invoke(this, DateUtils.parseDate(value, new String[] { "yyyy-MM-dd HH:mm:ss",
							"yyyy-MM-dd", "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd h:m:s", "yyyy/MM/dd h:m:s" }));
				} else if (type == Boolean.class) {
					writeMethod.invoke(this, Boolean.parseBoolean(value));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
    
	public Integer getOsName() {
		return osName;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public String getAppVersionName() {
		return appVersionName;
	}
	public Integer getAppVersionCode() {
		return appVersionCode;
	}
	public void setOsName(Integer osName) {
		this.osName = osName;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public void setAppVersionName(String appVersionName) {
		this.appVersionName = appVersionName;
	}
	public void setAppVersionCode(Integer appVersionCode) {
		this.appVersionCode = appVersionCode;
	}
	public String getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
}
