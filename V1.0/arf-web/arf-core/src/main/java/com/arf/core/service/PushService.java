/**   
* @Title: PushService.java 
* @author 广智发   
* @date 2015年12月16日 下午4:07:06 
* @version V1.0   
*/
package com.arf.core.service;

import java.util.Map;

/**
 * Service - 极光推送
 * @Deprecated {@link com.arf.core.oldws.PushUntils}
 * @author arf
 * @version 1.0
 */
@Deprecated
public interface PushService {

	
	/**
	 * 发送短信
	 * 
	 * @param mobiles
	 *            手机号码
	 * @param content
	 *            内容
	 * @param maxRetryTimes
	 *            发送最大次数
	 * @param async
	 *            是否异步
	 */
	void send(String[] mobiles, String content, int maxRetryTimes, boolean async);

	/**
	 * 发送短信
	 * 
	 * @param mobiles
	 *            手机号码
	 * @param templatePath
	 *            模板路径
	 * @param model
	 *            数据
	 * @param maxRetryTimes
	 *            最大重试次数
	 * @param async
	 *            是否异步
	 */
	void send(String[] mobiles, String templatePath, Map<String, Object> model,int maxRetryTimes, boolean async);

	/**
	 * 发送短信(异步)
	 * 
	 * @param mobile
	 *            手机号码
	 * @param content
	 *            内容
	 */
	void send(String mobile, String content);

	/**
	 * 发送短信(异步)
	 * 
	 * @param mobile
	 *            手机号码
	 * @param templatePath
	 *            模板路径
	 * @param model
	 *            数据
	 */
	void send(String mobile, String templatePath, Map<String, Object> model);
	
}
