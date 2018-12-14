/**
 * @(#)PushConfig.java
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
*  1     2016-2-27       arf      Created
**********************************************
*/

package com.arf.core.util;

/**
 * 推送消息配置表
 *
 * @author arf
 * @since 2016-2-27
 */
public class PushConfig {
	/**
	 * 车牌验证功能 值:fn_license
	 */
	public static final String FN_LICENSE = "fn_license";
	/**
	 * 违章推送 值:fn_violation
	 */
	public static final String FN_VIOLATION = "fn_violation";
	/***
	 * 车闸相关(月租进出闸金币) 值:fn_car
	 */
	public static final String FN_CAR = "fn_car";
	/***
	 * 临时车入闸 值:fn_temporary
	 */
	public static final String FN_TEMPORARY = "fn_temporary";

	/**
	 * 通知类消息不跳转 值:fn_notification
	 */
	public static final String FN_NOTIFICATION = "fn_notification";

	/**
	 * 物业费推送 值:fn_propretyPushmessage
	 */
	public static final String FN_PROPRETYPUSHMESSAGE = "fn_propretyPushmessage";

	/**
	 * 停车费推送 值:fn_partRatePushmessage
	 */
	public static final String FN_PARTRATEPUSHMESSAGE = "fn_partRatePushmessage";
	/**
	 * 开启安心点 值：fn_openaxd
	 */
	public static final String FN_OPENAXD = "fn_openaxd";

	/**
	 * 月租车出闸失败推送 值：fn-parkRate
	 */
	public static final String FN_PARKRATE = "fn-parkRate";
	
	/**
	 * 临时车出闸推送 值：fn_temporaryOut
	 */
	public static final String FN_TEMPORARYOUT = "fn_temporaryOut";
	
	

}
